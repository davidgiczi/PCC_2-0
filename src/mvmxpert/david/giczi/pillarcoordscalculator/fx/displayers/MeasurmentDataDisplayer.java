package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.PLRFileProcess;

public class MeasurmentDataDisplayer {

	
	 	private final AnchorPane pane = new AnchorPane();
	    public MeasuredPillarDataController measuredPillarDataController;
	    private static final double MILLIMETER = 1000.0 / 225.0;
	    private static final Font BOLD = Font.font("Book Antiqua", FontWeight.BOLD, 16);
	    private VBox ROWS;
	    
	    public MeasurmentDataDisplayer(MeasuredPillarDataController measuredPillarDataController){
	        this.measuredPillarDataController = measuredPillarDataController;
	        this.ROWS = new VBox();
	        Stage stage = new Stage();
	        stage.initOwner(measuredPillarDataController.fxHomeWindow.homeStage);
	        stage.setOnCloseRequest(windowEvent -> {
	            
	        	if( measuredPillarDataController
	        			.getConfirmationAlert("Jegyzőkönyv mentése", "Kívánod a jegyzőkönyvet fájlba menteni?") ) {
	        		
	        		measuredPillarDataController.getInfoAlert(PLRFileProcess.MEAS_FILE_NAME, 
	        				"A mérési jegyzőkönyv mentve a(z)\n\n" +  PLRFileProcess.FOLDER_PATH + " mappába.");
	        	}
	        	
	        	
	        });
	        pane.setStyle("-fx-background-color: white");
	        ScrollPane scrollPane = getScrollPane(pane);
	        Scene scene = new Scene(scrollPane);
	        stage.setTitle(PLRFileProcess.FOLDER_PATH + "\\" + PLRFileProcess.MEAS_FILE_NAME);
	        stage.getIcons().add(new Image("/img/MVM.jpg"));
	        addContent();
	        stage.setMinWidth(1050);
	        stage.setMinHeight(750);
	        stage.setResizable(true);
	        stage.setMaximized(true);
	        stage.setScene(scene);
	        stage.show();
	    }
	    private ScrollPane getScrollPane(AnchorPane content){
	        ScrollPane scroller = new ScrollPane(content);
	        scroller.setStyle("-fx-background-color: white");
	        scroller.setFitToWidth(true);
	        scroller.setFitToHeight(true);
	        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	        return scroller;
	    }
	    
