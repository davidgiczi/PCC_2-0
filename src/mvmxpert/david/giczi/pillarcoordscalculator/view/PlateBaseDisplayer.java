package mvmxpert.david.giczi.pillarcoordscalculator.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;

import mvmxpert.david.giczi.pillarcoordscalculator.fileprocess.FileProcess;
import mvmxpert.david.giczi.pillarcoordscalculator.service.AzimuthAndDistance;
import mvmxpert.david.giczi.pillarcoordscalculator.service.Point;
import mvmxpert.david.giczi.pillarcoordscalculator.service.PolarPoint;
import mvmxpert.david.giczi.pillarcoordscalculator.service.SteakoutedCoords;

public class PlateBaseDisplayer extends JFrame{

	
	private static final long serialVersionUID = 1L;
	
	private List<Point> pillarBasePoints;
	private List<Point> transformedPillarBasePoints;
	private List<SteakoutedCoords> controlledCoords = new ArrayList<>();
	private double displayerCenterX;
	private double displayerCenterY;
	private Point directionDisplayerPoint;
	private Point directionPoint;
	private double rotation = 0;
	private static final double SCALE = 2 * 22.5;
	
	public void setControlledCoords(List<SteakoutedCoords> controlledCoords) {
		this.controlledCoords = controlledCoords;
	}

	public PlateBaseDisplayer(List<Point> pillarBasePoints, Point directionPoint, double rotation, String projectPathAndName) {
		 	super(projectPathAndName);
		 	new FileProcess().addMVMXPertLogo(this);
		 	this.directionPoint = directionPoint;
		 	this.pillarBasePoints = pillarBasePoints;
		 	this.rotation = rotation;
		 	getDisplayerCenterCoords();
		 	this.directionDisplayerPoint = new Point(directionPoint.getPointID(), 
		 			displayerCenterX + Math.round((directionPoint.getX_coord() - pillarBasePoints.get(0).getX_coord()) * 1000.0) / SCALE,
		 			displayerCenterY - Math.round((directionPoint.getY_coord() - pillarBasePoints.get(0).getY_coord()) * 1000.0) / SCALE);
		 	transformPillarCoordsForDisplayer();
		 	setExtendedState(JFrame.MAXIMIZED_BOTH);
	        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	        getContentPane().setBackground(Color.WHITE);
	        setLocationRelativeTo(null);
	        setVisible(true);
	}
		
	private void transformPillarCoordsForDisplayer(){
		transformedPillarBasePoints = new ArrayList<>();
		double X = pillarBasePoints.get(0).getX_coord();
		double Y = pillarBasePoints.get(0).getY_coord();
		for (Point pillarBasePoint : pillarBasePoints) {
			Point point = new Point(pillarBasePoint.getPointID(), 
			displayerCenterX + Math.round((pillarBasePoint.getX_coord() - X) * 1000.0) / SCALE,
			displayerCenterY -	Math.round((pillarBasePoint.getY_coord() - Y) * 1000.0) / SCALE);
			transformedPillarBasePoints.add(point);
		}
	}
	
	private void getDisplayerCenterCoords() {
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    displayerCenterX = ((dimension.getWidth() - this.getWidth()) / 2);
	    displayerCenterY = ((dimension.getHeight() - this.getHeight()) / 2);
	}
	
