package GridLock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame {

    private JFrame gameFrame;
    private JPanel background;

    public GameFrame(){
        prepareGUI();
    }

    public static void main(String args[]) {
        GameFrame gameFrame = new GameFrame();
    }

    public void prepareGUI() {
        gameFrame = new JFrame("Grid Lock");
        gameFrame.setBounds(800,800,800,800);
        gameFrame.setLayout(new BorderLayout());
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if don't add this line, the program won't stop when click cross

        background = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon img=new ImageIcon(GameFrame.class.getClassLoader().getResource("menu_background.png"));
                g.drawImage(img.getImage(), 0, 0, null);
            }
        };

        background.setLayout(null);
        background.setBounds(800,800,800,800);
        gameFrame.add(background);

        // Button for creating new game
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(320,250,100,60);
        background.add(newGameButton);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // redirect to difficulty selection page
                difficultySelection();
                gameFrame.requestFocus();
            }
        });
        // Button for Setting
        JButton settingButton = new JButton("Setting");
        settingButton.setBounds(320,350,100,60);
        background.add(settingButton);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // redirect to setting page
                // settingPage();
            }
        });
        // Button for quit game
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(320,470,100,60);
        background.add(quitButton);
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        gameFrame.setVisible(true);
    }

    public void difficultySelection() {

        gameFrame.repaint();
        gameFrame.remove(background);

        JPanel difficulty_background = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon(GameFrame.class.getClassLoader().getResource("difficulty_background.jpg"));
                g.drawImage(img.getImage(), 0, 0, null);
            }
        };

        difficulty_background.setBounds(800,800,800,800);
        difficulty_background.setLayout(null);

        // Create Easy difficulty button
        Button easy = new Button("Easy");
        easy.setBounds(320,250,100,60);
        difficulty_background.add(easy);

        // Create Medium difficulty button
        Button medium = new Button("Medium");
        medium.setBounds(320,350,100,60);
        difficulty_background.add(medium);

        // Create Expert difficulty button
        Button expert = new Button("Expert");
        expert.setBounds(320,470,100,60);
        difficulty_background.add(expert);

        gameFrame.add(difficulty_background);
        gameFrame.setVisible(true);
    }

}
