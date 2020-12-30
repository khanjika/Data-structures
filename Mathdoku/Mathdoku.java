package Assignment;

import java.io.*;
import java.util.*;

import jdk.nashorn.internal.ir.SetSplitState;

import java.io.BufferedReader;

public class Mathdoku {

	// initializing variables
	int length = 0;
	int lengthCounter = 0;
	int stringCounter = 0;
	int stringOperationCounter = 0;
	int stringResultCounter = 0;
	int stringOperatorCounter = 0;
	int backtrackCounter = 0;

	// Defining TreeMap to store operation and result to be performed for a
	// character
	Map<String, OutputOperation> operations = new TreeMap<String, OutputOperation>();

	// Defining TreeMap to store grouping of cells to perform an operation
	Map<String, ArrayList<String>> operationsLocation = new HashMap<String, ArrayList<String>>();

	// Defining Set to store key of operations TreeMap
	Set<String> operationsRepresentation = new HashSet<String>();

	// Defining Set to store key of operationsLocation TreeMap
	Set<String> groupingOperation = new HashSet<String>();

	int[][] kenken;

	/*
	 * This method checks if the provided string contains only alphabets and no
	 * digits or special characters.
	 * 
	 * @param s This is the first parameter that is a string
	 * 
	 * @return boolean This returns true if the input is a string that contains only
	 * alphabets else it will return false.
	 */
	public static boolean isStringAlphabet(String s) {
		return ((s != null) && (!s.equals("")) && (s.chars().allMatch(Character::isLetter)));
	}

	/*
	 * This method checks if the provided string contains only digits and no
	 * alphabets or special characters.
	 * 
	 * @param s This is the first parameter that is a string
	 * 
	 * @return boolean This returns true if the input is a string that contains only
	 * digits else it will return false.
	 */
	public static boolean isStringDigit(String s) {
		return ((s != null) && (!s.equals("")) && (!s.equals("0")) && (s.chars().allMatch(Character::isDigit)));
	}

	/*
	 * This method checks if the provided string contains only mathematical
	 * operators and no alphabets, digits or, special characters.
	 * 
	 * @param s This is the first parameter that is a string
	 * 
	 * @return boolean This returns true if the input is a string that contains only
	 * mathematical operator else it will return false.
	 */
	public static boolean isStringOperator(String s) {
		return ((s != null) && (!s.equals("")) && (s.matches("[*/+-=]")));
	}

	/*
	 * This method reads from bufferReader and checks if the input provided is
	 * correct and sufficient to solve the mathdoku puzzle
	 * 
	 * @param stream This is the first parameter that is a character input stream
	 * which provides the grouping and operation to be performed on that grouping
	 * with the expected result
	 * 
	 * @return boolean This returns true if puzzle is loaded successfully and no
	 * error is detected and false in case of any error
	 */
	public boolean loadPuzzle(BufferedReader stream) throws IOException {
		int counter = 0;

		String line = new String();
		line = stream.readLine();

		// To check if line is empty or blank
		if (line == null || line == "") {
			return false;
		}
		
		// To find the length of first line read
		length = (line.replaceAll("\\s+", "")).length();
		
		// Initialized the array of size length
		kenken = new int[length][length];

		// to loop over all the lines
		do {
			if (line.equals(null)) {
				break;
			}

			counter = counter + 1;
			// to check if line has all alphabets and represent the grouping of cells
			if (counter <= length) {
				line = line.replaceAll("\\s+", "");
				// to check grouping of all cells are present
				if (line.length() != length) {
					lengthCounter = lengthCounter + 1;
					return false;
				}
				// to checks that only alphabets are present
				if (isStringAlphabet(line) != true) {
					stringCounter = stringCounter + 1;
					return false;
				}

				// to store the grouping in treeMap
				operationPosition(line, length, counter);
			}

			// To check that line represents operation and results
			if (counter > length) {
				line = line.replaceAll("\\s+", " ");
				String[] elements = line.split(" ");
				// to check if 3 elements are present i.e. alphabet, operator and result
				if (elements.length == 3) {
					String operationAlphabet = elements[0];
					// to check that first element is an alphabet
					if (isStringAlphabet(operationAlphabet) != true) {
						stringOperationCounter = stringOperationCounter + 1;
					}
					OutputOperation output = new OutputOperation();

					// to check if operator or result is empty in array
					if (!elements[1].equals(null) && !elements[2].equals(null)) {
						// to check if 2 element of array is a digit
						if (isStringDigit(elements[1]) != true) {
							stringResultCounter = stringResultCounter + 1;
						}
						output.result = elements[1];
						// to check if 3 element of array is a mathematical operator
						if (isStringOperator(elements[2]) != true) {
							stringOperatorCounter = stringOperatorCounter + 1;
						}
						output.operation = elements[2];
						// to insert alphabet as key and result and operator as value to the map
						operationInsert(operationAlphabet, output);
					}
				}
			}

			try {
				line = stream.readLine().trim();
			} catch (Exception e) {
				break;
			}
		} while (1 == 1);
		return true;
	}

