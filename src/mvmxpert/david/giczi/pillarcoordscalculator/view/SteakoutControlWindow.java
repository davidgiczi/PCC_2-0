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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import mvmxpert.david.giczi.pillarcoordscalculator.controllers.PlateBaseController;
import mvmxpert.david.giczi.pillarcoordscalculator.controllers.WeightBaseController;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.service.InputDataValidator;


public class SteakoutControlWindow {
	
	public JFrame steakoutControlFrame;
	public JTextField stkFileNameField;
	public JTextField stkFilePlaceField;
	public String prePostFixSelectedOption = "pontszám";
	public String prePostFixValue;
	public String delimiterSelectedValue = ",";
	public JRadioButton yesPrintRadioBtn;
	public JRadioButton noPrintRadioBtn;
	public JLabel savedPointsTextLabel;
	private WeightBaseController weightBaseController;
	private PlateBaseController plateBaseController;
	private Color color = new Color(112,128,144);
	private Font font1 = new Font("Arial", Font.PLAIN, 16);
	private Font font2 = new Font("Arial", Font.BOLD, 13);
	
	
	public SteakoutControlWindow(String projectPathAndName, WeightBaseController weightBaseController) {
		this.weightBaseController = weightBaseController;
		steakoutControlFrame = new JFrame(projectPathAndName);
		new FileProcess().addMVMXPertLogo(steakoutControlFrame);
		steakoutControlFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		steakoutControlFrame.setSize(400, 800);
		steakoutControlFrame.setLocationRelativeTo(null);
		steakoutControlFrame.setLocation((int) (steakoutControlFrame.getLocation().getX() + 465), 
				(int) steakoutControlFrame.getLocation().getY());
		steakoutControlFrame.setLayout(new FlowLayout());
		setSteakoutFileData();
		setSteakoutFileContentType();
		setOutputData();
		addOkButton();
		steakoutControlFrame.setResizable(false);
		steakoutControlFrame.setVisible(true);
	}
	
	public SteakoutControlWindow(String projectPathAndName, PlateBaseController plateBaseController) {
		this.plateBaseController = plateBaseController;
		steakoutControlFrame = new JFrame(projectPathAndName);
		new FileProcess().addMVMXPertLogo(steakoutControlFrame);
		steakoutControlFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		steakoutControlFrame.setSize(400, 800);
		steakoutControlFrame.setLocationRelativeTo(null);
		steakoutControlFrame.setLocation((int) (steakoutControlFrame.getLocation().getX() + 465), 
				(int) steakoutControlFrame.getLocation().getY());
		steakoutControlFrame.setLayout(new FlowLayout());
		setSteakoutFileData();
		setSteakoutFileContentType();
		setOutputData();
		addOkButton();
		steakoutControlFrame.setResizable(false);
		steakoutControlFrame.setVisible(true);
	}
	

