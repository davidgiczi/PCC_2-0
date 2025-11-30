package mvmxpert.david.giczi.pillarcoordscalculator.fileprocess;


import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.HomeController;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
import mvmxpert.david.giczi.pillarcoordscalculator.fx.displayers.FXHomeWindow;
import mvmxpert.david.giczi.pillarcoordscalculator.service.AvePoint;
import mvmxpert.david.giczi.pillarcoordscalculator.service.MeasPoint;
import mvmxpert.david.giczi.pillarcoordscalculator.service.RowData;
import mvmxpert.david.giczi.pillarcoordscalculator.service.TheoreticalPointData;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.PointType;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.DataFormatException;


public class PLRFileProcess {

	public static String PROJECT_FILE_NAME;
	public MeasuredPillarDataController measuredPillarDataController;
	public static String FOLDER_PATH;
	public static String MEAS_FILE_NAME;
	private List<String> pillarBaseMeasData;
	public List<String> pccData;

	public PLRFileProcess(MeasuredPillarDataController measuredPillarDataController){
		this.measuredPillarDataController = measuredPillarDataController;
	}

	public List<String> getPillarBaseMeasData() {
		return pillarBaseMeasData;
	}
	
	public List<String> getPccData() {
		return pccData;
	}

	public void setPccData(List<String> pccData) {
		this.pccData = pccData;
	}

