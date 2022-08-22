package mvmxpert.david.giczi.pillarcoordscalculator.controllers;


public class PlateBaseController implements Controller  {

	
	private Controller homeController;
	
	public PlateBaseController(Controller homeController) {
		this.homeController = homeController;
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
