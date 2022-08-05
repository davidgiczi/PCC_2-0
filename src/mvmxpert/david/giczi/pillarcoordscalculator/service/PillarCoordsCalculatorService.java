package mvmxpert.david.giczi.pillarcoordscalculator.service;

import java.util.List;
import javax.management.InvalidAttributeValueException;
import javax.swing.JOptionPane;
import mvmxpert.david.giczi.pillarcoordscalculator.domain.InputDataValidator;
import mvmxpert.david.giczi.pillarcoordscalculator.domain.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.domain.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.domain.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.domain.SteakoutControl;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.BaseType;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.PointID;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseInputWindow;

public class PillarCoordsCalculatorService {

	public static String PROJECT_NAME;
	private static PillarCoordsForPlateBase plateBaseCoordsCalculator;
	private static PillarCoordsForWeightBase weightBaseCoordsCalculator;
	private static SteakoutControl steakoutControl;
	private static HomeWindow homeWindow;
	private static WeightBaseInputWindow weightBaseInputWindow;
	private static PlateBaseInputWindow plateBaseInputWindow;
	private static SteakoutControlWindow steakoutControlWindow;
	
	
	public PillarCoordsCalculatorService() {
		homeWindow = new HomeWindow();
	}
	
	public static String setProjectName() {
		
		String projectName = 
				JOptionPane.showInputDialog(null, "Add meg a projekt nevét:", "A projekt nevének megadása", JOptionPane.DEFAULT_OPTION);
		
	    if( projectName != null && InputDataValidator.isValidProjectName(projectName) ) {
			FileProcess.setFolder();
			if( FileProcess.FOLDER_PATH != null ) {
			homeWindow.steakoutMenu.setEnabled(true);
		}
	}
		else if( projectName != null && !InputDataValidator.isValidProjectName(projectName) ) {
			getInfoMessage("Projekt név megadása", "A projekt neve legalább 3 karakter hosszúságú és betűvel kezdődő lehet.");
			return null;
	}
		
		return projectName;
	}
	
	public static void getExistedProjectInfoMessage() {
		if( FileProcess.isProjectFileExist() ) {
			getInfoMessage("\"" + PROJECT_NAME + "\"" + " projekt",
					"Létező " + getBaseType() +" projekt.");
		}
	}
	
	public static void setProject() {
		String projectName = FileProcess.setProject();
		if( projectName == null ) {
			return;
		}
		destroy();
		PROJECT_NAME = projectName;
		homeWindow.steakoutMenu.setEnabled(true);
		if( FileProcess.isProjectFileExist() ) {
			getInfoMessage("\"" + PROJECT_NAME + "\"" + " projekt",
					"Létező " + getBaseType() +" projekt megnyitva.");
			getProjectFileDataFromFile();
		}
	}
	
	public static void getPlateBaseInputWindow() {
		if(plateBaseInputWindow == null) {
		plateBaseInputWindow = new PlateBaseInputWindow(PROJECT_NAME);
		getProjectFileDataFromFile();
		}
		else {
		plateBaseInputWindow.inputFrameForPlateBase.setVisible(true);	
		}
	}
	
	public static void getWeightBaseInputWindow() {
		if(weightBaseInputWindow == null) {
		weightBaseInputWindow = new WeightBaseInputWindow(PROJECT_NAME);
		getProjectFileDataFromFile();
		}
		else {
		weightBaseInputWindow.inputFrameForWeightBase.setVisible(true);
		}
	}
	
	public static void getSteakoutControlWindow() {
		if(steakoutControlWindow == null) {
		steakoutControlWindow = new SteakoutControlWindow(PROJECT_NAME);
		}
		else {
		steakoutControlWindow.steakoutControlFrame.setVisible(true);
		}
	}
	
