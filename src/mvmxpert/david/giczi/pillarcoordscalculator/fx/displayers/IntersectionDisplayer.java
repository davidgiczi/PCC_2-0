package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.PLRFileProcess;
import  mvmxpert.david.giczi.pillarcoordscalculator.service.AzimuthAndDistance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class IntersectionDisplayer {


    private final AnchorPane pane = new AnchorPane();
    public MeasuredPillarDataController measuredPillarDataController;
    private ComboBox<String> scaleComboBox;
    private static final double MILLIMETER = 1000.0 / 225.0;
    private static double SCALE;
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 16);

    public IntersectionDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        SCALE = 1000;
        Stage stage = new Stage();
        stage.initOwner(measuredPillarDataController.fxHomeWindow.homeStage);
        stage.setOnCloseRequest(windowEvent -> {
        	measuredPillarDataController.crossedWirePointList.clear();
            measuredPillarDataController.fxHomeWindow.homeStage.show();
        });
        pane.setStyle("-fx-background-color: white");
        addContent();
        ScrollPane scrollPane = getScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        stage.setTitle(PLRFileProcess.FOLDER_PATH + "\\" + PLRFileProcess.PROJECT_FILE_NAME + ".ins");
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
        scroller.setFitToWidth(true);
        scroller.setFitToHeight(true);
        scroller.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroller.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        return scroller;
    }

    private void addContent(){
        addNorthSign();
        addComboBoxForScaleValue();
        if( MeasuredPillarDataController.ELEVATION_MEAS_ONLY ) {
        	addDataForElevationMeasureOnly();
        	addTheoreticalPoint();
        	addDistanceBetweenStandingAndTheoreticalPoint();
        	addDistancesBetweenStartAndTheoreticalAndEndPoint();
        	addStandingPointForElevationMeasureOnly();
        }
        else {
        addIntersectionData();
        addCalculatedAndMeasuredIntersectionPoints();
        addPointsDistanceLine();
        addCoordsDifferencesAndDistance();
        addStandingPoints();
       }
        if( measuredPillarDataController.crossedWirePointList.isEmpty() ) {
        	return;
        }
        addDistanceBetweenCrossedWirePoints();
    }

    private void addNorthSign(){
        ImageView northSign = new ImageView(new Image("/img/north.jpg"));
        northSign.setFitWidth(40 * MILLIMETER);
        northSign.setFitHeight(40 * MILLIMETER);
        northSign.xProperty().bind(pane.widthProperty().divide(17));
        northSign.setY(5 * MILLIMETER);
        pane.getChildren().add(northSign);
    }

    private void copyText(String text){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text.toUpperCase());
        clipboard.setContent(content);
    }

    private void addComboBoxForScaleValue(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "1",
                        "2",
                        "10",
                        "100",
                        "500",
                        "1000",
                        "2000",
                        "4000",
                        "5000",
                        "10000"
                );
        HBox hbox = new HBox();
        scaleComboBox = new ComboBox<>(options);
        scaleComboBox.setValue("1000");
        scaleComboBox
                .setStyle("-fx-background-color: white;-fx-font: 16px \"Book-Antique\";-fx-font-weight: bold;");
        scaleComboBox.setCursor(Cursor.HAND);
        scaleComboBox.setOnAction(e -> setOnActionEvent());
        Label textM = new Label("M= 1: ");
        textM.setFont(Font.font("Book-Antique", FontWeight.BOLD, FontPosture.REGULAR, 16));
        textM.setPadding(new Insets(5.5, 0 , 0 , 0));
        hbox.getChildren().addAll(textM, scaleComboBox);
        hbox.setLayoutX(5 * MILLIMETER);
        hbox.setLayoutY(2 * MILLIMETER);
        pane.getChildren().add(hbox);
    }
    private void setOnActionEvent(){
        String selectedScale = scaleComboBox.getSelectionModel().getSelectedItem();
        SCALE = Integer.parseInt(selectedScale);
        pane.getChildren().clear();
        addContent();
        scaleComboBox.setValue(selectedScale);
    }
    
    private void addTheoreticalPoint() {
    	  Circle theoreticalPointCircle = new Circle();
          theoreticalPointCircle.setRadius(5);
          theoreticalPointCircle.setStroke(Color.RED);
          theoreticalPointCircle.setStrokeWidth(2);
          theoreticalPointCircle.setFill(Color.TRANSPARENT);
          theoreticalPointCircle.setCursor(Cursor.HAND);
          theoreticalPointCircle.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5));
          theoreticalPointCircle.centerYProperty().bind(pane.heightProperty().divide(2));
          Tooltip theoreticalPointTooltip =
                  new Tooltip(measuredPillarDataController.intersection
                          .getIntersectionPoint().getPointID().toUpperCase() +
           "\tY=" + String.format("%.3f",
                          measuredPillarDataController.intersection.getIntersectionPoint().getX_coord())
                          .replace(",",".") + "m\tX=" +
           String.format("%.3f",
                          measuredPillarDataController.intersection.getIntersectionPoint().getY_coord())
                                  .replace(",",".") + "m\th=" +
          String.format("%.3f",
                           measuredPillarDataController.intersection.getIntersectionPoint().getZ_coord())
                                  .replace(",",".") + "m");
          Tooltip.install(theoreticalPointCircle, theoreticalPointTooltip);
          pane.getChildren().add(theoreticalPointCircle);
    }

    private void addCalculatedAndMeasuredIntersectionPoints(){
        Circle intersectionPointCircle = new Circle();
        intersectionPointCircle.setRadius(5);
        intersectionPointCircle.setStroke(Color.RED);
        intersectionPointCircle.setStrokeWidth(2);
        intersectionPointCircle.setFill(Color.TRANSPARENT);
        intersectionPointCircle.setCursor(Cursor.HAND);
        intersectionPointCircle.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5));
        intersectionPointCircle.centerYProperty().bind(pane.heightProperty().divide(2));
        Tooltip intersectionPointTooltip =
                new Tooltip(measuredPillarDataController.intersection
                        .getIntersectionPoint().getPointID().toUpperCase() +
         "\tY=" + String.format("%.3f",
                        measuredPillarDataController.intersection.getIntersectionPoint().getX_coord())
                        .replace(",",".") + "m\tX=" +
         String.format("%.3f",
                        measuredPillarDataController.intersection.getIntersectionPoint().getY_coord())
                                .replace(",",".") + "m\th=" +
        String.format("%.3f",
                         measuredPillarDataController.intersection.getIntersectionPoint().getZ_coord())
                                .replace(",",".") + "m");
        Tooltip.install(intersectionPointCircle, intersectionPointTooltip);
        pane.getChildren().add(intersectionPointCircle);
        if( measuredPillarDataController.intersection.getTheoreticalPointData() == null ) {
        	return;
        }
        Point wireLineTheoreticalPoint = new Point("Elméleti ponthely:\t" + 
        measuredPillarDataController.intersection.getIntersectionPoint().getPointID().toUpperCase(),
        		
        (measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() -
                measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000.0 * MILLIMETER / SCALE,
        (measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord() -
                measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000.0 * MILLIMETER / SCALE);
                       
        Circle wireLineHalfPointCircle = new Circle();
        wireLineHalfPointCircle.setRadius(5);
        wireLineHalfPointCircle.setStroke(Color.MAGENTA);
        wireLineHalfPointCircle.setStrokeWidth(2);
        wireLineHalfPointCircle.setFill(Color.TRANSPARENT);
        wireLineHalfPointCircle.setCursor(Cursor.HAND);
        wireLineHalfPointCircle.centerXProperty()
                .bind(pane.widthProperty().divide(10).multiply(5).add(wireLineTheoreticalPoint.getX_coord()));
        wireLineHalfPointCircle.centerYProperty()
                .bind(pane.heightProperty().divide(2).subtract(wireLineTheoreticalPoint.getY_coord()));
        Tooltip wireLineHalfPointTooltip =
                new Tooltip( wireLineTheoreticalPoint.getPointID() +
                        "\tY=" + String.format("%.3f", measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord())
                        .replace(",",".") +
                        "m\tX=" + String.format("%.3f", measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord())
                        .replace(",",".") + "m");
        Tooltip.install(wireLineHalfPointCircle, wireLineHalfPointTooltip);
        pane.getChildren().add(wireLineHalfPointCircle);
    }
    
    
    private void addStandingPointForElevationMeasureOnly() {
    	if( SCALE < 100){
            return;
        }
        Circle standDingAPointCircle = new Circle();
        standDingAPointCircle.setRadius(5);
        standDingAPointCircle.setStroke(Color.LIMEGREEN);
        standDingAPointCircle.setStrokeWidth(2);
        standDingAPointCircle.setFill(Color.TRANSPARENT);
        standDingAPointCircle.setCursor(Cursor.HAND);
        standDingAPointCircle.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(Math.round((measuredPillarDataController.intersection.getStandingPointA().getX_coord() -
                        measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000)
                        * MILLIMETER / SCALE));
        standDingAPointCircle.centerYProperty().bind(pane.heightProperty().divide(2)
                .subtract(Math.round((measuredPillarDataController.intersection.getStandingPointA().getY_coord() -
                        measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000)
                        * MILLIMETER / SCALE));
        Tooltip standingAPointTooltip =
                new Tooltip(measuredPillarDataController.intersection.getStandingPointA().getPointID().toUpperCase() +
                        "\tY=" + String.format("%.3f",
                                measuredPillarDataController.intersection.getStandingPointA().getX_coord())
                        .replace(",",".") + "m\tX=" +
                        String.format("%.3f",
                                        measuredPillarDataController.intersection.getStandingPointA().getY_coord())
                                .replace(",",".") + "m\th=" +
                        String.format("%.3f",
                                        measuredPillarDataController.intersection.getStandingPointA().getZ_coord())
                                .replace(",",".") + "m");
        Tooltip.install(standDingAPointCircle, standingAPointTooltip);
        pane.getChildren().addAll(standDingAPointCircle);
    }
    
    private void addStandingPoints(){
        if( SCALE < 100){
            return;
        }
        Circle standDingAPointCircle = new Circle();
        standDingAPointCircle.setRadius(5);
        standDingAPointCircle.setStroke(Color.LIMEGREEN);
        standDingAPointCircle.setStrokeWidth(2);
        standDingAPointCircle.setFill(Color.TRANSPARENT);
        standDingAPointCircle.setCursor(Cursor.HAND);
        standDingAPointCircle.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(Math.round((measuredPillarDataController.intersection.getStandingPointA().getX_coord() -
                        measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000)
                        * MILLIMETER / SCALE));
        standDingAPointCircle.centerYProperty().bind(pane.heightProperty().divide(2)
                .subtract(Math.round((measuredPillarDataController.intersection.getStandingPointA().getY_coord() -
                        measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000)
                        * MILLIMETER / SCALE));
        Tooltip standingAPointTooltip =
                new Tooltip(measuredPillarDataController.intersection.getStandingPointA().getPointID().toUpperCase() +
                        "\tY=" + String.format("%.3f",
                                measuredPillarDataController.intersection.getStandingPointA().getX_coord())
                        .replace(",",".") + "m\tX=" +
                        String.format("%.3f",
                                        measuredPillarDataController.intersection.getStandingPointA().getY_coord())
                                .replace(",",".") + "m\th=" +
                        String.format("%.3f",
                                        measuredPillarDataController.intersection.getStandingPointA().getZ_coord())
                                .replace(",",".") + "m");
        Tooltip.install(standDingAPointCircle, standingAPointTooltip);
        Circle standDingBPointCircle = new Circle();
        standDingBPointCircle.setRadius(5);
        standDingBPointCircle.setStroke(Color.LIMEGREEN);
        standDingBPointCircle.setStrokeWidth(2);
        standDingBPointCircle.setFill(Color.TRANSPARENT);
        standDingBPointCircle.setCursor(Cursor.HAND);
        standDingBPointCircle.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(Math.round((measuredPillarDataController.intersection.getStandingPointB().getX_coord() -
                        measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000)
                        * MILLIMETER / SCALE));
        standDingBPointCircle.centerYProperty().bind(pane.heightProperty().divide(2)
                .subtract(Math.round((measuredPillarDataController.intersection.getStandingPointB().getY_coord() -
                        measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000)
                        * MILLIMETER / SCALE));
        Tooltip standingBPointTooltip =
                new Tooltip(measuredPillarDataController.intersection.getStandingPointB().getPointID().toUpperCase() +
                        "\tY=" + String.format("%.3f",
                                measuredPillarDataController.intersection.getStandingPointB().getX_coord())
                        .replace(",",".") + "m\tX=" +
                        String.format("%.3f",
                                        measuredPillarDataController.intersection.getStandingPointB().getY_coord())
                                .replace(",",".") + "m\th=" +
                        String.format("%.3f",
                                        measuredPillarDataController.intersection.getStandingPointB().getZ_coord())
                                .replace(",",".") + "m");
        Tooltip.install(standDingBPointCircle, standingBPointTooltip);
        pane.getChildren().addAll(standDingAPointCircle, standDingBPointCircle);
    }
    
    private void addDistanceBetweenStandingAndTheoreticalPoint() {
    	if( SCALE < 100){
            return;
        }

        Line distanceBetweenStandingAAndIntersectionPointLine = new Line();
        distanceBetweenStandingAAndIntersectionPointLine.setStroke(Color.RED);
        distanceBetweenStandingAAndIntersectionPointLine.getStrokeDashArray().addAll(10d);
        distanceBetweenStandingAAndIntersectionPointLine.setStrokeWidth(2);
        distanceBetweenStandingAAndIntersectionPointLine.setCursor(Cursor.CLOSED_HAND);
        distanceBetweenStandingAAndIntersectionPointLine.startXProperty()
                .bind(pane.widthProperty().divide(10).multiply(5)
                        .add(Math.round((measuredPillarDataController.intersection.getStandingPointA().getX_coord() -
                                measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000)
                                * MILLIMETER / SCALE));
        distanceBetweenStandingAAndIntersectionPointLine.startYProperty()
                .bind(pane.heightProperty().divide(2)
                        .subtract(Math.round((measuredPillarDataController.intersection.getStandingPointA().getY_coord() -
                                measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000)
                                * MILLIMETER / SCALE));
        distanceBetweenStandingAAndIntersectionPointLine.endXProperty()
                .bind(pane.widthProperty().divide(10).multiply(5));
        distanceBetweenStandingAAndIntersectionPointLine.endYProperty()
                .bind(pane.heightProperty().divide(2));
        Tooltip betweenStandingAAndIntersectionPointDistanceTooltip = new Tooltip(String.format("%.2fm",
                        measuredPillarDataController.intersection.distanceBetweenStandingPointAAndIntersectionPointFromA)
                .replace(",", "."));
        Tooltip.install(distanceBetweenStandingAAndIntersectionPointLine,
                betweenStandingAAndIntersectionPointDistanceTooltip);
        pane.getChildren().add(distanceBetweenStandingAAndIntersectionPointLine);
    }
    
    private void addDistanceBetweenCrossedWirePoints() {
    	if( SCALE < 100){
            return;
        }   	
    
        Line crossedWireLine = new Line();
        crossedWireLine.setStroke(Color.BLUE);
        crossedWireLine.getStrokeDashArray().addAll(10d);
        crossedWireLine.setStrokeWidth(2);
        crossedWireLine.setCursor(Cursor.CLOSED_HAND);
        crossedWireLine.startXProperty()
                .bind(pane.widthProperty().divide(10).multiply(5)
                        .add(Math.round((measuredPillarDataController.crossedWirePointList.get(0).getX_coord() - 
                        		measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
        crossedWireLine.startYProperty()
                .bind(pane.heightProperty().divide(2)
                        .subtract(Math.round((measuredPillarDataController.crossedWirePointList.get(0).getY_coord() - 
                        		measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));     
        crossedWireLine.endXProperty()
        .bind(pane.widthProperty().divide(10).multiply(5)
                        .add(Math.round((measuredPillarDataController.crossedWirePointList.get(1).getX_coord() - 
                        		measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
        crossedWireLine.endYProperty()
        .bind(pane.heightProperty().divide(2)
                        .subtract(Math.round((measuredPillarDataController.crossedWirePointList.get(1).getY_coord() - 
                        		measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
        Tooltip crossedWireDistanceTooltip = new Tooltip(String.format("%.2fm",
                        new AzimuthAndDistance(measuredPillarDataController.crossedWirePointList.get(0).getAsPoint(), 
                        		measuredPillarDataController.crossedWirePointList.get(1).getAsPoint()).calcDistance())
                .replace(",", "."));
        Tooltip.install(crossedWireLine,
                crossedWireDistanceTooltip);      
        Circle wireStartPoint = new Circle();
        wireStartPoint.setRadius(5);
        wireStartPoint.setStroke(Color.BLUE);
        wireStartPoint.setStrokeWidth(2);
        wireStartPoint.setFill(Color.TRANSPARENT);
        wireStartPoint.setCursor(Cursor.HAND);
        wireStartPoint.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5)
       		 .add(Math.round((measuredPillarDataController.crossedWirePointList.get(0).getX_coord() - 
             		measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
        wireStartPoint.centerYProperty().bind(pane.heightProperty().divide(2)
        		.subtract(Math.round((measuredPillarDataController.crossedWirePointList.get(0).getY_coord() - 
                		measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
        Tooltip wireStartPointTooltip =
                new Tooltip(measuredPillarDataController.crossedWirePointList.get(0).getPointID().toUpperCase() +
                        "\tY=" + String.format("%.3fm",
                        		measuredPillarDataController.crossedWirePointList.get(0).getX_coord())
                        .replace(",",".") + "\tX=" +
                        String.format("%.3fm",
                        		measuredPillarDataController.crossedWirePointList.get(0).getY_coord())
                                .replace(",",".") + "\tH=" +
                                String.format("%.3fm",
                                		measuredPillarDataController.crossedWirePointList.get(0).getZ_coord())
                                        .replace(",","."));
        Tooltip.install(wireStartPoint, wireStartPointTooltip);   
        Circle wireEndPoint = new Circle();
        wireEndPoint.setRadius(5);
        wireEndPoint.setStroke(Color.BLUE);
        wireEndPoint.setStrokeWidth(2);
        wireEndPoint.setFill(Color.TRANSPARENT);
        wireEndPoint.setCursor(Cursor.HAND);
        wireEndPoint.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5)
       		 .add(Math.round((measuredPillarDataController.crossedWirePointList.get(1).getX_coord() - 
             		measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
        wireEndPoint.centerYProperty().bind(pane.heightProperty().divide(2)
       		 .subtract(Math.round((measuredPillarDataController.crossedWirePointList.get(1).getY_coord() - 
             		measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
        Tooltip wireEndPointTooltip =
                new Tooltip(measuredPillarDataController.crossedWirePointList.get(1).getPointID().toUpperCase() +
                        "\tY=" + String.format("%.3fm",
                        		measuredPillarDataController.crossedWirePointList.get(1).getX_coord())
                        .replace(",",".") + "\tX=" +
                        String.format("%.3fm",
                        		measuredPillarDataController.crossedWirePointList.get(1).getY_coord())
                                .replace(",",".") + "\tH=" +
                                String.format("%.3fm",
                                		measuredPillarDataController.crossedWirePointList.get(1).getZ_coord())
                                        .replace(",","."));
        Tooltip.install(wireEndPoint, wireEndPointTooltip);
        pane.getChildren().addAll(crossedWireLine, wireStartPoint, wireEndPoint);
    }
    
    private void addDistancesBetweenStartAndTheoreticalAndEndPoint() {	
    	 if( measuredPillarDataController.intersection.getLineStartPoint() == null || 
         		measuredPillarDataController.intersection.getLineEndPoint() == null  ){
             return;
         }
    	 if( SCALE < 100){
             return;
         }
    	 AzimuthAndDistance distanceBetweenStartAndEndPoint = 
    			 new AzimuthAndDistance(measuredPillarDataController.intersection.getLineStartPoint(), 
    					 measuredPillarDataController.intersection.getLineEndPoint());
        Line wireLineBetweenStartAndTheoreticalPoint = new Line();
        wireLineBetweenStartAndTheoreticalPoint.setStroke(Color.MAGENTA);
        wireLineBetweenStartAndTheoreticalPoint.setStrokeWidth(2);
        wireLineBetweenStartAndTheoreticalPoint.setCursor(Cursor.CLOSED_HAND);
        wireLineBetweenStartAndTheoreticalPoint.startXProperty().bind(pane.widthProperty().divide(10).multiply(5).
        		add(Math.round((measuredPillarDataController.intersection.getLineStartPoint().getX_coord() - 
        				measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
        wireLineBetweenStartAndTheoreticalPoint.startYProperty().bind(pane.heightProperty().divide(2)
        		.subtract(Math.round((measuredPillarDataController.intersection.getLineStartPoint().getY_coord() - 
        				measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
        wireLineBetweenStartAndTheoreticalPoint.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
        		.add(Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() - 
        				measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
        wireLineBetweenStartAndTheoreticalPoint.endYProperty().bind(pane.heightProperty().divide(2)
        		.subtract(Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord() - 
        				measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
        AzimuthAndDistance distanceBetweenStartAndTheoreticalPoint =
                new AzimuthAndDistance(measuredPillarDataController.intersection.getLineStartPoint(),
                        measuredPillarDataController.intersection.getTheoreticalPointData());
        Tooltip wireLineBetweenStartAndTheoreticalPointTooltip = new Tooltip(String.format("%.2fm",
        		 distanceBetweenStartAndTheoreticalPoint.calcDistance()).replace(",", ".") + "\t[" +
        		String.format("%.2fm",
                        distanceBetweenStartAndEndPoint.calcDistance()).replace(",", ".") + "]");
        Tooltip.install(wireLineBetweenStartAndTheoreticalPoint, wireLineBetweenStartAndTheoreticalPointTooltip);
        
        Line wireLineBetweenEndAndTheoreticalPoint = new Line();
        wireLineBetweenEndAndTheoreticalPoint.setStroke(Color.MAGENTA);
        wireLineBetweenEndAndTheoreticalPoint.setStrokeWidth(2);
        wireLineBetweenEndAndTheoreticalPoint.setCursor(Cursor.CLOSED_HAND);
        wireLineBetweenEndAndTheoreticalPoint.startXProperty().bind(pane.widthProperty().divide(10).multiply(5).
        		add(Math.round((measuredPillarDataController.intersection.getLineEndPoint().getX_coord() - 
        				measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
        wireLineBetweenEndAndTheoreticalPoint.startYProperty().bind(pane.heightProperty().divide(2)
        		.subtract(Math.round((measuredPillarDataController.intersection.getLineEndPoint().getY_coord() - 
        				measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
        wireLineBetweenEndAndTheoreticalPoint.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
        		.add(Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() - 
        				measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
        wireLineBetweenEndAndTheoreticalPoint.endYProperty().bind(pane.heightProperty().divide(2)
        		.subtract(Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord() - 
        				measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
        AzimuthAndDistance distanceBetweenEndAndTheoreticalPoint =
                new AzimuthAndDistance(measuredPillarDataController.intersection.getLineEndPoint(),
                        measuredPillarDataController.intersection.getTheoreticalPointData());
        Tooltip wireLineBetweenEndAndTheoreticalPointTooltip = new Tooltip(String.format("%.2fm",
        		 distanceBetweenEndAndTheoreticalPoint.calcDistance()).replace(",", ".") + "\t[" +
        		String.format("%.2fm",
                        distanceBetweenStartAndEndPoint.calcDistance()).replace(",", ".") + "]");
        Tooltip.install(wireLineBetweenEndAndTheoreticalPoint, wireLineBetweenEndAndTheoreticalPointTooltip);
    
         Circle wireStartPoint = new Circle();
         wireStartPoint.setRadius(5);
         wireStartPoint.setStroke(Color.MAGENTA);
         wireStartPoint.setStrokeWidth(2);
         wireStartPoint.setFill(Color.TRANSPARENT);
         wireStartPoint.setCursor(Cursor.HAND);
         wireStartPoint.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5)
        		 .add(Math.round((measuredPillarDataController.intersection.getLineStartPoint().getX_coord() - 
         				measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
         wireStartPoint.centerYProperty().bind(pane.heightProperty().divide(2)
        		 .subtract(Math.round((measuredPillarDataController.intersection.getLineStartPoint().getY_coord() - 
         				measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
         Tooltip wireStartPointTooltip =
                 new Tooltip(measuredPillarDataController.intersection.getLineStartPoint().getPointID().toUpperCase() +
                         "\tY=" + String.format("%.3f",
                                 measuredPillarDataController.intersection.getLineStartPoint().getX_coord())
                         .replace(",",".") + "m\tX=" +
                         String.format("%.3f",
                                         measuredPillarDataController.intersection.getLineStartPoint().getY_coord())
                                 .replace(",",".") + "m");
         Tooltip.install(wireStartPoint, wireStartPointTooltip);
         Circle wireEndPoint = new Circle();
         wireEndPoint.setRadius(5);
         wireEndPoint.setStroke(Color.MAGENTA);
         wireEndPoint.setStrokeWidth(2);
         wireEndPoint.setFill(Color.TRANSPARENT);
         wireEndPoint.setCursor(Cursor.HAND);
         wireEndPoint.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5)
        		 .add(Math.round((measuredPillarDataController.intersection.getLineEndPoint().getX_coord() - 
         				measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
         wireEndPoint.centerYProperty().bind(pane.heightProperty().divide(2)
        		 .subtract(Math.round((measuredPillarDataController.intersection.getLineEndPoint().getY_coord() - 
         				measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
         Tooltip wireEndPointTooltip =
                 new Tooltip(measuredPillarDataController.intersection.getLineEndPoint().getPointID().toUpperCase() +
                         "\tY=" + String.format("%.3f",
                                 measuredPillarDataController.intersection.getLineEndPoint().getX_coord())
                         .replace(",",".") + "m\tX=" +
                         String.format("%.3f",
                                         measuredPillarDataController.intersection.getLineEndPoint().getY_coord())
                                 .replace(",",".") + "m");
         Tooltip.install(wireEndPoint, wireEndPointTooltip);
         pane.getChildren().addAll(wireLineBetweenStartAndTheoreticalPoint, 
        		 wireLineBetweenEndAndTheoreticalPoint, wireStartPoint, wireEndPoint);
    }
    
    private void addPointsDistanceLine(){
        if( SCALE < 100){
            return;
        }

        Line distanceBetweenStandingAAndIntersectionPointLine = new Line();
        distanceBetweenStandingAAndIntersectionPointLine.setStroke(Color.RED);
        distanceBetweenStandingAAndIntersectionPointLine.getStrokeDashArray().addAll(10d);
        distanceBetweenStandingAAndIntersectionPointLine.setStrokeWidth(2);
        distanceBetweenStandingAAndIntersectionPointLine.setCursor(Cursor.CLOSED_HAND);
        distanceBetweenStandingAAndIntersectionPointLine.startXProperty()
                .bind(pane.widthProperty().divide(10).multiply(5)
                        .add(Math.round((measuredPillarDataController.intersection.getStandingPointA().getX_coord() -
                                measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000)
                                * MILLIMETER / SCALE));
        distanceBetweenStandingAAndIntersectionPointLine.startYProperty()
                .bind(pane.heightProperty().divide(2)
                        .subtract(Math.round((measuredPillarDataController.intersection.getStandingPointA().getY_coord() -
                                measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000)
                                * MILLIMETER / SCALE));
        distanceBetweenStandingAAndIntersectionPointLine.endXProperty()
                .bind(pane.widthProperty().divide(10).multiply(5));
        distanceBetweenStandingAAndIntersectionPointLine.endYProperty()
                .bind(pane.heightProperty().divide(2));
        Tooltip betweenStandingAAndIntersectionPointDistanceTooltip = new Tooltip(String.format("%.2fm",
                        measuredPillarDataController.intersection.distanceBetweenStandingPointAAndIntersectionPointFromA)
                .replace(",", "."));
        Tooltip.install(distanceBetweenStandingAAndIntersectionPointLine,
                betweenStandingAAndIntersectionPointDistanceTooltip);
        Line distanceBetweenStandingBAndIntersectionPointLine = new Line();
        distanceBetweenStandingBAndIntersectionPointLine.setStroke(Color.RED);
        distanceBetweenStandingBAndIntersectionPointLine.getStrokeDashArray().addAll(10d);
        distanceBetweenStandingBAndIntersectionPointLine.setStrokeWidth(2);
        distanceBetweenStandingBAndIntersectionPointLine.setCursor(Cursor.CLOSED_HAND);
        distanceBetweenStandingBAndIntersectionPointLine.startXProperty()
                .bind(pane.widthProperty().divide(10).multiply(5)
                        .add(Math.round((measuredPillarDataController.intersection.getStandingPointB().getX_coord() -
                                measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000)
                                * MILLIMETER / SCALE));
        distanceBetweenStandingBAndIntersectionPointLine.startYProperty()
                .bind(pane.heightProperty().divide(2)
                        .subtract(Math.round((measuredPillarDataController.intersection.getStandingPointB().getY_coord() -
                                measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000)
                                * MILLIMETER / SCALE));
        distanceBetweenStandingBAndIntersectionPointLine.endXProperty()
                .bind(pane.widthProperty().divide(10).multiply(5));
        distanceBetweenStandingBAndIntersectionPointLine.endYProperty()
                .bind(pane.heightProperty().divide(2));
        Tooltip betweenStandingBAndIntersectionPointDistanceTooltip = new Tooltip(String.format("%.2fm",
                        measuredPillarDataController.intersection.distanceBetweenStandingPointBAndIntersectionPointFromB)
                .replace(",", "."));
        Tooltip.install(distanceBetweenStandingBAndIntersectionPointLine,
                betweenStandingBAndIntersectionPointDistanceTooltip);
        pane.getChildren().addAll(distanceBetweenStandingAAndIntersectionPointLine,
                distanceBetweenStandingBAndIntersectionPointLine);
        if( measuredPillarDataController.intersection.getLineStartPoint() == null || 
        		measuredPillarDataController.intersection.getLineEndPoint() == null  ){
            return;
        }
        Point startPoint =
                new Point("WireLineStartPoint",
Math.round((measuredPillarDataController.intersection.getLineStartPoint().getX_coord() -
 measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000.0) * MILLIMETER / SCALE,
Math.round((measuredPillarDataController.intersection.getLineStartPoint().getY_coord() -
measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000.0) * MILLIMETER / SCALE);
        Point endPoint =
new Point("WireLineEndPoint",
Math.round((measuredPillarDataController.intersection.getLineEndPoint().getX_coord() -
measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000.0) * MILLIMETER / SCALE,
Math.round((measuredPillarDataController.intersection.getLineEndPoint().getY_coord() -
measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000.0) * MILLIMETER / SCALE);
        AzimuthAndDistance distanceBetweenStartAndEndPoint =
                new AzimuthAndDistance(measuredPillarDataController.intersection.getLineStartPoint(),
                        measuredPillarDataController.intersection.getLineEndPoint());
       Line wireLineBetweenStartAndTheoreticalPoint = new Line();
       wireLineBetweenStartAndTheoreticalPoint.setStroke(Color.MAGENTA);
       wireLineBetweenStartAndTheoreticalPoint.setStrokeWidth(2);
       wireLineBetweenStartAndTheoreticalPoint.setCursor(Cursor.CLOSED_HAND);
       wireLineBetweenStartAndTheoreticalPoint.startXProperty().bind(pane.widthProperty().divide(10).multiply(5).add(startPoint.getX_coord()));
       wireLineBetweenStartAndTheoreticalPoint.startYProperty().bind(pane.heightProperty().divide(2).subtract(startPoint.getY_coord()));
       wireLineBetweenStartAndTheoreticalPoint.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
    		   .add(Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() - 
    				   measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
       wireLineBetweenStartAndTheoreticalPoint.endYProperty().bind(pane.heightProperty().divide(2)
    		   .subtract(Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord() - 
    				   measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
       AzimuthAndDistance distanceBetweenStartAndTheoreticalPoint =
               new AzimuthAndDistance(measuredPillarDataController.intersection.getLineStartPoint(),
                       measuredPillarDataController.intersection.getTheoreticalPointData());
       Tooltip wireLineBetweenStartAndTheoreticalPointTooltip = new Tooltip(String.format("%.2fm",
    		  distanceBetweenStartAndTheoreticalPoint.calcDistance()).replace(",", ".") + "\t[" +
               String.format("%.2fm", distanceBetweenStartAndEndPoint.calcDistance()).replace(",", ".") + "]");
       Tooltip.install(wireLineBetweenStartAndTheoreticalPoint, wireLineBetweenStartAndTheoreticalPointTooltip);
       
       Line wireLineBetweenEndAndTheoreticalPoint = new Line();
       wireLineBetweenEndAndTheoreticalPoint.setStroke(Color.MAGENTA);
       wireLineBetweenEndAndTheoreticalPoint.setStrokeWidth(2);
       wireLineBetweenEndAndTheoreticalPoint.setCursor(Cursor.CLOSED_HAND);
       wireLineBetweenEndAndTheoreticalPoint.startXProperty().bind(pane.widthProperty().divide(10).multiply(5).add(endPoint.getX_coord()));
       wireLineBetweenEndAndTheoreticalPoint.startYProperty().bind(pane.heightProperty().divide(2).subtract(endPoint.getY_coord()));
       wireLineBetweenEndAndTheoreticalPoint.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
    		   .add(Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() - 
    				   measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000) * MILLIMETER / SCALE));
       wireLineBetweenEndAndTheoreticalPoint.endYProperty().bind(pane.heightProperty().divide(2)
    		   .subtract(Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord() - 
    				   measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000) * MILLIMETER / SCALE));
       AzimuthAndDistance distanceBetweenEndAndTheoreticalPoint =
               new AzimuthAndDistance(measuredPillarDataController.intersection.getLineEndPoint(),
                       measuredPillarDataController.intersection.getTheoreticalPointData());
       Tooltip wireLineBetweenEndAndTheoreticalPointTooltip = new Tooltip(String.format("%.2fm",
    		   distanceBetweenEndAndTheoreticalPoint.calcDistance()).replace(",", ".") + "\t[" +
                                   String.format("%.2fm", distanceBetweenStartAndEndPoint.calcDistance()).replace(",", ".") + "]");
       Tooltip.install(wireLineBetweenEndAndTheoreticalPoint, wireLineBetweenEndAndTheoreticalPointTooltip);
       
        Circle wireStartPoint = new Circle();
        wireStartPoint.setRadius(5);
        wireStartPoint.setStroke(Color.MAGENTA);
        wireStartPoint.setStrokeWidth(2);
        wireStartPoint.setFill(Color.TRANSPARENT);
        wireStartPoint.setCursor(Cursor.HAND);
        wireStartPoint.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5).add(startPoint.getX_coord()));
        wireStartPoint.centerYProperty().bind(pane.heightProperty().divide(2).subtract(startPoint.getY_coord()));
        Tooltip wireStartPointTooltip =
                new Tooltip(measuredPillarDataController.intersection.getLineStartPoint().getPointID().toUpperCase() +
                        "\tY=" + String.format("%.3f",
                                measuredPillarDataController.intersection.getLineStartPoint().getX_coord())
                        .replace(",",".") + "m\tX=" +
                        String.format("%.3f",
                                        measuredPillarDataController.intersection.getLineStartPoint().getY_coord())
                                .replace(",",".") + "m");
        Tooltip.install(wireStartPoint, wireStartPointTooltip);
        Circle wireEndPoint = new Circle();
        wireEndPoint.setRadius(5);
        wireEndPoint.setStroke(Color.MAGENTA);
        wireEndPoint.setStrokeWidth(2);
        wireEndPoint.setFill(Color.TRANSPARENT);
        wireEndPoint.setCursor(Cursor.HAND);
        wireEndPoint.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5).add(endPoint.getX_coord()));
        wireEndPoint.centerYProperty().bind(pane.heightProperty().divide(2).subtract(endPoint.getY_coord()));
        Tooltip wireEndPointTooltip =
                new Tooltip(measuredPillarDataController.intersection.getLineEndPoint().getPointID().toUpperCase() +
                        "\tY=" + String.format("%.3f",
                                measuredPillarDataController.intersection.getLineEndPoint().getX_coord())
                        .replace(",",".") + "m\tX=" +
                        String.format("%.3f",
                                        measuredPillarDataController.intersection.getLineEndPoint().getY_coord())
                                .replace(",",".") + "m");
        Tooltip.install(wireEndPoint, wireEndPointTooltip);
       pane.getChildren().addAll(wireLineBetweenStartAndTheoreticalPoint, 
    		   wireLineBetweenEndAndTheoreticalPoint, wireStartPoint, wireEndPoint);
 }


 private void addCoordsDifferencesAndDistance(){
	 if( measuredPillarDataController.intersection.getTheoreticalPointData() == null ){
         return;
     }
      Line deltaXLine = new Line();
      deltaXLine.setStroke(Color.LIMEGREEN);
      deltaXLine.setStrokeWidth(2);
      deltaXLine.setCursor(Cursor.CLOSED_HAND);
      deltaXLine.startXProperty().bind(pane.widthProperty().divide(10).multiply(5));
      deltaXLine.startYProperty().bind(pane.heightProperty().divide(2));
      deltaXLine.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
      .add((Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() -
                measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000))
                * MILLIMETER /SCALE));
      deltaXLine.endYProperty().bind(pane.heightProperty().divide(2));
      Tooltip deltaXTooltip = new Tooltip("ΔY=" + String.format("%+.1fcm",
                      100 * (measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() -
                              measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()))
                .replace(",", "."));
     Tooltip.install(deltaXLine, deltaXTooltip);
     Line deltaYLine = new Line();
     deltaYLine.setStroke(Color.BLUE);
     deltaYLine.setStrokeWidth(2);
     deltaYLine.setCursor(Cursor.CLOSED_HAND);
     deltaYLine.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
             .add((Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() -
             measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000))
             * MILLIMETER /SCALE));
     deltaYLine.startYProperty().bind(pane.heightProperty().divide(2));
     deltaYLine.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
             .add((Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() -
             measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000))
             * MILLIMETER /SCALE));
     deltaYLine.endYProperty().bind(pane.heightProperty().divide(2)
     .subtract((Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord() -
      measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000)) * MILLIMETER /SCALE));
     Tooltip deltaYTooltip = new Tooltip("ΔX=" + String.format("%+.1fcm",
                     100 * (measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord() -
                             measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()))
             .replace(",", "."));
     Tooltip.install(deltaYLine, deltaYTooltip);
     Line distanceLine = new Line();
     distanceLine.setStroke(Color.RED);
     distanceLine.getStrokeDashArray().addAll(10d);
     distanceLine.setStrokeWidth(2);
     distanceLine.setCursor(Cursor.CLOSED_HAND);
     distanceLine.startXProperty().bind(pane.widthProperty().divide(10).multiply(5));
     distanceLine.startYProperty().bind(pane.heightProperty().divide(2));
     distanceLine.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
             .add((Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() -
                     measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()) * 1000))
                     * MILLIMETER /SCALE));
     distanceLine.endYProperty().bind(pane.heightProperty().divide(2)
     .subtract((Math.round((measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord() -
     measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()) * 1000)) * MILLIMETER /SCALE));
     Tooltip distanceTooltip = new Tooltip("t=" + String.format("%.1fcm",
             100 *  measuredPillarDataController.intersection.distanceBetweenIntersectionPointAndTheoreticalPoint)
             .replace(",", "."));
     Tooltip.install(distanceLine, distanceTooltip);
    pane.getChildren().addAll(deltaXLine, deltaYLine, distanceLine);
 }

 	private void addDataForElevationMeasureOnly() {
 		Text newPointIdText = new Text(measuredPillarDataController
                .intersection.getIntersectionPoint().getPointID().toUpperCase());
        newPointIdText.xProperty().bind(pane.widthProperty().divide(22).multiply(4));
        newPointIdText.setY(10 * MILLIMETER);
        newPointIdText.setFont(boldFont);
        newPointIdText.setFill(Color.RED);
        Text newPointXText = new Text("Y (mért)[m]");
        newPointXText.setFont(boldFont);
        newPointXText.xProperty().bind(pane.widthProperty().divide(21).multiply(6));
        newPointXText.setY(5 * MILLIMETER);
        Text newPointX = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPoint().getX_coord()).replace(",", "."));
        newPointX.xProperty().bind(pane.widthProperty().divide(22).multiply(6));
        newPointX.setY(10 * MILLIMETER);
        newPointX.setFont(normalFont);
        Text newPointYText = new Text("X (mért)[m]");
        newPointYText.setFont(boldFont);
        newPointYText.xProperty().bind(pane.widthProperty().divide(21).multiply(8));
        newPointYText.setY(5 * MILLIMETER);
        Text newPointY = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPoint().getY_coord()).replace(",", "."));
        newPointY.setFont(normalFont);
        newPointY.xProperty().bind(pane.widthProperty().divide(22).multiply(8));
        newPointY.setY(10 * MILLIMETER);
        Text newPointZText = new Text("h (mért)[m]");
        newPointZText.setFont(boldFont);
        newPointZText.xProperty().bind(pane.widthProperty().divide(21).multiply(10));
        newPointZText.setY(5 * MILLIMETER);
        Text newPointZ = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPoint().getZ_coord()).replace(",", "."));
        newPointZ.setFont(normalFont);
        newPointZ.xProperty().bind(pane.widthProperty().divide(22).multiply(10));
        newPointZ.setY(10 * MILLIMETER);
        pane.getChildren().addAll(newPointIdText, newPointXText, newPointX,
                newPointYText, newPointY, newPointZText, newPointZ);
       copyText(measuredPillarDataController
               .intersection.getIntersectionPoint().getPointID() + " " +
       String.format("%9.3f",measuredPillarDataController.intersection.getIntersectionPoint().getX_coord())
               .replace(",", ".") + " " +
       String.format("%9.3f", measuredPillarDataController.intersection.getIntersectionPoint().getY_coord())
               .replace(",", ".")+ " " +
       String.format("%6.3f", measuredPillarDataController.intersection.getIntersectionPoint().getZ_coord())
               .replace(",", "."));
 	}
 
    private void addIntersectionData(){
        Text newPointIdText = new Text(measuredPillarDataController
                .intersection.getIntersectionPoint().getPointID().toUpperCase());
        newPointIdText.xProperty().bind(pane.widthProperty().divide(22).multiply(4));
        newPointIdText.setY(10 * MILLIMETER);
        newPointIdText.setFont(boldFont);
        newPointIdText.setFill(Color.RED);
        Text newPointXText = new Text("Y (mért)[m]");
        newPointXText.setFont(boldFont);
        newPointXText.xProperty().bind(pane.widthProperty().divide(21).multiply(6));
        newPointXText.setY(5 * MILLIMETER);
        Text newPointX = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPoint().getX_coord()).replace(",", "."));
        newPointX.xProperty().bind(pane.widthProperty().divide(22).multiply(6));
        newPointX.setY(10 * MILLIMETER);
        newPointX.setFont(normalFont);
        Text newPointYText = new Text("X (mért)[m]");
        newPointYText.setFont(boldFont);
        newPointYText.xProperty().bind(pane.widthProperty().divide(21).multiply(8));
        newPointYText.setY(5 * MILLIMETER);
        Text newPointY = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPoint().getY_coord()).replace(",", "."));
        newPointY.setFont(normalFont);
        newPointY.xProperty().bind(pane.widthProperty().divide(22).multiply(8));
        newPointY.setY(10 * MILLIMETER);
        Text newPointZText = new Text("h (mért)[m]");
        newPointZText.setFont(boldFont);
        newPointZText.xProperty().bind(pane.widthProperty().divide(21).multiply(10));
        newPointZText.setY(5 * MILLIMETER);
        Text newPointZ = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPoint().getZ_coord()).replace(",", "."));
        newPointZ.setFont(normalFont);
        newPointZ.xProperty().bind(pane.widthProperty().divide(22).multiply(10));
        newPointZ.setY(10 * MILLIMETER);
        Text deltaXText = new Text("ΔY [cm]");
        deltaXText.setFont(boldFont);
        deltaXText.xProperty().bind(pane.widthProperty().divide(21).multiply(14));
        deltaXText.setY(5 * MILLIMETER);
        Text deltaX = new Text(String.format("%+20.1f",
                measuredPillarDataController
                        .intersection.getTheoreticalPointData() == null ?
                        0.0 :
             100 * ( measuredPillarDataController.intersection.getTheoreticalPointData().getX_coord() -
                     measuredPillarDataController.intersection.getIntersectionPoint().getX_coord()))
                .replace(",", "."));
        deltaX.xProperty().bind(pane.widthProperty().divide(22).multiply(14));
        deltaX.setY(10 * MILLIMETER);
        deltaX.setFont(normalFont);
        Text deltaYText = new Text("ΔX [cm]");
        deltaYText.setFont(boldFont);
        deltaYText.xProperty().bind(pane.widthProperty().divide(21).multiply(16));
        deltaYText.setY(5 * MILLIMETER);
        Text deltaY = new Text(String.format("%+20.1f",
                measuredPillarDataController
                        .intersection.getTheoreticalPointData() == null ?
                        0.0 :
               100 * (measuredPillarDataController.intersection.getTheoreticalPointData().getY_coord() -
                       measuredPillarDataController.intersection.getIntersectionPoint().getY_coord()))
                .replace(",", "."));
        deltaY.xProperty().bind(pane.widthProperty().divide(22).multiply(16));
        deltaY.setY(10 * MILLIMETER);
        deltaY.setFont(normalFont);
        Text distanceText = new Text("t [cm]");
        distanceText.setFont(boldFont);
        distanceText.xProperty().bind(pane.widthProperty().divide(21).multiply(12));
        distanceText.setY(5 * MILLIMETER);
        Text distanceValue = new Text(String.format("%20.1f",
                        measuredPillarDataController
                                .intersection.getTheoreticalPointData() == null ?
                          0.0 :
                 100 *  measuredPillarDataController.intersection.distanceBetweenIntersectionPointAndTheoreticalPoint)
                .replace(",", "."));
        distanceValue.xProperty().bind(pane.widthProperty().divide(22).multiply(12));
        distanceValue.setY(10 * MILLIMETER);
        distanceValue.setFont(normalFont);
        Text deltaZText = new Text("Δh [cm]");
        deltaZText.setFont(boldFont);
        deltaZText.xProperty().bind(pane.widthProperty().divide(21).multiply(18));
        deltaZText.setY(5 * MILLIMETER);
        Text deltaZ = new Text(String.format("%+20.1f",
                 100 * (measuredPillarDataController.intersection.getIntersectionPointFromA().getZ_coord()
                - measuredPillarDataController.intersection.getIntersectionPointFromB().getZ_coord()))
                .replace(",", "."));
        deltaZ.xProperty().bind(pane.widthProperty().divide(22).multiply(18));
        deltaZ.setY(10 * MILLIMETER);
        deltaZ.setFont(normalFont);
        Text fromStandingAText = new Text(
                measuredPillarDataController.intersection.getStandingPointA().getPointID().toUpperCase() +"→");
        fromStandingAText.setFont(boldFont);
        fromStandingAText.xProperty().bind(pane.widthProperty().divide(21).multiply(4));
        fromStandingAText.setY(20 * MILLIMETER);
        Text intersectionPointXFromStandingA = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPointFromA().getX_coord()).replace(",", "."));
        intersectionPointXFromStandingA.xProperty().bind(pane.widthProperty().divide(22).multiply(6));
        intersectionPointXFromStandingA.setY(20 * MILLIMETER);
        intersectionPointXFromStandingA.setFont(normalFont);
        Text intersectionPointYFromStandingA = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPointFromA().getY_coord()).replace(",", "."));
        intersectionPointYFromStandingA.xProperty().bind(pane.widthProperty().divide(22).multiply(8));
        intersectionPointYFromStandingA.setY(20 * MILLIMETER);
        intersectionPointYFromStandingA.setFont(normalFont);
        Text intersectionPointZFromStandingA = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPointFromA().getZ_coord()).replace(",", "."));
        intersectionPointZFromStandingA.xProperty().bind(pane.widthProperty().divide(22).multiply(10));
        intersectionPointZFromStandingA.setY(20 * MILLIMETER);
        intersectionPointZFromStandingA.setFont(normalFont);
        Text standingAndIntersectionDistanceText = new Text("t [m]");
        standingAndIntersectionDistanceText.setFont(boldFont);
        standingAndIntersectionDistanceText.xProperty().bind(pane.widthProperty().divide(21).multiply(12));
        standingAndIntersectionDistanceText.setY(15 * MILLIMETER);
        Text deltaDistanceText = new Text("Δt [cm]");
        deltaDistanceText.setFont(boldFont);
        deltaDistanceText.xProperty().bind(pane.widthProperty().divide(21).multiply(14));
        deltaDistanceText.setY(15 * MILLIMETER);
        Text deltaAzimuthText = new Text("Δδ");
        deltaAzimuthText.setFont(boldFont);
        deltaAzimuthText.xProperty().bind(pane.widthProperty().divide(22).multiply(17));
        deltaAzimuthText.setY(15 * MILLIMETER);
        Text deltaDistanceFromStandingA = new Text(String.format("%+20.1f",
                        measuredPillarDataController
                                .intersection.getTheoreticalPointData() == null ?
                                0.0 :
                100 * (measuredPillarDataController.intersection.distanceBetweenStandingPointAAndTheoreticalPoint -
                       measuredPillarDataController.intersection.distanceBetweenStandingPointAAndIntersectionPointFromA))
                        .replace(",", "."));
        deltaDistanceFromStandingA.xProperty().bind(pane.widthProperty().divide(22).multiply(14));
        deltaDistanceFromStandingA.setY(20 * MILLIMETER);
        deltaDistanceFromStandingA.setFont(normalFont);
        Text deltaAzimuthFromStandingA =  new Text(String.format("%s",
        		measuredPillarDataController
                .intersection.getTheoreticalPointData() == null ?
                        "-" :
                measuredPillarDataController.intersection.deltaAzimuthAtStandingPointA));
        deltaAzimuthFromStandingA.xProperty().bind(pane.widthProperty().divide(22).multiply(17));
        deltaAzimuthFromStandingA.setY(20 * MILLIMETER);
        deltaAzimuthFromStandingA.setFont(normalFont);
        Text fromStandingBText = new Text(
                measuredPillarDataController.intersection.getStandingPointB().getPointID().toUpperCase() +"→");
        fromStandingBText.setFont(boldFont);
        fromStandingBText.xProperty().bind(pane.widthProperty().divide(21).multiply(4));
        fromStandingBText.setY(25 * MILLIMETER);
        Text intersectionPointXFromStandingB = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPointFromB().getX_coord()).replace(",", "."));
        intersectionPointXFromStandingB.xProperty().bind(pane.widthProperty().divide(22).multiply(6));
        intersectionPointXFromStandingB.setY(25 * MILLIMETER);
        intersectionPointXFromStandingB.setFont(normalFont);
        Text intersectionPointYFromStandingB = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPointFromB().getY_coord()).replace(",", "."));
        intersectionPointYFromStandingB.xProperty().bind(pane.widthProperty().divide(22).multiply(8));
        intersectionPointYFromStandingB.setY(25 * MILLIMETER);
        intersectionPointYFromStandingB.setFont(normalFont);
        Text intersectionPointZFromStandingB = new Text(String.format("%20.3f", measuredPillarDataController
                .intersection.getIntersectionPointFromB().getZ_coord()).replace(",", "."));
        intersectionPointZFromStandingB.xProperty().bind(pane.widthProperty().divide(22).multiply(10));
        intersectionPointZFromStandingB.setY(25 * MILLIMETER);
        intersectionPointZFromStandingB.setFont(normalFont);
        Text distanceBetweenStartWireAndIntersectionPoint = new Text(String.format("%20.2f",
        		measuredPillarDataController
                .intersection.getLineStartPoint() == null ?
                                0.0 :
        measuredPillarDataController.intersection.distanceBetweenStartWireAndIntersectionPoint)
                .replace(",", "."));
        distanceBetweenStartWireAndIntersectionPoint.xProperty()
                .bind(pane.widthProperty().divide(22).multiply(12));
        distanceBetweenStartWireAndIntersectionPoint.setY(20 * MILLIMETER);
        distanceBetweenStartWireAndIntersectionPoint.setFont(normalFont);
        Text distanceBetweenEndWireAndIntersectionPoint = new Text(String.format("%20.2f", 
                		 measuredPillarDataController
                         .intersection.getLineEndPoint() == null ?
                                0.0 :
                                measuredPillarDataController.intersection.distanceBetweenEndWireAndIntersectionPoint)
                .replace(",", "."));
        distanceBetweenEndWireAndIntersectionPoint.xProperty()
                .bind(pane.widthProperty().divide(22).multiply(12));
        distanceBetweenEndWireAndIntersectionPoint.setY(25 * MILLIMETER);
        distanceBetweenEndWireAndIntersectionPoint.setFont(normalFont);
        Text deltaDistanceFromStandingB = new Text(String.format("%+20.1f",
        		measuredPillarDataController
                .intersection.getTheoreticalPointData() == null ?
                                0.0 :
                100 * (measuredPillarDataController.intersection.distanceBetweenStandingPointBAndTheoreticalPoint -
                       measuredPillarDataController.intersection.distanceBetweenStandingPointBAndIntersectionPointFromB))
                        .replace(",", "."));
        deltaDistanceFromStandingB.xProperty().bind(pane.widthProperty().divide(22).multiply(14));
        deltaDistanceFromStandingB.setY(25 * MILLIMETER);
        deltaDistanceFromStandingB.setFont(normalFont);
        Text deltaAzimuthFromStandingB = new Text(String.format("%s",
        		measuredPillarDataController
                .intersection.getTheoreticalPointData() == null
                ?
                        "-" :
                measuredPillarDataController.intersection.deltaAzimuthAtStandingPointB));
        deltaAzimuthFromStandingB.xProperty().bind(pane.widthProperty().divide(22).multiply(17));
        deltaAzimuthFromStandingB.setY(25 * MILLIMETER);
        deltaAzimuthFromStandingB.setFont(normalFont);
        Text gammaText = new Text("γ:");
        gammaText.setFont(boldFont);
        gammaText.xProperty().bind(pane.widthProperty().divide(22).multiply(5));
        gammaText.setY(30 * MILLIMETER);
        Text gamma = new Text(measuredPillarDataController.intersection.getIntersectionAngleValueAtNewPoint());
        gamma.setFont(normalFont);
        gamma.xProperty().bind(pane.widthProperty().divide(22).multiply(6.5));
        gamma.setY(30 * MILLIMETER);

        pane.getChildren().addAll(newPointIdText, newPointXText, newPointX,
                newPointYText, newPointY, newPointZText, newPointZ,
                deltaXText, deltaX, deltaYText, deltaY, deltaZText, deltaZ,
                distanceText, distanceValue, fromStandingAText, intersectionPointXFromStandingA,
                intersectionPointYFromStandingA, intersectionPointZFromStandingA,
                deltaDistanceText, deltaDistanceFromStandingA, deltaAzimuthText,
                deltaAzimuthFromStandingA, fromStandingBText, intersectionPointXFromStandingB,
                intersectionPointYFromStandingB, intersectionPointZFromStandingB,
                standingAndIntersectionDistanceText, distanceBetweenStartWireAndIntersectionPoint,
                distanceBetweenEndWireAndIntersectionPoint, deltaDistanceFromStandingB,
                deltaAzimuthFromStandingB, gammaText, gamma);
       copyText(measuredPillarDataController
               .intersection.getIntersectionPoint().getPointID() + " " +
       String.format("%9.3f",measuredPillarDataController.intersection.getIntersectionPoint().getX_coord())
               .replace(",", ".") + " " +
       String.format("%9.3f", measuredPillarDataController.intersection.getIntersectionPoint().getY_coord())
               .replace(",", ".")+ " " +
       String.format("%6.3f", measuredPillarDataController.intersection.getIntersectionPoint().getZ_coord())
               .replace(",", "."));
    }


}
