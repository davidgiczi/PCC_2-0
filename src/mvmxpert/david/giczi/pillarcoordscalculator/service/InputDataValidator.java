package mvmxpert.david.giczi.pillarcoordscalculator.service;


public class InputDataValidator {


	public static boolean isValidProjectName(String projectName) {
	return !projectName.isBlank() && projectName.length() > 2 && !Character.isDigit(projectName.charAt(0));		
	}	
	
	public static boolean isValidPrePostFixValue(String value) {
		return !value.isBlank() && !value.isEmpty();
		}	
	
	public static double isValidInputDoubleValue(String inputNumber) throws NumberFormatException {
		
	Double number = Double.parseDouble(inputNumber);
	
	return number;
	}
	
	public static Double isValidInputPositiveDoubleValue(String inputNumber) throws NumberFormatException {
		Double	positiveDouble = Double.parseDouble(inputNumber);
		
			if( 0 > positiveDouble ) 
				throw new NumberFormatException();
			
		return positiveDouble;
	}
	
	public static Integer isValidInputPositiveIntegerValue(String inputNumber) throws NumberFormatException {
		Integer	positiveInteger = Integer.parseInt(inputNumber);
		
			if( 0 > positiveInteger ) 
				throw new NumberFormatException();
			
		return positiveInteger;
	}
	
	public static int isValidAngleValue(String angleValue) throws NumberFormatException {
		
		int angle = Integer.parseInt(angleValue);
		
		return angle;
	}
	
	public static boolean isValidID(String id) {
		return !id.isBlank() && !id.isEmpty();
	}
}
