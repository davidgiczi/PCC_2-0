package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;

public class MeasDataRow extends HBox {

	
	private TextField rowNumber;
	private TextField standingPointNameField;
	private TextField standingPointYField;
	private TextField standingPointXField;
	private TextField standingPointZField;
	private TextField totalStationHeightField;
	private TextField measuredPointNameField;
	private TextField measuredPointYField;
	private TextField measuredPointXField;
	private TextField measuredPointZField;
	private TextField measuredPointSignField;
	private TextField horizontalAngleField;
	private TextField verticalAngleField;
	private TextField horizontalDistanceField;
	private TextField measuredPointSignHeightField;
	private TextField dateTimeField;
	private TextField theoreticalPointNameField;
	private TextField theoreticalPointXField;
	private TextField theoreticalPointYField;
	private TextField theoreticalPointZField;
	private TextField theoreticalPointSignNameField;
	private MeasuredPillarDataController measuredPillarDataController;
	private static final double MILLIMETER = 1000.0 / 225.0;
	private static final Font NORMAL = Font.font("Book Antiqua", FontWeight.NORMAL, 14);
	private static final Font BOLD = Font.font("Book Antiqua", FontWeight.BOLD, 14);
	
	
	public MeasDataRow(boolean...isVisible) {
		this.setOnMouseClicked(e -> {
			if( measuredPillarDataController != null) {
			
				if( measuredPillarDataController
			.getConfirmationAlert("Mérés törlése", 
					"Biztos, hogy törlöd a(z) " + ((HBox) e.getSource()).getId() + ". sor mérési eredményeit?")) {
					deleteRowData();
				}
				else {
					
					noDeleteRowData();
				}
				;} 
			});
		this.setCursor(Cursor.HAND);
		createRow();
		setFieldVisible(isVisible);	
	}
	
	private void noDeleteRowData() {
			
		 rowNumber.setStyle("-fx-control-inner-background:white");
		 standingPointNameField.setStyle("-fx-control-inner-background:white");
		 standingPointYField.setStyle("-fx-control-inner-background:white");
		 standingPointXField.setStyle("-fx-control-inner-background:white");
		 standingPointZField.setStyle("-fx-control-inner-background:white");
		 totalStationHeightField.setStyle("-fx-control-inner-background:white");
		 measuredPointNameField.setStyle("-fx-control-inner-background:white");
		 measuredPointYField.setStyle("-fx-control-inner-background:white");
		 measuredPointXField.setStyle("-fx-control-inner-background:white");
		 measuredPointZField.setStyle("-fx-control-inner-background:white");
		 measuredPointSignField.setStyle("-fx-control-inner-background:white");
		 horizontalAngleField.setStyle("-fx-control-inner-background:white");
		 verticalAngleField.setStyle("-fx-control-inner-background:white");
		 horizontalDistanceField.setStyle("-fx-control-inner-background:white");
		 measuredPointSignHeightField.setStyle("-fx-control-inner-background:white");
		 dateTimeField.setStyle("-fx-control-inner-background:white");
		 theoreticalPointNameField.setStyle("-fx-control-inner-background:white");
		 theoreticalPointXField.setStyle("-fx-control-inner-background:white");
		 theoreticalPointYField.setStyle("-fx-control-inner-background:white");
		 theoreticalPointZField.setStyle("-fx-control-inner-background:white");
		 theoreticalPointSignNameField.setStyle("-fx-control-inner-background:white");
}
	
	
	private void deleteRowData() {
			
			 rowNumber.setStyle("-fx-control-inner-background:#F88379");
			 standingPointNameField.setStyle("-fx-control-inner-background:#F88379");
			 standingPointYField.setStyle("-fx-control-inner-background:#F88379");
			 standingPointXField.setStyle("-fx-control-inner-background:#F88379");
			 standingPointZField.setStyle("-fx-control-inner-background:#F88379");
			 totalStationHeightField.setStyle("-fx-control-inner-background:#F88379");
			 measuredPointNameField.setStyle("-fx-control-inner-background:#F88379");
			 measuredPointYField.setStyle("-fx-control-inner-background:#F88379");
			 measuredPointXField.setStyle("-fx-control-inner-background:#F88379");
			 measuredPointZField.setStyle("-fx-control-inner-background:#F88379");
			 measuredPointSignField.setStyle("-fx-control-inner-background:#F88379");
			 horizontalAngleField.setStyle("-fx-control-inner-background:#F88379");
			 verticalAngleField.setStyle("-fx-control-inner-background:#F88379");
			 horizontalDistanceField.setStyle("-fx-control-inner-background:#F88379");
			 measuredPointSignHeightField.setStyle("-fx-control-inner-background:#F88379");
			 dateTimeField.setStyle("-fx-control-inner-background:#F88379");
			 theoreticalPointNameField.setStyle("-fx-control-inner-background:#F88379");
			 theoreticalPointXField.setStyle("-fx-control-inner-background:#F88379");
			 theoreticalPointYField.setStyle("-fx-control-inner-background:#F88379");
			 theoreticalPointZField.setStyle("-fx-control-inner-background:#F88379");
			 theoreticalPointSignNameField.setStyle("-fx-control-inner-background:#F88379");
	}
	
