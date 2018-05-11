package Controller.Search;

import java.util.ArrayList;
import java.util.List;

import Controller.Board;

public class AStarNode implements Comparable<AStarNode>{
    public final Board board;
    public AStarNode parent;
    public int gValue;
    public int hValue;
    public int fValue;
    public final int MOVEMENT_COST = 1; // Each car move counts as 1

    public AStarNode(Board b) {
        this.board = b;
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
        return hValue;
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
    
    public boolean equals(Object o) {
		return getBoard().equals(((AStarNode) o).getBoard());
    }

	@Override
	public int compareTo(AStarNode other) {
		return Integer.compare(this.getFValue(), other.getFValue()); //ascending to get the lowest
	}
}