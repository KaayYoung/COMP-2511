package Controller.Search;

import java.util.List;

import Controller.Board;

public interface SearchAlgorithm {
	public enum PrintMode {
		PRINT_BOARD,
		PRINT_CARS
	}
	
	List <Board> getPath(Board board, Heuristic heuristic);
	
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
