package search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import gridlock.Board;
import settings.Settings;

public class SearchAlgorithmAStar extends SearchAlgorithm {
	private Heuristic heuristic;
	
	public SearchAlgorithmAStar(Heuristic heuristic) {
		this.heuristic = heuristic;
	}
	
	@Override
	public List<Board> getPath(Board board) {
		Map<AStarNode, AStarNode> cameFrom = new HashMap<AStarNode, AStarNode>();
		Set<AStarNode> closedList = new HashSet<AStarNode>();	// The list of nodes already evaluated.
    	
    	Queue<AStarNode> openList = new PriorityQueue<AStarNode>(); // The set of tentative nodes to be evaluated, initially containing the start node
	    AStarNode start = new AStarNode(board);
    	openList.add(start);
    	
    	// Cost from start to start is 0
    	start.setGValue(0);

	    // Estimate total cost from start to goal
    	start.setFValue(getHeuristic().calculateHValue(start) + start.getGValue());
	 
    	
	    while (!openList.isEmpty()) {
	    	AStarNode current = openList.poll();
	    	
	    	if (current.getBoard().isSolved()) {
	    		return reconstructPath(cameFrom, current);
	    	}
	    	
	    	closedList.add(current);
//	    	System.out.println(openList.size());
	    	for (AStarNode neighbor : current.getNeighbours()) {    		
	    		if (closedList.contains(neighbor)) {
//	    			System.out.println(closedList.size());
	    			continue;
	    		}
	    		
	    		int tentative_gScore = current.getGValue() + Settings.MOVEMENT_COST;
	    		
	    		if (!openList.contains(neighbor) || tentative_gScore < neighbor.getGValue()) {
	    			cameFrom.put(neighbor, current);
	    			neighbor.setParent(current);
	    			neighbor.setGValue(tentative_gScore);
	    			neighbor.setFValue(getHeuristic().calculateHValue(neighbor) + neighbor.getGValue());
	    			
	    			if (!openList.contains(neighbor)) {
	    				openList.add(neighbor);
	    			}
	    		}
	    	}
	    }
	    
    	return null;
    }
	
	// Reconstructs Path from A Star Search
	private List<Board> reconstructPath(Map<AStarNode, AStarNode> cameFrom, AStarNode current){
		List<Board> path = new ArrayList<Board>();
		path.add(current.getBoard());
		
		AStarNode curr = current;
		while (curr.getParent() != null) {
			AStarNode parent = curr.getParent();
            path.add(0, parent.getBoard());
            curr = parent;
		}
		
		return path;
	}

	public Heuristic getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(Heuristic heuristic) {
		this.heuristic = heuristic;
	}
}