	public void setTooltip() {
		
		Tooltip measPointNameTip = new Tooltip(measuredPointNameField.getText());
		Tooltip.install(measuredPointNameField, measPointNameTip);
		Tooltip measPointSignTip = new Tooltip(measuredPointSignField.getText());
		Tooltip.install(measuredPointSignField, measPointSignTip);
		if( theoreticalPointNameField.getText() != null && !theoreticalPointNameField.getText().isEmpty()) {
			Tooltip theoreticalPointNameTip = new Tooltip(theoreticalPointNameField.getText());
			Tooltip.install(theoreticalPointNameField, theoreticalPointNameTip);
			theoreticalPointNameField.setCursor(Cursor.HAND);
		}
		if( theoreticalPointSignNameField.getText() != null && !theoreticalPointSignNameField.getText().isEmpty()) {
			Tooltip theoreticalPointSignNameTip = new Tooltip(theoreticalPointSignNameField.getText());
			Tooltip.install(theoreticalPointSignNameField, theoreticalPointSignNameTip);
			theoreticalPointSignNameField.setCursor(Cursor.HAND);
		}
	}
	
	private void createRow() {
		rowNumber = new TextField();
   	 	rowNumber.setPrefWidth(11 * MILLIMETER);
   	 	HBox.setHgrow(rowNumber, Priority.ALWAYS);
        rowNumber.setFont(BOLD);
        rowNumber.setAlignment(Pos.CENTER);
        rowNumber.setEditable(false);
        this.getChildren().add(rowNumber);
        standingPointNameField = new TextField();
        standingPointNameField.setPrefWidth(21 * MILLIMETER);
   	 	HBox.setHgrow(standingPointNameField, Priority.ALWAYS);
        standingPointNameField.setFont(BOLD);
        standingPointNameField.setStyle("-fx-text-fill:#FF4122;"); 
        standingPointNameField.setAlignment(Pos.CENTER);
        this.getChildren().add(standingPointNameField);
        standingPointYField = new TextField();
        standingPointYField.setPrefWidth(20 * MILLIMETER);
        HBox.setHgrow(standingPointYField, Priority.ALWAYS);
        standingPointYField.setFont(NORMAL);
        standingPointYField.setAlignment(Pos.CENTER);
        this.getChildren().add(standingPointYField);
        standingPointXField = new TextField();
        standingPointXField.setPrefWidth(20 * MILLIMETER);
   	 	HBox.setHgrow(standingPointXField, Priority.ALWAYS);
        standingPointXField.setFont(NORMAL);
        standingPointXField.setAlignment(Pos.CENTER);
        this.getChildren().add(standingPointXField);
        standingPointZField = new TextField();
        standingPointZField.setPrefWidth(15 * MILLIMETER);
        HBox.setHgrow(standingPointZField, Priority.ALWAYS);
        standingPointZField.setFont(NORMAL);
        standingPointZField.setAlignment(Pos.CENTER);
        this.getChildren().add(standingPointZField);
        totalStationHeightField = new TextField();
        totalStationHeightField.setPrefWidth(16 * MILLIMETER);
        HBox.setHgrow(totalStationHeightField, Priority.ALWAYS);
        totalStationHeightField.setFont(NORMAL);
        totalStationHeightField.setAlignment(Pos.CENTER);
        this.getChildren().add(totalStationHeightField);
        measuredPointNameField = new TextField();
        measuredPointNameField.setPrefWidth(20 * MILLIMETER);
        HBox.setHgrow(measuredPointNameField, Priority.ALWAYS);
        measuredPointNameField.setFont(NORMAL);
        measuredPointNameField.setAlignment(Pos.CENTER);
        measuredPointNameField.setCursor(Cursor.HAND);
        this.getChildren().add(measuredPointNameField);
        measuredPointYField = new TextField();
        measuredPointYField.setPrefWidth(20 * MILLIMETER);
        HBox.setHgrow(measuredPointYField, Priority.ALWAYS);
        measuredPointYField.setFont(NORMAL);
        measuredPointYField.setAlignment(Pos.CENTER);
        this.getChildren().add(measuredPointYField);
        measuredPointXField = new TextField();
        measuredPointXField.setPrefWidth(20 * MILLIMETER);
        HBox.setHgrow(measuredPointXField, Priority.ALWAYS);
        measuredPointXField.setFont(NORMAL);
        measuredPointXField.setAlignment(Pos.CENTER);
        this.getChildren().add(measuredPointXField);
        measuredPointZField = new TextField();
        measuredPointZField.setPrefWidth(15 * MILLIMETER);
        HBox.setHgrow(measuredPointZField, Priority.ALWAYS);
        measuredPointZField.setFont(NORMAL);
        measuredPointZField.setAlignment(Pos.CENTER);
        this.getChildren().add(measuredPointZField);
        measuredPointSignField = new TextField();  
        measuredPointSignField.setPrefWidth(40 * MILLIMETER);
   	 	HBox.setHgrow(measuredPointSignField, Priority.ALWAYS);
        measuredPointSignField.setFont(NORMAL);
        measuredPointSignField.setAlignment(Pos.CENTER);
        measuredPointSignField.setCursor(Cursor.HAND);
        this.getChildren().add(measuredPointSignField);
        horizontalAngleField = new TextField();
        horizontalAngleField.setPrefWidth(25 * MILLIMETER);
   	 	HBox.setHgrow(horizontalAngleField, Priority.ALWAYS);
        horizontalAngleField.setFont(NORMAL);
        horizontalAngleField.setAlignment(Pos.CENTER);
        this.getChildren().add(horizontalAngleField);
        verticalAngleField = new TextField();
        verticalAngleField.setPrefWidth(25 * MILLIMETER);
        HBox.setHgrow(verticalAngleField, Priority.ALWAYS);
        verticalAngleField.setFont(NORMAL);
        verticalAngleField.setAlignment(Pos.CENTER);
        this.getChildren().add(verticalAngleField);
        horizontalDistanceField = new TextField();
        horizontalDistanceField.setPrefWidth(20 * MILLIMETER);
   	 	HBox.setHgrow(horizontalDistanceField, Priority.ALWAYS);
        horizontalDistanceField.setFont(NORMAL);
        horizontalDistanceField.setAlignment(Pos.CENTER);
        this.getChildren().add(horizontalDistanceField);
        measuredPointSignHeightField = new TextField();
        measuredPointSignHeightField.setPrefWidth(16 * MILLIMETER);
   	 	HBox.setHgrow(measuredPointSignHeightField, Priority.ALWAYS);
        measuredPointSignHeightField.setFont(NORMAL);
        measuredPointSignHeightField.setAlignment(Pos.CENTER);
        this.getChildren().add(measuredPointSignHeightField);
        dateTimeField = new TextField();
        dateTimeField.setPrefWidth(20 * MILLIMETER);
   	 	HBox.setHgrow(dateTimeField, Priority.ALWAYS);
        dateTimeField.setFont(NORMAL);
        dateTimeField.setAlignment(Pos.CENTER);
        dateTimeField.setCursor(Cursor.HAND);
        this.getChildren().add(dateTimeField);
        theoreticalPointNameField = new TextField();
        theoreticalPointNameField.setPrefWidth(40 * MILLIMETER);
   	 	HBox.setHgrow(theoreticalPointNameField, Priority.ALWAYS);
        theoreticalPointNameField.setFont(NORMAL);
        theoreticalPointNameField.setAlignment(Pos.CENTER);
        this.getChildren().add(theoreticalPointNameField);
        theoreticalPointYField = new TextField();
        theoreticalPointYField.setPrefWidth(20 * MILLIMETER);
        HBox.setHgrow(theoreticalPointYField, Priority.ALWAYS);
        theoreticalPointYField.setFont(NORMAL);
        theoreticalPointYField.setAlignment(Pos.CENTER);
        this.getChildren().add(theoreticalPointYField);
        theoreticalPointXField = new TextField();
        theoreticalPointXField.setPrefWidth(20 * MILLIMETER);
   	 	HBox.setHgrow(theoreticalPointXField, Priority.ALWAYS);
        theoreticalPointXField.setFont(NORMAL);
        theoreticalPointXField.setAlignment(Pos.CENTER);
        this.getChildren().add(theoreticalPointXField);
        theoreticalPointZField = new TextField();
        theoreticalPointZField.setPrefWidth(15 * MILLIMETER);
   	 	HBox.setHgrow(theoreticalPointZField, Priority.ALWAYS);
        theoreticalPointZField.setFont(NORMAL);
        theoreticalPointZField.setAlignment(Pos.CENTER);
        this.getChildren().add(theoreticalPointZField);
        theoreticalPointSignNameField = new TextField();
        theoreticalPointSignNameField.setPrefWidth(40 * MILLIMETER);
   	 	HBox.setHgrow(theoreticalPointSignNameField, Priority.ALWAYS);
        theoreticalPointSignNameField.setFont(NORMAL);
        theoreticalPointSignNameField.setAlignment(Pos.CENTER);
        this.getChildren().add(theoreticalPointSignNameField);
	}
	
