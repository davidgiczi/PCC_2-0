package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
import mvmxpert.david.giczi.pillarcoordscalculator.service.RowData;
import mvmxpert.david.giczi.pillarcoordscalculator.service.TheoreticalPointData;

public class MeasurmentDataDisplayer {

	
	 	private final AnchorPane pane = new AnchorPane();
	    public MeasuredPillarDataController measuredPillarDataController;
	    private static final double MILLIMETER = 1000.0 / 225.0;
	    private static final Font BOLD = Font.font("Book Antiqua", FontWeight.BOLD, 16);
	    private VBox ROWS;
	    private List<RowData> standingPointDataStore;
	    private List<TheoreticalPointData> theoreticalPointDataStore;
	    private int rowNumber = 1;
	    
	    public MeasurmentDataDisplayer(MeasuredPillarDataController measuredPillarDataController){
	        this.measuredPillarDataController = measuredPillarDataController;
	        this.ROWS = new VBox();
	        Stage stage = new Stage();
	        if( !addContent() ) {
		    	   return;
		       }
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
	    
	   private boolean addContent() {
	    	addHeader();
	    if(	addMeasurmentDataRow() ) {
	    	pane.getChildren().add(ROWS);
	    	return true;
	    }
	    	return false;
	  }
	    
	    
	    private boolean addMeasurmentDataRow() {
	    	
	    	createRowDataStore();
	    	parseTheoreticalPointData();
	    	if( standingPointDataStore == null || standingPointDataStore.isEmpty() ) {
	    		addTheoreticalPointData();
	    		return false;
	    	}
	    	
	    	for (RowData standingPointData : standingPointDataStore) {
	    		MeasDataRow standingPointRow = new MeasDataRow(true, true, true, true, true, 
	    				true, false, false, false, false, false, false, false, false, false,
	    				false, false, false, false, false, false);
	    		standingPointRow.getRowNumber().setText(standingPointData.getRowNumber());
	    		standingPointRow.getStandingPointNameField().setText(standingPointData.getStandingPointName());
	    		standingPointRow.getStandingPointYField().setText(standingPointData.getStandingPointY());
	    		standingPointRow.getStandingPointXField().setText(standingPointData.getStandingPointX());
	    		standingPointRow.getStandingPointZField().setText(standingPointData.getStandingPointZ());
	    		standingPointRow.getTotalStationHeightField().setText(standingPointData.getTotalStationHeight());
	    		standingPointRow.setStyle("-fx-background-color:#FFEFEA");
	    		ROWS.getChildren().add(standingPointRow);
	    		
	    		for (RowData measuredPointData : standingPointData.getMeasuredPointDataStore()) {
	    			
	    			if( standingPointData.getStandingPointName().equals(measuredPointData.getStandingPointName())) {
	    				
	    			MeasDataRow measuredPointRow = new MeasDataRow(true, false, false, false, false, 
		    				false, true, true, true, true, true, true, true, true, true,
		    				true, true, true, true, true, true);
	    			measuredPointRow.getRowNumber().setText(measuredPointData.getRowNumber());
	    			measuredPointRow.getMeasuredPointNameField().setText(measuredPointData.getMeasuredPointName());
	    			measuredPointRow.getMeasuredPointYField().setText(measuredPointData.getMeasuredPointY());
	    			measuredPointRow.getMeasuredPointXField().setText(measuredPointData.getMeasuredPointX());
	    			measuredPointRow.getMeasuredPointZField().setText(measuredPointData.getMeasuredPointZ());
	    			measuredPointRow.getMeasuredPointSignField().setText(measuredPointData.getMeasuredPointSign());
	    			measuredPointRow.getHorizontalAngleField().setText(measuredPointData.getHorizontalAngle());
	    			measuredPointRow.getVerticalAngleField().setText(measuredPointData.getVerticalAngle());
	    			measuredPointRow.getHorizontalDistanceField().setText(measuredPointData.getHorizontalDistance());
	    			measuredPointRow.getMeasuredPointSignHeightField().setText(measuredPointData.getMeasuredPointSignHeight());
	    			measuredPointRow.getDateTimeField().setText(measuredPointData.getDate());
	    			Tooltip time = new Tooltip(measuredPointData.getDate() + " " + measuredPointData.getTime());
	    			Tooltip.install(measuredPointRow.getDateTimeField(), time);
	    			if( measuredPointData.getTheoreticalPointData() != null ) {
	    				measuredPointRow.getTheoreticalPointNameField()
	    				.setText(measuredPointData.getTheoreticalPointData().getTheoreticalPointName());
	    				measuredPointRow.getTheoreticalPointYField()
	    				.setText(measuredPointData.getTheoreticalPointData().getTheoreticalPointY());
	    				measuredPointRow.getTheoreticalPointXField()
	    				.setText(measuredPointData.getTheoreticalPointData().getTheoreticalPointX());
	    				measuredPointRow.getTheoreticalPointZField()
	    				.setText(measuredPointData.getTheoreticalPointData().getTheoreticalPointZ());
	    				measuredPointRow.getTheoreticalPointSignNameField()
	    				.setText(measuredPointData.getTheoreticalPointData().getTheoreticalPointSignName());
	    			}
	    			ROWS.getChildren().add(measuredPointRow);
	    			}
				}
	    	}
	    	
	    		addTheoreticalPointData();
	    	
	    		return true;
		}
	    
	  private void addTheoreticalPointData() {
		  
		  if( theoreticalPointDataStore == null || theoreticalPointDataStore.isEmpty() ) {
			  measuredPillarDataController.getInfoAlert("Az adatok nem jeleníthetők meg", PLRFileProcess.MEAS_FILE_NAME +
					  "\n\nA beolvasott fájl tartalma nem megfelelő.");
			  return;
		  }
	    	
	    	for (TheoreticalPointData theoreticalPointData : theoreticalPointDataStore) {
				if( !theoreticalPointData.isDeleted() ) {
					MeasDataRow theoreticalPointRow = new MeasDataRow(true, false, false, false, false, 
		    				false, false, false, false, false, false, false, false, false, false,
		    				false, true, true, true, true, true);
					theoreticalPointRow.getRowNumber().setText(String.valueOf(rowNumber));
					rowNumber++;
					theoreticalPointRow.getTheoreticalPointNameField().setText(theoreticalPointData.getTheoreticalPointName());
					theoreticalPointRow.getTheoreticalPointYField().setText(theoreticalPointData.getTheoreticalPointY());
					theoreticalPointRow.getTheoreticalPointXField().setText(theoreticalPointData.getTheoreticalPointX());
					theoreticalPointRow.getTheoreticalPointZField().setText(theoreticalPointData.getTheoreticalPointZ());
					theoreticalPointRow.getTheoreticalPointSignNameField().setText(theoreticalPointData.getTheoreticalPointSignName());
					ROWS.getChildren().add(theoreticalPointRow);
				}
			}
	    	
	    }
	    
	    private void createRowDataStore() {
	    	
	    	List<String> measData = measuredPillarDataController.measurmentData;
	    	String[] rowData =  measData.get(0).split(";");
	    	if( rowData.length != 17 && rowData.length != 5 ) {
	    		return;
	    	}
	    	theoreticalPointDataStore = new ArrayList<>();
	    	String standingPointId = rowData[0];
	    	RowData standingPointRow = new RowData();
	    	RowData measuredPointRow = new RowData();
	    	
	    	if( rowData.length == 17 ) {
	    	standingPointDataStore = new ArrayList<>();
	    	standingPointRow.setRowNumber("1");
	    	standingPointRow.setStandingPointName(rowData[0]);
	    	standingPointRow.setStandingPointY(rowData[1]);
	    	standingPointRow.setStandingPointX(rowData[2]);
	    	standingPointRow.setStandingPointZ(rowData[3]);
	    	standingPointRow.setTotalStationHeight(rowData[4]);
	    	standingPointDataStore.add(standingPointRow);
	    	rowNumber = 2;
	    	}
	    	
	    	for (String row : measData) {
				rowData = row.split(";");
				
				if( rowData.length == 17 && standingPointId.equals(rowData[0]) ) {
					measuredPointRow = new RowData();
					measuredPointRow.setRowNumber(String.valueOf(rowNumber));
					rowNumber++;
			    	measuredPointRow.setStandingPointName(standingPointId);
			    	measuredPointRow.setMeasuredPointName(rowData[5]);
			    	measuredPointRow.setMeasuredPointSign(rowData[6]);
			    	measuredPointRow.setMeasuredPointY(rowData[7]);
			    	measuredPointRow.setMeasuredPointX(rowData[8]);
			    	measuredPointRow.setMeasuredPointZ(rowData[9]);
			    	measuredPointRow.setHorizontalAngle(rowData[10]);
			    	measuredPointRow.setVerticalAngle(rowData[11]);
			    	measuredPointRow.setHorizontalDistance(rowData[12]);
			    	measuredPointRow.setMeasuredPointSignHeight(rowData[13]);
			    	measuredPointRow.setDate(rowData[15]);
			    	measuredPointRow.setTime(rowData[16]);
			    	standingPointRow.getMeasuredPointDataStore().add(measuredPointRow);
				}
				else if( rowData.length == 17 && !standingPointId.equals(rowData[0])) {
					standingPointRow = new RowData();
					standingPointRow.setRowNumber(String.valueOf(rowNumber));
					rowNumber++;
			    	standingPointRow.setStandingPointName(rowData[0]);
			    	standingPointRow.setStandingPointY(rowData[1]);
			    	standingPointRow.setStandingPointX(rowData[2]);
			    	standingPointRow.setStandingPointZ(rowData[3]);
			    	standingPointRow.setTotalStationHeight(rowData[4]);
			    	standingPointDataStore.add(standingPointRow);
			    	measuredPointRow = new RowData();
			    	measuredPointRow.setRowNumber(String.valueOf(rowNumber));
					rowNumber++;
			    	measuredPointRow.setStandingPointName(rowData[0]);
			    	measuredPointRow.setMeasuredPointName(rowData[5]);
			    	measuredPointRow.setMeasuredPointSign(rowData[6]);
			    	measuredPointRow.setMeasuredPointY(rowData[7]);
			    	measuredPointRow.setMeasuredPointX(rowData[8]);
			    	measuredPointRow.setMeasuredPointZ(rowData[9]);
			    	measuredPointRow.setHorizontalAngle(rowData[10]);
			    	measuredPointRow.setVerticalAngle(rowData[11]);
			    	measuredPointRow.setHorizontalDistance(rowData[12]);
			    	measuredPointRow.setMeasuredPointSignHeight(rowData[13]);
			    	measuredPointRow.setDate(rowData[15]);
			    	measuredPointRow.setTime(rowData[16]);
			    	standingPointRow.getMeasuredPointDataStore().add(measuredPointRow);
					standingPointId = rowData[0];
				}
				else if( rowData.length == 5 ) {
					TheoreticalPointData theoretical = new TheoreticalPointData();
					theoretical.setTheoreticalPointName(rowData[0]);
					theoretical.setTheoreticalPointY(rowData[1]);
					theoretical.setTheoreticalPointX(rowData[2]);
					theoretical.setTheoreticalPointZ(rowData[3]);
					theoretical.setTheoreticalPointSignName(rowData[4]);
					theoreticalPointDataStore.add(theoretical);
					
				}	
			}
	    }
	      
	    private void parseTheoreticalPointData() {
	    	
	    	if( theoreticalPointDataStore == null || theoreticalPointDataStore.isEmpty() ) {
	    		return;
	    	}
	    	
	    	for(int i = 0; i < theoreticalPointDataStore.size(); i++) {
	    		
	    		for (RowData standingPoint : standingPointDataStore) {
	    			
					for(RowData measuredPoint : standingPoint.getMeasuredPointDataStore() ) {
						
						if( theoreticalPointDataStore.get(i).getTheoreticalPointName()
								.equals( measuredPoint.getMeasuredPointSign())) {
							measuredPoint.setTheoreticalPointData(theoreticalPointDataStore.get(i));
							theoreticalPointDataStore.get(i).setDeleted(true);
						}			
					}
				}
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
	         rowNumber.setStyle("-fx-background-color: #FFEFEA;");
	         rowNumber.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(rowNumber);
	         TextField standingPointName = new TextField("Álláspont");
	         standingPointName.setMaxWidth(21 * MILLIMETER);
	    	 HBox.setHgrow(standingPointName, Priority.ALWAYS);
	         standingPointName.setFont(BOLD);
	         standingPointName.setAlignment(Pos.CENTER);
	         standingPointName.setEditable(false);
	         standingPointName.setStyle("-fx-background-color: #FFEFEA;");
	         standingPointName.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(standingPointName);
	         TextField standingPointY = new TextField("Y");
	         standingPointY.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(standingPointY, Priority.ALWAYS);
	         standingPointY.setFont(BOLD);
	         standingPointY.setAlignment(Pos.CENTER);
	         standingPointY.setStyle("-fx-background-color: #FFEFEA;");
	         standingPointY.setPrefHeight(12 * MILLIMETER);
	         standingPointY.setEditable(false);
	         header.getChildren().add(standingPointY);
	         TextField standingPointX = new TextField("X");
	         standingPointX.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(standingPointX, Priority.ALWAYS);
	         standingPointX.setFont(BOLD);
	         standingPointX.setAlignment(Pos.CENTER);
	         standingPointX.setStyle("-fx-background-color: #FFEFEA;");
	         standingPointX.setPrefHeight(12 * MILLIMETER);
	         standingPointX.setEditable(false);
	         header.getChildren().add(standingPointX);
	         TextField standingPointZ = new TextField("H");
	         standingPointZ.setMaxWidth(15 * MILLIMETER);
	    	 HBox.setHgrow(standingPointZ, Priority.ALWAYS);
	         standingPointZ.setFont(BOLD);
	         standingPointZ.setAlignment(Pos.CENTER);
	         standingPointZ.setStyle("-fx-background-color: #FFEFEA;");
	         standingPointZ.setPrefHeight(12 * MILLIMETER);
	         standingPointZ.setEditable(false);
	         header.getChildren().add(standingPointZ);
	         TextField totalStationHeight = new TextField("Mmag.");
	         totalStationHeight.setMaxWidth(16 * MILLIMETER);
	         HBox.setHgrow(totalStationHeight, Priority.ALWAYS);
	         totalStationHeight.setFont(BOLD);
	         totalStationHeight.setEditable(false);
	         totalStationHeight.setAlignment(Pos.CENTER);
	         totalStationHeight.setStyle("-fx-background-color: #FFEFEA;");
	         totalStationHeight.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(totalStationHeight);
	         TextField directionPointName = new TextField("MértP.");
	         directionPointName.setMaxWidth(15 * MILLIMETER);
	    	 HBox.setHgrow(directionPointName, Priority.ALWAYS);
	         directionPointName.setFont(BOLD);
	         directionPointName.setEditable(false);
	         directionPointName.setAlignment(Pos.CENTER);
	         directionPointName.setStyle("-fx-background-color: #FFEFEA;");
	         directionPointName.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(directionPointName);
	         TextField directionPointY = new TextField("Y");
	         directionPointY.setMaxWidth(20 * MILLIMETER);
	         HBox.setHgrow(directionPointY, Priority.ALWAYS);
	         directionPointY.setFont(BOLD);
	         directionPointY.setAlignment(Pos.CENTER);
	         directionPointY.setStyle("-fx-background-color: #FFEFEA;");
	         directionPointY.setPrefHeight(12 * MILLIMETER);
	         directionPointY.setEditable(false);
	         header.getChildren().add(directionPointY);
	         TextField directionPointX = new TextField("X");
	         directionPointX.setMaxWidth(20 * MILLIMETER);
	         HBox.setHgrow(directionPointX, Priority.ALWAYS);
	         directionPointX.setFont(BOLD);
	         directionPointX.setAlignment(Pos.CENTER);
	         directionPointX.setStyle("-fx-background-color: #FFEFEA;");
	         directionPointX.setPrefHeight(12 * MILLIMETER);
	         directionPointX.setEditable(false);
	         header.getChildren().add(directionPointX);
	         TextField directionPointZ = new TextField("H");
	         directionPointZ.setMaxWidth(15 * MILLIMETER);
	         HBox.setHgrow(directionPointZ, Priority.ALWAYS);
	         directionPointZ.setFont(BOLD);
	         directionPointZ.setAlignment(Pos.CENTER);
	         directionPointZ.setStyle("-fx-background-color: #FFEFEA;");
	         directionPointZ.setPrefHeight(12 * MILLIMETER);
	         directionPointZ.setEditable(false);
	         header.getChildren().add(directionPointZ);
	         TextField directionPointSign = new TextField("Jellege");  
	         directionPointSign.setMaxWidth(40 * MILLIMETER);
	    	 HBox.setHgrow(directionPointSign, Priority.ALWAYS);
	         directionPointSign.setFont(BOLD);
	         directionPointSign.setAlignment(Pos.CENTER);
	         directionPointSign.setStyle("-fx-background-color: #FFEFEA;");
	         directionPointSign.setPrefHeight(12 * MILLIMETER);
	         directionPointSign.setEditable(false);
	         header.getChildren().add(directionPointSign);
	         TextField horizontalAngle = new TextField("Irányérték");
	         horizontalAngle.setMaxWidth(25 * MILLIMETER);
	    	 HBox.setHgrow(horizontalAngle, Priority.ALWAYS);
	         horizontalAngle.setFont(BOLD);
	         horizontalAngle.setAlignment(Pos.CENTER);
	         horizontalAngle.setStyle("-fx-background-color: #FFEFEA;");
	         horizontalAngle.setPrefHeight(12 * MILLIMETER);
	         horizontalAngle.setEditable(false);
	         header.getChildren().add(horizontalAngle);
	         TextField verticalAngle = new TextField("Zenitszög");
	         verticalAngle.setMaxWidth(25 * MILLIMETER);
	    	 HBox.setHgrow(verticalAngle, Priority.ALWAYS);
	         verticalAngle.setFont(BOLD);
	         verticalAngle.setAlignment(Pos.CENTER);
	         verticalAngle.setStyle("-fx-background-color: #FFEFEA;");
	         verticalAngle.setPrefHeight(12 * MILLIMETER);
	         verticalAngle.setEditable(false);
	         header.getChildren().add(verticalAngle);
	         TextField horizontalDistance = new TextField("Távolság");
	         horizontalDistance.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(horizontalDistance, Priority.ALWAYS);
	         horizontalDistance.setFont(BOLD);
	         horizontalDistance.setAlignment(Pos.CENTER);
	         horizontalDistance.setStyle("-fx-background-color: #FFEFEA;");
	         horizontalDistance.setPrefHeight(12 * MILLIMETER);
	         horizontalDistance.setEditable(false);
	         header.getChildren().add(horizontalDistance);
	         TextField directionPointSignHeight = new TextField("Jmag.");
	         directionPointSignHeight.setMaxWidth(16 * MILLIMETER);
	    	 HBox.setHgrow(directionPointSignHeight, Priority.ALWAYS);
	         directionPointSignHeight.setFont(BOLD);
	         directionPointSignHeight.setAlignment(Pos.CENTER);
	         directionPointSignHeight.setStyle("-fx-background-color: #FFEFEA;");
	         directionPointSignHeight.setPrefHeight(12 * MILLIMETER);
	         directionPointSignHeight.setEditable(false);
	         header.getChildren().add(directionPointSignHeight);
	         TextField measurementDateTime = new TextField("Dátum");
	         measurementDateTime.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(measurementDateTime, Priority.ALWAYS);
	         measurementDateTime.setFont(BOLD);
	         measurementDateTime.setAlignment(Pos.CENTER);
	         measurementDateTime.setStyle("-fx-background-color: #FFEFEA;");
	         measurementDateTime.setPrefHeight(12 * MILLIMETER);
	         measurementDateTime.setEditable(false);
	         header.getChildren().add(measurementDateTime);
	         TextField theoreticalPointName = new TextField("Elméleti pont");
	         theoreticalPointName.setMaxWidth(40 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointName, Priority.ALWAYS);
	         theoreticalPointName.setFont(BOLD);
	         theoreticalPointName.setAlignment(Pos.CENTER);
	         theoreticalPointName.setStyle("-fx-background-color: #FFEFEA;");
	         theoreticalPointName.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointName.setEditable(false);
	         header.getChildren().add(theoreticalPointName);
	         TextField theoreticalPointY = new TextField("Y");
	         theoreticalPointY.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointY, Priority.ALWAYS);
	         theoreticalPointY.setFont(BOLD);
	         theoreticalPointY.setAlignment(Pos.CENTER);
	         theoreticalPointY.setStyle("-fx-background-color: #FFEFEA;");
	         theoreticalPointY.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointY.setEditable(false);
	         header.getChildren().add(theoreticalPointY);
	         TextField theoreticalPointX = new TextField("X");
	         theoreticalPointX.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointX, Priority.ALWAYS);
	         theoreticalPointX.setFont(BOLD);
	         theoreticalPointX.setAlignment(Pos.CENTER);
	         theoreticalPointX.setStyle("-fx-background-color: #FFEFEA;");
	         theoreticalPointX.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointX.setEditable(false);
	         header.getChildren().add(theoreticalPointX);
	         TextField theoreticalPointZ = new TextField("H");
	         theoreticalPointZ.setMaxWidth(15 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointZ, Priority.ALWAYS);
	         theoreticalPointZ.setFont(BOLD);
	         theoreticalPointZ.setAlignment(Pos.CENTER);
	         theoreticalPointZ.setStyle("-fx-background-color: #FFEFEA;");
	         theoreticalPointZ.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointZ.setEditable(false);
	         header.getChildren().add(theoreticalPointZ);
	         TextField theoreticalPointSign = new TextField("Jellege");
	         theoreticalPointSign.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointSign, Priority.ALWAYS);
	         theoreticalPointSign.setFont(BOLD);
	         theoreticalPointSign.setAlignment(Pos.CENTER);
	         theoreticalPointSign.setStyle("-fx-background-color: #FFEFEA;");
	         theoreticalPointSign.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointSign.setEditable(false);
	         header.getChildren().add(theoreticalPointSign);
	         ROWS.getChildren().add(header);
	    }
	
}
