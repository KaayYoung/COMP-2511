
public class State implements Comparable<State> {
	private Board currentBoard;
	private int pathCostG;
	private int pathCostF;
	private int pathCostH;
	
	public State (Board currentBoard) {
		this.currentBoard = currentBoard;
		this.pathCostG = 0;
		this.pathCostF = 0;
		this.pathCostH = 0;
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
	
	public void setPathCostF(int pathCostF) {
		this.pathCostF = pathCostF;
	}
	
	public int getPathCostH() {
		return pathCostH;
	}
	
	public void setPathCostH(int pathCostH) {
		this.pathCostH = pathCostH;
	}
}