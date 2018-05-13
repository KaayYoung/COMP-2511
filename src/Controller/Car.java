package Controller;

public class Car{
	public enum Direction {
		HORIZONTAL,
		VERTICAL
	}
	
	public static final int MIN_LENGTH = 2;
	public static final int MAX_LENGTH = 3;
	private int carId;
	private int posRow;		// starting row
	private int posCol; 	// starting column
	private int length;
	private Direction direction;
	
	public Car(int carId, int posRow, int posCol, int length, Direction direction) {
		if (carId < 1) throw new IllegalArgumentException("Car ID must be greater than 0");
		else this.carId = carId;
		
		if (posRow < 0 || posRow > Board.BOARD_SIZE - 1) throw new IllegalArgumentException("Row position must be between 0 - " + (Board.BOARD_SIZE - 1));
		else this.posRow = posRow;
		
		if (posCol < 0 || posCol > Board.BOARD_SIZE - 1) throw new IllegalArgumentException("Col position must be between 0 - " + (Board.BOARD_SIZE - 1));
		else this.posCol = posCol;

		if (length > MAX_LENGTH || length < MIN_LENGTH) throw new IllegalArgumentException("Length must be between " + MIN_LENGTH + " - " + MAX_LENGTH);
		else this.length = length;
		
		if (direction != Direction.HORIZONTAL && direction != Direction.VERTICAL) throw new IllegalArgumentException("Direction must be VERTICAL or HORIZONTAL)");
		else if (direction == Direction.VERTICAL && (posRow + 1 - length < 0)) throw new IllegalArgumentException("Invalid position. Car is out of bound vertically");
		else if (direction == Direction.HORIZONTAL && (posCol + length > Board.BOARD_SIZE)) throw new IllegalArgumentException("Invalid position. Car is out of bound horizontally");
		else this.direction = direction;	
	}

	public boolean equals(Object comparedCar) {
		try {
	        return (getCarId() == ((Car) comparedCar).getCarId()
	    			&& getPosCol() == ((Car) comparedCar).getPosCol()
	    			&& getPosRow() == ((Car) comparedCar).getPosRow()
	    			&& getLength() == ((Car) comparedCar).getLength()
	    			&& getDirection() == ((Car) comparedCar).getDirection());

	    } catch (ClassCastException e) {
	    	return super.equals(comparedCar);
	    }
	}
	
	public void changePos(int row, int col) {
		this.posRow = row;
		this.posCol = col;
	}
	
	@Override
	public String toString() {
		return carId + " "  + posRow + " " + posCol + " " + length + " " + direction;
	}
	
	public int getCarId() {
		return carId;
	}
	
	public int getLength() {
		return length;
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public int getPosCol() {
		return posCol;
	}
	
	public int getPosRow() {
		return posRow;
	}

}
