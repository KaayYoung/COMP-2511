package search;

import java.util.ArrayList;
import java.util.List;

import gridlock.Board;
import settings.Settings;

public class AStarNode implements Comparable<AStarNode>{
    private final Board board;
    private AStarNode parent;
    private int gValue;
    private int hValue;
    private int fValue;

    public AStarNode(Board b) {
        this.board = new Board(b.getCars());
        this.parent = null;
        this.gValue = 0;
        this.hValue = 0;
        this.fValue = 0;
    }
    
    public List<AStarNode> getNeighbours(){
    	List<AStarNode> n = new ArrayList<AStarNode>();
    	
    	for (Board b : getBoard().getPossibleMoves()) {
    		AStarNode child = new AStarNode(b);
    		child.parent = this;
    		n.add(child);
    	}
    	
    	return n;
    }

    public int getFValue() {
        return fValue;
    }
    
    public int getHValue() {
        return hValue;
    }
    
    public int getGValue() {
        return gValue;
    }
    
    public void setFValue(int fValue) {
    	this.fValue = fValue;
    }
    
    public void setHValue(int hValue) {
    	this.hValue = hValue;
    }
    
    public void setGValue(int gValue) {
    	this.gValue = gValue;
    }
    
    public Board getBoard() {
    	return board;
    }
    
    public void setParent(AStarNode parent) {
    	this.parent = parent;
    }
    
    public AStarNode getParent() {
		return parent;
	}
    
    @Override
    public boolean equals(Object o) {
		return (getBoard().equals(((AStarNode) o).getBoard()));
    }

	@Override
	public int compareTo(AStarNode other) {
		return Integer.compare(this.fValue, other.fValue); //ascending to get the lowest
	}
	
	@Override
	public int hashCode() {
		int hash = 0;
		
		// Bad hash, a lot of collisions
//		for(int c : board.getCars().keySet()) {
//			hash = Constants.HASHCODE_PRIME * hash + board.getCars().get(c).hashCode();
//		}
		
		// Better hash and no collision
		for (int i = 0; i < 6; i++)
	        for (int j = 0; j < 6; j++)
	            hash = (hash) * Settings.HASHCODE_PRIME + board.getGameBoard()[i][j];

	    return hash;
	}
}