	private void drawBase(Graphics g) {
	        Graphics2D g2d = (Graphics2D) g;
	        g2d.setColor(Color.BLUE);
	        g2d.setStroke(new BasicStroke(2));
	        g2d.draw(new Line2D.Double(
	        			transformedPillarBasePoints.get(1).getX_coord(), 
	        			transformedPillarBasePoints.get(1).getY_coord(),
	        			transformedPillarBasePoints.get(2).getX_coord(),
	        			transformedPillarBasePoints.get(2).getY_coord()));
	        g2d.draw(new Line2D.Double(
	        			transformedPillarBasePoints.get(2).getX_coord(), 
	        			transformedPillarBasePoints.get(2).getY_coord(),
	        			transformedPillarBasePoints.get(3).getX_coord(),
	        			transformedPillarBasePoints.get(3).getY_coord()));
	        g2d.draw(new Line2D.Double(
					   	transformedPillarBasePoints.get(3).getX_coord(), 
					   	transformedPillarBasePoints.get(3).getY_coord(),
					   	transformedPillarBasePoints.get(4).getX_coord(),
					   	transformedPillarBasePoints.get(4).getY_coord()));
	        g2d.draw(new Line2D.Double(
				   		transformedPillarBasePoints.get(4).getX_coord(), 
				   		transformedPillarBasePoints.get(4).getY_coord(),
				   		transformedPillarBasePoints.get(1).getX_coord(),
				   		transformedPillarBasePoints.get(1).getY_coord()));
	        g2d.setColor(Color.RED);
	        float[] dashingPattern1 = {10f, 10f};
	        Stroke stroke = new BasicStroke(2f, BasicStroke.CAP_BUTT,
	                BasicStroke.JOIN_MITER, 1.0f, dashingPattern1, 2.0f);
	        g2d.setStroke(stroke);
	        g2d.draw(new Line2D.Double(
	        			transformedPillarBasePoints.get(5).getX_coord(), 
					   	transformedPillarBasePoints.get(5).getY_coord(),
					   	transformedPillarBasePoints.get(7).getX_coord(),
					   	transformedPillarBasePoints.get(7).getY_coord()));
	        g2d.draw(new Line2D.Double(
	        			transformedPillarBasePoints.get(6).getX_coord(), 
	        			transformedPillarBasePoints.get(6).getY_coord(),
	        			transformedPillarBasePoints.get(8).getX_coord(),
	        			transformedPillarBasePoints.get(8).getY_coord()));
	         
	        g2d.setStroke(new BasicStroke(2));
	        g2d.setColor(Color.MAGENTA);
	        
	        if( rotation == 0) {
	        AzimuthAndDistance azimuth = new AzimuthAndDistance(transformedPillarBasePoints.get(6), transformedPillarBasePoints.get(8));
	        PolarPoint polarPoint1 = new PolarPoint(transformedPillarBasePoints.get(6), 100,
	        		azimuth.calcAzimuth() + Math.PI, "baseLine");
	        PolarPoint polarPoint2 = new PolarPoint(polarPoint1.calcPolarPoint(), 30,
	        		azimuth.calcAzimuth() + Math.PI / 4, "arrow");
	        PolarPoint polarPoint3 = new PolarPoint(polarPoint1.calcPolarPoint(), 30,
	        		azimuth.calcAzimuth() - Math.PI / 4, "arrow");
	        g2d.draw(new Line2D.Double(
        			transformedPillarBasePoints.get(6).getX_coord(),
        			transformedPillarBasePoints.get(6).getY_coord(),
    				polarPoint1.calcPolarPoint().getX_coord(), 
    				polarPoint1.calcPolarPoint().getY_coord()));
	        g2d.draw(new Line2D.Double(
	        			polarPoint1.calcPolarPoint().getX_coord(),
	        			polarPoint1.calcPolarPoint().getY_coord(),
        				polarPoint2.calcPolarPoint().getX_coord(), 
        				polarPoint2.calcPolarPoint().getY_coord()));
	        g2d.draw(new Line2D.Double(
	        			polarPoint1.calcPolarPoint().getX_coord(),
	        			polarPoint1.calcPolarPoint().getY_coord(),
        				polarPoint3.calcPolarPoint().getX_coord(), 
        				polarPoint3.calcPolarPoint().getY_coord()));
	  }
	        else {
	        	
	        AzimuthAndDistance azimuthAndDistance = new AzimuthAndDistance(transformedPillarBasePoints.get(0), directionDisplayerPoint);
	  	    PolarPoint polarPoint = 
	  	        		new PolarPoint(transformedPillarBasePoints.get(0), 300, 
	  	        				azimuthAndDistance.calcAzimuth(), "baseLine");
	  	    PolarPoint polarPoint1 = new PolarPoint(polarPoint.calcPolarPoint(), 30,
	        		azimuthAndDistance.calcAzimuth() - 3 * Math.PI / 4, "arrow");
	        PolarPoint polarPoint2 = new PolarPoint(polarPoint.calcPolarPoint(), 30,
	        		azimuthAndDistance.calcAzimuth() + 3 * Math.PI / 4, "arrow");
	  	    g2d.draw(new Line2D.Double(
	  	    		transformedPillarBasePoints.get(0).getX_coord(),
	  	    		transformedPillarBasePoints.get(0).getY_coord(),
      				polarPoint.calcPolarPoint().getX_coord(), 
      				polarPoint.calcPolarPoint().getY_coord()));
	  	   g2d.draw(new Line2D.Double(
	  			   polarPoint.calcPolarPoint().getX_coord(), 
	  			   polarPoint.calcPolarPoint().getY_coord(),
	  			   polarPoint1.calcPolarPoint().getX_coord(), 
	  			   polarPoint1.calcPolarPoint().getY_coord()));
	  	   g2d.draw(new Line2D.Double(
	  			   polarPoint.calcPolarPoint().getX_coord(), 
	  			   polarPoint.calcPolarPoint().getY_coord(),
	  			   polarPoint2.calcPolarPoint().getX_coord(), 
	  			   polarPoint2.calcPolarPoint().getY_coord()));
	        }
	      //Scale
	        g2d.setColor(Color.BLACK);
	        g2d.draw(new Line2D.Double(displayerCenterX + 300.0, displayerCenterY + 300.0,
	        						   displayerCenterX + 322.5, displayerCenterY + 300.0));
	        g2d.draw(new Line2D.Double(displayerCenterX + 300.0, displayerCenterY + 295.5,
					   displayerCenterX + 300.0, displayerCenterY + 304.5));
	        g2d.draw(new Line2D.Double(displayerCenterX + 322.5, displayerCenterY + 295.5,
					   displayerCenterX + 322.5, displayerCenterY + 304.5));
	    }
	 
