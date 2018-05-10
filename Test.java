package search;

import java.util.ArrayList;
import java.util.HashMap;

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
		SearchAlgo search = new AStar();
		ArrayList<moves> path = new ArrayList<moves>();
		path = search.getPath(gBoard);
		//System.out.println(path.size());
		for(moves m: path)
		{
			System.out.println();
			System.out.println("current move is");
			System.out.println("car ID is " + m.getId());
			System.out.println("car dir is " + m.getDir());
			System.out.println("num of move is " + m.numofMove());
		}
		
//		System.out.println(path.get(1));
	}
	

	}

