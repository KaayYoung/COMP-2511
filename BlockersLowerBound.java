package search;

public class BlockersLowerBound implements Heuristic{

	@Override
	public int getHeuristic(Board b) {
		int startCol = b.getCar(1).getPosCol();
		int startRow = b.getCar(1).getPosRow();
		int redLength = b.getCar(1).getLength();
		int total = startCol + redLength;
		int[][] board = b.getGameBoard();
		
		int h = 0;
		
		while(total < 6)
		{
			//int Curcar = 1;
			if(board[startRow][total] != 1 && board[startRow][total] != 0)
			{
				h++;
				//Curcar = board[startRow][total];
			}
			total++;
		}
		return h;
	}

}
