import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Test {
	public static void main(String[] args) {
		Car car1 = new Car(1, 3, 2, 2, 0);
		Car car2 = new Car(2, 4, 1, 2, 1);
		Car car3 = new Car(3, 6, 1, 2, 1);
		Car car4 = new Car(4, 5, 2, 2, 1);
		Car car5 = new Car(5, 6, 3, 2, 0);
		Car car6 = new Car(6, 3, 4, 3, 1);
		Car car7 = new Car(7, 3, 5, 2, 1);
		
		HashMap<Integer, Car> cars = new HashMap<Integer, Car>();
		
		cars.put(1, car1);
		cars.put(2, car2);
		cars.put(3, car3);
		cars.put(4, car4);
		cars.put(5, car5);
		cars.put(6, car6);
		cars.put(7, car7);
		
		Board gBoard = new Board(cars);
		
		gBoard.printBoard();
		System.out.println();
		
		System.out.println("SOLUTION:");
		bfs(gBoard);
	}
	
	public static void bfs(Board board) {
		Board frontBoard = new Board(board.getCars());
		ArrayList<Board> frontPath = new ArrayList<Board>();
		
		ArrayList<Board> discovered = new ArrayList<Board>();
		List<List<Board>> solutions = new ArrayList<List<Board>>();
		
		ArrayList<Board> moves = new ArrayList<Board>();
		Map<Integer, Integer> depthStates = new HashMap<Integer, Integer>();
		
		Queue<Board> boardQueue = new LinkedList<Board>();
		boardQueue.add(frontBoard);
		
		Queue<ArrayList<Board>> pathQueue = new LinkedList<ArrayList<Board>>();
		ArrayList<Board> newPath = new ArrayList<Board>();
		
		while (boardQueue.size() != 0) {
		
			frontBoard = boardQueue.poll();
			
			if (pathQueue.size() != 0) {
				frontPath = new ArrayList<Board>(pathQueue.poll());
			}

			frontPath.add(frontBoard);
			newPath = frontPath;
			
			if (!depthStates.containsKey(newPath.size())) depthStates.put(newPath.size(), 1);
			else depthStates.put(newPath.size(), depthStates.get(newPath.size()) + 1);
			
			boolean found = false;
			for (Board b : discovered) {
				if (b.equals(frontBoard)) {
					found = true;
					break;
				}
			}
			
			if (found) {
				continue;
			}
			else {
				discovered.add(frontBoard);
			}
			
			if (frontBoard.isSolved()) {
				solutions.add(newPath);
			}
			else {
				moves = frontBoard.getPossibleMoves();
				for (Board b : moves) {
					boardQueue.add(b);
					pathQueue.add(newPath);
				}
			}
		}
		
		int maxNodes = 0;
		for (int i = 0; i < depthStates.size(); i++) {
			if (depthStates.get(i) != null) {
				maxNodes += depthStates.get(i);
			}
		}
		
		int cycleCount = 0;
		for (int i = 0; i < solutions.size(); i++) {
			for (int j = 0; j < solutions.size(); j++) {
				if (i != j && solutions.get(i).get(solutions.get(i).size() - 1) == solutions.get(j).get(solutions.get(j).size() - 1)) {
					cycleCount++;
				}
			}
		}
		
		List<Board> shortestSolution = new ArrayList<Board>();
		if (solutions.size() != 0) {
			shortestSolution = solutions.get(0);
			for (int i = 1; i < solutions.size(); i++) {
				if (solutions.get(i).size() < shortestSolution.size()) {
					shortestSolution = solutions.get(i);
				}
			}
			
			for (int i = 1; i < shortestSolution.size(); i++) {
//				for(int j = 1; j < shortestSolution.size(); j++) {
//					Car currentCar = shortestSolution.get(i).getCars().get(j);
//					System.out.println(currentCar.getCarId()+" "+currentCar.getPosRow()+" "+currentCar.getPosCol()+" "+currentCar.getLength()+" "+currentCar.getDirection());
					shortestSolution.get(i).printBoard();
					System.out.println();
//				}
			}
		}
		
		System.out.println("TOTAL SOLUTIONS: "+solutions.size());
		System.out.println("TOTAL CYCLES: "+cycleCount);
		System.out.println("MOVE # OF THE SHORTEST SOLUTION: "+(shortestSolution.size() - 1));
		System.out.println("TOTAL NODES GENERATED: "+discovered.size());
		System.out.println("MAXIMUM # OF NODES KEPT IN MEMORY: "+maxNodes);
	}
}
