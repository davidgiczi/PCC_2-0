package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import javax.management.InvalidAttributeValueException;
import javax.swing.JOptionPane;

import javafx.application.Platform;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.PCCFileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.FXHomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.PlateBaseFXDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.WeightBaseFXDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;

public class WeightBaseController implements Controller {

	public HomeController homeController;
	public String centerID;
	public String directionID;
	public double centerX;
	public double centerY;
	public double directionX;
	public double directionY;
	private double distanceOnTheAxis;
	private double horizontalDistanceBetweenPillarLegs;
	private double verticalDistanceBetweenPillarLegs;
	private double horizontalSizeOfHoleOfPillarLeg;
	private double verticalSizeOfHoleOfPillarLeg;
	public double rotationAngle;
	public double rotationMin;
	public double rotationSec;
	private boolean nonValidProjectName;
	
	public WeightBaseController(HomeController homeController) {
		this.homeController = homeController;
	}
	
	@Override
	public void handleCountButtonClick() {
		
		try {
			isValidInputData();
			if( !homeController.isValidControlDirectionData() ) {
				return;
			}
			this.centerID = homeController.weightBaseInputWindow.centerIdField.getText();
			this.directionID = homeController.weightBaseInputWindow.directionIdField.getText();
			if(	homeController.controlDirectionPoint != null &&	
					homeController.getYesNoMessage(homeController.getTitleForControlledAngle(), 
					homeController.getInfoForControlledAngle()) == 1 ){
				return;
			}
			Point center = new Point(centerID, centerX, centerY);
			Point direction = new Point(directionID, directionX, directionY);
			homeController.weightBaseCoordsCalculator = new PillarCoordsForWeightBase(center, direction);
			homeController.weightBaseCoordsCalculator.setDistanceOnTheAxis(distanceOnTheAxis);
			homeController.weightBaseCoordsCalculator.setHorizontalDistanceBetweenPillarLegs(horizontalDistanceBetweenPillarLegs);
			homeController.weightBaseCoordsCalculator.setVerticalDistanceBetweenPillarLegs(verticalDistanceBetweenPillarLegs);
			homeController.weightBaseCoordsCalculator.setHorizontalSizeOfHoleOfPillarLeg(horizontalSizeOfHoleOfPillarLeg);
			homeController.weightBaseCoordsCalculator.setVerticalSizeOfHoleOfPillarLeg(verticalSizeOfHoleOfPillarLeg);
			homeController.weightBaseCoordsCalculator.setAngleValueBetweenMainPath(rotationAngle);
			homeController.weightBaseCoordsCalculator.setAngularMinuteValueBetweenMainPath(rotationMin);
			homeController.weightBaseCoordsCalculator.setAngularSecondValueBetweenMainPath(rotationSec);
			int angleSideIndex = homeController.weightBaseInputWindow.sideComboBox.getSelectedIndex();
			homeController.weightBaseCoordsCalculator
			.setSideOfAngle(angleSideIndex == 0 ? true : false);
			homeController.weightBaseCoordsCalculator.calculatePillarPoints();
			
			if( PCCFileProcess.FOLDER_PATH == null ) {
				homeController.fileProcess.setFolder();
				if( PCCFileProcess.FOLDER_PATH == null ) {
					return;
				}
			}
			
			 if ( saveAsProject() ) {
				 createProjectFile(centerID, centerX, centerY, 
					  directionID, directionX, directionY,
					  distanceOnTheAxis, 
					  horizontalDistanceBetweenPillarLegs, 
					  verticalDistanceBetweenPillarLegs, 
					  horizontalSizeOfHoleOfPillarLeg, 
					  verticalSizeOfHoleOfPillarLeg, 
					  rotationAngle, rotationSec, rotationMin, angleSideIndex);
			 }
			 if( nonValidProjectName ) {
				 nonValidProjectName = false;
				 return;
			 }
			saveCoordFiles();
			setVisible();
			destroy();
			WeightBaseFXDisplayer.setDisplayPillarBaseCoords(true);
			homeController.getWeightBaseFXDisplayer();
		} catch (InvalidAttributeValueException e) {
			homeController.getInfoMessage("Bemeneti adatok megadása",
					"Az oszlopok megadott koordinátái alapján irányszög nem számítható.");
		}
		  catch (NumberFormatException e) {
			homeController.getInfoMessage("Bemeneti adatok megadása",
					"Minden üres adatmező kitöltése és szám értékek megadása szükséges.");	
			}
		}
	
