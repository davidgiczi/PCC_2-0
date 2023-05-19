package mvmxpert.david.giczi.pillarcoordscalculator.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import mvmxpert.david.giczi.pillarcoordscalculator.controllers.HomeController;
import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;

public class CalculateDistanceBetweenPillarLegsWindow {

	
	public JFrame inputFrame;
	public JTextField distanceOfLegsField;
	public JTextField illesztesiSikField;
	public JTextField sudarasodasField;
	public JTextField resultField;
	private Font font = new Font("Arial", Font.BOLD, 13);
	private Color color = new Color(112,128,144);
	private HomeController homeController;
	
	public CalculateDistanceBetweenPillarLegsWindow(HomeController homeController) {
		this.homeController = homeController;
		inputFrame = new JFrame("Oszloplábak távolságának számítása");
		this.homeController.fileProcess.addMVMXPertLogo(inputFrame);
		inputFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		inputFrame.setSize(400, 240);
		inputFrame.setLocationRelativeTo(homeController.homeWindow.homeFrame);
		inputFrame.setLayout(new FlowLayout());
		setInputData();
		addOkButton();
		inputFrame.setResizable(false);
		inputFrame.setVisible(true);
	}
	
	private void setInputData() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(380, 150));
		panel.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createEtchedBorder(),
						"Adatok megadása", TitledBorder.CENTER, TitledBorder.TOP, font, color));
		
		JLabel distanceLabel = new JLabel("Lábtávolság értéke:");
		distanceLabel.setFont(font);
		panel.add(distanceLabel);
		panel.add(Box.createHorizontalStrut(70));
		distanceOfLegsField = new JTextField(10);
		distanceOfLegsField.setFont(font);
		distanceOfLegsField.setForeground(color);
		panel.add(distanceOfLegsField);
		panel.add(new JLabel("mm"));
		
		JLabel illesztesiSikLabel = new JLabel("Illesztési sík értéke:");
		illesztesiSikLabel.setFont(font);
		panel.add(illesztesiSikLabel);
		panel.add(Box.createHorizontalStrut(71));
		illesztesiSikField = new JTextField(10);
		illesztesiSikField.setFont(font);
		illesztesiSikField.setForeground(color);
		panel.add(illesztesiSikField);
		panel.add(new JLabel("mm"));
		panel.add(Box.createVerticalStrut(30));
		
		JLabel sudarasodasLabel = new JLabel("Sudarasodás értéke:");
		sudarasodasLabel.setFont(font);
		panel.add(sudarasodasLabel);
		panel.add(Box.createHorizontalStrut(63));
		sudarasodasField = new JTextField(10);
		sudarasodasField.setFont(font);
		sudarasodasField.setForeground(color);
		panel.add(sudarasodasField);
		panel.add(new JLabel("mm/m"));
		panel.add(Box.createVerticalStrut(30));
		
		JLabel resultLabel = new JLabel("A lábak számított távolsága:");
		resultLabel.setFont(font);
		panel.add(resultLabel);
		panel.add(Box.createHorizontalStrut(18));
		resultField = new JTextField(10);
		resultField.setFont(font);
		resultField.setForeground(Color.RED);
		resultField.setBorder(new LineBorder(Color.WHITE, 1));
		resultField.setHorizontalAlignment(JTextField.CENTER);
		panel.add(resultField);
		panel.add(new JLabel("m"));
		inputFrame.add(panel);
	}
	
	private void addOkButton() {
		JPanel panel = new JPanel();
		JButton ok = new JButton("Számol");
		ok.setFont(font);
		ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int distanceOfLegsValue;
				try {
					distanceOfLegsValue = InputDataValidator.isValidInputPositiveIntegerValue(distanceOfLegsField.getText().replace(',', '.'));
				} catch (Exception e2) {
					homeController.getYesNoMessage("Nem megfelelő a lábtávolság értéke", 
							"A lábtávolság értéke csak pozitív egész szám lehet.");
					return;
				}
				int illesztesiSikValue;
				try {
					illesztesiSikValue = InputDataValidator.isValidInputPositiveIntegerValue(illesztesiSikField.getText().replace(',', '.'));
				} catch (Exception e2) {
					homeController.getYesNoMessage("Nem megfelelő az illesztesi sík értéke", 
							"Az illesztési sík értéke csak pozitív egész szám lehet.");
					return;
				}
				double sudarasodasValue;
				try {
					sudarasodasValue = InputDataValidator.isValidInputPositiveDoubleValue(sudarasodasField.getText().replace(',', '.'));
				} catch (Exception e2) {
					homeController.getYesNoMessage("Nem megfelelő a sudarasodás értéke", 
							"A sudarasodás értéke csak pozitív szám lehet.");
					return;
				}
				DecimalFormat df = new DecimalFormat("0.000");
				double distance = distanceOfLegsValue / 1000.0  + (2 * sudarasodasValue * illesztesiSikValue / 100.0) / 1000.0;
				resultField.setText(df.format(distance).replace(',', '.'));
			}
		});
	 
		panel.add(ok);
		inputFrame.add(panel);
	}
}
