package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import mvmxpert.david.giczi.pillarcoordscalculator.service.OrientationData;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.Location;

public class OrientationDisplayer {

	
	
	private TextField standingPointId;
	private TextField standingPointY;
	private TextField standingPointX;
	private TextField mediumOrientationData;
	private boolean isOrientationByDistance;
	private List<String> inputData;
	private List<OrientationDataRow> dataRowList;
	private final AnchorPane pane = new AnchorPane();
	private final Stage stage;
    public MeasuredPillarDataController measuredPillarDataController;
    private final Font headerFont = Font.font("Arial", FontWeight.BOLD, 12.5);
    private final Font dataFont = Font.font("Arial", FontWeight.SEMI_BOLD, 14);
    private final Font buttonFont = Font.font("Arial", FontWeight.BOLD, 14);
    private static final List<String> HEADER = 
    		Arrays.asList("Táj.pont sz.", "Y", "X", "Irányszög", "Távolság", "Irányérték", "Táj.szög", "Táj.irányért.");
    private static int LAYOUT_X = 20;
    private static int LAYOUT_Y = 60;
    
    
    public OrientationDisplayer(MeasuredPillarDataController measuredPillarDataController, Location location){
        this.measuredPillarDataController = measuredPillarDataController;
        this.stage = new Stage();
        stage.initOwner(this.measuredPillarDataController.measurmentDataDisplayer.stage);
        stage.setOnCloseRequest(windowEvent -> {
        	measuredPillarDataController.fxHomeWindow.homeStage.show();});
        pane.setStyle("-fx-background-color: white");
        ScrollPane scrollPane = getScrollPane(pane);
        addContent();
        Scene scene = new Scene(scrollPane);
        stage.setTitle("Tájékozás");
        stage.getIcons().add(new Image("/img/MVM.jpg"));
        if( location == Location.LEFT ) {
        stage.setX(0);
        stage.setY(100);
        }
        else if( location == Location.RIGHT ) {
        stage.setX(750);
        stage.setY(100);
        }
        stage.setWidth(800);
        stage.setHeight(465);
        stage.setResizable(false);
        stage.setMaximized(false);
        stage.setScene(scene);
        stage.show();
    }
	
    private ScrollPane getScrollPane(AnchorPane content){
        ScrollPane scroller = new ScrollPane(content);
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scroller;
    }
    
    private void addContent() {
    	addStandingPointData();
    	addDataTable();
    	addButtonRow();
    }
    
    private void addDataTable() {
    	addHeader();
    	addDataRow(10);
    }
    
    private void addHeader() {
    	OrientationDataRow header = new OrientationDataRow(true);
    	header.setLayoutX(LAYOUT_X);
    	header.setLayoutY(LAYOUT_Y - 10);
    	header.directionPointId.setFont(headerFont);
    	header.directionPointId.setEditable(false);
    	header.directionPointId.setText(HEADER.get(0));
    	header.directionPointY.setFont(headerFont);
    	header.directionPointY.setEditable(false);
    	header.directionPointY.setText(HEADER.get(1));
    	header.directionPointY.setFont(headerFont);
    	header.directionPointX.setFont(headerFont);
    	header.directionPointX.setEditable(false);
    	header.directionPointX.setText(HEADER.get(2));
    	header.directionPointX.setFont(headerFont);
    	header.azimuthAngelValue.setFont(headerFont);
    	header.azimuthAngelValue.setEditable(false);
    	header.azimuthAngelValue.setText(HEADER.get(3));
    	header.azimuthAngelValue.setFont(headerFont);
    	header.distanceValue.setFont(headerFont);
    	header.distanceValue.setEditable(false);
    	header.distanceValue.setText(HEADER.get(4));
    	header.distanceValue.setFont(headerFont);
    	header.directionAngelValue.setFont(headerFont);
    	header.directionAngelValue.setEditable(false);
    	header.directionAngelValue.setText(HEADER.get(5));
    	header.directionAngelValue.setFont(headerFont);
    	header.orientationAngelValue.setFont(headerFont);
    	header.orientationAngelValue.setEditable(false);
    	header.orientationAngelValue.setText(HEADER.get(6));
    	header.orientationAngelValue.setFont(headerFont);
    	header.orientedDirectionValue.setFont(headerFont);
    	header.orientedDirectionValue.setEditable(false);
    	header.orientedDirectionValue.setText(HEADER.get(7));
    	header.orientedDirectionValue.setFont(headerFont);
    	pane.getChildren().add(header);
    }
    
