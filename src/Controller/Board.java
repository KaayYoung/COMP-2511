package Controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import Controller.Car.Direction;

public class Board {
	public static final int BOARD_SIZE = 6;
	
	private HashMap<Integer, Car> cars;
	private int carCount;
	private int[][] gameBoard;
	private int width;
	private int height;
	
	public Board(HashMap<Integer, Car> cars) {
		this.width = BOARD_SIZE;
		this.height = BOARD_SIZE;
		this.cars = cars;
		this.carCount = cars.size();
		this.gameBoard = new int[width][height];
		updateGameBoard();
	}
	
	public void updateGameBoard() {
		// Empty the board
		// fill board with 0's
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				getGameBoard()[i][j] = 0;
			}
		}
		
		// Get each car's position and add to board
		if (getCarCount() > 0) {
			for (int key : getCars().keySet()) {
				Car currentCar = getCars().get(key);
				if (currentCar.getDirection() == Direction.HORIZONTAL) {
					for (int j = 0; j < currentCar.getLength(); j++) {
						getGameBoard()[currentCar.getPosRow()][currentCar.getPosCol() + j] = currentCar.getCarId();
					}
				}
				else {
					for (int j = 0; j < currentCar.getLength(); j++) {
						getGameBoard()[currentCar.getPosRow() + 1 - currentCar.getLength() + j][currentCar.getPosCol()] = currentCar.getCarId();
					}
				}
			}
		}
	}
	
	public int[][] getGameBoard() {
		return gameBoard;
	}
	
	public ArrayList<Board> getPossibleMoves() {
		ArrayList<Board> moves = new ArrayList<Board>();
		
		HashMap<Integer, Car> carsCopy;
		
		for (int key : getCars().keySet()) {	
			Car currentCar = getCars().get(key);
			
			updateGameBoard();
			int [][] board = getGameBoard();
			
			int curId = currentCar.getCarId();
			int curRow = currentCar.getPosRow();
			int curCol = currentCar.getPosCol();
			int curLength = currentCar.getLength();
			Direction curDirection = currentCar.getDirection();
			
			if (curDirection == Direction.HORIZONTAL) {
				// Move Left
				if((curCol >= 1) && board[curRow][curCol - 1] == 0) {
					Car newCar = new Car(curId, curRow, curCol - 1, curLength, curDirection);
					carsCopy = new HashMap<Integer, Car>(getCars());
					carsCopy.replace(curId, newCar);
					moves.add(new Board(carsCopy));
				}
				
				// Move Right
				if ((curCol + curLength <= getWidth() - 1) && board[curRow][curCol + curLength] == 0) {
					Car newCar = new Car(curId, curRow, curCol + 1, curLength, curDirection);
					carsCopy = new HashMap<Integer, Car>(getCars());
					carsCopy.replace(curId, newCar);
					moves.add(new Board(carsCopy));
				}
			}
			else if (curDirection == Direction.VERTICAL) {
				// Move Up
				if((curRow + 1 - curLength >= 1) && board[curRow + 1 - curLength - 1][curCol] == 0) {
					Car newCar = new Car(curId, curRow - 1, curCol, curLength, curDirection);
					carsCopy = new HashMap<Integer, Car>(getCars());
					carsCopy.replace(curId, newCar);
					moves.add(new Board(carsCopy));
				}
				
				// Move Down
				if ((curRow + 1 <= getHeight() - 1) && board[curRow + 1][curCol] == 0) {
					Car newCar = new Car(curId, curRow + 1, curCol, curLength, curDirection);
					carsCopy = new HashMap<Integer, Car>(getCars());
					carsCopy.replace(curId, newCar);
					moves.add(new Board(carsCopy));
				}
			}
		}
		
		return moves;
	}
	
	public boolean isSolved() {
		return (getCar(1).getPosCol() + getCar(1).getLength() == 6);
	}
	
	public boolean equals(Object otherBoard) {
		// Slow
//		try {
//
//	        return (getCars().equals(((Board) otherBoard).getCars()));
//
//	    } catch (ClassCastException e) {
//
//	    	return super.equals(otherBoard);
//	    }
		
		// 3x Faster
		return Arrays.deepEquals(getGameBoard(), ((Board) otherBoard).getGameBoard());
	}
	
	public void printBoard() {
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				System.out.print(getGameBoard()[i][j]);
			}
			System.out.println();
		}
	}
	
	public void printCars() {
		for (int key : getCars().keySet()) {	
			System.out.println(getCars().get(key));
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}

	public Car getCar(int carId) {
		return cars.get(carId);
	}
	
	public int getCarCount() {
		return carCount;
	}

	public HashMap<Integer, Car> getCars() {
		return cars; 
	}
}