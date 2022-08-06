package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseInputWindow;

public class WeightBaseController extends HomeController {

	
	private HomeWindow homeWindow;
	private PillarCoordsForWeightBase pillarCoordsCalculator;
	private WeightBaseInputWindow weightBaseInputWindow;
	private WeightBaseDisplayer weightBaseDisplayer;
	private SteakoutControlWindow steakoutControlWindow;
	
	public WeightBaseController() {
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
