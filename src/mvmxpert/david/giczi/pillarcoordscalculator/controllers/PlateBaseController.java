package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import javax.management.InvalidAttributeValueException;
import javax.swing.JOptionPane;

import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;

public class PlateBaseController implements Controller  {

	
	private HomeController homeController;
	private String centerID;
	private String directionID;
	private double centerX;
	private double centerY;
	private double directionX;
	private double directionY;
	private double horizontalSizeOfHole;
	private double verticalSizeOfHole;
	private double horizontalDistanceFromHole;
	private double verticalDistanceFromHole;
	private double rotationAngle;
	private double rotationMin;
	private double rotationSec;
	
	public PlateBaseController(HomeController homeController) {
		this.homeController = homeController;
	}
	
	@Override
	public void handleCountButtonClick() {
		
	if( !isValidInputID() ) {
		return;
	}
	try {
		isValidInputData();
		Point center = new Point(centerID, centerX, centerY);
		Point direction = new Point(directionID, directionX, directionY);
		homeController.plateBaseCoordsCalculator = new PillarCoordsForPlateBase(center, direction);
		homeController.plateBaseCoordsCalculator.setHorizontalSizeOfHole(horizontalSizeOfHole);
		homeController.plateBaseCoordsCalculator.setVerticalSizeOfHole(verticalSizeOfHole);
		homeController.plateBaseCoordsCalculator.setHorizontalDistanceFromTheSideOfHole(horizontalDistanceFromHole);
		homeController.plateBaseCoordsCalculator.setVerticalDistanceFromTheSideOfHole(verticalDistanceFromHole);
		homeController.plateBaseCoordsCalculator.setAngleValueBetweenMainPath(rotationAngle);
		homeController.plateBaseCoordsCalculator.setAngularMinuteValueBetweenMainPath(rotationMin);
		homeController.plateBaseCoordsCalculator.setAngularSecondValueBetweenMainPath(rotationSec);
		homeController.plateBaseCoordsCalculator.calculatePillarPoints();
		
		if( saveAsProject() ) {
			createProjectFile(
					centerID, centerX, centerY, 
					directionID, directionX, directionY,
					horizontalSizeOfHole, verticalSizeOfHole,
					horizontalDistanceFromHole, verticalDistanceFromHole,
					rotationAngle, rotationSec, rotationMin);
		}
		
		saveCoordFiles();
				homeController.plateBaseDisplayer =	
						new PlateBaseDisplayer(homeController.plateBaseCoordsCalculator.getPillarPoints(), 
				homeController.plateBaseCoordsCalculator.getAxisDirectionPoint(),
				homeController.plateBaseCoordsCalculator.getRadRotation(),
				   FileProcess.FOLDER_PATH + "\\" + HomeController.PROJECT_NAME + ".pcc");
		setVisible();
		destroy();
	} catch (InvalidAttributeValueException e) {
		HomeController.getInfoMessage("Bemeneti adatok megadása",
				"Az oszlopok megadott koordinátái alapján irányszög nem számítható.");
	}
	  catch (NumberFormatException e) {
		HomeController.getInfoMessage("Bemeneti adatok megadása",
				"Minden üres adatmező kitöltése és szám érték megadása szükséges.");	
		}
	}
	
	@Override
	public boolean saveAsProject() {
		
		if( FileProcess.isProjectFileExist() ) {
			
			if( homeController.getWarningMessage("\"" + HomeController.PROJECT_NAME + ".pcc\"", 
					"Létező " + homeController.getBaseType() + " projekt fájl, biztos, hogy felülírod?") == 2 ) {
				String newProjectName = createNewProject();
				if(newProjectName == null) {
					return false;
			}
		}
	}
		return true;
	}
	
	@Override
	public void setVisible() {
		homeController.homeWindow.baseDataMenu.setEnabled(true);
		homeController.homeWindow.controlSteakoutMenu.setEnabled(true);
		homeController.plateBaseInputWindow.inputFrameForPlateBase.setVisible(false);
		if( homeController.weightBaseDisplayer != null ) {
			homeController.weightBaseDisplayer.setVisible(false);
		}
	}
	
	@Override
	public String createNewProject() {
		
		String projectName = 
				JOptionPane.showInputDialog(null, "Add meg a projekt nevét:", "A projekt nevének megadása", JOptionPane.DEFAULT_OPTION);
		if( projectName != null && InputDataValidator.isValidProjectName(projectName) ) {
			FileProcess.setFolder();
			if( FileProcess.FOLDER_PATH != null ) {
			HomeController.PROJECT_NAME = projectName;
			homeController.plateBaseInputWindow.inputFrameForPlateBase.setTitle(HomeController.PROJECT_NAME);
		}
	}
		else if( projectName != null && !InputDataValidator.isValidProjectName(projectName) ) {
		HomeController.getInfoMessage("Projekt név megadása", "A projekt neve legalább 3 karakter hosszúságú és betűvel kezdődő lehet.");
		}
		
		return projectName;
	}
	