	private static boolean saveAsProject() {
		
		if( FileProcess.isProjectFileExist() ) {
			
		if(	getWarningMessage("\"" + PROJECT_NAME + ".pcc\"", 
						"Létező " + getBaseType() + " projekt fájl, biztos, hogy felülírod?" ) == 2 ) {
			String projectName = setProjectName();
			if( projectName != null ) {
				PROJECT_NAME = projectName;
				getExistedProjectInfoMessage();
			}
			else {
				return false;
			}
		}	
	}
		return true;
}
	
	public static void clickCountButtonAtPlateBaseInputWindow() {
		
		String centerID = plateBaseInputWindow.centerIdField.getText();
		String directionID = plateBaseInputWindow.directionIdField.getText();
		
		if( !InputDataValidator.isValidID(centerID) || !InputDataValidator.isValidID(directionID) ) {
			getInfoMessage("Az oszlopok nevének megadása",
					"Az oszlopok neve/száma legalább egy karakter hosszúságú legyen.");
			return;
		}
	
	try {
		double centerX = InputDataValidator
				.isValidInputNumberValue(plateBaseInputWindow.x_centerField.getText().replace(',' , '.'));
		double centerY = InputDataValidator
				.isValidInputNumberValue(plateBaseInputWindow.y_centerField.getText().replace(',', '.'));
		Point center = new Point(centerID, centerX, centerY);
		double directionX = InputDataValidator
				.isValidInputNumberValue(plateBaseInputWindow.x_directionField.getText().replace(',', '.'));
		double directionY = InputDataValidator
				.isValidInputNumberValue(plateBaseInputWindow.y_directionField.getText().replace(',', '.'));
		Point direction = new Point(directionID, directionX, directionY);
		plateBaseCoordsCalculator = new PillarCoordsForPlateBase(center, direction);
		double horizontalSizeOfHole = InputDataValidator
				.isValidInputNumberValue(plateBaseInputWindow.horizontalSizeOfHoleField.getText().replace(',', '.'));
		plateBaseCoordsCalculator.setHorizontalSizeOfHole(horizontalSizeOfHole);
		double verticalSizeOfHole = InputDataValidator
				.isValidInputNumberValue(plateBaseInputWindow.verticalSizeOfHoleField.getText().replace(',', '.'));
		plateBaseCoordsCalculator.setVerticalSizeOfHole(verticalSizeOfHole);
		double horizontalDistanceFromHole = InputDataValidator
				.isValidInputNumberValue(plateBaseInputWindow.horizontalDistanceFromHoleSideField.getText().replace(',', '.'));
		plateBaseCoordsCalculator.setHorizontalDistanceFromTheSideOfHole(horizontalDistanceFromHole);
		double verticalDistanceFromHole = InputDataValidator
				.isValidInputNumberValue(plateBaseInputWindow.verticalDistanceFromHoleSideField.getText().replace(',', '.'));
		plateBaseCoordsCalculator.setVerticalDistanceFromTheSideOfHole(verticalDistanceFromHole);
		double rotationAngle = InputDataValidator.isValidAngleValue(plateBaseInputWindow.rotateAngularField.getText());
		plateBaseCoordsCalculator.setAngleValueBetweenMainPath(rotationAngle);
		double rotationMin = InputDataValidator.isValidAngleValue(plateBaseInputWindow.rotateAngularMinField.getText());
		plateBaseCoordsCalculator.setAngularMinuteValueBetweenMainPath(rotationMin);
		double rotationSec = InputDataValidator.isValidAngleValue(plateBaseInputWindow.rotateAngularSecField.getText());
		plateBaseCoordsCalculator.setAngularSecondValueBetweenMainPath(rotationSec);
		plateBaseCoordsCalculator.calculatePillarPoints();
		if( saveAsProject() ) {
		createProjectFileForPlateBase
		(centerID, centerX, centerY, 
		 directionID, directionX, directionY, 
		 horizontalSizeOfHole, verticalSizeOfHole,
		 horizontalDistanceFromHole, verticalDistanceFromHole, 
		 rotationAngle, rotationSec, rotationMin);
		}
		 saveCoordFilesForPlateBase();
		 new PlateBaseDisplayer(plateBaseCoordsCalculator.getPillarPoints(), 
				   plateBaseCoordsCalculator.getAxisDirectionPoint(),
				   plateBaseCoordsCalculator.getRadRotation(),
				   FileProcess.FOLDER_PATH + "\\" + PROJECT_NAME + ".pcc");
		 destroy();
		 weightBaseCoordsCalculator = null;
		 homeWindow.controlSteakoutMenu.setEnabled(true);
	} catch (NumberFormatException e) {
		getInfoMessage("Bemeneti adatok megadása",
				"Minden üres adatmező kitöltése és szám érték megadása szükséges.");
	} catch (InvalidAttributeValueException e) {
		getInfoMessage("Bemeneti adatok megadása", "Az oszlopok megadott koordinátái alapján irányszög nem számítható.");
	}	
}
	
