package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;


import java.util.Arrays;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Orientation;
import mvmxpert.david.giczi.pillarcoordscalculator.service.OrientationData;

public class OrientationDisplayer {

	
	private final AnchorPane pane = new AnchorPane();
    public MeasuredPillarDataController measuredPillarDataController;
    private final Font bigFont = Font.font("Arial", FontWeight.BOLD, 14);
    private final Font smallFont = Font.font("Arial", FontWeight.BOLD, 12);
    private static final List<String> HEADER = 
    		Arrays.asList("Irányzott Psz.", "Y", "X", "Irányszög", "Távolság", "Irányérték", "Táj.szög", "Táj.irányérték");
    
    
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
        stage.setX(100);
        stage.setY(100);
        stage.setWidth(850);
        stage.setHeight(500);
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
    	addButton();
    }
    
    private void addDataTable() {
    	addHeader();
    	addDataRow(10);
    }
    
    private void addHeader() {
    	OrientationDataRow header = new OrientationDataRow(true);
    	header.setLayoutX(60);
    	header.setLayoutY(50);
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
    	
    	int layoutX = 60;
    	int layoutY = 80;
    	
    	for (int i = 0; i < pcs; i++) {
			OrientationDataRow dataRow = new OrientationDataRow(false);
			dataRow.setLayoutX(layoutX);
			dataRow.setLayoutY(layoutY);
			layoutY += 30;
			pane.getChildren().add(dataRow);
		}
    	
    }
    
    private void addStandingPointData() {
    	
    	HBox standingPointBox = new HBox();
    	standingPointBox.setLayoutX(60);
    	standingPointBox.setLayoutY(10);
    	
    	TextField standingPointId = new TextField();

    	standingPointId.setPromptText("Álláspont Psz.");
    	standingPointId.setPrefWidth(85);
    	standingPointId.setPrefHeight(30);
    	standingPointId.setStyle("-fx-alignment: CENTER;");
    	standingPointId.setStyle("-fx-text-fill: red;");
    	standingPointId.setAlignment(Pos.CENTER);
    	standingPointId.setCursor(Cursor.HAND);
    	
    	TextField standingPointY = new TextField();

    	standingPointY.setPromptText("Y");
    	standingPointY.setPrefWidth(85);
    	standingPointY.setPrefHeight(30);
    	standingPointY.setStyle("-fx-alignment: CENTER;");
    	standingPointY.setStyle("-fx-text-fill: red;");
    	standingPointY.setAlignment(Pos.CENTER);
    	standingPointY.setEditable(false);
    	
    	TextField standingPointX = new TextField();

    	standingPointX.setPromptText("X");
    	standingPointX.setPrefWidth(85);
    	standingPointX.setPrefHeight(30);
    	standingPointX.setStyle("-fx-alignment: CENTER;");
    	standingPointX.setStyle("-fx-text-fill: red;");
    	standingPointX.setAlignment(Pos.CENTER);
    	standingPointX.setEditable(false);

    	standingPointBox.getChildren().addAll(standingPointId, standingPointY, standingPointX);
    	pane.getChildren().addAll(standingPointBox);
    }
    
    
    private void addButton() {
    	HBox bottomButtonBox = new HBox(80);

    	Button loadButton = new Button("Beolvas");
    	Button counterButton = new Button("Számol");
  
    	loadButton.setFont(bigFont);
    	counterButton.setFont(bigFont);
    	loadButton.setCursor(Cursor.HAND);
    	counterButton.setCursor(Cursor.HAND);
    	
    	bottomButtonBox.setAlignment(Pos.CENTER);
    	bottomButtonBox.setLayoutY(410);
    	AnchorPane.setLeftAnchor(bottomButtonBox, 100.0);
    	AnchorPane.setRightAnchor(bottomButtonBox, 100.0);

    	bottomButtonBox.getChildren().addAll(
    	    loadButton, counterButton);
    	pane.getChildren().add(bottomButtonBox);
    }
    
}
