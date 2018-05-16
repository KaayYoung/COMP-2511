package gridlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gridlock.Car.Direction;
import settings.Settings;

public class Board {
	private Map<Integer, Car> cars;
	private int carCount;
	private int[][] gameBoard;
	
	public Board(Map<Integer, Car> cars) {	
		if (cars != null) {
			this.cars = cars;
			this.carCount = cars.size();
		}
		else {
			this.cars = new HashMap<Integer, Car>();
			this.carCount = 0;
		}
		
		this.gameBoard = new int[Settings.BOARD_SIZE][Settings.BOARD_SIZE];
		updateGameBoard();
	}
	
	public boolean addCar(Car newCar) {
		if (cars.size() <= Settings.MAX_CARS && !cars.containsKey(newCar.getCarId())) {
			if (newCar.getDirection() == Direction.HORIZONTAL) {
				for (int i = 0; i < newCar.getLength(); i++) {
					if (getGameBoard()[newCar.getPosRow()][newCar.getPosCol() + i] != 0) {
						return false;
					}
				}
				
				cars.put(newCar.getCarId(), newCar);
				carCount++;
				updateGameBoard();
				return true;
			}
			else {
				for (int i = 0; i < newCar.getLength(); i++) {
					if (getGameBoard()[newCar.getPosRow() + 1 - newCar.getLength() + i][newCar.getPosCol()] != 0) {
						return false;
					}
				}
				
				cars.put(newCar.getCarId(), newCar);
				carCount++;
				updateGameBoard();
				return true;
			}
		}
		return false;
	}
	
	public void changeCarPos(int carId, int newRow, int newCol) {
		cars.get(carId).changePos(newRow, newCol);
		updateGameBoard();
	}
	
	public void updateGameBoard() {
		// Empty the board
		// fill board with 0's
		for (int i = 0; i < Settings.BOARD_SIZE; i++) {
			for (int j = 0; j < Settings.BOARD_SIZE; j++) {
				gameBoard[i][j] = 0;
			}
		}
		
		// Get each car's position and add to board
		if (getCarCount() > 0) {
			for (int key : getCars().keySet()) {
//			for (int i = 1; i <= carCount; i++) {
				Car currentCar = cars.get(key);
				if (currentCar.getDirection() == Direction.HORIZONTAL) {
					for (int j = 0; j < currentCar.getLength(); j++) {
						gameBoard[currentCar.getPosRow()][currentCar.getPosCol() + j] = currentCar.getCarId();
					}
				}
				else {
					for (int j = 0; j < currentCar.getLength(); j++) {
						gameBoard[currentCar.getPosRow() + 1 - currentCar.getLength() + j][currentCar.getPosCol()] = currentCar.getCarId();
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
		
		Map<Integer, Car> carsCopy;
		
		updateGameBoard();
		
		for (int key : getCars().keySet()) {	
			Car currentCar = cars.get(key);
			
			int [][] board = gameBoard;
			
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
				if ((curCol + curLength <= Settings.BOARD_SIZE - 1) && board[curRow][curCol + curLength] == 0) {
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
				if ((curRow + 1 <= Settings.BOARD_SIZE - 1) && board[curRow + 1][curCol] == 0) {
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
		return (getCar(Settings.RED_CAR_ID).getPosCol() + getCar(Settings.RED_CAR_ID).getLength() == Settings.BOARD_SIZE);
	}
	
	@Override
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
		return Arrays.deepEquals(gameBoard, ((Board) otherBoard).gameBoard);
	}
	
	public void printBoard() {
		for (int i = 0; i < Settings.BOARD_SIZE; i++) {
			for (int j = 0; j < Settings.BOARD_SIZE; j++) {
				System.out.print(gameBoard[i][j]);
			}
			System.out.println();
		}
	}
	
	public void printCars() {
		for (int key : cars.keySet()) {	
			System.out.println(cars.get(key));
		}
	}

	public Car getCar(int carId) {
		return cars.get(carId);
	}
	
	public int getCarCount() {
		return carCount;
	}

	public Map<Integer, Car> getCars() {
		return cars; 
	}
}