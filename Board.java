import java.util.HashSet;

public class Board {
	private int board_size;
	private HashSet<Car> carSet;
	private int[][] gameBoard;
	
	public Board(int board_size, int[][] gameBoard) {
		this.board_size = board_size;
		this.gameBoard = gameBoard;
	}
	
	public int getBoard_size() {
		return board_size;
	}
	public void setBoard_size(int board_size) {
		this.board_size = board_size;
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
	
}
