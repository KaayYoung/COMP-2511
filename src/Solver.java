
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import gridlock.Board;
import gridlock.Car;
import gridlock.Car.Direction;
import search.Heuristic;
import search.HeuristicCarsInfront;
import search.SearchAlgorithm;
import search.SearchAlgorithmAStar;
import search.SearchAlgorithmBFS;

public class Solver {
	public static void main(String args[]) {
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
		
		Map<Integer, Car> cars = Collections.synchronizedMap(new HashMap<Integer, Car>());
		
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
		
		// 37-move configuration
//		Car car1 = new Car(1, 2, 0, 2, Car.Direction.HORIZONTAL);
//		Car car2 = new Car(2, 1, 1, 2, Car.Direction.VERTICAL);
//		Car car3 = new Car(3, 0, 2, 2, Car.Direction.HORIZONTAL);
//		Car car4 = new Car(4, 1, 2, 2, Car.Direction.HORIZONTAL);
//		Car car5 = new Car(5, 1, 4, 2, Car.Direction.VERTICAL);
//		Car car6 = new Car(6, 1, 5, 2, Car.Direction.VERTICAL);
//		Car car7 = new Car(7, 3, 3, 2, Car.Direction.VERTICAL);
//		Car car8 = new Car(8, 3, 4, 2, Car.Direction.VERTICAL);
//		Car car9 = new Car(9, 3, 5, 2, Car.Direction.VERTICAL);
//		Car car10 = new Car(10, 5, 3, 2, Car.Direction.VERTICAL);
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
//		cars.put(8, car8);
//		cars.put(9, car9);
//		cars.put(10, car10);
		
		Board gBoard = new Board(cars);
		gBoard.printBoard();
		
		// Search performance comparison
		Heuristic JC = new HeuristicCarsInfront();
		SearchAlgorithm searchAStar = new SearchAlgorithmAStar(JC);
		long startTimeAStar = System.currentTimeMillis();
		searchAStar.getPath(gBoard);
		long endTimeAStar = System.currentTimeMillis();
		long durationAStar = (endTimeAStar - startTimeAStar);
		System.out.println("AStarSearch time: " + durationAStar + " milliseconds.");

		SearchAlgorithm searchBFS = new SearchAlgorithmBFS();
		long startTimeBFS = System.currentTimeMillis();
		searchBFS.getPath(gBoard);
		long endTimeBFS = System.currentTimeMillis();
		long durationBFS = (endTimeBFS - startTimeBFS);
		System.out.println("BFSSearch time: " + durationBFS + " milliseconds.");
	}
}
