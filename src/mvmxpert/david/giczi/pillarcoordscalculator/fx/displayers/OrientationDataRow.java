package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import javafx.geometry.Pos;
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
	
	public OrientationDataRow() {
		this.directionPointId = new TextField();
		this.directionPointY = new TextField();
		this.directionPointX = new TextField();
		this.azimuthAngelValue = new TextField();
		this.distanceValue = new TextField();
		this.directionAngelValue = new TextField();
		this.orientationAngelValue = new TextField();
		this.orientedDirectionValue = new TextField();
		setTextFieldStyle();
		this.getChildren().addAll(directionPointId, directionPointY, 
				directionPointX, azimuthAngelValue, distanceValue, 
				directionAngelValue, orientationAngelValue, orientedDirectionValue);
	}
	
	private void setTextFieldStyle() {
		directionPointId.setPrefWidth(95);
    	directionPointId.setPrefHeight(30);
    	directionPointId.setAlignment(Pos.CENTER);
    	directionPointX.setPrefWidth(95);
    	directionPointX.setPrefHeight(30);
    	directionPointX.setAlignment(Pos.CENTER);
    	directionPointY.setPrefWidth(95);
    	directionPointY.setPrefHeight(30);
    	directionPointY.setAlignment(Pos.CENTER);
    	azimuthAngelValue.setPrefWidth(95);
    	azimuthAngelValue.setPrefHeight(30);
    	azimuthAngelValue.setAlignment(Pos.CENTER);
    	distanceValue.setPrefWidth(95);
    	distanceValue.setPrefHeight(30);
    	distanceValue.setAlignment(Pos.CENTER);
    	directionAngelValue.setPrefWidth(95);
    	directionAngelValue.setPrefHeight(30);
    	directionAngelValue.setAlignment(Pos.CENTER);
    	orientationAngelValue.setPrefWidth(95);
    	orientationAngelValue.setPrefHeight(30);
    	orientationAngelValue.setAlignment(Pos.CENTER);
    	orientedDirectionValue.setPrefWidth(95);
    	orientedDirectionValue.setPrefHeight(30);
    	orientedDirectionValue.setAlignment(Pos.CENTER);
	}
}