	public void setPillarBaseMeasData(List<String> pillarBaseMeasData) {
		this.pillarBaseMeasData = pillarBaseMeasData;
	}

	
	public void setFolder() {
		DirectoryChooser directoryChooser = new DirectoryChooser();
		directoryChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		directoryChooser.setTitle("Válassz mentési mappát");
		File selectedFile =	directoryChooser.showDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			FOLDER_PATH = selectedFile.getAbsolutePath();
		}
		else {
			FOLDER_PATH = null;
		}
	}
	public List<String> openPillarBaseProject() {
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz projekt fájlt");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Projekt fájlok (*.plr)", "*.plr");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			FOLDER_PATH = selectedFile.getParent();
			PROJECT_FILE_NAME = selectedFile.getName().substring(0, selectedFile.getName().indexOf('.'));
			HomeController.PROJECT_NAME = selectedFile.getName();
		}
		
		return getPillarBaseProjectFileData();
	}

	private List<String> getPillarBaseProjectFileData(){

		List<String> projectData = new ArrayList<>();

		if(PROJECT_FILE_NAME == null || FOLDER_PATH == null)
			return projectData;

		File file = new File(FOLDER_PATH + "/" + PROJECT_FILE_NAME+ ".plr");

		try(BufferedReader reader = new BufferedReader(
				new FileReader(file, StandardCharsets.UTF_8))) {

			String row = reader.readLine();
			while( row != null ) {
				projectData.add(row);
				row = reader.readLine();
			}
		}
		catch (IOException e) {

		}

		return projectData;
	}

	public List<String> openIntersectionProject() {
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz projekt fájlt");
		FileChooser.ExtensionFilter projectFileFilter =
				new FileChooser.ExtensionFilter("Projekt fájlok (*.ins)", "*.ins");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			FOLDER_PATH = selectedFile.getParent();
			PROJECT_FILE_NAME = selectedFile.getName().substring(0, selectedFile.getName().indexOf('.'));
		}
		else {
			PROJECT_FILE_NAME = null;
		}
		return getIntersectionFileData();
	}

	private List<String> getIntersectionFileData(){

		List<String> projectData = new ArrayList<>();

		if(PROJECT_FILE_NAME == null || FOLDER_PATH == null)
			return projectData;

		File file = new File(FOLDER_PATH + "/" + PROJECT_FILE_NAME+ ".ins");

		try(BufferedReader reader = new BufferedReader(
				new FileReader(file, StandardCharsets.UTF_8))) {

			String row = reader.readLine();
			while( row != null ) {
				projectData.add(row);
				row = reader.readLine();
			}
		}
		catch (IOException e) {

		}

		return projectData;
	}

	public void openMeasurmentFileData() {
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz mérési fájlt");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Mérési fájlok (*.txt)", "*.txt");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		MEAS_FILE_NAME = null;
		if ( selectedFile != null ) {
			FOLDER_PATH = selectedFile.getParent();
			MEAS_FILE_NAME = selectedFile.getName();
			getMeasurmentFileData();
		}
	}
	
	private void getMeasurmentFileData(){

		measuredPillarDataController.measurmentData = new ArrayList<>();

		if(MEAS_FILE_NAME == null || FOLDER_PATH == null)
			return;

		File file = new File(FOLDER_PATH + "/" + MEAS_FILE_NAME);
		
		try(BufferedReader reader = new BufferedReader(
				new FileReader(file, StandardCharsets.UTF_8))) {

			String row = reader.readLine();
			while( row != null ) {
				measuredPillarDataController.measurmentData.add(row);
				row = reader.readLine();
			}
		}
		catch (IOException e) {

		}
	}
	
	public void getPillarBaseMeasureFileData() {
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz mérési fájlt");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Mérési fájlok (*.txt)", "*.txt");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			setPillarBaseData(selectedFile);
			MEAS_FILE_NAME = selectedFile.getName();
			FOLDER_PATH = selectedFile.getParent();
		}
	}

	public void getPillarBaseDataByPCCProjectOrTopMeasurment() {
		
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz kitűzési projektet vagy mérési fájlt");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Kitűzési (*.pcc), Mérési (*.txt) fájlok", "*.pcc", "*.txt");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			
			if( selectedFile.getName().endsWith(".txt") ) {
				MEAS_FILE_NAME = selectedFile.getName();
				setPillarBaseData(selectedFile);
			}
			else if( selectedFile.getName().endsWith(".pcc" ) ) {
				HomeController.PROJECT_NAME = selectedFile.getName();
				setPillarBaseDataByPCCProject(selectedFile);
			}
			
		}
		else {
		measuredPillarDataController.inputPillarDataWindow.processButton.setText("Számol");
        MeasuredPillarDataController.IS_OPENING_PCC_OR_PLR_FILE_PROCESS = true;
        pillarBaseMeasData = null;
        pccData = null;   
		}
	}
	
		
	private void setPillarBaseDataByPCCProject(File selectedFile) {
		pccData = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(
				new FileReader(selectedFile, StandardCharsets.UTF_8))) {

			String row = reader.readLine();
			while (row != null) {
				
				pccData.add(row);
				
				row = reader.readLine();
			}
		}
		catch (IOException e) {
		}
	}
	
	private void setPillarBaseData(File selectedFile){
		pillarBaseMeasData = new ArrayList<>();
		try(BufferedReader reader = new BufferedReader(
				new FileReader(selectedFile, StandardCharsets.UTF_8))) {

			String row = reader.readLine();
			while (row != null) {
				if ( row.endsWith(PointType.alap.name()) ||
						row.endsWith(PointType.ALAP.name()) ||
						row.endsWith(PointType.alap.name() + ";") ||
						row.endsWith(PointType.ALAP.name() + ";") ||
						row.endsWith(PointType.CSUCS.name()) ||
						row.endsWith(PointType.csucs.name()) ||
						row.endsWith(PointType.CSUCS.name() + ";") ||
						row.endsWith(PointType.csucs.name() + ";") ) {
					pillarBaseMeasData.add(row);
				}
				row = reader.readLine();
			}
		}
		catch (IOException e) {
		}
	}
	public void savePillarProjectData(){
		File file = new File(FOLDER_PATH + "\\" + PROJECT_FILE_NAME + ".plr");
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))){
			writer.write(pccData == null ? measuredPillarDataController.pillarBaseProjectFileData == null ?
				measuredPillarDataController.inputPillarDataWindow.centerPillarIDField.getText().trim() :	
					measuredPillarDataController.pillarBaseProjectFileData.get(0) : pccData.get(1));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getX_coord()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getY_coord()));
			writer.newLine();
			writer.write(pccData == null ? measuredPillarDataController.pillarBaseProjectFileData == null ?
					measuredPillarDataController.inputPillarDataWindow.directionPillarIDField.getText().trim() :
						measuredPillarDataController.pillarBaseProjectFileData.get(3) : pccData.get(4));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getX_coord()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getY_coord()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getAngleRotation()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getMinRotation()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getSecRotation()));
			writer.newLine();
			if(  measuredPillarDataController.measuredPillarData.getPillarBasePoints() != null ){
				for (MeasPoint basePoint : measuredPillarDataController.measuredPillarData.getPillarBasePoints()) {
					writer.write(basePoint.toString());
					writer.newLine();
				}
			}
			if(  measuredPillarDataController.measuredPillarData.getPillarTopPoints() != null ) {
				for (MeasPoint topPoint : measuredPillarDataController.measuredPillarData.getPillarTopPoints()) {
					writer.write(topPoint.toString());
					writer.newLine();
				}
			}
			if( FXHomeWindow.homeController.controlDirectionPoint != null ) {
				writer.write("ControlPoint");
				writer.newLine();
				writer.write(FXHomeWindow.homeController.controlDirectionPoint.getPointID());
				writer.newLine();
				writer.write(String.valueOf(FXHomeWindow.homeController.controlDirectionPoint.getX_coord()));
				writer.newLine();
				writer.write(String.valueOf(FXHomeWindow.homeController.controlDirectionPoint.getY_coord()));
				writer.newLine();
			}
			if( measuredPillarDataController.measuredPillarData.isRightRotationAngle() ) {
				writer.write("0");
			}
			else {
				writer.write("1");
			}
		}catch (IOException e){
		}
	}

	public void saveIntersectionData(){
		File file = new File(FOLDER_PATH + "\\" + PROJECT_FILE_NAME + ".ins");
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))){
			if( !measuredPillarDataController.intersectionInputDataWindow.startPointIdField.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow
						.startPointIdField.getText().toUpperCase());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.startField_X.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow.startField_X.getText());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.startField_Y.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow.startField_Y.getText());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.endPointIdField.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow
						.endPointIdField.getText().toUpperCase());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.endField_X.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow.endField_X.getText());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.endField_Y.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow.endField_Y.getText());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.newPointIdField.getText().isEmpty() ){
				writer.write(measuredPillarDataController.intersectionInputDataWindow
						.newPointIdField.getText().toUpperCase());
				writer.newLine();
			}
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAIdField.getText().toUpperCase());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointField_X.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointField_Y.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointField_Z.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointAzimuthAngleField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointAzimuthMinField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointAzimuthSecField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointElevationAngleField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointElevationMinField.getText());
			writer.newLine();
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingAPointElevationSecField.getText());
			writer.newLine();
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBIdField.getText().isEmpty() ) {
				writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBIdField.getText().toUpperCase());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBPointField_X.getText().isEmpty() ) {
				writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointField_X.getText());
				writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBPointField_Y.getText().isEmpty() ) {
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointField_Y.getText());
			writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBPointField_Z.getText().isEmpty() ) {
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointField_Z.getText());
			writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBPointAzimuthAngleField.getText().isEmpty() ) {
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointAzimuthAngleField.getText());
			writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBPointAzimuthMinField.getText().isEmpty() ) {
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointAzimuthMinField.getText());
			writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBPointAzimuthSecField.getText().isEmpty() ) {
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointAzimuthSecField.getText());
			writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBPointElevationAngleField.getText().isEmpty() ) {
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointElevationAngleField.getText());
			writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBPointElevationMinField.getText().isEmpty() ) {
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointElevationMinField.getText());
			writer.newLine();
			}
			if( !measuredPillarDataController.intersectionInputDataWindow.standingBPointElevationSecField.getText().isEmpty() ) {
			writer.write(measuredPillarDataController.intersectionInputDataWindow.standingBPointElevationSecField.getText());
			writer.newLine();
			}
			
			if( measuredPillarDataController.intersection != null && 
					measuredPillarDataController.intersection.getTheoreticalPoint() != null) {
				writer.write(String.valueOf(measuredPillarDataController.intersection.getTheoreticalPoint().getX_coord()));
				writer.newLine();
				writer.write(String.valueOf(measuredPillarDataController.intersection.getTheoreticalPoint().getY_coord()));
				writer.newLine();
			}
			if( !measuredPillarDataController.crossedWirePointList.isEmpty() ) {
				writer.write("crossedWire");
				writer.newLine();
				writer.write(measuredPillarDataController.crossedWirePointList.get(0).getPointID());
				writer.newLine();
				writer.write(String.valueOf(measuredPillarDataController.crossedWirePointList.get(0).getX_coord()));
				writer.newLine();
				writer.write(String.valueOf(measuredPillarDataController.crossedWirePointList.get(0).getY_coord()));
				writer.newLine();
				writer.write(String.valueOf(measuredPillarDataController.crossedWirePointList.get(0).getZ_coord()));
				writer.newLine();
				writer.write(measuredPillarDataController.crossedWirePointList.get(1).getPointID());
				writer.newLine();
				writer.write(String.valueOf(measuredPillarDataController.crossedWirePointList.get(1).getX_coord()));
				writer.newLine();
				writer.write(String.valueOf(measuredPillarDataController.crossedWirePointList.get(1).getY_coord()));
				writer.newLine();
				writer.write(String.valueOf(measuredPillarDataController.crossedWirePointList.get(1).getZ_coord()));
				writer.newLine();
			}
		}
		catch (IOException e){
		}
	}
	
	public String saveMeasurmentReportRowData(List<RowData> standingPointDataStore) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd_HH_mm_ss");
		String fileName = MEAS_FILE_NAME.substring(0, MEAS_FILE_NAME.indexOf(".")) + "_" 
		+ df.format(new Date(System.currentTimeMillis())) + ".txt";
		File file = new File(FOLDER_PATH + "\\" + fileName);
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))){
			
			for (RowData standingPointData : standingPointDataStore) {
				
				for (RowData measPointData : standingPointData.getMeasuredPointDataStore()) {
					if( !measPointData.isDeleted() ) {
						writer.write(measPointData.toString());
						writer.newLine();	
					}
				}
		}
}
			catch (IOException e){
				
			}
		
		return fileName;
	}
		
	
	public File saveMeasurmentReportTheoreticalPointData(List<TheoreticalPointData> theoreticalPointDataStore, String fileName) {
		
		File file = new File(FOLDER_PATH + "\\" + fileName);
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8, true))){
			for (TheoreticalPointData theoreticalPointData : theoreticalPointDataStore) {
				if( !theoreticalPointData.isDeleted() ) {
				writer.write(theoreticalPointData.toString());
				writer.newLine();
				}
				
				}
		}
			catch (IOException e){
	}
		
		return file;
}
	
	public File savePillarCenterPoints(List<AvePoint> resultPointList) {
		File file = new File(FOLDER_PATH + "\\" + resultPointList.get(0).pointId + "-" + 
				resultPointList.get(resultPointList.size() - 1).pointId + "-OH_KOZEP.txt");
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file, StandardCharsets.UTF_8))){
			for (AvePoint resultPoint : resultPointList) {
				writer.write(resultPoint.getAveragePointData());
				writer.newLine();
				}
		}
			catch (IOException | DataFormatException e){
				return null;
	}
		
		return file;
}
	

	public static boolean isExistedProjectFile(String extension){
		return  new File(FOLDER_PATH + "\\" + PROJECT_FILE_NAME + "." + extension).exists();
	}

}
