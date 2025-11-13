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
import mvmxpert.david.giczi.pillarcoordscalculator.service.RowData;
import mvmxpert.david.giczi.pillarcoordscalculator.service.TheoreticalPointData;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.PointType;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.ArrayList;
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
    public MeasurmentDataDisplayer measurmentDataDisplayer;
    public FXHomeWindow fxHomeWindow;
    public List<String> pillarBaseProjectFileData;
    public List<String> measurmentData;
    public Intersection intersection;
    public List<MeasPoint> crossedWirePointList;
    private boolean isCreatedInputPillarDataWindow;
    public static boolean ELEVATION_MEAS_ONLY;
    public static boolean IS_RUNNING_PROCESS_OK;
    public static boolean IS_OPENING_PCC_OR_PLR_FILE_PROCESS;
    public static boolean IS_OPENING_INS_FILE_PROCESS;
    
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
        this.crossedWirePointList = new ArrayList<>();
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
        fileProcess.getPillarBaseMeasureFileData();
        if( fileProcess.getPillarBaseMeasData() == null || fileProcess.getPillarBaseMeasData().isEmpty() ){
        	getInfoAlert("Nem beolvasható adat",
                    "Nem található beolvasható mérési eredmény a fájlban.");
            return;
        }
        measuredPillarData.convertMeasuredDataToMeasPoints(fileProcess.getPillarBaseMeasData());
        if( measuredPillarData.getMeasPillarPoints() == null || measuredPillarData.getMeasPillarPoints().isEmpty()) {
            getInfoAlert("Nem beolvasható adat",
                    "Nem található beolvasható mérési eredmény a fájlban.");
        }
        this.measuredPointListDisplayer =
                new MeasPointListDisplayer(this, false);
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
        fileProcess.getPillarBaseMeasureFileData();
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
    
    public String getPillarBaseDirectionDifference() {
    	return measuredPillarData.calcPillarBaseDirectionDifference();
    }
    
    private boolean validatePillarBaseInputData() {
    	
    	 if( !InputDataValidator.isValidProjectName(inputPillarDataWindow.projectNameField.getText() ) ){
             getInfoAlert("Hibás projektnév megadása", "A projekt neve legalább 3 betű karakter lehet.");
             return false;
         }
         PLRFileProcess.PROJECT_FILE_NAME = inputPillarDataWindow.projectNameField.getText().trim();
         if( PLRFileProcess.FOLDER_PATH == null ){
             getInfoAlert("Hiányzó mentési mappa","Mentési mappa választása szükséges");
             return false;
         }
         PLRFileProcess.FOLDER_PATH = inputPillarDataWindow.projectPathField.getText();
          int centerPillarID = 0;
         try {
              centerPillarID =
                     InputDataValidator
                             .isValidInputPositiveIntegerValue(inputPillarDataWindow.centerPillarIDField.getText());
         }
         catch (NumberFormatException e){
        	inputPillarDataWindow.centerPillarIDField.setText("0");
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
              return false;
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
              return false;
          }

          measuredPillarData.setPillarCenterPoint(new MeasPoint(inputPillarDataWindow.centerPillarIDField.getText(),
                  centerPillarX, centerPillarY, 0.0, PointType.CENTER));

          int angle;
          try{
              angle = InputDataValidator.isValidAngleValue(inputPillarDataWindow.rotationAngleField.getText());
          }catch (NumberFormatException e){
              getInfoAlert("Nem megfelelő e forgatás szög értéke",
                      "A forgatás szög értéke csak 360-nál kisebb egész szám lehet.");
              return false;
          }
          measuredPillarData.setAngleRotation(angle);
          int min;
          try{
              min = InputDataValidator.isValidMinSecValue(inputPillarDataWindow.rotationMinField.getText());
          }catch (NumberFormatException e){
              getInfoAlert("Nem megfelelő a forgatás szögperc értéke",
                      "A forgatás szögperc értéke csak 59-nél kisebb egész szám lehet.");
              return false;
          }
          measuredPillarData.setMinRotation(min);
          int sec;
          try{
              sec = InputDataValidator.isValidMinSecValue(inputPillarDataWindow.rotationSecField.getText());
          }catch (NumberFormatException e){
              getInfoAlert("Nem megfelelő a forgatás szögmásodperc értéke",
                      "A forgatás szögmásodperc értéke csak 59-nél kisebb egész szám lehet.");
              return false;
          }
          measuredPillarData.setSecRotation(sec);
          int directionPillarID = 1;
          try {
              directionPillarID =
                      InputDataValidator
                              .isValidInputPositiveIntegerValue(inputPillarDataWindow.directionPillarIDField.getText());
              if( directionPillarID == centerPillarID ){
                  getInfoAlert("Nem megfelelő az előző/következő oszlop száma",
                          "Az előző/következő oszlop száma nem lehet egyenlő az oszlop számával.");
                  return false;
              }
          }
          catch (NumberFormatException e){
        	  inputPillarDataWindow.directionPillarIDField.setText("1");
           }

          try {
                      InputDataValidator
                              .isValidInputPositiveDoubleValue
                                      (inputPillarDataWindow.directionPillarField_X.getText().replace(",", "."));
          }
          catch (NumberFormatException e){
              getInfoAlert("Nem megfelelő az előző/következő oszlop X koordinátája",
                      "Az oszlop X koordinátája csak nem negatív szám lehet.");
              return false;
          }

          try {
                      InputDataValidator
                              .isValidInputPositiveDoubleValue
                                      (inputPillarDataWindow.directionPillarField_Y.getText().replace(",", "."));
          }
          catch (NumberFormatException e){
              getInfoAlert("Nem megfelelő az előző/következő oszlop Y koordinátája",
                      "Az oszlop Y koordinátája csak nem negatív szám lehet.");
              return false;
          }
          
          if( measuredPillarData.getPillarBasePoints() == null || measuredPillarData.getPillarBasePoints().isEmpty() ) {
        	  getInfoAlert("Hiányzó oszlop alap pontok",
                      "A projekt nem hozható létre, oszlop alap pontok megadása szükséges.");
        	  return false;
          }
    	
          return true;
    }
    
    private boolean canBeSetDataByPCC() {
    
    if( fileProcess.pccData == null || fileProcess.pccData.isEmpty() ) {
    		return false;
    	}
    if( measuredPillarData.isAscPillarOrder(fileProcess.pccData.get(1), fileProcess.pccData.get(4))) {
		inputPillarDataWindow.projectDataText.setText("A következő oszlop adatai");
	}
	else {
		inputPillarDataWindow.projectDataText.setText("Az előző oszlop adatai");
	}
    inputPillarDataWindow.centerPillarIDField.setText(fileProcess.pccData.get(1));	
    inputPillarDataWindow.centerPillarField_X.setText(fileProcess.pccData.get(2));
    inputPillarDataWindow.centerPillarField_Y.setText(fileProcess.pccData.get(3));
    inputPillarDataWindow.directionPillarIDField.setText(fileProcess.pccData.get(4));
    inputPillarDataWindow.directionPillarField_X.setText(fileProcess.pccData.get(5));
    inputPillarDataWindow.directionPillarField_Y.setText(fileProcess.pccData.get(6));
    if( "#WeightBase".equals(fileProcess.pccData.get(0)) ) {
    	inputPillarDataWindow.rotationAngleField
    	.setText(fileProcess.pccData.get(12).substring(0, fileProcess.pccData.get(12).indexOf(".")));
    	inputPillarDataWindow.rotationMinField
    	.setText(fileProcess.pccData.get(13).substring(0, fileProcess.pccData.get(13).indexOf(".")));
    	inputPillarDataWindow.rotationSecField.setText(fileProcess.pccData.get(14)
    			.substring(0, fileProcess.pccData.get(14).indexOf(".")));
    	if( fileProcess.pccData.size() == 16 && "0".equals(fileProcess.pccData.get(15))) {
    		measuredPillarData.setRightRotationAngle(true);
    		inputPillarDataWindow.rotationText.setText("A nyomvonal által bezárt jobb oldali szög");
    	}
    	else {
    		measuredPillarData.setRightRotationAngle(false);
    		inputPillarDataWindow.rotationText.setText("A nyomvonal által bezárt bal oldali szög");
    	}
    	
    }
    else if( "#PlateBase".equals(fileProcess.pccData.get(0)) ) {
    	inputPillarDataWindow.rotationAngleField
    	.setText(fileProcess.pccData.get(11).substring(0, fileProcess.pccData.get(11).indexOf(".")));
    	inputPillarDataWindow.rotationMinField
    	.setText(fileProcess.pccData.get(12).substring(0, fileProcess.pccData.get(12).indexOf(".")));
    	inputPillarDataWindow.rotationSecField
    	.setText(fileProcess.pccData.get(13).substring(0, fileProcess.pccData.get(13).indexOf(".")));
    	if( fileProcess.pccData.size() == 15 && "0".equals(fileProcess.pccData.get(14))) {
    		measuredPillarData.setRightRotationAngle(true);
    		inputPillarDataWindow.rotationText.setText("A nyomvonal által bezárt jobb oldali szög");
    	}
    	else {
    		measuredPillarData.setRightRotationAngle(false);
    		inputPillarDataWindow.rotationText.setText("A nyomvonal által bezárt bal oldali szög");
    	}
    }
    
    return true;	
    }
    
    private void runPillarBaseProcess() {
    	
    	 measuredPillarData.setBaseLineDirectionPoint(
    			 new MeasPoint(inputPillarDataWindow.directionPillarIDField.getText(),
                 Double.parseDouble(inputPillarDataWindow.directionPillarField_X.getText().replace(",",".")), 
                 Double.parseDouble(inputPillarDataWindow.directionPillarField_Y.getText().replace(",",".")), 
                 0.0, PointType.DIRECTION));
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
         fxHomeWindow.homeStage.show();
         this.pillarBaseDisplayer = new PillarBaseDisplayer(this);
    }

    public void onlClickProcessButtonForPillarBaseProject(){
    	fxHomeWindow.homeStage.hide();
        if ( IS_OPENING_PCC_OR_PLR_FILE_PROCESS ) {
        
        	if( validatePillarBaseInputData() ) {
        	runPillarBaseProcess();
        	fileProcess.setPccData(null);
        	}
        	IS_OPENING_PCC_OR_PLR_FILE_PROCESS = false;
        	inputPillarDataWindow.processButton.setText("Tallóz");
        }
        else {
        	fileProcess.getPillarBaseDataByPCCProjectOrTopMeasurment();
        	if ( canBeSetDataByPCC() ) {
        		inputPillarDataWindow.processButton.setText("Számol");
            	IS_OPENING_PCC_OR_PLR_FILE_PROCESS = true;
        	}
        	else if( fileProcess.getPillarBaseMeasData() != null ) {
        	
        		if( fileProcess.getPillarBaseMeasData().isEmpty() ) {
        			 getInfoAlert("Hiányzó mérési adatok",
                             "A fájlban nem található oszlopra való mérés.");
        			return;
        		}
        		measuredPillarData.convertMeasuredDataToMeasPoints(fileProcess.getPillarBaseMeasData());
        		this.measuredPointListDisplayer =
                        new MeasPointListDisplayer(this, true);
        		fileProcess.setPillarBaseMeasData(null);
        	}
        }
    }
    
    public void addPillarTopPointsForPLRProject() {
    	measuredPointListDisplayer.parseDisplayerData();
    	measuredPillarData.calcPillarTopPoints();
    	if( measuredPillarData.getPillarTopPoints().isEmpty() ) {
    		getInfoAlert("Nem található adat",
                    "Az oszlop csúcsára mért pontok választása szükséges.");
    		return;
    	}
    	measuredPointListDisplayer.stage.hide();
    	getInfoAlert("Az adatok beolvasva",
                "Az oszlop csúcsára mért pontok hozzáadva a projekthez.");
    	inputPillarDataWindow.processButton.setText("Számol");
    	IS_OPENING_PCC_OR_PLR_FILE_PROCESS = true;
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
    	if( intersectionInputDataWindow != null ) {
    		intersectionInputDataWindow.stage.hide();
    	}
        intersectionInputDataWindow = new IntersectionInputDataWindow(this);
    }
    
    public void initIntersectionInputDataWindow() {
    	
    	intersectionInputDataWindow.startField_X.setText("");
    	intersectionInputDataWindow.startField_Y.setText("");
    	intersectionInputDataWindow.endField_X.setText("");
    	intersectionInputDataWindow.endField_Y.setText("");
    	intersectionInputDataWindow.standingAPointField_X.setText("");
    	intersectionInputDataWindow.standingAPointField_Y.setText("");
    	intersectionInputDataWindow.standingAPointField_Z.setText("");
    	intersectionInputDataWindow.standingAPointAzimuthAngleField.setText("");
    	intersectionInputDataWindow.standingAPointAzimuthMinField.setText("");
    	intersectionInputDataWindow.standingAPointAzimuthSecField.setText("");
    	intersectionInputDataWindow.standingAPointElevationAngleField.setText("");
    	intersectionInputDataWindow.standingAPointElevationMinField.setText("");
    	intersectionInputDataWindow.standingAPointElevationSecField.setText("");
    	intersectionInputDataWindow.standingBPointField_X.setText("");
    	intersectionInputDataWindow.standingBPointField_Y.setText("");
    	intersectionInputDataWindow.standingBPointField_Z.setText("");
    	intersectionInputDataWindow.standingBPointAzimuthAngleField.setText("");
    	intersectionInputDataWindow.standingBPointAzimuthMinField.setText("");
    	intersectionInputDataWindow.standingBPointAzimuthSecField.setText("");
    	intersectionInputDataWindow.standingBPointElevationAngleField.setText("");
    	intersectionInputDataWindow.standingBPointElevationMinField.setText("");
    	intersectionInputDataWindow.standingBPointElevationSecField.setText("");
    	intersectionInputDataWindow.calcButton.setText("Adatok beolvasása");
    	MeasuredPillarDataController.IS_RUNNING_PROCESS_OK = false;
    }
    
    
    private void loadMeasureFileDataForElevationMeasureOnly() {
    	
    	if( !intersectionInputDataWindow.standingAIdField.getText().isEmpty() &&
                intersectionInputDataWindow.standingAPointField_X.getText().isEmpty() &&
                intersectionInputDataWindow.standingAPointField_Y.getText().isEmpty() &&
                intersectionInputDataWindow.standingAPointField_Z.getText().isEmpty() &&
                intersectionInputDataWindow.standingBIdField.getText().isEmpty() &&
                intersectionInputDataWindow.standingBPointField_X.getText().isEmpty() &&
                intersectionInputDataWindow.standingBPointField_Y.getText().isEmpty() &&
                intersectionInputDataWindow.standingBPointField_Z.getText().isEmpty() &&
                !intersectionInputDataWindow.newPointIdField.getText().isEmpty())  {
                getMeasureDataForElevationMeasureOnly();
            }
    	
    }
    
    public void onClickButtonForElevationMeasureOnly() {
    	
    	if( !IS_RUNNING_PROCESS_OK ) {
        	
        	if( measurmentDataDisplayer != null) {
        		loadMeasureFileDataForElevationMeasureOnly();
        		if( !isValidInputDataForElevationMeasureOnly() ) {
        			return;
        		}
        		intersectionInputDataWindow.calcButton.setText("Feldolgoz");
        		IS_RUNNING_PROCESS_OK = true;
        	}      	
        	
        }		
        	else {
        			loadMeasureFileDataForElevationMeasureOnly();
        		if( isValidInputDataForElevationMeasureOnly() ) {
        			collectCrossedWireStartAndEndPoints();
            		saveAndDisplayDataForElevationMeasureOnly();
            	}
            	else {
            		return;
            	}
        	}
  
    }
    
    
    private void saveAndDisplayDataForElevationMeasureOnly() {
    	
    	 fileProcess.setFolder();
    	    
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
    
    private boolean isValidInputDataForElevationMeasureOnly() {
    	
    	String startPointId;
   	 if( !InputDataValidator.isValidID(intersectionInputDataWindow.startPointIdField.getText()) ){
            if( !intersectionInputDataWindow.startPointIdField.getText().isEmpty() ){
                getInfoAlert("Nem megfelelő a sodrony kezdőpontjának megnevezése",
                        "Add meg a sodrony kezdőpontjának megnevezését.");
                return false;
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
           return false;
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
            return false;
        }
    }
    String endPointId;
    if( !InputDataValidator.isValidID(intersectionInputDataWindow.endPointIdField.getText()) ){
        if( !intersectionInputDataWindow.endPointIdField.getText().isEmpty() ){
            getInfoAlert("Nem megfelelő a sodrony végpontjának megnevezése",
                    "Add meg a sodrony végpontjának megnevezését.");
            return false;
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
            return false;
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
            return false;
        }
    }
    String newPointId;
    if( !InputDataValidator.isValidID(intersectionInputDataWindow.newPointIdField.getText()) ){
        getInfoAlert("Nem megfelelő az új pont megnevezése",
                "Add meg az új pont megnevezését.");
        return false;
    }
    newPointId = intersectionInputDataWindow.newPointIdField.getText();

    String standingAPointId;
    if( !InputDataValidator.isValidID(intersectionInputDataWindow.standingAIdField.getText()) ){
        getInfoAlert("Nem megfelelő az 1. álláspont megnevezése",
                "Add meg az 1. álláspont megnevezését.");
        return false;
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
        return false;
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
        return false;
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
        return false;
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
        return false;
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
        return false;
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
        return false;
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
        return false;
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
        return false;
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
        return false;
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
    return true;
    }
    
    private void getMeasureDataForElevationMeasureOnly() {
    	
    	measurmentDataDisplayer.getDisplayerData();
    	
    	measurmentDataDisplayer.collectSecondMeasurementValue();
    	
    	for (RowData standingPoint : measurmentDataDisplayer.getStandingPointDataStore()) {
    		
    		if( !intersectionInputDataWindow.standingAIdField.getText().isEmpty() &&
					standingPoint.getStandingPointName().equalsIgnoreCase(intersectionInputDataWindow.standingAIdField.getText().trim())) {
				intersectionInputDataWindow.standingAPointField_X.setText(standingPoint.getStandingPointY());
				intersectionInputDataWindow.standingAPointField_Y.setText(standingPoint.getStandingPointX());
				intersectionInputDataWindow.standingAPointField_Z.setText(standingPoint.getStandingPointZ());
			}
    		
			
    		for (RowData measPoint : standingPoint.getMeasuredPointDataStore()) {
    			
    			if( measPoint.getTheoreticalPointData() != null &&
    					measPoint.getTheoreticalPointData().getTheoreticalPointName()
    					.equalsIgnoreCase(intersectionInputDataWindow.newPointIdField.getText().trim()) &&
    					!measPoint.getTheoreticalPointData().isDeleted()) {
    				intersection = new Intersection();
    				intersection.setTheoreticalPoint(new Point("Theoretical", 
    						Double.parseDouble(measPoint.getTheoreticalPointData().getTheoreticalPointY()), 
    						Double.parseDouble(measPoint.getTheoreticalPointData().getTheoreticalPointX())));
    			}
    			
    			if( !intersectionInputDataWindow.startPointIdField.getText().isEmpty() &&
    					measPoint.getMeasuredPointName().equalsIgnoreCase(intersectionInputDataWindow.startPointIdField.getText().trim()) &&
    					!measPoint.isDeleted()) {
    			
    				intersectionInputDataWindow.startField_X.setText(measPoint.getMeasuredPointY());
    				intersectionInputDataWindow.startField_Y.setText(measPoint.getMeasuredPointX());
    			}
    			
    			if( !intersectionInputDataWindow.endPointIdField.getText().isEmpty() &&
    					measPoint.getMeasuredPointName().equalsIgnoreCase(intersectionInputDataWindow.endPointIdField.getText().trim()) &&
    					!measPoint.isDeleted()) {
    				
    				intersectionInputDataWindow.endField_X.setText(measPoint.getMeasuredPointY());
    				intersectionInputDataWindow.endField_Y.setText(measPoint.getMeasuredPointX());
    			}
    			if( !intersectionInputDataWindow.newPointIdField.getText().isEmpty() &&
    					measPoint.getStandingPointName().equalsIgnoreCase(intersectionInputDataWindow.standingAIdField.getText().trim()) &&
    					measPoint.getMeasuredPointSign().equalsIgnoreCase(intersectionInputDataWindow.newPointIdField.getText().trim()) &&
    					!measPoint.isDeleted()){
    				
    				if( measPoint.getFirstHrMeas() != null ) {
    					
    					String[] azimuthData = measPoint.getMediumHrValue().split("-");
						intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(azimuthData[0]);
						intersectionInputDataWindow.standingAPointAzimuthMinField.setText(azimuthData[1]);
						intersectionInputDataWindow.standingAPointAzimuthSecField.setText(azimuthData[2]);
    				}
    				else {
    					
    					String[] azimuthData = measPoint.getHorizontalAngle().split("-");
						intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(azimuthData[0]);
						intersectionInputDataWindow.standingAPointAzimuthMinField.setText(azimuthData[1]);
						intersectionInputDataWindow.standingAPointAzimuthSecField.setText(azimuthData[2]);
    				}
    				
    				if( measPoint.getFirstVrMeas() != null ) {
    					
    					String[] elevationData = measPoint.getMediumVrValue().split("-");
    					intersectionInputDataWindow.standingAPointElevationAngleField.setText(elevationData[0]);
    					intersectionInputDataWindow.standingAPointElevationMinField.setText(elevationData[1]);
    					intersectionInputDataWindow.standingAPointElevationSecField.setText(elevationData[2]);
    				}
    				else {
    					
    					String[] elevationData = measPoint.getVerticalAngle().split("-");
    					intersectionInputDataWindow.standingAPointElevationAngleField.setText(elevationData[0]);
    					intersectionInputDataWindow.standingAPointElevationMinField.setText(elevationData[1]);
    					intersectionInputDataWindow.standingAPointElevationSecField.setText(elevationData[2]);	
    				}				
    	}
    			
    		}
    	
    	}
    			getBaseLineEndPointsDataFromTheoreticalPoints();
	}
    
    
    private void getIntersectionMeasureData() {
    	
    	measurmentDataDisplayer.getDisplayerData();
    	measurmentDataDisplayer.collectSecondMeasurementValue();
    	
    	for (RowData standingPoint : measurmentDataDisplayer.getStandingPointDataStore()) {
    		
    		if( !intersectionInputDataWindow.standingAIdField.getText().isEmpty() &&
					standingPoint.getStandingPointName().equalsIgnoreCase(intersectionInputDataWindow.standingAIdField.getText().trim())) {
				intersectionInputDataWindow.standingAPointField_X.setText(standingPoint.getStandingPointY());
				intersectionInputDataWindow.standingAPointField_Y.setText(standingPoint.getStandingPointX());
				intersectionInputDataWindow.standingAPointField_Z.setText(standingPoint.getStandingPointZ());
			}
    		if( !intersectionInputDataWindow.standingBIdField.getText().isEmpty() &&
					standingPoint.getStandingPointName().equalsIgnoreCase(intersectionInputDataWindow.standingBIdField.getText().trim())) {
				intersectionInputDataWindow.standingBPointField_X.setText(standingPoint.getStandingPointY());
				intersectionInputDataWindow.standingBPointField_Y.setText(standingPoint.getStandingPointX());
				intersectionInputDataWindow.standingBPointField_Z.setText(standingPoint.getStandingPointZ());
    		}
			
    		for (RowData measPoint : standingPoint.getMeasuredPointDataStore()) {
    					
    			if( measPoint.getTheoreticalPointData() != null && 
    					measPoint.getTheoreticalPointData().getTheoreticalPointName()
    					.equalsIgnoreCase(intersectionInputDataWindow.newPointIdField.getText().trim()) 
    					&& !measPoint.getTheoreticalPointData().isDeleted()) {
    				intersection = new Intersection();
    				intersection.setTheoreticalPoint(new Point("Theoretical", 
    						Double.parseDouble(measPoint.getTheoreticalPointData().getTheoreticalPointY()), 
    						Double.parseDouble(measPoint.getTheoreticalPointData().getTheoreticalPointX())));
    				
    			}
    			
    			if( !intersectionInputDataWindow.startPointIdField.getText().isEmpty() &&
    					measPoint.getMeasuredPointName().equalsIgnoreCase(intersectionInputDataWindow.startPointIdField.getText().trim()) &&
    					!measPoint.isDeleted()) {
    			
    				intersectionInputDataWindow.startField_X.setText(measPoint.getMeasuredPointY());
    				intersectionInputDataWindow.startField_Y.setText(measPoint.getMeasuredPointX());
    			}
    			
    			if( !intersectionInputDataWindow.endPointIdField.getText().isEmpty() &&
    					measPoint.getMeasuredPointName().equalsIgnoreCase(intersectionInputDataWindow.endPointIdField.getText().trim()) &&
    					!measPoint.isDeleted()) {
    				
    				intersectionInputDataWindow.endField_X.setText(measPoint.getMeasuredPointY());
    				intersectionInputDataWindow.endField_Y.setText(measPoint.getMeasuredPointX());
    			}
    			if( !intersectionInputDataWindow.newPointIdField.getText().isEmpty() &&
    					measPoint.getStandingPointName().equalsIgnoreCase(intersectionInputDataWindow.standingAIdField.getText().trim()) &&
    					measPoint.getMeasuredPointSign().equalsIgnoreCase(intersectionInputDataWindow.newPointIdField.getText().trim()) &&
    					!measPoint.isDeleted()){
    				
    				if( measPoint.getFirstHrMeas() != null ) {
    					
    					String[] azimuthData = measPoint.getMediumHrValue().split("-");
						intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(azimuthData[0]);
						intersectionInputDataWindow.standingAPointAzimuthMinField.setText(azimuthData[1]);
						intersectionInputDataWindow.standingAPointAzimuthSecField.setText(azimuthData[2]);
    				}
    				else {
    					
    					String[] azimuthData = measPoint.getHorizontalAngle().split("-");
						intersectionInputDataWindow.standingAPointAzimuthAngleField.setText(azimuthData[0]);
						intersectionInputDataWindow.standingAPointAzimuthMinField.setText(azimuthData[1]);
						intersectionInputDataWindow.standingAPointAzimuthSecField.setText(azimuthData[2]);
    				}
    				
    				if( measPoint.getFirstVrMeas() != null ) {
    					
    					String[] elevationData = measPoint.getMediumVrValue().split("-");
    					intersectionInputDataWindow.standingAPointElevationAngleField.setText(elevationData[0]);
    					intersectionInputDataWindow.standingAPointElevationMinField.setText(elevationData[1]);
    					intersectionInputDataWindow.standingAPointElevationSecField.setText(elevationData[2]);
    				}
    				else {
    					
    					String[] elevationData = measPoint.getVerticalAngle().split("-");
    					intersectionInputDataWindow.standingAPointElevationAngleField.setText(elevationData[0]);
    					intersectionInputDataWindow.standingAPointElevationMinField.setText(elevationData[1]);
    					intersectionInputDataWindow.standingAPointElevationSecField.setText(elevationData[2]);	
    				}				
    	}
    			
    			if( !intersectionInputDataWindow.newPointIdField.getText().isEmpty() &&
    					measPoint.getStandingPointName().equalsIgnoreCase(intersectionInputDataWindow.standingBIdField.getText().trim()) &&
    					measPoint.getMeasuredPointSign().equalsIgnoreCase(intersectionInputDataWindow.newPointIdField.getText().trim()) &&
    					!measPoint.isDeleted()){
    				
    				if( measPoint.getFirstHrMeas() != null ) {
    					
    					String[] azimuthData = measPoint.getMediumHrValue().split("-");
    					intersectionInputDataWindow.standingBPointAzimuthAngleField.setText(azimuthData[0]);
    					intersectionInputDataWindow.standingBPointAzimuthMinField.setText(azimuthData[1]);
    					intersectionInputDataWindow.standingBPointAzimuthSecField.setText(azimuthData[2]);
    				}
    				else {
    					
    					String[] azimuthData = measPoint.getHorizontalAngle().split("-");
    					intersectionInputDataWindow.standingBPointAzimuthAngleField.setText(azimuthData[0]);
    					intersectionInputDataWindow.standingBPointAzimuthMinField.setText(azimuthData[1]);
    					intersectionInputDataWindow.standingBPointAzimuthSecField.setText(azimuthData[2]);
    				}
    				
    				if( measPoint.getFirstVrMeas() != null ) {
    					
    					String[] elevationData = measPoint.getMediumVrValue().split("-");
    					intersectionInputDataWindow.standingBPointElevationAngleField.setText(elevationData[0]);
    					intersectionInputDataWindow.standingBPointElevationMinField.setText(elevationData[1]);
    					intersectionInputDataWindow.standingBPointElevationSecField.setText(elevationData[2]);
    				}
    				else {
    					
    					String[] elevationData = measPoint.getVerticalAngle().split("-");
    					intersectionInputDataWindow.standingBPointElevationAngleField.setText(elevationData[0]);
    					intersectionInputDataWindow.standingBPointElevationMinField.setText(elevationData[1]);
    					intersectionInputDataWindow.standingBPointElevationSecField.setText(elevationData[2]);	
    				}	
    	}		
	}
    		
  }	
    	getBaseLineEndPointsDataFromTheoreticalPoints();
    }
    
    
    private void getBaseLineEndPointsDataFromTheoreticalPoints() {
    	
    	for (TheoreticalPointData theoreticalPointData : measurmentDataDisplayer.getTheoreticalPointDataStore()) {
			
    		if( !intersectionInputDataWindow.startPointIdField.getText().isEmpty() &&
					theoreticalPointData.getTheoreticalPointName().equalsIgnoreCase(intersectionInputDataWindow.startPointIdField.getText().trim())
							&& !theoreticalPointData.isDeleted()) {
			
				intersectionInputDataWindow.startField_X.setText(theoreticalPointData.getTheoreticalPointY());
				intersectionInputDataWindow.startField_Y.setText(theoreticalPointData.getTheoreticalPointX());
			}
			
			if( !intersectionInputDataWindow.endPointIdField.getText().isEmpty() &&
					theoreticalPointData.getTheoreticalPointName().equalsIgnoreCase(intersectionInputDataWindow.endPointIdField.getText().trim())
					&& !theoreticalPointData.isDeleted()) {
				
				intersectionInputDataWindow.endField_X.setText(theoreticalPointData.getTheoreticalPointY());
				intersectionInputDataWindow.endField_Y.setText(theoreticalPointData.getTheoreticalPointX());
			}
    		 		
		}
    	
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
            getIntersectionMeasureData();
        }
    }
    
    public void onClickButtonForIntersectionProcess(){
    	
    	if( !IS_RUNNING_PROCESS_OK ) {
    	
    	if( measurmentDataDisplayer != null) {
    		loadMeasureFileData();
    		if( !isValidIntersectionInputData() ) {
    			return;
    		}
    		intersectionInputDataWindow.calcButton.setText("Feldolgoz");
    		IS_RUNNING_PROCESS_OK = true;
    	}
    	
    	
    }		
    	else {
    			loadMeasureFileData();
    		if( isValidIntersectionInputData() ) {
    			collectCrossedWireStartAndEndPoints();
        		saveAndDisplayIntersectionData();
        	}
        	else {
        		return;
        	}
    	}
    }
    
    
    private boolean isValidIntersectionInputData() {
    	
    	 String startPointId;
         if( !InputDataValidator.isValidID(intersectionInputDataWindow.startPointIdField.getText()) ){
                 if( !intersectionInputDataWindow.startPointIdField.getText().isEmpty() ){
                     getInfoAlert("Nem megfelelő a sodrony kezdőpontjának megnevezése",
                             "Add meg a sodrony kezdőpontjának megnevezését.");
                     return false;
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
                return false;
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
                 return false;
             }
         }
         String endPointId;
         if( !InputDataValidator.isValidID(intersectionInputDataWindow.endPointIdField.getText()) ){
             if( !intersectionInputDataWindow.endPointIdField.getText().isEmpty() ){
                 getInfoAlert("Nem megfelelő a sodrony végpontjának megnevezése",
                         "Add meg a sodrony végpontjának megnevezését.");
                 return false;
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
                 return false;
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
                 return false;
             }
         }
         String newPointId;
         if( !InputDataValidator.isValidID(intersectionInputDataWindow.newPointIdField.getText()) ){
             getInfoAlert("Nem megfelelő az új pont megnevezése",
                     "Add meg az új pont megnevezését.");
             return false;
         }
         newPointId = intersectionInputDataWindow.newPointIdField.getText();

         String standingAPointId;
         if( !InputDataValidator.isValidID(intersectionInputDataWindow.standingAIdField.getText()) ){
             getInfoAlert("Nem megfelelő az 1. álláspont megnevezése",
                     "Add meg az 1. álláspont megnevezését.");
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
         }

         String standingBPointId;
         if( !InputDataValidator.isValidID(intersectionInputDataWindow.standingBIdField.getText()) ){
             getInfoAlert("Nem megfelelő az 2. álláspont megnevezése",
                     "Add meg az 2. álláspont megnevezését.");
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
             return false;
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
         return true;
    }
    
    private void saveAndDisplayIntersectionData() {
    
    fileProcess.setFolder();
    
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
    	fileProcess.openMeasurmentFileData();
        if( PLRFileProcess.MEAS_FILE_NAME == null ) {
     	   return;
        }
        if( measurmentDataDisplayer != null ) {
        	measurmentDataDisplayer.stage.hide();
        }
        measurmentDataDisplayer = new MeasurmentDataDisplayer(this);
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
        		 intersectionProjectFileData.size() == 19 ||
        		 intersectionProjectFileData.size() == 22 || 
        		 intersectionProjectFileData.size() == 26 || 
        		 intersectionProjectFileData.size() == 28) {
        	MeasuredPillarDataController.ELEVATION_MEAS_ONLY = true;
        }
        else if( intersectionProjectFileData.size() == 21 ||
       		 	 intersectionProjectFileData.size() == 23 ||
       		 	 intersectionProjectFileData.size() == 27 ||
       		 	 intersectionProjectFileData.size() == 29 ||
       		 	 intersectionProjectFileData.size() == 30 ||
      		 	 intersectionProjectFileData.size() == 32 ||
      		 	 intersectionProjectFileData.size() == 36 ||
      		 	 intersectionProjectFileData.size() == 38){
        	MeasuredPillarDataController.ELEVATION_MEAS_ONLY = false;
       }
        IS_OPENING_INS_FILE_PROCESS = true;
        openIntersectionInputDataWindow();
        intersectionInputDataWindow.stage.setTitle(PLRFileProcess.PROJECT_FILE_NAME + ".ins");
        intersection = new Intersection();
        if( intersectionProjectFileData.size() == 13 || intersectionProjectFileData.size() == 22 ) {
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
            if( intersectionProjectFileData.size() == 13 ) {
            	return;
            }
            setCrossedWirePoints(intersectionProjectFileData, 14);
        }
        else if( intersectionProjectFileData.size() == 17 || intersectionProjectFileData.size() == 26 ) {
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
             if( intersectionProjectFileData.size() == 17 ) {
             	return;
             }
             setCrossedWirePoints(intersectionProjectFileData, 18);
        }
        else if( intersectionProjectFileData.size() == 19 || intersectionProjectFileData.size() == 28 ) {
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
            if( intersectionProjectFileData.size() == 19 ) {
            	return;
            }
            setCrossedWirePoints(intersectionProjectFileData, 20);
       }
        else if( intersectionProjectFileData.size() == 21 || intersectionProjectFileData.size() == 30 ) {
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
             if( intersectionProjectFileData.size() == 21 ) {
             	return;
             }
             setCrossedWirePoints(intersectionProjectFileData, 22);
        }
        else if( intersectionProjectFileData.size() == 23 || intersectionProjectFileData.size() == 32 ){
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
            if( intersectionProjectFileData.size() == 23 ) {
             	return;
             }
             setCrossedWirePoints(intersectionProjectFileData, 24);
        }
        else if( intersectionProjectFileData.size() == 27 || intersectionProjectFileData.size() == 36 ){
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
            if( intersectionProjectFileData.size() == 27 ) {
             	return;
             }
             setCrossedWirePoints(intersectionProjectFileData, 28);
        }
        else if( intersectionProjectFileData.size() == 29 || intersectionProjectFileData.size() == 38 ){
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
            if( intersectionProjectFileData.size() == 29 ) {
             	return;
             }
             setCrossedWirePoints(intersectionProjectFileData, 30);
        }
    }
    
    private void setCrossedWirePoints(List<String> intersectionProjectFileData, int index) {
		crossedWirePointList.add(new MeasPoint(intersectionProjectFileData.get(index), 
								Double.parseDouble(intersectionProjectFileData.get(index + 1)),
								Double.parseDouble(intersectionProjectFileData.get(index + 2)),
								Double.parseDouble(intersectionProjectFileData.get(index + 3)), null));
		crossedWirePointList.add(new MeasPoint(intersectionProjectFileData.get(index + 4), 
				Double.parseDouble(intersectionProjectFileData.get(index + 5)),
				Double.parseDouble(intersectionProjectFileData.get(index + 6)),
				Double.parseDouble(intersectionProjectFileData.get(index + 7)), null));
    }
    
    private void collectCrossedWireStartAndEndPoints() {
   
    	String crossedPointId = intersectionInputDataWindow.newPointIdField.getText().toUpperCase();
    	
    	if( IS_OPENING_INS_FILE_PROCESS ) {
    		return;
    	}
    	else if( !crossedPointId.contains("20KV") && !crossedPointId.contains("130KV") && 
    			!crossedPointId.contains("400KV") && !crossedPointId.contains("VASUT")) {
    		return;
    	}
    	for (TheoreticalPointData theoreticalPointData : measurmentDataDisplayer.theoreticalPointDataStore) {
    		
    		String[] data = theoreticalPointData.toString().split(";");
    		
			if( data[0].startsWith(crossedPointId.substring(crossedPointId.indexOf('-') + 1, crossedPointId.lastIndexOf('-'))) ) {
				
					crossedWirePointList.add(new MeasPoint(data[0], Double.parseDouble(data[1]), 
							Double.parseDouble(data[2]), Double.parseDouble(data[3]), null));
			}
			
		}
        	
    }
    	
}