		private void writeCoords(Graphics g) {
			float X = 100f;
			float Y = 100f;
			Graphics2D g2d = (Graphics2D) g;
	 		g2d.setFont(new Font("Arial",Font.BOLD, 16));
	 		DecimalFormat df = new DecimalFormat("000.000");
	 		for (int i = 0; i < pillarBasePoints.size(); i++) {
	 			if( i == 0) {
	 				g2d.setColor(Color.MAGENTA);
	 			}
	 			else {
	 				g2d.setColor(Color.BLACK);
	 			}
	 			g2d.drawString(pillarBasePoints.get(i).getPointID(), X, Y);
	 			X += 80f;
	 			g2d.setColor(Color.RED);
	 			g2d.drawString(df.format(pillarBasePoints.get(i).getX_coord()), X, Y);
	 			X += 110f;
				g2d.drawString(df.format(pillarBasePoints.get(i).getY_coord()), X, Y);
				X = 100f;
				Y += 30f;
			}
			
		}
		
		private void writeCoordDifference(Graphics g) {
			float X = 100f;
			float Y = 80f;
			Graphics2D g2d = (Graphics2D) g;
	 		g2d.setFont(new Font("Arial",Font.BOLD, 16));
	 		DecimalFormat df = new DecimalFormat("000.000");
	 		for (SteakoutedCoords steakoutedCoord : controlledCoords) {
	 			g2d.setColor(Color.BLACK);
	 			g2d.drawString(steakoutedCoord.getStkPointID(), X, Y);
	 			X += 110f;
	 			g2d.setColor(Color.RED);
	 			g2d.drawString(df.format(steakoutedCoord.getDeltaX()), X, Y);
	 			X += 140f;
				g2d.drawString(df.format(steakoutedCoord.getDeltaY()), X, Y);
				X = 100f;
				Y += 30f;
			}
			
		}
		
		private void writeDistanceBetweenPillars(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
	 		DecimalFormat df = new DecimalFormat("###.###");
	 		double distance = new AzimuthAndDistance(pillarBasePoints.get(0), directionPoint).calcDistance();
	 		g2d.drawString(transformedPillarBasePoints.get(0).getPointID() + 
	 				". és " + directionDisplayerPoint.getPointID() + ". oszlopok távolsága: " +
	 				df.format(distance)+"m", (float) (displayerCenterX + 300), (float) (displayerCenterY + 350));
		}
		
