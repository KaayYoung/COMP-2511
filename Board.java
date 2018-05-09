
import java.util.ArrayList;
import java.util.HashMap;

public class Board {
	private int boardSize;
	private int[][] gameBoard;
	private HashMap<Integer, Car> cars;
	private int carCount;
	
	public Board(HashMap<Integer, Car> cars) {
		this.boardSize = 6;
		this.cars = new HashMap<Integer, Car>(cars);
		this.carCount = cars.size();
		this.gameBoard = new int[6][6];
		updateGameBoard();
	}
	
	public int getBoardSize() {
		return boardSize;
	}

	public Car getCar(int carId) {
		return cars.get(carId);
	}
	
	public void setGameBoard(int[][] gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public void updateGameBoard() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				getGameBoard()[i][j] = 0;
			}
		}
		
		for (int key : getCars().keySet()) {
			Car currentCar = getCars().get(key);
			if (currentCar.getDirection() == 0) {
				for (int j = 0; j < currentCar.getLength(); j++) {
					gameBoard[currentCar.getPosRow() - 1][currentCar.getPosCol() + j] = currentCar.getCarId();
				}
			}
			else {
				for (int j = 0; j < currentCar.getLength(); j++) {
					gameBoard[currentCar.getPosRow() - currentCar.getLength() + j][currentCar.getPosCol()] = currentCar.getCarId();
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
			
			int curId = currentCar.getCarId();
			int curRow = currentCar.getPosRow();
			int curCol = currentCar.getPosCol();
			int curLength = currentCar.getLength();
			int curDirection = currentCar.getDirection();
			
			if (curDirection == 1) {
				// Move Left
				if(curCol >= 1 && getGameBoard()[curRow - 1][curCol - 1] == 0) {
					Car newCar = new Car(curId, curRow, curCol - 1, curLength, 1);
					carsCopy = new HashMap<Integer, Car>(cars);
					carsCopy.replace(curId, newCar);
					moves.add(new Board(carsCopy));
				}
				
				// Move Right
				if ((curCol + curLength) >= 1 && getGameBoard()[curRow - 1][curCol + curLength] == 0) {
					Car newCar = new Car(curId, curRow, curCol + 1, curLength, 1);
					carsCopy = new HashMap<Integer, Car>(cars);
					carsCopy.replace(curId, newCar);
					moves.add(new Board(carsCopy));
				}
			}
			else {
				// Move Up
				if((curRow - curLength >= 1) && getGameBoard()[curRow - curLength - 1][curCol	] == 0) {
					Car newCar = new Car(curId, curRow - 1, curCol, curLength, 1);
					carsCopy = new HashMap<Integer, Car>(cars);
					carsCopy.replace(curId, newCar);
					moves.add(new Board(carsCopy));
				}
				
				// Move Down
				if (curRow <= 5 && getGameBoard()[curRow][curCol] == 0) {
					Car newCar = new Car(curId, curRow + 1, curCol, curLength, 1);
					carsCopy = new HashMap<Integer, Car>(cars);
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

	public int getCarCount() {
		return carCount;
	}

	public HashMap<Integer, Car> getCars() {
		return cars; 
	}
	
	public boolean equals(Board otherBoard) {
		return getCars().equals(otherBoard.getCars());
	}
	
	public void printBoard() {
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 6; j++) {
				System.out.print(getGameBoard()[i][j]);
			}
			System.out.println();
		}
	}
	
	public void printCars() {
		for (int key : getCars().keySet()) {	
			Car currentCar = getCars().get(key);
			
			System.out.println(currentCar);
		}
	}
}