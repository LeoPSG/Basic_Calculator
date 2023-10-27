package gui;

import java.util.ArrayList;

import gui.utils.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class BasicController {

	@FXML
	private Button bt1;

	@FXML
	private Button bt2;

	@FXML
	private Button bt3;

	@FXML
	private Button bt4;

	@FXML
	private Button bt5;

	@FXML
	private Button bt6;

	@FXML
	private Button bt7;

	@FXML
	private Button bt8;

	@FXML
	private Button bt9;

	@FXML
	private Button bt0;

	@FXML
	private Button decimal;

	@FXML
	private Button minus;

	@FXML
	private Button plus;

	@FXML
	private Button divide;

	@FXML
	private Button multiply;

	@FXML
	private Button root;

	@FXML
	private Button power;

	@FXML
	private Button leftParenthese;

	@FXML
	private Button rightParenthese;

	@FXML
	private Button percentage;

	@FXML
	private Button delete;

	@FXML
	private Button equal;

	@FXML
	private Label preResult;

	@FXML
	private Label result;

	String equation = "";
	ArrayList<String> resolution = new ArrayList<>();

	@FXML
	public void onBtAction(ActionEvent event) {

		String buttonValue = ((Button) event.getSource()).getText();
		int lastIndex = resolution.size() - 1;

		if (equation.trim().isEmpty()) {
			// empty
			resolution.add(buttonValue);
		} else if (equation.substring(equation.length() - 1).equals(",")) {
			// last char is ","
			String number = resolution.get(lastIndex) + buttonValue;
			resolution.set(lastIndex, number);
		} else if (Utils.isNumeric(equation.substring(equation.length() - 1))) {
			// last char is a number
			String number = resolution.get(lastIndex) + buttonValue;
			resolution.set(lastIndex, number);
		} else {
			// last char is not a number and is different from ","
			resolution.add(buttonValue);
		}

		equation += buttonValue;
		result.setText(equation);
	}

	@FXML
	public void onBt0Action() {
		int lastIndex = resolution.size() - 1;
		if (!resolution.isEmpty()) {
			if (Utils.isNumeric(equation.substring(equation.length() - 1)) || resolution.get(lastIndex).contains(",")) {
				String number = resolution.get(lastIndex) + "0";
				resolution.set(lastIndex, number);
				equation += "0";
				result.setText(equation);
			}
		}
	}

	@FXML
	public void onDecimalAction() {
		int lastIndex = resolution.size() - 1;
		if (Utils.isNumeric(equation.substring(equation.length() - 1)) && !resolution.get(lastIndex).contains(",")) {
			String number = resolution.get(lastIndex) + ",";
			resolution.set(lastIndex, number);
			equation += ",";
			result.setText(equation);
		}
	}
	
	public void addMathSymbol(String symbol) {
		if (isNumerical(resolution, resolution.size() - 1) || isLeftParentheses(resolution, resolution.size() - 1)) {
			equation += symbol;
			resolution.add(symbol);
		} else {
			Utils.removeLastChar(equation);
			equation += symbol;
			resolution.set(resolution.size() - 1, symbol);
		}
		result.setText(equation);
	}

	@FXML
	public void onMinusAction() {
		addMathSymbol("-");
		result.setText(equation);
	}

	@FXML
	public void onPlusAction() {
		addMathSymbol("+");
		result.setText(equation);
	}

	@FXML
	public void onDivideAction() {
		addMathSymbol("\u00F7");
		result.setText(equation);
	}

	@FXML
	public void onMultiplyAction() {
		addMathSymbol("×");
		result.setText(equation);
	}

	@FXML
	public void onRootAction() {
		equation += "\u221A";
		result.setText(equation);
		resolution.add("\u221A");
	}

	@FXML
	public void onPowerAction() {
		addMathSymbol("^");
		result.setText(equation);
	}
	
	@FXML
	public void onLeftParentheseAction() {
		equation += "(";
		result.setText(equation);
		resolution.add("(");
	}

	@FXML
	public void onRightParentheseAction() {
		int leftCounter = 0, rightCounter = 0;
		for (String e : resolution) {
			if (e == "(") {
				leftCounter += 1;
			} else if (e == ")") {
				rightCounter += 1;
			}
		}

		if (leftCounter > rightCounter) {
			equation += ")";
			result.setText(equation);
			resolution.add(")");
		}
	}
	
	@FXML
	public void onPercentageAction() {
		int lastIndex = resolution.size() - 1;
		if (Utils.isNumeric(resolution.get(lastIndex)) && !resolution.get(lastIndex).contains(",")) {
			Double percentageNumber = Utils.tryParseToDouble(resolution.get(lastIndex)) / 100;
			String percentage = Utils.decimalPrinting(percentageNumber);
			resolution.set(lastIndex, percentage);
			percentage = "";
			for (String e : resolution) {
				percentage += e;
			}
			equation = percentage;
			result.setText(equation);
		}
	}

	@FXML
	public void onDeleteAction() {
		int lastIndex = resolution.size() - 1;
		if (!equation.trim().isEmpty()) {
			String deletedResolution = Utils.removeLastChar(resolution.get(lastIndex));
			if (deletedResolution.trim().isEmpty()) {
				resolution.remove(lastIndex);
			} else {
				resolution.set(lastIndex, Utils.removeLastChar(resolution.get(lastIndex)));
			}
			String deletedEquation = Utils.removeLastChar(equation);
			equation = deletedEquation;
			result.setText(equation);
		}
	}
	
	public void powMultiplicationWithSameBase(ArrayList<String> list, int indexOfPowSymbol) {
		if (list.get(indexOfPowSymbol - 1) == list.get(indexOfPowSymbol + 3) && list.get(indexOfPowSymbol + 2) == "×") {
			Double newPowValue = Utils.tryParseToDouble(list.get(indexOfPowSymbol + 1)) + Utils.tryParseToDouble(list.get(indexOfPowSymbol + 5));
			list.set(indexOfPowSymbol + 1, newPowValue.toString());
			list.remove(indexOfPowSymbol + 5);
			list.remove(indexOfPowSymbol + 4);
			list.remove(indexOfPowSymbol + 3);
			list.remove(indexOfPowSymbol + 2);
		}
	}
	public void powDivisionWithSameBase(ArrayList<String> list, int indexOfPowSymbol) {
		if (list.get(indexOfPowSymbol - 1) == list.get(indexOfPowSymbol + 3) && list.get(indexOfPowSymbol + 2) == "\u00F7") {
			Double newPowValue = Utils.tryParseToDouble(list.get(indexOfPowSymbol + 1)) - Utils.tryParseToDouble(list.get(indexOfPowSymbol + 5));
			list.set(indexOfPowSymbol + 1, newPowValue.toString());
			list.remove(indexOfPowSymbol + 5);
			list.remove(indexOfPowSymbol + 4);
			list.remove(indexOfPowSymbol + 3);
			list.remove(indexOfPowSymbol + 2);
		}
	}
	public void powOnPow(ArrayList<String> list, int indexOfPowSymbol) {
		if (list.get(indexOfPowSymbol + 3) == "^") {
			Double newPowValue = Utils.tryParseToDouble(list.get(indexOfPowSymbol + 1)) * Utils.tryParseToDouble(list.get(indexOfPowSymbol + 4));
			list.set(indexOfPowSymbol + 1, newPowValue.toString());
			list.remove(indexOfPowSymbol + 4);
			list.remove(indexOfPowSymbol + 3);
			list.remove(indexOfPowSymbol + 2);
			list.remove(indexOfPowSymbol - 2);
		}
	}
	public void powOnMultiplication(ArrayList<String> list, int indexOfPowSymbol) {
		if (list.get(indexOfPowSymbol - 1) == ")" && list.get(indexOfPowSymbol - 3) == "×") {
			Double aVar = Utils.tryParseToDouble(list.get(indexOfPowSymbol - 4));
			Double bVar = Utils.tryParseToDouble(list.get(indexOfPowSymbol - 2));
			list.set(indexOfPowSymbol - 5, aVar.toString());
			list.set(indexOfPowSymbol - 4, "^");
			list.set(indexOfPowSymbol - 3, list.get(indexOfPowSymbol + 1));
			list.set(indexOfPowSymbol - 2, "*");
			list.set(indexOfPowSymbol - 1, bVar.toString());

		}
	}
	public void powOnDivision(ArrayList<String> list, int indexOfPowSymbol) {
		if (list.get(indexOfPowSymbol - 1) == ")" && list.get(indexOfPowSymbol - 3) == "\u00F7") {
			Double aVar = Utils.tryParseToDouble(list.get(indexOfPowSymbol - 4));
			Double bVar = Utils.tryParseToDouble(list.get(indexOfPowSymbol - 2));
			list.set(indexOfPowSymbol - 5, aVar.toString());
			list.set(indexOfPowSymbol - 4, "^");
			list.set(indexOfPowSymbol - 3, list.get(indexOfPowSymbol + 1));
			list.set(indexOfPowSymbol - 2, "\u00F7");
			list.set(indexOfPowSymbol - 1, bVar.toString());
		}
	}
	
	public void powerCalculation(ArrayList<String> list, int indexOfPowSymbol) {
		if (list.size() >= 7 && indexOfPowSymbol < (double) list.size() / 2) {
			powMultiplicationWithSameBase(list, indexOfPowSymbol);
			powDivisionWithSameBase(list, indexOfPowSymbol);
			powOnPow(list, indexOfPowSymbol);
		}
		else if (list.size() >= 7 && indexOfPowSymbol > (double) list.size() / 2) {
			powOnMultiplication(list, indexOfPowSymbol);
			powOnDivision(list, indexOfPowSymbol);
		}
	}
	
	public boolean isTheMathSymbol(ArrayList<String> list, String mathSymbolToBeChecked, int mathSymbolIndex) {
		if (list.get(mathSymbolIndex) == mathSymbolToBeChecked) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isThereAMinusSymbol(ArrayList<String> list, int mathSymbolIndex) {
		if (mathSymbolIndex >= 2) {
			if (list.get(mathSymbolIndex - 2) == "-" && list.get(mathSymbolIndex + 1) == "-") {
				return true;
			} else if (list.get(mathSymbolIndex - 2) == "-") {
				return true;
			} else if (list.get(mathSymbolIndex + 1) == "-") {
				return true;
			} else {
				return false;
			}
		} else {
			if (list.get(mathSymbolIndex + 1) == "-") {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public int minusSymbolFinder(ArrayList<String> list, int mathSymbolIndex) {
		int returnValue = 0;
		
		if (mathSymbolIndex >= 2) {
			if (list.get(mathSymbolIndex - 2) == "-" && list.get(mathSymbolIndex + 1) == "-") {
				returnValue = 1;
			} else if (list.get(mathSymbolIndex - 2) == "-") {
				returnValue = 2;
			} else if (list.get(mathSymbolIndex + 1) == "-") {
				returnValue = 3;
			} else {
				returnValue = 0;
			}
		} else {
			if (list.get(mathSymbolIndex + 1) == "-") {
				returnValue = 3;
			} else {
				returnValue = 0;
			}
		}
		
		return returnValue;
	}
	
	

	public void multiplyAndDivideCalculationWithMinusSymbol(ArrayList<String> list, int mathSymbolIndex, int minusSymbol) {
		
		double var1 = Utils.tryParseToDouble(list.get(mathSymbolIndex - 1));
		double var2;
		if (list.get(mathSymbolIndex + 1) == "-") {
			var2 = Utils.tryParseToDouble(list.get(mathSymbolIndex + 2));
		} else {
			var2 = Utils.tryParseToDouble(list.get(mathSymbolIndex + 1));
		}
		
		Double operation;
		
		switch (minusSymbol) {
		
		// both numbers are negative
		case 1:
			if (isTheMathSymbol(list, "×", mathSymbolIndex)) {
				operation = var1 * var2;
			} else {
				operation = var1 / var2;
			}
			list.set(mathSymbolIndex - 1, Utils.decimalPrinting(operation));
			list.remove(mathSymbolIndex + 2);
			list.remove(mathSymbolIndex + 1);
			list.remove(mathSymbolIndex);
		break;
		
		// left number is negative
		case 2:
			if (isTheMathSymbol(list, "×", mathSymbolIndex)) {
				operation = var1 * var2;
			} else {
				operation = var1 / var2;
			}
			list.set(mathSymbolIndex - 1, Utils.decimalPrinting(operation));
			list.remove(mathSymbolIndex + 1);
			list.remove(mathSymbolIndex);
		break;
		
		// right number is negative
		case 3:
			if (isTheMathSymbol(list, "×", mathSymbolIndex)) {
				operation = var1 * var2;
			} else {
				operation = var1 / var2;
			}
			list.set(mathSymbolIndex - 1, "-");
			list.set(mathSymbolIndex, Utils.decimalPrinting(operation));
			list.remove(mathSymbolIndex + 2);
			list.remove(mathSymbolIndex + 1);
		break;
				
		default:break;
		}
	}
	
	public void powerMath(ArrayList<String> list, int indexOfSymbol) {
		powerCalculation(list, indexOfSymbol);
		Double finalResult = Math.pow(Utils.tryParseToDouble(list.get(indexOfSymbol - 1)), Utils.tryParseToDouble(list.get(indexOfSymbol + 1)));
		list.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
		list.remove(indexOfSymbol + 1);
		list.remove(indexOfSymbol);
	}
	
	public void rootMath(ArrayList<String> list, int indexOfSymbol) {
		Double finalResult = Math.sqrt(Utils.tryParseToDouble(list.get(indexOfSymbol + 1)));
		list.set(indexOfSymbol, Utils.decimalPrinting(finalResult));
		list.remove(indexOfSymbol + 1);
	}
	
	public void multiplyMath(ArrayList<String> list, int indexOfSymbol) {
		if (isThereAMinusSymbol(list, indexOfSymbol)) {
			int minusSymbol = minusSymbolFinder(list, indexOfSymbol);
			multiplyAndDivideCalculationWithMinusSymbol(list, indexOfSymbol, minusSymbol);
		} else {
			Double finalResult = Utils.tryParseToDouble(list.get(indexOfSymbol - 1)) * Utils.tryParseToDouble(list.get(indexOfSymbol + 1));
			list.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
			list.remove(indexOfSymbol + 1);
			list.remove(indexOfSymbol);
		}
	}
	
	public void divideMath(ArrayList<String> list, int indexOfSymbol) {
		if (isThereAMinusSymbol(list, indexOfSymbol)) {
			int minusSymbol = minusSymbolFinder(list, indexOfSymbol);
			multiplyAndDivideCalculationWithMinusSymbol(list, indexOfSymbol, minusSymbol);
		} else {
			Double finalResult = Utils.tryParseToDouble(list.get(indexOfSymbol - 1)) / Utils.tryParseToDouble(list.get(indexOfSymbol + 1));
			list.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
			list.remove(indexOfSymbol + 1);
			list.remove(indexOfSymbol);
		}
	}
	
	public void plusMath(ArrayList<String> list, int indexOfSymbol) {
		Double finalResult = Utils.tryParseToDouble(list.get(indexOfSymbol - 1)) + Utils.tryParseToDouble(list.get(indexOfSymbol + 1));
		list.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
		list.remove(indexOfSymbol + 1);
		list.remove(indexOfSymbol);
	}
	
	public void minusMath(ArrayList<String> list, int indexOfSymbol) {
		Double finalResult = Utils.tryParseToDouble(list.get(indexOfSymbol - 1)) - Utils.tryParseToDouble(list.get(indexOfSymbol + 1));
		list.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
		list.remove(indexOfSymbol + 1);
		list.remove(indexOfSymbol);
	}
	
	public boolean isThereAMathSymbol(ArrayList<String> list) {
		if (list.contains("^") || list.contains("\u221A") || list.contains("×") || list.contains("\u00F7") || list.contains("+") || list.contains("-")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isNumerical(ArrayList<String> list, int index) {
		if (Utils.isNumeric(list.get(index))) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isMinus(ArrayList<String> list, int index) {
		if (list.get(index) == "-") {
			return true;
		} else {
			return false;
		}
	}
	
    public boolean isRoot(ArrayList<String> list, int index) {
    	if (list.get(index) == "\u221A") {
			return true;
		} else {
			return false;
		}
	}
    
    public boolean isLeftParentheses(ArrayList<String> list, int index) {
    	if (list.get(index) == "(") {
			return true;
		} else {
			return false;
		}
	}
    
    public boolean isRightParentheses(ArrayList<String> list, int index) {
    	if (list.get(index) == ")") {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFirstIndexUsable(ArrayList<String> list) {
		if (isNumerical(list, 0) || isMinus(list, 0) || isRoot(list, 0) || isLeftParentheses(list, 0)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isThereThisSymbol(ArrayList<String> list, String symbolToBeSearched) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == symbolToBeSearched && Utils.isNumeric(list.get(i + 1))) {
				return true;
			} else if (list.get(i) == symbolToBeSearched && list.get(i + 1) == "-") {
				return true;
			}
		}
		return false;
	}
	
	public int symbolIndexFinder(ArrayList<String> list, String symbolToBeSearched) {
		int symbolIndex = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == symbolToBeSearched && Utils.isNumeric(list.get(i + 1))) {
				symbolIndex =+ i;
			} else if (list.get(i) == symbolToBeSearched && list.get(i + 1) == "-") {
				symbolIndex =+ i;
			}
		}
		return symbolIndex;
	}
	
	public boolean mathCanBeDone(ArrayList<String> list) {
		if (isThereAMathSymbol(list) && isFirstIndexUsable(list) && list.size() >= 2) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isThereLeftParentheses(ArrayList<String> list, int startingIndex) {
		for (int i = startingIndex; i < list.size(); i++) {
			if (list.get(i) == "(") {
				return true;
			}
		}
		return false;
	}
	
	public int getIndexOfCorrespondingRightParentheses(ArrayList<String> list, int indexOfLeftParentheses) {
		int indexOfCorrespondingRightParentheses = 0;
		
		int leftParenthesesCounter = 1;
		int rightParenthesesCounter = 0;
		for (int i = indexOfLeftParentheses + 1; i < list.size(); i++) {
			if (isLeftParentheses(list, i)) {
				leftParenthesesCounter++;
			} else if (isRightParentheses(list, i)) {
				rightParenthesesCounter++;
			}
			if (leftParenthesesCounter == rightParenthesesCounter) {
				indexOfCorrespondingRightParentheses = i;
			}
		}
		return indexOfCorrespondingRightParentheses;
	}
	
	public ArrayList<String> getContentInsideParentheses(ArrayList<String> list, int leftParenthesesIndex, int rightParenthesesIndex) {
		ArrayList<String> contentInsideParentheses = new ArrayList<>();
		for (int i = leftParenthesesIndex + 1; i < rightParenthesesIndex; i++) {
			contentInsideParentheses.add(list.get(i));
		}
		return contentInsideParentheses;
	}
	
	public String getCalculatedContentInsideParentheses(ArrayList<String> contentInsideParentheses) {
		mathCalculationCaller(contentInsideParentheses);
		return contentInsideParentheses.get(0); // bug here WHEN (2x2)x2 and 2x((2x2)x(2x2)) but NOT when 2x(2x2) , 
	}
	
	public void parenthesesSubstitution(ArrayList<String> list, int leftParentheses, int rightParentheses, String newContent) {
		Utils.removeFromListInRange(list, leftParentheses + 1, rightParentheses);
		list.set(leftParentheses, newContent);
	}
	
	public void parenthesesMath(ArrayList<String> list) {
		int indexOfFirstLeftParentheses = list.indexOf("(");
		int indexOfCorrespondingRightParentheses = getIndexOfCorrespondingRightParentheses(list, indexOfFirstLeftParentheses);
		//ArrayList<String> contentInsideParentheses = new ArrayList<>();
		//contentInsideParentheses = getContentInsideParentheses(list, indexOfFirstLeftParentheses, indexOfCorrespondingRightParentheses);
		String newContent = getCalculatedContentInsideParentheses(getContentInsideParentheses(list, indexOfFirstLeftParentheses, indexOfCorrespondingRightParentheses));
		parenthesesSubstitution(list, indexOfFirstLeftParentheses, indexOfCorrespondingRightParentheses, newContent);
	}
	
	public void mathCalculationCaller(ArrayList<String> list) {
		if (mathCanBeDone(list)) {

			int indexOfSymbol = 0;
			
			//power
			if (isThereThisSymbol(list, "^")) {
				indexOfSymbol =+ symbolIndexFinder(list, "^");
				powerMath(list, indexOfSymbol);
				indexOfSymbol = 0;
			}
			//parentheses
			if (isThereLeftParentheses(list, 0)) {
				parenthesesMath(list);
			}
			//root
			if (isThereThisSymbol(list, "\u221A")) {
				indexOfSymbol =+ symbolIndexFinder(list, "\u221A");
				rootMath(list, indexOfSymbol);
				indexOfSymbol = 0;
			}
			//multiply
			if (isThereThisSymbol(list, "×")) {
				indexOfSymbol =+ symbolIndexFinder(list, "×");
				multiplyMath(list, indexOfSymbol);
				indexOfSymbol = 0;
			}
			//divide
			if (isThereThisSymbol(list, "\u00F7")) {
				indexOfSymbol =+ symbolIndexFinder(list, "\u00F7");
				divideMath(list, indexOfSymbol);
				indexOfSymbol = 0;
			}
			//plus
			if (isThereThisSymbol(list, "+")) {
				indexOfSymbol =+ symbolIndexFinder(list, "+");
				plusMath(list, indexOfSymbol);
				indexOfSymbol = 0;
			}
			//minus
            if (isThereThisSymbol(list, "-")) {
            	indexOfSymbol =+ symbolIndexFinder(list, "-");
				minusMath(list, indexOfSymbol);
				indexOfSymbol = 0;
			}
            
            mathCalculationCaller(list);
		}
	}
	
	@FXML
	public void onEqualAction() {
		preResult.setText(equation);
		
		mathCalculationCaller(resolution);
		
		String substituition = "";
        for (String e : resolution) {
        	substituition += e;
        }
        equation = substituition;
        result.setText(equation);
	}
}