package search;

public class HeuristicZero implements Heuristic{

	public int calculateHValue(AStarNode current) {
		return 0;
	}
}
