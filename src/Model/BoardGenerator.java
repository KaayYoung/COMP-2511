package Model;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Controller.Board;
import Controller.Car;
import Controller.Car.Direction;
import Controller.Search.SearchAlgorithm;
import Controller.Search.SearchAlgorithm.PrintMode;

public class BoardGenerator {
	public static Board generate(int maxCar){
		Board newBoard = new Board(null);
		int numCars = 0;
		
		// Put Red Car
		Car redCar = new Car(1, 2, ThreadLocalRandom.current().nextInt(0, Board.BOARD_SIZE - 1), 2, Car.Direction.HORIZONTAL);
		newBoard.addCar(redCar);
		
		int carId = 2;
		while (numCars < maxCar - 1) {
			// Randomize Row
			int randomRow = ThreadLocalRandom.current().nextInt(0, Board.BOARD_SIZE);
			
			// Randomize Col
			int randomCol = ThreadLocalRandom.current().nextInt(0, Board.BOARD_SIZE);
			
			// Randomize Length
			int randomLength = ThreadLocalRandom.current().nextInt(Car.MIN_LENGTH, Car.MAX_LENGTH + 1);
			
			// Randomize Direction
			Direction randomDirection = Car.Direction.HORIZONTAL;
			int d = ThreadLocalRandom.current().nextInt(0, 1 + 1);
			if (d == 1) {
				randomDirection = Car.Direction.VERTICAL;
			}
			else {
				while (randomRow == 2) {
					randomRow = ThreadLocalRandom.current().nextInt(0, Board.BOARD_SIZE);
				}	
			}
			
			// Check out of bounds
//			if ((randomDirection == Direction.VERTICAL && (randomRow + 1 - randomLength < 0))
//			 ||	(randomDirection == Direction.HORIZONTAL && (randomCol + randomLength > Board.BOARD_SIZE))){
//				 continue;
//			}
			
			// Check if there are 2 cars with length 3 in the same row/col
			if (randomLength == 3) {
				boolean obstruct = false;
				for (int key : newBoard.getCars().keySet()) {
					if (newBoard.getCars().get(key).getLength() == 3 && 
						(newBoard.getCars().get(key).getDirection() == Car.Direction.HORIZONTAL && newBoard.getCars().get(key).getPosRow() == randomRow)
					||  (newBoard.getCars().get(key).getDirection() == Car.Direction.VERTICAL && newBoard.getCars().get(key).getPosCol() == randomCol)) {
						obstruct = true;
						break;
					}
				}
				
				if (obstruct) {
					continue;
				}
			}
			
			Car newCar;
			
			try {
				newCar = new Car(carId, randomRow, randomCol, randomLength, randomDirection);
				if (newBoard.addCar(newCar)) {
					numCars++;
					carId++;
				}
			}
			catch (IllegalArgumentException e){
				continue;
			}	
			
		}
		
		
		return newBoard;
	}
	
	public static void printBoardToFile(String filename, Board board, List<Board> solutions) {
		PrintStream fileStream = null;
		try {
			fileStream = new PrintStream(filename+".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(fileStream);
		
		System.out.println("#BOARD");
		board.printCars();
		System.out.println("#SOLUTION " + solutions.size());
		SearchAlgorithm.printSolution(solutions, PrintMode.PRINT_CARS);
		
		PrintStream stdout = System.out;
		System.setOut(stdout);   
	}
}