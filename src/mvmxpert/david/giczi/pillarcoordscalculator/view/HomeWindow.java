package mvmxpert.david.giczi.pillarcoordscalculator.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.listeners.CreateSteakoutWindowListener;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PillarCoordsCalculatorService;

public class HomeWindow {

	private JFrame homeFrame;
	private Color textColor = new Color(112,128,144);
	public JMenu steakoutMenu;
	public JMenu controlSteakoutMenu;
	
	public HomeWindow() {
		
		homeFrame = new JFrame("Nagyfeszültségű távvezeték oszlop alapjának kitűzése");
		new FileProcess().addMVMXPertLogo(homeFrame);
		homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		homeFrame.setSize(550, 800);
		homeFrame.setLocationRelativeTo(null);
		addMenu();
		addImage();
		homeFrame.setResizable(false);
		homeFrame.setVisible(true);
	}
	
	private void addMenu() {
		JMenu menu1 = new JMenu("Projekt létrehozása/megnyitása");
		menu1.setForeground(textColor);
		menu1.setCursor(new Cursor(Cursor.HAND_CURSOR));
		JMenuItem item11 = new JMenuItem("Projekt megnyitása");
		item11.setForeground(textColor);
		item11.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item11.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PillarCoordsCalculatorService.setProject();
			}
		});
		JMenuItem item12 = new JMenuItem("Új projekt létrehozása");
		item12.setForeground(textColor);
		item12.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item12.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String projectName =  PillarCoordsCalculatorService.setProjectName();
				
				if( projectName != null )	{
					PillarCoordsCalculatorService.PROJECT_NAME = projectName;
					PillarCoordsCalculatorService.getExistedProjectInfoMessage();
					PillarCoordsCalculatorService.destroy();
				}
			}
		});
		menu1.add(item11);
		menu1.add(item12);
		steakoutMenu = new JMenu("Alap adatainak megadása");
		steakoutMenu.setForeground(textColor);
		steakoutMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		steakoutMenu.setEnabled(false);
		controlSteakoutMenu = new JMenu("Kitűzött pontok ellenőrzése");
		controlSteakoutMenu.setForeground(textColor);
		controlSteakoutMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		controlSteakoutMenu.addMenuListener(new CreateSteakoutWindowListener());
		controlSteakoutMenu.setEnabled(false);
		JMenuBar menuBar = new JMenuBar();
		JMenuItem item21 = new JMenuItem("Súlyalap pontjainak számítása");
		item21.setForeground(textColor);
		item21.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item21.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PillarCoordsCalculatorService.getWeightBaseInputWindow();
			}
		});
		JMenuItem item22 = new JMenuItem("Lemezalap pontjainak számítása");
		item22.setForeground(textColor);
		item22.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item22.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PillarCoordsCalculatorService.getPlateBaseInputWindow();
			}
		});
		menuBar.add(menu1);
		menuBar.add(steakoutMenu);
		steakoutMenu.add(item21);
		steakoutMenu.add(item22);
		menuBar.add(controlSteakoutMenu);
		homeFrame.setJMenuBar(menuBar);
	}
	
	private void addImage() {
		try {
			byte[] imageSource = this.getClass()
					.getResourceAsStream("/img/pillars" + (int) (Math.random() * 3 + 1)  +".jpg").readAllBytes();
			homeFrame.add(new JLabel(new ImageIcon(imageSource)));
			 
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
}
