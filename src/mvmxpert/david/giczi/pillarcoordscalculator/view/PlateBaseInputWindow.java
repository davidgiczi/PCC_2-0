package mvmxpert.david.giczi.pillarcoordscalculator.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.PlateBaseController;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.BaseType;


public class PlateBaseInputWindow {

	public JFrame inputFrameForPlateBase;
	public JTextField centerIdField;
	public JTextField y_centerField;
	public JTextField x_centerField;
	public JTextField directionIdField;
	public JTextField y_directionField;
	public JTextField x_directionField;
	public JTextField horizontalSizeOfHoleField;
	public JTextField verticalSizeOfHoleField;
	public JTextField horizontalDistanceFromHoleSideField;
	public JTextField verticalDistanceFromHoleSideField;
	public JTextField rotateAngularField;
	public JTextField rotateAngularMinField;
	public JTextField rotateAngularSecField;
	public JCheckBox tps;
	public JCheckBox rtk;
	public JCheckBox kml;
	public JCheckBox ms;
	public JCheckBox all;
	private Color color = new Color(112,128,144);
	private Font font1 = new Font("Arial", Font.PLAIN, 16);
	private Font font2 = new Font("Arial", Font.BOLD, 13);
	private PlateBaseController plateBaseController;
	public JComboBox<String> sideComboBox;
	
	public PlateBaseInputWindow(String projectName, PlateBaseController plateBaseController) {
		this.plateBaseController = plateBaseController;
		inputFrameForPlateBase = new JFrame(projectName);
		plateBaseController.homeController.fileProcess.addMVMXPertLogo(inputFrameForPlateBase);
		inputFrameForPlateBase.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		inputFrameForPlateBase.setSize(400, 800);
		inputFrameForPlateBase.setLocationRelativeTo(null);
		inputFrameForPlateBase.setLocation(
				(int) (inputFrameForPlateBase.getLocation().getX() - 100), 
				(int) inputFrameForPlateBase.getLocation().getY());
		inputFrameForPlateBase.setLayout(new FlowLayout());
		setPillarPointsData();
		setPillarSizeData();
		setOutputData();
		addOkButton();
		inputFrameForPlateBase.setResizable(false);
		inputFrameForPlateBase.setVisible(true);
	}
	
	public void sharePillarCenterDataBetweenInputWindows() {
		
	if( plateBaseController.homeController.weightBaseInputWindow == null ) {
		return;
	}
	else if( plateBaseController.homeController.weightBaseInputWindow != null &&
				plateBaseController.homeController.weightBaseInputWindow.centerIdField.getText().isEmpty() &&
				plateBaseController.homeController.weightBaseInputWindow.x_centerField.getText().isEmpty() &&
				plateBaseController.homeController.weightBaseInputWindow.y_centerField.getText().isEmpty() &&
				plateBaseController.homeController.weightBaseInputWindow.directionIdField.getText().isEmpty() &&
				plateBaseController.homeController.weightBaseInputWindow.x_directionField.getText().isEmpty() &&
				plateBaseController.homeController.weightBaseInputWindow.y_directionField.getText().isEmpty() ) {
			return;
		}
	else if( plateBaseController.homeController.getYesNoMessage("Súlyalap pont koordináták átvétele", "Átveszi az adatokat?") == 1 ) {
			return;
	}
		centerIdField.setText(plateBaseController.homeController.weightBaseInputWindow.centerIdField.getText());
		x_centerField.setText(plateBaseController.homeController.weightBaseInputWindow.x_centerField.getText());
		y_centerField.setText(plateBaseController.homeController.weightBaseInputWindow.y_centerField.getText());
		directionIdField.setText(plateBaseController.homeController.weightBaseInputWindow.directionIdField.getText());
		x_directionField.setText(plateBaseController.homeController.weightBaseInputWindow.x_directionField.getText());
		y_directionField.setText(plateBaseController.homeController.weightBaseInputWindow.y_directionField.getText());	
	
	}
	
