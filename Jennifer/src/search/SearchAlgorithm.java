package search;

import java.util.List;

import gridlock.Board;

public abstract class SearchAlgorithm {
	public enum PrintMode {
		PRINT_BOARD,
		PRINT_CARS
	}
	
	public List<Board> getPath(Board board){
		return null;
	}
	
	public static void printSolution(List<Board> solution, PrintMode mode) {
		if (mode == PrintMode.PRINT_CARS) {
			for (int i = 1; i < solution.size(); i++) {
				solution.get(i).printCars();
				System.out.println();
			}
		}
		else if (mode == PrintMode.PRINT_BOARD) {
			for (int i = 1; i < solution.size(); i++) {
				solution.get(i).printBoard();
				System.out.println();
			}
		}
	}
}
