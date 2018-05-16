package gridlock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;

import search.SearchAlgorithm;
import search.SearchAlgorithm.PrintMode;

public class BoardIO {
	public static void printBoardToFile(String filename, Board board, List<Board> solution) {
		PrintStream fileStream = null;
		try {
			fileStream = new PrintStream(filename+".txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		System.setOut(fileStream);
		
		System.out.println("#BOARD");
		board.printCars();
		System.out.println("#SOLUTION " + solution.size());
		SearchAlgorithm.printSolution(solution, PrintMode.PRINT_CARS);
	}
	
	@SuppressWarnings("resource")
	public static Board loadBoardFromFile(String filename) {
		Board board = new Board(null);
		
		try {

            Scanner input = new Scanner(System.in);

            File file = new File(filename);

            input = new Scanner(file);
            String line = input.nextLine();
            if (line.contains("#BOARD")) {
            	while (input.hasNextLine()) {
            		line = input.next();
            		if (line.contains("#SOLUTION")) {
            			break;
            		}
            		
	                int carId = Integer.parseInt(line);
	                int carRow = input.nextInt();
	                int carCol = input.nextInt();
	                int carLength = input.nextInt();
	                Car.Direction carDirection = Car.Direction.VERTICAL;
	                if (input.next().equals("HORIZONTAL")) {
	                	carDirection = Car.Direction.HORIZONTAL;
	                }
	                Car newCar = new Car(carId, carRow, carCol, carLength, carDirection);
	                board.addCar(newCar);	             
	            }
	            
	            input.close();
			}

        } catch (Exception ex) {
            ex.printStackTrace();
        }
		
		return board;
	}
}
