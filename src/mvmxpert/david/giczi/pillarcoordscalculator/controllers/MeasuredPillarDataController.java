package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.PLRFileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.FXHomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.InputPillarDataWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.IntersectionDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.IntersectionInputDataWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.MeasPointListDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.MeasurmentDataDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.PillarBaseDifferenceDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.PillarBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Intersection;
import mvmxpert.david.giczi.pillarcoordscalculator.service.MeasPoint;
import mvmxpert.david.giczi.pillarcoordscalculator.service.MeasuredPillarData;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.PointType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.util.List;
import java.util.Optional;

public class MeasuredPillarDataController {

    public final PLRFileProcess fileProcess;
    public final MeasuredPillarData measuredPillarData;
    public MeasPointListDisplayer measuredPointListDisplayer;
    public PillarBaseDifferenceDisplayer pillarBaseDifferenceDisplayer;
    public PillarBaseDisplayer pillarBaseDisplayer;
    public InputPillarDataWindow inputPillarDataWindow;
    public IntersectionInputDataWindow intersectionInputDataWindow;
    public FXHomeWindow fxHomeWindow;
    public List<String> pillarBaseProjectFileData;
    public List<String> measurmentData;
    public Intersection intersection;
    private boolean isCreatedInputPillarDataWindow;
    public static boolean ELEVATION_MEAS_ONLY;
    
    public boolean isCreatedInputPillarDataWindow() {
		return isCreatedInputPillarDataWindow;
	}

	public void setCreatedInputPillarDataWindow(boolean isCreatedInputPillarDataWindow) {
		this.isCreatedInputPillarDataWindow = isCreatedInputPillarDataWindow;
	}

	public MeasuredPillarDataController(FXHomeWindow fxHomeWindow){
    	this.fxHomeWindow = fxHomeWindow;
        this.fileProcess = new PLRFileProcess(this);
        this.measuredPillarData = new MeasuredPillarData(this);
        setCreatedInputPillarDataWindow(true);
    }

    public void init(){
        if( measuredPillarData.getMeasPillarPoints() != null &&
                !measuredPillarData.getMeasPillarPoints().isEmpty() ){
            measuredPillarData.getMeasPillarPoints().clear();
        }
        if( fileProcess.getPillarBaseMeasData() != null && !fileProcess.getPillarBaseMeasData().isEmpty()){
            fileProcess.getPillarBaseMeasData().clear();
        }
        
    }
    public void getInfoAlert(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/MVM.jpg"));
        alert.initOwner(fxHomeWindow.homeStage);
        alert.setTitle(title);
        alert.setHeaderText(text);
        alert.showAndWait();
    }

    public boolean getConfirmationAlert(String title, String text) {
        ButtonType yes = new ButtonType("Igen", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("Nem", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION ,null, no, yes);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image("/img/MVM.jpg"));
        alert.initOwner(fxHomeWindow.homeStage);
        alert.setTitle(title);
        alert.setHeaderText(text);
        Optional<ButtonType> option = alert.showAndWait();
        return option.orElse(no) == yes;
    }
    
    public void openMeasuredData(){
    	init();
        fileProcess.getPillarBaseMeasureFileData();;
        if( fileProcess.getPillarBaseMeasData() == null || fileProcess.getPillarBaseMeasData().isEmpty() ){
            return;
        }
        measuredPillarData.convertMeasuredDataToMeasPoints(fileProcess.getPillarBaseMeasData());
        if( measuredPillarData.getMeasPillarPoints() == null || measuredPillarData.getMeasPillarPoints().isEmpty()) {
            getInfoAlert("Nem beolvasható adat",
                    "Nem található beolvasható mérési eredmény a fájlban.");
        }
        this.measuredPointListDisplayer =
                new MeasPointListDisplayer(this);
    }

    public void createNewProject(){
        measuredPointListDisplayer.stage.hide();
        measuredPointListDisplayer.parseDisplayerData();
        measuredPillarData.calcPillarLegsPoint();
        measuredPillarData.calcPillarTopPoints();
        if( isCreatedInputPillarDataWindow ){
            this.inputPillarDataWindow = new InputPillarDataWindow(this);
        }
    }

    public void addMoreMeasuredPillarData(){
        measuredPointListDisplayer.stage.hide();
        measuredPointListDisplayer.parseDisplayerData();
        fileProcess.getPillarBaseMeasureFileData();;
        measuredPillarData.convertMeasuredDataToMeasPoints(fileProcess.getPillarBaseMeasData());
        if( measuredPillarData.getMeasPillarPoints() == null || measuredPillarData.getMeasPillarPoints().isEmpty() ){
            getInfoAlert("Nem beolvasható adat",
                    "Nem található beolvasható mérési eredmény a fájlban.");
            measuredPointListDisplayer.stage.show();
            return;
        }
        measuredPointListDisplayer.stage.setTitle(PLRFileProcess.MEAS_FILE_NAME);
        measuredPointListDisplayer.addMeasData();
        measuredPointListDisplayer.stage.show();
    }

    public void openNewMeasuredPillarData(){
        measuredPointListDisplayer.stage.hide();
        init();
        openMeasuredData();
    }

    public void onlClickCountButtonProcessForPillarBaseProject(){
        fxHomeWindow.homeStage.hide();
       if( !InputDataValidator.isValidProjectName(inputPillarDataWindow.projectNameField.getText() ) ){
           getInfoAlert("Hibás projektnév megadása", "A projekt neve legalább 3 betű karakter lehet.");
           return;
       }
       PLRFileProcess.PROJECT_FILE_NAME = inputPillarDataWindow.projectNameField.getText().trim();
       if( PLRFileProcess.FOLDER_PATH == null ){
           getInfoAlert("Hiányzó mentési mappa","Mentési mappa választása szükséges");
           return;
       }
       PLRFileProcess.FOLDER_PATH = inputPillarDataWindow.projectPathField.getText();
        int centerPillarID;
       try {
            centerPillarID =
                   InputDataValidator
                           .isValidInputPositiveIntegerValue(inputPillarDataWindow.centerPillarIDField.getText());
       }
       catch (NumberFormatException e){
           getInfoAlert("Nem megfelelő az oszlop száma",
                   "Az oszlop száma csak pozitív egész érték lehet.");
           return;
       }
        double centerPillarX;
        try {
            centerPillarX =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (inputPillarDataWindow.centerPillarField_X.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az oszlop X koordinátája",
                    "Az oszlop X koordinátája csak nem negatív szám lehet.");
            return;
        }
        double centerPillarY;
        try {
            centerPillarY =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az oszlop Y koordinátája",
                    "Az oszlop Y koordinátája csak nem negatív szám lehet.");
            return;
        }

