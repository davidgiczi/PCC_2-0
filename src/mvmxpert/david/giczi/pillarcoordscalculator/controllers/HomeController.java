package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import java.util.Arrays;
import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.PCCFileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.FXHomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.PlateBaseFXDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.WeightBaseFXDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.service.AzimuthAndDistance;
import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.service.SteakoutControl;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.BaseType;
import mvmxpert.david.giczi.pillarcoordscalculator.view.CalculateDistanceBetweenPillarLegsWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.ControlDirectionPointInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControllWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseInputWindow;

public class HomeController {

	public static String PROJECT_NAME;
	public HomeWindow homeWindow;
	public PCCFileProcess fileProcess;
	public WeightBaseInputWindow weightBaseInputWindow;
	public PlateBaseInputWindow plateBaseInputWindow;
	public ControlDirectionPointInputWindow controlDirectionPointInputWindow;
	public Point controlDirectionPoint;
	WeightBaseDisplayer weightBaseDisplayer;
	PlateBaseDisplayer plateBaseDisplayer;
	SteakoutControllWindow steakoutControlWindow;
	public PillarCoordsForWeightBase weightBaseCoordsCalculator;
	public PillarCoordsForPlateBase plateBaseCoordsCalculator;
	public SteakoutControl steakoutControl;
	WeightBaseController weightBaseController;
	PlateBaseController plateBaseController;
	SteakoutController steakoutController;
	public MeasuredPillarDataController measuredPillarDataController;
	CalculateDistanceBetweenPillarLegsWindow calculateDistanceBetweenPillarLegsWindow;
	WeightBaseFXDisplayer weightBaseFXDisplayer;
	PlateBaseFXDisplayer plateBaseFXDisplayer;
	
	public HomeController() {
		this.fileProcess = new PCCFileProcess(this);
		FXHomeWindow.setHomeController(this);
		FXHomeWindow.main(null);
	}

	public void destroy() {
		weightBaseInputWindow = null;
		plateBaseInputWindow = null;
		weightBaseDisplayer = null;
		plateBaseDisplayer = null;
		steakoutControlWindow = null;
		weightBaseCoordsCalculator = null;
		plateBaseCoordsCalculator = null;
		steakoutControl = null;
		weightBaseController = null;
		plateBaseController = null;
		steakoutController = null;
	}
	
	public void getWeightBaseInputWindow() {
		if( weightBaseInputWindow == null ) {
			weightBaseController = new WeightBaseController(this);
			weightBaseInputWindow = new WeightBaseInputWindow(PROJECT_NAME, weightBaseController);
			}
		else {
			weightBaseInputWindow.sharePillarCenterDataBetweenInputWindows();
			weightBaseInputWindow.inputFrameForWeightBase.setVisible(true);
			}
	}
	
	public void getPlateBaseInputWindow() {
		if( plateBaseInputWindow == null ) {
			plateBaseController = new PlateBaseController(this);
			plateBaseInputWindow = new PlateBaseInputWindow(PROJECT_NAME, plateBaseController);
			}
		else {
			plateBaseInputWindow.sharePillarCenterDataBetweenInputWindows();
			plateBaseInputWindow.inputFrameForPlateBase.setVisible(true);	
			}
	}
	
	public void getSteakoutControlWindow() {
		if( steakoutControlWindow == null ) {
			steakoutController = new SteakoutController(this);
			steakoutControlWindow = new SteakoutControllWindow(PROJECT_NAME, steakoutController);
		}
		else {
			steakoutControlWindow.steakoutControlFrame.setVisible(true);
		}
	}
	
	public void getCalculateDistanceBetweenPillarLegsWindow() {
		if( calculateDistanceBetweenPillarLegsWindow == null ) {
			calculateDistanceBetweenPillarLegsWindow = new CalculateDistanceBetweenPillarLegsWindow(this);
		}
		else {
			calculateDistanceBetweenPillarLegsWindow.inputFrame.setVisible(true);
		}
	}
	
	public void getControlDirectionPointInputWindow() {
		
		if( controlDirectionPointInputWindow == null ) {
			controlDirectionPointInputWindow = new ControlDirectionPointInputWindow(PROJECT_NAME, this);
		}
		else {
			controlDirectionPointInputWindow.inputFrameForDirectionControl.setVisible(true);
		}
		
	}
	
