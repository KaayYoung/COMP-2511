import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.awt.event.*;
import javax.swing.*;

/**
   A program that allows users to move a car with the mouse.
*/
public class CarMove{
	
	static int matrix[][] = {{1,1,0,0,0,0},
			  		  		 {0,0,0,0,5,0},
			  		  		 {0,0,6,6,5,0},
			  		  		 {4,4,4,0,0,0},
			  		  		 {0,0,0,7,7,7},
			  		  		 {0,0,0,0,0,0}};
	
	private static ArrayList<JLabel> carList = new ArrayList<JLabel>();
	private static final int Car_width = 150;
	private static ArrayList<MoveComponent> moveList = new ArrayList<MoveComponent>();
	
	
	public static void  main(String[] args){
		
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
	    
	    frame.add(textPanel);

		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
   }

	private static final int FRAME_WIDTH = 1500;
	private static final int FRAME_HEIGHT = 1500;
}