	/*
	 * This method stores the alphabet that represents the grouping of cells as key
	 * and the operation and results of that grouping as the value in Map
	 * 
	 * @param key This is the first parameter that provides alphabet which represent
	 * the grouping of cells in 2d array
	 * 
	 * @param output This is the second parameter of type OutputOperation that
	 * contains the operation to be performed on the grouping and its result
	 * 
	 * @return boolean This returns true for successful insertion in the map else in
	 * case of duplication will return false
	 */
	public boolean operationInsert(String key, OutputOperation output) {
		boolean checkFlag = true;
		// to check if key is already present in the map
		if (operations.containsKey(key)) {
			checkFlag = false;
		} else {
			operationsRepresentation.add(key);
			operations.put(key, output);
		}
		return checkFlag;
	}

	/*
	 * This method stores the alphabet that represents the grouping of cells as key
	 * and cells that are a part of that grouping as the value in Map
	 * 
	 * @param value This is the first parameter that represents the data present in
	 * a particular line of stream
	 * 
	 * @param length This is the second parameter which indicates the length of the
	 * first line read from the stream
	 *
	 * @param count This the second parameter indicating the position of the read
	 * line
	 */
	public void operationPosition(String value, int length, int count) {
		// to obtain the x- cordinate of the cell of an array
		String xCoordinate = Integer.toString(count - 1);
		for (int i = 0; i < length; i++) {
			// to obtain first character of the string
			char character = value.charAt(i);
			String s = Character.toString(character);
			// defining the y co-ordinate of the cell of an array
			String yCoordinate = Integer.toString(i);
			// to check if the alphabet representing the grouping is already present in the
			// map
			if (operationsLocation.containsKey(s)) {
				ArrayList<String> existing = operationsLocation.get(s);
				existing.add(xCoordinate + yCoordinate);
				// to add grouping for that alphabet
				operationsLocation.put(s, existing);
			} else {
				// to add the alphabet to the map as key
				groupingOperation.add(s);
				ArrayList<String> mapValues = new ArrayList<String>();
				mapValues.add(xCoordinate + yCoordinate);
				// to insert cell position as value for that map key
				operationsLocation.put(s, mapValues);
			}

		}
	}

	/*
	 * This method indicates if the mathdoku puzzle is solvable based on the data
	 * provided in the input stream
	 *
	 * @return boolean This returns true if the puzzle is solvable else returns
	 * false in case puzzle can not be solved
	 */
	public boolean readyToSolve() {
		if (!groupingOperation.isEmpty() && !operationsRepresentation.isEmpty()) {
			// to check if key of both map contains same alphabets
			if (!groupingOperation.equals(operationsRepresentation)) {
				return false;
			}
			// to check if various counter kept to keep a track if strings have digits,
			// alphabets or different operations are zero
			else if (lengthCounter > 0 || stringCounter > 0 || stringOperationCounter > 0 || stringResultCounter > 0
					|| stringOperatorCounter > 0) {
				return false;
			}
			return true;
		} else {
			return false;
		}
	}

