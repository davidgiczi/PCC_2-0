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
	
	public PlateBaseController(HomeWindow homeWindow) {
				this.homeWindow = homeWindow;
	}

	@Override
	protected void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void handleCountButtonClick() {
		// TODO Auto-generated method stub
		
	}
	
	
}
