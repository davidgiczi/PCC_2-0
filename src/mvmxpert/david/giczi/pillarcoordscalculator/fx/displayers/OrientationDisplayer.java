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
import mvmxpert.david.giczi.pillarcoordscalculator.utils.Location;

public class OrientationDisplayer {

	
	
	private TextField standingPointId;
	private TextField standingPointY;
	private TextField standingPointX; 
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
			layoutY += 30;
			pane.getChildren().add(dataRow);
			dataRowList.add(dataRow);
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
    	
    	HBox rightButtonBox = new HBox(15);
    	rightButtonBox.setLayoutY(390);
    	rightButtonBox.setLayoutX(480);
    	
    	Button calcButton = new Button("Számol");
    	calcButton.setFont(buttonFont);
    	calcButton.setPrefHeight(30);
    	calcButton.setCursor(Cursor.HAND);
    	calcButton.setOnAction( c ->{
    		
    		int option = FXHomeWindow.homeController.getYesNoMessage("Súlyegység választása", 
					"Igen: p=t [km], Nem: p=1.");
    		
    		if( option == 0 ) {
    			stage.setTitle("Tájékozás - [távolságfüggő]");
			}
    		else if( option == 1 )  {
    			stage.setTitle("Tájékozás - [egységsúly]");
    		}
    		
    	});
    	
    	TextField mediumOrientationData = new TextField();
    	mediumOrientationData.setEditable(false);
    	mediumOrientationData.setPromptText("Zk");
    	mediumOrientationData.setPrefWidth(90);
    	mediumOrientationData.setPrefHeight(30);
    	mediumOrientationData.setAlignment(Pos.CENTER);
    	mediumOrientationData.setStyle("-fx-text-fill: blue;");
    	mediumOrientationData.setFont(buttonFont);
  
    	Button loadButton = new Button("Beolvas");
    	loadButton.setPrefHeight(30);
    	loadButton.setFont(buttonFont);
    	loadButton.setLayoutX(210);
    	loadButton.setLayoutY(390);
    	loadButton.setCursor(Cursor.HAND);
    	loadButton.setOnAction( e -> {
    		List<String> inputData = getOrientationInputData();
    		if(  inputData.size() < 2 ) {
    			return;
    		}
    		measuredPillarDataController.calcOrientation(inputData);
    		loadDataForOrientation();
    	});
    	rightButtonBox.getChildren().addAll(calcButton, mediumOrientationData);
    	pane.getChildren().addAll(loadButton, rightButtonBox);
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
			if( directionPointId.isEmpty() ) {
				continue;
			}
			inputDataList.add(directionPointId);
		}
    	
    	if( inputDataList.size() ==  1 ) {
    		FXHomeWindow.homeController.getInfoMessage("Hiányzó tájékozó irány", "Tájékozó irányok számának megadása szükséges.");
    		return inputDataList;
    	}
    	
    	return inputDataList;
    }
    
    private void loadDataForOrientation() {
    	if( measuredPillarDataController.orientation != null && 
    			measuredPillarDataController.orientation.getStandingPoint() != null ) {
    		standingPointY.setText(String.valueOf(measuredPillarDataController.orientation.getStandingPoint().getX_coord()));
    		standingPointX.setText(String.valueOf(measuredPillarDataController.orientation.getStandingPoint().getY_coord()));
    	}
    }
    
}
