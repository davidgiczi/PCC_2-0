package mvmxpert.david.giczi.pillarcoordscalculator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;

public class AvePoint {

	public String pointId;
	public List<String> measData;
	
	
	public AvePoint(String pointId) {
		this.pointId = pointId;
		this.measData = new ArrayList<>();
	}
	
	public String getAveragePointData() throws DataFormatException {
		double Y = 0.0;
        double X = 0.0;
        double Z = 0.0;
        for (String measurment : measData) {
			String[] parts = measurment.split(",");
        	if( parts.length == 1 ) {
        		parts = measurment.split(";");
        	}
        	if( parts.length == 1 ) {
        		throw new DataFormatException();
        	}
        	 Y += Double.parseDouble(parts[1]);
             X += Double.parseDouble(parts[2]);
             Z += Double.parseDouble(parts[3]);
		}
        
        return pointId + " " + String.format("%.3f", Y / measData.size()).replace(",", ".") +
        				 " " + String.format("%.3f", X / measData.size()).replace(",", ".") +
        				 " " + String.format("%.3f", Z / measData.size()).replace(",", ".");
	}

	@Override
	public String toString() {
		return measData.toString();
	}
	
	
}
