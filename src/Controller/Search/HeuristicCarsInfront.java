package Controller.Search;

import Controller.Board;

public class HeuristicCarsInfront implements Heuristic{
	// Many thanks to Jennifer
	public int calculateHValue(AStarNode current) {
		int h = 0;
		
		int mainCarId = current.getBoard().getCar(1).getCarId();
		int mainCarRow = current.getBoard().getCar(1).getPosRow();
		int mainCarCol = current.getBoard().getCar(1).getPosCol();
		int mainCarLength = current.getBoard().getCar(1).getLength();
		
		for (int i = mainCarCol + mainCarLength; i < Board.BOARD_SIZE; i++) {
			if (current.getBoard().getGameBoard()[mainCarRow][i] != mainCarId && current.getBoard().getGameBoard()[mainCarRow][i] != 0){
				h++;
			}
		}
		
		return h;
	}
}
