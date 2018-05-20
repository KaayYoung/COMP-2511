package gridlock;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import search.Heuristic;
import search.HeuristicCarsInfront;
import search.SearchAlgorithm;
import search.SearchAlgorithmAStar;
import settings.Settings;

public class BoardGenerator {
//	public void main (String args[]) {
//		// Generate Boards
//		int nSolutions = 35;
//		
//		for (int i = 1; i <= 10; i++) {
//			Heuristic JC = new HeuristicCarsInfront();
//			SearchAlgorithm searchAStar = new SearchAlgorithmAStar(JC);
//			Board randomBoard = generate(ThreadLocalRandom.current().nextInt(7, 10));
//			List<Board> randomBoardSolutions = searchAStar.getPath(randomBoard);
//			while (randomBoardSolutions == null || randomBoardSolutions.size() != nSolutions) {
//				randomBoard = BoardGenerator.generate(ThreadLocalRandom.current().nextInt(7, 10));
//				randomBoardSolutions = searchAStar.getPath(randomBoard);
//			}
//			BoardIO.printBoardToFile("puzzles/hard"+i, randomBoard, randomBoardSolutions);
//			nSolutions++;
//		}
//	}
	
	public static Board generate(int maxCar){
		Board newBoard = new Board(null);
		int numCars = 0;
		
		// Put Red Car in row 2 with length 2
		Car redCar = new Car(Settings.RED_CAR_ID, Settings.RED_CAR_ROW, ThreadLocalRandom.current().nextInt(0, Settings.BOARD_SIZE - 1), Settings.RED_CAR_LENGTH, Car.Direction.HORIZONTAL);
		newBoard.addCar(redCar);
		
		int carId = 2;
		while (numCars < maxCar - 1) {
			// Randomize Row
			int randomRow = ThreadLocalRandom.current().nextInt(0, Settings.BOARD_SIZE);
			
			// Randomize Col
			int randomCol = ThreadLocalRandom.current().nextInt(0, Settings.BOARD_SIZE);
			
			// Randomize Length
			int randomLength = ThreadLocalRandom.current().nextInt(Settings.CAR_MIN_LENGTH, Settings.CAR_MAX_LENGTH + 1);
			
			// Randomize Direction
			Car.Direction randomDirection = Car.Direction.HORIZONTAL;
			int d = ThreadLocalRandom.current().nextInt(0, 1 + 1);
			if (d == 1) {
				randomDirection = Car.Direction.VERTICAL;
			}
			else {
				while (randomRow == 2) {
					randomRow = ThreadLocalRandom.current().nextInt(0, Settings.BOARD_SIZE);
				}	
			}

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
			
			// Check out of bounds
//			if ((randomDirection == Direction.VERTICAL && (randomRow + 1 - randomLength < 0))
//			 ||	(randomDirection == Direction.HORIZONTAL && (randomCol + randomLength > Constants.BOARD_SIZE))){
//				 continue;
//			}
			
			Car newCar;
			
			// Other problems like out of bounds, invalid Col/Row/Length ...
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
}