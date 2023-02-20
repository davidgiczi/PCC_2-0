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
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import mvmxpert.david.giczi.pillarcoordscalculator.controllers.HomeController;
import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;


public class HomeWindow {

	private JFrame homeFrame;
	private HomeController homeController;
	private Color textColor = new Color(112,128,144);
	public JMenu baseDataMenu;
	public JMenu controlSteakoutMenu;
	
	public HomeWindow(HomeController homeController) {
		this.homeController = homeController;
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
				homeController.openProject();
			}
		});
		JMenuItem item12 = new JMenuItem("Új projekt létrehozása");
		item12.setForeground(textColor);
		item12.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item12.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				homeController.createNewProject();
			}
		});
		menu1.add(item11);
		menu1.add(item12);
		baseDataMenu = new JMenu("Alap adatainak megadása");
		baseDataMenu.setForeground(textColor);
		baseDataMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		baseDataMenu.setEnabled(false);
		controlSteakoutMenu = new JMenu("Kitűzött pontok ellenőrzése");
		controlSteakoutMenu.setForeground(textColor);
		controlSteakoutMenu.setCursor(new Cursor(Cursor.HAND_CURSOR));
		controlSteakoutMenu.addMenuListener(null);
		controlSteakoutMenu.setEnabled(false);
		controlSteakoutMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
			homeController.getSteakoutControlWindow();	
			}
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
			}
		});
		
		JMenuBar menuBar = new JMenuBar();
		JMenuItem item20 = new JMenuItem("Oszlop lábak távolságának számítása");
		item20.setForeground(textColor);
		item20.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item20.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				homeController.getCalculateDistanceBetweenPillarLegsWindow();
			}
		});
		JMenuItem item21 = new JMenuItem("Súlyalap pontjainak számítása");
		item21.setForeground(textColor);
		item21.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item21.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				homeController.getWeightBaseDisplayer();
			}
		});
		JMenuItem item22 = new JMenuItem("Lemezalap pontjainak számítása");
		item22.setForeground(textColor);
		item22.setCursor(new Cursor(Cursor.HAND_CURSOR));
		item22.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				homeController.getPlateBaseDisplayer();
			}
		});
		menuBar.add(menu1);
		menuBar.add(baseDataMenu);
		baseDataMenu.add(item20);
		baseDataMenu.addSeparator();
		baseDataMenu.add(item21);
		baseDataMenu.add(item22);
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
