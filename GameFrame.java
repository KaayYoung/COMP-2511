package GridLock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class GameFrame extends JFrame{

    private JPanel background;

    public GameFrame(){
        prepareGUI();
    }

    public static void main(String args[]) {
        GameFrame gameFrame = new GameFrame();
    }

    public void prepareGUI() {
        this.setTitle("Grid Lock");
        this.setBounds(800,800,800,800);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // if don't add this line, the program won't stop when click cross

        background = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);

                ImageIcon img=new ImageIcon(GameFrame.class.getClassLoader().getResource("menu_background.png"));
                g.drawImage(img.getImage(), 0, 0, null);
            }
        };

        background.setLayout(null);
        background.setBounds(800,800,800,800);
        this.setContentPane(background);




        // Button for creating new game
        JButton newGameButton = new JButton("New Game");
        newGameButton.setBounds(320,250,100,60);
        background.add(newGameButton);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // redirect to difficulty selection page
                // dispose();
                difficultySelection();

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
                //dispose();
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

        this.setVisible(true);
    }

    public void difficultySelection() {

//        this.repaint();
//        this.remove(background);

        JPanel difficulty_background = new JPanel() {
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon img = new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("difficulty_background.jpg")));
                g.drawImage(img.getImage(), 0, 0, null);
            }
        };

        difficulty_background.setBounds(800,800,800,800);
        difficulty_background.setLayout(null);

        // Create Easy difficulty button
        JButton easy = new JButton("Easy");
        easy.setBounds(320,200,100,60);
        difficulty_background.add(easy);
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set up difficulty to easy

            }
        });

        // Create Medium difficulty button
        JButton medium = new JButton("Medium");
        medium.setBounds(320,300,100,60);
        difficulty_background.add(medium);
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set up difficulty to Medium

            }
        });

        // Create Expert difficulty button
        JButton expert = new JButton("Expert");
        expert.setBounds(320,420,100,60);
        difficulty_background.add(expert);
        expert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set up difficulty to Expert

            }
        });

        // Create Back button
        JButton back = new JButton();
        try {
            back.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("back.jpg"))));
        } catch (Exception e) {
            System.out.println("No such image");
            System.exit(0);
        }
        back.setBounds(320,550,100,60);
        difficulty_background.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Back to Menu Page
                setContentPane(background);
            }
        });

        validate();

        this.setContentPane(difficulty_background);
        //this.setContentPane(new DifficultyFrame(this));
        this.setVisible(true);
    }

}
