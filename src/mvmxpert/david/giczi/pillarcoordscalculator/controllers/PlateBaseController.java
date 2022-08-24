package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import javax.management.InvalidAttributeValueException;

import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;

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
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
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
		
		if( homeController.saveAsProject() ) {
			
			
		}
		
	} catch (InvalidAttributeValueException e) {
		homeController.getInfoMessage("Bemeneti adatok megadása",
				"Az oszlopok megadott koordinátái alapján irányszög nem számítható.");
	}
	  catch (NumberFormatException e) {
		homeController.getInfoMessage("Bemeneti adatok megadása",
				"Minden üres adatmező kitöltése és szám érték megadása szükséges.");	
		}
	}
	
	private boolean isValidInputID() {
		
		String centerID = homeController.plateBaseInputWindow.centerIdField.getText();
		String directionID = homeController.plateBaseInputWindow.directionIdField.getText();
		 
		if( !InputDataValidator.isValidID(centerID) || !InputDataValidator.isValidID(directionID) ) {
			homeController.getInfoMessage("Az oszlopok nevének megadása",
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
	
}
