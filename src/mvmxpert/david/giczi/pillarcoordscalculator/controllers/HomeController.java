package mvmxpert.david.giczi.pillarcoordscalculator.controllers;



import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseInputWindow;

public abstract class HomeController {

	
	public static String PROJECT_NAME;
	protected HomeWindow homeWindow;
	protected WeightBaseInputWindow weightBaseInputWindow;
	protected PlateBaseInputWindow plateBaseInputWindow;
	protected WeightBaseDisplayer weightBaseDisplayer;
	protected PlateBaseDisplayer plateBaseDisplayer;
	protected SteakoutControlWindow steakoutControlWindow;
	protected PillarCoordsForWeightBase weightBaseCoordsCalculator;
	protected PillarCoordsForPlateBase plateBaseCoordsCalculator;
	
	
	protected abstract void init();
	protected abstract void destroy();
	protected abstract void handleCountButtonClick();

	public void launch() {
		homeWindow = new HomeWindow();
	}
	
	public Boolean isValidInputData() {
		return null;
	}
	
	public Boolean isExistedProjectFile() {
		return null;
	}
	
	
	
}
