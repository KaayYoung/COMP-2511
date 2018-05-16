package gui;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gridlock.Board;
import gridlock.BoardIO;
import search.Heuristic;
import search.HeuristicCarsInfront;
import search.SearchAlgorithm;
import search.SearchAlgorithmAStar;
import settings.Settings;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

import javax.swing.JButton;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.awt.CardLayout;

import javax.swing.JLayeredPane;
import java.awt.Rectangle;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

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
	JPanel MainMenuPanel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "setting_background.png")));
			g.drawImage(img.getImage(), 0, 0, null);
		}
	};
	JPanel DifficultyPanel = new JPanel();
	JPanel GamePanel = new JPanel() {
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "board_background.jpg")));
			g.drawImage(img.getImage(), 0, 0, null);
		}
	};
	JPanel GameBoard = new JPanel();
	
	JPanel GameHintLoading = new JPanel();
	JLabel lblMovesscore;
	JLabel lblHighscorescore;
	JLabel lblDifficultyscore;
	JLabel lblYourScore;
	JLabel lblBestScore;
	
	private static ArrayList<JLabel> carList = new ArrayList<JLabel>();
	private static ArrayList<MoveComponent> moveList = new ArrayList<MoveComponent>();
	private int moves = 0;
	private boolean hasContinue = false;
	private Board board;
	
	
	static InputStream bgm = GameFrame.class.getClassLoader().getResourceAsStream(Settings.PATH_UI_IMAGES + "background.wav");
	static AudioStream s;
	static {
		try {
			s = new AudioStream(bgm);
		} catch(IOException e) {
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
		MainMenuButtons.setOpaque(false);
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
				newGame(Difficulty.EASY, 7);
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
				newGame(Difficulty.MEDIUM, 7);
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
				newGame(Difficulty.HARD, 7);
				cl.show(contentPane, "game");
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
		//GamePanel.setOpaque(false);
		
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
		Grid.setBounds(new Rectangle(200, 100, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE + 1, Settings.UI_BLOCK_SIZE * Settings.BOARD_SIZE + 1));
		GameBoardLayeredPane.setLayer(Grid, 0);
		GameBoardLayeredPane.add(Grid);
		
		JPanel GameButtons = new JPanel();
		GameButtons.setMaximumSize(new Dimension(100, 600));
		GamePanel.add(GameButtons);
		GameButtons.setLayout(new BorderLayout(0, 0));
		GameButtons.setOpaque(false);
		
		JPanel GameInfo = new JPanel();
		GameInfo.setMaximumSize(new Dimension(100, 300));
		GameButtons.add(GameInfo, BorderLayout.NORTH);
		GameInfo.setLayout(new BorderLayout(0, 0));
		GameInfo.setOpaque(false);
		
		JPanel GameScoreLabels = new JPanel();
		GameInfo.add(GameScoreLabels, BorderLayout.WEST);
		GameScoreLabels.setLayout(new BoxLayout(GameScoreLabels, BoxLayout.Y_AXIS));
		GameScoreLabels.setOpaque(false);
		
		JLabel lblDifficulty = new JLabel("Difficulty: ");
		GameScoreLabels.add(lblDifficulty);
		
		JLabel lblMoves = new JLabel("Moves: ");
		GameScoreLabels.add(lblMoves);
		
		JLabel lblHighscore = new JLabel("Highscore: ");
		GameScoreLabels.add(lblHighscore);
		
		JPanel GameScore = new JPanel();
		GameInfo.add(GameScore, BorderLayout.CENTER);
		GameScore.setLayout(new BoxLayout(GameScore, BoxLayout.Y_AXIS));
		GameScore.setOpaque(false);
		
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
		
		
		settingPage();
		
	}
	
	public void newGame(Difficulty difficulty, int level) {
		resetGame();
		updateDifficulty(difficulty);
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
	}
	
	public void settingPage() {
		repaint();
		JPanel SettingsPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				
				ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "setting_background.png")));
				g.drawImage(img.getImage(), 0, 0, null);
			}
		};
		//SettingsPanel.setOpaque(false);
		SettingsPanel.setLayout(new BorderLayout(0, 0));

		contentPane.add(SettingsPanel, "settings");
		
		JPanel SettingsTitle = new JPanel();
		SettingsPanel.add(SettingsTitle, BorderLayout.NORTH);
		//SettingsTitle.setLayout(new BoxLayout(SettingsTitle, BoxLayout.Y_AXIS));
		SettingsTitle.setOpaque(false);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettings.setFont(new Font("Segoe UI", Font.PLAIN, 90));
		lblSettings.setAlignmentX(0.5f);
		SettingsTitle.add(lblSettings);
		
		JPanel sound_panel = new JPanel();
		sound_panel.setLayout(null);
		sound_panel.setOpaque(false);
		SettingsPanel.add(sound_panel);
		if (isplaying == true) {
			
			JButton sound_close = new JButton();
			
			sound_close.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "sound_close.jpg"))));
			sound_close.setBounds(450, 200, 200, 200);
			sound_panel.add(sound_close, BorderLayout.CENTER);
			
			//sound_close.setBounds(550, 500, 600, 600);
			sound_close.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					AudioPlayer.player.stop(loop);
					isplaying = false;
					//sound_panel.remove(sound_close);
					settingPage();
					//sound_panel.remove(sound_close);
					
					cl.show(contentPane, "settings");
				}
			});
			
		
		} else if (isplaying == false) {
			JButton sound_open = new JButton();
			
			sound_open.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource(Settings.PATH_UI_IMAGES + "sound_open.jpg"))));
			sound_open.setBounds(450, 200, 200, 200);
			sound_panel.add(sound_open, BorderLayout.CENTER);
			
			//sound_close.setBounds(550, 500, 600, 600);
			sound_open.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					AudioPlayer.player.start(loop);
					isplaying = true;
					settingPage();
					
					cl.show(contentPane, "settings");
				}
			});
			
		}
		
		

		
		JPanel SettingsButtons = new JPanel();
		SettingsPanel.add(SettingsButtons, BorderLayout.SOUTH);
		//SettingsButtons.setLayout(new BoxLayout(SettingsButtons, BoxLayout.Y_AXIS));
		SettingsButtons.setOpaque(false);
		
		JButton btnSettingsBackToMain = new JButton("Back to Main Menu");
		btnSettingsBackToMain.setMinimumSize(new Dimension(300, 50));
		btnSettingsBackToMain.setMaximumSize(new Dimension(300, 50));
		btnSettingsBackToMain.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cl.show(contentPane, "mainmenu");
			}
		});
		
		btnSettingsBackToMain.setPreferredSize(new Dimension(300, 50));
		btnSettingsBackToMain.setFont(new Font("Segoe UI", Font.PLAIN, 20));
		btnSettingsBackToMain.setAlignmentX(0.5f);
		SettingsButtons.add(btnSettingsBackToMain);
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
	
	public void gameOver() {
		cl.show(contentPane, "gameover");
		lblYourScore.setText(Integer.toString(this.moves));
		lblBestScore.setText(Integer.toString(this.moves));
		resetGame();
	}
	
	public void resetGame() {
		GameBoard.removeAll();
		resetMoves();
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
