package Controller.Search;

import java.util.List;

import Controller.Board;

public interface SearchAlgorithm {
	public enum PrintMode {
		PRINT_BOARD,
		PRINT_CARS
	}
	
	List <Board> getPath(Board board, Heuristic heuristic);
	
	public void printSolution(List<Board> shortestSolution, PrintMode mode);
}