        measuredPillarData.setPillarCenterPoint(new MeasPoint(inputPillarDataWindow.centerPillarIDField.getText(),
                centerPillarX, centerPillarY, 0.0, PointType.CENTER));

        int angle;
        try{
            angle = InputDataValidator.isValidAngleValue(inputPillarDataWindow.rotationAngleField.getText());
        }catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő e forgatás szög értéke",
                    "A forgatás szög értéke csak 360-nál kisebb egész szám lehet.");
            return;
        }
        measuredPillarData.setAngleRotation(angle);
        int min;
        try{
            min = InputDataValidator.isValidMinSecValue(inputPillarDataWindow.rotationMinField.getText());
        }catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a forgatás szögperc értéke",
                    "A forgatás szögperc értéke csak 59-nél kisebb egész szám lehet.");
            return;
        }
        measuredPillarData.setMinRotation(min);
        int sec;
        try{
            sec = InputDataValidator.isValidMinSecValue(inputPillarDataWindow.rotationSecField.getText());
        }catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a forgatás szögmásodperc értéke",
                    "A forgatás szögmásodperc értéke csak 59-nél kisebb egész szám lehet.");
            return;
        }
        measuredPillarData.setSecRotation(sec);
        int directionPillarID;
        try {
            directionPillarID =
                    InputDataValidator
                            .isValidInputPositiveIntegerValue(inputPillarDataWindow.directionPillarIDField.getText());
            if( directionPillarID == centerPillarID ){
                getInfoAlert("Nem megfelelő az előző/következő oszlop száma",
                        "Az előző/következő oszlop száma nem lehet egyenlő az oszlop számával.");
                return;
            }
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az előző/következő oszlop száma",
                    "Az oszlop száma csak pozitív egész érték lehet.");
            return;
        }

        double directionPillarX;
        try {
            directionPillarX =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (inputPillarDataWindow.directionPillarField_X.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az előző/következő oszlop X koordinátája",
                    "Az oszlop X koordinátája csak nem negatív szám lehet.");
            return;
        }

        double directionPillarY;
        try {
            directionPillarY =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (inputPillarDataWindow.directionPillarField_Y.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az előző/következő oszlop Y koordinátája",
                    "Az oszlop Y koordinátája csak nem negatív szám lehet.");
            return;
        }
        measuredPillarData.setBaseLineDirectionPoint(new MeasPoint(inputPillarDataWindow.directionPillarIDField.getText(),
                directionPillarX, directionPillarY, 0.0, PointType.DIRECTION));
        measuredPillarData.addIDsForPillarLegs();
        if( PLRFileProcess.isExistedProjectFile("plr") ){

            if( getConfirmationAlert( "Létező projekt fájl, felülírod?",
                    PLRFileProcess.FOLDER_PATH + "\\" + PLRFileProcess.PROJECT_FILE_NAME + ".plr") ){
                fileProcess.savePillarProjectData();
            }
            else {
                return;
            }
        }
        else {
            fileProcess.savePillarProjectData();
        }
        inputPillarDataWindow.stage.hide();
        this.pillarBaseDisplayer = new PillarBaseDisplayer(this);
    }

    public void openPillarBaseProject(){
       pillarBaseProjectFileData = fileProcess.openPillarBaseProject();
       if( pillarBaseProjectFileData.isEmpty() ){
           return;
       }
       measuredPillarData.parseProjectFileData(pillarBaseProjectFileData);
        if( inputPillarDataWindow != null ){
            inputPillarDataWindow.stage.hide();
        }
        inputPillarDataWindow = new InputPillarDataWindow(this);
    }
    
   
    public void openIntersectionInputDataWindow(){
    	fxHomeWindow.homeStage.hide();
        if( intersectionInputDataWindow == null ){
            intersectionInputDataWindow = new IntersectionInputDataWindow(this);
        }
        else {
        	intersectionInputDataWindow.stage.setTitle("Előmetszés számítása");
            intersectionInputDataWindow.startPointIdField.setText("");
            intersectionInputDataWindow.startField_X.setText("");
            intersectionInputDataWindow.startField_Y.setText("");
            intersectionInputDataWindow.endPointIdField.setText("");
            intersectionInputDataWindow.endField_X.setText("");
            intersectionInputDataWindow.endField_Y.setText("");
            intersectionInputDataWindow.newPointIdField.setText("");
            intersectionInputDataWindow.standingAIdField.setText("");
            intersectionInputDataWindow.standingAPointField_X.setText("");
            intersectionInputDataWindow.standingAPointField_Y.setText("");
            intersectionInputDataWindow.standingAPointField_Z.setText("");
            intersectionInputDataWindow.standingAPointAzimuthAngleField.setText("");
            intersectionInputDataWindow.standingAPointAzimuthMinField.setText("");
            intersectionInputDataWindow.standingAPointAzimuthSecField.setText("");
            intersectionInputDataWindow.standingAPointElevationAngleField.setText("");
            intersectionInputDataWindow.standingAPointElevationMinField.setText("");
            intersectionInputDataWindow.standingAPointElevationSecField.setText("");
            intersectionInputDataWindow.standingBIdField.setText("");
            intersectionInputDataWindow.standingBPointField_X.setText("");
            intersectionInputDataWindow.standingBPointField_Y.setText("");
            intersectionInputDataWindow.standingBPointField_Z.setText("");
            intersectionInputDataWindow.standingBPointAzimuthAngleField.setText("");
            intersectionInputDataWindow.standingBPointAzimuthMinField.setText("");
            intersectionInputDataWindow.standingBPointAzimuthSecField.setText("");
            intersectionInputDataWindow.standingBPointElevationAngleField.setText("");
            intersectionInputDataWindow.standingBPointElevationMinField.setText("");
            intersectionInputDataWindow.standingBPointElevationSecField.setText("");
            intersectionInputDataWindow.elevationMeasureCheckbox.setSelected(ELEVATION_MEAS_ONLY);
            intersectionInputDataWindow.stage.show();
        }
    }

    
    public void onClickCountButtonForElevationMeasureOnly() {
    	ELEVATION_MEAS_ONLY = true;
    	if( !intersectionInputDataWindow.standingAIdField.getText().isEmpty() &&
                intersectionInputDataWindow.standingAPointField_X.getText().isEmpty() &&
                intersectionInputDataWindow.standingAPointField_Y.getText().isEmpty() &&
                intersectionInputDataWindow.standingAPointField_Z.getText().isEmpty() &&
                intersectionInputDataWindow.standingBIdField.getText().isEmpty() &&
                intersectionInputDataWindow.standingBPointField_X.getText().isEmpty() &&
                intersectionInputDataWindow.standingBPointField_Y.getText().isEmpty() &&
                intersectionInputDataWindow.standingBPointField_Z.getText().isEmpty() &&
                !intersectionInputDataWindow.newPointIdField.getText().isEmpty())  {
                fileProcess.getIntersectionMeasureFileData();
            }
    	String startPointId;
    	 if( !InputDataValidator.isValidID(intersectionInputDataWindow.startPointIdField.getText()) ){
             if( !intersectionInputDataWindow.startPointIdField.getText().isEmpty() ){
                 getInfoAlert("Nem megfelelő a sodrony kezdőpontjának megnevezése",
                         "Add meg a sodrony kezdőpontjának megnevezését.");
                 return;
             }

     }
     startPointId = intersectionInputDataWindow.startPointIdField.getText();
     Double startPointX = null;
     try {
         startPointX =
                 InputDataValidator
                         .isValidInputPositiveDoubleValue
                                 (intersectionInputDataWindow.startField_X.getText().replace(",", "."));
     }
     catch (NumberFormatException e){
        if( !intersectionInputDataWindow.startField_X.getText().isEmpty() ){
            getInfoAlert("Nem megfelelő a sodrony kezdőpontjának Y koordinátája",
                    "A sodrony kezdőpontjának Y koordinátája csak nem negatív szám lehet.");
            return;
        }
     }
     Double startPointY = null;
     try {
         startPointY =
                 InputDataValidator
                         .isValidInputPositiveDoubleValue
                                 (intersectionInputDataWindow.startField_Y.getText().replace(",", "."));
     }
     catch (NumberFormatException e){
         if( !intersectionInputDataWindow.startField_Y.getText().isEmpty() ){
             getInfoAlert("Nem megfelelő a sodrony kezdőpontjának X koordinátája",
                     "A sodrony kezdőpontjának X koordinátája csak nem negatív szám lehet.");
             return;
         }
     }
     String endPointId;
     if( !InputDataValidator.isValidID(intersectionInputDataWindow.endPointIdField.getText()) ){
         if( !intersectionInputDataWindow.endPointIdField.getText().isEmpty() ){
             getInfoAlert("Nem megfelelő a sodrony végpontjának megnevezése",
                     "Add meg a sodrony végpontjának megnevezését.");
             return;
         }

     }
     endPointId = intersectionInputDataWindow.endPointIdField.getText();
     Double endPointX = null;
     try {
         endPointX =
                 InputDataValidator
                         .isValidInputPositiveDoubleValue
                                 (intersectionInputDataWindow.endField_X.getText().replace(",", "."));
     }
     catch (NumberFormatException e){
         if( !intersectionInputDataWindow.endField_X.getText().isEmpty() ){
             getInfoAlert("Nem megfelelő a sodrony végpontjának Y koordinátája",
                     "A sodrony végpontjának Y koordinátája csak nem negatív szám lehet.");
             return;
         }
     }

     Double endPointY = null;
     try {
         endPointY =
                 InputDataValidator
                         .isValidInputPositiveDoubleValue
                                 (intersectionInputDataWindow.endField_Y.getText().replace(",", "."));
     }
     catch (NumberFormatException e){
         if( !intersectionInputDataWindow.endField_Y.getText().isEmpty() ){
             getInfoAlert("Nem megfelelő a sodrony végpontjának X koordinátája",
                     "A sodrony végpontjának X koordinátája csak nem negatív szám lehet.");
             return;
         }
     }
     String newPointId;
     if( !InputDataValidator.isValidID(intersectionInputDataWindow.newPointIdField.getText()) ){
         getInfoAlert("Nem megfelelő az új pont megnevezése",
                 "Add meg az új pont megnevezését.");
         return;
     }
     newPointId = intersectionInputDataWindow.newPointIdField.getText();

     String standingAPointId;
     if( !InputDataValidator.isValidID(intersectionInputDataWindow.standingAIdField.getText()) ){
         getInfoAlert("Nem megfelelő az 1. álláspont megnevezése",
                 "Add meg az 1. álláspont megnevezését.");
         return;
     }
     standingAPointId = intersectionInputDataWindow.standingAIdField.getText();

     double standingPointA_X;
     try {
         standingPointA_X =
                 InputDataValidator
                         .isValidInputPositiveDoubleValue
                                 (intersectionInputDataWindow.standingAPointField_X.getText().replace(",", "."));
     }
     catch (NumberFormatException e){
         getInfoAlert("Nem megfelelő az 1. álláspont Y koordinátája",
                 "Az 1. álláspont Y koordinátája csak nem negatív szám lehet.");
         return;
     }
     double standingPointA_Y;
     try {
         standingPointA_Y =
                 InputDataValidator
                         .isValidInputPositiveDoubleValue
                                 (intersectionInputDataWindow.standingAPointField_Y.getText().replace(",", "."));
     }
     catch (NumberFormatException e){
         getInfoAlert("Nem megfelelő az 1. álláspont X koordinátája",
                 "Az 1. álláspont X koordinátája csak nem negatív szám lehet.");
         return;
     }

     double standingPointA_Z;
     try {
         standingPointA_Z =
                 InputDataValidator
                         .isValidInputPositiveDoubleValue
                                 (intersectionInputDataWindow.standingAPointField_Z.getText().replace(",", "."));
     }
     catch (NumberFormatException e){
         getInfoAlert("Nem megfelelő az 1. álláspont magassága",
                 "Az 1. álláspont magassági adata csak nem negatív szám lehet.");
         return;
     }
     int standingAPointHzAngle;
     try {
        standingAPointHzAngle  = InputDataValidator
                 .isValidAngleValue(intersectionInputDataWindow
                         .standingAPointAzimuthAngleField.getText());
     }
     catch (NumberFormatException e){
         getInfoAlert("Nem megfelelő az 1. állásponton mért víszintes szög érték",
                 "A vízszintes szög értéke egész szám és -360 < érték < 360 lehet.");
         return;
     }
     int standingAPointHzMin;
     try {
         standingAPointHzMin  = InputDataValidator
                 .isValidMinSecValue(intersectionInputDataWindow
                         .standingAPointAzimuthMinField.getText());
     }
     catch (NumberFormatException e){
         getInfoAlert("Nem megfelelő az 1. állásponton mért víszintes szögperc érték",
                 "A vízszintes szögperc értéke egész szám és -1 < érték < 60 lehet.");
         return;
     }
     int standingAPointHzSec;
     try {
         standingAPointHzSec  = InputDataValidator
                 .isValidMinSecValue(intersectionInputDataWindow
                         .standingAPointAzimuthSecField.getText());
     }
     catch (NumberFormatException e){
         getInfoAlert("Nem megfelelő az 1. állásponton mért víszintes szögmásodperc érték",
                 "A vízszintes szögmásodperc értéke egész szám és -1 < érték < 60 lehet.");
         return;
     }
     int standingAPointElevationAngle;
     try {
         standingAPointElevationAngle  = InputDataValidator
                 .isValidElevationAngleValue(intersectionInputDataWindow
                         .standingAPointElevationAngleField.getText());
     }
     catch (NumberFormatException e){
         getInfoAlert("Nem megfelelő az 1. állásponton mért magassági szög érték",
                 "A magassági szög értéke egész szám és -1 < érték < 181 lehet.");
         return;
     }

     int standingAPointElevationMin;
     try {
         standingAPointElevationMin  = InputDataValidator
                 .isValidMinSecValue(intersectionInputDataWindow
                         .standingAPointElevationMinField.getText());
     }
     catch (NumberFormatException e){
         getInfoAlert("Nem megfelelő az 1. állásponton mért magassági szögperc érték",
                 "A magassági szögperc értéke egész szám és -1 < érték < 60 lehet.");
         return;
     }

     int standingAPointElevationSec;
     try {
         standingAPointElevationSec  = InputDataValidator
                 .isValidMinSecValue(intersectionInputDataWindow
                         .standingAPointElevationMinField.getText());
     }
     catch (NumberFormatException e){
         getInfoAlert("Nem megfelelő az 1. állásponton mért magassági szögmásodperc érték",
                 "A magassági szögmásodperc értéke egész szám és -1 < érték < 60 lehet.");
         return;
     }
     
     Point startPoint = null;
     Point endPoint = null;

     if( startPointId != null && startPointX != null && startPointY != null &&
         endPointId != null && endPointX != null && endPointY != null ){

         startPoint = new Point(startPointId, startPointX, startPointY);
         endPoint = new Point(endPointId, endPointX, endPointY);
     }
     MeasPoint standingPointA = new MeasPoint(standingAPointId,
             standingPointA_X, standingPointA_Y, standingPointA_Z, null);
     fileProcess.setFolder();
     if( intersection == null ) {
     	intersection = new Intersection();
     }
     intersection.setStandingPointA(standingPointA);
     intersection.setLineStartPoint(startPoint);
     intersection.setLineEndPoint(endPoint);
     intersection.setAzimuthAngleA(standingAPointHzAngle);
     intersection.setAzimuthMinuteA(standingAPointHzMin);
     intersection.setAzimuthSecA(standingAPointHzSec);
     intersection.setElevationAngleA(standingAPointElevationAngle);
     intersection.setElevationMinuteA(standingAPointElevationMin);
     intersection.setElevationSecA(standingAPointElevationSec);
     intersection.calcElevationOnly();
     intersection.getIntersectionPoint().setPointID(newPointId);
    
     if( PLRFileProcess.FOLDER_PATH == null ) {
     	return;
     }
     PLRFileProcess.PROJECT_FILE_NAME = intersectionInputDataWindow.standingAIdField.getText().toUpperCase() +
             "-" + intersectionInputDataWindow.standingBIdField.getText().toUpperCase() + "_" +
             intersectionInputDataWindow.newPointIdField.getText().toUpperCase() + "_METSZES";

     if( PLRFileProcess.isExistedProjectFile("ins") ){
         if( getConfirmationAlert( "Létező projekt fájl, felülírod?",
                 PLRFileProcess.FOLDER_PATH + "\\" + PLRFileProcess.PROJECT_FILE_NAME + ".ins") ){
             fileProcess.saveIntersectionData();
         }
         else {
             intersectionInputDataWindow.stage.show();
             return;
         }
     }
     else {
         fileProcess.saveIntersectionData();
     }
     intersectionInputDataWindow.stage.hide();
     new IntersectionDisplayer(this);		
    }
    
    
    private void loadMeasureFileData(){
        if( !intersectionInputDataWindow.standingAIdField.getText().isEmpty() &&
            intersectionInputDataWindow.standingAPointField_X.getText().isEmpty() &&
            intersectionInputDataWindow.standingAPointField_Y.getText().isEmpty() &&
            intersectionInputDataWindow.standingAPointField_Z.getText().isEmpty() &&
            !intersectionInputDataWindow.standingBIdField.getText().isEmpty() &&
            intersectionInputDataWindow.standingBPointField_X.getText().isEmpty() &&
            intersectionInputDataWindow.standingBPointField_Y.getText().isEmpty() &&
            intersectionInputDataWindow.standingBPointField_Z.getText().isEmpty() &&
            !intersectionInputDataWindow.newPointIdField.getText().isEmpty())  {
            fileProcess.getIntersectionMeasureFileData();
        }
    }
    
    public void onClickCountButtonForIntersectionProcess(){
    	ELEVATION_MEAS_ONLY = false;
        loadMeasureFileData();
        String startPointId;
        if( !InputDataValidator.isValidID(intersectionInputDataWindow.startPointIdField.getText()) ){
                if( !intersectionInputDataWindow.startPointIdField.getText().isEmpty() ){
                    getInfoAlert("Nem megfelelő a sodrony kezdőpontjának megnevezése",
                            "Add meg a sodrony kezdőpontjának megnevezését.");
                    return;
                }

        }
        startPointId = intersectionInputDataWindow.startPointIdField.getText();
        Double startPointX = null;
        try {
            startPointX =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.startField_X.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
           if( !intersectionInputDataWindow.startField_X.getText().isEmpty() ){
               getInfoAlert("Nem megfelelő a sodrony kezdőpontjának Y koordinátája",
                       "A sodrony kezdőpontjának Y koordinátája csak nem negatív szám lehet.");
               return;
           }
        }
        Double startPointY = null;
        try {
            startPointY =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.startField_Y.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            if( !intersectionInputDataWindow.startField_Y.getText().isEmpty() ){
                getInfoAlert("Nem megfelelő a sodrony kezdőpontjának X koordinátája",
                        "A sodrony kezdőpontjának X koordinátája csak nem negatív szám lehet.");
                return;
            }
        }
        String endPointId;
        if( !InputDataValidator.isValidID(intersectionInputDataWindow.endPointIdField.getText()) ){
            if( !intersectionInputDataWindow.endPointIdField.getText().isEmpty() ){
                getInfoAlert("Nem megfelelő a sodrony végpontjának megnevezése",
                        "Add meg a sodrony végpontjának megnevezését.");
                return;
            }

        }
        endPointId = intersectionInputDataWindow.endPointIdField.getText();
        Double endPointX = null;
        try {
            endPointX =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.endField_X.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            if( !intersectionInputDataWindow.endField_X.getText().isEmpty() ){
                getInfoAlert("Nem megfelelő a sodrony végpontjának Y koordinátája",
                        "A sodrony végpontjának Y koordinátája csak nem negatív szám lehet.");
                return;
            }
        }

        Double endPointY = null;
        try {
            endPointY =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.endField_Y.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            if( !intersectionInputDataWindow.endField_Y.getText().isEmpty() ){
                getInfoAlert("Nem megfelelő a sodrony végpontjának X koordinátája",
                        "A sodrony végpontjának X koordinátája csak nem negatív szám lehet.");
                return;
            }
        }
        String newPointId;
        if( !InputDataValidator.isValidID(intersectionInputDataWindow.newPointIdField.getText()) ){
            getInfoAlert("Nem megfelelő az új pont megnevezése",
                    "Add meg az új pont megnevezését.");
            return;
        }
        newPointId = intersectionInputDataWindow.newPointIdField.getText();

        String standingAPointId;
        if( !InputDataValidator.isValidID(intersectionInputDataWindow.standingAIdField.getText()) ){
            getInfoAlert("Nem megfelelő az 1. álláspont megnevezése",
                    "Add meg az 1. álláspont megnevezését.");
            return;
        }
        standingAPointId = intersectionInputDataWindow.standingAIdField.getText();

        double standingPointA_X;
        try {
            standingPointA_X =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.standingAPointField_X.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az 1. álláspont Y koordinátája",
                    "Az 1. álláspont Y koordinátája csak nem negatív szám lehet.");
            return;
        }
        double standingPointA_Y;
        try {
            standingPointA_Y =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.standingAPointField_Y.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az 1. álláspont X koordinátája",
                    "Az 1. álláspont X koordinátája csak nem negatív szám lehet.");
            return;
        }

        double standingPointA_Z;
        try {
            standingPointA_Z =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.standingAPointField_Z.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az 1. álláspont magassága",
                    "Az 1. álláspont magassági adata csak nem negatív szám lehet.");
            return;
        }
        int standingAPointHzAngle;
        try {
           standingAPointHzAngle  = InputDataValidator
                    .isValidAngleValue(intersectionInputDataWindow
                            .standingAPointAzimuthAngleField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az 1. állásponton mért víszintes szög érték",
                    "A vízszintes szög értéke egész szám és -360 < érték < 360 lehet.");
            return;
        }
        int standingAPointHzMin;
        try {
            standingAPointHzMin  = InputDataValidator
                    .isValidMinSecValue(intersectionInputDataWindow
                            .standingAPointAzimuthMinField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az 1. állásponton mért víszintes szögperc érték",
                    "A vízszintes szögperc értéke egész szám és -1 < érték < 60 lehet.");
            return;
        }
        int standingAPointHzSec;
        try {
            standingAPointHzSec  = InputDataValidator
                    .isValidMinSecValue(intersectionInputDataWindow
                            .standingAPointAzimuthSecField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az 1. állásponton mért víszintes szögmásodperc érték",
                    "A vízszintes szögmásodperc értéke egész szám és -1 < érték < 60 lehet.");
            return;
        }
        int standingAPointElevationAngle;
        try {
            standingAPointElevationAngle  = InputDataValidator
                    .isValidElevationAngleValue(intersectionInputDataWindow
                            .standingAPointElevationAngleField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az 1. állásponton mért magassági szög érték",
                    "A magassági szög értéke egész szám és -1 < érték < 181 lehet.");
            return;
        }

        int standingAPointElevationMin;
        try {
            standingAPointElevationMin  = InputDataValidator
                    .isValidMinSecValue(intersectionInputDataWindow
                            .standingAPointElevationMinField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az 1. állásponton mért magassági szögperc érték",
                    "A magassági szögperc értéke egész szám és -1 < érték < 60 lehet.");
            return;
        }

        int standingAPointElevationSec;
        try {
            standingAPointElevationSec  = InputDataValidator
                    .isValidMinSecValue(intersectionInputDataWindow
                            .standingAPointElevationMinField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő az 1. állásponton mért magassági szögmásodperc érték",
                    "A magassági szögmásodperc értéke egész szám és -1 < érték < 60 lehet.");
            return;
        }

        String standingBPointId;
        if( !InputDataValidator.isValidID(intersectionInputDataWindow.standingBIdField.getText()) ){
            getInfoAlert("Nem megfelelő az 2. álláspont megnevezése",
                    "Add meg az 2. álláspont megnevezését.");
            return;
        }
        standingBPointId = intersectionInputDataWindow.standingBIdField.getText();

        double standingPointB_X;
        try {
            standingPointB_X =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.standingBPointField_X.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a 2. álláspont Y koordinátája",
                    "A 2. álláspont Y koordinátája csak nem negatív szám lehet.");
            return;
        }
        double standingPointB_Y;
        try {
            standingPointB_Y =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.standingBPointField_Y.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a 2. álláspont X koordinátája",
                    "Az 2. álláspont X koordinátája csak nem negatív szám lehet.");
            return;
        }

        double standingPointB_Z;
        try {
            standingPointB_Z =
                    InputDataValidator
                            .isValidInputPositiveDoubleValue
                                    (intersectionInputDataWindow.standingBPointField_Z.getText().replace(",", "."));
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a 2. álláspont magassága",
                    "A 2. álláspont magassági adata csak nem negatív szám lehet.");
            return;
        }
        int standingBPointHzAngle;
        try {
            standingBPointHzAngle  = InputDataValidator
                    .isValidAngleValue(intersectionInputDataWindow
                            .standingBPointAzimuthAngleField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a 2. állásponton mért víszintes szög érték",
                    "A vízszintes szög értéke egész szám és -360 < érték < 360 lehet.");
            return;
        }
        int standingBPointHzMin;
        try {
            standingBPointHzMin  = InputDataValidator
                    .isValidMinSecValue(intersectionInputDataWindow
                            .standingBPointAzimuthMinField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a 2. állásponton mért víszintes szögperc érték",
                    "A vízszintes szögperc értéke egész szám és -1 < érték < 60 lehet.");
            return;
        }
        int standingBPointHzSec;
        try {
            standingBPointHzSec  = InputDataValidator
                    .isValidMinSecValue(intersectionInputDataWindow
                            .standingBPointAzimuthSecField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a 2. állásponton mért víszintes szögmásodperc érték",
                    "A vízszintes szögmásodperc értéke egész szám és -1 < érték < 60 lehet.");
            return;
        }
        int standingBPointElevationAngle;
        try {
            standingBPointElevationAngle  = InputDataValidator
                    .isValidElevationAngleValue(intersectionInputDataWindow
                            .standingBPointElevationAngleField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a 2. állásponton mért magassági szög érték",
                    "A magassági szög értéke egész szám és -1 < érték < 181 lehet.");
            return;
        }

        int standingBPointElevationMin;
        try {
            standingBPointElevationMin  = InputDataValidator
                    .isValidMinSecValue(intersectionInputDataWindow
                            .standingBPointElevationMinField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a 2. állásponton mért magassági szögperc érték",
                    "A magassági szögperc értéke egész szám és -1 < érték < 60 lehet.");
            return;
        }

        int standingBPointElevationSec;
        try {
            standingBPointElevationSec  = InputDataValidator
                    .isValidMinSecValue(intersectionInputDataWindow
                            .standingBPointElevationMinField.getText());
        }
        catch (NumberFormatException e){
            getInfoAlert("Nem megfelelő a 2. állásponton mért magassági szögmásodperc érték",
                    "A magassági szögmásodperc értéke egész szám és -1 < érték < 60 lehet.");
            return;
        }
        
        Point startPoint = null;
        Point endPoint = null;

        if( startPointId != null && startPointX != null && startPointY != null &&
            endPointId != null && endPointX != null && endPointY != null ){

            startPoint = new Point(startPointId, startPointX, startPointY);
            endPoint = new Point(endPointId, endPointX, endPointY);
        }
        MeasPoint standingPointA = new MeasPoint(standingAPointId,
                standingPointA_X, standingPointA_Y, standingPointA_Z, null);
        MeasPoint standingPointB = new MeasPoint(standingBPointId,
                standingPointB_X, standingPointB_Y, standingPointB_Z, null);
        fileProcess.setFolder();
        
        if( intersection == null ) {
        	intersection = new Intersection();
        }
        intersection.setStandingPointA(standingPointA);
        intersection.setStandingPointB(standingPointB);
        intersection.setLineStartPoint(startPoint);
        intersection.setLineEndPoint(endPoint);
        intersection.setAzimuthAngleA(standingAPointHzAngle);
        intersection.setAzimuthMinuteA(standingAPointHzMin);
        intersection.setAzimuthSecA(standingAPointHzSec);
        intersection.setElevationAngleA(standingAPointElevationAngle);
        intersection.setElevationMinuteA(standingAPointElevationMin);
        intersection.setElevationSecA(standingAPointElevationSec);
        intersection.setAzimuthAngleB(standingBPointHzAngle);
        intersection.setAzimuthMinuteB(standingBPointHzMin);
        intersection.setAzimuthSecB(standingBPointHzSec);
        intersection.setElevationAngleB(standingBPointElevationAngle);
        intersection.setElevationMinuteB(standingBPointElevationMin);
        intersection.setElevationSecB(standingBPointElevationSec);
        intersection.calcIntersectionPoint();
        intersection.getIntersectionPoint().setPointID(newPointId);
       
        if( PLRFileProcess.FOLDER_PATH == null ) {
        	return;
        }
        PLRFileProcess.PROJECT_FILE_NAME = intersectionInputDataWindow.standingAIdField.getText().toUpperCase() +
                "-" + intersectionInputDataWindow.standingBIdField.getText().toUpperCase() + "_" +
                intersectionInputDataWindow.newPointIdField.getText().toUpperCase() + "_METSZES";

        if( PLRFileProcess.isExistedProjectFile("ins") ){
            if( getConfirmationAlert( "Létező projekt fájl, felülírod?",
                    PLRFileProcess.FOLDER_PATH + "\\" + PLRFileProcess.PROJECT_FILE_NAME + ".ins") ){
                fileProcess.saveIntersectionData();
            }
            else {
                intersectionInputDataWindow.stage.show();
                return;
            }
        }
        else {
            fileProcess.saveIntersectionData();
        }
        intersectionInputDataWindow.stage.hide();
        
        new IntersectionDisplayer(this);
    }
    
    public void openMeasurmentFXDisplayer() {
    	measurmentData = fileProcess.openMeasurmentFileData();
        if( PLRFileProcess.MEAS_FILE_NAME == null ) {
     	   return;
        }
         if( measurmentData.isEmpty() ){
             return;
         }
		new MeasurmentDataDisplayer(this);
	}
    
    public void openIntersectionProject(){
        List<String>  intersectionProjectFileData = fileProcess.openIntersectionProject();
       if( PLRFileProcess.PROJECT_FILE_NAME == null ) {
    	   return;
       }
        if( intersectionProjectFileData.isEmpty() ){
            return;
        }
        else if( intersectionProjectFileData.size() == 13 || 
        		 intersectionProjectFileData.size() == 17 || 
        		 intersectionProjectFileData.size() == 19) {
        	MeasuredPillarDataController.ELEVATION_MEAS_ONLY = true;
        }
        else {
        	MeasuredPillarDataController.ELEVATION_MEAS_ONLY = false;
        }
        openIntersectionInputDataWindow();
        intersectionInputDataWindow.stage.setTitle(PLRFileProcess.PROJECT_FILE_NAME + ".ins");
        intersection = new Intersection();
        if( intersectionProjectFileData.size() == 13 ) {
        	intersectionInputDataWindow.newPointIdField.setText(intersectionProjectFileData.get(0));
            intersectionInputDataWindow.standingAIdField.setText(intersectionProjectFileData.get(1));
            intersectionInputDataWindow.standingAPointField_X.setText(intersectionProjectFileData.get(2));
            intersectionInputDataWindow.standingAPointField_Y.setText(intersectionProjectFileData.get(3));
            intersectionInputDataWindow.standingAPointField_Z.setText(intersectionProjectFileData.get(4));
            intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(intersectionProjectFileData.get(5));
            intersectionInputDataWindow.standingAPointAzimuthMinField.setText(intersectionProjectFileData.get(6));
            intersectionInputDataWindow.standingAPointAzimuthSecField.setText(intersectionProjectFileData.get(7));
            intersectionInputDataWindow.standingAPointElevationAngleField.setText(intersectionProjectFileData.get(8));
            intersectionInputDataWindow.standingAPointElevationMinField.setText(intersectionProjectFileData.get(9));
            intersectionInputDataWindow.standingAPointElevationSecField.setText(intersectionProjectFileData.get(10));
            intersection.setTheoreticalPoint(new Point("TheoreticalPoint", Double.parseDouble(intersectionProjectFileData.get(11)), 
            		Double.parseDouble(intersectionProjectFileData.get(12))));
        }
        else if( intersectionProjectFileData.size() == 17 ) {
        	 intersectionInputDataWindow.startPointIdField.setText(intersectionProjectFileData.get(0));
             intersectionInputDataWindow.startField_X.setText(intersectionProjectFileData.get(1));
             intersectionInputDataWindow.startField_Y.setText(intersectionProjectFileData.get(2));
             intersectionInputDataWindow.endPointIdField.setText(intersectionProjectFileData.get(3));
             intersectionInputDataWindow.endField_X.setText(intersectionProjectFileData.get(4));
             intersectionInputDataWindow.endField_Y.setText(intersectionProjectFileData.get(5));
             intersectionInputDataWindow.newPointIdField.setText(intersectionProjectFileData.get(6));
             intersectionInputDataWindow.standingAIdField.setText(intersectionProjectFileData.get(7));
             intersectionInputDataWindow.standingAPointField_X.setText(intersectionProjectFileData.get(8));
             intersectionInputDataWindow.standingAPointField_Y.setText(intersectionProjectFileData.get(9));
             intersectionInputDataWindow.standingAPointField_Z.setText(intersectionProjectFileData.get(10));
             intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(intersectionProjectFileData.get(11));
             intersectionInputDataWindow.standingAPointAzimuthMinField.setText(intersectionProjectFileData.get(12));
             intersectionInputDataWindow.standingAPointAzimuthSecField.setText(intersectionProjectFileData.get(13));
             intersectionInputDataWindow.standingAPointElevationAngleField.setText(intersectionProjectFileData.get(14));
             intersectionInputDataWindow.standingAPointElevationMinField.setText(intersectionProjectFileData.get(15));
             intersectionInputDataWindow.standingAPointElevationSecField.setText(intersectionProjectFileData.get(16));
        }
        else if( intersectionProjectFileData.size() == 19 ) {
       	 	intersectionInputDataWindow.startPointIdField.setText(intersectionProjectFileData.get(0));
            intersectionInputDataWindow.startField_X.setText(intersectionProjectFileData.get(1));
            intersectionInputDataWindow.startField_Y.setText(intersectionProjectFileData.get(2));
            intersectionInputDataWindow.endPointIdField.setText(intersectionProjectFileData.get(3));
            intersectionInputDataWindow.endField_X.setText(intersectionProjectFileData.get(4));
            intersectionInputDataWindow.endField_Y.setText(intersectionProjectFileData.get(5));
            intersectionInputDataWindow.newPointIdField.setText(intersectionProjectFileData.get(6));
            intersectionInputDataWindow.standingAIdField.setText(intersectionProjectFileData.get(7));
            intersectionInputDataWindow.standingAPointField_X.setText(intersectionProjectFileData.get(8));
            intersectionInputDataWindow.standingAPointField_Y.setText(intersectionProjectFileData.get(9));
            intersectionInputDataWindow.standingAPointField_Z.setText(intersectionProjectFileData.get(10));
            intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(intersectionProjectFileData.get(11));
            intersectionInputDataWindow.standingAPointAzimuthMinField.setText(intersectionProjectFileData.get(12));
            intersectionInputDataWindow.standingAPointAzimuthSecField.setText(intersectionProjectFileData.get(13));
            intersectionInputDataWindow.standingAPointElevationAngleField.setText(intersectionProjectFileData.get(14));
            intersectionInputDataWindow.standingAPointElevationMinField.setText(intersectionProjectFileData.get(15));
            intersectionInputDataWindow.standingAPointElevationSecField.setText(intersectionProjectFileData.get(16));
            intersection.setTheoreticalPoint(new Point("TheoreticalPoint", Double.parseDouble(intersectionProjectFileData.get(17)), 
            		Double.parseDouble(intersectionProjectFileData.get(18))));
       }
        else if( intersectionProjectFileData.size() == 23){
            intersectionInputDataWindow.newPointIdField.setText(intersectionProjectFileData.get(0));
            intersectionInputDataWindow.standingAIdField.setText(intersectionProjectFileData.get(1));
            intersectionInputDataWindow.standingAPointField_X.setText(intersectionProjectFileData.get(2));
            intersectionInputDataWindow.standingAPointField_Y.setText(intersectionProjectFileData.get(3));
            intersectionInputDataWindow.standingAPointField_Z.setText(intersectionProjectFileData.get(4));
            intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(intersectionProjectFileData.get(5));
            intersectionInputDataWindow.standingAPointAzimuthMinField.setText(intersectionProjectFileData.get(6));
            intersectionInputDataWindow.standingAPointAzimuthSecField.setText(intersectionProjectFileData.get(7));
            intersectionInputDataWindow.standingAPointElevationAngleField.setText(intersectionProjectFileData.get(8));
            intersectionInputDataWindow.standingAPointElevationMinField.setText(intersectionProjectFileData.get(9));
            intersectionInputDataWindow.standingAPointElevationSecField.setText(intersectionProjectFileData.get(10));
            intersectionInputDataWindow.standingBIdField.setText(intersectionProjectFileData.get(11));
            intersectionInputDataWindow.standingBPointField_X.setText(intersectionProjectFileData.get(12));
            intersectionInputDataWindow.standingBPointField_Y.setText(intersectionProjectFileData.get(13));
            intersectionInputDataWindow.standingBPointField_Z.setText(intersectionProjectFileData.get(14));
            intersectionInputDataWindow.standingBPointAzimuthAngleField.setText(intersectionProjectFileData.get(15));
            intersectionInputDataWindow.standingBPointAzimuthMinField.setText(intersectionProjectFileData.get(16));
            intersectionInputDataWindow.standingBPointAzimuthSecField.setText(intersectionProjectFileData.get(17));
            intersectionInputDataWindow.standingBPointElevationAngleField.setText(intersectionProjectFileData.get(18));
            intersectionInputDataWindow.standingBPointElevationMinField.setText(intersectionProjectFileData.get(19));
            intersectionInputDataWindow.standingBPointElevationSecField.setText(intersectionProjectFileData.get(20));
            intersection.setTheoreticalPoint(new Point("TheoreticalPoint", Double.parseDouble(intersectionProjectFileData.get(21)), 
            		Double.parseDouble(intersectionProjectFileData.get(22))));
        }
        else if( intersectionProjectFileData.size() == 27 ){
            intersectionInputDataWindow.startPointIdField.setText(intersectionProjectFileData.get(0));
            intersectionInputDataWindow.startField_X.setText(intersectionProjectFileData.get(1));
            intersectionInputDataWindow.startField_Y.setText(intersectionProjectFileData.get(2));
            intersectionInputDataWindow.endPointIdField.setText(intersectionProjectFileData.get(3));
            intersectionInputDataWindow.endField_X.setText(intersectionProjectFileData.get(4));
            intersectionInputDataWindow.endField_Y.setText(intersectionProjectFileData.get(5));
            intersectionInputDataWindow.newPointIdField.setText(intersectionProjectFileData.get(6));
            intersectionInputDataWindow.standingAIdField.setText(intersectionProjectFileData.get(7));
            intersectionInputDataWindow.standingAPointField_X.setText(intersectionProjectFileData.get(8));
            intersectionInputDataWindow.standingAPointField_Y.setText(intersectionProjectFileData.get(9));
            intersectionInputDataWindow.standingAPointField_Z.setText(intersectionProjectFileData.get(10));
            intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(intersectionProjectFileData.get(11));
            intersectionInputDataWindow.standingAPointAzimuthMinField.setText(intersectionProjectFileData.get(12));
            intersectionInputDataWindow.standingAPointAzimuthSecField.setText(intersectionProjectFileData.get(13));
            intersectionInputDataWindow.standingAPointElevationAngleField.setText(intersectionProjectFileData.get(14));
            intersectionInputDataWindow.standingAPointElevationMinField.setText(intersectionProjectFileData.get(15));
            intersectionInputDataWindow.standingAPointElevationSecField.setText(intersectionProjectFileData.get(16));
            intersectionInputDataWindow.standingBIdField.setText(intersectionProjectFileData.get(17));
            intersectionInputDataWindow.standingBPointField_X.setText(intersectionProjectFileData.get(18));
            intersectionInputDataWindow.standingBPointField_Y.setText(intersectionProjectFileData.get(19));
            intersectionInputDataWindow.standingBPointField_Z.setText(intersectionProjectFileData.get(20));
            intersectionInputDataWindow.standingBPointAzimuthAngleField.setText(intersectionProjectFileData.get(21));
            intersectionInputDataWindow.standingBPointAzimuthMinField.setText(intersectionProjectFileData.get(22));
            intersectionInputDataWindow.standingBPointAzimuthSecField.setText(intersectionProjectFileData.get(23));
            intersectionInputDataWindow.standingBPointElevationAngleField.setText(intersectionProjectFileData.get(24));
            intersectionInputDataWindow.standingBPointElevationMinField.setText(intersectionProjectFileData.get(25));
            intersectionInputDataWindow.standingBPointElevationSecField.setText(intersectionProjectFileData.get(26));
        }
        else if( intersectionProjectFileData.size() == 29 ){
            intersectionInputDataWindow.startPointIdField.setText(intersectionProjectFileData.get(0));
            intersectionInputDataWindow.startField_X.setText(intersectionProjectFileData.get(1));
            intersectionInputDataWindow.startField_Y.setText(intersectionProjectFileData.get(2));
            intersectionInputDataWindow.endPointIdField.setText(intersectionProjectFileData.get(3));
            intersectionInputDataWindow.endField_X.setText(intersectionProjectFileData.get(4));
            intersectionInputDataWindow.endField_Y.setText(intersectionProjectFileData.get(5));
            intersectionInputDataWindow.newPointIdField.setText(intersectionProjectFileData.get(6));
            intersectionInputDataWindow.standingAIdField.setText(intersectionProjectFileData.get(7));
            intersectionInputDataWindow.standingAPointField_X.setText(intersectionProjectFileData.get(8));
            intersectionInputDataWindow.standingAPointField_Y.setText(intersectionProjectFileData.get(9));
            intersectionInputDataWindow.standingAPointField_Z.setText(intersectionProjectFileData.get(10));
            intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(intersectionProjectFileData.get(11));
            intersectionInputDataWindow.standingAPointAzimuthMinField.setText(intersectionProjectFileData.get(12));
            intersectionInputDataWindow.standingAPointAzimuthSecField.setText(intersectionProjectFileData.get(13));
            intersectionInputDataWindow.standingAPointElevationAngleField.setText(intersectionProjectFileData.get(14));
            intersectionInputDataWindow.standingAPointElevationMinField.setText(intersectionProjectFileData.get(15));
            intersectionInputDataWindow.standingAPointElevationSecField.setText(intersectionProjectFileData.get(16));
            intersectionInputDataWindow.standingBIdField.setText(intersectionProjectFileData.get(17));
            intersectionInputDataWindow.standingBPointField_X.setText(intersectionProjectFileData.get(18));
            intersectionInputDataWindow.standingBPointField_Y.setText(intersectionProjectFileData.get(19));
            intersectionInputDataWindow.standingBPointField_Z.setText(intersectionProjectFileData.get(20));
            intersectionInputDataWindow.standingBPointAzimuthAngleField.setText(intersectionProjectFileData.get(21));
            intersectionInputDataWindow.standingBPointAzimuthMinField.setText(intersectionProjectFileData.get(22));
            intersectionInputDataWindow.standingBPointAzimuthSecField.setText(intersectionProjectFileData.get(23));
            intersectionInputDataWindow.standingBPointElevationAngleField.setText(intersectionProjectFileData.get(24));
            intersectionInputDataWindow.standingBPointElevationMinField.setText(intersectionProjectFileData.get(25));
            intersectionInputDataWindow.standingBPointElevationSecField.setText(intersectionProjectFileData.get(26));
            intersection.setTheoreticalPoint(new Point("TheoreticalPoint", Double.parseDouble(intersectionProjectFileData.get(27)), 
            		Double.parseDouble(intersectionProjectFileData.get(28))));
        }
    }

}