	private void setPillarPointsData() {
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if( e.getButton() == MouseEvent.BUTTON3 ) {
					
			if(plateBaseController.homeController.getYesNoMessage("Szeretnéd megcserélni az adatokat?", 
					"A kitűzendő-, és az előző/következő oszlop adatainak cseréje.") == 0) {
				exchangePillarData();
			}
					
				}
				
			}
				
		});
	
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createVerticalStrut(10));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(380, 260));
		panel.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createEtchedBorder(),
						"Az oszlop hely adatainak megadása", TitledBorder.CENTER, TitledBorder.TOP, font1, color));
		
		JLabel titleForCenter = new JLabel("Az oszlop középpontjának megadása");
		titleForCenter.setFont(font2);
		panel.add(Box.createHorizontalStrut(60));
		panel.add(titleForCenter);
		
		JLabel centerIDLabel = new JLabel("Az oszlop száma:");
		centerIDLabel.setFont(font2);
		centerIdField = new JTextField(15);
		centerIdField.setFont(font2);
		centerIdField.setForeground(color);
		panel.add(centerIDLabel);
		panel.add(Box.createHorizontalStrut(3));
		panel.add(centerIdField);
		
		JLabel centerYText = new JLabel("Y koordináta:");
		centerYText.setFont(font2);
		panel.add(centerYText);
		panel.add(Box.createHorizontalStrut(30));
		x_centerField = new JTextField(15);
		x_centerField.setDocument(new PlainDocument() {

			
			private static final long serialVersionUID = 1L;

			@Override
			protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
				
				if( chng.getOffset() < 3 && Character.isDigit(x_centerField.getText().charAt(0))) {
					x_directionField.setText(x_centerField.getText());
				}
				
				super.insertUpdate(chng, attr);
			}
			
		});
		x_centerField.setFont(font2);
		x_centerField.setForeground(color);
		panel.add(x_centerField);
		panel.add(new JLabel("m"));
		
		JLabel centerXText = new JLabel("X koordináta:");
		centerXText.setFont(font2);
		panel.add(centerXText);
		panel.add(Box.createHorizontalStrut(30));
		y_centerField = new JTextField(15);
		y_centerField.setDocument(new PlainDocument() {

			
			private static final long serialVersionUID = 1L;

			@Override
			protected void insertUpdate(DefaultDocumentEvent chng, AttributeSet attr) {
				
				if( chng.getOffset() < 3 && Character.isDigit(y_centerField.getText().charAt(0))) {
					y_directionField.setText(y_centerField.getText());
				}
				
				super.insertUpdate(chng, attr);
			}
			
		});
		y_centerField.setFont(font2);
		y_centerField.setForeground(color);
		panel.add(y_centerField);
		panel.add(new JLabel("m"));
		
		panel.add(Box.createVerticalStrut(30));
		JLabel titleForDirection = new JLabel("Az előző/következő oszlop koordinátáinak megadása");
		titleForDirection.setFont(font2);
		panel.add(titleForDirection);
		
		JLabel directionIDLabel = new JLabel("Az oszlop száma:");
		directionIDLabel.setFont(font2);
		directionIdField = new JTextField(15);
		directionIdField.setFont(font2);
		directionIdField.setForeground(color);
		panel.add(directionIDLabel);
		panel.add(Box.createHorizontalStrut(3));
		panel.add(directionIdField);
		
		panel.add(Box.createVerticalStrut(30));
		JLabel directionYText = new JLabel("Y koordináta:");
		directionYText.setFont(font2);
		panel.add(directionYText);
		panel.add(Box.createHorizontalStrut(30));
		x_directionField = new JTextField(15);
		x_directionField.setFont(font2);
		x_directionField.setForeground(color);
		panel.add(x_directionField);
		panel.add(new JLabel("m"));
		
		JLabel directionXText = new JLabel("X koordináta:");
		directionXText.setFont(font2);
		panel.add(directionXText);
		panel.add(Box.createHorizontalStrut(30));
		y_directionField = new JTextField(15);
		y_directionField.setFont(font2);
		y_directionField.setForeground(color);
		panel.add(y_directionField);
		panel.add(new JLabel("m"));
		sharePillarCenterDataBetweenInputWindows();
		inputFrameForPlateBase.add(panel);

	}
	
	private void setPillarSizeData() {
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if( e.getButton() == MouseEvent.BUTTON3 ) {
				plateBaseController.homeController.getCalculateDistanceBetweenPillarLegsWindow();
				}
				
			}
		});
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createVerticalStrut(10));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(380, 300));
		panel.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createEtchedBorder(),
						"Az alap geomteriai adatainak megadása", TitledBorder.CENTER, TitledBorder.TOP, font1, color));
		
		JLabel horizontalSizeForHoleText = new JLabel("Az alap gödrének mérete az oszlopkarra merőlegesen");
		horizontalSizeForHoleText.setFont(font2);
		panel.add(Box.createHorizontalStrut(60));
		panel.add(horizontalSizeForHoleText);
		
		panel.add(Box.createHorizontalStrut(120));
		horizontalSizeOfHoleField = new JTextField(15);
		horizontalSizeOfHoleField.setFont(font2);
		horizontalSizeOfHoleField.setForeground(color);
		panel.add(horizontalSizeOfHoleField);
		panel.add(new JLabel("m"));
		
		JLabel verticalSizeForHoleText = new JLabel("Az alap gödrének mérete az oszlopkarral párhuzamosan");
		verticalSizeForHoleText.setFont(font2);
		panel.add(Box.createHorizontalStrut(60));
		panel.add(verticalSizeForHoleText);
		
		panel.add(Box.createHorizontalStrut(120));
		verticalSizeOfHoleField = new JTextField(15);
		verticalSizeOfHoleField.setFont(font2);
		verticalSizeOfHoleField.setForeground(color);
		panel.add(verticalSizeOfHoleField);
		panel.add(new JLabel("m"));
				
		JLabel titleForHorizontalDistanceFromHole = new JLabel("A gödörtől vett távolság az oszlopkarra merőlegesen");
		titleForHorizontalDistanceFromHole.setFont(font2);
		panel.add(Box.createHorizontalStrut(60));
		panel.add(titleForHorizontalDistanceFromHole);
		
		panel.add(Box.createHorizontalStrut(120));
		horizontalDistanceFromHoleSideField = new JTextField(15);
		horizontalDistanceFromHoleSideField.setFont(font2);
		horizontalDistanceFromHoleSideField.setForeground(color);
		panel.add(horizontalDistanceFromHoleSideField);
		panel.add(new JLabel("m"));
		
		JLabel titleForVerticalSizeForPillarLeg = new JLabel("A gödörtől vett távolság az oszlopkarral párhuzamosan");
		titleForVerticalSizeForPillarLeg.setFont(font2);
		panel.add(Box.createHorizontalStrut(60));
		panel.add(titleForVerticalSizeForPillarLeg);
		
		panel.add(Box.createHorizontalStrut(120));
		verticalDistanceFromHoleSideField = new JTextField(15);
		verticalDistanceFromHoleSideField.setFont(font2);
		verticalDistanceFromHoleSideField.setForeground(color);
		panel.add(verticalDistanceFromHoleSideField);
		panel.add(new JLabel("m"));
		
		sideComboBox = new JComboBox<>();
		sideComboBox.addItem("jobb");
		sideComboBox.addItem("bal");
		sideComboBox.setFont(font2);
		JLabel angularText = new JLabel("A nyomvonal által közbezárt");
		angularText.setFont(font2);
		panel.add(angularText);
		panel.add(sideComboBox);
		JLabel restAngularText = new JLabel("oldali szög");
		restAngularText.setFont(font2);
		panel.add(restAngularText);
		panel.add(Box.createHorizontalStrut(55));
		
		rotateAngularField = new JTextField(5);
		rotateAngularField.setForeground(color);
		rotateAngularField.setText("180");
		rotateAngularMinField = new JTextField(5);
		rotateAngularMinField.setForeground(color);
		rotateAngularMinField.setText("0");
		rotateAngularSecField = new JTextField(5);
		rotateAngularSecField.setForeground(color);
		rotateAngularSecField.setText("0");
		rotateAngularField.setFont(font2);
		rotateAngularMinField.setFont(font2);
		rotateAngularSecField.setFont(font2);
		panel.add(rotateAngularField);
		panel.add(new JLabel("fok"));
		panel.add(rotateAngularMinField);
		panel.add(new JLabel("perc"));
		panel.add(rotateAngularSecField);
		panel.add(new JLabel("mperc"));
		
		inputFrameForPlateBase.add(panel);
	}
	
	private void setOutputData() {
		JPanel panel = new JPanel();
		panel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				plateBaseController.homeController.getControlDirectionPointInputWindow();
				plateBaseController.homeController.controlDirectionPointInputWindow.setBaseType(BaseType.PLATE_BASE);
			}
		});
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createVerticalStrut(30));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(380, 130));
		panel.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createEtchedBorder(),
						"Kimeneti fájlok megadása", TitledBorder.CENTER, TitledBorder.TOP, font1, color));
		tps = new JCheckBox("Mérőállomás formátumban");
		tps.setCursor(new Cursor(Cursor.HAND_CURSOR));
		tps.setBackground(Color.WHITE);
		tps.setFont(font2);
		tps.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				all.setSelected(false);
				
			}
		});
		panel.add(tps);
		rtk = new JCheckBox("GPS formátumban");
		rtk.setCursor(new Cursor(Cursor.HAND_CURSOR));
		rtk.setBackground(Color.WHITE);
		rtk.setFont(font2);
		rtk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				all.setSelected(false);
				
			}
		});
		panel.add(rtk);
		panel.add(Box.createHorizontalStrut(30));
		kml = new JCheckBox("KML formátumban");
		kml.setCursor(new Cursor(Cursor.HAND_CURSOR));
		kml.setBackground(Color.WHITE);
		kml.setFont(font2);
		kml.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				all.setSelected(false);
				
			}
		});
		panel.add(kml);
		ms = new JCheckBox("Microstation formátumban");
		ms.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ms.setBackground(Color.WHITE);
		ms.setFont(font2);
		ms.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				all.setSelected(false);
				
			}
		});
		panel.add(ms);
		panel.add(Box.createHorizontalStrut(100));
		all = new JCheckBox("Mindegyik formátumban", true);
		all.setCursor(new Cursor(Cursor.HAND_CURSOR));
		all.setBackground(Color.WHITE);
		all.setFont(font2);
		all.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				tps.setSelected(false);
				rtk.setSelected(false);
				kml.setSelected(false);
				ms.setSelected(false);
			}
		});
		panel.add(all);
		inputFrameForPlateBase.add(panel);
	}
	
	private void addOkButton() {
		JPanel panel = new JPanel();
		JButton ok = new JButton("Számol");
		ok.setFont(font2);
		ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				plateBaseController.handleCountButtonClick();
			}
		});
		panel.add(ok);
		inputFrameForPlateBase.add(panel);
	}
	
	private void exchangePillarData() {
		String centerPillarId = centerIdField.getText();
		String centerPillarX = x_centerField.getText();
		String centerPillarY = y_centerField.getText();
		centerIdField.setText(directionIdField.getText());
		x_centerField.setText(x_directionField.getText());
		y_centerField.setText(y_directionField.getText());
		directionIdField.setText(centerPillarId);
		x_directionField.setText(centerPillarX);
		y_directionField.setText(centerPillarY);
		
	}
	
}