	@Override
	public boolean saveAsProject() {
		
		if( PCCFileProcess.isProjectFileExist() ) {
			
			if( homeController.getYesNoMessage("\"" + HomeController.PROJECT_NAME + ".pcc\"", 
					"Létező " + homeController.getBaseType() + " projekt fájl, biztos, hogy felülírod?") == 1 ) {
				String newProjectName = createNewProject();
				if(newProjectName == null) 
					return false;
				if( !InputDataValidator.isValidProjectName(newProjectName) ) {
					nonValidProjectName = true;
					return false;
				}
		}
	}
		return true;
}
	
	@Override
	public void setVisible() {
		FXHomeWindow.setBaseData.setDisable(false);
		FXHomeWindow.controlSteakoutedPoint.setDisable(false);
		homeController.weightBaseInputWindow.inputFrameForWeightBase.setVisible(false);
		if(homeController.plateBaseDisplayer != null ) {
			homeController.plateBaseDisplayer.setVisible(false);
		}
	}
	
	@Override
	public String createNewProject() {
		String projectName = 
				JOptionPane.showInputDialog(null, "Add meg a projekt nevét:", "A projekt nevének megadása", JOptionPane.DEFAULT_OPTION);
		if( projectName != null && InputDataValidator.isValidProjectName(projectName) ) {
			homeController.fileProcess.setFolder();
			if( PCCFileProcess.FOLDER_PATH != null ) {
			HomeController.PROJECT_NAME = projectName;
			homeController.getExistedProjectInfoMessage();
			homeController.weightBaseInputWindow.inputFrameForWeightBase.setTitle(HomeController.PROJECT_NAME);
		}
	}
		else if( projectName != null && !InputDataValidator.isValidProjectName(projectName) ) {
		homeController.getInfoMessage("Projekt név megadása", "A projekt neve legalább 3 karakter hosszúságú és betűvel kezdődő lehet.");
		}
		
		return projectName;
	}
	
