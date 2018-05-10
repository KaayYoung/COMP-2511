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
	    board.setLayout(null);
	    
	    board.setBackground(Color.GRAY);
	    
		for(int row = 0; row < 6; row++) {
			for(int col = 0; col < 6; col++) {
			
				if(matrix[row][col] != 0) {
					
					int num = matrix[row][col];
					if(col != 5 && matrix[row][col+1] == num) {
						if(col != 4 && matrix[row][col+2] == num) {
							// x , y, width, length, direction
						    JLabel a = new JLabel();
						    a.setBounds(col*Car_width,row*Car_width,Car_width*3,Car_width);
						    a.setBackground(Color.BLACK);
						    a.setOpaque(true);
							carList.add(a);
							
							MoveComponent mc = new MoveComponent();
							mc.registerComponent(a);
							mc.setDirection(0);
							mc.setSnapSize(new Dimension(150,150));
							
							moveList.add(mc);
							col = col + 2;
						} else {
						    JLabel a = new JLabel();
						    a.setBounds(col*Car_width,row*Car_width,Car_width*2,Car_width);
						    a.setBackground(Color.BLACK);
						    a.setOpaque(true);
							carList.add(a);
							MoveComponent mc = new MoveComponent();
							mc.registerComponent(a);
							mc.setDirection(0);
							mc.setSnapSize(new Dimension(150,150));
							
							moveList.add(mc);
							col = col + 1;
						}
					}	
				}		
			}
		}
		
		for(int col = 0; col < 6; col++) {
			for(int row = 0;  row < 6; row++) {
				
				if(matrix[row][col] != 0) {
					
					int num = matrix[row][col];
					if(row != 5 && matrix[row+1][col] == num) {
						if(row != 4 && matrix[row+2][col] == num) {
						    JLabel a = new JLabel();
						    a.setBounds(col*Car_width,row*Car_width,Car_width,Car_width*3);
						    a.setBackground(Color.YELLOW);
						    a.setOpaque(true);
							carList.add(a);
							
							MoveComponent mc = new MoveComponent();
							mc.registerComponent(a);
							mc.setDirection(1);
							mc.setSnapSize(new Dimension(150,150));
							
							moveList.add(mc);
							row = row + 2;
						}else {
						    JLabel a = new JLabel();
						    a.setBounds(col*Car_width,row*Car_width,Car_width,Car_width*2);
						    a.setBackground(Color.YELLOW);
						    a.setOpaque(true);
							carList.add(a);
							
							MoveComponent mc = new MoveComponent();
							mc.registerComponent(a);
							mc.setDirection(1);
							mc.setSnapSize(new Dimension(150,150));
							moveList.add(mc);
							row = row + 1;
						}
					}
					
				}
				
			}
		}
	     
   
		for(int i = 0; i < carList.size(); i++) {
			JLabel c = carList.get(i);
			board.add(c);
			
		}
		
		
	    
//	    JComponent c1 = new CarComponent();
//
//	    board.add(c1, BorderLayout.CENTER);
	     
	    textPanel.add(board);
	    
	    frame.add(textPanel);

		
		frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		frame.setVisible(true);
   }

	private static final int FRAME_WIDTH = 1500;
	private static final int FRAME_HEIGHT = 1500;
}
