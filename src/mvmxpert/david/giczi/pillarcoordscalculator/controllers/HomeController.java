package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseInputWindow;

public class HomeController {

	public static String PROJECT_NAME;
	protected HomeWindow homeWindow;
	protected WeightBaseInputWindow weightBaseInputWindow;
	protected PlateBaseInputWindow plateBaseInputWindow;
	protected WeightBaseDisplayer weightBaseDisplayer;
	protected PlateBaseDisplayer plateBaseDisplayer;
	protected SteakoutControlWindow steakoutControlWindow;
	protected PillarCoordsForWeightBase weightBaseCoordsCalculator;
	protected PillarCoordsForPlateBase plateBaseCoordsCalculator;
	
	protected void launch() {
		homeWindow = new HomeWindow();
	}
	
	protected void handleCountButtonClick() {};
	protected void init() {};
	protected void destroy(){};
	protected void isValidInputData() {}
	protected void handleOkButtonClick() {};
	protected Boolean isExistedProjectFile() {
		return null;
	}
	
	
	
}
