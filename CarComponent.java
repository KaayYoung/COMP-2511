import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.JComponent;

public class CarComponent extends JComponent {
	
	private CarShape car;
	private Point mousePoint;
	private int count;
	private int direction;
	private ArrayList<CarShape> carList = new ArrayList<CarShape>();
	private static final int Car_width = 150;
	
	int matrix[][] = {{1,1,0,0,0,0},
					  {0,0,0,0,5,0},
					  {0,0,6,6,5,0},
					  {4,4,4,0,0,0},
					  {0,0,0,7,7,7},
					  {0,0,0,0,0,0}};
	
	
	
	public CarComponent() {
		
		//this.setOpaque(false);
		this.setVisible(true);
		this.setFocusable(true);
		// search the horizontal car and store into carList
		for(int row = 0; row < 6; row++) {
			for(int col = 0; col < 6; col++) {
			
				if(matrix[row][col] != 0) {
					
					int num = matrix[row][col];
					if(col != 5 && matrix[row][col+1] == num) {
						if(col != 4 && matrix[row][col+2] == num) {
							// x , y, width, length, direction
							carList.add(new CarShape(col*Car_width, row*Car_width, Car_width,3, 0));
							col = col + 2;
						} else {
							carList.add(new CarShape(col*Car_width, row*Car_width, Car_width,2, 0));
							col = col + 1;
						}
					}	
				}		
			}
		}
		// search the vertical car and store into carList
		for(int col = 0; col < 6; col++) {
			for(int row = 0;  row < 6; row++) {
				
				if(matrix[row][col] != 0) {
					
					int num = matrix[row][col];
					if(row != 5 && matrix[row+1][col] == num) {
						if(row != 4 && matrix[row+2][col] == num) {
							carList.add(new CarShape(col*Car_width, row*Car_width, Car_width,3, 1));
							row = row + 2;
						}else {
							carList.add(new CarShape(col*Car_width, row*Car_width, Car_width,2, 1));
							row = row + 1;
						}
					}
					
				}
				
			}
		}
		
		
		
//		car = new CarShape(startX, startY, length, direc);
//		direction = car.getDirection();
		count = 0;
		addMouseListener(new MouseAdapter() {
			
			public void mousePressed(MouseEvent event) {
				System.out.println("Pressed:" + count);
				
				mousePoint = event.getPoint();
				System.out.println("X:" + mousePoint.getX());
				System.out.println("Y:" + mousePoint.getY());
				
				CarShape s = carList.get(0);
				System.out.println("y:" + s.getYco());
				System.out.println("x:" + s.getXco());
//				if(!car.contains(mousePoint)) {
//					mousePoint = null;
//				}
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			
			public void mouseDragged(MouseEvent event) {
				System.out.println("Dragger: " + count++);
				if(mousePoint == null) return;
				Point lastMousePoint = mousePoint;
				mousePoint = event.getPoint();
				double dx = 0;
				double dy = 0;
				
				dx = mousePoint.getX() - lastMousePoint.getX();
				dy = mousePoint.getY() - lastMousePoint.getY();
				
				for(int i = 0; i < carList.size(); i++) {
					CarShape c = carList.get(i);
					// move the car has mousePoint
					if(c.contains(mousePoint)) {
						int d = c.getDirection();
						if(d == 1) {
							dx = 0;
						}else {
							dy = 0;
						}
						c.translate((int) dx, (int) dy);
					}
				}
				
				
				repaint();	
			}
			
		});
		
	}
	
	
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for(int i = 0; i < carList.size(); i++) {
			CarShape c = carList.get(i);
			c.draw(g2);
			
		}
		//car.draw(g2);
		
	}
}
