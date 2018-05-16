package gui;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gridlock.Board;
import gridlock.BoardIO;
import gridlock.Player;
import search.Heuristic;
import search.HeuristicCarsInfront;
import search.SearchAlgorithm;
import search.SearchAlgorithmAStar;
import settings.Settings;
import settings.Settings.Difficulty;

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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.CardLayout;

import javax.swing.JLayeredPane;
import java.awt.Rectangle;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = -5739699646629372557L;
	
	private JPanel contentPane;
	private JPanel GameOverScoreInfo;
	private Difficulty difficulty = Difficulty.EASY;
	private int level = 0;
	
	CardLayout cl = new CardLayout();
	CardLayout cl_gameover = new CardLayout();
	JPanel MainMenuPanel = new JPanel();
	JPanel DifficultyPanel = new JPanel();
	JPanel GamePanel = new JPanel();
	JPanel GameBoard = new JPanel();
	JPanel GameHintLoading = new JPanel();
	JLabel lblMovesscore;
	JLabel lblHighscorescore;
	JLabel lblDifficultyscore;
	JLabel lblYourScore;
	JLabel lblLevellevel;
	JPanel GameOverHighScore;
	JPanel SelectLevelButtons;
	
	private static ArrayList<JLabel> carList = new ArrayList<JLabel>();
	private static ArrayList<MoveComponent> moveList = new ArrayList<MoveComponent>();
	private int moves = 0;
	private boolean hasContinue = false;
	private Board board;
	private JTextField txtEnterYourName;
	
	private List<Player> highScore;

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
		btnNewGame.setMaximumSize(new Dimension(300, 75));
		btnNewGame.setMinimumSize(new Dimension(300, 75));
		btnNewGame.setPreferredSize(new Dimension(300, 75));
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
		
		JButton btnContinue = new JButton("Continue");
		btnContinue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if (hasContinue) {
					cl.show(contentPane, "game");
				}
			}
		});
		btnContinue.setEnabled(false);
		btnContinue.setPreferredSize(new Dimension(300, 75));
		btnContinue.setMinimumSize(new Dimension(300, 75));
		btnContinue.setMaximumSize(new Dimension(300, 75));
		btnContinue.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnContinue.setAlignmentX(Component.CENTER_ALIGNMENT);
		MainMenuButtons.add(btnContinue);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		MainMenuButtons.add(verticalStrut_1);
		
		JButton btnSettings = new JButton("Settings");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cl.show(contentPane, "settings");
			}
		});
		btnSettings.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnSettings.setPreferredSize(new Dimension(300, 75));
		btnSettings.setMinimumSize(new Dimension(300, 75));
		btnSettings.setMaximumSize(new Dimension(300, 75));
		btnSettings.setSize(new Dimension(300, 100));
		MainMenuButtons.add(btnSettings);
		btnSettings.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		MainMenuButtons.add(Box.createVerticalStrut(20));
		
		JButton btnQuit = new JButton("Quit");
		btnQuit.setMinimumSize(new Dimension(300, 75));
		btnQuit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		btnQuit.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnQuit.setMaximumSize(new Dimension(300, 75));
		btnQuit.setPreferredSize(new Dimension(300, 75));
		MainMenuButtons.add(btnQuit);
		btnQuit.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel SelectLevelPanel = new JPanel();
		contentPane.add(SelectLevelPanel, "levelselect");
		SelectLevelPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel SelectLevelTitle = new JPanel();
		SelectLevelPanel.add(SelectLevelTitle, BorderLayout.NORTH);
		SelectLevelTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblSelectLevel = new JLabel("Select Level");
		lblSelectLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectLevel.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		lblSelectLevel.setAlignmentX(0.5f);
		SelectLevelTitle.add(lblSelectLevel);
		
		JPanel SelectLevelBackButtons = new JPanel();
		SelectLevelPanel.add(SelectLevelBackButtons, BorderLayout.SOUTH);
		SelectLevelBackButtons.setLayout(new BoxLayout(SelectLevelBackButtons, BoxLayout.X_AXIS));
		
		Component horizontalGlue = Box.createHorizontalGlue();
		SelectLevelBackButtons.add(horizontalGlue);
		
		JButton btnBackToDifficulty = new JButton("Back to Difficulty");
		btnBackToDifficulty.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SelectLevelButtons.removeAll();
				cl.show(contentPane, "difficulty");
			}
		});
		btnBackToDifficulty.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnBackToDifficulty.setPreferredSize(new Dimension(300, 50));
		btnBackToDifficulty.setMinimumSize(new Dimension(300, 50));
		btnBackToDifficulty.setMaximumSize(new Dimension(300, 50));
		SelectLevelBackButtons.add(btnBackToDifficulty);
		
		Component horizontalStrut = Box.createHorizontalStrut(50);
		SelectLevelBackButtons.add(horizontalStrut);
		
		JButton button = new JButton("Back to Main Menu");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SelectLevelButtons.removeAll();
				cl.show(contentPane, "mainmenu");
			}
		});
		button.setPreferredSize(new Dimension(300, 50));
		button.setMinimumSize(new Dimension(300, 50));
		button.setMaximumSize(new Dimension(300, 50));
		button.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		button.setAlignmentX(0.5f);
		SelectLevelBackButtons.add(button);
		
		Component horizontalGlue_1 = Box.createHorizontalGlue();
		SelectLevelBackButtons.add(horizontalGlue_1);
		
		SelectLevelButtons = new JPanel();
		SelectLevelPanel.add(SelectLevelButtons, BorderLayout.CENTER);
		SelectLevelButtons.setLayout(new BoxLayout(SelectLevelButtons, BoxLayout.X_AXIS));
		
		contentPane.add(DifficultyPanel, "difficulty");
		DifficultyPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel DifficultyBackButtons = new JPanel();
		DifficultyPanel.add(DifficultyBackButtons, BorderLayout.SOUTH);
		
		JButton btnDifficultyBackToMain = new JButton("Back to Main Menu");
		btnDifficultyBackToMain.setMinimumSize(new Dimension(300, 50));
		btnDifficultyBackToMain.setMaximumSize(new Dimension(300, 50));
		DifficultyBackButtons.add(btnDifficultyBackToMain);
		btnDifficultyBackToMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetGame();
				cl.show(contentPane, "mainmenu");
			}
		});
		btnDifficultyBackToMain.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnDifficultyBackToMain.setPreferredSize(new Dimension(300, 50));
		btnDifficultyBackToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JPanel DifficultyButtons = new JPanel();
		DifficultyPanel.add(DifficultyButtons);
		DifficultyButtons.setLayout(new BoxLayout(DifficultyButtons, BoxLayout.PAGE_AXIS));
		
		DifficultyButtons.add(Box.createVerticalGlue());
		
		JButton btnEasy = new JButton("Easy");
		btnEasy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				levelSelect(Difficulty.EASY);
				cl.show(contentPane, "levelselect");
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
				levelSelect(Difficulty.MEDIUM);
				cl.show(contentPane, "levelselect");
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
				levelSelect(Difficulty.HARD);
				cl.show(contentPane, "levelselect");
			}
		});
		btnHard.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		btnHard.setPreferredSize(new Dimension(300, 100));
		btnHard.setMaximumSize(new Dimension(300, 100));
		btnHard.setMinimumSize(new Dimension(300, 100));
		btnHard.setAlignmentX(Component.CENTER_ALIGNMENT);
		DifficultyButtons.add(btnHard);
		
		DifficultyButtons.add(Box.createVerticalGlue());
		
		JPanel DifficultyTitle = new JPanel();
		DifficultyPanel.add(DifficultyTitle, BorderLayout.NORTH);
		DifficultyTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblDifficultyTitle = new JLabel("Select Difficulty");
		lblDifficultyTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblDifficultyTitle.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		lblDifficultyTitle.setAlignmentX(0.5f);
		DifficultyTitle.add(lblDifficultyTitle);
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
		
		JLabel lblLevel = new JLabel("Level");
		GameScoreLabels.add(lblLevel);
		
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
		
		lblLevellevel = new JLabel("0");
		lblLevellevel.setAlignmentX(1.0f);
		GameScore.add(lblLevellevel);
		
		lblMovesscore = new JLabel("0");
		lblMovesscore.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GameScore.add(lblMovesscore);
		
		lblHighscorescore = new JLabel("0");
		lblHighscorescore.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GameScore.add(lblHighscorescore);
		
		JButton btnHint = new JButton("Hint");
		btnHint.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				Heuristic JC = new HeuristicCarsInfront();
				SearchAlgorithm searchAStar = new SearchAlgorithmAStar(JC);
				List<Board> solution = searchAStar.getPath(board);
				if (solution.size() > 0) {
					cl.show(contentPane, "hintloading");
					GameBoard.removeAll();
	
					board = solution.get(1);
					CarCreate car = new CarCreate(board);
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
			        
			        if (board.isSolved()) {
			        	gameOver();
			        }
				}
			}
		});
		btnHint.setPreferredSize(new Dimension(150, 25));
		btnHint.setMinimumSize(new Dimension(150, 25));
		btnHint.setMaximumSize(new Dimension(150, 25));
		GameInfo.add(btnHint, BorderLayout.SOUTH);
		
		JPanel GameNavigationButtons = new JPanel();
		GameButtons.add(GameNavigationButtons, BorderLayout.SOUTH);
		GameNavigationButtons.setLayout(new BoxLayout(GameNavigationButtons, BoxLayout.X_AXIS));
		
		JButton btnGameBackToMain = new JButton("Main Menu");
		btnGameBackToMain.setMaximumSize(new Dimension(150, 25));
		btnGameBackToMain.setHorizontalTextPosition(SwingConstants.CENTER);
		GameNavigationButtons.add(btnGameBackToMain);
		btnGameBackToMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!hasContinue) {
					btnContinue.setEnabled(true);
					hasContinue = true;
				}
				cl.show(contentPane, "mainmenu");
			}
		});
		btnGameBackToMain.setPreferredSize(new Dimension(150, 50));
		btnGameBackToMain.setFont(new Font("Segoe UI", Font.PLAIN, 15));
		btnGameBackToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
		
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
		
		GameOverScoreInfo = new JPanel();
		GameOverScore.add(GameOverScoreInfo);
		GameOverScoreInfo.setLayout(cl_gameover);
		
		JPanel GameOverEnterName = new JPanel();
		GameOverEnterName.setPreferredSize(new Dimension(600, 300));
		GameOverEnterName.setMaximumSize(new Dimension(32767, 300));
		GameOverScoreInfo.add(GameOverEnterName, "entername");
		GameOverEnterName.setLayout(new BoxLayout(GameOverEnterName, BoxLayout.Y_AXIS));
		
		JPanel YourScore = new JPanel();
		GameOverEnterName.add(YourScore);
		
		lblYourScore = new JLabel("Your score: 0");
		lblYourScore.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		YourScore.add(lblYourScore);
		
		JPanel EnterName = new JPanel();
		GameOverEnterName.add(EnterName);
		
		JLabel lblYourName = new JLabel("Your name: ");
		EnterName.add(lblYourName);
		lblYourName.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		
		JLabel label = new JLabel("");
		EnterName.add(label);
		
		JLabel label_1 = new JLabel("");
		EnterName.add(label_1);
		
		txtEnterYourName = new JTextField();
		txtEnterYourName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					showHighScore();
				}
			}
		});
		EnterName.add(txtEnterYourName);
		txtEnterYourName.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		txtEnterYourName.setColumns(10);
		
		JButton btnSubmit = new JButton("Submit");
		btnSubmit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				showHighScore();
			}
		});
		EnterName.add(btnSubmit);
		btnSubmit.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		
		GameOverHighScore = new JPanel();
		GameOverScoreInfo.add(GameOverHighScore, "highscore");
		GameOverHighScore.setLayout(new BoxLayout(GameOverHighScore, BoxLayout.Y_AXIS));
		
		Component verticalStrut = Box.createVerticalStrut(25);
		GameOverScore.add(verticalStrut);
		
		JButton btnGameOverBackToMain = new JButton("Back to Main Menu");
		btnGameOverBackToMain.setAlignmentX(Component.CENTER_ALIGNMENT);
		GameOverScore.add(btnGameOverBackToMain);
		btnGameOverBackToMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetGame();
				if (hasContinue) {
					btnContinue.setEnabled(false);
					hasContinue = false;
				}
				cl.show(contentPane, "mainmenu");
			}
		});
		btnGameOverBackToMain.setPreferredSize(new Dimension(300, 50));
		btnGameOverBackToMain.setMinimumSize(new Dimension(300, 50));
		btnGameOverBackToMain.setMaximumSize(new Dimension(300, 50));
		btnGameOverBackToMain.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		
		GameHintLoading = new JPanel();
		contentPane.add(GameHintLoading, "hintloading");
		GameHintLoading.setLayout(new BoxLayout(GameHintLoading, BoxLayout.Y_AXIS));
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		GameHintLoading.add(verticalGlue_1);
		
		JLabel lblLoadingSolutions = new JLabel("Loading Solutions ...");
		lblLoadingSolutions.setFont(new Font("Segoe UI", Font.PLAIN, 60));
		lblLoadingSolutions.setAlignmentX(Component.CENTER_ALIGNMENT);
		GameHintLoading.add(lblLoadingSolutions);
		
		Component verticalGlue = Box.createVerticalGlue();
		GameHintLoading.add(verticalGlue);
		
		JPanel SettingsPanel = new JPanel();
		contentPane.add(SettingsPanel, "settings");
		SettingsPanel.setLayout(new BorderLayout(0, 0));
		
		JPanel SettingsTitle = new JPanel();
		SettingsPanel.add(SettingsTitle, BorderLayout.NORTH);
		SettingsTitle.setLayout(new BoxLayout(SettingsTitle, BoxLayout.Y_AXIS));
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettings.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		lblSettings.setAlignmentX(0.5f);
		SettingsTitle.add(lblSettings);
		
		JPanel SettingsButtons = new JPanel();
		SettingsPanel.add(SettingsButtons, BorderLayout.SOUTH);
		SettingsButtons.setLayout(new BoxLayout(SettingsButtons, BoxLayout.Y_AXIS));
		
		JButton btnSettingsBackToMain = new JButton("Back to Main Menu");
		btnSettingsBackToMain.setMinimumSize(new Dimension(300, 50));
		btnSettingsBackToMain.setMaximumSize(new Dimension(300, 50));
		btnSettingsBackToMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				resetGame();
				if (hasContinue) {
					btnContinue.setEnabled(false);
					hasContinue = false;
				}
				cl.show(contentPane, "mainmenu");
			}
		});
		btnSettingsBackToMain.setPreferredSize(new Dimension(300, 50));
		btnSettingsBackToMain.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnSettingsBackToMain.setAlignmentX(0.5f);
		SettingsButtons.add(btnSettingsBackToMain);
	}
	
	public void levelSelect(Difficulty difficulty) {
		SelectLevelButtons.add(Box.createHorizontalGlue());
		for (int i = 0; i < Settings.MAX_LEVELS; i++) {
			int level_i = i + 1;
			JButton button_1 = new JButton(Integer.toString(level_i));
			button_1.setFont(new Font("Segoe UI", Font.PLAIN, 30));
			button_1.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					newGame(difficulty, level_i);
					cl.show(contentPane, "game");
				}
			});
			SelectLevelButtons.add(button_1);
			
			if (i != Settings.MAX_LEVELS - 1) {
				SelectLevelButtons.add(Box.createHorizontalStrut(20));
			}
		}
		SelectLevelButtons.add(Box.createHorizontalGlue());
	}
	
	public void newGame(Difficulty difficulty, int level) {
		resetGame();
		updateDifficulty(difficulty);
		updateLevel(level);
		updateHighScore();
		board = BoardIO.loadBoardFromFile(Settings.PATH_PUZZLES + difficulty.name().toLowerCase() + Integer.toString(level) + ".txt");
        CarCreate car = new CarCreate(board);
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
        
        if (highScore.size() > 0) {
        	lblHighscorescore.setText(Integer.toString(highScore.get(0).getScore()));
        }
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
		else if (difficulty == Difficulty.HARD) {
			lblDifficultyscore.setText("Hard");
		}
	}
	
	public void updateLevel(int level) {
		this.setLevel(level);
		lblLevellevel.setText(Integer.toString(level));
	}
	
	public void updateHighScore() {
		highScore = BoardIO.loadHighScoreFromFile(Settings.PATH_PUZZLES + Settings.PATH_HIGHSCORE_FILE, difficulty, level);
	}
	
	public void gameOver() {
		cl.show(contentPane, "gameover");
		lblYourScore.setText("Your score: " + moves); 
		cl_gameover.show(GameOverScoreInfo, "entername");
	}
	
	public void resetGame() {
		GameBoard.removeAll();
		GameOverHighScore.removeAll();
		SelectLevelButtons.removeAll();
		txtEnterYourName.setText("");
		resetMoves();
	}
	
	public void showHighScore() {
		String playerName = txtEnterYourName.getText();
		BoardIO.printHighScoreToFile(Settings.PATH_PUZZLES + Settings.PATH_HIGHSCORE_FILE, difficulty, level, moves, playerName);
		updateHighScore();
		
		if (highScore.size() >= 5) {
			for (int i = 0; i < 5; i++) {
				JLabel score = new JLabel(Integer.toString(i + 1) + ". " + highScore.get(i).getPlayerName() + " " + Integer.toString(highScore.get(i).getScore()));
				score.setFont(new Font("Segoe UI", Font.PLAIN, 20));
				score.setAlignmentX(Component.CENTER_ALIGNMENT);
				GameOverHighScore.add(score);
			}
		}
		else {
			for (int i = 0; i < highScore.size(); i++) {
				JLabel score = new JLabel(Integer.toString(i + 1) + ". " + highScore.get(i).getPlayerName() + " " + Integer.toString(highScore.get(i).getScore()));
				score.setFont(new Font("Segoe UI", Font.PLAIN, 20));
				score.setAlignmentX(Component.CENTER_ALIGNMENT);
				GameOverHighScore.add(score);
			}
		}
		
		cl_gameover.show(GameOverScoreInfo, "highscore");
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

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
