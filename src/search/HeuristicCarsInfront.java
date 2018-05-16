package search;

import settings.Settings;
/*
 * This heuristic is also known as the blocking heuristic 
 * which is equal to zero at any goal state, 
 * and is equal to one plus the number of cars blocking the path to the exit in all other states.
 */
public class HeuristicCarsInfront implements Heuristic{
	// Many thanks to Jennifer
	public int calculateHValue(AStarNode current) {
		int h = 0;
		
		if (!current.getBoard().isSolved()) {
			int mainCarId = current.getBoard().getCar(1).getCarId();
			int mainCarRow = current.getBoard().getCar(1).getPosRow();
			int mainCarCol = current.getBoard().getCar(1).getPosCol();
			int mainCarLength = current.getBoard().getCar(1).getLength();
			
			for (int i = mainCarCol + mainCarLength; i < Settings.BOARD_SIZE; i++) {
				if (current.getBoard().getGameBoard()[mainCarRow][i] != mainCarId && current.getBoard().getGameBoard()[mainCarRow][i] != 0){
					h++;
				}
			}
			return 1 + h;
		}

		return h;
	}
}
