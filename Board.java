
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Board {
	private int boardSize;
	private int[][] gameBoard;
	private HashMap<Integer, Car> cars;
	private int carCount;
	
	public Board(HashMap<Integer, Car> cars) {
		this.boardSize = 6;
		this.cars = new HashMap<Integer, Car>(cars);
		this.carCount = cars.size();
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
	
	public int[][] getGameBoard() {
		@SuppressWarnings("rawtypes")
		Iterator i = cars.entrySet().iterator();
		
		while(i.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry me = (Map.Entry)i.next();
			Car currentCar = (Car) me.getValue();
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
		
		return gameBoard;
	}
	
	public ArrayList<Board> getPossibleMoves() {
		ArrayList<Board> moves = new ArrayList<Board>();
		
		int[][] board = getGameBoard();
		
		HashMap<Integer, Car> carsCopy = new HashMap<Integer, Car>(getCars());;
		
		return moves;
	}
	
	public boolean addCars() {
		boolean canAdd = false;
		/**
		 *  to_to
		 */
		return canAdd;
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
}