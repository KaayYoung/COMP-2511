import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;


import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Objects;

public class GameFrame extends JFrame {

    private JPanel background;
    static InputStream bgm = GameFrame.class.getClassLoader().getResourceAsStream("background.wav");
    static AudioStream s;
    static {
        try {
            s = new AudioStream(bgm);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static AudioData audiodata;

    static {
        try {
            audiodata = s.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static ContinuousAudioDataStream loop = new ContinuousAudioDataStream(audiodata);
    boolean isplaying = true;

    private static final int FRAME_WIDTH = 1500;
    private static final int FRAME_HEIGHT = 1500;

    static int matrix[][] = {{1,1,0,0,0,0},
            {0,0,0,0,5,0},
            {0,0,6,6,5,0},
            {4,4,4,0,0,0},
            {0,0,0,7,7,7},
            {0,0,0,0,0,0}};

    private static ArrayList<JLabel> carList = new ArrayList<JLabel>();
    private static final int Car_width = 150;
    private static ArrayList<MoveComponent> moveList = new ArrayList<MoveComponent>();


    public GameFrame(){
        AudioPlayer.player.start(loop);
        prepareGUI();
    }

    public static void main(String args[]) {
        GameFrame gameFrame = new GameFrame();
    }

    public void prepareGUI() {
        this.setTitle("Grid Lock");
        this.setBounds(1500,1500,1500,1500);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if don't add this line, the program won't stop when click cross

        background = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon img=new ImageIcon(GameFrame.class.getClassLoader().getResource("menu_background.png"));
                //Image a = img.getImage().getScaledInstance(1500,1500, Image.SCALE_DEFAULT);
                g.drawImage(img.getImage(), 0, 0, 1500,1500, this);
            }
        };

        background.setLayout(null);
        background.setBounds(1500,1500,1500,1500);
        this.setContentPane(background);

        // Button for creating new game
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(650,500,200,120);
        newGameButton.setFont(new Font("Arial", Font.PLAIN, 25));
        newGameButton.setActionCommand("New Game");
        newGameButton.addActionListener(new ButtonClickListener());
        background.add(newGameButton);

        // Button for Setting
        JButton settingButton = new JButton("Setting");
        settingButton.setBounds(650,750,200,120);
        settingButton.setFont(new Font("Arial", Font.PLAIN, 25));
        settingButton.setActionCommand("Setting");
        settingButton.addActionListener(new ButtonClickListener());
        background.add(settingButton);

        // Button for quit game
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(650,1050,200,120);
        quitButton.setFont(new Font("Arial", Font.PLAIN, 25));
        quitButton.setActionCommand("Quit");
        quitButton.addActionListener(new ButtonClickListener());
        background.add(quitButton);

        this.setVisible(true);
    }



    public void difficultySelection() {

        this.repaint();
//        this.remove(background);

        JPanel difficulty_background = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("difficulty_background.jpg")));
                g.drawImage(img.getImage(), 0, 0,1500,1500, this);
            }
        };

        difficulty_background.setBounds(1500,1500,1500,1500);
        difficulty_background.setLayout(null);

        // Create Easy difficulty button
        JButton easy = new JButton("Easy");
        easy.setBounds(650,400,200,120);
        easy.setFont(new Font("Arial", Font.PLAIN, 25));
        easy.setActionCommand("Easy");
        easy.addActionListener(new ButtonClickListener());
        difficulty_background.add(easy);

        // Create Medium difficulty button
        JButton medium = new JButton("Medium");
        medium.setBounds(650,650,200,120);
        medium.setFont(new Font("Arial", Font.PLAIN, 25));
        medium.setActionCommand("Medium");
        medium.addActionListener(new ButtonClickListener());
        difficulty_background.add(medium);

        // Create Expert difficulty button
        JButton expert = new JButton("Expert");
        expert.setBounds(650,900,200,120);
        expert.setFont(new Font("Arial", Font.PLAIN, 25));
        expert.setActionCommand("Expert");
        expert.addActionListener(new ButtonClickListener());
        difficulty_background.add(expert);

        // Create Back button
        JButton back = new JButton();
        try {
            back.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("back.png"))));
        } catch (Exception e) {
            System.out.println("No such image");
            System.exit(0);
        }
        back.setBounds(650,1200,200,120);
        back.setActionCommand("Back");
        back.addActionListener(new ButtonClickListener());
        difficulty_background.add(back);

        this.setContentPane(difficulty_background);
        this.setVisible(true);
    }


    public void settingPage() {

        this.repaint();

        JPanel setting_background = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("setting_background.png")));
                g.drawImage(img.getImage(), 0, 0, 1500,1500, this);
            }
        };

        setting_background.setBounds(1500,1500,1500,1500);
        setting_background.setLayout(null);

        // Add sound open/close button
        if (isplaying == true) {


            JButton sound_close = new JButton();
            try {
                sound_close.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("sound_close.jpg"))));
            } catch (Exception e) {
                System.out.println("No such image");
                System.exit(0);
            }
            sound_close.setBounds(550, 700, 310, 200);
            setting_background.add(sound_close);
            sound_close.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // should stop bgm then display sound_open button
                    AudioPlayer.player.stop(loop);
                    isplaying = false;
                    settingPage();
                }
            });
        }

        // Add sound_open button when sound is closed
        else if (isplaying == false) {
            JButton sound_open = new JButton();
            try {
                sound_open.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("sound_open.jpg"))));
            } catch (Exception e) {
                System.out.println("No such image");
                System.exit(0);
            }
            sound_open.setBounds(550, 700, 310, 200);
            setting_background.add(sound_open);
            sound_open.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    // should start bgm then display sound_close button
                    AudioPlayer.player.start(loop);
                    isplaying = true;
                    settingPage();
                }
            });
        }

        // Add back button
        JButton back = new JButton();
        try {
            back.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("back.png"))));
        } catch (Exception e) {
            System.out.println("No such image");
            System.exit(0);
        }
        back.setBounds(600,1050,200,120);
        back.setActionCommand("Back");
        back.addActionListener(new ButtonClickListener());
        setting_background.add(back);

        this.setContentPane(setting_background);
        this.setVisible(true);
    }

    public void boardPage() {

        repaint();

        JPanel textPanel = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon img=new ImageIcon("C:/Users/KayLa/Downloads/COMP2511/project/Gridlock/board_background.jpg");
                g.drawImage(img.getImage(), 0, 0, 1500, 1500, this);

            }
        };
        textPanel.setLayout(null);

        JLabel move = new JLabel("move:");
        JLabel score = new JLabel("score:");
        textPanel.add(move);
        textPanel.add(score);

        // col, row, width, height
        move.setBounds(200, 0, 200, 150);
        move.setFont(new Font("Serif", Font.BOLD, 60));
        move.setForeground(Color.WHITE);

        score.setBounds(1000, 0, 200, 150);
        score.setFont(new Font("Serif", Font.BOLD, 60));
        score.setForeground(Color.WHITE);

        JPanel board = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon img=new ImageIcon("C:/Users/KayLa/Downloads/COMP2511/project/Gridlock/board.jpg");
                g.drawImage(img.getImage(), 0, 0, 900, 900, this);
            }
        };
        board.setBounds(250, 300, 900, 900);
        board.setLayout(null);

        board.setBackground(Color.GRAY);

        CarCreate car = new CarCreate(matrix);
        car.createCarList();
        carList = car.getCarList();
        moveList = car.getMoveList();

        for(int i = 0; i < carList.size(); i++) {
            JLabel c = carList.get(i);
            board.add(c);
        }

        for(int i = 0; i < moveList.size(); i++) {
            MoveComponent c = moveList.get(i);
            c.setCarList(carList);
        }

        textPanel.add(board);

        this.add(textPanel);

        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setContentPane(textPanel);
        this.setVisible(true);

    }

    private class ButtonClickListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("New Game")) {
                try {
                    AudioInputStream click_audio = AudioSystem.getAudioInputStream(GameFrame.class.getClassLoader().getResource("button_click.wav"));
                    Clip clip = null;
                    try {
                        clip = AudioSystem.getClip();
                    } catch (LineUnavailableException e1) {
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
                difficultySelection();
                requestFocus();
            } else if (command.equals("Setting")) {
                settingPage();
                requestFocus();
            } else if (command.equals("Quit")) {
                System.exit(0);
            } else if (command.equals("Easy")) {
                boardPage();
            } else if (command.equals("Medium")) {
                boardPage();
            } else if (command.equals("Expert")) {
                boardPage();
            } else if (command.equals("Back")) {
                setContentPane(background);
            }
        }

    }
}