	    private void addContent() {
	    	addHeader();
	    	addMeasurmentDataRow();
	    	pane.getChildren().add(ROWS);
	    }
	    
	    
	    private void addMeasurmentDataRow() {
	    for (String rowData : measuredPillarDataController.measurmentData) {
	    	String[] data = rowData.split(";");
	    	MeasDataRow row = new MeasDataRow(true);
	    	row.getRowNumber().setText(String.valueOf((measuredPillarDataController.measurmentData.indexOf(rowData) + 1)));
	    	if( data.length == 17 ) {
	    		row.getStandingPointNameField().setText(data[0]);
	    		row.getStandingPointYField().setText(data[1]);
	    		row.getStandingPointXField().setText(data[2]);
	    		row.getStandingPointZField().setText(data[3]);
	    		row.getTotalStationHeightField().setText(data[4]);
	    		row.getDirectionPointNameField().setText(data[5]);
	    		row.getDirectionPointSignField().setText(data[6]);
	    		row.getDirectionPointYField().setText(data[7]);
	    		row.getDirectionPointXField().setText(data[8]);
	    		row.getDirectionPointZField().setText(data[9]);
	    		row.getHorizontalAngleField().setText(data[10]);
	    		row.getVerticalAngleField().setText(data[11]);
	    		row.getHorizontalDistanceField().setText(data[12]);
	    		row.getDirectionPointSignHeightField().setText(data[13]);
	    		row.getDateTimeField().setText(data[15]);
	    	}
	    	else if( data.length == 5) {
	    		
	    	}
	    	ROWS.getChildren().add(row);
		}	
}
	    
	    
	    private void addHeader() {
	    	 HBox header = new HBox();
	    	 TextField rowNumber = new TextField("Ssz.");
	    	 rowNumber.setMaxWidth(11 * MILLIMETER);
	    	 HBox.setHgrow(rowNumber, Priority.ALWAYS);
	         rowNumber.setFont(BOLD);
	         rowNumber.setAlignment(Pos.CENTER);
	         rowNumber.setEditable(false);
	         rowNumber.setStyle("-fx-background-color: #D8F2FF;");
	         rowNumber.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(rowNumber);
	         TextField standingPointName = new TextField("Álláspont");
	         standingPointName.setMaxWidth(21 * MILLIMETER);
	    	 HBox.setHgrow(standingPointName, Priority.ALWAYS);
	         standingPointName.setFont(BOLD);
	         standingPointName.setAlignment(Pos.CENTER);
	         standingPointName.setEditable(false);
	         standingPointName.setStyle("-fx-background-color: #D8F2FF;");
	         standingPointName.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(standingPointName);
	         TextField standingPointY = new TextField("Y");
	         standingPointY.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(standingPointY, Priority.ALWAYS);
	         standingPointY.setFont(BOLD);
	         standingPointY.setAlignment(Pos.CENTER);
	         standingPointY.setStyle("-fx-background-color: #D8F2FF;");
	         standingPointY.setPrefHeight(12 * MILLIMETER);
	         standingPointY.setEditable(false);
	         header.getChildren().add(standingPointY);
	         TextField standingPointX = new TextField("X");
	         standingPointX.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(standingPointX, Priority.ALWAYS);
	         standingPointX.setFont(BOLD);
	         standingPointX.setAlignment(Pos.CENTER);
	         standingPointX.setStyle("-fx-background-color: #D8F2FF;");
	         standingPointX.setPrefHeight(12 * MILLIMETER);
	         standingPointX.setEditable(false);
	         header.getChildren().add(standingPointX);
	         TextField standingPointZ = new TextField("H");
	         standingPointZ.setMaxWidth(15 * MILLIMETER);
	    	 HBox.setHgrow(standingPointZ, Priority.ALWAYS);
	         standingPointZ.setFont(BOLD);
	         standingPointZ.setAlignment(Pos.CENTER);
	         standingPointZ.setStyle("-fx-background-color: #D8F2FF;");
	         standingPointZ.setPrefHeight(12 * MILLIMETER);
	         standingPointZ.setEditable(false);
	         header.getChildren().add(standingPointZ);
	         TextField totalStationHeight = new TextField("Mmag.");
	         totalStationHeight.setMaxWidth(16 * MILLIMETER);
	         HBox.setHgrow(totalStationHeight, Priority.ALWAYS);
	         totalStationHeight.setFont(BOLD);
	         totalStationHeight.setEditable(false);
	         totalStationHeight.setAlignment(Pos.CENTER);
	         totalStationHeight.setStyle("-fx-background-color: #D8F2FF;");
	         totalStationHeight.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(totalStationHeight);
	         TextField directionPointName = new TextField("MértP.");
	         directionPointName.setMaxWidth(15 * MILLIMETER);
	    	 HBox.setHgrow(directionPointName, Priority.ALWAYS);
	         directionPointName.setFont(BOLD);
	         directionPointName.setEditable(false);
	         directionPointName.setAlignment(Pos.CENTER);
	         directionPointName.setStyle("-fx-background-color: #D8F2FF;");
	         directionPointName.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(directionPointName);
	         TextField directionPointY = new TextField("Y");
	         directionPointY.setMaxWidth(20 * MILLIMETER);
	         HBox.setHgrow(directionPointY, Priority.ALWAYS);
	         directionPointY.setFont(BOLD);
	         directionPointY.setAlignment(Pos.CENTER);
	         directionPointY.setStyle("-fx-background-color: #D8F2FF;");
	         directionPointY.setPrefHeight(12 * MILLIMETER);
	         directionPointY.setEditable(false);
	         header.getChildren().add(directionPointY);
	         TextField directionPointX = new TextField("X");
	         directionPointX.setMaxWidth(20 * MILLIMETER);
	         HBox.setHgrow(directionPointX, Priority.ALWAYS);
	         directionPointX.setFont(BOLD);
	         directionPointX.setAlignment(Pos.CENTER);
	         directionPointX.setStyle("-fx-background-color: #D8F2FF;");
	         directionPointX.setPrefHeight(12 * MILLIMETER);
	         directionPointX.setEditable(false);
	         header.getChildren().add(directionPointX);
	         TextField directionPointZ = new TextField("H");
	         directionPointZ.setMaxWidth(15 * MILLIMETER);
	         HBox.setHgrow(directionPointZ, Priority.ALWAYS);
	         directionPointZ.setFont(BOLD);
	         directionPointZ.setAlignment(Pos.CENTER);
	         directionPointZ.setStyle("-fx-background-color: #D8F2FF;");
	         directionPointZ.setPrefHeight(12 * MILLIMETER);
	         directionPointZ.setEditable(false);
	         header.getChildren().add(directionPointZ);
	         TextField directionPointSign = new TextField("Jellege");  
	         directionPointSign.setMaxWidth(40 * MILLIMETER);
	    	 HBox.setHgrow(directionPointSign, Priority.ALWAYS);
	         directionPointSign.setFont(BOLD);
	         directionPointSign.setAlignment(Pos.CENTER);
	         directionPointSign.setStyle("-fx-background-color: #D8F2FF;");
	         directionPointSign.setPrefHeight(12 * MILLIMETER);
	         directionPointSign.setEditable(false);
	         header.getChildren().add(directionPointSign);
	         TextField horizontalAngle = new TextField("Irányérték");
	         horizontalAngle.setMaxWidth(25 * MILLIMETER);
	    	 HBox.setHgrow(horizontalAngle, Priority.ALWAYS);
	         horizontalAngle.setFont(BOLD);
	         horizontalAngle.setAlignment(Pos.CENTER);
	         horizontalAngle.setStyle("-fx-background-color: #D8F2FF;");
	         horizontalAngle.setPrefHeight(12 * MILLIMETER);
	         horizontalAngle.setEditable(false);
	         header.getChildren().add(horizontalAngle);
	         TextField verticalAngle = new TextField("Zenitszög");
	         verticalAngle.setMaxWidth(25 * MILLIMETER);
	    	 HBox.setHgrow(verticalAngle, Priority.ALWAYS);
	         verticalAngle.setFont(BOLD);
	         verticalAngle.setAlignment(Pos.CENTER);
	         verticalAngle.setStyle("-fx-background-color: #D8F2FF;");
	         verticalAngle.setPrefHeight(12 * MILLIMETER);
	         verticalAngle.setEditable(false);
	         header.getChildren().add(verticalAngle);
	         TextField horizontalDistance = new TextField("Távolság");
	         horizontalDistance.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(horizontalDistance, Priority.ALWAYS);
	         horizontalDistance.setFont(BOLD);
	         horizontalDistance.setAlignment(Pos.CENTER);
	         horizontalDistance.setStyle("-fx-background-color: #D8F2FF;");
	         horizontalDistance.setPrefHeight(12 * MILLIMETER);
	         horizontalDistance.setEditable(false);
	         header.getChildren().add(horizontalDistance);
	         TextField directionPointSignHeight = new TextField("Jmag.");
	         directionPointSignHeight.setMaxWidth(16 * MILLIMETER);
	    	 HBox.setHgrow(directionPointSignHeight, Priority.ALWAYS);
	         directionPointSignHeight.setFont(BOLD);
	         directionPointSignHeight.setAlignment(Pos.CENTER);
	         directionPointSignHeight.setStyle("-fx-background-color: #D8F2FF;");
	         directionPointSignHeight.setPrefHeight(12 * MILLIMETER);
	         directionPointSignHeight.setEditable(false);
	         header.getChildren().add(directionPointSignHeight);
	         TextField measurementDateTime = new TextField("Dátum");
	         measurementDateTime.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(measurementDateTime, Priority.ALWAYS);
	         measurementDateTime.setFont(BOLD);
	         measurementDateTime.setAlignment(Pos.CENTER);
	         measurementDateTime.setStyle("-fx-background-color: #D8F2FF;");
	         measurementDateTime.setPrefHeight(12 * MILLIMETER);
	         measurementDateTime.setEditable(false);
	         header.getChildren().add(measurementDateTime);
	         TextField theoreticalPointName = new TextField("Elméleti pont");
	         theoreticalPointName.setMaxWidth(40 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointName, Priority.ALWAYS);
	         theoreticalPointName.setFont(BOLD);
	         theoreticalPointName.setAlignment(Pos.CENTER);
	         theoreticalPointName.setStyle("-fx-background-color: #D8F2FF;");
	         theoreticalPointName.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointName.setEditable(false);
	         header.getChildren().add(theoreticalPointName);
	         TextField theoreticalPointY = new TextField("Y");
	         theoreticalPointY.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointY, Priority.ALWAYS);
	         theoreticalPointY.setFont(BOLD);
	         theoreticalPointY.setAlignment(Pos.CENTER);
	         theoreticalPointY.setStyle("-fx-background-color: #D8F2FF;");
	         theoreticalPointY.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointY.setEditable(false);
	         header.getChildren().add(theoreticalPointY);
	         TextField theoreticalPointX = new TextField("X");
	         theoreticalPointX.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointX, Priority.ALWAYS);
	         theoreticalPointX.setFont(BOLD);
	         theoreticalPointX.setAlignment(Pos.CENTER);
	         theoreticalPointX.setStyle("-fx-background-color: #D8F2FF;");
	         theoreticalPointX.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointX.setEditable(false);
	         header.getChildren().add(theoreticalPointX);
	         TextField theoreticalPointZ = new TextField("H");
	         theoreticalPointZ.setMaxWidth(15 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointZ, Priority.ALWAYS);
	         theoreticalPointZ.setFont(BOLD);
	         theoreticalPointZ.setAlignment(Pos.CENTER);
	         theoreticalPointZ.setStyle("-fx-background-color: #D8F2FF;");
	         theoreticalPointZ.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointZ.setEditable(false);
	         header.getChildren().add(theoreticalPointZ);
	         TextField theoreticalPointSign = new TextField("Jellege");
	         theoreticalPointSign.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointSign, Priority.ALWAYS);
	         theoreticalPointSign.setFont(BOLD);
	         theoreticalPointSign.setAlignment(Pos.CENTER);
	         theoreticalPointSign.setStyle("-fx-background-color: #D8F2FF;");
	         theoreticalPointSign.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointSign.setEditable(false);
	         header.getChildren().add(theoreticalPointSign);
	         ROWS.getChildren().add(header);
	    }
	
}
