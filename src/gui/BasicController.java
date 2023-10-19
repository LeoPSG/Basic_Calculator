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
		if (isNumerical(resolution.size() - 1) || isParenthese(resolution.size() - 1)) {
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
	
	public void powMultiplicationWithSameBase(int indexOfPowSymbol) {
		if (resolution.get(indexOfPowSymbol - 1) == resolution.get(indexOfPowSymbol + 3) && resolution.get(indexOfPowSymbol + 2) == "×") {
			Double newPowValue = Utils.tryParseToDouble(resolution.get(indexOfPowSymbol + 1)) + Utils.tryParseToDouble(resolution.get(indexOfPowSymbol + 5));
			resolution.set(indexOfPowSymbol + 1, newPowValue.toString());
			resolution.remove(indexOfPowSymbol + 5);
			resolution.remove(indexOfPowSymbol + 4);
			resolution.remove(indexOfPowSymbol + 3);
			resolution.remove(indexOfPowSymbol + 2);
		}
	}
	public void powDivisionWithSameBase(int indexOfPowSymbol) {
		if (resolution.get(indexOfPowSymbol - 1) == resolution.get(indexOfPowSymbol + 3) && resolution.get(indexOfPowSymbol + 2) == "\u00F7") {
			Double newPowValue = Utils.tryParseToDouble(resolution.get(indexOfPowSymbol + 1)) - Utils.tryParseToDouble(resolution.get(indexOfPowSymbol + 5));
			resolution.set(indexOfPowSymbol + 1, newPowValue.toString());
			resolution.remove(indexOfPowSymbol + 5);
			resolution.remove(indexOfPowSymbol + 4);
			resolution.remove(indexOfPowSymbol + 3);
			resolution.remove(indexOfPowSymbol + 2);
		}
	}
	public void powOnPow(int indexOfPowSymbol) {
		if (resolution.get(indexOfPowSymbol + 3) == "^") {
			Double newPowValue = Utils.tryParseToDouble(resolution.get(indexOfPowSymbol + 1)) * Utils.tryParseToDouble(resolution.get(indexOfPowSymbol + 4));
			resolution.set(indexOfPowSymbol + 1, newPowValue.toString());
			resolution.remove(indexOfPowSymbol + 4);
			resolution.remove(indexOfPowSymbol + 3);
			resolution.remove(indexOfPowSymbol + 2);
			resolution.remove(indexOfPowSymbol - 2);
		}
	}
	public void powOnMultiplication(int indexOfPowSymbol) {
		if (resolution.get(indexOfPowSymbol - 1) == ")" && resolution.get(indexOfPowSymbol - 3) == "×") {
			Double aVar = Utils.tryParseToDouble(resolution.get(indexOfPowSymbol - 4));
			Double bVar = Utils.tryParseToDouble(resolution.get(indexOfPowSymbol - 2));
			resolution.set(indexOfPowSymbol - 5, aVar.toString());
			resolution.set(indexOfPowSymbol - 4, "^");
			resolution.set(indexOfPowSymbol - 3, resolution.get(indexOfPowSymbol + 1));
			resolution.set(indexOfPowSymbol - 2, "*");
			resolution.set(indexOfPowSymbol - 1, bVar.toString());

		}
	}
	public void powOnDivision(int indexOfPowSymbol) {
		if (resolution.get(indexOfPowSymbol - 1) == ")" && resolution.get(indexOfPowSymbol - 3) == "\u00F7") {
			Double aVar = Utils.tryParseToDouble(resolution.get(indexOfPowSymbol - 4));
			Double bVar = Utils.tryParseToDouble(resolution.get(indexOfPowSymbol - 2));
			resolution.set(indexOfPowSymbol - 5, aVar.toString());
			resolution.set(indexOfPowSymbol - 4, "^");
			resolution.set(indexOfPowSymbol - 3, resolution.get(indexOfPowSymbol + 1));
			resolution.set(indexOfPowSymbol - 2, "\u00F7");
			resolution.set(indexOfPowSymbol - 1, bVar.toString());
		}
	}
	
	public void powerCalculation(int indexOfPowSymbol) {
		if (resolution.size() >= 7 && indexOfPowSymbol < (double) resolution.size() / 2) {
			powMultiplicationWithSameBase(indexOfPowSymbol);
			powDivisionWithSameBase(indexOfPowSymbol);
			powOnPow(indexOfPowSymbol);
		}
		else if (resolution.size() >= 7 && indexOfPowSymbol > (double) resolution.size() / 2) {
			powOnMultiplication(indexOfPowSymbol);
			powOnDivision(indexOfPowSymbol);
		}
	}
	
	public boolean isTheMathSymbol(String mathSymbolToBeChecked, int mathSymbolIndex) {
		if (resolution.get(mathSymbolIndex) == mathSymbolToBeChecked) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isThereAMinusSymbol(int mathSymbolIndex) {
		if (mathSymbolIndex >= 2) {
			if (resolution.get(mathSymbolIndex - 2) == "-" && resolution.get(mathSymbolIndex + 1) == "-") {
				return true;
			} else if (resolution.get(mathSymbolIndex - 2) == "-") {
				return true;
			} else if (resolution.get(mathSymbolIndex + 1) == "-") {
				return true;
			} else {
				return false;
			}
		} else {
			if (resolution.get(mathSymbolIndex + 1) == "-") {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public int minusSymbolFinder(int mathSymbolIndex) {
		int returnValue = 0;
		
		if (mathSymbolIndex >= 2) {
			if (resolution.get(mathSymbolIndex - 2) == "-" && resolution.get(mathSymbolIndex + 1) == "-") {
				returnValue = 1;
			} else if (resolution.get(mathSymbolIndex - 2) == "-") {
				returnValue = 2;
			} else if (resolution.get(mathSymbolIndex + 1) == "-") {
				returnValue = 3;
			} else {
				returnValue = 0;
			}
		} else {
			if (resolution.get(mathSymbolIndex + 1) == "-") {
				returnValue = 3;
			} else {
				returnValue = 0;
			}
		}
		
		return returnValue;
	}
	
	

	public void multiplyAndDivideCalculationWithMinusSymbol(int mathSymbolIndex, int minusSymbol) {
		
		double var1 = Utils.tryParseToDouble(resolution.get(mathSymbolIndex - 1));
		double var2;
		if (resolution.get(mathSymbolIndex + 1) == "-") {
			var2 = Utils.tryParseToDouble(resolution.get(mathSymbolIndex + 2));
		} else {
			var2 = Utils.tryParseToDouble(resolution.get(mathSymbolIndex + 1));
		}
		
		Double operation;
		
		switch (minusSymbol) {
		
		// both numbers are negative
		case 1:
			if (isTheMathSymbol("×", mathSymbolIndex)) {
				operation = var1 * var2;
			} else {
				operation = var1 / var2;
			}
			resolution.set(mathSymbolIndex - 1, Utils.decimalPrinting(operation));
			resolution.remove(mathSymbolIndex + 2);
			resolution.remove(mathSymbolIndex + 1);
			resolution.remove(mathSymbolIndex);
		break;
		
		// left number is negative
		case 2:
			if (isTheMathSymbol("×", mathSymbolIndex)) {
				operation = var1 * var2;
			} else {
				operation = var1 / var2;
			}
			resolution.set(mathSymbolIndex - 1, Utils.decimalPrinting(operation));
			resolution.remove(mathSymbolIndex + 1);
			resolution.remove(mathSymbolIndex);
		break;
		
		// right number is negative
		case 3:
			if (isTheMathSymbol("×", mathSymbolIndex)) {
				operation = var1 * var2;
			} else {
				operation = var1 / var2;
			}
			resolution.set(mathSymbolIndex - 1, "-");
			resolution.set(mathSymbolIndex, Utils.decimalPrinting(operation));
			resolution.remove(mathSymbolIndex + 2);
			resolution.remove(mathSymbolIndex + 1);
		break;
				
		default:break;
		}
	}
	
	public void powerMath(int indexOfSymbol) {
		powerCalculation(indexOfSymbol);
		Double finalResult = Math.pow(Utils.tryParseToDouble(resolution.get(indexOfSymbol - 1)), Utils.tryParseToDouble(resolution.get(indexOfSymbol + 1)));
		resolution.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
		resolution.remove(indexOfSymbol + 1);
		resolution.remove(indexOfSymbol);
	}
	
	public void rootMath(int indexOfSymbol) {
		Double finalResult = Math.sqrt(Utils.tryParseToDouble(resolution.get(indexOfSymbol + 1)));
		resolution.set(indexOfSymbol, Utils.decimalPrinting(finalResult));
		resolution.remove(indexOfSymbol + 1);
	}
	
	public void multiplyMath(int indexOfSymbol) {
		if (isThereAMinusSymbol(indexOfSymbol)) {
			int minusSymbol = minusSymbolFinder(indexOfSymbol);
			multiplyAndDivideCalculationWithMinusSymbol(indexOfSymbol, minusSymbol);
		} else {
			Double finalResult = Utils.tryParseToDouble(resolution.get(indexOfSymbol - 1)) * Utils.tryParseToDouble(resolution.get(indexOfSymbol + 1));
			resolution.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
			resolution.remove(indexOfSymbol + 1);
			resolution.remove(indexOfSymbol);
		}
	}
	
	public void divideMath(int indexOfSymbol) {
		if (isThereAMinusSymbol(indexOfSymbol)) {
			int minusSymbol = minusSymbolFinder(indexOfSymbol);
			multiplyAndDivideCalculationWithMinusSymbol(indexOfSymbol, minusSymbol);
		} else {
			Double finalResult = Utils.tryParseToDouble(resolution.get(indexOfSymbol - 1)) / Utils.tryParseToDouble(resolution.get(indexOfSymbol + 1));
			resolution.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
			resolution.remove(indexOfSymbol + 1);
			resolution.remove(indexOfSymbol);
		}
	}
	
	public void plusMath(int indexOfSymbol) {
		Double finalResult = Utils.tryParseToDouble(resolution.get(indexOfSymbol)) + Utils.tryParseToDouble(resolution.get(indexOfSymbol));
		resolution.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
		resolution.remove(indexOfSymbol + 1);
		resolution.remove(indexOfSymbol);
	}
	
	public void minusMath(int indexOfSymbol) {
		Double finalResult = Utils.tryParseToDouble(resolution.get(indexOfSymbol)) - Utils.tryParseToDouble(resolution.get(indexOfSymbol));
		resolution.set(indexOfSymbol - 1, Utils.decimalPrinting(finalResult));
		resolution.remove(indexOfSymbol + 1);
		resolution.remove(indexOfSymbol);
	}
	
	public boolean isThereAMathSymbol() {
		if (resolution.contains("^") || resolution.contains("\u221A") || resolution.contains("×") || resolution.contains("\u00F7") || resolution.contains("+") || resolution.contains("-")) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isNumerical(int index) {
		if (Utils.isNumeric(resolution.get(index))) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isMinus(int index) {
		if (resolution.get(index) == "-") {
			return true;
		} else {
			return false;
		}
	}
	
    public boolean isRoot(int index) {
    	if (resolution.get(index) == "\u221A") {
			return true;
		} else {
			return false;
		}
	}
    
    public boolean isParenthese(int index) {
    	if (resolution.get(index) == "(") {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isFirstIndexUsable() {
		if (isNumerical(0) || isMinus(0) || isRoot(0) || isParenthese(0)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isThereThisSymbol(String symbolToBeSearched) {
		for (int i = 0; i < resolution.size(); i++) {
			if (resolution.get(i) == symbolToBeSearched && Utils.isNumeric(resolution.get(i + 1))) {
				return true;
			} else if (resolution.get(i) == symbolToBeSearched && resolution.get(i + 1) == "-") {
				return true;
			}
		}
		return false;
	}
	
	public int symbolIndexFinder(String symbolToBeSearched) {
		int symbolIndex = 0;
		for (int i = 0; i < resolution.size(); i++) {
			if (resolution.get(i) == symbolToBeSearched && Utils.isNumeric(resolution.get(i + 1))) {
				symbolIndex =+ i;
			} else if (resolution.get(i) == symbolToBeSearched && resolution.get(i + 1) == "-") {
				symbolIndex =+ i;
			}
		}
		return symbolIndex;
	}
	
	public boolean mathCanBeDone() {
		if (isThereAMathSymbol() && isFirstIndexUsable() && resolution.size() >= 2) {
			return true;
		} else {
			return false;
		}
	}
	
	@FXML
	public void onEqualAction() {
		
		if (mathCanBeDone()) {

			preResult.setText(equation);
			int indexOfSymbol = 0;
			
			//power
			if (isThereThisSymbol("^")) {
				indexOfSymbol =+ symbolIndexFinder("^");
				powerMath(indexOfSymbol);
				indexOfSymbol = 0;
			}
			//parentheses
			if (resolution.contains("(") && resolution.contains(")")) {
	
				int leftParentheseCount = 0;
				int rightParentheseCount = 0;
				
				int leftParentheseIndex = -1;
				int rightParentheseIndex = -1;
				
				// error where the parentheses is in the wrong order or isn't counted correctly
				//
				// example: 2x(2x'('2+2)')' , *the "marked" parentheses are being used together , result: 2x(8
				// example: (2+2)x(2+2) , *the second set of parentheses is getting the index of the first set , result: x2)
				
				for (int i = 0; i < resolution.size(); i++) {
					if (resolution.get(i) == "(" && leftParentheseCount < 2) {
						leftParentheseCount++;
						leftParentheseIndex = i;
					}
					else if (resolution.get(i) == ")") {
						rightParentheseCount++;
						rightParentheseIndex = i;
					}
					if (leftParentheseCount > 0 && leftParentheseCount == rightParentheseCount) {
						ArrayList<String> substitutionList = new ArrayList<>();
						for (String e : resolution) {
							substitutionList.add(e);
						}
						resolution.clear();
						for (int j = leftParentheseIndex + 1; j < rightParentheseIndex; j++) {
							resolution.add(substitutionList.get(j));
						}
						
						onEqualAction();
						
						for (int j = rightParentheseIndex; j > leftParentheseIndex; j--) {
							substitutionList.remove(j);
						}
						substitutionList.set(leftParentheseIndex, resolution.get(0));
						resolution = substitutionList;
					}
				}
			}
			//root
			if (isThereThisSymbol("\u221A")) {
				indexOfSymbol =+ symbolIndexFinder("\u221A");
				rootMath(indexOfSymbol);
				indexOfSymbol = 0;
			}
			//multiply
			if (isThereThisSymbol("×")) {
				indexOfSymbol =+ symbolIndexFinder("×");
				multiplyMath(indexOfSymbol);
				indexOfSymbol = 0;
			}
			//divide
			if (isThereThisSymbol("\u00F7")) {
				indexOfSymbol =+ symbolIndexFinder("\u00F7");
				divideMath(indexOfSymbol);
				indexOfSymbol = 0;
			}
			//plus & minus
			if (isThereThisSymbol("+")) {
				indexOfSymbol =+ symbolIndexFinder("+");
				plusMath(indexOfSymbol);
				indexOfSymbol = 0;
			}
			
            if (isThereThisSymbol("-")) {
            	indexOfSymbol =+ symbolIndexFinder("-");
				minusMath(indexOfSymbol);
				indexOfSymbol = 0;
			}
            
			/*
					if (resolution.get(i) == "+" && resolution.get(i + 1) == "-") {
						resolution.set(i, "-");
						resolution.remove(i + 1);
					} else if (resolution.get(i) == "-" && resolution.get(i + 1) == "-") {
						resolution.set(i, "+");
						resolution.remove(i + 1);
					}
					if (resolution.get(i) == "+" && Utils.isNumeric(resolution.get(i + 1))) {
						Double plus = Utils.tryParseToDouble(resolution.get(i - 1))
								+ Utils.tryParseToDouble(resolution.get(i + 1));
						resolution.set(i - 1, Utils.decimalPrinting(plus));
						resolution.remove(i + 1);
						resolution.remove(i);
					} else if (i > 0 && resolution.get(i) == "-" && Utils.isNumeric(resolution.get(i + 1))) {
						Double minus = Utils.tryParseToDouble(resolution.get(i - 1))
								- Utils.tryParseToDouble(resolution.get(i + 1));
						resolution.set(i - 1, Utils.decimalPrinting(minus));
						resolution.remove(i + 1);
						resolution.remove(i);
					}
			*/
            
            String substituition = "";
            for (String e : resolution) {
            	substituition += e;
            }
            equation = substituition;
            result.setText(equation);
		}
	}
}