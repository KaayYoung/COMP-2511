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
		
		BoardFX board = new BoardFX();
		
	    frame.setLayout(new BorderLayout());
	    
	    JPanel grid = board.getBoard();
	    JPanel text = board.getText();    
	    text.add(grid);
	    frame.add(text);
		
		
		
		//JComponent c1 = new CarComponent();
		
		//frame.add(c1);
		
		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
   }

	private static final int FRAME_WIDTH = 1500;
	private static final int FRAME_HEIGHT = 1500;
}
