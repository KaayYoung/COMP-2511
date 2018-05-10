import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class BoardFX extends JFrame{
	
	public BoardFX() {
		
	}
	
	public JPanel getText() {
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
	    
	    return textPanel;
		
		
	}
	
	public JPanel getBoard() {
		
	    JPanel board = new JPanel(new GridLayout(6, 6, 20, 20));
	    board.setBounds(300, 300, 800, 800);
	    
	    board.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

	    for (int i =0; i<(6*6); i++){
	        JLabel label = new JLabel();
	        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	        board.add(label);
	    }
	    
	    board.setBackground(Color.GRAY);
	    
	    return board;
		
	}
	
//	public JLabel getImageLabel() throws IOException {
//		String path = "src/images/car1.PNG";
//
//		BufferedImage img = ImageIO.read(new File(path));
//        ImageIcon icon = new ImageIcon(img);
//        Image scaleImage = icon.getImage().getScaledInstance(120, 400, Image.SCALE_DEFAULT);
//        ImageIcon i = new ImageIcon(scaleImage);
//        JLabel label = new JLabel(i);
//        label.setBounds(400, 200, 200, 600); //leftup 260, 200, 200, 600  rightone: 400, 200, 200, 200, 600
//        
//        return label;
//		
//	}
	
//	public static void main(String[] args) throws IOException {
//		//String path = "src/images/car1.PNG";
//		BoardFX board = new BoardFX();
//		
//		JFrame frame = new JFrame();
//	    frame.setLayout(new BorderLayout());
//	    
//	    JPanel grid = board.getBoard();
//	    JPanel text = board.getText();
//	    JLabel image = board.getImageLabel();
//	    
//	    frame.add(image);
//	    text.add(grid);
//	    frame.add(text);
//	    
//	    Point mousePoint;
//	    
//	    int counter = 0;
//	    image.addMouseListener(new MouseAdapter() {
//	    	public void mousePressed(MouseEvent event) {
//	    		
//	    		System.out.println("press: "+ counter);
//	    		mousePoint = event.getPoint();
//	    		if(!image.contains(mousePoint)) {
//	    			mousePoint = null;
//	    		}
//	    		
//	    	}
//	    });
//	    
//	    image.addMouseMotionListener(new MouseMotionAdaptor() {
//	    	public void mouseDragged(MouseEvent event) {
//	    		System.out.println("drag:" + counter++);
//	    		if(mousePoint == null) {
//	    			return;
//	    		}
//	    		Point lastMousePoint = mousePoint;
//	    		mousePoint = event.getPoint();
//	    		
//	    		double dx = mousePoint.getX() - lastMousePoint.getX();
//	    		double dy = mousePoint.getY() - lastMousePoint.getY();
//	    		image.translate((int) dx,(int) dy);
//	    		image.repaint();
//	    		
//	    	}
//	    });
//	    
// 	    frame.setSize(1500, 1500);
//	    frame.setVisible(true);
//	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		  
//	}
	
	
	
}