    private void addDataRow(int pcs) {
    	this.dataRowList = new ArrayList<>();
    	int layoutY = 80;
    	
    	for (int i = 0; i < pcs; i++) {
			OrientationDataRow dataRow = new OrientationDataRow(false);
			dataRow.setLayoutX(LAYOUT_X);
			dataRow.setLayoutY(layoutY);
			dataRow.directionPointId.setFont(dataFont);
			dataRow.directionPointId.setStyle("-fx-text-fill: red;");
			dataRow.directionPointY.setFont(dataFont);
			dataRow.directionPointX.setFont(dataFont);
			dataRow.azimuthAngelValue.setFont(dataFont);
			dataRow.distanceValue.setFont(dataFont);
			dataRow.directionAngelValue.setFont(dataFont);
			dataRow.orientationAngelValue.setFont(dataFont);
			dataRow.orientedDirectionValue.setFont(dataFont);
			dataRow.orientedDirectionValue.setStyle("-fx-text-fill: #00CF00;");
			dataRow.orientationAngelValue.setStyle("-fx-text-fill: blue;");
			dataRow.useData.setOnAction( e -> {
				
				this.inputData = getOrientationInputData();
				setShadowForRow(dataRow);
				if(  this.inputData.size() < 2 ) {
	    			return;
				}
				measuredPillarDataController.calcOrientation(inputData, isOrientationByDistance);
				refreshDisplayerData();
			});
			layoutY += 30;
			pane.getChildren().add(dataRow);
			dataRowList.add(dataRow);
		}
    	
    }
    
    
    private void setShadowForRow(OrientationDataRow dataRow) {
    	if( dataRow.useData.isSelected() ) {
			dataRow.directionPointId.setStyle(
				    "-fx-control-inner-background: white;" +
				    	    "-fx-text-fill: red;"
				    	);
			dataRow.directionPointY.setStyle("-fx-control-inner-background: white;");
			dataRow.directionPointX.setStyle("-fx-control-inner-background: white;");
			dataRow.azimuthAngelValue.setStyle("-fx-control-inner-background: white;");
			dataRow.distanceValue.setStyle("-fx-control-inner-background: white;");
			dataRow.directionAngelValue.setStyle("-fx-control-inner-background: white;");
			dataRow.orientationAngelValue.setStyle(
				    "-fx-control-inner-background: white;" +
				    	    "-fx-text-fill: blue;"
				    	);
			dataRow.orientedDirectionValue.setStyle(
				    "-fx-control-inner-background: white;" +
				    	    "-fx-text-fill: #00CF00;"
				    	);
		}
		else {
			dataRow.directionPointId.setStyle(
				    "-fx-control-inner-background: #F2F2F2;" +
				    	    "-fx-text-fill: red;"
				    	);
			dataRow.directionPointY.setStyle("-fx-control-inner-background: #F2F2F2;");
			dataRow.directionPointX.setStyle("-fx-control-inner-background: #F2F2F2;");
			dataRow.azimuthAngelValue.setStyle("-fx-control-inner-background: #F2F2F2;");
			dataRow.distanceValue.setStyle("-fx-control-inner-background: #F2F2F2;");
			dataRow.directionAngelValue.setStyle("-fx-control-inner-background: #F2F2F2;");
			dataRow.orientationAngelValue.setStyle(
				    "-fx-control-inner-background: #F2F2F2;" +
				    	    "-fx-text-fill: blue;");
			dataRow.orientedDirectionValue.setStyle(
				    "-fx-control-inner-background: #F2F2F2;" +
				    	    "-fx-text-fill: #00CF00;");
		}
    }
    
    
    private void addStandingPointData() {
    	
    	int width = 90;
    	int height = 30;
    	
    	HBox standingPointBox = new HBox();
    	standingPointBox.setLayoutX(LAYOUT_X);
    	standingPointBox.setLayoutY(10);
    	
    	this.standingPointId = new TextField();
    	standingPointId.setPromptText("Állásp.sz.");
    	standingPointId.setPrefWidth(width);
    	standingPointId.setPrefHeight(height);
    	standingPointId.setFont(dataFont);
    	standingPointId.setStyle("-fx-alignment: CENTER;");
    	standingPointId.setStyle("-fx-text-fill: red;");
    	standingPointId.setAlignment(Pos.CENTER);
    	standingPointId.setCursor(Cursor.HAND);
    	
    	this.standingPointY = new TextField();
    	standingPointY.setPromptText("Y");
    	standingPointY.setPrefWidth(width);
    	standingPointY.setPrefHeight(height);
    	standingPointY.setFont(dataFont);
    	standingPointY.setStyle("-fx-alignment: CENTER;");
    	standingPointY.setAlignment(Pos.CENTER);
    	standingPointY.setEditable(false);
    	
    	this.standingPointX = new TextField();
    	standingPointX.setPromptText("X");
    	standingPointX.setPrefWidth(width);
    	standingPointX.setPrefHeight(height);
    	standingPointX.setFont(dataFont);
    	standingPointX.setStyle("-fx-alignment: CENTER;");
    	standingPointX.setAlignment(Pos.CENTER);
    	standingPointX.setEditable(false);

    	standingPointBox.getChildren().addAll(standingPointId, standingPointY, standingPointX);
    	pane.getChildren().addAll(standingPointBox);
    }
    
    
    private void addButtonRow() {
    	
    	HBox buttonBox = new HBox(145);
    	buttonBox.setLayoutY(390);
    	buttonBox.setLayoutX(350);
    	
    	Button calcButton = new Button("Számol");
    	calcButton.setFont(buttonFont);
    	calcButton.setPrefHeight(30);
    	calcButton.setCursor(Cursor.HAND);
    	calcButton.setOnAction( c ->{
    		
    		this.inputData = getOrientationInputData();
    		if(  inputData.size() < 2 ) {
    			return;
    		}
    		getYesNoMessageForOrientation();
    		displayDataForOrientation();
    		for (OrientationDataRow dataRow : dataRowList) {
    			dataRow.useData.setDisable(false);
    		} 
    	});
    	
    	this.mediumOrientationData = new TextField();
    	mediumOrientationData.setEditable(false);
    	mediumOrientationData.setPromptText("Zk");
    	mediumOrientationData.setPrefWidth(90);
    	mediumOrientationData.setPrefHeight(30);
    	mediumOrientationData.setAlignment(Pos.CENTER);
    	mediumOrientationData.setStyle("-fx-text-fill: blue;");
    	mediumOrientationData.setFont(buttonFont);
 
    	buttonBox.getChildren().addAll(calcButton, mediumOrientationData);
    	pane.getChildren().addAll(buttonBox);
    }
    
