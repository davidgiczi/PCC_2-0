package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Pos;
import javafx.scene.Node;
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
	    public Stage stage;
	    private static final double MILLIMETER = 1000.0 / 225.0;
	    private static final Font LARGE_BOLD = Font.font("Book Antiqua", FontWeight.BOLD, 16);
	    private static final Font SMALL_BOLD = Font.font("Book Antiqua", FontWeight.BOLD, 14);
	    private VBox ROWS;
	    private List<RowData> standingPointDataStore;
	    public List<TheoreticalPointData> theoreticalPointDataStore;
	    private int rowNumber = 1;
	    
	    public List<RowData> getStandingPointDataStore() {
			return standingPointDataStore;
		}
		public List<TheoreticalPointData> getTheoreticalPointDataStore() {
			return theoreticalPointDataStore;
		}
		
		public AnchorPane getPane() {
			return pane;
		}
		public MeasurmentDataDisplayer(MeasuredPillarDataController measuredPillarDataController){
	        this.measuredPillarDataController = measuredPillarDataController;
	        this.ROWS = new VBox();
	        stage = new Stage();
	        if( !addContent() ) {
		    	   return;
		       }
	        stage.setOnCloseRequest(windowEvent -> {
	          
			getDisplayerData();
				
	        	if( measuredPillarDataController
	        			.getConfirmationAlert("Jegyzőkönyv mentése", "Kívánod a jegyzőkönyvet fájlba menteni?") ) {
	        		
	        		
	        		
	        		for (RowData standingPointData : standingPointDataStore) {
						for (RowData measPointData: standingPointData.getMeasuredPointDataStore()) {
							if( measPointData.getTheoreticalPointData() != null && 
									!measPointData.getTheoreticalPointData().isDeleted() &&
									!theoreticalPointDataStore.contains(measPointData.getTheoreticalPointData())) {
								theoreticalPointDataStore.add(measPointData.getTheoreticalPointData());
							}
						}
					}
	        		
	        		String fileName = measuredPillarDataController.fileProcess.saveMeasurmentReportRowData(standingPointDataStore);
	        		File file = measuredPillarDataController.fileProcess.saveMeasurmentReportTheoreticalPointData(theoreticalPointDataStore, fileName);
	        		
	        	if(	file.length() != 0 ) {
	        		
	        		measuredPillarDataController.getInfoAlert(fileName, 
	        				"A mérési jegyzőkönyv mentve a(z)\n\n" +  PLRFileProcess.FOLDER_PATH + " mappába.");
	        	}
	        	else {
	        		measuredPillarDataController.getInfoAlert("Adatok mentése sikertelen", 
	        				"Nincs menthető adat.");
	        		file.delete();
	        	}
	        		
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
	    	collectSecondMeasurementValue();
	    	
	    	if( standingPointDataStore == null ) {
	    		addTheoreticalPointData();
	    		return false;
	    	}
	    	
	    	for (RowData standingPointData : standingPointDataStore) {
	    		MeasDataRow standingPointRow = new MeasDataRow(true, true, true, true, true, 
	    				true, false, false, false, false, false, false, false, false, false,
	    				false, false, false, false, false, false);
	    		standingPointRow.getRowNumber().setText(standingPointData.getRowNumber());
	    		standingPointRow.setId(standingPointData.getStandingPointName());
	    		standingPointRow.getStandingPointNameField().setText(standingPointData.getStandingPointName());
	    		standingPointRow.getStandingPointYField().setText(standingPointData.getStandingPointY());
	    		standingPointRow.getStandingPointXField().setText(standingPointData.getStandingPointX());
	    		standingPointRow.getStandingPointZField().setText(standingPointData.getStandingPointZ());
	    		standingPointRow.getTotalStationHeightField().setText(standingPointData.getTotalStationHeight());
	    		standingPointRow.setStyle("-fx-background-color: #E8D9D3");
	    		ROWS.getChildren().add(standingPointRow);
	    		
	    		for (RowData measuredPointData : standingPointData.getMeasuredPointDataStore()) {
	    			
	    			if( standingPointData.getStandingPointName().equals(measuredPointData.getStandingPointName())) {
	    				
	    			MeasDataRow measuredPointRow = new MeasDataRow(true, false, false, false, false, 
		    				false, true, true, true, true, true, true, true, true, true,
		    				true, true, true, true, true, true);
	    			measuredPointRow.setMeasuredPillarDataController(measuredPillarDataController);
	    			measuredPointRow.setId("MEAS_" + measuredPointData.getRowNumber());
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
	    			measuredPointRow.getDateField().setText(measuredPointData.getDate());
	    			measuredPointRow.getTimeField().setText(measuredPointData.getTime());
	    			Tooltip time = new Tooltip(measuredPointData.getDate() + " " + measuredPointData.getTime());
	    			Tooltip.install(measuredPointRow.getDateField(), time);
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
	    			measuredPointRow.setTooltip();
	    			if( Integer.parseInt(measuredPointData.getRowNumber()) % 2 == 0 ) {
	    				measuredPointRow.setStyle("-fx-background-color:#F4F4F4");
	    				measuredPointRow.getMeasuredPointNameField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getMeasuredPointYField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getMeasuredPointXField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getMeasuredPointZField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getMeasuredPointSignHeightField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getMeasuredPointSignField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getHorizontalAngleField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getVerticalAngleField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getHorizontalDistanceField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getDateField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getTheoreticalPointNameField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getTheoreticalPointYField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getTheoreticalPointXField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getTheoreticalPointZField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    				measuredPointRow.getTheoreticalPointSignNameField().setStyle("-fx-control-inner-background:#F4F4F4;");
	    			}
	    			MeasDataRow twoMeasmentRow = null; 
	    			if( measuredPointData.getFirstHrMeas() != null && measuredPointData.getFirstVrMeas() != null ) {
	    				twoMeasmentRow = new MeasDataRow(false, false, false, false, false, 
			    				false, false, false, false, false, false, true, true, true, false,
			    				false, false, false, false, false, false);
	    				twoMeasmentRow.getHorizontalAngleField().setText(measuredPointData.getMediumHrValue());
	    				twoMeasmentRow.getVerticalAngleField().setText(measuredPointData.getMediumVrValue());
	    				twoMeasmentRow.getHorizontalDistanceField().setText(measuredPointData.getMediumDistanceValue());
	    				twoMeasmentRow.setStyle("-fx-background-color:#FFFFE0");
	    				twoMeasmentRow.getHorizontalAngleField().setFont(SMALL_BOLD);
	    				twoMeasmentRow.getHorizontalAngleField().setStyle("-fx-background-color:#FFFFE0;-fx-text-fill: #FF4122;");
	    				twoMeasmentRow.getVerticalAngleField().setStyle("-fx-background-color:#FFFFE0;-fx-text-fill: #FF4122;");
	    				twoMeasmentRow.getVerticalAngleField().setFont(SMALL_BOLD);
	    				twoMeasmentRow.getHorizontalDistanceField().setStyle("-fx-background-color:#FFFFE0;-fx-text-fill: #FF4122;"); 
	    				twoMeasmentRow.getHorizontalDistanceField().setFont(SMALL_BOLD);
		
	    			}
	    			ROWS.getChildren().add(measuredPointRow);
	    			if( twoMeasmentRow != null ) {
	    				ROWS.getChildren().add(twoMeasmentRow);	
	    				MeasDataRow secondMeasRow = (MeasDataRow) ROWS.getChildren().get(ROWS.getChildren().indexOf(twoMeasmentRow) - 1);
	    				secondMeasRow.getHorizontalAngleField().setStyle("-fx-control-inner-background:#FFFFE0;");
	    				secondMeasRow.getVerticalAngleField().setStyle("-fx-control-inner-background:#FFFFE0;");
	    				secondMeasRow.getHorizontalDistanceField().setStyle("-fx-control-inner-background:#FFFFE0;");
	    			}
	    		}
			}
	    }
	    	
	    		addTheoreticalPointData();
	    	
	    		return true;
		}
	    
	  private void addTheoreticalPointData() {
		  
		  if( theoreticalPointDataStore == null ) {
			  measuredPillarDataController.getInfoAlert("Az adatok nem jeleníthetők meg", PLRFileProcess.MEAS_FILE_NAME +
					  "\n\nA beolvasott fájl tartalma nem megfelelő.");
			  return;
		  }
	    	
	    	for (TheoreticalPointData theoreticalPointData : theoreticalPointDataStore) {
				if( !theoreticalPointData.isDeleted() ) {
					MeasDataRow theoreticalPointRow = new MeasDataRow(true, false, false, false, false, 
		    				false, false, false, false, false, false, false, false, false, false,
		    				false, true, true, true, true, true);
					theoreticalPointRow.setMeasuredPillarDataController(measuredPillarDataController);
					theoreticalPointRow.setId("TEO_" + String.valueOf(rowNumber));
					theoreticalPointRow.getRowNumber().setText(String.valueOf(rowNumber));
					rowNumber++;
					theoreticalPointRow.getTheoreticalPointNameField().setText(theoreticalPointData.getTheoreticalPointName());
					theoreticalPointRow.getTheoreticalPointYField().setText(theoreticalPointData.getTheoreticalPointY());
					theoreticalPointRow.getTheoreticalPointXField().setText(theoreticalPointData.getTheoreticalPointX());
					theoreticalPointRow.getTheoreticalPointZField().setText(theoreticalPointData.getTheoreticalPointZ());
					theoreticalPointRow.getTheoreticalPointSignNameField().setText(theoreticalPointData.getTheoreticalPointSignName());
					theoreticalPointRow.setTooltip();
					if( rowNumber % 2 == 0 ) {
	    				theoreticalPointRow.setStyle("-fx-background-color:#F4F4F4");
	    			}
					ROWS.getChildren().add(theoreticalPointRow);
				}
			}
	    	
	    	for (TheoreticalPointData theoreticalPointData : theoreticalPointDataStore) {
				theoreticalPointData.setDeleted(false);
			
		}
	 }
	  
	  
	    private void createRowDataStore() {
	    	
	    	List<String> measData = measuredPillarDataController.measurmentData;
	    	 boolean isSeparatedByComma = false;
	    	String[] rowData =  measData.get(0).split(";");
	    	if( rowData.length == 1) {
	    	rowData = measData.get(0).split(",");	
	    	isSeparatedByComma = true;
	    	}
	    	if( rowData.length != 4 && rowData.length != 5 && rowData.length != 17) {
	    		return;
	    	}
	    	standingPointDataStore = new ArrayList<>();
	    	theoreticalPointDataStore = new ArrayList<>();
	    	String standingPointId = rowData[0];
	    	RowData standingPointRow = new RowData();
	    	RowData measuredPointRow = new RowData();
	    	
	    	if( rowData.length == 17 ) {
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
	    		
	    		if( isSeparatedByComma ) {
	    			rowData = row.split(",");	
	    		}
	    		else {
	    			rowData = row.split(";");
	    		}
				
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
				else if( rowData.length == 4 || rowData.length == 5) {
					TheoreticalPointData theoretical = new TheoreticalPointData();
					theoretical.setTheoreticalPointName(rowData[0]);
					theoretical.setTheoreticalPointY(rowData[1]);
					theoretical.setTheoreticalPointX(rowData[2]);
					theoretical.setTheoreticalPointZ(rowData[3]);
					if( rowData.length == 5) {
					theoretical.setTheoreticalPointSignName(rowData[4]);	
					}
					theoreticalPointDataStore.add(theoretical);
				}	
			}
	    }
	      
	    private void parseTheoreticalPointData() {
	    	
	    	if( theoreticalPointDataStore == null ) {
	    		return;
	    	}
	    	
	    	for(int i = 0; i < theoreticalPointDataStore.size(); i++) {
	    		
	    		for (RowData standingPoint : standingPointDataStore) {
	    			
					for(RowData measuredPoint : standingPoint.getMeasuredPointDataStore() ) {
						
						if( theoreticalPointDataStore.get(i).getTheoreticalPointName()
								.equals( measuredPoint.getMeasuredPointSign())) {
							
							measuredPoint.setTheoreticalPointData(theoreticalPointDataStore.get(i));
							theoreticalPointDataStore.get(i).setDeleted(true);
							if("0.000".equals(measuredPoint.getMeasuredPointY()) && "0.000".equals(measuredPoint.getMeasuredPointX()) ) {
								measuredPoint.setMeasuredPointY(theoreticalPointDataStore.get(i).getTheoreticalPointY());
								measuredPoint.setMeasuredPointX(theoreticalPointDataStore.get(i).getTheoreticalPointX());
								measuredPoint.setMeasuredPointZ(theoreticalPointDataStore.get(i).getTheoreticalPointZ());
							}
						}
						
					}
				}
	    	}
	    }
	    
	    public void collectSecondMeasurementValue() {
	    	
	    	if( standingPointDataStore == null ) {
	    		return;
	    	}
	    	
	    	for (RowData standingPoint1 : standingPointDataStore) {
	    		
	    		for (RowData measPoint1 : standingPoint1.getMeasuredPointDataStore()) {
	    			
	    			
	    			for (RowData standingPoint2 : standingPointDataStore) {
	    	    		
	    	    		for (RowData measPoint2 : standingPoint2.getMeasuredPointDataStore()) {
	    	    			
	    	    			
	    	    			if( standingPoint1.getStandingPointName().equals(standingPoint2.getStandingPointName()) &&
	    	    					measPoint1.getMeasuredPointSign().equals(measPoint2.getMeasuredPointSign()) &&
	    	    					measPoint1.getMeasuredPointX().equals(measPoint2.getMeasuredPointX()) &&
	    	    					measPoint1.getMeasuredPointY().equals(measPoint2.getMeasuredPointY()) &&
	    	    					!measPoint1.getHorizontalAngle().equals(measPoint2.getHorizontalAngle()) &&
	    	    					!measPoint1.getVerticalAngle().equals(measPoint2.getVerticalAngle()) &&
	    	    					measPoint1.getFirstHrMeas() == null &&
	    	    					measPoint1.getFirstVrMeas() == null &&
	    	    					measPoint2.getFirstHrMeas() == null &&
	    	    					measPoint2.getFirstVrMeas() == null ) {
	    	    				
	    	    					measPoint2.setFirstHrMeas(measPoint1.getHorizontalAngle());
	    	    					measPoint2.setFirstVrMeas(measPoint1.getVerticalAngle());
	    	    					measPoint2.setFirstDistValue(measPoint1.getHorizontalDistance());
	    	    			}		
	    	    		}
	    	    	}	
	    		}
	    			
	    	}
	    	
	    }
	    
	    public void getDisplayerData() throws RuntimeException {
	    	
	    	try {
	    	
	    	standingPointDataStore.clear();
	    	theoreticalPointDataStore.clear();
	    	RowData standingPointRow = null;
	    	for (Node row : ROWS.getChildren()) {
	    		
	    		if(	row.getId() == null ) {continue;}
	    			
	    			MeasDataRow measDataRow = (MeasDataRow) row;
	    			
	    			if( row.getId().startsWith("ST") ) {
	    				if( standingPointRow == null) {
	    					standingPointRow = new RowData();
	    				}
	    				else {
	    					standingPointDataStore.add(standingPointRow);
	    					standingPointRow = new RowData();
	    				}
	    				standingPointRow.setStandingPointName(row.getId());
	    				standingPointRow.setStandingPointY(measDataRow.getStandingPointYField().getText());
	    				standingPointRow.setStandingPointX(measDataRow.getStandingPointXField().getText());
	    				standingPointRow.setStandingPointZ(measDataRow.getStandingPointZField().getText());
	    				standingPointRow.setTotalStationHeight(measDataRow.getTotalStationHeightField().getText());
	    				
	    			}
	    			else if(row.getId().startsWith("MEAS")){
	    				RowData measPointRow = new RowData();
	    				measPointRow.setDeleted(measDataRow.isDeletedRow());
	    				measPointRow.setStandingPointName(standingPointRow.getStandingPointName());
	    				measPointRow.setStandingPointY(standingPointRow.getStandingPointY());
	    				measPointRow.setStandingPointX(standingPointRow.getStandingPointX());
	    				measPointRow.setStandingPointZ(standingPointRow.getStandingPointZ());
	    				measPointRow.setTotalStationHeight(standingPointRow.getTotalStationHeight());
	    				measPointRow.setMeasuredPointName(measDataRow.getMeasuredPointNameField().getText());
	    				measPointRow.setMeasuredPointY(measDataRow.getMeasuredPointYField().getText());
	    				measPointRow.setMeasuredPointX(measDataRow.getMeasuredPointXField().getText());
	    				measPointRow.setMeasuredPointZ(measDataRow.getMeasuredPointZField().getText());
	    				measPointRow.setMeasuredPointSign(measDataRow.getMeasuredPointSignField().getText());
	    				measPointRow.setHorizontalAngle(measDataRow.getHorizontalAngleField().getText());
	    				measPointRow.setVerticalAngle(measDataRow.getVerticalAngleField().getText());
	    				measPointRow.setHorizontalDistance(measDataRow.getHorizontalDistanceField().getText());
	    				measPointRow.setMeasuredPointSignHeight(measDataRow.getMeasuredPointSignHeightField().getText());
	    				measPointRow.setDate(measDataRow.getDateField().getText());
	    				measPointRow.setTime(measDataRow.getTimeField().getText());
	    				TheoreticalPointData theoreticalPointData = null;
	    				if( !measDataRow.getTheoreticalPointNameField().getText().isEmpty() && !measDataRow.isDeletedTheoretical() ) {
	    					theoreticalPointData = new TheoreticalPointData();
	    					theoreticalPointData.setTheoreticalPointName(measDataRow.getTheoreticalPointNameField().getText());
	    					theoreticalPointData.setDeleted(measDataRow.isDeletedTheoretical());
	    				}
	    				if( !measDataRow.getTheoreticalPointYField().getText().isEmpty() && !measDataRow.isDeletedTheoretical() ) {
	    					theoreticalPointData.setTheoreticalPointY(measDataRow.getTheoreticalPointYField().getText());
	    				}
	    				if( !measDataRow.getTheoreticalPointXField().getText().isEmpty() && !measDataRow.isDeletedTheoretical() ) {
	    					theoreticalPointData.setTheoreticalPointX(measDataRow.getTheoreticalPointXField().getText());
	    				}
	    				if( !measDataRow.getTheoreticalPointZField().getText().isEmpty() && !measDataRow.isDeletedTheoretical() ) {
	    					theoreticalPointData.setTheoreticalPointZ(measDataRow.getTheoreticalPointZField().getText());
	    				}
	    				if( !measDataRow.getTheoreticalPointSignNameField().getText().isEmpty() && !measDataRow.isDeletedTheoretical() ) {
	    					theoreticalPointData.setTheoreticalPointSignName(measDataRow.getTheoreticalPointSignNameField().getText());
	    				}
	    				if( theoreticalPointData != null ) {
	    					measPointRow.setTheoreticalPointData(theoreticalPointData);
	    				}
	    				standingPointRow.getMeasuredPointDataStore().add(measPointRow);
	    			}
	    			else if( row.getId().startsWith("TEO") && !measDataRow.isDeletedTheoretical()) {
	    				TheoreticalPointData theoreticalPointRow = new TheoreticalPointData();
	    				theoreticalPointRow.setDeleted(measDataRow.isDeletedTheoretical());
	    				theoreticalPointRow.setTheoreticalPointName(measDataRow.getTheoreticalPointNameField().getText());
	    				theoreticalPointRow.setTheoreticalPointY(measDataRow.getTheoreticalPointYField().getText());
	    				theoreticalPointRow.setTheoreticalPointX(measDataRow.getTheoreticalPointXField().getText());
	    				theoreticalPointRow.setTheoreticalPointZ(measDataRow.getTheoreticalPointZField().getText());
	    				theoreticalPointRow.setTheoreticalPointSignName(measDataRow.getTheoreticalPointSignNameField().getText());
	    				theoreticalPointDataStore.add(theoreticalPointRow);
	    			}
	    		}
	    	if( standingPointRow != null ) {
	    		standingPointDataStore.add(standingPointRow);
	    	}
	    	} catch (Exception e) {
				e.printStackTrace();
			}
	   	}
	  
	    private void addHeader() {
	    	 HBox header = new HBox();
	    	 TextField rowNumber = new TextField("Ssz.");
	    	 rowNumber.setMaxWidth(11 * MILLIMETER);
	    	 HBox.setHgrow(rowNumber, Priority.ALWAYS);
	         rowNumber.setFont(LARGE_BOLD);
	         rowNumber.setAlignment(Pos.CENTER);
	         rowNumber.setEditable(false);
	         rowNumber.setStyle("-fx-background-color: #E8D9D3;");
	         rowNumber.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(rowNumber);
	         TextField standingPointName = new TextField("Álláspont");
	         standingPointName.setMaxWidth(21 * MILLIMETER);
	    	 HBox.setHgrow(standingPointName, Priority.ALWAYS);
	         standingPointName.setFont(LARGE_BOLD);
	         standingPointName.setAlignment(Pos.CENTER);
	         standingPointName.setEditable(false);
	         standingPointName.setStyle("-fx-background-color: #E8D9D3;");
	         standingPointName.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(standingPointName);
	         TextField standingPointY = new TextField("Y");
	         standingPointY.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(standingPointY, Priority.ALWAYS);
	         standingPointY.setFont(LARGE_BOLD);
	         standingPointY.setAlignment(Pos.CENTER);
	         standingPointY.setStyle("-fx-background-color: #E8D9D3;");
	         standingPointY.setPrefHeight(12 * MILLIMETER);
	         standingPointY.setEditable(false);
	         header.getChildren().add(standingPointY);
	         TextField standingPointX = new TextField("X");
	         standingPointX.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(standingPointX, Priority.ALWAYS);
	         standingPointX.setFont(LARGE_BOLD);
	         standingPointX.setAlignment(Pos.CENTER);
	         standingPointX.setStyle("-fx-background-color: #E8D9D3;");
	         standingPointX.setPrefHeight(12 * MILLIMETER);
	         standingPointX.setEditable(false);
	         header.getChildren().add(standingPointX);
	         TextField standingPointZ = new TextField("H");
	         standingPointZ.setMaxWidth(15 * MILLIMETER);
	    	 HBox.setHgrow(standingPointZ, Priority.ALWAYS);
	         standingPointZ.setFont(LARGE_BOLD);
	         standingPointZ.setAlignment(Pos.CENTER);
	         standingPointZ.setStyle("-fx-background-color: #E8D9D3;");
	         standingPointZ.setPrefHeight(12 * MILLIMETER);
	         standingPointZ.setEditable(false);
	         header.getChildren().add(standingPointZ);
	         TextField totalStationHeight = new TextField("Mmag.");
	         totalStationHeight.setMaxWidth(16 * MILLIMETER);
	         HBox.setHgrow(totalStationHeight, Priority.ALWAYS);
	         totalStationHeight.setFont(LARGE_BOLD);
	         totalStationHeight.setEditable(false);
	         totalStationHeight.setAlignment(Pos.CENTER);
	         totalStationHeight.setStyle("-fx-background-color: #E8D9D3;");
	         totalStationHeight.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(totalStationHeight);
	         TextField directionPointName = new TextField("MértP.");
	         directionPointName.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(directionPointName, Priority.ALWAYS);
	         directionPointName.setFont(LARGE_BOLD);
	         directionPointName.setEditable(false);
	         directionPointName.setAlignment(Pos.CENTER);
	         directionPointName.setStyle("-fx-background-color: #E8D9D3;");
	         directionPointName.setPrefHeight(12 * MILLIMETER);
	         header.getChildren().add(directionPointName);
	         TextField directionPointY = new TextField("Y");
	         directionPointY.setMaxWidth(20 * MILLIMETER);
	         HBox.setHgrow(directionPointY, Priority.ALWAYS);
	         directionPointY.setFont(LARGE_BOLD);
	         directionPointY.setAlignment(Pos.CENTER);
	         directionPointY.setStyle("-fx-background-color: #E8D9D3;");
	         directionPointY.setPrefHeight(12 * MILLIMETER);
	         directionPointY.setEditable(false);
	         header.getChildren().add(directionPointY);
	         TextField directionPointX = new TextField("X");
	         directionPointX.setMaxWidth(20 * MILLIMETER);
	         HBox.setHgrow(directionPointX, Priority.ALWAYS);
	         directionPointX.setFont(LARGE_BOLD);
	         directionPointX.setAlignment(Pos.CENTER);
	         directionPointX.setStyle("-fx-background-color: #E8D9D3;");
	         directionPointX.setPrefHeight(12 * MILLIMETER);
	         directionPointX.setEditable(false);
	         header.getChildren().add(directionPointX);
	         TextField directionPointZ = new TextField("H");
	         directionPointZ.setMaxWidth(15 * MILLIMETER);
	         HBox.setHgrow(directionPointZ, Priority.ALWAYS);
	         directionPointZ.setFont(LARGE_BOLD);
	         directionPointZ.setAlignment(Pos.CENTER);
	         directionPointZ.setStyle("-fx-background-color: #E8D9D3;");
	         directionPointZ.setPrefHeight(12 * MILLIMETER);
	         directionPointZ.setEditable(false);
	         header.getChildren().add(directionPointZ);
	         TextField directionPointSign = new TextField("Jellege");  
	         directionPointSign.setMaxWidth(40 * MILLIMETER);
	    	 HBox.setHgrow(directionPointSign, Priority.ALWAYS);
	         directionPointSign.setFont(LARGE_BOLD);
	         directionPointSign.setAlignment(Pos.CENTER);
	         directionPointSign.setStyle("-fx-background-color: #E8D9D3;");
	         directionPointSign.setPrefHeight(12 * MILLIMETER);
	         directionPointSign.setEditable(false);
	         header.getChildren().add(directionPointSign);
	         TextField horizontalAngle = new TextField("Irányérték");
	         horizontalAngle.setMaxWidth(25 * MILLIMETER);
	    	 HBox.setHgrow(horizontalAngle, Priority.ALWAYS);
	         horizontalAngle.setFont(LARGE_BOLD);
	         horizontalAngle.setAlignment(Pos.CENTER);
	         horizontalAngle.setStyle("-fx-background-color: #E8D9D3;");
	         horizontalAngle.setPrefHeight(12 * MILLIMETER);
	         horizontalAngle.setEditable(false);
	         header.getChildren().add(horizontalAngle);
	         TextField verticalAngle = new TextField("Zenitszög");
	         verticalAngle.setMaxWidth(25 * MILLIMETER);
	    	 HBox.setHgrow(verticalAngle, Priority.ALWAYS);
	         verticalAngle.setFont(LARGE_BOLD);
	         verticalAngle.setAlignment(Pos.CENTER);
	         verticalAngle.setStyle("-fx-background-color: #E8D9D3;");
	         verticalAngle.setPrefHeight(12 * MILLIMETER);
	         verticalAngle.setEditable(false);
	         header.getChildren().add(verticalAngle);
	         TextField horizontalDistance = new TextField("Távolság");
	         horizontalDistance.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(horizontalDistance, Priority.ALWAYS);
	         horizontalDistance.setFont(LARGE_BOLD);
	         horizontalDistance.setAlignment(Pos.CENTER);
	         horizontalDistance.setStyle("-fx-background-color: #E8D9D3;");
	         horizontalDistance.setPrefHeight(12 * MILLIMETER);
	         horizontalDistance.setEditable(false);
	         header.getChildren().add(horizontalDistance);
	         TextField directionPointSignHeight = new TextField("Jmag.");
	         directionPointSignHeight.setMaxWidth(16 * MILLIMETER);
	    	 HBox.setHgrow(directionPointSignHeight, Priority.ALWAYS);
	         directionPointSignHeight.setFont(LARGE_BOLD);
	         directionPointSignHeight.setAlignment(Pos.CENTER);
	         directionPointSignHeight.setStyle("-fx-background-color: #E8D9D3;");
	         directionPointSignHeight.setPrefHeight(12 * MILLIMETER);
	         directionPointSignHeight.setEditable(false);
	         header.getChildren().add(directionPointSignHeight);
	         TextField measurementDateTime = new TextField("Dátum");
	         measurementDateTime.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(measurementDateTime, Priority.ALWAYS);
	         measurementDateTime.setFont(LARGE_BOLD);
	         measurementDateTime.setAlignment(Pos.CENTER);
	         measurementDateTime.setStyle("-fx-background-color: #E8D9D3;");
	         measurementDateTime.setPrefHeight(12 * MILLIMETER);
	         measurementDateTime.setEditable(false);
	         header.getChildren().add(measurementDateTime);
	         TextField theoreticalPointName = new TextField("Elméleti pont");
	         theoreticalPointName.setMaxWidth(40 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointName, Priority.ALWAYS);
	         theoreticalPointName.setFont(LARGE_BOLD);
	         theoreticalPointName.setAlignment(Pos.CENTER);
	         theoreticalPointName.setStyle("-fx-background-color: #E8D9D3;");
	         theoreticalPointName.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointName.setEditable(false);
	         header.getChildren().add(theoreticalPointName);
	         TextField theoreticalPointY = new TextField("Y");
	         theoreticalPointY.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointY, Priority.ALWAYS);
	         theoreticalPointY.setFont(LARGE_BOLD);
	         theoreticalPointY.setAlignment(Pos.CENTER);
	         theoreticalPointY.setStyle("-fx-background-color: #E8D9D3;");
	         theoreticalPointY.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointY.setEditable(false);
	         header.getChildren().add(theoreticalPointY);
	         TextField theoreticalPointX = new TextField("X");
	         theoreticalPointX.setMaxWidth(20 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointX, Priority.ALWAYS);
	         theoreticalPointX.setFont(LARGE_BOLD);
	         theoreticalPointX.setAlignment(Pos.CENTER);
	         theoreticalPointX.setStyle("-fx-background-color: #E8D9D3;");
	         theoreticalPointX.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointX.setEditable(false);
	         header.getChildren().add(theoreticalPointX);
	         TextField theoreticalPointZ = new TextField("H");
	         theoreticalPointZ.setMaxWidth(15 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointZ, Priority.ALWAYS);
	         theoreticalPointZ.setFont(LARGE_BOLD);
	         theoreticalPointZ.setAlignment(Pos.CENTER);
	         theoreticalPointZ.setStyle("-fx-background-color: #E8D9D3;");
	         theoreticalPointZ.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointZ.setEditable(false);
	         header.getChildren().add(theoreticalPointZ);
	         TextField theoreticalPointSign = new TextField("Jellege");
	         theoreticalPointSign.setMaxWidth(30 * MILLIMETER);
	    	 HBox.setHgrow(theoreticalPointSign, Priority.ALWAYS);
	         theoreticalPointSign.setFont(LARGE_BOLD);
	         theoreticalPointSign.setAlignment(Pos.CENTER);
	         theoreticalPointSign.setStyle("-fx-background-color: #E8D9D3;");
	         theoreticalPointSign.setPrefHeight(12 * MILLIMETER);
	         theoreticalPointSign.setEditable(false);
	         header.getChildren().add(theoreticalPointSign);
	         ROWS.getChildren().add(header);
	    }
	
}
