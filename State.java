package search;

import java.util.ArrayList;

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
	
	
	public ArrayList<moves> reconstruct_path()
	{
		ArrayList<moves> reverse = new ArrayList<moves>();
		State cur = this;
		int i = 0;
		while(cur.getPreState()!=null)
		{
			reverse.add(cur.getMove());
			System.out.println();
			cur.currentBoard.printBoard();
//			System.out.println();
//			System.out.println("current move is " + i);
//			System.out.println("car ID is " + cur.getMove().getId());
//			System.out.println("car dir is " + cur.getMove().getDir());
//			System.out.println("num of move is " + cur.getMove().numofMove());
			cur = cur.getPreState();
			i++;
		}
		System.out.println("totoal number of moves is "+ i);
		ArrayList<moves> path = new ArrayList<moves>();
		while(i>0)
		{
			i--;
			path.add(reverse.get(i));
//			System.out.println();
//			System.out.println("current move is " + i);
//			System.out.println("car ID is " + m.getId());
//			System.out.println("car dir is " + m.getDir());
//			System.out.println("num of move is " + m.numofMove());
			
			//i--;
		}
		
		return path;
		
	}
}