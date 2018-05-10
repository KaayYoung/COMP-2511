package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class AStar implements SearchAlgo{
	
	
	@Override
	public ArrayList<moves> getPath(Board iniBoard)
	{
		State iniState = new State(iniBoard);
		Heuristic h = new BlockersLowerBound();
		int iniH = h.getHeuristic(iniBoard);
		iniState.setPathCostH(iniH);
		iniState.setPathCostF();
		//set of nodes already visited
		Set<Board> closed = new LinkedHashSet<Board>();
		//set of nodes discovered but not visited yet
		PriorityQueue<State> open = new PriorityQueue<State>(); 
		open.add(iniState);
		while(!open.isEmpty())
		{
			State currentState = open.poll();
			Board curBoard = currentState.getCurrentBoard();
//			System.out.println();
//			curBoard.printBoard();
			if(curBoard.isSolved())
			{
//				curBoard.printBoard();
				return currentState.reconstruct_path();
			}
			//if(!closed.contains(curBoard))
			closed.add(curBoard);
			int gCur = currentState.getPathCostG(); 
			int gNex = gCur + 1;
			HashMap<Board, moves> possible = curBoard.getPossibleMoves();
			for(Board nex : possible.keySet())
			{
				if(nex.equals(curBoard)) continue;
				//moves n = possible.get(nex);
				//moves m = new moves(n.getId(), n.getDir(), n.numofMove());
				if(closed.contains(nex)) continue;
//				System.out.println();
//				System.out.println("size of closed list " + closed.size());
				moves m = possible.get(nex);
				State s = new State(nex);
				s.setMove(m);
				s.setPathCostG(gNex);
				s.setPathCostH(h.getHeuristic(nex));
				s.setPathCostF();
				s.setpreState(currentState);
				open.add(s);
				
				
			}
			
			
		}
		
		return null;
		
	}
	
	
	

	
	
}