    private void getYesNoMessageForOrientation() {
    	int option = FXHomeWindow.homeController.getYesNoMessage("Súlyegység választása", 
				"Igen: p=t [km], Nem: p=1.");
		
		if( option == 0 ) {
			this.isOrientationByDistance = true;
			stage.setTitle("Tájékozás - [távolságfüggő]");
			
		}
		else if( option == 1 )  {
			this.isOrientationByDistance = false;
			stage.setTitle("Tájékozás - [egységsúly]");
		}
		measuredPillarDataController.calcOrientation(inputData, isOrientationByDistance);
    }
    
    private List<String> getOrientationInputData(){
    	List<String> inputDataList = new ArrayList<>();
    	String standingPointId = this.standingPointId.getText().trim();
    	if( standingPointId.isEmpty() ) {
    		FXHomeWindow.homeController.getInfoMessage("Hiányzó álláspont", "Az álláspont számának megadása szükséges.");
    		return inputDataList;
    	}
    	inputDataList.add(standingPointId);
    	for (OrientationDataRow dataRow : dataRowList) {
    		String directionPointId = dataRow.directionPointId.getText().trim();
			if( directionPointId.isEmpty() || !dataRow.useData.isSelected() ) {
				continue;
			}
			inputDataList.add(directionPointId);
		}
    	
    	if( inputDataList.size() ==  1 ) {
    		FXHomeWindow.homeController.getInfoMessage("Hiányzó tájékozó irány", "Tájékozó irány adatok megadása szükséges.");
    		return inputDataList;
    	}
    	
    	return inputDataList;
    }
    