	public static void clickCountButtonAtWeightBaseInputWindow() {
		
		String centerID = weightBaseInputWindow.centerIdField.getText();
		String directionID = weightBaseInputWindow.directionIdField.getText();
		
		if( !InputDataValidator.isValidID(centerID) || !InputDataValidator.isValidID(directionID) ) {
			getInfoMessage("Az oszlopok nevének megadása",
					"Az oszlopok neve/száma legalább egy karakter hosszúságú legyen.");
			return;
		}
	
	try {
		double centerX = InputDataValidator
				.isValidInputNumberValue(weightBaseInputWindow.x_centerField.getText().replace(',' , '.'));
		double centerY = InputDataValidator
				.isValidInputNumberValue(weightBaseInputWindow.y_centerField.getText().replace(',', '.'));
		Point center = new Point(centerID, centerX, centerY);
		double directionX = InputDataValidator
				.isValidInputNumberValue(weightBaseInputWindow.x_directionField.getText().replace(',', '.'));
		double directionY = InputDataValidator
				.isValidInputNumberValue(weightBaseInputWindow.y_directionField.getText().replace(',', '.'));
		Point direction = new Point(directionID, directionX, directionY);
		weightBaseCoordsCalculator = new PillarCoordsForWeightBase(center, direction);
		double distanceOnTheAxis = InputDataValidator
				.isValidInputNumberValue(weightBaseInputWindow.directionSizeField.getText().replace(',', '.'));
		weightBaseCoordsCalculator.setDistanceOnTheAxis(distanceOnTheAxis);
		double horizontalDistanceBetweenPillarLegs = InputDataValidator
				.isValidInputNumberValue(weightBaseInputWindow.horizontalSizeForPillarLegField.getText().replace(',', '.'));
		weightBaseCoordsCalculator.setHorizontalDistanceBetweenPillarLegs(horizontalDistanceBetweenPillarLegs);
		double verticalDistanceBetweenPillarLegs = InputDataValidator
				.isValidInputNumberValue(weightBaseInputWindow.verticalSizeForPillarLegField.getText().replace(',', '.'));
		weightBaseCoordsCalculator.setVerticalDistanceBetweenPillarLegs(verticalDistanceBetweenPillarLegs);
		double horizontalSizeOfHoleOfPillarLeg = InputDataValidator
				.isValidInputNumberValue(weightBaseInputWindow.horizontalSizeForHoleField.getText().replace(',', '.'));
		weightBaseCoordsCalculator.setHorizontalSizeOfHoleOfPillarLeg(horizontalSizeOfHoleOfPillarLeg);
		double verticalSizeOfHoleOfPillarLeg = InputDataValidator
				.isValidInputNumberValue(weightBaseInputWindow.verticalSizeForHoleField.getText().replace(',', '.'));
		weightBaseCoordsCalculator.setVerticalSizeOfHoleOfPillarLeg(verticalSizeOfHoleOfPillarLeg);
		double rotationAngle = InputDataValidator.isValidAngleValue(weightBaseInputWindow.rotateAngularField.getText());
		weightBaseCoordsCalculator.setAngleValueBetweenMainPath(rotationAngle);
		double rotationMin = InputDataValidator.isValidAngleValue(weightBaseInputWindow.rotateAngularMinField.getText());
		weightBaseCoordsCalculator.setAngularMinuteValueBetweenMainPath(rotationMin);
		double rotationSec = InputDataValidator.isValidAngleValue(weightBaseInputWindow.rotateAngularSecField.getText());
		weightBaseCoordsCalculator.setAngularSecondValueBetweenMainPath(rotationSec);
		weightBaseCoordsCalculator.calculatePillarPoints();
		if( saveAsProject() ) {
		createProjectFileForWeightBase
		(centerID, centerX, centerY, 
		directionID, directionX, directionY,
		distanceOnTheAxis, 
		horizontalDistanceBetweenPillarLegs, 
		verticalDistanceBetweenPillarLegs, 
		horizontalSizeOfHoleOfPillarLeg, 
		verticalSizeOfHoleOfPillarLeg, 
		rotationAngle, rotationSec, rotationMin);
		}
		saveCoordFilesForWeightBase();
		new WeightBaseDisplayer(weightBaseCoordsCalculator.getPillarPoints(), 
				   weightBaseCoordsCalculator.getAxisDirectionPoint(),
				   weightBaseCoordsCalculator.getRadRotation(),
				   FileProcess.FOLDER_PATH + "\\" + PROJECT_NAME + ".pcc");
		destroy();
		plateBaseCoordsCalculator = null;
		homeWindow.controlSteakoutMenu.setEnabled(true);
	} catch (NumberFormatException e) {
		getInfoMessage("Bemeneti adatok megadása",
				"Minden üres adatmező kitöltése szükséges.");
	} catch (InvalidAttributeValueException e) {
		getInfoMessage("Bemeneti adatok megadása", "Az oszlopok megadott koordinátái alapján irányszög nem számítható.");
	}			
}	
	
