package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import javafx.geometry.Pos;
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
	private final static int WIDTH = 85;
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
		setTextFieldStyle(isHeader);
		this.getChildren().addAll(directionPointId, directionPointY, 
				directionPointX, azimuthAngelValue, distanceValue, 
				directionAngelValue, orientationAngelValue, orientedDirectionValue);
		if( isHeader ) {
			return;
		}
		this.getChildren().add(useData);
	}
	
	private void setTextFieldStyle(boolean isHeader) {
		directionPointId.setPrefWidth(WIDTH);
    	directionPointId.setPrefHeight(HEIGHT);
    	directionPointId.setAlignment(Pos.CENTER);
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
    	if( isHeader ) {
    		return;
    	}
    	useData.setTranslateX(10);
    	useData.setTranslateY(10);
    	
}
}