    private void displayDataForOrientation() {
    		standingPointY.setText(String.valueOf(measuredPillarDataController.orientation.getStandingPoint().getX_coord()));
    		standingPointX.setText(String.valueOf(measuredPillarDataController.orientation.getStandingPoint().getY_coord()));
    		for (Point orientationPoint : measuredPillarDataController.orientation.getOrientationPointList()) {
				dataRowList.get(measuredPillarDataController.orientation.getOrientationPointList().indexOf(orientationPoint))
				.directionPointY.setText(String.valueOf(orientationPoint.getX_coord()));
				dataRowList.get(measuredPillarDataController.orientation.getOrientationPointList().indexOf(orientationPoint))
				.directionPointX.setText(String.valueOf(orientationPoint.getY_coord()));
			}
    		for (OrientationData orientationData : measuredPillarDataController.orientation.orientationResultData) {
    			dataRowList.get(measuredPillarDataController.orientation.orientationResultData.indexOf(orientationData))
    			.azimuthAngelValue.setText(orientationData.getAzimuthAngelValue());
    			dataRowList.get(measuredPillarDataController.orientation.orientationResultData.indexOf(orientationData))
    			.distanceValue.setText(orientationData.getDistanceValue());
    			dataRowList.get(measuredPillarDataController.orientation.orientationResultData.indexOf(orientationData))
    			.directionAngelValue.setText(orientationData.getDirectionAngelValue());
    			dataRowList.get(measuredPillarDataController.orientation.orientationResultData.indexOf(orientationData))
    			.orientationAngelValue.setText(orientationData.getOrientationAngelValue());
    			dataRowList.get(measuredPillarDataController.orientation.orientationResultData.indexOf(orientationData))
    			.orientedDirectionValue.setText(orientationData.getOrientedDirectionValue());
		}	
    		mediumOrientationData.setText(measuredPillarDataController.orientation.getAveOrientation());
    }
    
    
    private void refreshDisplayerData() {
    	
    	for (OrientationData orientationData : measuredPillarDataController.orientation.orientationResultData) {
    		
    		for (OrientationDataRow dataRow : dataRowList) {
    			
    			if( orientationData.getDirectionPointId().equals(dataRow.directionPointId.getText()) ) {
    				dataRow.azimuthAngelValue.setText(orientationData.getAzimuthAngelValue());
    				dataRow.distanceValue.setText(orientationData.getDistanceValue());
    				dataRow.directionAngelValue.setText(orientationData.getDirectionAngelValue());
    				dataRow.orientationAngelValue.setText(orientationData.getOrientationAngelValue());
    				dataRow.orientedDirectionValue.setText(orientationData.getOrientedDirectionValue());
    			}
				
		}
    		
	}	
		mediumOrientationData.setText(measuredPillarDataController.orientation.getAveOrientation());
    	
    }
    
}