	public static void clickOkButtonAtStakeoutControlWindow() {
		
		if( steakoutControlWindow.stkFileNameField.getText().isEmpty() || 
				steakoutControlWindow.stkFilePlaceField.getText().isEmpty()) {
			getInfoMessage("Kitűzési fájl nevének/helyének megadása", "Kitűzési fájl választása szükséges.");
			return;
		}
		
		String prePostFixSelectedOption = steakoutControlWindow.prePostFixSelectedOption;
		String prePostFixValue = steakoutControlWindow.prePostFixValue;
		
		if( prePostFixValue != null && prePostFixValue.isEmpty() && "előtag".equals(prePostFixSelectedOption)) {
			getInfoMessage("Pontszám előtag megadása", "Pontszám előtag értékének megadása szükséges.");
			return;
		}
		else if(prePostFixValue != null && prePostFixValue.isEmpty() && "utótag".equals(prePostFixSelectedOption)) {
			getInfoMessage("Pontszám utótag megadása", "Pontszám utótag értékének megadása szükséges.");
			return;
		}
		
		String delimiter = steakoutControlWindow.delimiterSelectedValue;
		
		if( plateBaseCoordsCalculator != null) {
		
		if( steakoutControl == null ) {
				steakoutControl = new SteakoutControl(BaseType.PLATE_BASE);
			}
		steakoutControl.setDesignedPillarCoords(plateBaseCoordsCalculator.getPillarPoints());
		steakoutControl.setPointID(getPointID(prePostFixSelectedOption));
		steakoutControl.setPointIDValue(prePostFixValue);
		steakoutControl.setDelimiter(delimiter);
		steakoutControl.setRotation((int) plateBaseCoordsCalculator.getRotation());
		steakoutControl.controlSteakout();
		PlateBaseDisplayer plateBaseDisplayer =	new PlateBaseDisplayer
					(plateBaseCoordsCalculator.getPillarPoints(), 
					plateBaseCoordsCalculator.getAxisDirectionPoint(), 
					plateBaseCoordsCalculator.getRadRotation(),
					FileProcess.STK_SAVED_FILE_PATH + "\\" + PROJECT_NAME + "_kit.txt");
		plateBaseDisplayer.setControlledCoords(steakoutControl.getControlledCoords());
		}
		else if( weightBaseCoordsCalculator != null) {
		
		if( steakoutControl == null ) {
				steakoutControl = new SteakoutControl(BaseType.WEIGHT_BASE);
			}
		steakoutControl.setDesignedPillarCoords(weightBaseCoordsCalculator.getPillarPoints());
		steakoutControl.setPointID(getPointID(prePostFixSelectedOption));
		steakoutControl.setPointIDValue(prePostFixValue);
		steakoutControl.setDelimiter(delimiter);
		steakoutControl.setRotation((int) weightBaseCoordsCalculator.getRotation());
		steakoutControl.controlSteakout();
		WeightBaseDisplayer weightBaseDisplayer = new WeightBaseDisplayer(weightBaseCoordsCalculator.getPillarPoints(), 
					weightBaseCoordsCalculator.getAxisDirectionPoint(), 
					weightBaseCoordsCalculator.getRadRotation(),
					FileProcess.STK_SAVED_FILE_PATH + "\\" + PROJECT_NAME + "_kit.txt");
		weightBaseDisplayer.setControlledCoords(steakoutControl.getControlledCoords());
		}
		
		if( steakoutControlWindow.yesPrintRadioBtn.isSelected() ) {
		steakoutControl.printControlledCoords();
		}
		setControlledPointsNumberValue();
		steakoutControlWindow.steakoutControlFrame.setVisible(false);
	}
	
