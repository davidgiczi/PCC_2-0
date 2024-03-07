package mvmxpert.david.giczi.pillarcoordscalculator.fileprocess;


import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.MeasuredPillarDataController;
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

public class PLRFileProcess {

	public static String PROJECT_FILE_NAME;
	public MeasuredPillarDataController measuredPillarDataController;
	public static String FOLDER_PATH;
	public static String MEAS_FILE_NAME;
	private List<String> pillarBaseMeasData;
	public List<String> pccData;
	private String delimiter = ";";

	public PLRFileProcess(MeasuredPillarDataController measuredPillarDataController){
		this.measuredPillarDataController = measuredPillarDataController;
	}

	public List<String> getPillarBaseMeasData() {
		return pillarBaseMeasData;
	}
	
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
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

	public void getPillarBaseDataByPCCProject() {
		
		FileChooser projectFileChooser = new FileChooser();
		projectFileChooser.setInitialDirectory(FOLDER_PATH == null ?
				new File(System.getProperty("user.home")) : new File(FOLDER_PATH));
		projectFileChooser.setTitle("Válassz kitűzési projektet");
		FileChooser.ExtensionFilter projectFileFilter = new FileChooser.ExtensionFilter("Kitűzés projekt fájl (*.pcc)", "*.pcc");
		projectFileChooser.getExtensionFilters().add(projectFileFilter);
		File selectedFile = projectFileChooser.showOpenDialog(measuredPillarDataController.fxHomeWindow.homeStage);
		if ( selectedFile != null ) {
			setPillarBaseDataByPCCProject(selectedFile);
		}
		else {
		measuredPillarDataController.inputPillarDataWindow.processButton.setText("Számol");
        MeasuredPillarDataController.IS_OPEN_PCC_DATA = true;
        if( pccData != null ) {
        	 pccData = null;
        }
       
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
				if (row.endsWith(PointType.alap.name()) ||
						row.endsWith(PointType.ALAP.name()) ||
						row.endsWith(PointType.CSUCS.name() + delimiter) ||
						row.endsWith(PointType.csucs.name() + delimiter)) {
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
			writer.write(measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getPointID());
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getX_coord()));
			writer.newLine();
			writer.write(String.valueOf(
					measuredPillarDataController.measuredPillarData.getPillarCenterPoint().getY_coord()));
			writer.newLine();
			writer.write(measuredPillarDataController.measuredPillarData.getBaseLineDirectionPoint().getPointID());
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

	public static boolean isExistedProjectFile(String extension){
		return  new File(FOLDER_PATH + "\\" + PROJECT_FILE_NAME + "." + extension).exists();
	}

}
