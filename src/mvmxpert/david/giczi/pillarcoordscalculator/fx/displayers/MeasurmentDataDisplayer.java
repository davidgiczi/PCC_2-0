package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.PLRFileProcess;

public class MeasurmentDataDisplayer {

	
	 	private final AnchorPane pane = new AnchorPane();
	    public MeasuredPillarDataController measuredPillarDataController;
	    private static final double MILLIMETER = 1000.0 / 225.0;
	    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
	    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 16);

	    public MeasurmentDataDisplayer(MeasuredPillarDataController measuredPillarDataController){
	        this.measuredPillarDataController = measuredPillarDataController;
	        Stage stage = new Stage();
	        stage.initOwner(measuredPillarDataController.fxHomeWindow.homeStage);
	        stage.setOnCloseRequest(windowEvent -> {
	            measuredPillarDataController.fxHomeWindow.homeStage.show();
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
	    	 HBox headerRow = new HBox();
	    	 Label rowNumber = new Label("Sorszám");
	         rowNumber.setFont(boldFont);
	         headerRow.setLayoutX(3 * MILLIMETER);
	         headerRow.getChildren().addAll(rowNumber);
	         pane.getChildren().add(headerRow);
	    }
	
}