	public static void destroy() {
		if( weightBaseInputWindow != null ) {
			weightBaseInputWindow.inputFrameForWeightBase.setVisible(false);
		}
		if( plateBaseInputWindow != null ) {
			plateBaseInputWindow.inputFrameForPlateBase.setVisible(false);	
		}
		if( steakoutControlWindow != null ) {
			steakoutControlWindow.steakoutControlFrame.setVisible(false);
		}
		homeWindow.controlSteakoutMenu.setEnabled(false);
		plateBaseInputWindow = null;
		weightBaseInputWindow = null;
		steakoutControl = null;
		steakoutControlWindow = null;
	}
	
	private static void getInfoMessage(String title, String message) {
		JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
	
	private static int getWarningMessage(String title, String message) {
		return JOptionPane.showConfirmDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
	}
	
	private static String getBaseType() {
		String baseType = "#WeightBase".equals(FileProcess.getProjectFileData().get(0)) ? "súlyalap" : 
			"#PlateBase".equals(FileProcess.getProjectFileData().get(0)) ? "lemezalap" : "";
		return baseType;
	}
	
	private static void createProjectFileForPlateBase(
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
	
	private static void createProjectFileForWeightBase
		(String centerID, double centerX, double centerY, 
		 String directionID, double directionX,  double directionY,
		 double distanceOnTheAxis, 
		 double horizontalDistanceBetweenPillarLegs,
		 double verticalDistanceBetweenPillarLegs, 
		 double horizontalSizeOfHoleOfPillarLeg,
		 double verticalSizeOfHoleOfPillarLeg,
		 double rotationAngle, double rotationSec, double rotationMin) {
		
		FileProcess.saveProjectFileForWeightBase
		(centerID, centerX, centerY, 
		 directionID, directionX, directionY, 
		 distanceOnTheAxis, 
		 horizontalDistanceBetweenPillarLegs, 
		 verticalDistanceBetweenPillarLegs, 
		 horizontalSizeOfHoleOfPillarLeg, 
		 verticalSizeOfHoleOfPillarLeg, 
		 rotationAngle, rotationSec, rotationMin);
	}
	
	private static void saveCoordFilesForPlateBase() {
	
		if( plateBaseInputWindow == null ) {
			return;
		}
		
		if( plateBaseInputWindow.all.isSelected() ) {
			FileProcess.saveDataForKML(plateBaseCoordsCalculator.getPillarCenterPoint(), 
					plateBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForRTK(plateBaseCoordsCalculator.getPillarPoints(), 
					plateBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForTPS(plateBaseCoordsCalculator.getPillarPoints(), 
					plateBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForMS(plateBaseCoordsCalculator.getPillarPoints(), 
								plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( plateBaseInputWindow.kml.isSelected() ) {
			FileProcess.saveDataForKML(plateBaseCoordsCalculator.getPillarCenterPoint(), 
					plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( plateBaseInputWindow.rtk.isSelected() ) {
			FileProcess.saveDataForRTK(plateBaseCoordsCalculator.getPillarPoints(), 
					plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( plateBaseInputWindow.tps.isSelected() ) {
			FileProcess.saveDataForTPS(plateBaseCoordsCalculator.getPillarPoints(), 
					plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( plateBaseInputWindow.ms.isSelected() ) {
			FileProcess.saveDataForMS(plateBaseCoordsCalculator.getPillarPoints(),
								plateBaseCoordsCalculator.getAxisDirectionPoint());
		}
}
	
	private static void saveCoordFilesForWeightBase() {
		
		if( weightBaseInputWindow == null ) {
			return;
		}
		
		if( weightBaseInputWindow.all.isSelected() ) {
			FileProcess.saveDataForKML(weightBaseCoordsCalculator.getPillarCenterPoint(), 
					weightBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForRTK(weightBaseCoordsCalculator.getPillarPoints(), 
					weightBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForTPS(weightBaseCoordsCalculator.getPillarPoints(), 
					weightBaseCoordsCalculator.getAxisDirectionPoint());
			FileProcess.saveDataForMS(weightBaseCoordsCalculator.getPillarPoints(), 
								weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( weightBaseInputWindow.kml.isSelected() ) {
			FileProcess.saveDataForKML(weightBaseCoordsCalculator.getPillarCenterPoint(), 
					weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( weightBaseInputWindow.rtk.isSelected() ) {
			FileProcess.saveDataForRTK(weightBaseCoordsCalculator.getPillarPoints(), weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( weightBaseInputWindow.tps.isSelected() ) {
			FileProcess.saveDataForTPS(weightBaseCoordsCalculator.getPillarPoints(), weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
		if( weightBaseInputWindow.ms.isSelected() ) {
			FileProcess.saveDataForMS(weightBaseCoordsCalculator.getPillarPoints(),
								weightBaseCoordsCalculator.getAxisDirectionPoint());
		}
	}
	
	private static void getProjectFileDataFromFile() {
		
		List<String> projectFileData = FileProcess.getProjectFileData();
	
		if( plateBaseInputWindow !=null && !projectFileData.isEmpty() && "#PlateBase".equals(projectFileData.get(0)) ) {
			
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
		else if(weightBaseInputWindow != null && !projectFileData.isEmpty() && "#WeightBase".equals(projectFileData.get(0)) ) {
			
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
	
	
	private static void setControlledPointsNumberValue() {
		if(steakoutControl != null) {
		steakoutControlWindow.savedPointsTextLabel
		.setText("Az ellenőrzött pontok száma: " + steakoutControl.getControlledCoords().size() + " db");
		}
	}
	
	public static void clickDeleteButtonAtSteakoutControlWindow(){
		steakoutControl = null;
		steakoutControlWindow.savedPointsTextLabel.setText("Az ellenőrzött pontok száma: 0 db");
		
	}
	
	private static PointID getPointID(String value) {
		
		switch (value) {
		case "előtag":
			return PointID.PREFIX;
		case "utótag":
			return PointID.POSTFIX;
		default:
			return PointID.POINT_ID;
		}
	}
	
}
