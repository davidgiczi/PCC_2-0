package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.PLRFileProcess;

public class MeasurmentDataDisplayer {

	
	 	private final AnchorPane pane = new AnchorPane();
	    public MeasuredPillarDataController measuredPillarDataController;
	    private static final double MILLIMETER = 1000.0 / 225.0;
	    private final Font normalFont = Font.font("Book Antiqua", FontWeight.NORMAL, 14);
	    private final Font boldFont = Font.font("Book Antiqua", FontWeight.BOLD, 16);

	    public MeasurmentDataDisplayer(MeasuredPillarDataController measuredPillarDataController){
	        this.measuredPillarDataController = measuredPillarDataController;
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
	        scroller.setFitToWidth(true);
	        scroller.setFitToHeight(true);
	        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	        return scroller;
	    }
	    
	    private void addContent() {
	    	addHeader();
	    }
	    
	    private void addHeader() {
	    	 Label rowNumber = new Label("Ssz.");
	    	 rowNumber.translateXProperty().bind(pane.widthProperty().divide(210));
	         rowNumber.setFont(boldFont);
	         Label standingPointName = new Label("Álláspont");
	         standingPointName.translateXProperty().bind(pane.widthProperty().divide(210).multiply(10));
	         standingPointName.setFont(boldFont);
	         Label standingPointY = new Label("Y");
	         standingPointY.translateXProperty().bind(pane.widthProperty().divide(210).multiply(25));
	         standingPointY.setFont(boldFont);
	         Label standingPointX = new Label("X");
	         standingPointX.translateXProperty().bind(pane.widthProperty().divide(210).multiply(30));
	         standingPointX.setFont(boldFont);
	         Label standingPointZ = new Label("H");
	         standingPointZ.translateXProperty().bind(pane.widthProperty().divide(210).multiply(35));
	         standingPointZ.setFont(boldFont);
	         Label totalStationElevation = new Label("Műszermag.");
	         totalStationElevation.translateXProperty().bind(pane.widthProperty().divide(210).multiply(40));
	         totalStationElevation.setFont(boldFont);
	         Label directionPointName = new Label("Irányzott pont");
	         directionPointName.translateXProperty().bind(pane.widthProperty().divide(210).multiply(55));
	         directionPointName.setFont(boldFont);
	         Label directionPointY = new Label("Y");
	         directionPointY.translateXProperty().bind(pane.widthProperty().divide(210).multiply(70));
	         directionPointY.setFont(boldFont);
	         Label directionPointX = new Label("X");
	         directionPointX.translateXProperty().bind(pane.widthProperty().divide(210).multiply(75));
	         directionPointX.setFont(boldFont);
	         Label directionPointZ = new Label("H");
	         directionPointZ.translateXProperty().bind(pane.widthProperty().divide(210).multiply(80));
	         directionPointZ.setFont(boldFont);
	         Label directionPointSign = new Label("Jellege");
	         directionPointSign.translateXProperty().bind(pane.widthProperty().divide(210).multiply(85));
	         directionPointSign.setFont(boldFont);
	         Label horizontalAngle = new Label("Irányérték");
	         horizontalAngle.translateXProperty().bind(pane.widthProperty().divide(210).multiply(100));
	         horizontalAngle.setFont(boldFont);
	         Label verticalAngle = new Label("Zenitszög");
	         verticalAngle.translateXProperty().bind(pane.widthProperty().divide(210).multiply(115));
	         verticalAngle.setFont(boldFont);
	         Label horizontalDistance = new Label("Távolság");
	         horizontalDistance.translateXProperty().bind(pane.widthProperty().divide(210).multiply(130));
	         horizontalDistance.setFont(boldFont);
	         Label directionPointSignHeight = new Label("Jelmag.");
	         directionPointSignHeight.translateXProperty().bind(pane.widthProperty().divide(210).multiply(145));
	         directionPointSignHeight.setFont(boldFont);
	         Label measurementDateTime = new Label("Dátum");
	         measurementDateTime.translateXProperty().bind(pane.widthProperty().divide(210).multiply(160));
	         measurementDateTime.setFont(boldFont);
	         totalStationElevation.setFont(boldFont);
	         Label theoreticalPointName = new Label("Elméleti pont");
	         theoreticalPointName.translateXProperty().bind(pane.widthProperty().divide(210).multiply(170));
	         theoreticalPointName.setFont(boldFont);
	         Label theoreticalPointY = new Label("Y");
	         theoreticalPointY.translateXProperty().bind(pane.widthProperty().divide(210).multiply(185));
	         theoreticalPointY.setFont(boldFont);
	         Label theoreticalPointX = new Label("X");
	         theoreticalPointX.translateXProperty().bind(pane.widthProperty().divide(210).multiply(190));
	         theoreticalPointX.setFont(boldFont);
	         Label theoreticalPointZ = new Label("H");
	         theoreticalPointZ.translateXProperty().bind(pane.widthProperty().divide(210).multiply(195));
	         theoreticalPointZ.setFont(boldFont);
	         Label theoreticalPointSign = new Label("Jellege");
	         theoreticalPointSign.translateXProperty().bind(pane.widthProperty().divide(210).multiply(200));
	         theoreticalPointSign.setFont(boldFont);
	         pane.getChildren().addAll(rowNumber, 
	        		 standingPointName, standingPointY, standingPointX, standingPointZ,
	        		 totalStationElevation,
	        		 directionPointName, directionPointY, directionPointX, directionPointZ, directionPointSign,
	        		 horizontalAngle, verticalAngle, horizontalDistance, directionPointSignHeight,
	        		 measurementDateTime,
	        		 theoreticalPointName, theoreticalPointX, theoreticalPointY, theoreticalPointZ, theoreticalPointSign);
	    }
	
}
