package gridlock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import search.SearchAlgorithm;
import search.SearchAlgorithm.PrintMode;
import settings.Settings.Difficulty;

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
		fileStream.close();
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
	
	public static void printHighScoreToFile(String filename, Difficulty difficulty, int level, int score, String playerName) {
		boolean playerExists = false;
		Path filePath = FileSystems.getDefault().getPath(filename);
	
		try {
			List<String> fileContent = new ArrayList<>(Files.readAllLines(filePath, StandardCharsets.UTF_8));
			
			for (int i = 0; i < fileContent.size(); i++) {
				if (fileContent.get(i).contains(difficulty.name() + " " + Integer.toString(level) + " " + playerName)) {
					String a = difficulty.name() + " " + Integer.toString(level) + " " + playerName + " " + Integer.toString(score);
					fileContent.set(i, a);
					System.out.println(score);
					playerExists = true;
					break;
				}
			}
			
			if (!playerExists) {
				fileContent.add(difficulty.name() + " " + Integer.toString(level) + " " + playerName + " " + Integer.toString(score));
			}
			
			Files.write(filePath, fileContent, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public static List<Player> loadHighScoreFromFile(String filename, Difficulty difficulty, int level) {
		List<Player> highScore = new ArrayList<Player>();
		
		Scanner scan = null;
		
		try {
			scan = new Scanner(new FileReader(filename));
			while(scan.hasNext()) {
				String firstWord = scan.next();
				if (firstWord.equals(difficulty.name()) && scan.nextInt() == level) {
					String playerName = scan.next();
					int playerScore = scan.nextInt();
					Player newPlayer = new Player(playerName, playerScore);
					if (!highScore.contains(newPlayer)) {
						highScore.add(newPlayer);
					}
				} else {
					scan.nextLine();
				}
			} 
		} catch (Exception ex) {
            ex.printStackTrace();
        }
		
		List<Player> sortedHighScore = new ArrayList<Player>();
		
		for (Player p : highScore) {
			sortedHighScore.add(p);
		}
		
		Collections.sort(sortedHighScore, new Comparator<Player>() {
			@Override
			public int compare(Player o1, Player o2) {
				return Integer.compare(o1.getScore(), o2.getScore());
			}
		});
		
		return sortedHighScore;
	}
}
