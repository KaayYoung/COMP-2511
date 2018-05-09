package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

public class AStar {
	
	
	
	public ArrayList<moves> getPath(Board iniBoard)
	{
		State iniState = new State(iniBoard);
		Heuristic h = new BlockersLowerBound();
		int iniH = h.getHeuristic(iniBoard);
		iniState.setPathCostH(iniH);
		iniState.setPathCostF();
		//set of nodes already visited
//		PriorityQueue<State> closed = new PriorityQueue<State>(); 
		//set of nodes discovered but not visited yet
		PriorityQueue<State> open = new PriorityQueue<State>(); 
		open.add(iniState);
		while(!open.isEmpty())
		{
			State currentState = open.poll();
			Board curBoard = currentState.getCurrentBoard();
			if(curBoard.isSolved())
			{
				return reconstruct_path(currentState);
			}
//			closed.add(currentState);
			int gCur = currentState.getPathCostG(); 
			int gNex = gCur + 1;
			HashMap<Board, moves> possible = curBoard.getPossibleMoves();
			for(Board nex : possible.keySet())
			{
				if(nex.equals(curBoard)) continue;
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
	
	
	private ArrayList<moves> reconstruct_path(State finalState)
	{
		ArrayList<moves> reverse = new ArrayList<moves>();
		State cur = finalState;
		while(cur!=null)
		{
			reverse.add(cur.getMove());
			cur = cur.getPreState();
		}
		int i = reverse.size() - 1;
		ArrayList<moves> path = new ArrayList<moves>();
		while(i>=0)
		{
			path.add(reverse.get(i));
			i--;
		}
		
		return path;
		
	}

	
	
}


