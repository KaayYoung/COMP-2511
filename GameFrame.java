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
        background.add(newGameButton);
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // redirect to difficulty selection page
                difficultySelection();
                requestFocus();
            }
        });




        // Button for Setting
        JButton settingButton = new JButton("Setting");
        settingButton.setBounds(650,750,200,120);
        settingButton.setFont(new Font("Arial", Font.PLAIN, 25));
        background.add(settingButton);
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // redirect to setting page
                settingPage();
                requestFocus();
            }
        });
        // Button for quit game
        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(650,1050,200,120);
        quitButton.setFont(new Font("Arial", Font.PLAIN, 25));
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
        difficulty_background.add(easy);
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set up difficulty to easy

            }
        });

        // Create Medium difficulty button
        JButton medium = new JButton("Medium");
        medium.setBounds(650,650,200,120);
        medium.setFont(new Font("Arial", Font.PLAIN, 25));
        difficulty_background.add(medium);
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Set up difficulty to Medium

            }
        });

        // Create Expert difficulty button
        JButton expert = new JButton("Expert");
        expert.setBounds(650,900,200,120);
        expert.setFont(new Font("Arial", Font.PLAIN, 25));
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
            back.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("back.png"))));
        } catch (Exception e) {
            System.out.println("No such image");
            System.exit(0);
        }
        back.setBounds(650,1200,200,120);
        difficulty_background.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Back to Menu Page
                setContentPane(background);
            }
        });

        this.setContentPane(difficulty_background);
        //this.setContentPane(new DifficultyFrame(this));
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

        // Add back button
        JButton back = new JButton();
        try {
            back.setIcon(new ImageIcon(Objects.requireNonNull(GameFrame.class.getClassLoader().getResource("back.png"))));
        } catch (Exception e) {
            System.out.println("No such image");
            System.exit(0);
        }
        back.setBounds(320,550,100,60);
        setting_background.add(back);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Back to Menu Page
                setContentPane(background);
            }
        });

        this.setContentPane(setting_background);
        this.setVisible(true);
    }


}
