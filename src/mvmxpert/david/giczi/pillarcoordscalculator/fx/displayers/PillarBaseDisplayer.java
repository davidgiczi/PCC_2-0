package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import mvmxpert.david.giczi.pillarcoordscalculator.service.MeasPoint;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.PLRFileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.service.AzimuthAndDistance;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PolarPoint;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.PointType;
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
import javafx.scene.input.MouseButton;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PillarBaseDisplayer {
    private final AnchorPane pane = new AnchorPane();
    public Stage stage;
    public MeasuredPillarDataController measuredPillarDataController;
    private List<MeasPoint> transformedPillarBasePoints;
    private ComboBox<String> scaleComboBox;
    private static final double MILLIMETER = 1000.0 / 225.0;
    private static double SCALE;
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 16);

    public PillarBaseDisplayer(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        SCALE = 100;
        stage = new Stage();
        stage.initOwner(measuredPillarDataController.fxHomeWindow.homeStage);
        stage.setOnCloseRequest(windowEvent -> {
            measuredPillarDataController.fxHomeWindow.homeStage.show();
            measuredPillarDataController.init();
            measuredPillarDataController.setCreatedInputPillarDataWindow(true);
            measuredPillarDataController.fileProcess.setPccData(null);
        	measuredPillarDataController.fileProcess.setPillarBaseMeasData(null);
        	measuredPillarDataController.pillarBaseDisplayer = null;
        	if( measuredPillarDataController.pillarBaseDifferenceDisplayer != null ) {
        		measuredPillarDataController.pillarBaseDifferenceDisplayer.stage.hide();
        	}
        });
        pane.setStyle("-fx-background-color: white");
        pane.setOnMouseClicked(mouseEvent -> {
            if( mouseEvent.getButton() == MouseButton.SECONDARY ){
                if( measuredPillarDataController.measuredPillarData.getPillarTopPoints().isEmpty() ) {
                    measuredPillarDataController.getInfoAlert(
                            "Az oszlop magasságára/dőlésére vonatkozó adatok nem jeleníthetők meg",
                            "Az oszlop csúcsára vonatkozó mérési adatok nem kerültek beolvasásra.");
                    return;
                }
                if( measuredPillarDataController.pillarBaseDifferenceDisplayer == null ||
                !measuredPillarDataController.pillarBaseDifferenceDisplayer.stage.isShowing()){
                    measuredPillarDataController.pillarBaseDifferenceDisplayer =
                            new PillarBaseDifferenceDisplayer(measuredPillarDataController);
                }

            }
        });
        addContent();
        ScrollPane scrollPane = getScrollPane(pane);
        Scene scene = new Scene(scrollPane);
        stage.setTitle(PLRFileProcess.FOLDER_PATH + "\\" + PLRFileProcess.PROJECT_FILE_NAME + ".plr");
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
        addCenterPillarData();
        addComboBoxForScaleValue();
        getTransformedPillarBaseCoordsForDisplayer();
        drawPillarCenterDifferences();
        if( measuredPillarDataController.measuredPillarData.getPillarBasePoints().size() == 4 ){
            addPillarAxis();
        }
        addPillarBase();
        addCircleForBasePoints();
        addNextPillarDirection();
        addPreviousPillarDirection();
        addSmallScaleDistanceInformation();
        addDirectionInformation();
    }

    private void addNorthSign(){
        ImageView northSign = new ImageView(new Image("/img/north.jpg"));
        northSign.setFitWidth(40 * MILLIMETER);
        northSign.setFitHeight(40 * MILLIMETER);
        northSign.xProperty().bind(pane.widthProperty().divide(20));
        northSign.setY(5 * MILLIMETER);
        pane.getChildren().add(northSign);
    }

    private void addCenterPillarData(){
      Text idText = new Text(measuredPillarDataController.fileProcess.pccData == null ? 
      						measuredPillarDataController.pillarBaseProjectFileData == null ?
      						measuredPillarDataController.inputPillarDataWindow.centerPillarIDField.getText().trim() :	
      						measuredPillarDataController.pillarBaseProjectFileData.get(0) : 
      						measuredPillarDataController.fileProcess.pccData.get(1));
        idText.xProperty().bind(pane.widthProperty().divide(22).multiply(5));
        idText.setY(10 * MILLIMETER);
        idText.setFont(boldFont);
        idText.setFill(Color.MAGENTA);
        Text designedXText = new Text("Y (tervezett)");
        designedXText.setFont(boldFont);
        designedXText.xProperty().bind(pane.widthProperty().divide(21).multiply(6));
        designedXText.setY(5 * MILLIMETER);
        Text designedX = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getX_coord()).replace(",", "."));
        designedX.xProperty().bind(pane.widthProperty().divide(22).multiply(6));
        designedX.setY(10 * MILLIMETER);
        designedX.setFont(normalFont);
        Text designedYText = new Text("X (tervezett)");
        designedYText.setFont(boldFont);
        designedYText.xProperty().bind(pane.widthProperty().divide(21).multiply(8));
        designedYText.setY(5 * MILLIMETER);
        Text designedY = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarCenterPoint().getY_coord()).replace(",", "."));
        designedY.setFont(normalFont);
        designedY.xProperty().bind(pane.widthProperty().divide(22).multiply(8));
        designedY.setY(10 * MILLIMETER);
        Text measXText = new Text("Y (mért)");
        measXText.setFont(boldFont);
        measXText.xProperty().bind(pane.widthProperty().divide(21).multiply(10));
        measXText.setY(5 * MILLIMETER);
        Text measX = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarBaseCenterPoint().getX_coord()).replace(",", "."));
       measX.xProperty().bind(pane.widthProperty().divide(22).multiply(10));
       measX.setY(10 * MILLIMETER);
       measX.setFont(normalFont);
        Text measYText = new Text("X (mért)");
        measYText.setFont(boldFont);
        measYText.xProperty().bind(pane.widthProperty().divide(21).multiply(12));
        measYText.setY(5 * MILLIMETER);
       Text measY = new Text(String.format("%20.3f", measuredPillarDataController
                .measuredPillarData.getPillarBaseCenterPoint().getY_coord()).replace(",", "."));
        measY.xProperty().bind(pane.widthProperty().divide(22).multiply(12));
        measY.setY(10 * MILLIMETER);
        measY.setFont(normalFont);
        Text deltaXText = new Text("Nyomvonalban [cm]");
        deltaXText.setFont(boldFont);
        deltaXText.xProperty().bind(pane.widthProperty().divide(21).multiply(14));
        deltaXText.setY(5 * MILLIMETER);
        Text deltaX = new Text(String.format("%+30.1f", 100 * getXDifferenceOnMainLine()).replace(",", "."));
        deltaX.xProperty().bind(pane.widthProperty().divide(22).multiply(14));
        deltaX.setY(10 * MILLIMETER);
        deltaX.setFont(normalFont);
        Text deltaYText = new Text("Nyomvonalra merőlegesen [cm]");
        deltaYText.setFont(boldFont);
        deltaYText.xProperty().bind(pane.widthProperty().divide(21).multiply(17));
        deltaYText.setY(5 * MILLIMETER);
        Text deltaY = new Text(String.format("%+30.1f", 100 * getYDifferenceOnMainLine()).replace(",", "."));
        deltaY.xProperty().bind(pane.widthProperty().divide(22).multiply(18));
        deltaY.setY(10 * MILLIMETER);
        deltaY.setFont(normalFont);
        Text errorMarginTextForX = getErrorMarginTextForXDifferenceOnMainLine();
        Text errorMarginTextForY = getErrorMarginTextForYDifferenceOnMainLine();
        pane.getChildren().addAll(idText, designedXText, designedX,
                designedYText, designedY, measXText, measX, measYText, measY,
                deltaXText, deltaX, errorMarginTextForX, deltaYText, deltaY, errorMarginTextForY);
        setDataToClipboard();
    }
    
    private double getXDifferenceOnMainLine() {
    	try {
            AzimuthAndDistance mainLineData =
                    new AzimuthAndDistance(new Point("teoCenter",
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."))),
                            new Point("direction",
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_X.getText().replace(",", ".")), 
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_Y.getText().replace(",", "."))));
            AzimuthAndDistance differenceData =
                    new AzimuthAndDistance(new Point("teoCenter",
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."))),
                            new Point("measuredCenter",
               measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord(), 
               measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()));   
        return differenceData.calcDistance()
                * Math.cos(mainLineData.calcAzimuth() - differenceData.calcAzimuth());
    	}
    	catch (NumberFormatException e) {
			return Double.NaN;
		}
    }
    
    private Text getErrorMarginTextForXDifferenceOnMainLine() {
    	AzimuthAndDistance mainLineData;
    	try {
    	 mainLineData = new AzimuthAndDistance(new Point("teoCenter",
          Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
          Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."))),
                         new Point("direction",
          Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_X.getText().replace(",", ".")), 
          Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_Y.getText().replace(",", "."))));
    	}
    	catch (NumberFormatException e) {
			return new Text("-");
		}
    	DecimalFormat df = new DecimalFormat("0.0");
    	Text errorMarginText = new Text("|" + df.format(250 * mainLineData.calcDistance() / 1000.0).replace(",", ".") + "cm|");
    	errorMarginText.xProperty().bind(pane.widthProperty().divide(22).multiply(17));
        errorMarginText.setY(10 * MILLIMETER);
        errorMarginText.setFont(boldFont);
    	errorMarginText.setFill((Math.abs(getXDifferenceOnMainLine()) > (2.5 * mainLineData.calcDistance() / 1000.0)) ? Color.RED : Color.GREEN );
    	return errorMarginText;
    }
    
    private Text getErrorMarginTextForYDifferenceOnMainLine() {
    	AzimuthAndDistance mainLineData;
    	try {
    	 mainLineData = new AzimuthAndDistance(new Point("teoCenter",
          Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
          Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."))),
                         new Point("direction",
          Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_X.getText().replace(",", ".")), 
          Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_Y.getText().replace(",", "."))));
    	}
    	catch (NumberFormatException e) {
			return new Text("-");
		}
    	DecimalFormat df = new DecimalFormat("0.0");
    	Text errorMarginText = new Text("|" + df.format(300 * mainLineData.calcDistance() / 1000.0).replace(",", ".") + "cm|");
    	errorMarginText.xProperty().bind(pane.widthProperty().divide(22).multiply(21));
        errorMarginText.setY(10 * MILLIMETER);
        errorMarginText.setFont(boldFont);
    	errorMarginText.setFill((Math.abs(getYDifferenceOnMainLine()) > (3 * mainLineData.calcDistance() / 1000.0)) ? Color.RED : Color.GREEN );
    	return errorMarginText;
    }

    private double getYDifferenceOnMainLine() {
    	try {
        AzimuthAndDistance mainLineData =
                new AzimuthAndDistance(new Point("teoCenter",
         Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
         Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."))),
                        new Point("direction",
         Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_X.getText().replace(",", ".")), 
         Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_Y.getText().replace(",", "."))));
        AzimuthAndDistance differenceData =
                new AzimuthAndDistance(new Point("teoCenter",
         Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
         Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."))),
                        new Point("measuredCenter",
           measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord(), 
           measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()));
        return differenceData.calcDistance()
                * Math.sin(mainLineData.calcAzimuth() - differenceData.calcAzimuth());
    	}
    	catch (NumberFormatException e) {
			return Double.NaN;
		}
    }

    public void setDataToClipboard(){
    	String deltaX = String.format("%+3.1f", 100 * getXDifferenceOnMainLine()).replace(",", ".");
    	String deltaY = String.format("%+3.1f", 100 * getYDifferenceOnMainLine()).replace(",", ".");
        
        copyText((measuredPillarDataController.fileProcess.pccData == null ? 
        		measuredPillarDataController.pillarBaseProjectFileData == null ?
				measuredPillarDataController.inputPillarDataWindow.centerPillarIDField.getText().trim() :	
				measuredPillarDataController.pillarBaseProjectFileData.get(0) : 
				measuredPillarDataController.fileProcess.pccData.get(1)) + "." + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint()
                        .getX_coord()).replace(",", ".") + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarCenterPoint()
                        .getY_coord()).replace(",", ".") + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarBaseCenterPoint()
                        .getX_coord()).replace(",", ".") + "\t" +
                String.format("%10.3f", measuredPillarDataController
                        .measuredPillarData.getPillarBaseCenterPoint()
                        .getY_coord()).replace(",", ".") + "\t" +
                        deltaX + "\t" + deltaY);
    }

    private void copyText(String text){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    private void getTransformedPillarBaseCoordsForDisplayer() {
        transformedPillarBasePoints = new ArrayList<>();
        double X = measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord();
        double Y = measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord();
        for (MeasPoint pillarBasePoint : measuredPillarDataController.measuredPillarData.getPillarBasePoints()) {
            MeasPoint point = new MeasPoint(pillarBasePoint.getPointID(),
                    Math.round((pillarBasePoint.getX_coord() - X) * 1000.0) * MILLIMETER / SCALE,
                    Math.round((pillarBasePoint.getY_coord() - Y) * 1000.0) * MILLIMETER / SCALE,
                    pillarBasePoint.getZ_coord(), PointType.ALAP);
        transformedPillarBasePoints.add(point);
        }
    }
    
    private Point getTransformControlDirectionPoint() {
 	   if(FXHomeWindow.homeController.controlDirectionPoint == null ) {
 		   return null;
 	   }
 	   return new Point(FXHomeWindow.homeController.controlDirectionPoint.getPointID(),
                Math.round((FXHomeWindow.homeController.controlDirectionPoint.getX_coord() - 
                		measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord()) 
                		* 1000.0) * MILLIMETER / SCALE,
                Math.round((FXHomeWindow.homeController.controlDirectionPoint.getY_coord() - 
                		measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()) 
                		* 1000.0) * MILLIMETER / SCALE);
    }

    private void addPillarBase(){
        if( measuredPillarDataController.measuredPillarData.getPillarBasePoints().size() == 1 || 50 > SCALE ){
            return;
        }

        for (int i = 0;  i < transformedPillarBasePoints.size(); i++) {
            Line line = new Line();
            line.setStroke(Color.BLUE);
            line.setStrokeWidth(2);
            line.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                    .add(transformedPillarBasePoints.get(i).getX_coord()));
            line.startYProperty().bind(pane.heightProperty().divide(2)
                    .subtract(transformedPillarBasePoints.get(i).getY_coord()));
            String distance;
            if( i == transformedPillarBasePoints.size() - 1 ){
                line.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                        .add(transformedPillarBasePoints.get(0).getX_coord()));
                line.endYProperty().bind(pane.heightProperty().divide(2)
                        .subtract(transformedPillarBasePoints.get(0).getY_coord()));
                distance = String.format("%.2fm",
                        new AzimuthAndDistance(new Point("1",
                                measuredPillarDataController.measuredPillarData.getPillarBasePoints().get(i).getX_coord(),
                                measuredPillarDataController.measuredPillarData.getPillarBasePoints().get(i).getY_coord()),
                                new Point("2",
                                        measuredPillarDataController
                                                .measuredPillarData.getPillarBasePoints().get(0).getX_coord(),
                                        measuredPillarDataController
                                                .measuredPillarData.getPillarBasePoints().get(0).getY_coord())
                        ).calcDistance()).replace(",", ".");
            }
            else {
                line.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                        .add(transformedPillarBasePoints.get(i + 1).getX_coord()));
                line.endYProperty().bind(pane.heightProperty().divide(2)
                        .subtract(transformedPillarBasePoints.get(i + 1).getY_coord()));
               distance = String.format("%.2fm",
                        new AzimuthAndDistance(new Point("1",
                                measuredPillarDataController.measuredPillarData.getPillarBasePoints().get(i).getX_coord(),
                                measuredPillarDataController.measuredPillarData.getPillarBasePoints().get(i).getY_coord()),
                                new Point("2",
                                        measuredPillarDataController
                                                .measuredPillarData.getPillarBasePoints().get(i + 1).getX_coord(),
                                        measuredPillarDataController
                                                .measuredPillarData.getPillarBasePoints().get(i + 1).getY_coord())
                        ).calcDistance()).replace(",", ".");
            }
            line.setCursor(Cursor.CLOSED_HAND);
            Tooltip distanceTip = new Tooltip(distance);
            Tooltip.install(line, distanceTip);
            pane.getChildren().addAll(line);
        }
    }

    private void drawPillarCenterDifferences() {
    	if( SCALE > 10 ) {
    		return;
    	}
    	addTheoreticalPillarCenterPoint();
    	addMeasuredPillarCenterPoint();
    	addLargeScaleDistanceInformation();
    	addForwardDirection();
    	addBackwardDirection();
    	addForwardDifferences();
    }
    
    private void addMeasuredPillarCenterPoint() {
    	Point teoCenter;
    	try {
    	teoCenter = new Point("teoCenter",
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", ".")));		
    	}
    	catch (NumberFormatException e) {
			return;
		}
      double X = measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord();
      double Y = measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord();
      Point transformedCenter = new Point("transformedCenter",
                  Math.round((X - teoCenter.getX_coord()) * 1000.0) * MILLIMETER / SCALE,
                  Math.round((Y - teoCenter.getY_coord()) * 1000.0) * MILLIMETER / SCALE);
      Circle measuredCenter = new Circle();
      measuredCenter.setRadius(5);
      measuredCenter.setStroke(Color.BLUE);
      measuredCenter.setStrokeWidth(2);
      measuredCenter.setFill(Color.TRANSPARENT);
      measuredCenter.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5).add(transformedCenter.getX_coord()));
      measuredCenter.centerYProperty().bind(pane.heightProperty().divide(2).subtract(transformedCenter.getY_coord()));
      Tooltip tooltip = new Tooltip(measuredPillarDataController.measuredPillarData
              .getPillarBaseCenterPoint().getPointID() +  
              "\tY=" + String.format("%.3fm",  X).replace(",", ".") +
              "\tX=" + String.format("%.3fm",  Y).replace(",", "."));
      Tooltip.install(measuredCenter, tooltip);
      measuredCenter.setCursor(Cursor.HAND);
      pane.getChildren().add(measuredCenter);  
    }
    
    private void addTheoreticalPillarCenterPoint() {
    Circle teoCenter = new Circle();
    teoCenter.setRadius(5);
    teoCenter.setStroke(Color.MAGENTA);
    teoCenter.setStrokeWidth(2);
    teoCenter.setFill(Color.TRANSPARENT);
    teoCenter.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5));
    teoCenter.centerYProperty().bind(pane.heightProperty().divide(2));
    Tooltip tooltip = new Tooltip(measuredPillarDataController.measuredPillarData
            .getPillarBaseCenterPoint().getPointID() +   
    "\tY=" + measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".") +
    "\t\tX=" + measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", "."));
    Tooltip.install(teoCenter, tooltip);
    teoCenter.setCursor(Cursor.HAND);
    pane.getChildren().add(teoCenter);  
 }
    
    private void addForwardDifferences() {
    	Point teoCenter;
    	Point direction;
    	try {
    		teoCenter = new Point("teoCenter",
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", ".")));
    		
             direction = new Point("direction",
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_X.getText().replace(",", ".")), 
             Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.directionPillarField_Y.getText().replace(",", ".")));	
    	}
    	catch (NumberFormatException e) {
			return;
		}
    	double X = measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord();
        double Y = measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord();
        Point measuredCenter = new Point("measuredCenter",
                    Math.round((X - teoCenter.getX_coord()) * 1000.0) * MILLIMETER / SCALE,
                    Math.round((Y - teoCenter.getY_coord()) * 1000.0) * MILLIMETER / SCALE);
    	AzimuthAndDistance mainLineDirection = new AzimuthAndDistance(teoCenter, direction);
          PolarPoint slavePoint = new PolarPoint(new Point("transformedCenter", 0, 0),
          1000 * getXDifferenceOnMainLine() * MILLIMETER / SCALE, mainLineDirection.calcAzimuth(),"forwardXDifference");
          Line forwardXDifference = new Line();
          forwardXDifference.setStrokeWidth(2);
          forwardXDifference.setStroke(Color.LIMEGREEN);
          forwardXDifference.startXProperty()
                  .bind(pane.widthProperty()
                          .divide(10)
                          .multiply(5));
          forwardXDifference.startYProperty()
                  .bind(pane.heightProperty()
                          .divide(2));
          forwardXDifference.endXProperty()
                  .bind(pane.widthProperty()
                          .divide(10)
                          .multiply(5).add(slavePoint.calcPolarPoint().getX_coord()));
          forwardXDifference.endYProperty()
                  .bind(pane.heightProperty()
                          .divide(2)
                          .subtract(slavePoint.calcPolarPoint().getY_coord()));
          Tooltip forwardXDistanceTooltip = new Tooltip(String.format("%+3.1fcm", 
               		100 * getXDifferenceOnMainLine()).replace(",", "."));
          Tooltip.install(forwardXDifference, forwardXDistanceTooltip);
          forwardXDifference.setCursor(Cursor.CLOSED_HAND);
          Line forwardYDifference = new Line();
          forwardYDifference.setStrokeWidth(2);
          forwardYDifference.setStroke(Color.LIMEGREEN);
          forwardYDifference.startXProperty()
                  .bind(pane.widthProperty()
                          .divide(10)
                          .multiply(5).add(slavePoint.calcPolarPoint().getX_coord()));
          forwardYDifference.startYProperty()
                  .bind(pane.heightProperty()
                          .divide(2)
                          .subtract(slavePoint.calcPolarPoint().getY_coord()));
          forwardYDifference.endXProperty()
                          .bind(pane.widthProperty()
                                  .divide(10)
                                  .multiply(5).add(measuredCenter.getX_coord()));
          forwardYDifference.endYProperty()
                          .bind(pane.heightProperty()
                                  .divide(2).subtract(measuredCenter.getY_coord()));
          Tooltip forwardYDistanceTooltip = new Tooltip(String.format("%+3.1fcm", 100 * getYDifferenceOnMainLine()).replace(",", "."));
          Tooltip.install(forwardYDifference, forwardYDistanceTooltip);
          forwardYDifference.setCursor(Cursor.CLOSED_HAND);
          Line forwardDistance = new Line();
          forwardDistance.setStrokeWidth(2);
          forwardDistance.setStroke(Color.LIMEGREEN);
          forwardDistance.getStrokeDashArray().addAll(10d);
          forwardDistance.startXProperty()
                  .bind(pane.widthProperty()
                          .divide(10)
                          .multiply(5));
          forwardDistance.startYProperty()
                  .bind(pane.heightProperty()
                          .divide(2));
          forwardDistance.endXProperty()
                  .bind(pane.widthProperty()
                          .divide(10)
                          .multiply(5).add(measuredCenter.getX_coord()));
          forwardDistance.endYProperty()
                  .bind(pane.heightProperty()
                          .divide(2).subtract(measuredCenter.getY_coord()));
          Tooltip forwardDistanceTooltip = new Tooltip(String.format("%3.1fcm",
                  100 * Math.sqrt(
                          Math.pow(teoCenter.getX_coord() -
                                  measuredPillarDataController.measuredPillarData
                                          .getPillarBaseCenterPoint().getX_coord(), 2) +
                                  Math.pow(teoCenter.getY_coord() -
                                          measuredPillarDataController.measuredPillarData
                                                  .getPillarBaseCenterPoint().getY_coord(), 2)))
                  .replace(",", ".") );
          Tooltip.install(forwardDistance, forwardDistanceTooltip);
          forwardDistance.setCursor(Cursor.CLOSED_HAND);
          pane.getChildren().addAll(forwardXDifference, forwardYDifference, forwardDistance);
      }	
    
    
    private void addForwardDirection() {
    	Point pillarCenterPoint =  new Point("pillarCenterPoint", 0.0, 0.0);
        Point directionPillarPoint = new Point("transformedDirectionPoint",
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord()),
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()));
        AzimuthAndDistance mainLineDirection =
                new AzimuthAndDistance(pillarCenterPoint, directionPillarPoint);
        PolarPoint startPoint = new PolarPoint(pillarCenterPoint,
                3 * MILLIMETER, mainLineDirection.calcAzimuth(),
                "forwardDirection");

        PolarPoint endPoint = new PolarPoint(startPoint.calcPolarPoint(),
                (1000 * Math.abs(getXDifferenceOnMainLine()) + 30) * MILLIMETER / SCALE,
                mainLineDirection.calcAzimuth(),
                "forwardDirection");

        Line forwardDirection = new Line();
        forwardDirection.setStrokeWidth(2);
        forwardDirection.setStroke(Color.MAGENTA);
        forwardDirection.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(startPoint.calcPolarPoint().getX_coord()));
        forwardDirection.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2).subtract(startPoint.calcPolarPoint().getY_coord()));
        forwardDirection.endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
        forwardDirection.endYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(endPoint.calcPolarPoint().getY_coord()));
        pane.getChildren().add(forwardDirection);
        addArrow(endPoint.calcPolarPoint(), startPoint.calcPolarPoint());
        setText(measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getPointID(),
                endPoint.calcPolarPoint(), Color.RED, 16);
    }
   
    private void addBackwardDirection() {
    	 Point pillarCenterPoint =  new Point("pillarCenterPoint", 0.0, 0.0);
         Point directionPoint = new Point("transformedDirectionPoint",
                 (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord() -
                         measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord()),
                 (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord() -
                         measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()));
         AzimuthAndDistance baseLineData = new AzimuthAndDistance(pillarCenterPoint, directionPoint);
         double rotation = measuredPillarDataController.measuredPillarData.isRightRotationAngle() ?
        		  measuredPillarDataController.measuredPillarData.radRotation : 
       			   - measuredPillarDataController.measuredPillarData.radRotation;
         PolarPoint startPoint = new PolarPoint(pillarCenterPoint, 3 * MILLIMETER,
                 baseLineData.calcAzimuth() + rotation,"prevPoint");
         
         PolarPoint endPoint = new PolarPoint(startPoint.calcPolarPoint(),
                 (1000 * Math.abs(getXDifferenceOnMainLine()) + 30) * MILLIMETER / SCALE,
                 baseLineData.calcAzimuth() + rotation,
                 "backwardDirection");

         Line backwardDirection = new Line();
         backwardDirection.setStrokeWidth(2);
         backwardDirection.setStroke(Color.MAGENTA);
         backwardDirection.startXProperty()
                 .bind(pane.widthProperty()
                         .divide(10)
                         .multiply(5).add(startPoint.calcPolarPoint().getX_coord()));
         backwardDirection.startYProperty()
                 .bind(pane.heightProperty()
                         .divide(2).subtract(startPoint.calcPolarPoint().getY_coord()));
         backwardDirection.endXProperty()
                 .bind(pane.widthProperty()
                         .divide(10)
                         .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
         backwardDirection.endYProperty()
                 .bind(pane.heightProperty()
                         .divide(2)
                         .subtract(endPoint.calcPolarPoint().getY_coord()));
         pane.getChildren().add(backwardDirection);
         addArrow(endPoint.calcPolarPoint(), startPoint.calcPolarPoint());
         int centerID = Integer
                 .parseInt(measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getPointID());
         int directionID = Integer
                 .parseInt(measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getPointID());
         setText(centerID < directionID ? String.valueOf((centerID - 1)) :
                         String.valueOf((centerID + 1)),
                 endPoint.calcPolarPoint(), Color.MAGENTA, 16);
    }
    
    
    private void addComboBoxForScaleValue(){
        ObservableList<String> options =
                FXCollections.observableArrayList(
                		"1",
                		"2",
                		"4",
                		"10",
                        "50",
                        "100",
                        "200",
                        "400",
                        "500"
                );
        HBox hbox = new HBox();
        scaleComboBox = new ComboBox<>(options);
        scaleComboBox.setValue("100");
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

    private void addCircleForBasePoints(){
    	if( 50 > SCALE ) {
    		return;
    	}
        for (int i = 0; i < transformedPillarBasePoints.size(); i++) {
            Circle circle = new Circle();
            circle.setRadius(5);
            circle.setStroke(Color.MAGENTA);
            circle.setStrokeWidth(2);
            circle.setFill(Color.TRANSPARENT);
            circle.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                    .add(transformedPillarBasePoints.get(i).getX_coord()));
            circle.centerYProperty().bind(pane.heightProperty().divide(2)
                    .subtract(transformedPillarBasePoints.get(i).getY_coord()));
            Tooltip tooltip = new Tooltip(
                    "\"" +measuredPillarDataController.
                            measuredPillarData.getPillarBasePoints().get(i).getPointID() + "\" láb\tY=" +
                    String.format("%.3fm",
                            measuredPillarDataController.measuredPillarData.
                                    getPillarBasePoints().get(i).getX_coord()).replace(",", ".") +
                    "\tX=" + String.format("%.3fm",
                    measuredPillarDataController.measuredPillarData.
                            getPillarBasePoints().get(i).getY_coord()).replace(",", ".") +
                    "\tH=" + String.format("%.3fm",
                    measuredPillarDataController.measuredPillarData.
                            getPillarBasePoints().get(i).getZ_coord()).replace(",", "."));
            Tooltip.install(circle, tooltip);
            circle.setCursor(Cursor.HAND);
            pane.getChildren().add(circle);
        }
        addPillarCenterPoint();
    }

    private void addPillarCenterPoint(){
        Circle center = new Circle();
        center.setRadius(5);
        center.setStroke(Color.MAGENTA);
        center.setStrokeWidth(2);
        center.setFill(Color.TRANSPARENT);
        center.centerXProperty().bind(pane.widthProperty().divide(10).multiply(5));
        center.centerYProperty().bind(pane.heightProperty().divide(2));
        Tooltip tooltip = new Tooltip(measuredPillarDataController.measuredPillarData
                .getPillarBaseCenterPoint().getPointID()
            +    "\tY=" + String.format("%.3fm",
                        measuredPillarDataController.measuredPillarData.
                                getPillarBaseCenterPoint().getX_coord()).replace(",", ".") +
                        "\tX=" + String.format("%.3fm",
                        measuredPillarDataController.measuredPillarData.
                                getPillarBaseCenterPoint().getY_coord()).replace(",", ".") +
                        "\th=" + String.format("%.3fm",
                        measuredPillarDataController.measuredPillarData.
                                getPillarBaseCenterPoint().getZ_coord()).replace(",", "."));
        Tooltip.install(center, tooltip);
        center.setCursor(Cursor.HAND);
        pane.getChildren().add(center);
    }

    private void addPillarAxis(){
    	if( 50 > SCALE ) {
    		return;
    	}
        AzimuthAndDistance half1Data = new AzimuthAndDistance(
                new Point("1", transformedPillarBasePoints.get(0).getX_coord(),
                        transformedPillarBasePoints.get(0).getY_coord()),
                new Point("2", transformedPillarBasePoints.get(1).getX_coord(),
                        transformedPillarBasePoints.get(1).getY_coord()));
        Point halfDistancePoint1 = new PolarPoint( new Point("1", transformedPillarBasePoints.get(0).getX_coord(),
                transformedPillarBasePoints.get(0).getY_coord()), half1Data.calcDistance() / 2.0,
                half1Data.calcAzimuth(), "1-2").calcPolarPoint();
        AzimuthAndDistance half2Data = new AzimuthAndDistance(
                new Point("2", transformedPillarBasePoints.get(1).getX_coord(),
                        transformedPillarBasePoints.get(1).getY_coord()),
                new Point("3", transformedPillarBasePoints.get(2).getX_coord(),
                        transformedPillarBasePoints.get(2).getY_coord()));
        Point halfDistancePoint2 = new PolarPoint( new Point("2", transformedPillarBasePoints.get(1).getX_coord(),
                transformedPillarBasePoints.get(1).getY_coord()), half2Data.calcDistance() / 2.0,
                half2Data.calcAzimuth(), "2-3").calcPolarPoint();
        AzimuthAndDistance half3Data = new AzimuthAndDistance(
                new Point("3", transformedPillarBasePoints.get(2).getX_coord(),
                        transformedPillarBasePoints.get(2).getY_coord()),
                new Point("4", transformedPillarBasePoints.get(3).getX_coord(),
                        transformedPillarBasePoints.get(3).getY_coord()));
        Point halfDistancePoint3 = new PolarPoint( new Point("3", transformedPillarBasePoints.get(2).getX_coord(),
                transformedPillarBasePoints.get(2).getY_coord()), half3Data.calcDistance() / 2.0,
                half3Data.calcAzimuth(), "3-4").calcPolarPoint();
        AzimuthAndDistance half4Data = new AzimuthAndDistance(
                new Point("4", transformedPillarBasePoints.get(3).getX_coord(),
                        transformedPillarBasePoints.get(3).getY_coord()),
                new Point("1", transformedPillarBasePoints.get(0).getX_coord(),
                        transformedPillarBasePoints.get(0).getY_coord()));
        Point halfDistancePoint4 = new PolarPoint( new Point("4", transformedPillarBasePoints.get(3).getX_coord(),
                transformedPillarBasePoints.get(3).getY_coord()), half4Data.calcDistance() / 2.0,
                half4Data.calcAzimuth(), "4-1").calcPolarPoint();
       AzimuthAndDistance axis1Data =
               new AzimuthAndDistance(new Point("center", 0.0, 0.0), halfDistancePoint1);
       PolarPoint startPoint1 =
               new PolarPoint(new Point("center" , 0.0,0.0), 3 * MILLIMETER,
                       axis1Data.calcAzimuth(), "startPoint1");
       PolarPoint endPoint1 =
               new PolarPoint(startPoint1.calcPolarPoint(),
                       5000 * MILLIMETER / SCALE, axis1Data.calcAzimuth(), "endPoint1");
        Line axis1 = new Line();
        axis1.setStroke(Color.RED);
        axis1.setStrokeWidth(2);
        axis1.getStrokeDashArray().addAll(10d);
        axis1.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(startPoint1.calcPolarPoint().getX_coord()));
        axis1.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint1.calcPolarPoint().getY_coord()));
        axis1.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(endPoint1.calcPolarPoint().getX_coord()));
        axis1.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(endPoint1.calcPolarPoint().getY_coord()));

        AzimuthAndDistance axis2Data =
                new AzimuthAndDistance(new Point("center", 0.0, 0.0), halfDistancePoint2);
        PolarPoint startPoint2 =
                new PolarPoint(new Point("center" , 0.0,0.0), 3 * MILLIMETER,
                        axis2Data.calcAzimuth(), "startPoint2");
       PolarPoint endPoint2 = new PolarPoint(startPoint2.calcPolarPoint(),
                        5000 * MILLIMETER / SCALE, axis2Data.calcAzimuth(), "endPoint1");
        Line axis2 = new Line();
        axis2.setStroke(Color.RED);
        axis2.setStrokeWidth(2);
        axis2.getStrokeDashArray().addAll(10d);
        axis2.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(startPoint2.calcPolarPoint().getX_coord()));
        axis2.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint2.calcPolarPoint().getY_coord()));
        axis2.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(endPoint2.calcPolarPoint().getX_coord()));
        axis2.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(endPoint2.calcPolarPoint().getY_coord()));

        AzimuthAndDistance axis3Data =
                new AzimuthAndDistance(new Point("center", 0.0, 0.0), halfDistancePoint3);
        PolarPoint startPoint3 =
                new PolarPoint(new Point("center" , 0.0,0.0), 3 * MILLIMETER,
                        axis3Data.calcAzimuth(), "startPoint3");
        PolarPoint endPoint3 = new PolarPoint(startPoint3.calcPolarPoint(),
                5000 * MILLIMETER / SCALE, axis3Data.calcAzimuth(), "endPoint3");
        Line axis3 = new Line();
        axis3.setStroke(Color.RED);
        axis3.setStrokeWidth(2);
        axis3.getStrokeDashArray().addAll(10d);
        axis3.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(startPoint3.calcPolarPoint().getX_coord()));
        axis3.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint3.calcPolarPoint().getY_coord()));
        axis3.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(endPoint3.calcPolarPoint().getX_coord()));
        axis3.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(endPoint3.calcPolarPoint().getY_coord()));

        AzimuthAndDistance axis4Data =
                new AzimuthAndDistance(new Point("center", 0.0, 0.0), halfDistancePoint4);
        PolarPoint startPoint4 =
                new PolarPoint(new Point("center" , 0.0,0.0), 3 * MILLIMETER,
                        axis4Data.calcAzimuth(), "startPoint4");
        PolarPoint endPoint4 = new PolarPoint(startPoint4.calcPolarPoint(),
                5000 * MILLIMETER / SCALE, axis4Data.calcAzimuth(), "endPoint4");
        Line axis4 = new Line();
        axis4.setStroke(Color.RED);
        axis4.setStrokeWidth(2);
        axis4.getStrokeDashArray().addAll(10d);
        axis4.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(startPoint4.calcPolarPoint().getX_coord()));
        axis4.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint4.calcPolarPoint().getY_coord()));
        axis4.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(endPoint4.calcPolarPoint().getX_coord()));
        axis4.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(endPoint4.calcPolarPoint().getY_coord()));

        pane.getChildren().addAll(axis1, axis2, axis3, axis4);

    }

    private void addNextPillarDirection(){
    	if( 50 > SCALE ) {
    		return;
    	}
        Point pillarCenterPoint =  new Point("pillarCenterPoint", 0.0, 0.0);
        Point directionPillarPoint = new Point("transformedDirectionPoint",
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord()),
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord()));
            AzimuthAndDistance mainLineDirection =
                    new AzimuthAndDistance(pillarCenterPoint, directionPillarPoint);
            PolarPoint startPoint = new PolarPoint(pillarCenterPoint,
                3 * MILLIMETER, mainLineDirection.calcAzimuth(),
                "forwardDirection");

            PolarPoint endPoint = new PolarPoint(startPoint.calcPolarPoint(),
                7000 * MILLIMETER / SCALE, mainLineDirection.calcAzimuth(),
                "forwardDirection");

            Line forwardDirection = new Line();
            forwardDirection.setStrokeWidth(2);
            forwardDirection.setStroke(Color.MAGENTA);
            forwardDirection.startXProperty()
                    .bind(pane.widthProperty()
                            .divide(10)
                            .multiply(5).add(startPoint.calcPolarPoint().getX_coord()));
            forwardDirection.startYProperty()
                    .bind(pane.heightProperty()
                            .divide(2).subtract(startPoint.calcPolarPoint().getY_coord()));
            forwardDirection.endXProperty()
                    .bind(pane.widthProperty()
                            .divide(10)
                            .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
            forwardDirection.endYProperty()
                    .bind(pane.heightProperty()
                            .divide(2)
                            .subtract(endPoint.calcPolarPoint().getY_coord()));
            pane.getChildren().add(forwardDirection);
            addArrow(endPoint.calcPolarPoint(), startPoint.calcPolarPoint());
            setText(measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getPointID(),
                    endPoint.calcPolarPoint(), Color.RED, 16);
    }

    private void addPreviousPillarDirection(){
    	if( 50 > SCALE ) {
    		return;
    	}
    	
    	Point controlDirectionPoint = getTransformControlDirectionPoint();
        Point pillarCenterPoint =  new Point("pillarCenterPoint", 0.0, 0.0);
        Point directionPoint =  controlDirectionPoint == null ? new Point("transformedDirectionPoint",
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord()),
                (measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord() -
                        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord())) :
                        	controlDirectionPoint;
       AzimuthAndDistance baseLineData = new AzimuthAndDistance(pillarCenterPoint, directionPoint);
       double rotation = measuredPillarDataController.measuredPillarData.isRightRotationAngle() ?
     		  measuredPillarDataController.measuredPillarData.radRotation : 
    			   - measuredPillarDataController.measuredPillarData.radRotation;
      
       PolarPoint startPoint = new PolarPoint(pillarCenterPoint, 3 * MILLIMETER,
       controlDirectionPoint == null ? baseLineData.calcAzimuth() + rotation : baseLineData.calcAzimuth(), "prevPoint");
       PolarPoint endPoint = new PolarPoint(startPoint.calcPolarPoint(),
                7000 * MILLIMETER / SCALE,
        controlDirectionPoint == null ? baseLineData.calcAzimuth() + rotation : baseLineData.calcAzimuth(), "backwardDirection");

        Line backwardDirection = new Line();
        backwardDirection.setStrokeWidth(2);
        backwardDirection.setStroke(Color.MAGENTA);
        backwardDirection.startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(startPoint.calcPolarPoint().getX_coord()));
        backwardDirection.startYProperty()
                .bind(pane.heightProperty()
                        .divide(2).subtract(startPoint.calcPolarPoint().getY_coord()));
        backwardDirection.endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(5).add(endPoint.calcPolarPoint().getX_coord()));
        backwardDirection.endYProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(endPoint.calcPolarPoint().getY_coord()));
        pane.getChildren().add(backwardDirection);
        addArrow(endPoint.calcPolarPoint(), startPoint.calcPolarPoint());
        int centerID = Integer
                .parseInt(measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getPointID());
        int directionID = Integer
                .parseInt(measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getPointID());
        setText(centerID < directionID ? String.valueOf((centerID - 1)) :  String.valueOf((centerID + 1)),
                endPoint.calcPolarPoint(), Color.MAGENTA, 16);
    }

    private void addArrow(Point startPoint, Point endPoint){
        AzimuthAndDistance baseLineData = new AzimuthAndDistance(startPoint, endPoint);
        PolarPoint slavePoint1 = new PolarPoint(startPoint, 5 * MILLIMETER,
                baseLineData.calcAzimuth() + Math.PI / 6, "arrow1");
        PolarPoint slavePoint2 = new PolarPoint(startPoint, 5 * MILLIMETER,
                baseLineData.calcAzimuth() - Math.PI / 6, "arrow2");
        Line arrow1 = new Line();
        arrow1.setStroke(Color.MAGENTA);
        arrow1.setStrokeWidth(2);
        arrow1.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(startPoint.getX_coord()));
        arrow1.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint.getY_coord()));
        arrow1.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(slavePoint1.calcPolarPoint().getX_coord()));
        arrow1.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(slavePoint1.calcPolarPoint().getY_coord()));
        Line arrow2 = new Line();
        arrow2.setStroke(Color.MAGENTA);
        arrow2.setStrokeWidth(2);
        arrow2.startXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(startPoint.getX_coord()));
        arrow2.startYProperty().bind(pane.heightProperty().divide(2)
                .subtract(startPoint.getY_coord()));
        arrow2.endXProperty().bind(pane.widthProperty().divide(10).multiply(5)
                .add(slavePoint2.calcPolarPoint().getX_coord()));
        arrow2.endYProperty().bind(pane.heightProperty().divide(2)
                .subtract(slavePoint2.calcPolarPoint().getY_coord()));
        pane.getChildren().addAll(arrow1, arrow2);
    }
    
    private void addDirectionInformation() {
    	Text pillarBaseTwisting;
    	Double pillarBaseTwistingByControlPoint = measuredPillarDataController.getPillarBaseTwistingByControlPoint();
    	if( pillarBaseTwistingByControlPoint == null ) {
    		pillarBaseTwisting = new Text("Alap elcsavarodás: e= " + measuredPillarDataController.measuredPillarData
    				.getAngleMinSecFormat(measuredPillarDataController.measuredPillarData.getPillarBaseTwisting()));
    	}
    	else {
    		pillarBaseTwisting = new Text("Alap elcsavarodás: e= " + measuredPillarDataController.measuredPillarData
    				.getAngleMinSecFormat(pillarBaseTwistingByControlPoint));
    	}
    	pillarBaseTwisting.xProperty().bind(pane.widthProperty().divide(10).multiply(1));
        pillarBaseTwisting.yProperty().bind(pane.heightProperty().divide(10).multiply(5));
        pillarBaseTwisting.setFont(boldFont);
    	Text directionInfo = new Text(measuredPillarDataController.getPillarBaseDirectionDifference() );
        directionInfo.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        directionInfo.xProperty().bind(pane.widthProperty().divide(10).multiply(1));
        directionInfo.yProperty().bind(pane.heightProperty().divide(10).multiply(3));
        Text errorMarginText = new Text(directionInfo.getText().isEmpty() ? "" : "|2°|");
        errorMarginText.xProperty().bind(pane.widthProperty().divide(10).multiply(3));
        errorMarginText.yProperty().bind(pane.heightProperty().divide(10).multiply(5));
        errorMarginText.setFont(boldFont);
        if( pillarBaseTwistingByControlPoint == null ) {
        	errorMarginText.setFill(Math.abs(
        			measuredPillarDataController.measuredPillarData.getPillarBaseTwisting()) > 2 ? Color.RED : Color.GREEN);
        }
        else {
        	errorMarginText.setFill(Math.abs(pillarBaseTwistingByControlPoint) > 2 ? Color.RED : Color.GREEN);
        }
        pane.getChildren().addAll(directionInfo, errorMarginText, pillarBaseTwisting);
    }
    
    private void addSmallScaleDistanceInformation(){
    	if( 50 > SCALE ) {
    		return;
    	}
        Point centerPoint = new Point(measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getPointID(),
        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord(),
        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord());
        Point directionPoint = new Point(measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getPointID(),
                measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord(),
                measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord());
        AzimuthAndDistance baseLineData =
                new AzimuthAndDistance(centerPoint, directionPoint);
        Text distanceInfo =
                new Text( measuredPillarDataController.getDistanceBetweenCenterAndControlPoint() +
                		centerPoint.getPointID() + ". és " + directionPoint.getPointID() + ". oszlopok távolsága: " +
                        String.format("%8.3f" , baseLineData.calcDistance()).replace(",", ".") + "m\n" +
                        measuredPillarDataController.getInfoByControlPoint());
        distanceInfo.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        distanceInfo.xProperty().bind(pane.widthProperty().divide(10).multiply(1));
        distanceInfo.yProperty().bind(pane.heightProperty().divide(10).multiply(9));
        Text unit = new Text("1m");
        unit.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        unit.xProperty().bind(pane.widthProperty().divide(10).multiply(1).subtract(100 * MILLIMETER / SCALE));
        unit.yProperty().bind(pane.heightProperty()
                .divide(10).multiply(9).subtract(10 * MILLIMETER ));
        Line distanceUnit = new Line();
        distanceUnit.setStrokeWidth(2);
        distanceUnit
                .startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1));
        distanceUnit
                .startYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract(8 * MILLIMETER ));
        distanceUnit
                .endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1)
                        .add(1000 * MILLIMETER / SCALE));
        distanceUnit
                .endYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract( 8 * MILLIMETER ));
        Line leftEndLine = new Line();
        leftEndLine.setStrokeWidth(2);
        leftEndLine
                .startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1));
        leftEndLine
                .startYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract(8 * MILLIMETER ).add(0.5 * MILLIMETER));
        leftEndLine
                .endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1));
        leftEndLine
                .endYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract( 8 * MILLIMETER ).subtract(0.5 * MILLIMETER));
        Line rightEndLine = new Line();
        rightEndLine.setStrokeWidth(2);
        rightEndLine
                .startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1).add(1000 * MILLIMETER / SCALE));
        rightEndLine
                .startYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract(8 * MILLIMETER ).add(0.5 * MILLIMETER));
        rightEndLine
                .endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1).add(1000 * MILLIMETER / SCALE));
        rightEndLine
                .endYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract( 8 * MILLIMETER ).subtract(0.5 * MILLIMETER));
        pane.getChildren().addAll(distanceInfo, unit, distanceUnit, leftEndLine, rightEndLine);
    }

    private void addLargeScaleDistanceInformation(){
        Point centerPoint = new Point(measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getPointID(),
        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getX_coord(),
        measuredPillarDataController.measuredPillarData.getPillarBaseCenterPoint().getY_coord());
        Point directionPoint = centerPoint;
        try {
        directionPoint = new Point("teoCenter",
                Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_X.getText().replace(",", ".")), 
                Double.parseDouble(measuredPillarDataController.inputPillarDataWindow.centerPillarField_Y.getText().replace(",", ".")));
        }
        catch (NumberFormatException e) {
		}
        AzimuthAndDistance centerPointData =
                new AzimuthAndDistance(centerPoint, directionPoint);
        Text distanceInfo =
                new Text(centerPoint.getPointID() + ". oszlop alapjának tervezett és mért középpontjának távolsága: " +
                        String.format("%8.3f" , centerPointData.calcDistance()).replace(",", ".") + "m");
        distanceInfo.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        distanceInfo.xProperty().bind(pane.widthProperty().divide(10).multiply(1));
        distanceInfo.yProperty().bind(pane.heightProperty().divide(10).multiply(9));
        Text unit = new Text("1cm");
        unit.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 16));
        unit.xProperty().bind(pane.widthProperty().divide(10));
        unit.yProperty().bind(pane.heightProperty()
                .divide(10).multiply(9).subtract(10 * MILLIMETER ));
        Line distanceUnit = new Line();
        distanceUnit.setStrokeWidth(2);
        distanceUnit
                .startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1));
        distanceUnit
                .startYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract(8 * MILLIMETER ));
        distanceUnit
                .endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1)
                        .add(10 * MILLIMETER / SCALE));
        distanceUnit
                .endYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract( 8 * MILLIMETER ));
        Line leftEndLine = new Line();
        leftEndLine.setStrokeWidth(2);
        leftEndLine
                .startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1));
        leftEndLine
                .startYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract(8 * MILLIMETER ).add(0.5 * MILLIMETER));
        leftEndLine
                .endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1));
        leftEndLine
                .endYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract( 8 * MILLIMETER ).subtract(0.5 * MILLIMETER));
        Line rightEndLine = new Line();
        rightEndLine.setStrokeWidth(2);
        rightEndLine
                .startXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1).add(10 * MILLIMETER / SCALE));
        rightEndLine
                .startYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract(8 * MILLIMETER ).add(0.5 * MILLIMETER));
        rightEndLine
                .endXProperty()
                .bind(pane.widthProperty()
                        .divide(10)
                        .multiply(1).add(10 * MILLIMETER / SCALE));
        rightEndLine
                .endYProperty()
                .bind(pane.heightProperty()
                        .divide(10)
                        .multiply(9)
                        .subtract( 8 * MILLIMETER ).subtract(0.5 * MILLIMETER));
        pane.getChildren().addAll(distanceInfo, unit, distanceUnit, leftEndLine, rightEndLine);
    }
    
    private void setText(String textData, Point startPoint, Color color, int size){
        Text text = new Text(textData);
        text.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, size));
        text.setFill(color);
        text.xProperty()
                .bind(pane.widthProperty()
                        .divide(10).multiply(5)
                        .add(startPoint.getX_coord())
                        .add(2 * MILLIMETER));
        text.yProperty()
                .bind(pane.heightProperty()
                        .divide(2)
                        .subtract(startPoint.getY_coord())
                        .subtract(2 * MILLIMETER));
        pane.getChildren().add(text);
    }

}
