package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class MeasDataRow extends HBox {

	
	private TextField rowNumber;
	private TextField standingPointNameField;
	private TextField standingPointYField;
	private TextField standingPointXField;
	private TextField standingPointZField;
	private TextField totalStationHeightField;
	private TextField directionPointNameField;
	private TextField directionPointYField;
	private TextField directionPointXField;
	private TextField directionPointZField;
	private TextField directionPointSignField;
	private TextField horizontalAngleField;
	private TextField verticalAngleField;
	private TextField horizontalDistanceField;
	private TextField directionPointSignHeightField;
	private TextField dateTimeField;
	private TextField theoreticalPointNameField;
	private TextField theoreticalPointXField;
	private TextField theoreticalPointYField;
	private TextField theoreticalPointZField;
	private TextField theoreticalPointSignNameField;
	private static final double MILLIMETER = 1000.0 / 225.0;
	private static final Font NORMAL = Font.font("Book Antiqua", FontWeight.NORMAL, 14);
	
	
	public MeasDataRow(boolean...isVisible) {
		createRow();
		setFieldVisible(isVisible);	
	}
	
	private void createRow() {
		rowNumber = new TextField();
   	 	rowNumber.setMaxWidth(11 * MILLIMETER);
   	 	HBox.setHgrow(rowNumber, Priority.ALWAYS);
        rowNumber.setFont(NORMAL);
        rowNumber.setAlignment(Pos.CENTER);
        this.getChildren().add(rowNumber);
        standingPointNameField = new TextField();
        standingPointNameField.setMaxWidth(21 * MILLIMETER);
   	 	HBox.setHgrow(standingPointNameField, Priority.ALWAYS);
        standingPointNameField.setFont(NORMAL);
        standingPointNameField.setAlignment(Pos.CENTER);
        this.getChildren().add(standingPointNameField);
        standingPointYField = new TextField();
        standingPointYField.setMaxWidth(20 * MILLIMETER);
        HBox.setHgrow(standingPointYField, Priority.ALWAYS);
        standingPointYField.setFont(NORMAL);
        standingPointYField.setAlignment(Pos.CENTER);
        this.getChildren().add(standingPointYField);
        standingPointXField = new TextField();
        standingPointXField.setMaxWidth(20 * MILLIMETER);
   	 	HBox.setHgrow(standingPointXField, Priority.ALWAYS);
        standingPointXField.setFont(NORMAL);
        standingPointXField.setAlignment(Pos.CENTER);
        this.getChildren().add(standingPointXField);
        standingPointZField = new TextField();
        standingPointZField.setMaxWidth(15 * MILLIMETER);
        HBox.setHgrow(standingPointZField, Priority.ALWAYS);
        standingPointZField.setFont(NORMAL);
        standingPointZField.setAlignment(Pos.CENTER);
        this.getChildren().add(standingPointZField);
        totalStationHeightField = new TextField();
        totalStationHeightField.setMaxWidth(16 * MILLIMETER);
        HBox.setHgrow(totalStationHeightField, Priority.ALWAYS);
        totalStationHeightField.setFont(NORMAL);
        totalStationHeightField.setAlignment(Pos.CENTER);
        this.getChildren().add(totalStationHeightField);
        directionPointNameField = new TextField();
        directionPointNameField.setMaxWidth(15 * MILLIMETER);
        HBox.setHgrow(directionPointNameField, Priority.ALWAYS);
        directionPointNameField.setFont(NORMAL);
        directionPointNameField.setAlignment(Pos.CENTER);
        this.getChildren().add(directionPointNameField);
        directionPointYField = new TextField();
        directionPointYField.setMaxWidth(20 * MILLIMETER);
        HBox.setHgrow(directionPointYField, Priority.ALWAYS);
        directionPointYField.setFont(NORMAL);
        directionPointYField.setAlignment(Pos.CENTER);
        this.getChildren().add(directionPointYField);
        directionPointXField = new TextField();
        directionPointXField.setMaxWidth(20 * MILLIMETER);
        HBox.setHgrow(directionPointXField, Priority.ALWAYS);
        directionPointXField.setFont(NORMAL);
        directionPointXField.setAlignment(Pos.CENTER);
        this.getChildren().add(directionPointXField);
        directionPointZField = new TextField();
        directionPointZField.setMaxWidth(15 * MILLIMETER);
        HBox.setHgrow(directionPointZField, Priority.ALWAYS);
        directionPointZField.setFont(NORMAL);
        directionPointZField.setAlignment(Pos.CENTER);
        this.getChildren().add(directionPointZField);
        directionPointSignField = new TextField();  
        directionPointSignField.setMaxWidth(40 * MILLIMETER);
   	 	HBox.setHgrow(directionPointSignField, Priority.ALWAYS);
        directionPointSignField.setFont(NORMAL);
        directionPointSignField.setAlignment(Pos.CENTER);
        this.getChildren().add(directionPointSignField);
        horizontalAngleField = new TextField();
        horizontalAngleField.setMaxWidth(25 * MILLIMETER);
   	 	HBox.setHgrow(horizontalAngleField, Priority.ALWAYS);
        horizontalAngleField.setFont(NORMAL);
        horizontalAngleField.setAlignment(Pos.CENTER);
        this.getChildren().add(horizontalAngleField);
        verticalAngleField = new TextField();
        verticalAngleField.setMaxWidth(25 * MILLIMETER);
        HBox.setHgrow(verticalAngleField, Priority.ALWAYS);
        verticalAngleField.setFont(NORMAL);
        verticalAngleField.setAlignment(Pos.CENTER);
        this.getChildren().add(verticalAngleField);
        horizontalDistanceField = new TextField();
        horizontalDistanceField.setMaxWidth(20 * MILLIMETER);
   	 	HBox.setHgrow(horizontalDistanceField, Priority.ALWAYS);
        horizontalDistanceField.setFont(NORMAL);
        horizontalDistanceField.setAlignment(Pos.CENTER);
        this.getChildren().add(horizontalDistanceField);
        directionPointSignHeightField = new TextField();
        directionPointSignHeightField.setMaxWidth(16 * MILLIMETER);
   	 	HBox.setHgrow(directionPointSignHeightField, Priority.ALWAYS);
        directionPointSignHeightField.setFont(NORMAL);
        directionPointSignHeightField.setAlignment(Pos.CENTER);
        this.getChildren().add(directionPointSignHeightField);
        dateTimeField = new TextField();
        dateTimeField.setMaxWidth(20 * MILLIMETER);
   	 	HBox.setHgrow(dateTimeField, Priority.ALWAYS);
        dateTimeField.setFont(NORMAL);
        dateTimeField.setAlignment(Pos.CENTER);
        this.getChildren().add(dateTimeField);
        theoreticalPointNameField = new TextField();
        theoreticalPointNameField.setMaxWidth(40 * MILLIMETER);
   	 	HBox.setHgrow(theoreticalPointNameField, Priority.ALWAYS);
        theoreticalPointNameField.setFont(NORMAL);
        theoreticalPointNameField.setAlignment(Pos.CENTER);
        this.getChildren().add(theoreticalPointNameField);
        theoreticalPointYField = new TextField();
        theoreticalPointYField.setMaxWidth(20 * MILLIMETER);
        HBox.setHgrow(theoreticalPointYField, Priority.ALWAYS);
        theoreticalPointYField.setFont(NORMAL);
        theoreticalPointYField.setAlignment(Pos.CENTER);
        this.getChildren().add(theoreticalPointYField);
        theoreticalPointXField = new TextField();
        theoreticalPointXField.setMaxWidth(20 * MILLIMETER);
   	 	HBox.setHgrow(theoreticalPointXField, Priority.ALWAYS);
        theoreticalPointXField.setFont(NORMAL);
        theoreticalPointXField.setAlignment(Pos.CENTER);
        this.getChildren().add(theoreticalPointXField);
        theoreticalPointZField = new TextField();
        theoreticalPointZField.setMaxWidth(15 * MILLIMETER);
   	 	HBox.setHgrow(theoreticalPointZField, Priority.ALWAYS);
        theoreticalPointZField.setFont(NORMAL);
        theoreticalPointZField.setAlignment(Pos.CENTER);
        this.getChildren().add(theoreticalPointZField);
        theoreticalPointSignNameField = new TextField();
        theoreticalPointSignNameField.setMaxWidth(20 * MILLIMETER);
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
				
				directionPointNameField.setVisible(false);
			}
			else if( i == 7 && !visible[7] ) {
				
				directionPointYField.setVisible(false);	
			}
			else if( i == 8 && !visible[8] ) {
				
				directionPointXField.setVisible(false);
			}
			else if( i == 9 && !visible[9] ) {
				
				directionPointZField.setVisible(false);
			}
			else if( i == 10 && !visible[10] ) {
				
				directionPointSignField.setVisible(false);
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
				
				directionPointSignHeightField.setVisible(false);
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

	public TextField getDirectionPointNameField() {
		return directionPointNameField;
	}

	public TextField getDirectionPointYField() {
		return directionPointYField;
	}

	public TextField getDirectionPointXField() {
		return directionPointXField;
	}

	public TextField getDirectionPointZField() {
		return directionPointZField;
	}

	public TextField getDirectionPointSignField() {
		return directionPointSignField;
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

	public TextField getDirectionPointSignHeightField() {
		return directionPointSignHeightField;
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
	

}
 