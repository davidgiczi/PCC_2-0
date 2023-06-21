package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import java.util.List;

import javax.swing.JOptionPane;

import javafx.application.Platform;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.FXHomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.WeightBaseFXDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.SteakoutControl;
import mvmxpert.david.giczi.pillarcoordscalculator.view.CalculateDistanceBetweenPillarLegsWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseInputWindow;

public class HomeController {

	public static String PROJECT_NAME;
	public HomeWindow homeWindow;
	public FileProcess fileProcess;
	WeightBaseInputWindow weightBaseInputWindow;
	PlateBaseInputWindow plateBaseInputWindow;
	WeightBaseDisplayer weightBaseDisplayer;
	PlateBaseDisplayer plateBaseDisplayer;
	SteakoutControlWindow steakoutControlWindow;
	public PillarCoordsForWeightBase weightBaseCoordsCalculator;
	public PillarCoordsForPlateBase plateBaseCoordsCalculator;
	public SteakoutControl steakoutControl;
	WeightBaseController weightBaseController;
	PlateBaseController plateBaseController;
	SteakoutController steakoutController;
	CalculateDistanceBetweenPillarLegsWindow calculateDistanceBetweenPillarLegsWindow;
	WeightBaseFXDisplayer weightBaseFXDisplayer;
	
	public HomeController() {
		this.fileProcess = new FileProcess(this);
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
			weightBaseInputWindow.inputFrameForWeightBase.setVisible(true);
			}
	}
	
	public void getPlateBaseInputWindow() {
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
	
	public void getCalculateDistanceBetweenPillarLegsWindow() {
		if( calculateDistanceBetweenPillarLegsWindow == null ) {
			calculateDistanceBetweenPillarLegsWindow = new CalculateDistanceBetweenPillarLegsWindow(this);
		}
		else {
			calculateDistanceBetweenPillarLegsWindow.inputFrame.setVisible(true);
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
			if( FileProcess.FOLDER_PATH != null ) {
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
		if( FileProcess.isProjectFileExist() ) {
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
		if( FileProcess.isProjectFileExist() ) {
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