	private void setFieldVisible(boolean[] visible) {
		
		if( visible.length == 1 && visible[0] ) {
			return;
		}
		else if( visible.length == 1 && !visible[0] ) {
			this.setVisible(false);
			return;
		}
		
		for (int i = 0; i < visible.length; i++) {
			
			if( i == 0 && !visible[0] ) {
				rowNumber.setVisible(false);
			}
			else if( i == 1 && !visible[1] ) {
				
				standingPointNameField.setVisible(false);
			}
			else if( i == 2 && !visible[2] ) {
				
				standingPointYField.setVisible(false);
			}
			else if( i == 3 && !visible[3] ) {
				
				standingPointXField.setVisible(false);	
			}
			else if( i == 4 && !visible[4] ) {
				
				standingPointZField.setVisible(false);
			}
			else if( i == 5 && !visible[5] ) {
				
				totalStationHeightField.setVisible(false);	
			}
			else if( i == 6 && !visible[6] ) {
				
				measuredPointNameField.setVisible(false);
			}
			else if( i == 7 && !visible[7] ) {
				
				measuredPointYField.setVisible(false);	
			}
			else if( i == 8 && !visible[8] ) {
				
				measuredPointXField.setVisible(false);
			}
			else if( i == 9 && !visible[9] ) {
				
				measuredPointZField.setVisible(false);
			}
			else if( i == 10 && !visible[10] ) {
				
				measuredPointSignField.setVisible(false);
			}
			else if( i == 11 && !visible[11] ) {
				
				horizontalAngleField.setVisible(false);
			}
			else if( i == 12 && !visible[12] ) {
				
				verticalAngleField.setVisible(false);
			}
			else if( i == 13 && !visible[13] ) {
				
				horizontalDistanceField.setVisible(false);
			}
			else if( i == 14 && !visible[14] ) {
				
				measuredPointSignHeightField.setVisible(false);
			}
			else if( i == 15 && !visible[15] ) {
				
				dateTimeField.setVisible(false);
			}
			else if( i == 16 && !visible[16] ) {
				
				theoreticalPointNameField.setVisible(false);
			}
			else if( i == 17 && !visible[17] ) {
				
				theoreticalPointYField.setVisible(false);
			}
			else if( i == 18 && !visible[18] ) {
				
				theoreticalPointXField.setVisible(false);
			}
			else if( i == 19 && !visible[19] ) {
				
				theoreticalPointZField.setVisible(false);
			}
			else if( i == 20 && !visible[20] ) {
				
				theoreticalPointSignNameField.setVisible(false);
			}
			
			
			
		}
	
	}

