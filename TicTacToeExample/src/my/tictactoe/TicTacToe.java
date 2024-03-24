package my.tictactoe;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {

//	private static int COUNTER = 0;

	private static List<Integer> computerInexList = new LinkedList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

	private static char[][] charArr = { { ' ', ' ', ' ' }, { ' ', ' ', ' ' }, { ' ', ' ', ' ' } };

	private static String userName = "";

	public static void main(String[] args) throws Exception {
		try (Scanner scan = new Scanner(System.in);) {
			new TicTacToe().startGame(scan);
		}
	}

	private void startGame(Scanner scan) throws Exception {
		System.out.println("Eneter Your Name");
		userName = readUserName(scan);
		System.out.println();
		printTicTac(charArr);
		int totalMoves = charArr.length * 3;
		for (int i = 0; i < totalMoves; i++) {
			if (i % 2 == 0) {
				playerTurn(scan);
			} else {
				computerTurn();
			}
			if (i > 2)
				checkIfWON();
		}
		System.out.println("GAME OVER");
	}

	private void checkIfWON() {
		char wonCharacter = checkIfRowMatched();
		checkWonCharAndExit(wonCharacter);
		wonCharacter = checkIfColumnMatched();
		checkWonCharAndExit(wonCharacter);
		wonCharacter = checkIfDiagonalMatched();
		checkWonCharAndExit(wonCharacter);
	}

	private void checkWonCharAndExit(char wonCharacter) {
		if (wonCharacter == 'X' || wonCharacter == 'O') {
			String wonCharName = wonCharacter == 'X' ? userName : "BOT";
			System.out.println(String.format("%s Won with character %c", wonCharName, wonCharacter));
			System.out.println("GAME OVER");
			System.exit(0);
		}
	}

	private char checkIfDiagonalMatched() {
		char wonCharacter = 0;
		if (charArr[1][1] == ' ') {
			return wonCharacter;
		} else {
			char firstRowFirstColumn = charArr[0][0];
			char firstRowThirdColumn = charArr[0][2];
			char middle = charArr[1][1];
			if (firstRowFirstColumn == middle && firstRowFirstColumn == charArr[2][2]) {
				wonCharacter = firstRowFirstColumn;
			} else if (firstRowThirdColumn == middle && firstRowThirdColumn == charArr[2][0]) {
				wonCharacter = firstRowFirstColumn;
			}

		}
		return wonCharacter;
	}

	private char checkIfRowMatched() {
		char wonCharacter = 0;
		for (int i = 0; i < charArr.length; i++) {
			if (charArr[i][0] == ' ') {
				continue;
			} else if (charArr[i][1] == ' ') {
				continue;
			} else if (charArr[i][2] == ' ') {
				continue;
			} else {
				char atFirstPosition = charArr[i][0];
				if (atFirstPosition == charArr[i][1] && atFirstPosition == charArr[i][2]) {
					wonCharacter = atFirstPosition;
					break;
				}
			}
		}
		return wonCharacter;
	}

	private char checkIfColumnMatched() {
		char wonCharacter = 0;
		for (int j = 0; j < charArr.length; j++) {
			if (charArr[0][j] == ' ') {
				continue;
			} else if (charArr[1][j] == ' ') {
				continue;
			} else if (charArr[2][j] == ' ') {
				continue;
			} else {
				char atFirstPosition = charArr[0][j];
				if (atFirstPosition == charArr[1][j] && atFirstPosition == charArr[2][j]) {
					wonCharacter = atFirstPosition;
					break;
				}
			}
		}
		return wonCharacter;
	}

	private void playerTurn(Scanner scan) throws Exception {
		int inputNumber = 0;
		boolean isPlayerMoveCompleted = false;
		while (!isPlayerMoveCompleted) {
			try {
				inputNumber = Integer.valueOf(readUserInput(scan));

			} catch (Exception e) {
				System.out.println("provided input is not a number :) " + inputNumber);
				throw new Exception("provided input is not a number :) " + inputNumber);
			}
			isPlayerMoveCompleted = addInputNumberToTicTac(charArr, inputNumber, 'X');
			if (!isPlayerMoveCompleted) {
				System.out.println("Provided number is already present with character please provide different number");
			}
		}
		removeFromRemainingList(computerInexList.indexOf(inputNumber));
	}

	private void computerTurn() throws Exception {
		boolean isComputerMoveCompleted = false;
		int randomIndex = 0;
		while (!isComputerMoveCompleted) {
			randomIndex = getRandomIndex(computerInexList.size());
			int randomPosition = computerInexList.get(randomIndex);
			System.out.println("computerTurn number " + randomPosition);
			isComputerMoveCompleted = addInputNumberToTicTac(charArr, randomPosition, 'O');
		}
		removeFromRemainingList(randomIndex);
	}

	private void removeFromRemainingList(int index) {
		computerInexList.remove(index);
	}

	private int getRandomIndex(int arayLength) {
		int randomPosition = new Random().nextInt(arayLength);
		return randomPosition;
	}

	private boolean addInputNumberToTicTac(char[][] charArr, int position, char charSelected) throws Exception {
		if (position <= 0 || position > 3 * charArr.length) {
			System.out.println("provided number is out of range :) " + position);
			throw new Exception("provided number is out of range :) " + position);
		}
		int[] firstAndSecondIndex = calculateFirstAndSecondIndex(position);
		int firstIndex = firstAndSecondIndex[0];
		int secondIndex = firstAndSecondIndex[1];
		if (charArr[firstIndex][secondIndex] != ' ') {
			System.out.println("Already present for given position " + position + " character "
					+ charArr[firstIndex][secondIndex]);
			return false;
		}
		charArr[firstIndex][secondIndex] = charSelected;
		printTicTac(charArr);
//		COUNTER++;
		return true;
	}

	private int[] calculateFirstAndSecondIndex(int position) {
		double firstIndexDouble = (position - 1) / 3;
		int firstIndex = (int) firstIndexDouble;
		int secondIndex = 0;
		int secondIndexPoistion = firstIndex * 3;
		if (secondIndexPoistion == 0) {
			secondIndex = (position - 1);
		} else {
			secondIndex = (position - 1) - secondIndexPoistion;
		}
		return new int[] { firstIndex, secondIndex };
	}

	private String readUserName(Scanner scan) {
		return scan.nextLine();
	}

	private String readUserInput(Scanner scan) {
		System.out.println("Please provide number between 1-9");
		String inputnumber = scan.nextLine();
		System.out.println("plyer turn number " + inputnumber);
		return inputnumber;
	}

	private void printTicTac(char[][] chara) {
		int charrLength = chara.length;
		for (int i = 0; i < charrLength; i++) {
			printRow(chara, i);
			if (i < charrLength - 1) {
				printRowSeperator();
			}
		}
	}

	private void printRow(char[][] chara, int rowIndex) {
		System.out.println(String.format("%s | %s | %s", chara[rowIndex][0], chara[rowIndex][1], chara[rowIndex][2]));
	}

	private void printRowSeperator() {
		System.out.println("- + - + -");
	}

}
