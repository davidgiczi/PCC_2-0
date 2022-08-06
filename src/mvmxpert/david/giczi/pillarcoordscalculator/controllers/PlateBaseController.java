package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;

public class PlateBaseController extends HomeController {
	
	private HomeWindow homeWindow;
	private PillarCoordsForPlateBase pillarCoordsCalculator;
	private PlateBaseInputWindow plateBaseInputWindow;
	private PlateBaseDisplayer plateBaseDisplayer;
	private SteakoutControlWindow steakoutControlWindow;
	
	public PlateBaseController() {
				this.homeWindow = super.homeWindow;
	}

	@Override
	protected void init() {
		
	}

	@Override
	protected void destroy() {
		
	}

	@Override
	protected void handleCountButtonClick() {
		
	}
	
	
}
