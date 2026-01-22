package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import java.util.ArrayList;
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
	private Point centerPillarPoint;
	private Point nextPillarPoint;
	private Double inputAngleValue;
	private Double complAngleValue;
	private List<String> pillarIdList;
	public WeightBaseDisplayer weightBaseDisplayer;
	public PlateBaseDisplayer plateBaseDisplayer;
	public SteakoutControllWindow steakoutControlWindow;
	public PillarCoordsForWeightBase weightBaseCoordsCalculator;
	public PillarCoordsForPlateBase plateBaseCoordsCalculator;
	public SteakoutControl steakoutControl;
	WeightBaseController weightBaseController;
	PlateBaseController plateBaseController;
	SteakoutController steakoutController;
	public MeasuredPillarDataController measuredPillarDataController;
	public CalculateDistanceBetweenPillarLegsWindow calculateDistanceBetweenPillarLegsWindow;
	public WeightBaseFXDisplayer weightBaseFXDisplayer;
	public PlateBaseFXDisplayer plateBaseFXDisplayer;
	
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
		controlDirectionPoint = null;
		if( controlDirectionPointInputWindow != null ) {
        	controlDirectionPointInputWindow.inputFrameForDirectionControl.setVisible(false);
        	controlDirectionPointInputWindow = null;
        }
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
		
		controlDirectionPointInputWindow.inputFrameForDirectionControl.setVisible(true);
		
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
			else if( projectFileData.size() == 18 ) {
			plateBaseInputWindow.sideComboBox.setSelectedIndex(Integer.parseInt(projectFileData.get(14)));
			getControlDirectionPointInputWindow();
			controlDirectionPointInputWindow.setBaseType(BaseType.PLATE_BASE);
			controlDirectionPointInputWindow.directionControlPointIdField.setText(projectFileData.get(15));
			controlDirectionPointInputWindow.x_directionControlPointField.setText(projectFileData.get(16));
			controlDirectionPointInputWindow.y_directionControlPointField.setText(projectFileData.get(17));
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
			else if( projectFileData.size() == 19 ) {
			weightBaseInputWindow.sideComboBox.setSelectedIndex(Integer.parseInt(projectFileData.get(15)));
			getControlDirectionPointInputWindow();
			controlDirectionPointInputWindow.setBaseType(BaseType.WEIGHT_BASE);
			controlDirectionPointInputWindow.directionControlPointIdField.setText(projectFileData.get(16));
			controlDirectionPointInputWindow.x_directionControlPointField.setText(projectFileData.get(17));
			controlDirectionPointInputWindow.y_directionControlPointField.setText(projectFileData.get(18));
			}
		}
	}
	