	public void getWeightBaseFXDisplayer() {
	
		WeightBaseFXDisplayer.setHomeController(this);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				weightBaseFXDisplayer = new WeightBaseFXDisplayer();
			}
		});
		
 }
	
	public void getPlateBaseFXDisplayer() {
		
		PlateBaseFXDisplayer.setHomeController(this);
		
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				plateBaseFXDisplayer = new PlateBaseFXDisplayer();
			}
		});
		
 }
	
	private void setVisible() {
		if( weightBaseInputWindow != null ) {
			weightBaseInputWindow.inputFrameForWeightBase.setVisible(false);
		}
		if( plateBaseInputWindow != null ) {
			plateBaseInputWindow.inputFrameForPlateBase.setVisible(false);
		}
		if( weightBaseDisplayer != null ) {
			weightBaseDisplayer.setVisible(false);
		}
		if( plateBaseDisplayer != null ) {
			plateBaseDisplayer.setVisible(false);
		}
		if( steakoutControlWindow != null ) {
			steakoutControlWindow.steakoutControlFrame.setVisible(false);
		}
		FXHomeWindow.setBaseData.setDisable(true);
		FXHomeWindow.controlSteakoutedPoint.setDisable(true);
	}
	
	public String createNewProject() {
		String projectName = JOptionPane.showInputDialog(null, "Add meg a projekt nevét:", "A projekt nevének megadása", JOptionPane.DEFAULT_OPTION);
		if( projectName != null && InputDataValidator.isValidProjectName(projectName) ) {
			fileProcess.setFolder();
			if( PCCFileProcess.FOLDER_PATH != null ) {
			PROJECT_NAME = projectName;
			setVisible();
			destroy();
			FXHomeWindow.setBaseData.setDisable(false);
		}
	}
		else if( projectName != null && !InputDataValidator.isValidProjectName(projectName) ) {
			getInfoMessage("Projekt név megadása", "A projekt neve legalább 3 karakter hosszúságú és betűvel kezdődő lehet.");
		}
		return projectName;
	}
	
	public void openProject() {
		String projectName = fileProcess.setProject();
		if( projectName == null ) {
			return;
		}
		PROJECT_NAME = projectName;
		FXHomeWindow.setBaseData.setDisable(false);
		if( PCCFileProcess.isProjectFileExist() ) {
			setVisible();
			destroy();
			getInfoMessage("\"" + PROJECT_NAME + "\"" + " projekt",
					"Létező " + getBaseType() +" projekt megnyitva.");
			getProjectFileDataFromFile();
		}
	}
	
	public String getBaseType() {
		String baseType = "#WeightBase".equals(fileProcess.getProjectFileData().get(0)) ? "súlyalap" : 
			"#PlateBase".equals(fileProcess.getProjectFileData().get(0)) ? "lemezalap" : "";
		return baseType;
	}
	
	public void getExistedProjectInfoMessage() {
		if( PCCFileProcess.isProjectFileExist() ) {
			getInfoMessage("\"" + PROJECT_NAME + "\"" + " projekt",
					"Létező " + getBaseType() +" projekt.");
		}
	}
	
	public void getInfoMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public int getYesNoMessage(String title, String message) {
		Object[] options = {"Igen", "Nem"};
		return JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_OPTION, 
				JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	}
	
	private void getProjectFileDataFromFile() {
		
		List<String> projectFileData = fileProcess.getProjectFileData();
	
		if( !projectFileData.isEmpty() && "#PlateBase".equals(projectFileData.get(0)) ) {
			plateBaseController = new PlateBaseController(this);
			plateBaseInputWindow = new PlateBaseInputWindow(PROJECT_NAME, plateBaseController);
			plateBaseInputWindow.inputFrameForPlateBase.setTitle(PROJECT_NAME);
			plateBaseInputWindow.centerIdField.setText(projectFileData.get(1));
			plateBaseInputWindow.x_centerField.setText(projectFileData.get(2));
			plateBaseInputWindow.y_centerField.setText(projectFileData.get(3));
			plateBaseInputWindow.directionIdField.setText(projectFileData.get(4));
			plateBaseInputWindow.x_directionField.setText(projectFileData.get(5));
			plateBaseInputWindow.y_directionField.setText(projectFileData.get(6));
			plateBaseInputWindow.horizontalSizeOfHoleField.setText(projectFileData.get(7));
			plateBaseInputWindow.verticalSizeOfHoleField.setText(projectFileData.get(8));
			plateBaseInputWindow.horizontalDistanceFromHoleSideField.setText(projectFileData.get(9));
			plateBaseInputWindow.verticalDistanceFromHoleSideField.setText(projectFileData.get(10));
			plateBaseInputWindow.rotateAngularField
			.setText(projectFileData.get(11).substring(0, projectFileData.get(11).indexOf('.')));
			plateBaseInputWindow.rotateAngularMinField
			.setText(projectFileData.get(12).substring(0, projectFileData.get(12).indexOf('.')));
			plateBaseInputWindow.rotateAngularSecField
			.setText(projectFileData.get(13).substring(0, projectFileData.get(13).indexOf('.')));
			if( projectFileData.size() == 15 ) {
			plateBaseInputWindow.sideComboBox.setSelectedIndex(Integer.parseInt(projectFileData.get(14)));
			}
		}
		else if( !projectFileData.isEmpty() && "#WeightBase".equals(projectFileData.get(0)) ) {
			weightBaseController = new WeightBaseController(this);
			weightBaseInputWindow = new WeightBaseInputWindow(PROJECT_NAME, weightBaseController);
			weightBaseInputWindow.inputFrameForWeightBase.setTitle(PROJECT_NAME);
			weightBaseInputWindow.centerIdField.setText(projectFileData.get(1));
			weightBaseInputWindow.x_centerField.setText(projectFileData.get(2));
			weightBaseInputWindow.y_centerField.setText(projectFileData.get(3));
			weightBaseInputWindow.directionIdField.setText(projectFileData.get(4));
			weightBaseInputWindow.x_directionField.setText(projectFileData.get(5));
			weightBaseInputWindow.y_directionField.setText(projectFileData.get(6));
			weightBaseInputWindow.directionSizeField.setText(projectFileData.get(7));
			weightBaseInputWindow.horizontalSizeForPillarLegField.setText(projectFileData.get(8));
			weightBaseInputWindow.verticalSizeForPillarLegField.setText(projectFileData.get(9));
			weightBaseInputWindow.horizontalSizeForHoleField.setText(projectFileData.get(10));
			weightBaseInputWindow.verticalSizeForHoleField.setText(projectFileData.get(11));
			weightBaseInputWindow.rotateAngularField
			.setText(projectFileData.get(12).substring(0, projectFileData.get(12).indexOf('.')));
			weightBaseInputWindow.rotateAngularMinField
			.setText(projectFileData.get(13).substring(0, projectFileData.get(13).indexOf('.')));
			weightBaseInputWindow.rotateAngularSecField
			.setText(projectFileData.get(14).substring(0, projectFileData.get(14).indexOf('.')));
			if( projectFileData.size() == 16 ) {
			weightBaseInputWindow.sideComboBox.setSelectedIndex(Integer.parseInt(projectFileData.get(15)));
			}
		}
	}
	
	public void handleAddButtonClickForControlDirectionProcess() {	
	List<Integer> pillarIds = validateInputPillarIds();
	if( pillarIds == null ) {
		getInfoMessage("Hibás bemeneti adatok", "Az oszlop azonosítója csak szám, vagy üres karakterrel elválasztott betű-szám lehet.");
		return;
	}
	List<Double> pillarData = validateControlDirectionPointData();
	if( pillarData == null ) {
		getInfoMessage("Hibás bemeneti adatok", "Az oszlop helyének x, y koordinátája csak szám lehet.");
		return;
	}
	Double complAngleValue;
	if( controlDirectionPointInputWindow.noRadioButton.isSelected() ) {
		complAngleValue = validateComplAngleMinSecValue();
		if( complAngleValue == null ) {
			getInfoMessage("Hibás bemeneti adatok", "A kiegészítő szög fok, perc, másodperc értéke csak egész szám lehet.");
			return;
		}
	}
	Point centerPillarPoint = null;
	Point nextPillarPoint = null;
	if( controlDirectionPointInputWindow.baseType == BaseType.PLATE_BASE ) {
		try {
			plateBaseController.isValidInputData();
			centerPillarPoint = new Point(plateBaseController.centerID, plateBaseController.centerX, plateBaseController.centerY);
			nextPillarPoint = new Point(plateBaseController.directionID, plateBaseController.directionX, plateBaseController.directionY);
		}
		catch (NumberFormatException e) {
			getInfoMessage("Hibás bemeneti adatok", "Minden üres adatmező kitöltése és szám értékek megadása szükséges.");
			return;
		}
	}
	else if( controlDirectionPointInputWindow.baseType == BaseType.WEIGHT_BASE) {
		try {
			weightBaseController.isValidInputData();
			centerPillarPoint = new Point(weightBaseController.centerID, weightBaseController.centerX, weightBaseController.centerY);
			nextPillarPoint = new Point(weightBaseController.directionID, weightBaseController.directionX, weightBaseController.directionY);
		}
		catch (NumberFormatException e) {
			getInfoMessage("Hibás bemeneti adatok", "Minden üres adatmező kitöltése és szám értékek megadása szükséges.");
			return;
		}
	}
	this.controlDirectionPoint = new Point(
			controlDirectionPointInputWindow.directionControlPointIdField.getText(), pillarData.get(0), pillarData.get(1));
	double centerToControlPointAzimuth = new AzimuthAndDistance(centerPillarPoint, controlDirectionPoint).calcAzimuth(); 
	double centerToNextPointAzimuth = new AzimuthAndDistance(centerPillarPoint, nextPillarPoint).calcAzimuth(); 
	double mainLineAngle = centerToNextPointAzimuth - centerToControlPointAzimuth;
	controlDirectionPointInputWindow.inputFrameForDirectionControl.setVisible(false);
	getInfoMessage(pillarIds.get(0) + ". oh → " + pillarIds.get(1) + ". oh → " + pillarIds.get(2) + ". oh", 
			"Oszlop karja szögfelezőben.\nΔΥ= " + (convertAngleMinSecFormat(mainLineAngle)));
	
//	if( mainLineAngle > 0 &&  Math.PI > mainLineAngle && pillarIds.get(1) < pillarIds.get(2) ) {
//		
//	}
//	else if( mainLineAngle > 0 && pillarIds.get(1) > pillarIds.get(2) ) {
//		
//	}
//	else if( mainLineAngle < 0 && pillarIds.get(1) < pillarIds.get(2) ) {
//		
//	}
//	else if( mainLineAngle < 0 && pillarIds.get(1) > pillarIds.get(2) ) {
//		
//	}
	
}
	
	private List<Integer> validateInputPillarIds(){
		int controlPillarId;
		int centerPillarId = -1;
		int nextPillarId = -1;
		try {
			controlPillarId = InputDataValidator.
					validateIdForControlDirectionInputData(controlDirectionPointInputWindow.directionControlPointIdField.getText());
			if( controlDirectionPointInputWindow.baseType == BaseType.PLATE_BASE ) {
			centerPillarId = InputDataValidator.
						validateIdForControlDirectionInputData(plateBaseInputWindow.centerIdField.getText());
			nextPillarId = InputDataValidator.
					validateIdForControlDirectionInputData(plateBaseInputWindow.directionIdField.getText());
			}
			else if( controlDirectionPointInputWindow.baseType == BaseType.WEIGHT_BASE ) {
				centerPillarId = InputDataValidator.
						validateIdForControlDirectionInputData(weightBaseInputWindow.centerIdField.getText());
				nextPillarId = InputDataValidator.
					validateIdForControlDirectionInputData(weightBaseInputWindow.directionIdField.getText());
			}
			
		} catch (NumberFormatException e) {
			return null;
		}
		return Arrays.asList(controlPillarId, centerPillarId, nextPillarId);
	}
	
	private List<Double> validateControlDirectionPointData(){
		double x_point;
		double y_point;
		try {
			x_point = InputDataValidator
					.isValidInputDoubleValue(controlDirectionPointInputWindow.x_directionControlPointField.getText().replace(',' , '.'));
			y_point = InputDataValidator
					.isValidInputDoubleValue(controlDirectionPointInputWindow.y_directionControlPointField.getText().replace(',' , '.'));
		}
		catch (NumberFormatException e) {
			return null;
		}
		return Arrays.asList(x_point, y_point);
	}
	
	private Double validateComplAngleMinSecValue() {
		int angle;
		int min;
		int sec;
		try {
			angle = InputDataValidator.isValidAngleValue(controlDirectionPointInputWindow.complAngularField.getText());
			min = InputDataValidator.isValidAngleValue(controlDirectionPointInputWindow.complMinField.getText());
			sec = InputDataValidator.isValidAngleValue(controlDirectionPointInputWindow.complSecField.getText());
		}catch (NumberFormatException e) {
			return null;
		}
		return Math.toRadians(angle + min / 60.0 + sec / 3600.0);
	}
	
	private String convertAngleMinSecFormat(double angleData){
        int angle = (int) angleData;
        int min = (int) ((angleData - angle) * 60);
        double sec = ((int) ((angleData - angle) * 3600 - min * 60));
        return (0 > angleData ? "-" :  "") + Math.abs(angle) + "° "
                + (9 < Math.abs(min) ? Math.abs(min) : "0" + Math.abs(min)) + "' "
                + (9 < Math.abs(sec) ? Math.abs(sec) : "0" + Math.abs(sec)) + "\"";
    }
}


