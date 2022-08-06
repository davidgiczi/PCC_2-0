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
	
	public WeightBaseController(HomeWindow homeWindow) {
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
