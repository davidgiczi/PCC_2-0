package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import mvmxpert.david.giczi.pillarcoordscalculator.domain.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseInputWindow;

public class WeightBaseController {

	
	private HomeWindow homeWindow;
	private PillarCoordsForWeightBase pillarCoordsCalculator;
	private WeightBaseInputWindow weightBaseInputWindow;
	private SteakoutControlWindow steakoutControlWindow;
	
	public WeightBaseController(HomeWindow homeWindow) {
			this.homeWindow = homeWindow;
	}
	
	
}
