package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class OrientationDataRow extends HBox{

	
	public TextField directionPointId;
	public TextField directionPointY;
	public TextField directionPointX;
	public TextField azimuthAngelValue;
	public TextField distanceValue;
	public TextField directionAngelValue;
	public TextField orientationAngelValue;
	public TextField orientedDirectionValue;
	public CheckBox useData;
	private final static int WIDTH = 90;
	private final static int HEIGHT = 30;
	
	public OrientationDataRow(boolean isHeader) {
		this.directionPointId = new TextField();
		this.directionPointY = new TextField();
		this.directionPointX = new TextField();
		this.azimuthAngelValue = new TextField();
		this.distanceValue = new TextField();
		this.directionAngelValue = new TextField();
		this.orientationAngelValue = new TextField();
		this.orientedDirectionValue = new TextField();
		this.useData = new CheckBox();
		setTextFieldStyle();
		this.getChildren().addAll(directionPointId, directionPointY, 
				directionPointX, azimuthAngelValue, distanceValue, 
				directionAngelValue, orientationAngelValue, orientedDirectionValue);
		if( isHeader ) {
			return;
		}
		this.getChildren().add(useData);
	}
	
	private void setTextFieldStyle() {
		directionPointId.setPrefWidth(WIDTH);
    	directionPointId.setPrefHeight(HEIGHT);
    	directionPointId.setAlignment(Pos.CENTER);
    	directionPointId.setCursor(Cursor.HAND);
    	directionPointX.setPrefWidth(WIDTH);
    	directionPointX.setPrefHeight(HEIGHT);
    	directionPointX.setAlignment(Pos.CENTER);
    	directionPointY.setPrefWidth(WIDTH);
    	directionPointY.setPrefHeight(HEIGHT);
    	directionPointY.setAlignment(Pos.CENTER);
    	azimuthAngelValue.setPrefWidth(WIDTH);
    	azimuthAngelValue.setPrefHeight(HEIGHT);
    	azimuthAngelValue.setAlignment(Pos.CENTER);
    	distanceValue.setPrefWidth(WIDTH);
    	distanceValue.setPrefHeight(HEIGHT);
    	distanceValue.setAlignment(Pos.CENTER);
    	directionAngelValue.setPrefWidth(WIDTH);
    	directionAngelValue.setPrefHeight(HEIGHT);
    	directionAngelValue.setAlignment(Pos.CENTER);
    	orientationAngelValue.setPrefWidth(WIDTH);
    	orientationAngelValue.setPrefHeight(HEIGHT);
    	orientationAngelValue.setAlignment(Pos.CENTER);
    	orientedDirectionValue.setPrefWidth(WIDTH);
    	orientedDirectionValue.setPrefHeight(HEIGHT);
    	orientedDirectionValue.setAlignment(Pos.CENTER);
    	useData.setTranslateX(10);
    	useData.setTranslateY(10);
    	useData.setCursor(Cursor.HAND);
    	useData.setSelected(true);
    	useData.setOnAction( e ->{
    		if( useData.isSelected() ) {
    			directionPointId.setStyle(
    				    "-fx-control-inner-background: white;" +
    				    	    "-fx-text-fill: red;"
    				    	);
    			directionPointY.setStyle("-fx-control-inner-background: white;");
    			directionPointX.setStyle("-fx-control-inner-background: white;");
    			azimuthAngelValue.setStyle("-fx-control-inner-background: white;");
    			distanceValue.setStyle("-fx-control-inner-background: white;");
    			directionAngelValue.setStyle("-fx-control-inner-background: white;");
    			orientationAngelValue.setStyle(
    				    "-fx-control-inner-background: white;" +
    				    	    "-fx-text-fill: blue;"
    				    	);
    			orientedDirectionValue.setStyle(
    				    "-fx-control-inner-background: white;" +
    				    	    "-fx-text-fill: #00CF00;"
    				    	);
    		}
    		else {
    			directionPointId.setStyle(
    				    "-fx-control-inner-background: #F2F2F2;" +
    				    	    "-fx-text-fill: red;"
    				    	);
    			directionPointY.setStyle("-fx-control-inner-background: #F2F2F2;");
    			directionPointX.setStyle("-fx-control-inner-background: #F2F2F2;");
    			azimuthAngelValue.setStyle("-fx-control-inner-background: #F2F2F2;");
    			distanceValue.setStyle("-fx-control-inner-background: #F2F2F2;");
    			directionAngelValue.setStyle("-fx-control-inner-background: #F2F2F2;");
    			orientationAngelValue.setStyle(
    				    "-fx-control-inner-background: #F2F2F2;" +
    				    	    "-fx-text-fill: blue;");
    			orientedDirectionValue.setStyle(
    				    "-fx-control-inner-background: #F2F2F2;" +
    				    	    "-fx-text-fill: #00CF00;");
    		}
    	});
}
}