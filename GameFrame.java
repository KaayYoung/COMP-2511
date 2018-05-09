package GridLock;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BoxView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

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

        //JPanel controlPanel = new JPanel();
        //controlPanel.setLayout(null);
        //gameFrame.setContentPane(controlPanel);

        // Button for creating new game
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(320,250,100,60);
        background.add(newGameButton);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // redirect to difficulty selection page
                // difficultySelection();
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
                //settingPage();
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

        //gameFrame.pack();
        gameFrame.setVisible(true);
    }

}
