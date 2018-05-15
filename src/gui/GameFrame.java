package gui;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gridlock.Board;
import gridlock.BoardIO;
import settings.Settings;

import javax.swing.JButton;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.CardLayout;

import javax.swing.JLayeredPane;
import java.awt.Rectangle;
import java.awt.FlowLayout;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = -5739699646629372557L;
	
	private enum Difficulty {
		EASY,
		MEDIUM,
		HARD
	}
	private JPanel contentPane;
	private Difficulty difficulty = Difficulty.EASY;
	
	CardLayout cl = new CardLayout();
	JPanel MainMenuPanel = new JPanel();
	JPanel DifficultyPanel = new JPanel();
	JPanel GamePanel = new JPanel();
	JPanel GameBoard = new JPanel();
	JLabel lblMovesscore;
	JLabel lblHighscorescore;
	JLabel lblDifficultyscore;
	JLabel lblYourScore;
	JLabel lblBestScore;
	
	private static ArrayList<JLabel> carList = new ArrayList<JLabel>();
	private static ArrayList<MoveComponent> moveList = new ArrayList<MoveComponent>();
	private int moves = 0;

	/**
	 * Create the frame.
	 */
	public GameFrame() {
		setTitle("Gridlock 1.0");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, Settings.UI_FRAME_WIDTH, Settings.UI_FRAME_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(cl);
		
		contentPane.add(MainMenuPanel, "mainmenu");
		cl.show(contentPane, "mainmenu");
		
		MainMenuPanel.setLayout(new BoxLayout(MainMenuPanel, BoxLayout.PAGE_AXIS));
		
		JPanel MainMenuTitle = new JPanel();
		MainMenuPanel.add(MainMenuTitle);
		MainMenuTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblGridlock = new JLabel("GridLock");
		MainMenuTitle.add(lblGridlock);
		lblGridlock.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		lblGridlock.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblGridlock.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel MainMenuButtons = new JPanel();
		MainMenuPanel.add(MainMenuButtons);
		MainMenuPanel.add(Box.createVerticalGlue());
		
		MainMenuButtons.setLayout(new BoxLayout(MainMenuButtons, BoxLayout.Y_AXIS));
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnNewGame.setMaximumSize(new Dimension(300, 100));
		btnNewGame.setMinimumSize(new Dimension(300, 100));
		btnNewGame.setPreferredSize(new Dimension(300, 100));
		btnNewGame.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				 cl.show(contentPane, "difficulty");
			}
		});
		btnNewGame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		MainMenuButtons.add(btnNewGame);
		btnNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		MainMenuButtons.add(Box.createVerticalStrut(20));
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnSettings.setPreferredSize(new Dimension(300, 100));
		btnSettings.setMinimumSize(new Dimension(300, 100));
		btnSettings.setMaximumSize(new Dimension(300, 100));
		btnSettings.setSize(new Dimension(300, 100));
		MainMenuButtons.add(btnSettings);
		btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		MainMenuButtons.add(Box.createVerticalStrut(20));
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnQuit.setMaximumSize(new Dimension(300, 100));
		btnQuit.setPreferredSize(new Dimension(300, 100));
		MainMenuButtons.add(btnQuit);
		btnQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		contentPane.add(DifficultyPanel, "difficulty");
		DifficultyPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel DifficultyButtons = new JPanel();
		DifficultyPanel.add(DifficultyButtons, BorderLayout.CENTER);
		DifficultyButtons.setLayout(new BoxLayout(DifficultyButtons, BoxLayout.PAGE_AXIS));
		
		DifficultyButtons.add(Box.createVerticalGlue());
		
		JButton btnEasy = new JButton("Easy");
		btnEasy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetMoves();
				updateDifficulty(Difficulty.EASY);
				Board newBoard = BoardIO.loadBoardFromFile("puzzles/easy7.txt");
		        CarCreate car = new CarCreate(newBoard);
		        car.createCarList();
		        carList = car.getCarList();
		        moveList = car.getMoveList();
		        for(int i = 0; i < carList.size(); i++) {
		            JLabel c = carList.get(i);
		            GameBoard.add(c);
		        }

		        for(int i = 0; i < moveList.size(); i++) {
		            MoveComponent c = moveList.get(i);
		            c.setCarList(carList);
		        }
				cl.show(contentPane, "game");
			}
		});
		btnEasy.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnEasy.setPreferredSize(new Dimension(300, 100));
		btnEasy.setMinimumSize(new Dimension(300, 100));
		btnEasy.setMaximumSize(new Dimension(300, 100));
		btnEasy.setAlignmentX(Component.CENTER_ALIGNMENT);
		DifficultyButtons.add(btnEasy);
		
		DifficultyButtons.add(Box.createVerticalStrut(20));
		
		JButton btnMedium = new JButton("Medium");
		btnMedium.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetMoves();
				updateDifficulty(Difficulty.MEDIUM);
				Board newBoard = BoardIO.loadBoardFromFile("puzzles/medium7.txt");
		        CarCreate car = new CarCreate(newBoard);
		        car.createCarList();
		        carList = car.getCarList();
		        moveList = car.getMoveList();
		        
		        for(int i = 0; i < carList.size(); i++) {
		            JLabel c = carList.get(i);
		            GameBoard.add(c);
		        }

		        for(int i = 0; i < moveList.size(); i++) {
		            MoveComponent c = moveList.get(i);
		            c.setCarList(carList);
		        }
				cl.show(contentPane, "game");
			}
		});
		btnMedium.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnMedium.setMinimumSize(new Dimension(300, 100));
		btnMedium.setMaximumSize(new Dimension(300, 100));
		btnMedium.setPreferredSize(new Dimension(30, 100));
		btnMedium.setAlignmentX(Component.CENTER_ALIGNMENT);
		DifficultyButtons.add(btnMedium);
		
		DifficultyButtons.add(Box.createVerticalStrut(20));
		
		JButton btnHard = new JButton("Hard");
		btnHard.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetMoves();
				updateDifficulty(Difficulty.HARD);
				Board newBoard = BoardIO.loadBoardFromFile("puzzles/hard7.txt");
		        CarCreate car = new CarCreate(newBoard);
		        car.createCarList();
		        carList = car.getCarList();
		        moveList = car.getMoveList();
		        
		        for(int i = 0; i < carList.size(); i++) {
		            JLabel c = carList.get(i);
		            GameBoard.add(c);
		        }

		        for(int i = 0; i < moveList.size(); i++) {
		            MoveComponent c = moveList.get(i);
		            c.setCarList(carList);
		        }
				cl.show(contentPane, "game");
			}
		});
		btnHard.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnHard.setPreferredSize(new Dimension(300, 100));
		btnHard.setMaximumSize(new Dimension(300, 100));
		btnHard.setMinimumSize(new Dimension(300, 100));
		btnHard.setAlignmentX(Component.CENTER_ALIGNMENT);
		DifficultyButtons.add(btnHard);
		
		DifficultyButtons.add(Box.createVerticalStrut(40));
		
		JButton btnBackToMain = new JButton("Back to Main Menu");
		btnBackToMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameBoard.removeAll();
				resetMoves();
				cl.show(contentPane, "mainmenu");
			}
		});
		btnBackToMain.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnBackToMain.setPreferredSize(new Dimension(150, 50));
		btnBackToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
		DifficultyButtons.add(btnBackToMain);
		
		DifficultyButtons.add(Box.createVerticalGlue());
		GamePanel.setMaximumSize(new Dimension(1, 32767));
		GamePanel.setOpaque(false);
		
		contentPane.add(GamePanel, "game");
		GamePanel.setLayout(new BoxLayout(GamePanel, BoxLayout.X_AXIS));
		
		JLayeredPane GameBoardLayeredPane = new JLayeredPane();
		GamePanel.add(GameBoardLayeredPane);
		GameBoard.setBounds(1, 1, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE);
		GameBoardLayeredPane.setLayer(GameBoard, 1);
		GameBoardLayeredPane.add(GameBoard);
		
		GameBoard.setPreferredSize(new Dimension(Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE));
		GameBoard.setMinimumSize(new Dimension(Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE));
		GameBoard.setMaximumSize(new Dimension(Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE));
		GameBoard.setLayout(null);
		GameBoard.setOpaque(false);
		
		Grid Grid = new Grid();
		Grid.setBounds(new Rectangle(0, 0, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE + 1, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE + 1));
		GameBoardLayeredPane.setLayer(Grid, 0);
		GameBoardLayeredPane.add(Grid);
		
		JPanel GameButtons = new JPanel();
		GameButtons.setMaximumSize(new Dimension(100, 600));
		GamePanel.add(GameButtons);
		GameButtons.setLayout(new BorderLayout(0, 0));
		
		JPanel GameInfo = new JPanel();
		GameInfo.setMaximumSize(new Dimension(100, 300));
		GameButtons.add(GameInfo, BorderLayout.NORTH);
		GameInfo.setLayout(new BorderLayout(0, 0));
		
		JPanel GameScoreLabels = new JPanel();
		GameInfo.add(GameScoreLabels, BorderLayout.WEST);
		GameScoreLabels.setLayout(new BoxLayout(GameScoreLabels, BoxLayout.Y_AXIS));
		
		JLabel lblDifficulty = new JLabel("Difficulty: ");
		GameScoreLabels.add(lblDifficulty);
		
		JLabel lblMoves = new JLabel("Moves: ");
		GameScoreLabels.add(lblMoves);
		
		JLabel lblHighscore = new JLabel("Highscore: ");
		GameScoreLabels.add(lblHighscore);
		
		JPanel GameScore = new JPanel();
		GameInfo.add(GameScore, BorderLayout.CENTER);
		GameScore.setLayout(new BoxLayout(GameScore, BoxLayout.Y_AXIS));
		
		lblDifficultyscore = new JLabel("Easy");
		lblDifficultyscore.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GameScore.add(lblDifficultyscore);
		
		lblMovesscore = new JLabel("0");
		lblMovesscore.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GameScore.add(lblMovesscore);
		
		lblHighscorescore = new JLabel("0");
		lblHighscorescore.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GameScore.add(lblHighscorescore);
		
		JButton btnHint = new JButton("Hint");
		btnHint.setPreferredSize(new Dimension(150, 25));
		btnHint.setMinimumSize(new Dimension(150, 25));
		btnHint.setMaximumSize(new Dimension(150, 25));
		GameInfo.add(btnHint, BorderLayout.SOUTH);
		
		JPanel GameNavigationButtons = new JPanel();
		GameButtons.add(GameNavigationButtons, BorderLayout.SOUTH);
		GameNavigationButtons.setLayout(new BoxLayout(GameNavigationButtons, BoxLayout.X_AXIS));
		
		JButton btnMainMenu = new JButton("Main Menu");
		btnMainMenu.setMaximumSize(new Dimension(150, 25));
		btnMainMenu.setHorizontalTextPosition(SwingConstants.CENTER);
		GameNavigationButtons.add(btnMainMenu);
		btnMainMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameBoard.removeAll();
				resetMoves();
				cl.show(contentPane, "mainmenu");
			}
		});
		btnMainMenu.setPreferredSize(new Dimension(150, 50));
		btnMainMenu.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnMainMenu.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel GameOverPanel = new JPanel();
		contentPane.add(GameOverPanel, "gameover");
		GameOverPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel GameOverTitle = new JPanel();
		GameOverPanel.add(GameOverTitle, BorderLayout.NORTH);
		
		JLabel lblGameOver = new JLabel("Game Over");
		lblGameOver.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		GameOverTitle.add(lblGameOver);
		
		JPanel GameOverScore = new JPanel();
		GameOverPanel.add(GameOverScore, BorderLayout.CENTER);
		GameOverScore.setLayout(new BoxLayout(GameOverScore, BoxLayout.Y_AXIS));
		
		GameOverScore.add(Box.createVerticalStrut(50));
		
		JPanel GameOverCongrats = new JPanel();
		GameOverScore.add(GameOverCongrats);
		GameOverCongrats.setLayout(new BoxLayout(GameOverCongrats, BoxLayout.X_AXIS));
		
		JLabel lblCongratulationYouHave = new JLabel("Congratulation! You have solved the puzzle.");
		GameOverCongrats.add(lblCongratulationYouHave);
		lblCongratulationYouHave.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		lblCongratulationYouHave.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		GameOverScore.add(Box.createVerticalStrut(50));
		
		JPanel GameOverScoreInfo = new JPanel();
		GameOverScore.add(GameOverScoreInfo);
		GameOverScoreInfo.setLayout(new BoxLayout(GameOverScoreInfo, BoxLayout.X_AXIS));
		
		JPanel ScoreText = new JPanel();
		GameOverScoreInfo.add(ScoreText);
		ScoreText.setLayout(new BoxLayout(ScoreText, BoxLayout.Y_AXIS));
		
		JLabel lblYourMoves = new JLabel("Your moves: ");
		lblYourMoves.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		ScoreText.add(lblYourMoves);
		lblYourMoves.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel lblBestMoves = new JLabel("Best moves:");
		lblBestMoves.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		ScoreText.add(lblBestMoves);
		lblBestMoves.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel ScoreInfo = new JPanel();
		GameOverScoreInfo.add(ScoreInfo);
		ScoreInfo.setLayout(new BoxLayout(ScoreInfo, BoxLayout.Y_AXIS));
		
		lblYourScore = new JLabel("Your Score");
		lblYourScore.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		ScoreInfo.add(lblYourScore);
		
		lblBestScore = new JLabel("Best Score");
		lblBestScore.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		ScoreInfo.add(lblBestScore);
		
		Component verticalStrut = Box.createVerticalStrut(25);
		GameOverScore.add(verticalStrut);
		
		JButton btnBackToMain_1 = new JButton("Back to Main Menu");
		btnBackToMain_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		GameOverScore.add(btnBackToMain_1);
		btnBackToMain_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				GameBoard.removeAll();
				resetMoves();
				cl.show(contentPane, "mainmenu");
			}
		});
		btnBackToMain_1.setPreferredSize(new Dimension(300, 50));
		btnBackToMain_1.setMinimumSize(new Dimension(300, 50));
		btnBackToMain_1.setMaximumSize(new Dimension(300, 50));
		btnBackToMain_1.setFont(new Font("Segoe UI", Font.PLAIN, 30));
	}
	
	public void updateMoves(int moves) {
	     this.moves += moves;
	     lblMovesscore.setText(Integer.toString(this.moves));
	}
	
	public void resetMoves() {
	     this.moves = 0;
	     lblMovesscore.setText(Integer.toString(this.moves));
	}
	
	public void updateDifficulty(Difficulty difficulty) {
		this.setDifficulty(difficulty);
		if (difficulty == Difficulty.EASY) {
			lblDifficultyscore.setText("Easy");
		}
		else if (difficulty == Difficulty.MEDIUM) {
			lblDifficultyscore.setText("Medium");
		}
		else if (difficulty == Difficulty.MEDIUM) {
			lblDifficultyscore.setText("Hard");
		}
		
	}
	
	public void gameOver() {
		cl.show(contentPane, "gameover");
		lblYourScore.setText(Integer.toString(this.moves));
		lblBestScore.setText(Integer.toString(this.moves));
	}

	/**
	 * @return the difficulty
	 */
	public Difficulty getDifficulty() {
		return difficulty;
	}

	/**
	 * @param difficulty the difficulty to set
	 */
	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}
}
