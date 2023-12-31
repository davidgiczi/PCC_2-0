package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.HomeController;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;

public class FXHomeWindow extends Application {

	private AnchorPane pane;
	public Stage homeStage;
	private static HomeController homeController;
	public static Menu setBaseData;
	public static Menu controlSteakoutedPoint;
	
	public static void setHomeController(HomeController homeController) {
		FXHomeWindow.homeController = homeController;
	}
	
	@Override
	public void stop() throws Exception {
		System.exit(0);
	}

	@Override
	public void start(Stage stage) throws Exception {
		homeController.measuredPillarDataController = new MeasuredPillarDataController(this);
		homeStage = stage;
		pane = new AnchorPane();
		addBackgroundImage();
		addMenu();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				
				if( homeController.getYesNoMessage("Program bezárása", 
						"Biztos, hogy kilépsz a programból?") == 0 ) {
					System.exit(0);
				}
				event.consume();
			};
		});
		stage.setTitle("Nagyfeszültségű távvezeték oszlop alapjának kitűzése");
		stage.getIcons().add(new Image("/img/MVM.jpg"));
		stage.setWidth(550);
		stage.setHeight(800);
		stage.setResizable(false);
		Scene root = new Scene(pane);
		stage.setScene(root);
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	private void addMenu() {
		MenuBar menuBar = new MenuBar();
		menuBar.setCursor(Cursor.HAND);
		Menu projectProcess = new Menu("Projekt műveletek");
		MenuItem openProject = new MenuItem("Projekt megnyitása");
		openProject.setOnAction(e -> homeController.openProject());
		MenuItem createProject = new MenuItem("Új projekt létrehozása");
		createProject.setOnAction(e -> homeController.createNewProject());
		MenuItem closeProject = new MenuItem("Program bezárása");
		closeProject.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				
				if( homeController.getYesNoMessage("Program bezárása", 
						"Biztos, hogy kilépsz a programból?") == 0 ) {
					System.exit(0);
				}
				
			}
		});
		projectProcess.getItems().addAll(openProject, createProject, new SeparatorMenuItem(), closeProject); 
		setBaseData = new Menu("Alap adatainak megadása");
		setBaseData.setDisable(true);
		MenuItem calcDistanceBetweenLegs = new MenuItem("Oszloplábak távolságának számítása");
		calcDistanceBetweenLegs.setOnAction(e -> homeController.getCalculateDistanceBetweenPillarLegsWindow());
		MenuItem calcWeightBasePoints = new MenuItem("Súlyalap pontjainak számítása");
		calcWeightBasePoints.setOnAction(e -> homeController.getWeightBaseInputWindow());
		MenuItem calcPlateBasePoints = new MenuItem("Lemezalap pontjainak számítása");
		calcPlateBasePoints.setOnAction(e -> homeController.getPlateBaseInputWindow());
		setBaseData.getItems().addAll(calcDistanceBetweenLegs, 
				new SeparatorMenuItem(), calcWeightBasePoints, calcPlateBasePoints);
		controlSteakoutedPoint = new Menu("Kitűzés vizsgálata");
		controlSteakoutedPoint.setDisable(true);
		MenuItem controll = new MenuItem("Kitűzött pontok ellenőrzése");
		controlSteakoutedPoint.getItems().add(controll);
		controll.setOnAction(e -> homeController.getSteakoutControlWindow());
		Menu pillarProject = new Menu("Oszlop bemérés");
		MenuItem openPillarProject = new MenuItem("Projekt megnyitása");

		openPillarProject.setOnAction(e -> {
		homeController.measuredPillarDataController.openPillarBaseProject();
		});

		MenuItem createPillarProject = new MenuItem("Új projekt létrehozása");
		createPillarProject.setOnAction(e -> {
		homeController.measuredPillarDataController.openMeasuredData();
		});
		MenuItem createIntersection = new MenuItem("Előmetszés létrehozása");
		createIntersection.setOnAction( e -> {
		homeController.measuredPillarDataController.openIntersectionInputDataWindow();
		});
		MenuItem openIntersection = new MenuItem("Előmetszés megnyitása");
		openIntersection.setOnAction(e -> {
		homeController.measuredPillarDataController.openIntersectionProject();
		});
		pillarProject.getItems().addAll(openPillarProject, createPillarProject, 
				new SeparatorMenuItem(), createIntersection, openIntersection);
		menuBar.getMenus().addAll(projectProcess, setBaseData, controlSteakoutedPoint, pillarProject);
		VBox vBox = new VBox(menuBar);
		vBox.setPrefWidth(550);
		pane.getChildren().add(vBox);
	}
	
	private void addBackgroundImage() {
		Image img = new Image("/img/pillars" + (int) (Math.random() * 3 + 1)  +".jpg");
		ImageView view = new ImageView(img);
		view.setFitWidth(550);
		view.setFitHeight(800);
		pane.getChildren().add(view);
	}
	
}
