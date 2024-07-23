package mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.PLRFileProcess;


public class InputPillarDataWindow {

    public Stage stage;
    private final MeasuredPillarDataController measuredPillarDataController;
    private final AnchorPane pane;
    private final VBox vBox;
    private final Color color = Color.rgb(112,128,144);
    private final Font normalFont = Font.font("Arial", FontWeight.NORMAL, 14);
    private final Font boldFont = Font.font("Arial", FontWeight.BOLD, 13);
    public Text rotationText;
    public Text projectDataText;
    public TextField projectNameField;
    public TextField projectPathField;
    public TextField centerPillarIDField;
    public TextField centerPillarField_X;
    public TextField centerPillarField_Y;
    public TextField rotationAngleField;
    public TextField rotationMinField;;
    public TextField rotationSecField;
    public TextField directionPillarIDField;
    public TextField directionPillarField_X;
    public TextField directionPillarField_Y;
    public  Button processButton;

    public InputPillarDataWindow(MeasuredPillarDataController measuredPillarDataController){
        this.measuredPillarDataController = measuredPillarDataController;
        stage = new Stage();
        stage.setOnCloseRequest(windowEvent ->
        {measuredPillarDataController.init();
        measuredPillarDataController.fxHomeWindow.homeStage.show();
        measuredPillarDataController.setCreatedInputPillarDataWindow(true);});
        pane = new AnchorPane();
        pane.setOnMouseClicked(mouseEvent -> {
            if( mouseEvent.getButton() == MouseButton.SECONDARY ){
                measuredPillarDataController.fxHomeWindow.homeStage.hide();
                if( measuredPillarDataController
                        .getConfirmationAlert("Bemeneti adatok cseréje",
                                "Kívánod cserélni a bemért-, és az előző/következő oszlop adatait?") ) {
                        exchangeMeasuredPillarAndDirectionPillarInputData();
                        measuredPillarDataController.openMeasuredData();
                        measuredPillarDataController.setCreatedInputPillarDataWindow(false);
                }
                measuredPillarDataController.inputPillarDataWindow.processButton.setText("Tallóz");
                MeasuredPillarDataController.IS_OPEN_PCC_DATA = false;
            }
        });
        vBox = new VBox();
        pane.setStyle("-fx-background-color: white");
        addProjectDataFields();
        addCenterPillarDataFields();
        addDirectionPillarDataFields();
        initDataFieldsByProjectFile();
        addCalcButton();
        pane.getChildren().add(vBox);
        Scene scene = new Scene(pane);
        stage.setWidth(400);
        stage.setHeight(580);
        stage.setTitle("Tervezés szerinti adatok megadása");
        stage.getIcons().add(new Image("/img/MVM.jpg"));
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    private void exchangeMeasuredPillarAndDirectionPillarInputData(){
        String centerPillarID = centerPillarIDField.getText();
        String centerPillarX = centerPillarField_X.getText();
        String centerPillarY = centerPillarField_Y.getText();
        centerPillarIDField.setText(directionPillarIDField.getText());
        centerPillarField_X.setText(directionPillarField_X.getText());
        centerPillarField_Y.setText(directionPillarField_Y.getText());
        directionPillarIDField.setText(centerPillarID);
        directionPillarField_X.setText(centerPillarX);
        directionPillarField_Y.setText(centerPillarY);
    }
    private void addProjectDataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(10);
        leftTopLine.setEndX(80);
        leftTopLine.setEndY(10);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(290);
        rightTopLine.setStartY(10);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(10);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(10);
        leftLine.setEndX(5);
        leftLine.setEndY(170);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(10);
        rightLine.setEndX(380);
        rightLine.setEndY(170);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(170);
        bottomLine.setEndX(380);
        bottomLine.setEndY(170);
        pane.getChildren().addAll(leftTopLine, rightTopLine, leftLine, rightLine, bottomLine);
        Text projectDataText = new Text("Projekt adatainak megadása");
        projectDataText.setFont(normalFont);
        projectDataText.setFill(color);
        HBox projectDataTextHbox = new HBox();
        projectDataTextHbox.setAlignment(Pos.CENTER);
        projectDataTextHbox.getChildren().add(projectDataText);
        projectNameField = new TextField();
        if( PLRFileProcess.isExistedProjectFile("plr") ){
            projectNameField.setText(PLRFileProcess.PROJECT_FILE_NAME);
        }
        projectNameField.setCursor(Cursor.HAND);
        projectNameField.setFont(normalFont);
        projectNameField.setStyle("-fx-text-inner-color: #708090; -fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        projectNameField.setPrefColumnCount(15);
        Text projectNameText = new Text("A projekt neve:");
        projectNameText.setFont(boldFont);
        HBox projectNameHbox = new HBox();
        projectNameHbox.setAlignment(Pos.BASELINE_LEFT);
        projectNameHbox.setPadding(new Insets(10,10,10,10));
        projectNameHbox.setSpacing(50);
        projectNameHbox.getChildren().addAll(projectNameText, projectNameField);
        HBox filePathTextHbox = new HBox();
        filePathTextHbox.setPadding(new Insets(10,10,10,10));
        filePathTextHbox.setAlignment(Pos.CENTER);
        Text filePathText = new Text("Mentési mappa választása");
        filePathText.setFont(boldFont);
        filePathTextHbox.getChildren().add(filePathText);
        projectPathField = new TextField();
        if( PLRFileProcess.isExistedProjectFile("plr") ){
            projectPathField.setText(PLRFileProcess.FOLDER_PATH);
        }
        projectPathField.setCursor(Cursor.HAND);
        projectPathField.setPrefColumnCount(26);
        projectPathField.setStyle("-fx-text-inner-color: #708090; -fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        projectPathField.setFont(normalFont);
        HBox projectPathHbox = new HBox();
        projectPathHbox.setPadding(new Insets(5,5,5,5));
        projectPathHbox.setAlignment(Pos.CENTER);
        projectPathHbox.getChildren().add(projectPathField);
        Button browseButton = new Button("Tallóz");
        browseButton.setOnMouseClicked( e -> {
          measuredPillarDataController.fxHomeWindow.homeStage.hide();
          measuredPillarDataController.fileProcess.setFolder();
          projectPathField.setTooltip(new Tooltip(PLRFileProcess.FOLDER_PATH));
          projectPathField.setText(PLRFileProcess.FOLDER_PATH);});
        browseButton.setCursor(Cursor.HAND);
        browseButton.setFont(boldFont);
        HBox browseButtonHbox = new HBox();
        browseButtonHbox.setAlignment(Pos.CENTER);
        browseButtonHbox.getChildren().add(browseButton);
        vBox.getChildren().addAll(projectDataTextHbox, projectNameHbox,
                filePathTextHbox, projectPathHbox, browseButtonHbox);
    }

    private void addCenterPillarDataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(180);
        leftTopLine.setEndX(80);
        leftTopLine.setEndY(180);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(290);
        rightTopLine.setStartY(180);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(180);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(180);
        leftLine.setEndX(5);
        leftLine.setEndY(370);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(180);
        rightLine.setEndX(380);
        rightLine.setEndY(370);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(370);
        bottomLine.setEndX(380);
        bottomLine.setEndY(370);
        Text projectDataText = new Text("Oszlop adatainak megadása");
        projectDataText.setFont(normalFont);
        projectDataText.setFill(color);
        HBox centerPillarTextHbox = new HBox();
        centerPillarTextHbox.setAlignment(Pos.CENTER);
        centerPillarTextHbox.setPadding(new Insets(15,15,15,15));
        centerPillarTextHbox.getChildren().add(projectDataText);
        vBox.getChildren().add(centerPillarTextHbox);
        pane.getChildren().addAll(leftTopLine, rightTopLine, leftLine, rightLine, bottomLine);

        HBox pillarIDTextHbox = new HBox();
        pillarIDTextHbox.setPadding(new Insets(5,5,5,5));
        pillarIDTextHbox.setAlignment(Pos.CENTER);
        pillarIDTextHbox.setSpacing(35);
        Text pillarIDText = new Text("Az oszlop száma:");
        pillarIDText.setFont(boldFont);
        centerPillarIDField = new TextField();
        centerPillarIDField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        centerPillarIDField.setPrefColumnCount(15);
        centerPillarIDField.setFont(normalFont);
        centerPillarIDField.setCursor(Cursor.HAND);
        pillarIDTextHbox.getChildren().addAll(pillarIDText, centerPillarIDField );
        HBox yCoordHbox = new HBox();
        yCoordHbox.setPadding(new Insets(5,5,5,5));
        yCoordHbox.setSpacing(40);
        yCoordHbox.setAlignment(Pos.CENTER);
        Text yCoordText = new Text("X koordináta [m]:");
        yCoordText.setFont(boldFont);
        centerPillarField_Y = new TextField();
        centerPillarField_Y.setCursor(Cursor.HAND);
        centerPillarField_Y.setFont(normalFont);
        centerPillarField_Y.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        centerPillarField_Y.setPrefColumnCount(15);
        centerPillarField_Y.setFont(normalFont);
        yCoordHbox.getChildren().addAll(yCoordText, centerPillarField_Y);
        HBox xCoordHbox = new HBox();
        xCoordHbox.setPadding(new Insets(5,5,5,5));
        xCoordHbox.setSpacing(40);
        xCoordHbox.setAlignment(Pos.CENTER);
        Text xCoordText = new Text("Y koordináta [m]:");
        xCoordText.setFont(boldFont);
        centerPillarField_X = new TextField();
        centerPillarField_X.setCursor(Cursor.HAND);
        centerPillarField_X.setFont(normalFont);
        centerPillarField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        centerPillarField_X.setPrefColumnCount(15);
        centerPillarField_X.setFont(normalFont);
        xCoordHbox.getChildren().addAll(xCoordText, centerPillarField_X);
        rotationText = new Text("A nyomvonal által bezárt szög");
        rotationText.setFont(boldFont);
        HBox rotationTextHbox = new HBox();
        rotationTextHbox.setAlignment(Pos.CENTER);
        rotationTextHbox.getChildren().add(rotationText);
        Text angleText = new Text("fok");
        angleText.setFont(boldFont);
        Text minText = new Text("perc");
        minText.setFont(boldFont);
        Text secText = new Text("mperc");
        secText.setFont(boldFont);
        rotationAngleField = new TextField();
        rotationAngleField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        rotationAngleField.setText("180");
        rotationAngleField.setFont(normalFont);
        rotationAngleField.setCursor(Cursor.HAND);
        rotationAngleField.setPrefColumnCount(3);
        rotationMinField = new TextField();
        rotationMinField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        rotationMinField.setText("0");
        rotationMinField.setFont(normalFont);
        rotationMinField.setCursor(Cursor.HAND);
        rotationMinField.setPrefColumnCount(3);
        rotationSecField = new TextField();
        rotationSecField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        rotationSecField.setText("0");
        rotationSecField.setFont(normalFont);
        rotationSecField.setCursor(Cursor.HAND);
        rotationSecField.setPrefColumnCount(3);
        HBox rotationHbox = new HBox();
        rotationHbox.setAlignment(Pos.CENTER);
        rotationHbox.setSpacing(5);
        rotationHbox.setPadding(new Insets(10,10,10,10));
        rotationHbox.getChildren().addAll(rotationAngleField, angleText,
       rotationMinField, minText, rotationSecField, secText);
        vBox.getChildren().addAll(pillarIDTextHbox, xCoordHbox,
                yCoordHbox, rotationTextHbox, rotationHbox);
    }

    private void initDataFieldsByProjectFile(){
        if( PLRFileProcess.isExistedProjectFile("plr") ){
        	 projectDataText.setText((measuredPillarDataController.
             		measuredPillarData.isAscPillarOrder(
             				measuredPillarDataController.pillarBaseProjectFileData.get(0),
             				measuredPillarDataController.pillarBaseProjectFileData.get(3) ) ? 
             						"A következő " : "Az előző " ) + "oszlop adatainak megadása");
            centerPillarIDField.setText(measuredPillarDataController.pillarBaseProjectFileData.get(0));
            centerPillarField_X.setText(measuredPillarDataController.pillarBaseProjectFileData.get(1));
            centerPillarField_Y.setText(measuredPillarDataController.pillarBaseProjectFileData.get(2));
            directionPillarIDField.setText(measuredPillarDataController.pillarBaseProjectFileData.get(3));
            directionPillarField_X.setText(measuredPillarDataController.pillarBaseProjectFileData.get(4));
            directionPillarField_Y.setText(measuredPillarDataController.pillarBaseProjectFileData.get(5));
            rotationAngleField.setText(measuredPillarDataController.pillarBaseProjectFileData.get(6));
            rotationMinField.setText(measuredPillarDataController.pillarBaseProjectFileData.get(7));
            rotationSecField.setText(measuredPillarDataController.pillarBaseProjectFileData.get(8));
            int rotationDirectionData = 1;
            try {
            rotationDirectionData = Integer.parseInt( measuredPillarDataController
            		.pillarBaseProjectFileData.get(measuredPillarDataController.pillarBaseProjectFileData.size() - 1));
            }
            catch(NumberFormatException n) {
            	
            }
            measuredPillarDataController.measuredPillarData.setRightRotationAngle( rotationDirectionData == 0 ? true : false );
            rotationText.setText("A nyomvonal által bezárt" + 
                    (rotationDirectionData == 0 ? " jobb " : " bal ") + "oldali szög");
        }
    }

    private void addDirectionPillarDataFields(){
        Line leftTopLine = new Line();
        leftTopLine.setStroke(color);
        leftTopLine.setStartX(5);
        leftTopLine.setStartY(380);
        leftTopLine.setEndX(40);
        leftTopLine.setEndY(380);
        Line rightTopLine = new Line();
        rightTopLine.setStroke(color);
        rightTopLine.setStartX(340);
        rightTopLine.setStartY(380);
        rightTopLine.setEndX(380);
        rightTopLine.setEndY(380);
        Line leftLine = new Line();
        leftLine.setStroke(color);
        leftLine.setStartX(5);
        leftLine.setStartY(380);
        leftLine.setEndX(5);
        leftLine.setEndY(500);
        Line rightLine = new Line();
        rightLine.setStroke(color);
        rightLine.setStartX(380);
        rightLine.setStartY(380);
        rightLine.setEndX(380);
        rightLine.setEndY(500);
        Line bottomLine = new Line();
        bottomLine.setStroke(color);
        bottomLine.setStartX(5);
        bottomLine.setStartY(500);
        bottomLine.setEndX(380);
        bottomLine.setEndY(500);
        projectDataText = new Text("Következő/előző oszlop adatainak megadása");
        projectDataText.setFont(normalFont);
        projectDataText.setFill(color);
        HBox directionPillarTextHbox = new HBox();
        directionPillarTextHbox.setAlignment(Pos.CENTER);
        directionPillarTextHbox.getChildren().add(projectDataText);
        vBox.getChildren().add(directionPillarTextHbox);
        pane.getChildren().addAll(leftTopLine, rightTopLine,
                 leftLine, rightLine, bottomLine);
        HBox pillarIDTextHbox = new HBox();
        pillarIDTextHbox.setPadding(new Insets(5,5,5,5));
        pillarIDTextHbox.setAlignment(Pos.CENTER);
        pillarIDTextHbox.setSpacing(35);
        Text pillarIDText = new Text("Az oszlop száma:");
        pillarIDText.setFont(boldFont);
        directionPillarIDField = new TextField();
        directionPillarIDField.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        directionPillarIDField.setPrefColumnCount(15);
        directionPillarIDField.setFont(normalFont);
        directionPillarIDField.setCursor(Cursor.HAND);
        pillarIDTextHbox.getChildren().addAll(pillarIDText, directionPillarIDField);
        HBox yCoordHbox = new HBox();
        yCoordHbox.setPadding(new Insets(5,5,5,5));
        yCoordHbox.setSpacing(40);
        yCoordHbox.setAlignment(Pos.CENTER);
        Text xCoordText = new Text("Y koordináta [m]:");
        xCoordText.setFont(boldFont);
        Text yCoordText = new Text("X koordináta [m]:");
        yCoordText.setFont(boldFont);
        directionPillarField_X = new TextField();
        directionPillarField_X.setCursor(Cursor.HAND);
        directionPillarField_X.setFont(normalFont);
        directionPillarField_X.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        directionPillarField_X.setPrefColumnCount(15);
        directionPillarField_X.setFont(normalFont);
        directionPillarField_Y = new TextField();
        directionPillarField_Y.setCursor(Cursor.HAND);
        directionPillarField_Y.setFont(normalFont);
        directionPillarField_Y.setStyle("-fx-text-inner-color: #708090; " +
                "-fx-text-box-border: #708090;" +
                "-fx-focus-color: #708090;");
        directionPillarField_Y.setPrefColumnCount(15);
        directionPillarField_Y.setFont(normalFont);
        yCoordHbox.getChildren().addAll(yCoordText, directionPillarField_Y);
        HBox xCoordHbox = new HBox();
        xCoordHbox.setPadding(new Insets(5,5,5,5));
        xCoordHbox.setSpacing(40);
        xCoordHbox.setAlignment(Pos.CENTER);
        xCoordHbox.getChildren().addAll(xCoordText, directionPillarField_X);
        vBox.getChildren().addAll(pillarIDTextHbox,
                xCoordHbox, yCoordHbox);
    }

    private void addCalcButton(){
    processButton = new Button("Tallóz");
    processButton.setOnMouseClicked(e -> measuredPillarDataController.onlClickProcessButtonForPillarBaseProject());
    processButton.setCursor(Cursor.HAND);
    processButton.setFont(boldFont);
    HBox calcButtonHbox = new HBox();
    calcButtonHbox.setPadding(new Insets(20,20,20,20));
    calcButtonHbox.setAlignment(Pos.CENTER);
    calcButtonHbox.getChildren().add(processButton);
    vBox.getChildren().add(calcButtonHbox);
    }

}
