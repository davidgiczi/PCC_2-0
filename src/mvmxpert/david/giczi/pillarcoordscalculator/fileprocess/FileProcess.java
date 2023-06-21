package mvmxpert.david.giczi.pillarcoordscalculator.fileprocess;

import java.awt.Component;
import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import mvmxpert.david.giczi.pillarcoordscalculator.controllers.HomeController;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.service.SteakoutedCoords;


public class FileProcess {
	
	public static String FOLDER_PATH;
	public static String STK_FILE_PATH;
	public static String STK_FILE_NAME;
	public static String STK_SAVED_FILE_PATH;
	private HomeController homeController;
	
	public FileProcess(HomeController homeController) {
		this.homeController = homeController;
	}

	public void saveDataForKML(Point pillarCenter,  Point directionPoint) {
		
		if(FOLDER_PATH == null) {
			return;
		}

		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_KML.txt");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, true))) {
			
				writer.write(pillarCenter.writePointForKML());
				writer.newLine();
				writer.write(directionPoint.writePointForKML());
				writer.newLine();
		
		} catch (IOException e) {
			homeController.getInfoMessage("Kitűzési fájl létrehozása", "Kitűzési fájl létrehozása sikertelen: \"" + file.getName() + "\"");
		}
	}
	
	public void saveDataForRTK(List<Point> points, Point directionPoint) {
		
		if(FOLDER_PATH == null) {
			return;
		}
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_RTK.txt");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8, true))) {
			
			for (Point point : points) {
				writer.write(point.writePointForRTK());
				writer.newLine();
			}
			writer.write(directionPoint.writePointForRTK());
			writer.newLine();
					
		} catch (IOException e) {
			homeController.getInfoMessage("Kitűzési fájl létrehozása", "Kitűzési fájl létrehozása sikertelen: \"" + file.getName() + "\"");
		}
	}
	
	public void saveDataForTPS(List<Point> points, Point directionPoint) {
		
		if(FOLDER_PATH == null) {
			return;
		}
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + "_TPS.txt");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8, true))) {
			
			for (Point point : points) {
				writer.write(point.writePointForTPS());
				writer.newLine();
			}
			writer.write(directionPoint.writePointForTPS());
			writer.newLine();
			
		} catch (IOException e) {
			homeController.getInfoMessage("Kitűzési fájl létrehozása", "Kitűzési fájl létrehozása sikertelen: \"" + file.getName() + "\"");
		}
	}
	
	public void saveDataForMS(List<Point> points, Point directionPoint) {
		
		if(FOLDER_PATH == null) {
			return;
		}
		
		File file = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME +  "_MS.txt");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8, true))) {
			
			for (Point point : points) {
				writer.write(point.writePointForMS());
				writer.newLine();
			}
			writer.write(directionPoint.writePointForMS());
			writer.newLine();
			
		} catch (IOException e) {
			homeController.getInfoMessage("Kitűzési fájl létrehozása", "Kitűzési fájl létrehozása sikertelen: \"" + file.getName() + "\"");
		}
	}
	
	public void saveSteakoutedPoints(List<SteakoutedCoords> steakoutedPoints) {
		
		if(STK_SAVED_FILE_PATH == null) {
			STK_SAVED_FILE_PATH = FOLDER_PATH;
		}
		
		File file = new File(STK_SAVED_FILE_PATH + "/" + HomeController.PROJECT_NAME + "_kit.txt");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(file, StandardCharsets.UTF_8))) {
			
			for (SteakoutedCoords steakoutedPoint : steakoutedPoints) {
				writer.write(steakoutedPoint.getSteakoutedPointData());
				writer.newLine();
			}
			
		} catch (IOException e) {
			homeController.getInfoMessage("Kitűzési fájl létrehozása", "Kitűzési fájl létrehozása sikertelen: \"" + file.getName() + "\"");
		}
		
	}
	
	public List<String> getSteakoutedPointData(){
		
		List<String> pointData = new ArrayList<>();
		
		if(STK_FILE_PATH == null) {
			return pointData;
		}
		File file = new File(STK_FILE_PATH + "/" + STK_FILE_NAME);
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))){
			
			String row = reader.readLine();
			while(row != null) { 
			pointData.add(row);
			row = reader.readLine();
			}
			
		} catch (IOException e) {
			homeController.getInfoMessage("Kitűzési fájl választása", "Nem található kitűzési fájl: \"" + file.getName() + "\"");
		}
		
		return pointData;
	}
	
	public void setFolder() {
		JFileChooser jfc = new JFileChooser(){
		    
			private static final long serialVersionUID = 1L;

			@Override
		    protected JDialog createDialog( Component parent ) throws HeadlessException {
		        JDialog dialog = super.createDialog( parent );
		        dialog.setLocationRelativeTo(null);
		        try {
					byte[] imageSource = this.getClass()
							.getResourceAsStream("/img/MVM.jpg").readAllBytes();
					dialog.setIconImage( new ImageIcon(imageSource).getImage() );
				} catch (IOException e) {
					e.printStackTrace();
				} 
		        return dialog;
		    }
		};
		jfc.setCurrentDirectory(FOLDER_PATH == null ? FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
		jfc.setDialogTitle("Válassz mentési mappát a projektnek.");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			FOLDER_PATH = selectedFile.getAbsolutePath();
		}
		else {
			FOLDER_PATH = null;
		}
	}
	
	public void setFolderForSteakoutedPointFile() {
		JFileChooser jfc = new JFileChooser(){
		    
			private static final long serialVersionUID = 1L;

			@Override
		    protected JDialog createDialog( Component parent ) throws HeadlessException {
		        JDialog dialog = super.createDialog( parent );
		        dialog.setLocationRelativeTo(homeController.homeWindow.homeFrame);
		        try {
					byte[] imageSource = this.getClass()
							.getResourceAsStream("/img/MVM.jpg").readAllBytes();
					dialog.setIconImage( new ImageIcon(imageSource).getImage() );
				} catch (IOException e) {
					e.printStackTrace();
				} 
		        return dialog;
		    }
		};
		jfc.setCurrentDirectory(STK_SAVED_FILE_PATH == null ? new File(FOLDER_PATH) : new File(STK_SAVED_FILE_PATH));
		jfc.setDialogTitle("Válassz mentési mappát a kitűzési fájlnak.");
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			STK_SAVED_FILE_PATH = selectedFile.getAbsolutePath();
		}
		else {
			STK_SAVED_FILE_PATH = FOLDER_PATH;
		}
	}
	
	public String setProject() {
		JFileChooser jfc = new JFileChooser(){
		    
			private static final long serialVersionUID = 1L;

			@Override
		    protected JDialog createDialog( Component parent ) throws HeadlessException {
		        JDialog dialog = super.createDialog( parent );
		        dialog.setLocationRelativeTo(null);
		        try {
					byte[] imageSource = this.getClass()
							.getResourceAsStream("/img/MVM.jpg").readAllBytes();
					dialog.setIconImage( new ImageIcon(imageSource).getImage() );
				} catch (IOException e) {
					e.printStackTrace();
				} 
		        return dialog;
		    }
		};
		String projectName = null;
		jfc.setCurrentDirectory(FOLDER_PATH == null ? FileSystemView.getFileSystemView().getHomeDirectory() : new File(FOLDER_PATH));
		jfc.setDialogTitle("Válassz projekt fájlt.");
		jfc.setFileFilter(new FileFilter() {
			@Override
			public String getDescription() {
				return "*.pcc fájlok";
			}
			
			@Override
			public boolean accept(File f) {
				return f.isDirectory() || f.getName().toLowerCase().endsWith(".pcc");
			}
		});
		
		int returnValue = jfc.showOpenDialog(null);
		if (returnValue == JFileChooser.APPROVE_OPTION) {
			File selectedFile = jfc.getSelectedFile();
			FOLDER_PATH = selectedFile.getParent();
			try {
				projectName = selectedFile.getName().substring(0, selectedFile.getName().indexOf('.'));
			} catch (Exception e) {
				homeController.getInfoMessage("Projekt fájl választása", "*.pcc fájl választása szükséges.");
			}
			
		}
		
		return projectName;
	}
	
	public void setSteakoutFile() {
		JFileChooser jfc = new JFileChooser(){
		    
			private static final long serialVersionUID = 1L;

			@Override
		    protected JDialog createDialog( Component parent ) throws HeadlessException {
		        JDialog dialog = super.createDialog( parent );
		        dialog.setLocationRelativeTo(homeController.homeWindow.homeFrame);
		        try {
					byte[] imageSource = this.getClass()
							.getResourceAsStream("/img/MVM.jpg").readAllBytes();
					dialog.setIconImage( new ImageIcon(imageSource).getImage() );
				} catch (IOException e) {
					e.printStackTrace();
				} 
		        return dialog;
		    }
		};	
			jfc.setCurrentDirectory(STK_FILE_PATH == null ? new File(FOLDER_PATH) : new File(STK_FILE_PATH));
			jfc.setDialogTitle("Válassz kitűzési fájlt.");
			jfc.setAcceptAllFileFilterUsed(false);
			FileNameExtensionFilter filter = new FileNameExtensionFilter("txt fájlok", "txt");
			jfc.addChoosableFileFilter(filter);
			int returnValue = jfc.showOpenDialog(null);
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				File selectedFile = jfc.getSelectedFile();
				STK_FILE_PATH = selectedFile.getParent();
				STK_FILE_NAME = selectedFile.getName();
		}
			
	}
	
	public void saveProjectFileForPlatetBase
	(String centerID, double centerX, double centerY, 
	 String directionID, double directionX,  double directionY,
	 double horizontalSizeOfHole, double verticalSizeOfHole,
	 double horizontalDistanceFromHole, double verticalDistanceFromHole,
	 double rotationAngle, double rotationSec, double rotationMin) {
		
		File projectFile = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + ".pcc");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(projectFile, StandardCharsets.UTF_8))) {
			
				writer.write("#PlateBase");
				writer.newLine();
				writer.write(centerID);
				writer.newLine();
				writer.write(String.valueOf(centerX));
				writer.newLine();
				writer.write(String.valueOf(centerY));
				writer.newLine();
				writer.write(directionID);
				writer.newLine();
				writer.write(String.valueOf(directionX));
				writer.newLine();
				writer.write(String.valueOf(directionY));
				writer.newLine();
				writer.write(String.valueOf(horizontalSizeOfHole));
				writer.newLine();
				writer.write(String.valueOf(verticalSizeOfHole));
				writer.newLine();
				writer.write(String.valueOf(horizontalDistanceFromHole));
				writer.newLine();
				writer.write(String.valueOf(verticalDistanceFromHole));
				writer.newLine();
				writer.write(String.valueOf(rotationAngle));
				writer.newLine();
				writer.write(String.valueOf(rotationMin));
				writer.newLine();
				writer.write(String.valueOf(rotationSec));
				writer.newLine();
				
		} catch (IOException e) {
			homeController
			.getInfoMessage("Projekt fájl létrehozása", "Projekt fájl létrehozása sikertelen: \"" + projectFile.getName() + "\"");
		}
			
	}
	
	public void saveProjectFileForWeightBase(String centerID, double centerX, double centerY, 
			 String directionID, double directionX,  double directionY,
			 double distanceOnTheAxis, 
			 double horizontalDistanceBetweenPillarLegs,
			 double verticalDistanceBetweenPillarLegs, 
			 double horizontalSizeOfHoleOfPillarLeg,
			 double verticalSizeOfHoleOfPillarLeg,
			 double rotationAngle, double rotationSec, double rotationMin) {
		
		File projectFile = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + ".pcc");
		
		try(BufferedWriter writer = new BufferedWriter(
				new FileWriter(projectFile, StandardCharsets.UTF_8))) {
			
				writer.write("#WeightBase");
				writer.newLine();
				writer.write(centerID);
				writer.newLine();
				writer.write(String.valueOf(centerX));
				writer.newLine();
				writer.write(String.valueOf(centerY));
				writer.newLine();
				writer.write(directionID);
				writer.newLine();
				writer.write(String.valueOf(directionX));
				writer.newLine();
				writer.write(String.valueOf(directionY));
				writer.newLine();
				writer.write(String.valueOf(distanceOnTheAxis));
				writer.newLine();
				writer.write(String.valueOf(horizontalDistanceBetweenPillarLegs));
				writer.newLine();
				writer.write(String.valueOf(verticalDistanceBetweenPillarLegs));
				writer.newLine();
				writer.write(String.valueOf(horizontalSizeOfHoleOfPillarLeg));
				writer.newLine();
				writer.write(String.valueOf(verticalSizeOfHoleOfPillarLeg));
				writer.newLine();
				writer.write(String.valueOf(rotationAngle));
				writer.newLine();
				writer.write(String.valueOf(rotationMin));
				writer.newLine();
				writer.write(String.valueOf(rotationSec));
				writer.newLine();
				
		} catch (IOException e) {
			homeController
			.getInfoMessage("Projekt fájl létrehozása", "Projekt fájl létrehozása sikertelen: \"" + projectFile.getName() + "\"");
		}
	}
	
	public static boolean isProjectFileExist() {
		
		String[] pcc = new File(FOLDER_PATH).list(new FilenameFilter() {
			
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".pcc");
			}
		});

		return Arrays.asList(pcc).contains(HomeController.PROJECT_NAME + ".pcc");
	}
	
	public List<String> getProjectFileData(){
		
		List<String> projectData = new ArrayList<>();
		
		if( FOLDER_PATH == null ) {
			return projectData;
		}
		else if( isProjectFileExist() ) {
			
		File projectFile = new File(FOLDER_PATH + "/" + HomeController.PROJECT_NAME + ".pcc");
				
		try(BufferedReader reader = new BufferedReader(new FileReader(projectFile, StandardCharsets.UTF_8))){
			String row = reader.readLine();
			while(row != null) { 
			projectData.add(row);
			row = reader.readLine();
			}
			
		} catch (IOException e) {
			homeController
			.getInfoMessage("Projekt fájl beolvasása", "Projekt fájl beolvasása sikertelen: \"" + projectFile.getName() + "\"");
		}
	}	
		return projectData;
	}
	
	public void addMVMXPertLogo(JFrame frame) {
		
		try {
			byte[] imageSource = this.getClass()
					.getResourceAsStream("/img/MVM.jpg").readAllBytes();
			frame.setIconImage(new ImageIcon(imageSource).getImage());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
}
