package mvmxpert.david.giczi.pillarcoordscalculator.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import mvmxpert.david.giczi.pillarcoordscalculator.controllers.HomeController;
import mvmxpert.david.giczi.pillarcoordscalculator.utils.BaseType;


public class ControlDirectionPointInputWindow {

	public HomeController homeController;
	public BaseType baseType;
	public JFrame inputFrameForDirectionControl;
	public JTextField directionControlPointIdField;
	public JTextField y_directionControlPointField;
	public JTextField x_directionControlPointField;
	public JRadioButton yesRadioButton;
	public JRadioButton noRadioButton;
	public JTextField complAngularField;
	public JTextField complMinField;
	public JTextField complSecField;
	private Color color = new Color(112,128,144);
	private Font font1 = new Font("Arial", Font.PLAIN, 16);
	private Font font2 = new Font("Arial", Font.BOLD, 13);
	
	public ControlDirectionPointInputWindow(String projectName, HomeController homeController) {
		inputFrameForDirectionControl = new JFrame(projectName);
		homeController.fileProcess.addMVMXPertLogo(inputFrameForDirectionControl);
		this.homeController = homeController;
		inputFrameForDirectionControl.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		inputFrameForDirectionControl.setSize(400, 300);
		inputFrameForDirectionControl.setLocationRelativeTo(null);
		inputFrameForDirectionControl.setLocationRelativeTo(null);
		inputFrameForDirectionControl.setLayout(new FlowLayout());
		setControlPointData();
		addButtons();
		inputFrameForDirectionControl.setResizable(false);
		inputFrameForDirectionControl.setVisible(true);
	}
	
	public void setBaseType(BaseType baseType) {
		this.baseType = baseType;
	}

	private void setControlPointData() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(Box.createVerticalStrut(10));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(380, 200));
		panel.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createEtchedBorder(),
						"Az előző/következő oh adatainak megadása", TitledBorder.CENTER, TitledBorder.TOP, font1, color));
		JLabel titleForCenter = new JLabel("Az oszlop hely középpontjának megadása");
		titleForCenter.setFont(font2);
		panel.add(Box.createHorizontalStrut(60));
		panel.add(titleForCenter);
		
		JLabel centerIDLabel = new JLabel("Az oszlop száma:");
		centerIDLabel.setFont(font2);
		directionControlPointIdField = new JTextField(15);
		directionControlPointIdField.setFont(font2);
		directionControlPointIdField.setForeground(color);
		panel.add(centerIDLabel);
		panel.add(Box.createHorizontalStrut(3));
		panel.add(directionControlPointIdField);
		
		JLabel centerYText = new JLabel("Y koordináta:");
		centerYText.setFont(font2);
		panel.add(centerYText);
		panel.add(Box.createHorizontalStrut(30));
		x_directionControlPointField = new JTextField(15);
		x_directionControlPointField.setFont(font2);
		x_directionControlPointField.setForeground(color);
		panel.add(x_directionControlPointField);
		panel.add(new JLabel("m"));
		JLabel centerXText = new JLabel("X koordináta:");
		centerXText.setFont(font2);
		panel.add(centerXText);
		panel.add(Box.createHorizontalStrut(30));
		y_directionControlPointField = new JTextField(15);
		y_directionControlPointField.setFont(font2);
		y_directionControlPointField.setForeground(color);
		panel.add(y_directionControlPointField);
		panel.add(new JLabel("m"));
		panel.add(Box.createVerticalStrut(30));
		inputFrameForDirectionControl.add(panel);
		setPillarArmDirectionData(panel);

	}
	
	private void setPillarArmDirectionData(JPanel panel) {
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(380, 210));
		JLabel askForArmDirection = new JLabel("Az oszlop karja szögfelezőben?");
		askForArmDirection.setFont(font2);
		panel.add(askForArmDirection);
		panel.add(Box.createVerticalStrut(40));
		panel.add(Box.createHorizontalStrut(20));
		yesRadioButton = new JRadioButton("Igen");
		yesRadioButton.setSelected(true);
		yesRadioButton.setBackground(Color.WHITE);
		yesRadioButton.setPreferredSize(new Dimension(60, 30));
		noRadioButton = new JRadioButton("Nem");
		noRadioButton.setBackground(Color.WHITE);
		noRadioButton.setPreferredSize(new Dimension(60, 30));
		ButtonGroup group = new ButtonGroup();
		group.add(yesRadioButton);
		group.add(noRadioButton);
		panel.add(yesRadioButton);
		panel.add(noRadioButton);
		JLabel complAngle = new JLabel("Kiegészítő szög:");
		complAngle.setFont(font2);
		panel.add(complAngle);
		panel.add(Box.createHorizontalStrut(2));
		complAngularField = new JTextField(4);
		complAngularField.setForeground(color);
		complAngularField.setEnabled(false);
		complMinField = new JTextField(4);
		complMinField.setForeground(color);
		complMinField.setEnabled(false);
		complSecField = new JTextField(4);
		complSecField.setForeground(color);
		complSecField.setEnabled(false);
		complAngularField.setFont(font2);
		complMinField.setFont(font2);
		complSecField.setFont(font2);
		panel.add(complAngularField);
		panel.add(new JLabel("fok"));
		panel.add(complMinField);
		panel.add(new JLabel("perc"));
		panel.add(complSecField);
		panel.add(new JLabel("mperc"));
		inputFrameForDirectionControl.add(panel);
		yesRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				complAngularField.setEnabled(false);
				complMinField.setEnabled(false);
				complSecField.setEnabled(false);
			}
		});
		noRadioButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				complAngularField.setEnabled(true);
				complMinField.setEnabled(true);
				complSecField.setEnabled(true);
			}
		});
	}
	
	private void addButtons() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(400, 50));
		JButton  delete = new JButton("Törlés");
		delete.setFont(font2);
		delete.setCursor(new Cursor(Cursor.HAND_CURSOR));
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if( homeController.getYesNoMessage("Adatok törlése", "Biztos, hogy törlöd a tájékozó pont adatait?") == 1) {
					return;
				}
				directionControlPointIdField.setText("");
				x_directionControlPointField.setText("");
				y_directionControlPointField.setText("");
				yesRadioButton.setSelected(true);
				complAngularField.setText("");
				complAngularField.setEnabled(false);
				complMinField.setText("");
				complMinField.setEnabled(false);
				complSecField.setText("");
				complSecField.setEnabled(false);
				homeController.controlDirectionPoint = null;
			}
		});
		panel.add(delete);
		inputFrameForDirectionControl.add(panel);
	}
	
}
