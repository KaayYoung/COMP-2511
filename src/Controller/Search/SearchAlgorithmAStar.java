package Controller.Search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import Controller.Board;

public class SearchAlgorithmAStar implements SearchAlgorithm {
	
	public List<Board> getPath(Board board, Heuristic heuristic) {
		Map<AStarNode, AStarNode> cameFrom = new HashMap<AStarNode, AStarNode>();
    	List<AStarNode> closedList = new ArrayList<AStarNode>();	// The list of nodes already evaluated.
    	
    	Queue<AStarNode> openList = new PriorityQueue<AStarNode>(); // The set of tentative nodes to be evaluated, initially containing the start node
	    AStarNode start = new AStarNode(board);
    	openList.add(start);
    	
    	// Cost from start to start is 0
    	start.setGValue(0);

	    // Estimate total cost from start to goal
    	start.setFValue(heuristic.calculateHValue(start) + start.getGValue());
	 
    	
	    while (!openList.isEmpty()) {
	    	AStarNode current = openList.poll();
	    	
	    	if (current.getBoard().isSolved()) {
	    		return reconstructPath(cameFrom, current);
	    	}
	    
	    	closedList.add(current);

	    	for (AStarNode neighbor : current.getNeighbours()) {
	    		if (closedList.contains(neighbor)) {
	    			continue;
	    		}
	    		
	    		int tentative_gScore = current.getGValue() + current.MOVEMENT_COST;
	    		
	    		if (!openList.contains(neighbor) || tentative_gScore < neighbor.getGValue()) {
	    			cameFrom.put(neighbor, current);
	    			neighbor.setParent(current);
	    			neighbor.setGValue(tentative_gScore);
	    			neighbor.setFValue(heuristic.calculateHValue(neighbor) + neighbor.getGValue());
	    			
	    			if (!openList.contains(neighbor)) {
	    				openList.add(neighbor);
	    			}
	    		}

	    	}
	    }
    	return null;
    }
	
	// Reconstructs Path from A Star Search
	public List<Board> reconstructPath(Map<AStarNode, AStarNode> cameFrom, AStarNode current){
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
}
