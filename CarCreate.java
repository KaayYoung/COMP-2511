package View;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JLabel;

import Controller.Board;
import Controller.Car;
import Controller.Car.Direction;


public class CarCreate {

	private ArrayList<JLabel> carList = new ArrayList<JLabel>();
	private int matrix[][];
	private static final int Car_width = 150;
	private ArrayList<MoveComponent> moveList = new ArrayList<MoveComponent>();
	private HashMap<Integer, Car> listCar;
	private Board newBoard;
	
	public CarCreate(Board newBoard) {
		
		this.newBoard = newBoard;
		
	}
	
	public ArrayList<JLabel> getCarList(){
		return carList;
	}
	
	public ArrayList<MoveComponent> getMoveList(){
		return moveList;
	}
	
	
	public void createCarList() {
		listCar = newBoard.getCars();
		for (int key : listCar.keySet()) {
			Car currentCar = listCar.get(key);
			if (currentCar.getDirection() == Direction.HORIZONTAL) {
			
			    JLabel a = new JLabel();
			    a.setBounds(currentCar.getPosCol()*Car_width,currentCar.getPosRow()*Car_width,Car_width*currentCar.getLength(),Car_width);
			    if(key == 1) {
			    	a.setBackground(Color.RED);
			    }else {
			    	a.setBackground(Color.BLACK);
			    }
			    
			    a.setOpaque(true);
				carList.add(a);
				
				MoveComponent mc = new MoveComponent();
				mc.registerComponent(a);
				mc.setDirection(0);
				mc.setSnapSize(new Dimension(150,150));
				mc.setCarId(key); ////////////////////////////////
				mc.setLabel(a);
				mc.setBoard(newBoard);
				
				moveList.add(mc);
			}else {
			    JLabel a = new JLabel();
			    a.setBounds(currentCar.getPosCol()*Car_width,(currentCar.getPosRow() - currentCar.getLength()+1)*Car_width,Car_width,Car_width*currentCar.getLength());
			    a.setBackground(Color.YELLOW);
			    a.setOpaque(true);
				carList.add(a);
				
				MoveComponent mc = new MoveComponent();
				mc.registerComponent(a);
				mc.setDirection(1);
				mc.setSnapSize(new Dimension(150,150));
				mc.setCarId(key);
				mc.setLabel(a);
				
				mc.setBoard(newBoard);
				moveList.add(mc);
				
			}
		}
		
		
//		
//		for(int row = 0; row < 6; row++) {
//			for(int col = 0; col < 6; col++) {
//			
//				if(matrix[row][col] != 0) {
//					
//					int num = matrix[row][col];
//					if(col != 5 && matrix[row][col+1] == num) {
//						if(col != 4 && matrix[row][col+2] == num) {
//							// x , y, width, length, direction
//						    JLabel a = new JLabel();
//						    a.setBounds(col*Car_width,row*Car_width,Car_width*3,Car_width);
//						    a.setBackground(Color.BLACK);
//						    a.setOpaque(true);
//							carList.add(a);
//							
//							MoveComponent mc = new MoveComponent();
//							mc.registerComponent(a);
//							mc.setDirection(0);
//							mc.setSnapSize(new Dimension(150,150));
//							mc.setLabel(a);
//							
//							moveList.add(mc);
//							col = col + 2;
//						} else {
//						    JLabel a = new JLabel();
//						    a.setBounds(col*Car_width,row*Car_width,Car_width*2,Car_width);
//						    a.setBackground(Color.BLACK);
//						    a.setOpaque(true);
//							carList.add(a);
//							MoveComponent mc = new MoveComponent();
//							mc.registerComponent(a);
//							mc.setDirection(0);
//							mc.setSnapSize(new Dimension(150,150));
//							mc.setLabel(a);
//							
//							moveList.add(mc);
//							col = col + 1;
//						}
//					}	
//				}		
//			}
//		}
//		
//		for(int col = 0; col < 6; col++) {
//			for(int row = 0;  row < 6; row++) {
//				
//				if(matrix[row][col] != 0) {
//					
//					int num = matrix[row][col];
//					if(row != 5 && matrix[row+1][col] == num) {
//						if(row != 4 && matrix[row+2][col] == num) {
//						    JLabel a = new JLabel();
//						    a.setBounds(col*Car_width,row*Car_width,Car_width,Car_width*3);
//						    a.setBackground(Color.YELLOW);
//						    a.setOpaque(true);
//							carList.add(a);
//							
//							MoveComponent mc = new MoveComponent();
//							mc.registerComponent(a);
//							mc.setDirection(1);
//							mc.setSnapSize(new Dimension(150,150));
//							mc.setLabel(a);
//							
//							moveList.add(mc);
//							row = row + 2;
//						}else {
//						    JLabel a = new JLabel();
//						    a.setBounds(col*Car_width,row*Car_width,Car_width,Car_width*2);
//						    a.setBackground(Color.YELLOW);
//						    a.setOpaque(true);
//							carList.add(a);
//							
//							MoveComponent mc = new MoveComponent();
//							mc.registerComponent(a);
//							mc.setDirection(1);
//							mc.setSnapSize(new Dimension(150,150));
//							mc.setLabel(a);
//							
//							moveList.add(mc);
//							row = row + 1;
//						}
//					}
//					
//				}
//				
//			}
//		}
	}
	
	
	
}
