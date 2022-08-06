package mvmxpert.david.giczi.pillarcoordscalculator.app;

import mvmxpert.david.giczi.pillarcoordscalculator.controllers.HomeController;

public class PillarCoordsCalculatorApp {

	public static void main(String[] args)  {
				
		new HomeController() {
			
			@Override
			protected void init() {
				// TODO Auto-generated method stub
			}
			
			@Override
			protected void handleCountButtonClick() {
				// TODO Auto-generated method stub
			}
			
			@Override
			protected void destroy() {
				// TODO Auto-generated method stub
			}
		}.launch();
		
	}
}