public boolean isValidControlDirectionData() {
	
	if( controlDirectionPointInputWindow == null ) {
		return true;
		}
	else if( controlDirectionPointInputWindow.directionControlPointIdField.getText().isEmpty() && 
			controlDirectionPointInputWindow.x_directionControlPointField.getText().isEmpty() &&
			controlDirectionPointInputWindow.y_directionControlPointField.getText().isEmpty() ) {
		return true;
	}
	
	pillarIdList = new ArrayList<>();
	String prePillarId = controlDirectionPointInputWindow.directionControlPointIdField.getText();
	if( !InputDataValidator.isValidID(prePillarId) ) {
		getInfoMessage("Hibás bemeneti adatok", "Az ellenőrző oh azonosítójának megadása szükséges.");
		return false;
	}
	pillarIdList.add(prePillarId);
	String centerPillarId;
	String nextPillarId;
if( controlDirectionPointInputWindow.baseType == BaseType.PLATE_BASE ) {
		centerPillarId = plateBaseInputWindow.centerIdField.getText();
		nextPillarId = plateBaseInputWindow.directionIdField.getText();
	if(	!InputDataValidator.isValidID(centerPillarId) ){
		getInfoMessage("Hibás bemeneti adatok", "A számítandó oh azonosítójának megadása szükséges.");
		return false;
	}
	else if( !InputDataValidator.isValidID(nextPillarId) ){
		getInfoMessage("Hibás bemeneti adatok", "A tájékozó oh azonosítójának megadása szükséges.");
		return false;
	}
	pillarIdList.add(centerPillarId);
	pillarIdList.add(nextPillarId);
}
else if( controlDirectionPointInputWindow.baseType == BaseType.WEIGHT_BASE ) {
		centerPillarId = weightBaseInputWindow.centerIdField.getText();
		nextPillarId = weightBaseInputWindow.directionIdField.getText();
	if(	!InputDataValidator.isValidID(centerPillarId) ) {
		getInfoMessage("Hibás bemeneti adatok", "A számítandó oh azonosítójának megadása szükséges.");
		return false;
	}
	else if( !InputDataValidator.isValidID(nextPillarId) ) {
		getInfoMessage("Hibás bemeneti adatok", "A tájékozó oh azonosítójának megadása szükséges.");
		return false;
	};
	pillarIdList.add(centerPillarId);
	pillarIdList.add(nextPillarId);
}

	List<Double> pillarData = validateControlDirectionPointData();
	if( pillarData == null ) {
		getInfoMessage("Hibás bemeneti adatok", "Az oszlop helyének x, y koordinátája csak szám lehet.");
		return false;
	}
	if( controlDirectionPointInputWindow.noRadioButton.isSelected() ) {
		complAngleValue = validateComplAngleMinSecValue();
		if( complAngleValue == null ) {
			getInfoMessage("Hibás bemeneti adatok", "A kiegészítő szög fok, perc, másodperc értéke csak egész szám lehet.");
			return false;
		}
	}
	
	if( controlDirectionPointInputWindow.baseType == BaseType.PLATE_BASE ) {
		
			centerPillarPoint = new Point(plateBaseController.centerID, plateBaseController.centerX, plateBaseController.centerY);
			nextPillarPoint = new Point(plateBaseController.directionID, plateBaseController.directionX, plateBaseController.directionY);
			inputAngleValue = Math.toRadians(plateBaseController.rotationAngle + 
					plateBaseController.rotationMin / 60.0 + plateBaseController.rotationSec / 3600.0);
		
	}
	else if( controlDirectionPointInputWindow.baseType == BaseType.WEIGHT_BASE) {
		
			centerPillarPoint = new Point(weightBaseController.centerID, weightBaseController.centerX, weightBaseController.centerY);
			nextPillarPoint = new Point(weightBaseController.directionID, weightBaseController.directionX, weightBaseController.directionY);
			inputAngleValue = Math.toRadians(weightBaseController.rotationAngle + 
					weightBaseController.rotationMin / 60.0 + weightBaseController.rotationSec / 3600.0);
		
	}
	this.controlDirectionPoint = new Point(prePillarId, pillarData.get(0), pillarData.get(1));
	controlDirectionPointInputWindow.inputFrameForDirectionControl.setVisible(false);
	
	return true;
}

	public String getInfoForControlledAngle() {
	double centerToControlPointAzimuth = new AzimuthAndDistance(centerPillarPoint, controlDirectionPoint).calcAzimuth();
	double centerToNextPointAzimuth = new AzimuthAndDistance(centerPillarPoint, nextPillarPoint).calcAzimuth();
	double mainLineAngle = centerToNextPointAzimuth - centerToControlPointAzimuth;
	if(	0 > mainLineAngle ) {
		mainLineAngle += 2 * Math.PI;
	}
	
	int angleSideIndex = -1;
	
	if( controlDirectionPointInputWindow.baseType == BaseType.PLATE_BASE ) {
		angleSideIndex = plateBaseInputWindow.sideComboBox.getSelectedIndex();
	}
	else if( controlDirectionPointInputWindow.baseType == BaseType.WEIGHT_BASE ) {
		angleSideIndex = weightBaseInputWindow.sideComboBox.getSelectedIndex();
	}
	
	String info = controlDirectionPointInputWindow.yesRadioButton.isSelected() ? 
												"Az oszlop karja szögfelezőben.\n" : 
												"Az oszlop karja NINCS szögfelezőben.\n";
	
	if( inputAngleValue == Math.PI ) {
		
		if( mainLineAngle > 0 && mainLineAngle < Math.PI && controlDirectionPointInputWindow.yesRadioButton.isSelected() ) {
			info += Math.PI / 90  > Math.abs(mainLineAngle - inputAngleValue) ?
					"<html><span style='color: green;'>" + "180°-os törésszög, ΔΥ= " +
					  convertAngleMinSecFormat(mainLineAngle - inputAngleValue) + "</span></html>" :
					"<html><span style='color: red;'>" + "180°-os törésszög, ΔΥ= " +
					  convertAngleMinSecFormat(mainLineAngle - inputAngleValue) + "</span></html>";
		}
		else if( mainLineAngle > Math.PI && controlDirectionPointInputWindow.yesRadioButton.isSelected()) {
			info += Math.PI / 90 > Math.abs(2 * Math.PI - mainLineAngle - inputAngleValue) ?
					"<html><span style='color: green;'>" + "180°-os törésszög, ΔΥ= " +
					convertAngleMinSecFormat(2 * Math.PI - mainLineAngle - inputAngleValue) + "</span></html>" :
					"<html><span style='color: red;'>" + "180°-os törésszög, ΔΥ= " +
					convertAngleMinSecFormat(2 * Math.PI - mainLineAngle - inputAngleValue) + "</span></html>";
		}
}
	else {
	
	if( mainLineAngle > 0 && mainLineAngle < Math.PI && controlDirectionPointInputWindow.yesRadioButton.isSelected() ) {
		info += Math.PI / 90  > Math.abs(mainLineAngle - inputAngleValue) && angleSideIndex == 1 ?
				"<html><span style='color: green;'>" + "BAL oldali törésszög, ΔΥ= " +
				  convertAngleMinSecFormat(mainLineAngle - inputAngleValue) + "</span></html>" :
				"<html><span style='color: red;'>" + "BAL oldali törésszög, ΔΥ= " +
				  convertAngleMinSecFormat(mainLineAngle - inputAngleValue) + "</span></html>";
	}
	else if( mainLineAngle > Math.PI && controlDirectionPointInputWindow.yesRadioButton.isSelected()) {
		info += Math.PI / 90 > Math.abs(2 * Math.PI - mainLineAngle - inputAngleValue) && angleSideIndex == 0 ?
				"<html><span style='color: green;'>" + "JOBB oldali törésszög, ΔΥ= " +
				convertAngleMinSecFormat(2 * Math.PI - mainLineAngle - inputAngleValue) + "</span></html>" :
				"<html><span style='color: red;'>" + "JOBB oldali törésszög, ΔΥ= " +
				convertAngleMinSecFormat(2 * Math.PI - mainLineAngle - inputAngleValue) + "</span></html>";
	}
	else if( mainLineAngle > 0 && mainLineAngle < Math.PI && controlDirectionPointInputWindow.noRadioButton.isSelected() ) {
		info += Math.PI / 90 > Math.abs(mainLineAngle - 0.5 * inputAngleValue - complAngleValue) && angleSideIndex == 1 ?
				"<html><span style='color: green;'>" + "BAL oldali törésszög, ΔΥ= " +
				convertAngleMinSecFormat(mainLineAngle - 0.5 * inputAngleValue - complAngleValue) + "</span></html>" :
				"<html><span style='color: red;'>" + "BAL oldali törésszög, ΔΥ= " +
				convertAngleMinSecFormat(mainLineAngle - 0.5 * inputAngleValue - complAngleValue) + "</span></html>";
	}
	else if( mainLineAngle > Math.PI && controlDirectionPointInputWindow.noRadioButton.isSelected()) {
		info += Math.PI / 90 > Math.abs(2 * Math.PI - mainLineAngle + 0.5 * inputAngleValue + complAngleValue) && angleSideIndex == 0 ?
				"<html><span style='color: green;'>" + "JOBB oldali törésszög, ΔΥ= " +
				convertAngleMinSecFormat(2 * Math.PI - mainLineAngle + 0.5 * inputAngleValue + complAngleValue) + "</span></html>" :
				"<html><span style='color: red;'>" + "JOBB oldali törésszög, ΔΥ= " +
				convertAngleMinSecFormat(2 * Math.PI - mainLineAngle + 0.5 * inputAngleValue + complAngleValue) + "</span></html>";		
}
	}
	
	return info;
}
	
	public String getInfoByControlPoint(boolean isWeightBase) {
		
		int sideOfAngle;
		double inputAngleValue;
		String mainLineAngle;
		
		if( isWeightBase ) {
		sideOfAngle = weightBaseInputWindow.sideComboBox.getSelectedIndex();
		inputAngleValue = Math.toRadians(Double.parseDouble(weightBaseInputWindow.rotateAngularField.getText()) +
								Double.parseDouble(weightBaseInputWindow.rotateAngularMinField.getText()) / 60.0 +
								Double.parseDouble(weightBaseInputWindow.rotateAngularSecField.getText()) / 3600.0);
		mainLineAngle = convertAngleMinSecFormat(inputAngleValue);
		}
		else {
		sideOfAngle = plateBaseInputWindow.sideComboBox.getSelectedIndex();
		inputAngleValue = Math.toRadians(Double.parseDouble(plateBaseInputWindow.rotateAngularField.getText()) +
				Double.parseDouble(plateBaseInputWindow.rotateAngularMinField.getText()) / 60.0 +
				Double.parseDouble(plateBaseInputWindow.rotateAngularSecField.getText()) / 3600.0);
		mainLineAngle = convertAngleMinSecFormat(inputAngleValue);
		}
		
		if( controlDirectionPoint == null ) {
		if( inputAngleValue == Math.PI ) {
			return "Az oszlop karja szögfelezőben.\n" +
					"A nyomvonal törésszöge: " + convertAngleMinSecFormat(inputAngleValue);
		}
		return  "Az oszlop karja szögfelezőben.\n" +
				"A nyomvonal " + (sideOfAngle == 0 ? "JOBB" : "BAL") + 
				" oldali törésszöge: " + mainLineAngle;
		}
		int angleSideIndex = -1;
		
		if( controlDirectionPointInputWindow.baseType == BaseType.PLATE_BASE ) {
			angleSideIndex = plateBaseInputWindow.sideComboBox.getSelectedIndex();
		}
		else if( controlDirectionPointInputWindow.baseType == BaseType.WEIGHT_BASE ) {
			angleSideIndex = weightBaseInputWindow.sideComboBox.getSelectedIndex();
		}
		
		if( controlDirectionPointInputWindow.yesRadioButton.isSelected() ) {
			if( inputAngleValue == Math.PI ) {
				return "Az oszlop karja szögfelezőben.\n" +
						"A nyomvonal törésszöge: "+ convertAngleMinSecFormat(inputAngleValue);
			}
			return "Az oszlop karja szögfelezőben.\n" +
					"A nyomvonal " +  (angleSideIndex == 0 ? "JOBB" : "BAL") + 
					" oldali törésszöge: " +  convertAngleMinSecFormat(this.inputAngleValue);
		}
		if( inputAngleValue == Math.PI ) {
			return "Az oszlop karja szögfelezőben.\n" +
					"A nyomvonal törésszöge: " + convertAngleMinSecFormat(inputAngleValue);
		}
		return 	"Az oszlop karja NINCS szögfelezőben.\n" +
				"A nyomvonal " +  (angleSideIndex == 0 ? "JOBB" : "BAL") + 
				" oldali törésszöge: " +  convertAngleMinSecFormat(0.5 * this.inputAngleValue + complAngleValue);
	}
	
	public String getDistanceBetweenCenterAndControlPoint() {
		if( controlDirectionPoint == null ) {
			return "";
		}
		AzimuthAndDistance controlPointData = new AzimuthAndDistance(centerPillarPoint, controlDirectionPoint);
		return  pillarIdList.get(0) + ". és " + pillarIdList.get(1) + ". oszlopok távolsága: " + 
				String.format("%8.3f" , controlPointData.calcDistance()).replace(",", ".") + "m\n";
	}

	public String getTitleForControlledAngle() {
		return pillarIdList.get(0) + ". oh → " + pillarIdList.get(1) + ". oh → " + pillarIdList.get(2) + ". oh";
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
	
	public String convertAngleMinSecFormat(double radianAngle){
		double angleData = Math.toDegrees(radianAngle);
        int angle = (int) angleData;
        int min = (int) ((angleData - angle) * 60);
        int sec = ((int) ((angleData - angle) * 3600 - min * 60));
        return (0 > angleData ? "-" :  "") + Math.abs(angle) + "° "
                + (9 < Math.abs(min) ? Math.abs(min) : "0" + Math.abs(min)) + "' "
                + (9 < Math.abs(sec) ? Math.abs(sec) : "0" + Math.abs(sec)) + "\"";
    }
		
}