	/*
	 * This method solves the mathdoku puzzle using recursion and backtracking
	 *
	 * @return boolean This returns true if the puzzle is solved else returns false
	 * in case puzzle was not solvable
	 */
	public boolean solve() {
		if (!operations.isEmpty() && !operationsLocation.isEmpty() && readyToSolve()) {
			SolvePuzzleIndividual();
			// to loop through row
			for (int i = 0; i < length; i++) {
				// to loop thrugh column
				for (int j = 0; j < length; j++) {
					// to check if that particular cell is empty
					if (kenken[i][j] == 0) {
						// to loop through possible value for a cell
						for (int numberFilled = 1; numberFilled <= length; numberFilled++) {
							kenken[i][j] = numberFilled;
							// to check if that value is possible to be stored for that cell
							if (possibleValue(i, j, numberFilled) == true) {
								// to call the function again to perform the same process on another cell
								if (solve() == true) {
									return true;
								} else {
									// backtrackCounter = backtrackCounter + 1;
								}
							}
							// to set the value of cell to zero if any value can not be fit
							kenken[i][j] = 0;
						}
						// to increment the counter of backtracking
						backtrackCounter = backtrackCounter + 1;
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/*
	 * This method inserts value for the cell in 2d array where "=" operation is to
	 * be performed
	 */
	private void SolvePuzzleIndividual() {
		ArrayList<String> keyAlpha = new ArrayList<String>();
		String alphabetRepresentation = null;
		// to iterate over the key of that map that stores operation
		for (String key : operations.keySet()) {
			OutputOperation oo = operations.get(key);
			// to check if operation is "="
			if (oo.operation.equals("=")) {
				String output = oo.result;
				alphabetRepresentation = key;
				// to keep track of alphabets to be removed
				keyAlpha.add(alphabetRepresentation);
				// to obtain the position of cell with "=" operation
				String array = insertingValue(alphabetRepresentation);
				if (array != null) {
					int row = Integer.parseInt(array.substring(0, 1));
					int column = Integer.parseInt(array.substring(1));
					// to insert the result in 2d array
					kenken[row][column] = Integer.parseInt(output);
				}
				operationsLocation.remove(alphabetRepresentation);
			}
		}
		// to remove the alphabet which is used as key from map
		for (String k : keyAlpha) {
			operations.remove(k);
		}
	}

	/*
	 * This method is used to obtain the position of the cell from 2d array on which
	 * "=" operation is to be performed.
	 *
	 * @param alphabet This is the first parameter which indicates the alphabet to
	 * be found from map to obtain the cell to perform "=" operation
	 *
	 * @return String this returns the position of the cell with "=" operation
	 */
	private String insertingValue(String alphabet) {
		// to obtain the cell on which "=" operation is performed
		ArrayList<String> position = operationsLocation.get(alphabet);
		String arrayPosition = null;
		// to check if any cell has the alphabet that represents "=" operation
		if (position != null) {
			arrayPosition = position.get(0);
		}
		return arrayPosition;

	}

	/*
	 * This method is used to check if provided value for the cell satisfies that it
	 * is unique for row and column as well it satisfies the result to be obtained
	 * by performing the operation on that cell.
	 *
	 * @param row This is the first parameter which indicates row of the cell for
	 * which the value is to be filled
	 *
	 * @param column This is the second parameter which indicates column of the cell
	 * for which the value is to be filled
	 *
	 * @param number This is the third parameter which indicates the number to be
	 * filled for that cell
	 *
	 * @return boolean this returns true if the number satisfies all the three
	 * conditions else returns false
	 */
	public boolean possibleValue(int row, int col, int number) {

		boolean rowFlag = true;
		boolean columnFlag = true;
		boolean operandFlag = false;
		// to loop over row and column to check if that number is present in that row or
		// column
		for (int i = 0; i < length; i++) {
			if (kenken[row][i] == number && i != col) {
				rowFlag = false;
			}

			if (kenken[i][col] == number && i != row) {
				columnFlag = false;
			}
		}
		// to check if the provided value fits the output to be achieved from the
		// operation to be
		// performed on that cell
		if (rowFlag == true && columnFlag == true) {
			operandFlag = checkValueForOperation(row, col, number);
		}

		// to check if all the condition are satisfied for a particular number
		if (rowFlag == true && columnFlag == true && operandFlag == true) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * This method is used to check if the provided number satisfies the result to
	 * be achieved by performing the operation stated for that cell.
	 *
	 * @param row This is the first parameter which indicates row of the cell for
	 * which the value is to be filled
	 *
	 * @param column This is the second parameter which indicates column of the cell
	 * for which the value is to be filled
	 *
	 * @param number This is the third parameter which indicates the number to be
	 * filled for that cell
	 *
	 * @return boolean this returns true if the number satisfies the result to be
	 * achieved as per the operation to be performed on that cell else returns false
	 */
	private boolean checkValueForOperation(int row, int col, int number) {
		String stringRow = String.valueOf(row);
		String stringCol = String.valueOf(col);

		String keyAlphabet = null;
		ArrayList<String> position = null;

		// to get the alphabet that represents that location
		for (String key : operationsLocation.keySet()) {
			ArrayList<String> cellsOperation = operationsLocation.get(key);
			for (String cell : cellsOperation) {
				if (cell.equals(stringRow + stringCol)) {
					keyAlphabet = key;
				}
			}
		}

		// location on which same operation is to be performed
		position = operationsLocation.get(keyAlphabet);

		// to find operation and result for that location
		OutputOperation o = operations.get(keyAlphabet);
		String result = o.result;
		String operation = o.operation;

		// to check if operation to be performed is "-" or "/"
		if (operation.equals("-") || operation.equals("/")) {
			int subtraction = -1;
			int division = -1;
			int secondOperandRow = -1;
			int secondOperandCol = -1;

			// to obtain the position of 2nd cell of that grouping of cells
			for (String x : position) {
				if (!x.equals(stringRow + stringCol)) {
					String secondOperand = x;
					secondOperandRow = Integer.parseInt(secondOperand.substring(0, 1));
					secondOperandCol = Integer.parseInt(secondOperand.substring(1));
				}
			}

			// to obtain value of 2nd cell
			int value2 = kenken[secondOperandRow][secondOperandCol];

			// to check if 2nd cell is 0, so the provided value will work
			if (value2 == 0) {
				return true;
			} else {
				if (operation.equals("-")) {
					// to check if value of 2nd cell or inserted value is greater
					if (value2 > number) {
						subtraction = value2 - number;
					} else {
						subtraction = number - value2;
					}
					// to check the result of subtraction is similar to the expected result
					if (subtraction == Integer.parseInt(result)) {
						return true;
					} else {
						return false;
					}
				} else {
					// to check if value of 2nd cell or inserted value is greater
					if (value2 > number) {
						division = value2 / number;
					} else {
						division = number / value2;
					}
					// to check the result of division is similar to the expected result
					if (division == Integer.parseInt(result)) {
						return true;
					} else {
						return false;
					}
				}
			}
		}

		// to check if operation to be performed is "+" or "*"
		if (operation.equals("+") || operation.equals("*")) {
			int multiplication = 1;
			int operandRow = -1;
			int operandCol = -1;
			int flagSum = 0;
			int flagMultiply = 0;
			int sum = 0;

			// to obtain position of cell from the grouping and compute the
			// summation/multiplication value
			// for the cells where value is filled
			for (String x : position) {
				if (!x.equals(stringRow + stringCol)) {
					String operand = x;
					operandRow = Integer.parseInt(operand.substring(0, 1));
					operandCol = Integer.parseInt(operand.substring(1));
					int value = kenken[operandRow][operandCol];
					if (operation.equals("+")) {
						if (value != 0) {
							sum = sum + value;
						}
						// to increment the flag if value is blank
						else {
							flagSum = flagSum + 1;
						}
					} else {
						if (value != 0) {
							multiplication = multiplication * value;
						} else {
							flagMultiply = flagMultiply + 1;
						}
					}
				}
			}

			if (operation.equals("+")) {
				// if cells are present in grouping for which the value is not present
				if (flagSum >= 1) {
					return true;
				} else {
					sum = sum + number;
					// check if sum is equal to the expected result
					if (sum == Integer.parseInt(result)) {
						return true;
					} else {
						return false;
					}
				}
			} else {
				// if cells are present in grouping for which the value is not present
				if (flagMultiply >= 1) {
					return true;
				} else {
					// check if multiplication is equal to the expected result
					multiplication = multiplication * number;
					if (multiplication == Integer.parseInt(result)) {
						return true;
					} else {
						return false;
					}
				}
			}

		}
		return true;
	}

	/*
	 * This method is used to print the output of the solved mathdoku puzzle.
	 * 
	 * @return String this returns each row of the current puzzle state; listed from
	 * top-to-bottom and rows separated by a carriage return (\n) character.
	 */
	public String print() {
		String output = null;
		String keyAlphabet = null;
		if (lengthCounter > 0 || stringCounter > 0) {
			return null;
		}

		// to iterate over row and column
		for (int row = 0; row < length; row++) {
			for (int column = 0; column < length; column++) {
				keyAlphabet = null;
				// if value is not present for the cells of the puzzle obtain the alphabet
				// that represents that cell
				if (kenken[row][column] == 0) {
					String stringRow = String.valueOf(row);
					String stringCol = String.valueOf(column);
					for (String key : operationsLocation.keySet()) {
						ArrayList<String> cellsOperation = operationsLocation.get(key);
						for (String cell : cellsOperation) {
							if (cell.equals(stringRow + stringCol)) {
								keyAlphabet = key;
							}
						}
					}
					if (output == null) {
						output = keyAlphabet;
					} else {
						output = output + keyAlphabet;
					}
				}
				// if puzzle is solved obtain the value of that particular cell
				else {
					if (output == null) {
						output = Integer.toString(kenken[row][column]);
					} else {
						output = output + kenken[row][column];
					}
				}
			}
			output = output + "\\n";
		}
		return output;
	}

	/*
	 * This method is used to obtain the number of times backtracking is performed
	 * in order to solve mathdoku puzzle .
	 *
	 * @return int this returns the number of times backtracking is performed to
	 * obtain
	 */
	public int choices() {
		return backtrackCounter;
	}

}
