
public class Car {

	private int carId;
	private int length;
	private String color;
	private int direction; // vertical: 1, horizontal: 0;
	private int posCol;
	private int posRow;
	
	
	public Car(int carId, int length, String color, int direction, int posCol, int posRow) {
		this.carId = carId;
		this.length = length;
		this.color = color;
		this.direction = direction;
		this.posCol = posCol;
		this.posRow = posRow;
	}
	
	public void changePos(int col, int row) {
		posCol = col;
		posRow = row;
		
	}
	
	public int getId() {
		return carId;
	}
	
	public int getLength() {
		return length;
	}
	
	public String getColor() {
		return color;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public int getPosCol() {
		return posCol;
	}
	
	public int getPosRow() {
		return posRow;
	}
	
	
	
}