	public TextField getRowNumber() {
		return rowNumber;
	}

	public TextField getStandingPointNameField() {
		return standingPointNameField;
	}

	public TextField getStandingPointYField() {
		return standingPointYField;
	}

	public TextField getStandingPointXField() {
		return standingPointXField;
	}

	public TextField getStandingPointZField() {
		return standingPointZField;
	}

	public TextField getTotalStationHeightField() {
		return totalStationHeightField;
	}

	public TextField getMeasuredPointNameField() {
		return measuredPointNameField;
	}

	public TextField getMeasuredPointYField() {
		return measuredPointYField;
	}

	public TextField getMeasuredPointXField() {
		return measuredPointXField;
	}

	public TextField getMeasuredPointZField() {
		return measuredPointZField;
	}

	public TextField getMeasuredPointSignField() {
		return measuredPointSignField;
	}

	public static double getMillimeter() {
		return MILLIMETER;
	}

	public static Font getNormal() {
		return NORMAL;
	}

	public static Font getBold() {
		return BOLD;
	}

	public TextField getHorizontalAngleField() {
		return horizontalAngleField;
	}

	public TextField getVerticalAngleField() {
		return verticalAngleField;
	}

	public TextField getHorizontalDistanceField() {
		return horizontalDistanceField;
	}

	public TextField getMeasuredPointSignHeightField() {
		return measuredPointSignHeightField;
	}

	public TextField getDateTimeField() {
		return dateTimeField;
	}

	public TextField getTheoreticalPointNameField() {
		return theoreticalPointNameField;
	}

	public TextField getTheoreticalPointXField() {
		return theoreticalPointXField;
	}

	public TextField getTheoreticalPointYField() {
		return theoreticalPointYField;
	}

	public TextField getTheoreticalPointZField() {
		return theoreticalPointZField;
	}

	public TextField getTheoreticalPointSignNameField() {
		return theoreticalPointSignNameField;
	}

	
	public void setMeasuredPillarDataController(MeasuredPillarDataController measuredPillarDataController) {
		this.measuredPillarDataController = measuredPillarDataController;
	}
	
	
}
 