	@Override
	public void saveCoordFiles() {
		
		if(homeController.weightBaseInputWindow.all.isSelected() ) {
			homeController.fileProcess.saveDataForKML(homeController.weightBaseCoordsCalculator.getPillarCenterPoint(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
			homeController.fileProcess.saveDataForRTK(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
			homeController.fileProcess.saveDataForTPS(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
			homeController.fileProcess.saveDataForMS(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.weightBaseInputWindow.kml.isSelected() ) {
			homeController.fileProcess.saveDataForKML(homeController.weightBaseCoordsCalculator.getPillarCenterPoint(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.weightBaseInputWindow.rtk.isSelected() ) {
			homeController.fileProcess.saveDataForRTK(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.weightBaseInputWindow.tps.isSelected() ) {
			homeController.fileProcess.saveDataForTPS(homeController.weightBaseCoordsCalculator.getPillarPoints(), 
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( homeController.weightBaseInputWindow.ms.isSelected() ) {
			homeController.fileProcess.saveDataForMS(homeController.weightBaseCoordsCalculator.getPillarPoints(),
					homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
	}
	
	@Override
	public void destroy() {
		homeController.plateBaseCoordsCalculator = null;
		Platform.runLater(new Runnable() {
			
			@Override
			public void run() {
				
				if( PlateBaseFXDisplayer.stage != null )
					PlateBaseFXDisplayer.stage.hide();
				
			}
		});
		
	}

		public void isValidInputData() throws NumberFormatException {
			
			centerX = InputDataValidator
					.isValidInputDoubleValue(homeController.weightBaseInputWindow.x_centerField.getText().replace(',' , '.'));
			centerY = InputDataValidator
					.isValidInputDoubleValue(homeController.weightBaseInputWindow.y_centerField.getText().replace(',', '.'));
			directionX = InputDataValidator
					.isValidInputDoubleValue(homeController.weightBaseInputWindow.x_directionField.getText().replace(',', '.'));
			directionY = InputDataValidator
					.isValidInputDoubleValue(homeController.weightBaseInputWindow.y_directionField.getText().replace(',', '.'));
			distanceOnTheAxis = InputDataValidator
					.isValidInputDoubleValue(homeController.weightBaseInputWindow.directionSizeField.getText().replace(',', '.'));
			horizontalDistanceBetweenPillarLegs = InputDataValidator
					.isValidInputDoubleValue(homeController.weightBaseInputWindow.horizontalSizeForPillarLegField.getText().replace(',', '.'));
			verticalDistanceBetweenPillarLegs = InputDataValidator
					.isValidInputDoubleValue(homeController.weightBaseInputWindow.verticalSizeForPillarLegField.getText().replace(',', '.'));
			horizontalSizeOfHoleOfPillarLeg = InputDataValidator
					.isValidInputDoubleValue(homeController.weightBaseInputWindow.horizontalSizeForHoleField.getText().replace(',', '.'));
			verticalSizeOfHoleOfPillarLeg = InputDataValidator
					.isValidInputDoubleValue(homeController.weightBaseInputWindow.verticalSizeForHoleField.getText().replace(',', '.'));
			rotationAngle = InputDataValidator.isValidAngleValue(homeController.weightBaseInputWindow.rotateAngularField.getText());
			rotationMin = InputDataValidator.isValidAngleValue(homeController.weightBaseInputWindow.rotateAngularMinField.getText());
			rotationSec = InputDataValidator.isValidAngleValue(homeController.weightBaseInputWindow.rotateAngularSecField.getText());
		}
		
	private void createProjectFile
		(String centerID, double centerX, double centerY, 
		 String directionID, double directionX,  double directionY,
		 double distanceOnTheAxis, 
		 double horizontalDistanceBetweenPillarLegs,
		 double verticalDistanceBetweenPillarLegs, 
		 double horizontalSizeOfHoleOfPillarLeg,
		 double verticalSizeOfHoleOfPillarLeg,
		 double rotationAngle, double rotationSec, double rotationMin, int angleSideIndex) {
		
		if( homeController.controlDirectionPoint == null ) {
			homeController.fileProcess.saveProjectFileForWeightBase
			(centerID, centerX, centerY, 
			 directionID, directionX, directionY,
			 null, 0, 0,
			 distanceOnTheAxis, 
			 horizontalDistanceBetweenPillarLegs, 
			 verticalDistanceBetweenPillarLegs, 
			 horizontalSizeOfHoleOfPillarLeg, 
			 verticalSizeOfHoleOfPillarLeg, 
			 rotationAngle, rotationSec, rotationMin, angleSideIndex);
		}
		else {
			homeController.fileProcess.saveProjectFileForWeightBase
			(centerID, centerX, centerY, 
			 directionID, directionX, directionY,
			 homeController.controlDirectionPoint.getPointID(), 
			 homeController.controlDirectionPoint.getX_coord(),
			 homeController.controlDirectionPoint.getY_coord(),
			 distanceOnTheAxis, 
			 horizontalDistanceBetweenPillarLegs, 
			 verticalDistanceBetweenPillarLegs, 
			 horizontalSizeOfHoleOfPillarLeg, 
			 verticalSizeOfHoleOfPillarLeg, 
			 rotationAngle, rotationSec, rotationMin, angleSideIndex);
		}
		
		
	}
		
}
