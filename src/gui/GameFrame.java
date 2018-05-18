package gui;
import java.awt.*;

import javax.sound.sampled.*;
import javax.swing.*;
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
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = -5739699646629372557L;

	private JPanel contentPane;
	private JPanel GameOverScoreInfo;
	private Difficulty difficulty = Difficulty.EASY;
	private int level = 0;

	CardLayout cl = new CardLayout();
	CardLayout cl_gameover = new CardLayout();
	JPanel MainMenuPanel = new JPanel() {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "main_background.jpg")));
			g.drawImage(img.getImage(), 0, 0, null);
		}
	};
	JPanel DifficultyPanel = new JPanel() {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "difficulty_background.jpg")));
			g.drawImage(img.getImage(), 0, 0, null);
		}
	};
	JPanel GamePanel = new JPanel() {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "setting_background.jpg")));
			g.drawImage(img.getImage(), 0, 0, null);
		}
	};
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

	static InputStream bgm = GameFrame.class.getClassLoader().getResourceAsStream(Settings.PATH_UI_IMAGES + "background.wav");
	static AudioStream s;

	static {
		try {
			s = new AudioStream(bgm);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static AudioData audioData;

	static {
		try {
			audioData = s.getData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static ContinuousAudioDataStream loop = new ContinuousAudioDataStream(audioData);
	boolean isplaying = true;

	/**
	 * Create the frame.
	 */
	public GameFrame() {
		setTitle("Gridlock 1.0");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, Settings.UI_FRAME_WIDTH, Settings.UI_FRAME_HEIGHT);

		// Start bgm
		AudioPlayer.player.start(loop);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(cl);

		contentPane.add(MainMenuPanel, "mainmenu");
		cl.show(contentPane, "mainmenu");

		MainMenuPanel.setLayout(new BoxLayout(MainMenuPanel, BoxLayout.PAGE_AXIS));

		JPanel MainMenuTitle = new JPanel();
		MainMenuTitle.setOpaque(false);
		MainMenuPanel.add(MainMenuTitle);
		MainMenuTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblGridlock = new JLabel("GridLock");
		MainMenuTitle.add(lblGridlock);
		lblGridlock.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		lblGridlock.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblGridlock.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel MainMenuButtons = new JPanel();
		MainMenuPanel.add(MainMenuButtons);
		MainMenuButtons.setOpaque(false);
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
				ButtonSound();
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
					ButtonSound();
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
				ButtonSound();
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

		JPanel SelectLevelPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "difficulty_background.jpg")));
				g.drawImage(img.getImage(), 0, 0, null);
			}
		};
		contentPane.add(SelectLevelPanel, "levelselect");
		SelectLevelPanel.setLayout(new BorderLayout(0, 0));

		JPanel SelectLevelTitle = new JPanel();
		SelectLevelTitle.setOpaque(false);
		SelectLevelPanel.add(SelectLevelTitle, BorderLayout.NORTH);
		SelectLevelTitle.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblSelectLevel = new JLabel("Select Level");
		lblSelectLevel.setHorizontalAlignment(SwingConstants.CENTER);
		lblSelectLevel.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		lblSelectLevel.setAlignmentX(0.5f);
		SelectLevelTitle.add(lblSelectLevel);

		JPanel SelectLevelBackButtons = new JPanel();
		SelectLevelBackButtons.setOpaque(false);
		SelectLevelPanel.add(SelectLevelBackButtons, BorderLayout.SOUTH);
		SelectLevelBackButtons.setLayout(new BoxLayout(SelectLevelBackButtons, BoxLayout.X_AXIS));

		Component horizontalGlue = Box.createHorizontalGlue();
		SelectLevelBackButtons.add(horizontalGlue);

		JButton btnBackToDifficulty = new JButton("Back to Difficulty");
		btnBackToDifficulty.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				SelectLevelButtons.removeAll();
				ButtonSound();
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
				ButtonSound();
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
		DifficultyBackButtons.setOpaque(false);
		DifficultyPanel.add(DifficultyBackButtons, BorderLayout.SOUTH);

		JButton btnDifficultyBackToMain = new JButton("Back to Main Menu");
		btnDifficultyBackToMain.setMinimumSize(new Dimension(300, 50));
		btnDifficultyBackToMain.setMaximumSize(new Dimension(300, 50));
		DifficultyBackButtons.add(btnDifficultyBackToMain);
		btnDifficultyBackToMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ButtonSound();
				resetGame();
				cl.show(contentPane, "mainmenu");
			}
		});
		btnDifficultyBackToMain.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnDifficultyBackToMain.setPreferredSize(new Dimension(300, 50));
		btnDifficultyBackToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel DifficultyButtons = new JPanel();
		DifficultyButtons.setOpaque(false);
		DifficultyPanel.add(DifficultyButtons);
		DifficultyButtons.setLayout(new BoxLayout(DifficultyButtons, BoxLayout.PAGE_AXIS));

		DifficultyButtons.add(Box.createVerticalGlue());

		SelectLevelButtons.setOpaque(false);

		JButton btnEasy = new JButton("Easy");
		btnEasy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ButtonSound();
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
				ButtonSound();
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
				ButtonSound();
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
		DifficultyTitle.setOpaque(false);
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
		GameBoardLayeredPane.setLayer(GameBoard, 1);
				GameBoard.setBounds(200, 110, 547, 551);
				GameBoardLayeredPane.add(GameBoard);
		
				GameBoard.setPreferredSize(new Dimension(Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE));
				GameBoard.setMinimumSize(new Dimension(Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE));
				GameBoard.setMaximumSize(new Dimension(Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE));
				GameBoard.setLayout(null);
				GameBoard.setOpaque(false);

		Grid Grid = new Grid();
		Grid.setBounds(new Rectangle(200, 110, 547, 547));
		GameBoardLayeredPane.setLayer(Grid, 0);
		GameBoardLayeredPane.add(Grid);
		Grid.setLayout(null);
		
		JLabel lblExit = new JLabel("EXIT");
		lblExit.setHorizontalAlignment(SwingConstants.CENTER);
		lblExit.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		lblExit.setBounds(458, 209, 88, 37);
		Grid.add(lblExit);

		JPanel GameButtons = new JPanel();
		GameButtons.setOpaque(false);
		GameButtons.setMaximumSize(new Dimension(100, 600));
		GamePanel.add(GameButtons);
		GameButtons.setLayout(new BorderLayout(0, 0));

		JPanel GameInfo = new JPanel();
		GameInfo.setOpaque(false);
		GameInfo.setMaximumSize(new Dimension(100, 300));
		GameButtons.add(GameInfo, BorderLayout.NORTH);
		GameInfo.setLayout(new BorderLayout(0, 0));

		JPanel GameScoreLabels = new JPanel();
		GameScoreLabels.setOpaque(false);
		GameInfo.add(GameScoreLabels, BorderLayout.WEST);
		GameScoreLabels.setLayout(new BoxLayout(GameScoreLabels, BoxLayout.Y_AXIS));

		JLabel lblDifficulty = new JLabel("Difficulty: ");
		lblDifficulty.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		GameScoreLabels.add(lblDifficulty);

		JLabel lblLevel = new JLabel("Level:");
		lblLevel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		GameScoreLabels.add(lblLevel);

		JLabel lblMoves = new JLabel("Moves: ");
		lblMoves.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		GameScoreLabels.add(lblMoves);

		JLabel lblHighscore = new JLabel("Highscore: ");
		lblHighscore.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		GameScoreLabels.add(lblHighscore);

		JPanel GameScore = new JPanel();
		GameScore.setOpaque(false);
		GameInfo.add(GameScore, BorderLayout.CENTER);
		GameScore.setLayout(new BoxLayout(GameScore, BoxLayout.Y_AXIS));

		lblDifficultyscore = new JLabel("Easy");
		lblDifficultyscore.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblDifficultyscore.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GameScore.add(lblDifficultyscore);

		lblLevellevel = new JLabel("0");
		lblLevellevel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblLevellevel.setAlignmentX(1.0f);
		GameScore.add(lblLevellevel);

		lblMovesscore = new JLabel("0");
		lblMovesscore.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblMovesscore.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GameScore.add(lblMovesscore);

		lblHighscorescore = new JLabel("0");
		lblHighscorescore.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		lblHighscorescore.setAlignmentX(Component.RIGHT_ALIGNMENT);
		GameScore.add(lblHighscorescore);

		JButton btnHint = new JButton("Hint");
		btnHint.setFont(new Font("Segoe UI", Font.PLAIN, 20));
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
					
					updateMoves(2);

					for (int i = 0; i < carList.size(); i++) {
						JLabel c = carList.get(i);
						GameBoard.add(c);
					}

					for (int i = 0; i < moveList.size(); i++) {
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
		btnHint.setPreferredSize(new Dimension(150, 50));
		btnHint.setMinimumSize(new Dimension(150, 50));
		btnHint.setMaximumSize(new Dimension(150, 50));
		GameInfo.add(btnHint, BorderLayout.SOUTH);

		JPanel GameNavigationButtons = new JPanel();
		GameNavigationButtons.setOpaque(false);
		GameButtons.add(GameNavigationButtons, BorderLayout.SOUTH);
		GameNavigationButtons.setLayout(new BoxLayout(GameNavigationButtons, BoxLayout.X_AXIS));

		JButton btnGameBackToMain = new JButton("Main Menu");
		btnGameBackToMain.setMaximumSize(new Dimension(150, 50));
		btnGameBackToMain.setHorizontalTextPosition(SwingConstants.CENTER);
		GameNavigationButtons.add(btnGameBackToMain);
		btnGameBackToMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ButtonSound();
				if (!hasContinue) {
					btnContinue.setEnabled(true);
					hasContinue = true;
				}
				cl.show(contentPane, "mainmenu");
			}
		});
		btnGameBackToMain.setPreferredSize(new Dimension(150, 50));
		btnGameBackToMain.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnGameBackToMain.setAlignmentX(Component.CENTER_ALIGNMENT);

		JPanel GameOverPanel = new JPanel();
		GameOverPanel.setOpaque(false);
		contentPane.add(GameOverPanel, "gameover");
		GameOverPanel.setLayout(new BorderLayout(0, 0));

		JPanel GameOverTitle = new JPanel();
		GameOverTitle.setOpaque(false);
		GameOverPanel.add(GameOverTitle, BorderLayout.NORTH);

		JLabel lblGameOver = new JLabel("Game Over");
		lblGameOver.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		GameOverTitle.add(lblGameOver);

		JPanel GameOverScore = new JPanel();
		GameOverScore.setOpaque(false);
		GameOverPanel.add(GameOverScore, BorderLayout.CENTER);
		GameOverScore.setLayout(new BoxLayout(GameOverScore, BoxLayout.Y_AXIS));

		GameOverScore.add(Box.createVerticalStrut(50));

		JPanel GameOverCongrats = new JPanel();
		GameOverCongrats.setOpaque(false);
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
		GameOverEnterName.setOpaque(false);
		GameOverEnterName.setPreferredSize(new Dimension(600, 300));
		GameOverEnterName.setMaximumSize(new Dimension(32767, 300));
		GameOverScoreInfo.add(GameOverEnterName, "entername");
		GameOverEnterName.setLayout(new BoxLayout(GameOverEnterName, BoxLayout.Y_AXIS));

		JPanel YourScore = new JPanel();
		YourScore.setOpaque(false);
		GameOverEnterName.add(YourScore);

		lblYourScore = new JLabel("Your score: 0");
		lblYourScore.setFont(new Font("Segoe UI", Font.PLAIN, 30));
		YourScore.add(lblYourScore);

		JPanel EnterName = new JPanel();
		EnterName.setOpaque(false);
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
				ButtonSound();
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

		settingPage(btnContinue);
	}

	public void settingPage(JButton btnContinue) {

		repaint();
		JPanel SettingsPanel = new JPanel(){
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "setting_background.jpg")));
				g.drawImage(img.getImage(), 0, 0, null);
			}
		};

		contentPane.add(SettingsPanel, "settings");
		SettingsPanel.setLayout(new BorderLayout(0, 0));

		JPanel SettingsTitle = new JPanel();
		SettingsTitle.setOpaque(false);
		SettingsPanel.add(SettingsTitle, BorderLayout.NORTH);
		SettingsTitle.setLayout(new BoxLayout(SettingsTitle, BoxLayout.Y_AXIS));

		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettings.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		lblSettings.setAlignmentX(0.5f);
		SettingsTitle.add(lblSettings);

		// Add sound_close and sound_open buttons
		JPanel sound_panel = new JPanel();
		sound_panel.setLayout(null);
		sound_panel.setOpaque(false);
		SettingsPanel.add(sound_panel);
		if (isplaying == true) {

			JButton sound_close = new JButton();

			sound_close.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "sound_close.jpg"))));
			sound_close.setBounds(450, 200, 200, 200);
			sound_panel.add(sound_close, BorderLayout.CENTER);

			sound_close.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					AudioPlayer.player.stop(loop);
					isplaying = false;
					//sound_panel.remove(sound_close);
					settingPage(btnContinue);

					cl.show(contentPane, "settings");
				}
			});


		} else if (isplaying == false) {
			JButton sound_open = new JButton();

			sound_open.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "sound_open.jpg"))));
			sound_open.setBounds(450, 200, 200, 200);
			sound_panel.add(sound_open, BorderLayout.CENTER);

			sound_open.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					AudioPlayer.player.start(loop);
					isplaying = true;
					settingPage(btnContinue);

					cl.show(contentPane, "settings");
				}
			});

		}


		JPanel SettingsButtons = new JPanel();
		SettingsButtons.setOpaque(false);
		SettingsPanel.add(SettingsButtons, BorderLayout.SOUTH);
		SettingsButtons.setLayout(new BoxLayout(SettingsButtons, BoxLayout.Y_AXIS));

		JButton btnSettingsBackToMain = new JButton("Back to Main Menu");
		btnSettingsBackToMain.setMinimumSize(new Dimension(300, 50));
		btnSettingsBackToMain.setMaximumSize(new Dimension(300, 50));
		btnSettingsBackToMain.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ButtonSound();
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
        else {
        	lblHighscorescore.setText("0");
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

	public void ButtonSound() {
		if(isplaying) {
			try {
				AudioInputStream click_audio = AudioSystem.getAudioInputStream(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "button_click.wav"));
				Clip clip = null;
				try {
					clip = AudioSystem.getClip();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				clip.open(click_audio);
				clip.start();
			} catch (UnsupportedAudioFileException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (LineUnavailableException e1) {
				e1.printStackTrace();
			}
		}
	}
}
