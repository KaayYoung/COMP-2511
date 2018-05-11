package View;

import java.util.HashMap;
import Controller.Board;
import Controller.Car;
import Controller.Car.Direction;
import Controller.Search.Heuristic;
import Controller.Search.HeuristicCarsInfront;
import Controller.Search.SearchAlgorithm;
import Controller.Search.SearchAlgorithmAStar;
import Controller.Search.SearchAlgorithmDFS;

public class Test {
	public static void main(String[] args) {
		// 83-move configuration
		Car car1 = new Car(1, 2, 3, 2, Direction.HORIZONTAL);
		Car car2 = new Car(2, 2, 2, 3, Direction.VERTICAL);
		Car car3 = new Car(3, 0, 3, 2, Direction.HORIZONTAL);
		Car car4 = new Car(4, 2, 5, 3, Direction.VERTICAL);
		Car car5 = new Car(5, 3, 4, 2, Direction.HORIZONTAL);
		Car car6 = new Car(6, 5, 3, 3, Direction.VERTICAL);
		Car car7 = new Car(7, 5, 4, 2, Direction.HORIZONTAL);
		Car car8 = new Car(8, 5, 0, 2, Direction.VERTICAL);
		Car car9 = new Car(9, 4, 1, 2, Direction.HORIZONTAL);
		
		HashMap<Integer, Car> cars = new HashMap<Integer, Car>();
		
		cars.put(car1.getCarId(), car1);
		cars.put(car2.getCarId(), car2);
		cars.put(car3.getCarId(), car3);
		cars.put(car4.getCarId(), car4);
		cars.put(car5.getCarId(), car5);
		cars.put(car6.getCarId(), car6);
		cars.put(car7.getCarId(), car7);
		cars.put(car8.getCarId(), car8);
		cars.put(car9.getCarId(), car9);
		
		// 7-move configuration
//		Car car1 = new Car(1, 2, 2, 2, Direction.HORIZONTAL);
//		Car car2 = new Car(2, 3, 1, 2, Direction.VERTICAL);
//		Car car3 = new Car(3, 5, 1, 2, Direction.VERTICAL);
//		Car car4 = new Car(4, 4, 2, 2, Direction.VERTICAL);
//		Car car5 = new Car(5, 5, 3, 2, Direction.HORIZONTAL);
//		Car car6 = new Car(6, 2, 4, 3, Direction.VERTICAL);
//		Car car7 = new Car(7, 2, 5, 2, Direction.VERTICAL);
//		
//		HashMap<Integer, Car> cars = new HashMap<Integer, Car>();
//		
//		cars.put(1, car1);
//		cars.put(2, car2);
//		cars.put(3, car3);
//		cars.put(4, car4);
//		cars.put(5, car5);
//		cars.put(6, car6);
//		cars.put(7, car7);
		
		Board gBoard = new Board(cars);
		
		gBoard.printBoard();
		
		// Search performance comparison
		SearchAlgorithm searchAStar = new SearchAlgorithmAStar();
		long startTimeAStar = System.currentTimeMillis();
		Heuristic JC = new HeuristicCarsInfront(); // TODO: Add more heuristic functions
		searchAStar.getPath(gBoard, JC);
		long endTimeAStar = System.currentTimeMillis();
		long durationAStar = (endTimeAStar - startTimeAStar);
		System.out.println("AStarSearch time: " + durationAStar + " milliseconds.");
		
		SearchAlgorithm searchDFS = new SearchAlgorithmDFS();
		long startTimeDFS = System.currentTimeMillis();
		searchDFS.getPath(gBoard, null);
		long endTimeDFS = System.currentTimeMillis();
		long durationDFS = (endTimeDFS - startTimeDFS);
		System.out.println("DFSSearch time: " + durationDFS + " milliseconds.");
	}

}