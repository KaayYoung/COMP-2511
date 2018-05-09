package search;


public class State implements Comparable<State> {
	private Board currentBoard;
	private State preState;
	private int pathCostG;
	private int pathCostF;
	private int pathCostH;
	private moves m;
	
	public State (Board currentBoard) {
		this.currentBoard = currentBoard;
		this.preState = null;
		this.pathCostG = 0;
		this.pathCostF = 0;
		this.pathCostH = 0;
		this.m = null;
	}

	public int compareTo(State currentState) {
		return this.getPathCostF() - currentState.getPathCostF();
	}
	
	public boolean isGoalState() {
		// TODO add conditions for goal state
		// if (currentBoard.hasExit()){
		// 		return true;
		// }
		return false;
	}
	
	public Board getCurrentBoard() {
		return currentBoard;
	}
	
	public int getPathCostG() {
		return pathCostG;
	}
	
	public void setPathCostG(int pathCostG) {
		this.pathCostG = pathCostG;
	}
	
	public int getPathCostF() {
		return pathCostF;
	}
	
	public void setPathCostF() {
		this.pathCostF = pathCostH + pathCostG;
	}
	
	public int getPathCostH() {
		return pathCostH;
	}
	public moves getMove()
	{
		return m;
	}
	public State getPreState()
	{
		return preState;
	}
	
	public void setPathCostH(int pathCostH) {
		this.pathCostH = pathCostH;
	}
	
	public void setMove(moves curmove){
		this.m = curmove;
	}
	
	public void setpreState(State pre)
	{
		this.preState = pre;
	}
}