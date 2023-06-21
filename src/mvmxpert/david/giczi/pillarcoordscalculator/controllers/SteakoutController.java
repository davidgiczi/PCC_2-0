package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.service.SteakoutControl;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.BaseType;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.PointID;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;

public class SteakoutController {

	public HomeController homeController;
	private String delimiter;
	private String prePostFixValue;
	private String prePostFixSelectedOption;
	
	
	public SteakoutController(HomeController homeController) {
		this.homeController = homeController;
	}

	public void handleDeleteButtonClick() {
		homeController.steakoutControl = null;
		homeController.steakoutControlWindow.savedPointsTextLabel.setText("Az ellenőrzött pontok száma: 0 db");
	}
	
	public void handleOkButtonClick() {
		if( !isValidInputData() ) {
			return;
		}
		delimiter = homeController.steakoutControlWindow.delimiterSelectedValue;
		
		if(FileProcess.STK_SAVED_FILE_PATH == null) {
			FileProcess.STK_SAVED_FILE_PATH = FileProcess.FOLDER_PATH;
		}
		
		if( homeController.weightBaseCoordsCalculator != null ) {
			controlSteakoutForWeightBase();
		}
		else if( homeController.plateBaseCoordsCalculator != null ) {
			controlSteakoutForPlateBase();
		}
		saveSteakoutControlProcessResult();
		setControlledPointsNumberValue();
		setVisible();
	}
	
	private boolean isValidInputData() {
		
		if(homeController.steakoutControlWindow.stkFileNameField.getText().isEmpty() || 
			homeController.steakoutControlWindow.stkFilePlaceField.getText().isEmpty()) {
			homeController.getInfoMessage("Kitűzési fájl nevének/helyének megadása", "Kitűzési fájl választása szükséges.");
			return false;
		}
		if( prePostFixValue != null && prePostFixValue.isEmpty() && "előtag".equals(prePostFixSelectedOption)) {
			homeController.getInfoMessage("Pontszám előtag megadása", "Pontszám előtag értékének megadása szükséges.");
			return false;
		}
		else if(prePostFixValue != null && prePostFixValue.isEmpty() && "utótag".equals(prePostFixSelectedOption)) {
			homeController.getInfoMessage("Pontszám utótag megadása", "Pontszám utótag értékének megadása szükséges.");
			return false;
		}
		prePostFixValue = homeController.steakoutControlWindow.prePostFixValue;
		prePostFixSelectedOption = homeController.steakoutControlWindow.prePostFixSelectedOption;
		return true;
	}
	
	private void controlSteakoutForWeightBase() {
		
		if( homeController.steakoutControl == null ) {
			homeController.steakoutControl = new SteakoutControl(homeController, BaseType.WEIGHT_BASE);
		}
		homeController.steakoutControl.setDirectionPoint(homeController.weightBaseCoordsCalculator.getAxisDirectionPoint());
		homeController.steakoutControl.setDesignedPillarCoords(homeController.weightBaseCoordsCalculator.getPillarPoints());
		homeController.steakoutControl.setPointID(getPointID(prePostFixSelectedOption));
		homeController.steakoutControl.setPointIDValue(prePostFixValue);
		homeController.steakoutControl.setDelimiter(delimiter);
		homeController.steakoutControl.setRotation((int) homeController.weightBaseCoordsCalculator.getRotation());
		homeController.steakoutControl.controlSteakout();
		homeController.getWeightBaseFXDisplayer();
	}
	
	private void controlSteakoutForPlateBase() {
		
		if(homeController.steakoutControl == null ) {
			homeController.steakoutControl = new SteakoutControl(homeController, BaseType.PLATE_BASE);
		}
		homeController.steakoutControl.setDirectionPoint(homeController.plateBaseCoordsCalculator.getAxisDirectionPoint());
		homeController.steakoutControl.setDesignedPillarCoords(homeController.plateBaseCoordsCalculator.getPillarPoints());
		homeController.steakoutControl.setPointID(getPointID(prePostFixSelectedOption));
		homeController.steakoutControl.setPointIDValue(prePostFixValue);
		homeController.steakoutControl.setDelimiter(delimiter);
		homeController.steakoutControl.setRotation((int) homeController.plateBaseCoordsCalculator.getRotation());
		homeController.steakoutControl.controlSteakout();
		homeController.plateBaseDisplayer =	new PlateBaseDisplayer(homeController,
				 FileProcess.STK_SAVED_FILE_PATH + "\\" + HomeController.PROJECT_NAME + "_kit.txt");
		homeController.plateBaseDisplayer.setControlledCoords(homeController.steakoutControl.getControlledCoords());
	}
	
	private void setControlledPointsNumberValue() {
		if(homeController.steakoutControl != null) {
		homeController.steakoutControlWindow.savedPointsTextLabel
		.setText("Az ellenőrzött pontok száma: " + homeController.steakoutControl.getControlledCoords().size() + " db");
		}
	}
	
	private void saveSteakoutControlProcessResult() {
		if(homeController.steakoutControlWindow.yesPrintRadioBtn.isSelected() ) {
			homeController.steakoutControl.printControlledPoints();
			}
	}
	
	private PointID getPointID(String value) {
		
		switch (value) {
		case "előtag":
			return PointID.PREFIX;
		case "utótag":
			return PointID.POSTFIX;
		default:
			return PointID.POINT_ID;
		}
	}
	
	private void setVisible() {
	homeController.steakoutControlWindow.steakoutControlFrame.setVisible(false);
	}
}
