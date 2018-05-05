import java.util.HashSet;

public class Board {
	private int board_size;
	private HashSet<Car> carSet;
	private int[][] gameBoard;
	
	public Board() {
		this.board_size = 6;
		for (int col = 0; col < board_size; col++) {
			for (int row = 0; row < board_size; row++) {
				this.gameBoard[col][row] = 0;
			}
		}
	}
	
	public int getBoard_size() {
		return board_size;
	}

	public HashSet<Car> getCarSet() {
		return carSet;
	}
	public void setCarSet(HashSet<Car> carSet) {
		this.carSet = carSet;
	}
	public int[][] getGameBoard() {
		return gameBoard;
	}
	public void setGameBoard(int[][] gameBoard) {
		this.gameBoard = gameBoard;
	}
	
	public int canMove(Car cars) {
		int minBlocks = 0;
		/**
		 *  to_do
		 */
		return minBlocks;
	}
	
	public boolean addCars() {
		boolean canAdd = false;
		/**
		 *  to_to
		 */
		return canAdd;
	}
}