	private void setSteakoutFileData() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(380, 230));
		panel.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createEtchedBorder(),
						"Kitűzési fájl helyének/nevének megadása", TitledBorder.CENTER, TitledBorder.TOP, font1, color));
		panel.add(Box.createHorizontalStrut(130));
		stkFileNameField = new JTextField(32);
		stkFilePlaceField = new JTextField(32);
		JButton browseBtn = new JButton("Tallózás");
		browseBtn.setFont(font2);
		browseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.add(browseBtn);
		browseBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				stkFileNameField.setText(null);
				stkFilePlaceField.setText(null);
				FileProcess.setSteakoutFile();
				stkFileNameField.setText(FileProcess.STK_FILE_NAME);
				stkFilePlaceField.setText(FileProcess.STK_FILE_PATH);
			}
		});
		panel.add(Box.createHorizontalStrut(100));
		JLabel stkFileNameTextLabel = new JLabel("A fájl neve:");
		stkFileNameTextLabel.setFont(font2);
		panel.add(stkFileNameTextLabel);
		panel.add(Box.createVerticalStrut(30));
		stkFileNameField.setFont(font2);
		stkFileNameField.setForeground(color);
		stkFileNameField.setEditable(false);
		stkFilePlaceField.setFont(font2);
		stkFilePlaceField.setForeground(color);
		stkFilePlaceField.setEditable(false);
		panel.add(stkFileNameField);
		panel.add(Box.createVerticalStrut(30));
		JLabel stkFilePlaceTextLabel = new JLabel("A fájl helye:");
		stkFilePlaceTextLabel.setFont(font2);
		savedPointsTextLabel = new JLabel("Az ellenőrzött pontok száma: 0 db");
		savedPointsTextLabel.setFont(font2);
		panel.add(stkFilePlaceTextLabel);
		panel.add(stkFilePlaceField);
		panel.add(Box.createVerticalStrut(30));
		panel.add(savedPointsTextLabel);
		panel.add(Box.createHorizontalStrut(30));
		JButton delBtn = new JButton("Törlés");
		delBtn.setFont(font2);
		delBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		delBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		panel.add(delBtn);
		steakoutControlFrame.add(panel);
	}
	
	private void setSteakoutFileContentType() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(380, 230));
		panel.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createEtchedBorder(),
						"Kitűzési fájl lekérdezési adatainak beállítása", TitledBorder.CENTER, TitledBorder.TOP, font1, color));
		JLabel prePostFixText = new JLabel("A kitűzési fájlban lévő pontok azonosítója:");
		prePostFixText.setFont(font2);
		panel.add(prePostFixText);
		panel.add(Box.createVerticalStrut(80));
		String[] prePostFixType = {"pontszám", "előtag", "utótag"};
		JComboBox<String> prePostFixCombo = new JComboBox<>(prePostFixType);
		JTextField prePostFixValueField = new JTextField(32);
		JLabel prePostFixValueText = new JLabel();
		prePostFixCombo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		prePostFixCombo.addActionListener(new ActionListener() {

				
			@Override
			public void actionPerformed(ActionEvent e) {
				
				prePostFixValueText.setText(null);
				prePostFixValueField.setText(null);
				prePostFixSelectedOption = prePostFixCombo.getSelectedItem().toString();
				
				if( !"pontszám".equals(prePostFixSelectedOption) ) {
				prePostFixValue = 
						JOptionPane.showInputDialog(null, "Add meg az " + prePostFixSelectedOption + " értékét:",
								"Az " + prePostFixSelectedOption +  " értékének megadása", JOptionPane.DEFAULT_OPTION);
				
					if(prePostFixValue != null && InputDataValidator.isValidPrePostFixValue(prePostFixValue)) {
					prePostFixValueText.setText("A pontszám " + prePostFixSelectedOption + " értéke:");
					prePostFixValueField.setText(prePostFixValue);
					}
					else if(prePostFixValue != null && !InputDataValidator.isValidPrePostFixValue(prePostFixValue)) {
					JOptionPane.showMessageDialog(null, "Az " + prePostFixSelectedOption + " értéke legalább egy, nem üres karakter lehet." , 
							"Az " + prePostFixSelectedOption  + " értékének megadása", JOptionPane.INFORMATION_MESSAGE);
					}
			}
				
		}
		});
		panel.add(prePostFixCombo);
		prePostFixValueText.setFont(font2);
		panel.add(prePostFixValueText);
		prePostFixValueField.setFont(font2);
		prePostFixValueField.setForeground(color);
		prePostFixValueField.setEditable(false);
		panel.add(prePostFixValueField);
		JLabel delimiterText = new JLabel("A kitűzési fájlban lévő elválasztó karakter:");
		delimiterText.setFont(font2);
		panel.add(delimiterText);
		panel.add(Box.createVerticalStrut(80));
		String[] delimiterValues = {",", ";", "space"};
		JComboBox<String> delimiterCombo = new JComboBox<>(delimiterValues);
		delimiterCombo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String selectedValue = delimiterCombo.getSelectedItem().toString();
				
				if( "space".equals(selectedValue) ) {
					delimiterSelectedValue = " ";
				}
				else {
					delimiterSelectedValue = selectedValue;
				}
			}
		});
		prePostFixCombo.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.add(delimiterCombo);
		
		steakoutControlFrame.add(panel);
	}
	
	
	private void setOutputData() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setBackground(Color.WHITE);
		panel.setPreferredSize(new Dimension(380, 240));
		panel.setBorder(BorderFactory
				.createTitledBorder(BorderFactory.createEtchedBorder(),
						"Kimeneti fájl mentése", TitledBorder.CENTER, TitledBorder.TOP, font1, color));
		panel.add(Box.createVerticalStrut(50));
		JLabel saveFileLabel = new JLabel("Az ellenőrzés eredményének fájlba mentése?");
		saveFileLabel.setFont(font2);
		panel.add(saveFileLabel);
		panel.add(Box.createVerticalStrut(30));
		panel.add(Box.createHorizontalStrut(100));
		ButtonGroup group = new ButtonGroup();
		yesPrintRadioBtn = new JRadioButton("Igen", true);
		yesPrintRadioBtn.setBackground(Color.WHITE);
		panel.add(yesPrintRadioBtn);
		noPrintRadioBtn = new JRadioButton("Nem");
		noPrintRadioBtn.setBackground(Color.WHITE);
		group.add(yesPrintRadioBtn);
		group.add(noPrintRadioBtn);
		panel.add(noPrintRadioBtn);
		panel.add(Box.createVerticalStrut(30));
		panel.add(Box.createHorizontalStrut(100));
		JLabel saveStkFileTextLabel = new JLabel("A fájl mentési helye:");
		saveStkFileTextLabel.setFont(font2);
		panel.add(saveStkFileTextLabel);
		panel.add(Box.createHorizontalStrut(130));
		JButton browseBtn = new JButton("Tallózás");
		browseBtn.setFont(font2);
		browseBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		panel.add(browseBtn);
		panel.add(Box.createVerticalStrut(60));
		JTextField saveStkFileField = new JTextField(32);
		saveStkFileField.setFont(font2);
		saveStkFileField.setForeground(color);
		saveStkFileField.setText(FileProcess.FOLDER_PATH);
		saveStkFileField.setEditable(false);
		panel.add(saveStkFileField);
		browseBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveStkFileField.setText(null);
				FileProcess.setFolderForSteakoutedPointFile();
				saveStkFileField.setText(FileProcess.STK_SAVED_FILE_PATH);
			}
		});
		steakoutControlFrame.add(panel);
	}
	
	private void addOkButton() {
		
		JPanel panel = new JPanel();
		JButton ok = new JButton("Ok");
		ok.setFont(font2);
		ok.setCursor(new Cursor(Cursor.HAND_CURSOR));
		ok.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		panel.add(ok);
		steakoutControlFrame.add(panel);
		
	}
}
