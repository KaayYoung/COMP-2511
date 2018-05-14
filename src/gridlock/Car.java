package gridlock;

import settings.Settings;

public class Car{
	public static enum Direction {
		HORIZONTAL,
		VERTICAL
	}
	
	private int carId;
	private int posRow;		// starting row
	private int posCol; 	// starting column
	private int length;
	private Direction direction;
	
	public Car(int carId, int posRow, int posCol, int length, Direction direction) {
		if (carId < 1) throw new IllegalArgumentException("Car ID must be greater than 0");
		else this.carId = carId;
		
		if (posRow < 0 || posRow > Settings.BOARD_SIZE - 1) throw new IllegalArgumentException("Row position must be between 0 - " + (Settings.BOARD_SIZE - 1));
		else this.posRow = posRow;
		
		if (posCol < 0 || posCol > Settings.BOARD_SIZE - 1) throw new IllegalArgumentException("Col position must be between 0 - " + (Settings.BOARD_SIZE - 1));
		else this.posCol = posCol;

		if (length > Settings.CAR_MAX_LENGTH || length < Settings.CAR_MIN_LENGTH) throw new IllegalArgumentException("Length must be between " + Settings.CAR_MIN_LENGTH + " - " + Settings.CAR_MAX_LENGTH);
		else this.length = length;
		
		if (direction != Direction.HORIZONTAL && direction != Direction.VERTICAL) throw new IllegalArgumentException("Direction must be VERTICAL or HORIZONTAL)");
		else if (direction == Direction.VERTICAL && (posRow + 1 - length < 0)) throw new IllegalArgumentException("Invalid position. Car is out of bound vertically");
		else if (direction == Direction.HORIZONTAL && (posCol + length > Settings.BOARD_SIZE)) throw new IllegalArgumentException("Invalid position. Car is out of bound horizontally");
		else this.direction = direction;
		
//		this.carId = carId;
//		this.posRow = posRow;
//		this.posCol = posCol;
//		this.length = length;
//		this.direction = direction;
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
	
	@Override
	public int hashCode() {
		int hash = 1;
		hash = Settings.HASHCODE_PRIME * hash + carId;
		hash = Settings.HASHCODE_PRIME * hash + posRow;
		hash = Settings.HASHCODE_PRIME * hash + posCol;
		hash = Settings.HASHCODE_PRIME * hash + length;
		if (direction.equals(Direction.HORIZONTAL)) {
			hash = Settings.HASHCODE_PRIME * hash + 0;
		}
		else {
			hash = Settings.HASHCODE_PRIME * hash + 1;
		}
		return hash;
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
