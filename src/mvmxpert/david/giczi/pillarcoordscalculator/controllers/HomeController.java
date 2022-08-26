package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import java.util.List;

import javax.swing.JOptionPane;

import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.SteakoutControl;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseInputWindow;

public class HomeController {

	public static String PROJECT_NAME;
	HomeWindow homeWindow;
	WeightBaseInputWindow weightBaseInputWindow;
	PlateBaseInputWindow plateBaseInputWindow;
	WeightBaseDisplayer weightBaseDisplayer;
	PlateBaseDisplayer plateBaseDisplayer;
	SteakoutControlWindow steakoutControlWindow;
	PillarCoordsForWeightBase weightBaseCoordsCalculator;
	PillarCoordsForPlateBase plateBaseCoordsCalculator;
	SteakoutControl steakoutControl;
	WeightBaseController weightBaseController;
	PlateBaseController plateBaseController;
	SteakoutController steakoutController;
	
	public HomeController() {
		this.homeWindow = new HomeWindow(this);
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
	
	public void getWeightBaseDisplayer() {
		if( weightBaseInputWindow == null ) {
			weightBaseController = new WeightBaseController(this);
			weightBaseInputWindow = new WeightBaseInputWindow(PROJECT_NAME, weightBaseController);
			}
		else {
			weightBaseInputWindow.inputFrameForWeightBase.setVisible(true);
			}
	}
	
	public void getPlateBaseDisplayer() {
		if( plateBaseInputWindow == null ) {
			plateBaseController = new PlateBaseController(this);
			plateBaseInputWindow = new PlateBaseInputWindow(PROJECT_NAME, plateBaseController);
			}
		else {
			plateBaseInputWindow.inputFrameForPlateBase.setVisible(true);	
			}
	}
	
	public void getSteakoutControlWindow() {
		if( steakoutControlWindow == null ) {
			steakoutController = new SteakoutController(this);
			steakoutControlWindow = new SteakoutControlWindow(PROJECT_NAME, steakoutController);
		}
		else {
			steakoutControlWindow.steakoutControlFrame.setVisible(true);
		}
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
		homeWindow.baseDataMenu.setEnabled(false);
		homeWindow.controlSteakoutMenu.setEnabled(false);
	}
	
	public String createNewProject() {
		String projectName = JOptionPane.showInputDialog(null, "Add meg a projekt nevét:", "A projekt nevének megadása", JOptionPane.DEFAULT_OPTION);
		if( projectName != null && InputDataValidator.isValidProjectName(projectName) ) {
			FileProcess.setFolder();
			if( FileProcess.FOLDER_PATH != null ) {
			PROJECT_NAME = projectName;
			setVisible();
			destroy();
			homeWindow.baseDataMenu.setEnabled(true);
		}
	}
		else if( projectName != null && !InputDataValidator.isValidProjectName(projectName) ) {
			getInfoMessage("Projekt név megadása", "A projekt neve legalább 3 karakter hosszúságú és betűvel kezdődő lehet.");
		}
		return projectName;
	}
	
	public void openProject() {
		String projectName = FileProcess.setProject();
		if( projectName == null ) {
			return;
		}
		PROJECT_NAME = projectName;
		homeWindow.baseDataMenu.setEnabled(true);
		if( FileProcess.isProjectFileExist() ) {
			setVisible();
			destroy();
			getInfoMessage("\"" + PROJECT_NAME + "\"" + " projekt",
					"Létező " + getBaseType() +" projekt megnyitva.");
			getProjectFileDataFromFile();
		}
	}
	
	public String getBaseType() {
		String baseType = "#WeightBase".equals(FileProcess.getProjectFileData().get(0)) ? "súlyalap" : 
			"#PlateBase".equals(FileProcess.getProjectFileData().get(0)) ? "lemezalap" : "";
		return baseType;
	}
	
	public void getExistedProjectInfoMessage() {
		if( FileProcess.isProjectFileExist() ) {
			getInfoMessage("\"" + PROJECT_NAME + "\"" + " projekt",
					"Létező " + getBaseType() +" projekt.");
		}
	}
	
	public static void getInfoMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}

	public int getWarningMessage(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}
	
	private void getProjectFileDataFromFile() {
		
		List<String> projectFileData = FileProcess.getProjectFileData();
	
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
		}
	}
	
}