	 	private void writeText(Graphics g) {
	 		Graphics2D g2d = (Graphics2D) g;
	 		g2d.setColor(Color.BLACK);
	 		
	 		for (int i = 1; i < pillarBasePoints.size(); i++) {
	 			g2d.drawString(pillarBasePoints.get(i).getPointID(),
						(float)	transformedPillarBasePoints.get(i).getX_coord() + 15, (float) transformedPillarBasePoints.get(i).getY_coord() + 20);
	 		}
	 		g2d.setColor(Color.MAGENTA);
	 		g2d.drawString(pillarBasePoints.get(0).getPointID(),
					(float)	transformedPillarBasePoints.get(0).getX_coord() + 15, (float) transformedPillarBasePoints.get(0).getY_coord() + 20);
	 		AzimuthAndDistance azimuthAndDistance = new AzimuthAndDistance(transformedPillarBasePoints.get(0), directionDisplayerPoint);
	 		PolarPoint polarPoint = 
	  	        		new PolarPoint(transformedPillarBasePoints.get(0), 300, 
	  	        				azimuthAndDistance.calcAzimuth(), "baseLine");
	 		
	 		g2d.drawString(directionDisplayerPoint.getPointID(), (float) (polarPoint.calcPolarPoint().getX_coord() - 80), 
	 				(float) polarPoint.calcPolarPoint().getY_coord() + 50);
	 		g2d.setColor(Color.BLACK);
	 		writeDistanceBetweenPillars(g2d);
	 		g2d.drawString("1m", (float) (displayerCenterX + 300), (float) (displayerCenterY + 290));
	 		g2d.drawString("M= 1:200", (float) (displayerCenterX + 300), (float) (displayerCenterY + 260));
	 		
	 		AzimuthAndDistance base14 = new AzimuthAndDistance(pillarBasePoints.get(1), pillarBasePoints.get(4));
	 		AzimuthAndDistance tr14 = new AzimuthAndDistance(transformedPillarBasePoints.get(1), transformedPillarBasePoints.get(4));
	 		AzimuthAndDistance tr34 = new AzimuthAndDistance(transformedPillarBasePoints.get(3), transformedPillarBasePoints.get(4));
	 		DecimalFormat df = new DecimalFormat("###.###");
	        g2d.rotate(base14.calcAzimuth() + Math.PI / 2,
	        		(float) transformedPillarBasePoints.get(0).getX_coord(),
	        		(float) transformedPillarBasePoints.get(0).getY_coord());
	 		g2d.drawString(df.format(base14.calcDistance()) + "m",
	 				(float) (transformedPillarBasePoints.get(0).getX_coord() + 10),
	 				(float) (transformedPillarBasePoints.get(0).getY_coord() - (tr34.calcDistance() + 10) / 2));
	 		AzimuthAndDistance base68 = new AzimuthAndDistance(pillarBasePoints.get(6), pillarBasePoints.get(8));
	 		g2d.drawString(df.format((base68.calcDistance() - base14.calcDistance()) / 2) + "m",
	 				(float) (transformedPillarBasePoints.get(0).getX_coord() - (tr14.calcDistance() + 100) / 2),
	 				(float) (transformedPillarBasePoints.get(0).getY_coord() - 10));
	 		
	 		AzimuthAndDistance base34 = new AzimuthAndDistance(pillarBasePoints.get(3), pillarBasePoints.get(4));
	 		g2d.rotate(- Math.PI / 2,
	        		(float) transformedPillarBasePoints.get(0).getX_coord(),
	        		(float) transformedPillarBasePoints.get(0).getY_coord());
	 		g2d.drawString(df.format(base34.calcDistance()) + "m",
	 				(float) (transformedPillarBasePoints.get(0).getX_coord() + 10),
	 				(float) (transformedPillarBasePoints.get(0).getY_coord() + (tr14.calcDistance() - 10) / 2));
	 		AzimuthAndDistance base57 = new AzimuthAndDistance(pillarBasePoints.get(5), pillarBasePoints.get(7));
	 		g2d.drawString(df.format((base57.calcDistance() - base34.calcDistance()) / 2) + "m",
	 				(float) (transformedPillarBasePoints.get(0).getX_coord() - (tr34.calcDistance() + 100) / 2),
	 				(float) (transformedPillarBasePoints.get(0).getY_coord() - 10));
	 	}
	 	
	 	@Override
	    public void paint(Graphics g) {
	        super.paint(g);
	        drawBase(g);
	        if( controlledCoords.isEmpty() ) {
				writeCoords(g);	
			}
			else {
				writeCoordDifference(g);
			}
	        writeText(g);
	    }
	
	
	
	
}