	@Override
	public void saveCoordFiles() {
		
		if(homeController.plateBaseInputWindow.all.isSelected() ) {
			FileProcess.saveDataForKML(homeController.plateBaseCoordsCalculator.getPillarCenterPoint(), 
					homeController.plateBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForRTK(homeController.plateBaseCoordsCalculator.getPillarPoints(), 
					homeController.plateBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForTPS(homeController.plateBaseCoordsCalculator.getPillarPoints(), 
					homeController.plateBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForMS(homeController.plateBaseCoordsCalculator.getPillarPoints(), 
					homeController.plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.plateBaseInputWindow.kml.isSelected() ) {
			FileProcess.saveDataForKML(homeController.plateBaseCoordsCalculator.getPillarCenterPoint(), 
					homeController.plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.plateBaseInputWindow.rtk.isSelected() ) {
			FileProcess.saveDataForRTK(homeController.plateBaseCoordsCalculator.getPillarPoints(), 
					homeController.plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.plateBaseInputWindow.tps.isSelected() ) {
			FileProcess.saveDataForTPS(homeController.plateBaseCoordsCalculator.getPillarPoints(), 
					homeController.plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.plateBaseInputWindow.ms.isSelected() ) {
			FileProcess.saveDataForMS(homeController.plateBaseCoordsCalculator.getPillarPoints(),
					homeController.plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
}

	@Override
	public void destroy() {
		homeController.weightBaseCoordsCalculator = null;
		homeController.plateBaseDisplayer = null;
	}

	private boolean isValidInputID() {
		
		String centerID = homeController.plateBaseInputWindow.centerIdField.getText();
		String directionID = homeController.plateBaseInputWindow.directionIdField.getText();
		 
		if( !InputDataValidator.isValidID(centerID) || !InputDataValidator.isValidID(directionID) ) {
			HomeController.getInfoMessage("Az oszlopok nevének megadása",
					"Az oszlopok neve/száma legalább egy karakter hosszúságú legyen.");
			return false;
	}
			this.centerID = centerID;
			this.directionID = directionID;
			return true;
	}
	
	private void isValidInputData() throws NumberFormatException {
		centerX = InputDataValidator
				.isValidInputNumberValue(homeController.plateBaseInputWindow.x_centerField.getText().replace(',' , '.'));
		centerY = InputDataValidator
				.isValidInputNumberValue(homeController.plateBaseInputWindow.y_centerField.getText().replace(',', '.'));
		directionX = InputDataValidator
				.isValidInputNumberValue(homeController.plateBaseInputWindow.x_directionField.getText().replace(',', '.'));
		directionY = InputDataValidator
				.isValidInputNumberValue(homeController.plateBaseInputWindow.y_directionField.getText().replace(',', '.'));
		horizontalSizeOfHole = InputDataValidator
				.isValidInputNumberValue(homeController.plateBaseInputWindow.horizontalSizeOfHoleField.getText().replace(',', '.'));
		verticalSizeOfHole = InputDataValidator
				.isValidInputNumberValue(homeController.plateBaseInputWindow.verticalSizeOfHoleField.getText().replace(',', '.'));
		horizontalDistanceFromHole = InputDataValidator
				.isValidInputNumberValue(homeController.plateBaseInputWindow.horizontalDistanceFromHoleSideField.getText().replace(',', '.'));
		verticalDistanceFromHole = InputDataValidator
				.isValidInputNumberValue(homeController.plateBaseInputWindow.verticalDistanceFromHoleSideField.getText().replace(',', '.'));
		rotationAngle = InputDataValidator.isValidAngleValue(homeController.plateBaseInputWindow.rotateAngularField.getText());
		rotationMin = InputDataValidator.isValidAngleValue(homeController.plateBaseInputWindow.rotateAngularMinField.getText());
		rotationSec = InputDataValidator.isValidAngleValue(homeController.plateBaseInputWindow.rotateAngularSecField.getText());
	}
	
	private void createProjectFile(
			String centerID, double centerX, double centerY, 
			String directionID, double directionX,  double directionY,
			double horizontalSizeOfHole, double verticalSizeOfHole,
			double horizontalDistanceFromHole, double verticalDistanceFromHole,
			double rotationAngle, double rotationSec, double rotationMin) {
			
			FileProcess.saveProjectFileForPlatetBase
			(centerID, centerX, centerY, 
			 directionID, directionX, directionY, 
			 horizontalSizeOfHole, verticalSizeOfHole, 
			 horizontalDistanceFromHole, verticalDistanceFromHole, 
			 rotationAngle, rotationSec, rotationMin);
		}
}
