package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import mvmxpert.david.giczi.pillarcoordscalculator.domain.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;

public class PlateBaseController {
	
	private HomeWindow homeWindow;
	private PillarCoordsForPlateBase pillarCoordsCalculator;
	private PlateBaseInputWindow plateBaseInputWindow;
	private SteakoutControlWindow steakoutControlWindow;
	
	public PlateBaseController(HomeWindow homeWindow) {
				this.homeWindow = homeWindow;
	}
	
	
}
