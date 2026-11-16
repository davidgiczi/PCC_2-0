package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;


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

public class OrientationDisplayer {

	
	private final AnchorPane pane = new AnchorPane();
    public MeasuredPillarDataController measuredPillarDataController;
    private final Font bigFont = Font.font("Arial", FontWeight.BOLD, 14);
    private final Font smallFont = Font.font("Arial", FontWeight.BOLD, 12);
    private static final List<String> HEADER = 
    		Arrays.asList("Táj.pont psz.", "Y", "X", "Irányszög", "Távolság", "Irányérték", "Táj.szög", "Táj.irányért.");
    private static int LAYOUT_X = 20;
    private static int LAYOUT_Y = 60;
    
    
    public OrientationDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        Stage stage = new Stage();
        stage.initOwner(measuredPillarDataController.fxHomeWindow.homeStage);
        stage.setOnCloseRequest(windowEvent -> {
        });
        pane.setStyle("-fx-background-color: white");
        ScrollPane scrollPane = getScrollPane(pane);
        addContent();
        Scene scene = new Scene(scrollPane);
        stage.setTitle("Tájékozás");
        stage.getIcons().add(new Image("/img/MVM.jpg"));
        stage.setX(0);
        stage.setY(100);
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
    	addButtonHBox();
    }
    
    private void addDataTable() {
    	addHeader();
    	addDataRow(10);
    }
    
    private void addHeader() {
    	OrientationDataRow header = new OrientationDataRow(true);
    	header.setLayoutX(LAYOUT_X);
    	header.setLayoutY(LAYOUT_Y - 10);
    	header.directionPointId.setFont(smallFont);
    	header.directionPointId.setEditable(false);
    	header.directionPointId.setText(HEADER.get(0));
    	header.directionPointY.setFont(smallFont);
    	header.directionPointY.setEditable(false);
    	header.directionPointY.setText(HEADER.get(1));
    	header.directionPointY.setFont(smallFont);
    	header.directionPointX.setFont(smallFont);
    	header.directionPointX.setEditable(false);
    	header.directionPointX.setText(HEADER.get(2));
    	header.directionPointX.setFont(smallFont);
    	header.azimuthAngelValue.setFont(smallFont);
    	header.azimuthAngelValue.setEditable(false);
    	header.azimuthAngelValue.setText(HEADER.get(3));
    	header.azimuthAngelValue.setFont(smallFont);
    	header.distanceValue.setFont(smallFont);
    	header.distanceValue.setEditable(false);
    	header.distanceValue.setText(HEADER.get(4));
    	header.distanceValue.setFont(smallFont);
    	header.directionAngelValue.setFont(smallFont);
    	header.directionAngelValue.setEditable(false);
    	header.directionAngelValue.setText(HEADER.get(5));
    	header.directionAngelValue.setFont(smallFont);
    	header.orientationAngelValue.setFont(smallFont);
    	header.orientationAngelValue.setEditable(false);
    	header.orientationAngelValue.setText(HEADER.get(6));
    	header.orientationAngelValue.setFont(smallFont);
    	header.orientedDirectionValue.setFont(smallFont);
    	header.orientedDirectionValue.setEditable(false);
    	header.orientedDirectionValue.setText(HEADER.get(7));
    	header.orientedDirectionValue.setFont(smallFont);
    	pane.getChildren().add(header);
    }
    
    private void addDataRow(int pcs) {
    	
    	int layoutY = 80;
    	
    	for (int i = 0; i < pcs; i++) {
			OrientationDataRow dataRow = new OrientationDataRow(false);
			dataRow.setLayoutX(LAYOUT_X);
			dataRow.setLayoutY(layoutY);
			layoutY += 30;
			pane.getChildren().add(dataRow);
		}
    	
    }
    
    private void addStandingPointData() {
    	
    	int width = 90;
    	int height = 30;
    	
    	HBox standingPointBox = new HBox();
    	standingPointBox.setLayoutX(LAYOUT_X);
    	standingPointBox.setLayoutY(10);
    	
    	TextField standingPointId = new TextField();

    	standingPointId.setPromptText("Álláspont Psz.");
    	standingPointId.setPrefWidth(width);
    	standingPointId.setPrefHeight(height);
    	standingPointId.setStyle("-fx-alignment: CENTER;");
    	standingPointId.setStyle("-fx-text-fill: red;");
    	standingPointId.setAlignment(Pos.CENTER);
    	standingPointId.setCursor(Cursor.HAND);
    	
    	TextField standingPointY = new TextField();

    	standingPointY.setPromptText("Y");
    	standingPointY.setPrefWidth(width);
    	standingPointY.setPrefHeight(height);
    	standingPointY.setStyle("-fx-alignment: CENTER;");
    	standingPointY.setStyle("-fx-text-fill: red;");
    	standingPointY.setAlignment(Pos.CENTER);
    	standingPointY.setEditable(false);
    	
    	TextField standingPointX = new TextField();

    	standingPointX.setPromptText("X");
    	standingPointX.setPrefWidth(width);
    	standingPointX.setPrefHeight(height);
    	standingPointX.setStyle("-fx-alignment: CENTER;");
    	standingPointX.setStyle("-fx-text-fill: red;");
    	standingPointX.setAlignment(Pos.CENTER);
    	standingPointX.setEditable(false);

    	standingPointBox.getChildren().addAll(standingPointId, standingPointY, standingPointX);
    	pane.getChildren().addAll(standingPointBox);
    }
    
    
    private void addButtonHBox() {
    	
    	HBox rightButtonBox = new HBox(15);
    	rightButtonBox.setLayoutY(390);
    	rightButtonBox.setLayoutX(480);
    	
    	Button calcButton = new Button("Számol");
    	calcButton.setFont(bigFont);
    	calcButton.setPrefHeight(30);
    	calcButton.setCursor(Cursor.HAND);
    	
    	TextField mediumOrientationData = new TextField();
    	mediumOrientationData.setEditable(true);
    	mediumOrientationData.setPromptText("Zk");
    	mediumOrientationData.setPrefWidth(90);
    	mediumOrientationData.setPrefHeight(30);
    	mediumOrientationData.setAlignment(Pos.CENTER);
    	mediumOrientationData.setStyle("-fx-text-fill: blue;");
    	mediumOrientationData.setFont(smallFont);
  
    	Button loadButton = new Button("Beolvas");
    	loadButton.setPrefHeight(30);
    	loadButton.setFont(bigFont);
    	loadButton.setLayoutX(210);
    	loadButton.setLayoutY(390);
    	loadButton.setCursor(Cursor.HAND);
    	
    	rightButtonBox.getChildren().addAll(calcButton, mediumOrientationData);
    	pane.getChildren().addAll(loadButton, rightButtonBox);
    }
    
}
