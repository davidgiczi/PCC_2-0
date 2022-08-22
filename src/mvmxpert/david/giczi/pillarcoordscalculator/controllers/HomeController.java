package mvmxpert.david.giczi.pillarcoordscalculator.controllers;

import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForPlateBase;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsForWeightBase;
import mvmxpert.david.giczi.pillarcoordscalculator.view.HomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.PlateBaseInputWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.SteakoutControlWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseDisplayer;
import mvmxpert.david.giczi.pillarcoordscalculator.view.WeightBaseInputWindow;

public class HomeController implements Controller {

	public static String PROJECT_NAME;
	HomeWindow homeWindow;
	WeightBaseInputWindow weightBaseInputWindow;
	PlateBaseInputWindow plateBaseInputWindow;
	WeightBaseDisplayer weightBaseDisplayer;
	PlateBaseDisplayer plateBaseDisplayer;
	SteakoutControlWindow steakoutControlWindow;
	PillarCoordsForWeightBase weightBaseCoordsCalculator;
	PillarCoordsForPlateBase plateBaseCoordsCalculator;
	
	public HomeController() {
		this.homeWindow = new HomeWindow(this);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void destroy() {
		setVisible();
		weightBaseInputWindow = null;
		plateBaseInputWindow = null;
		weightBaseDisplayer = null;
		plateBaseDisplayer = null;
		steakoutControlWindow = null;
		weightBaseCoordsCalculator = null;
		plateBaseCoordsCalculator = null;
	}
	
	private void setVisible() {
		if( weightBaseInputWindow != null ) {
			weightBaseInputWindow.inputFrameForWeightBase.setVisible(false);
		}
		if( plateBaseCoordsCalculator != null ) {
			plateBaseInputWindow.inputFrameForPlateBase.setVisible(false);
		}
		if( weightBaseDisplayer != null ) {
			weightBaseDisplayer.setVisible(false);
		}
		if( plateBaseDisplayer != null ) {
			plateBaseDisplayer.setVisible(false);
		}
		if( steakoutControlWindow != null ) {
			steakoutControlWindow.steakoutControlFrame.setVisible(false);
		}
	}
	 
	
	
	
}
