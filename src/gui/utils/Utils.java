package gui.utils;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	
	public static Double tryParseToDouble(String str) {
		
		try {
			return Double.parseDouble(str);
		} catch (NumberFormatException e) {
			return null;
		}
	}
	
	public static String decimalPrinting(Double var) {
		if (var % 1 == 0) {
			return String.format("%.0f", var);
		}
		else {
			return String.format("%.2f", var);
		}
	}
	
	public static boolean isNumeric(String str) { 
		try {  
		  Double.parseDouble(str);
		  return true;
		} catch(NumberFormatException e){  
		  return false;  
		}  
	}
	
	public static String removeChars(String str, int numberOfCharactersToRemove) {
	    if(str != null && !str.trim().isEmpty()) {
	        return str.substring(0, str.length() - numberOfCharactersToRemove);
	    }
	    return "";
	}
	
	public static String removeLastChar(String str) {
	    return removeChars(str, 1);
	}
	
}
