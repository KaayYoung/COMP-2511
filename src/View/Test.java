package View;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Controller.Board;
import Controller.Car;
import Controller.Car.Direction;
import Controller.Search.Heuristic;
import Controller.Search.HeuristicCarsInfront;
import Controller.Search.HeuristicFreeFromRedCar;
import Controller.Search.SearchAlgorithm;
import Controller.Search.SearchAlgorithmAStar;
import Controller.Search.SearchAlgorithmBFS;
import Controller.Search.SearchAlgorithm.PrintMode;
import Model.BoardGenerator;

public class Test {
	public static void main(String[] args) {
		// 83-move configuration
//		Car car1 = new Car(1, 2, 3, 2, Direction.HORIZONTAL);
//		Car car2 = new Car(2, 2, 2, 3, Direction.VERTICAL);
//		Car car3 = new Car(3, 0, 3, 2, Direction.HORIZONTAL);
//		Car car4 = new Car(4, 2, 5, 3, Direction.VERTICAL);
//		Car car5 = new Car(5, 3, 4, 2, Direction.HORIZONTAL);
//		Car car6 = new Car(6, 5, 3, 3, Direction.VERTICAL);
//		Car car7 = new Car(7, 5, 4, 2, Direction.HORIZONTAL);
//		Car car8 = new Car(8, 5, 0, 2, Direction.VERTICAL);
//		Car car9 = new Car(9, 4, 1, 2, Direction.HORIZONTAL);
//		
//		HashMap<Integer, Car> cars = new HashMap<Integer, Car>();
//		
//		cars.put(car1.getCarId(), car1);
//		cars.put(car2.getCarId(), car2);
//		cars.put(car3.getCarId(), car3);
//		cars.put(car4.getCarId(), car4);
//		cars.put(car5.getCarId(), car5);
//		cars.put(car6.getCarId(), car6);
//		cars.put(car7.getCarId(), car7);
//		cars.put(car8.getCarId(), car8);
//		cars.put(car9.getCarId(), car9);
		
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
//		Car car1 = new Car(1, 2, 0, 2, Direction.HORIZONTAL);
//		Car car2 = new Car(2, 1, 1, 2, Direction.VERTICAL);
//		Car car3 = new Car(3, 0, 2, 2, Direction.HORIZONTAL);
//		Car car4 = new Car(4, 1, 2, 2, Direction.HORIZONTAL);
//		Car car5 = new Car(5, 1, 4, 2, Direction.VERTICAL);
//		Car car6 = new Car(6, 1, 5, 2, Direction.VERTICAL);
//		Car car7 = new Car(7, 3, 3, 2, Direction.VERTICAL);
//		Car car8 = new Car(8, 3, 4, 2, Direction.VERTICAL);
//		Car car9 = new Car(9, 3, 5, 2, Direction.VERTICAL);
//		Car car10 = new Car(10, 5, 3, 2, Direction.VERTICAL);
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
//
//		Board gBoard = new Board(cars);
//		
//		gBoard.printBoard();
		
		
		// Search performance comparison
//		SearchAlgorithm searchAStar = new SearchAlgorithmAStar();
//		long startTimeAStar = System.currentTimeMillis();
//		Heuristic JC = new HeuristicFreeFromRedCar();
//		Heuristic JC = new HeuristicCarsInfront();
//		List<Board> pathAStar = searchAStar.getPath(gBoard, JC);
//		long endTimeAStar = System.currentTimeMillis();
//		long durationAStar = (endTimeAStar - startTimeAStar);
//		System.out.println("AStarSearch time: " + durationAStar + " milliseconds.");
//		System.out.println(pathAStar.size());
		
//		SearchAlgorithm searchBFS = new SearchAlgorithmBFS();
//		long startTimeBFS = System.currentTimeMillis();
//		searchBFS.getPath(gBoard, null);
//		long endTimeBFS = System.currentTimeMillis();
//		long durationBFS = (endTimeBFS - startTimeBFS);
//		System.out.println("BFSSearch time: " + durationBFS + " milliseconds.");
		
		// Generate Boards
//		int nSolutions = 15;
//		
//		for (int i = 1; i <= 10; i++) {
//			SearchAlgorithm searchAStar = new SearchAlgorithmAStar();
//			Heuristic JC = new HeuristicCarsInfront();
//			Board randomBoard = BoardGenerator.generate(ThreadLocalRandom.current().nextInt(5, 8));
//			List<Board> randomBoardSolutions = searchAStar.getPath(randomBoard, JC);
//			while (randomBoardSolutions == null || randomBoardSolutions.size() != nSolutions) {
//				randomBoard = BoardGenerator.generate(ThreadLocalRandom.current().nextInt(5, 8));
//				randomBoardSolutions = searchAStar.getPath(randomBoard, JC);
//			}
//			BoardGenerator.printBoardToFile("puzzles/easy"+i, randomBoard, randomBoardSolutions);
//			nSolutions++;
//		}
//		
//		nSolutions = 25;
//		
//		for (int i = 1; i <= 10; i++) {
//			SearchAlgorithm searchAStar = new SearchAlgorithmAStar();
//			Heuristic JC = new HeuristicCarsInfront();
//			Board randomBoard = BoardGenerator.generate(ThreadLocalRandom.current().nextInt(5, 8));
//			List<Board> randomBoardSolutions = searchAStar.getPath(randomBoard, JC);
//			while (randomBoardSolutions == null || randomBoardSolutions.size() != nSolutions) {
//				randomBoard = BoardGenerator.generate(ThreadLocalRandom.current().nextInt(5, 8));
//				randomBoardSolutions = searchAStar.getPath(randomBoard, JC);
//			}
//			BoardGenerator.printBoardToFile("puzzles/medium"+i, randomBoard, randomBoardSolutions);
//			nSolutions++;
//		}
//		
//		nSolutions = 35;
//		
//		for (int i = 1; i <= 10; i++) {
//			SearchAlgorithm searchAStar = new SearchAlgorithmAStar();
//			Heuristic JC = new HeuristicCarsInfront();
//			Board randomBoard = BoardGenerator.generate(ThreadLocalRandom.current().nextInt(5, 8));
//			List<Board> randomBoardSolutions = searchAStar.getPath(randomBoard, JC);
//			while (randomBoardSolutions == null || randomBoardSolutions.size() != nSolutions) {
//				randomBoard = BoardGenerator.generate(ThreadLocalRandom.current().nextInt(5, 8));
//				randomBoardSolutions = searchAStar.getPath(randomBoard, JC);
//			}
//			BoardGenerator.printBoardToFile("puzzles/hard"+i, randomBoard, randomBoardSolutions);
//			nSolutions++;
//		}
		
//		int nSolutions = 52;
//		
//		for (int i = 8; i <= 10; i++) {
//			SearchAlgorithm searchAStar = new SearchAlgorithmAStar();
//			Heuristic JC = new HeuristicCarsInfront();
//			Board randomBoard = BoardGenerator.generate(ThreadLocalRandom.current().nextInt(5, 9));
//			List<Board> randomBoardSolutions = searchAStar.getPath(randomBoard, JC);
//			while (randomBoardSolutions == null || randomBoardSolutions.size() <= nSolutions) {
//				randomBoard = BoardGenerator.generate(ThreadLocalRandom.current().nextInt(5, 9));
//				randomBoardSolutions = searchAStar.getPath(randomBoard, JC);
//				
//				System.out.println(randomBoardSolutions.size());
//			}
//			BoardGenerator.printBoardToFile("puzzles/expert"+i, randomBoard, randomBoardSolutions);
//			nSolutions++;
//		}
		
		Board newBoard = BoardGenerator.loadBoardFromFile("../puzzles/easy1.txt");
		newBoard.printBoard();
	}

}