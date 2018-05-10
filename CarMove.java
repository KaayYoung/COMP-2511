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
	    
	    JPanel board = new JPanel();
	    board.setBounds(250, 300, 900, 900);
	    board.setLayout(new BorderLayout());
	    
	    board.setBackground(Color.GRAY);
   
	    JComponent c1 = new CarComponent();

	    board.add(c1, BorderLayout.CENTER);
	     
	    textPanel.add(board);
	    
	    frame.add(textPanel);
		
		
		
		
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
   }

	private static final int FRAME_WIDTH = 1500;
	private static final int FRAME_HEIGHT = 1500;
}
