package Controller.Search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import Controller.Board;

public class SearchAlgorithmBFS implements SearchAlgorithm {

	public List<Board> getPath(Board board, Heuristic heuristic) {
		Board frontBoard = new Board(board.getCars());
		ArrayList<Board> frontPath = new ArrayList<Board>();
		
		ArrayList<Board> discovered = new ArrayList<Board>();
		List<List<Board>> solutions = new ArrayList<List<Board>>();
		ArrayList<Board> moves = new ArrayList<Board>();
		
		Queue<Board> boardQueue = new LinkedList<Board>();
		boardQueue.add(frontBoard);
		
		Queue<ArrayList<Board>> pathQueue = new LinkedList<ArrayList<Board>>();
		ArrayList<Board> newPath = new ArrayList<Board>();
		
		// Find all solutions
		while (boardQueue.size() != 0) {
			frontBoard = boardQueue.poll();
			
			if (pathQueue.size() != 0) {
				frontPath = new ArrayList<Board>(pathQueue.poll());
			}

			frontPath.add(frontBoard);
			newPath = frontPath;
			
			if (discovered.contains(frontBoard)) {
				continue;
			}
			else {
				discovered.add(frontBoard);
			}
			
			if (frontBoard.isSolved()) {
				solutions.add(newPath);
			}
			else {
				moves = frontBoard.getPossibleMoves();
				for (Board b : moves) {
					boardQueue.add(b);
					pathQueue.add(newPath);
				}
			}
		}
		
		// Get shortest solution
		List<Board> shortestSolution = getShortestSolution(solutions);
		
		return shortestSolution;
	}
	
	public List<Board> getShortestSolution(List<List<Board>> solutions){
		List<Board> shortestSolution = new ArrayList<Board>();
		if (solutions.size() != 0) {
			shortestSolution = solutions.get(0);
			for (int i = 1; i < solutions.size(); i++) {
				if (solutions.get(i).size() < shortestSolution.size()) {
					shortestSolution = solutions.get(i);
				}
			}
		}
		return shortestSolution;
	}
}
