import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;

/**
   A program that allows users to move a car with the mouse.
*/
public class CarMove{
	public static void main(String[] args){
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    JPanel textPanel = new JPanel();
	    textPanel.setLayout(null);
	    
	    JLabel move = new JLabel("move:");
	    JLabel score = new JLabel("score:");
	    textPanel.add(move);
	    textPanel.add(score);
	    
	    // col, row, width, height
	    move.setBounds(200, 0, 200, 150);
	    move.setFont(new Font("Serif", Font.BOLD, 60));
	    
	    score.setBounds(1000, 0, 200, 150);
	    score.setFont(new Font("Serif", Font.BOLD, 60));
	    
	    JPanel board = new JPanel(new GridLayout(6, 6, 20, 20));
	    board.setBounds(300, 300, 800, 800);
	    
	    board.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

	    for (int i =0; i<(6*6); i++){
	        JLabel label = new JLabel();
	        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	        board.add(label);
	    }
	    
	    board.setBackground(Color.GRAY);
	    
	    JComponent c1 = new CarComponent();
	    
	    //frame.add(c1);
	    
	    board.add(c1);
	    
	    board.setLayout(new BorderLayout());
	    board.add(c1, BorderLayout.CENTER);
	     
	    textPanel.add(board);
	    
	    frame.add(textPanel);
	    
		
		
		
		
		
		
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
   }

	private static final int FRAME_WIDTH = 1500;
	private static final int FRAME_HEIGHT = 1500;
}
