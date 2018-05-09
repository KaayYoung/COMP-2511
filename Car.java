
public class Car{
	private int carId;
	private int length;
	private int direction;	// vertical: 1, horizontal: 0;
	private int posCol; 	// starting column
	private int posRow;		// starting row
	
	
	public Car(int carId, int posCol, int posRow, int length, int direction) {
		if (carId < 1) throw new IllegalArgumentException("Car ID must be greater than 0");
		else this.carId = carId;
		
		if (posCol > 6 || posCol < 1) throw new IllegalArgumentException("Col position must be between 1 - 6");
		else this.posCol = posCol;

		if (posRow < 1 || posRow > 6) throw new IllegalArgumentException("Row position must be between 1 - 6");
		else this.posRow = posRow;
		
		if (length > 3 || length < 2) throw new IllegalArgumentException("Length must be between 2 - 3");
		else this.length = length;
		
		if (direction != 0 || direction != 1) throw new IllegalArgumentException("Direction must be 1 (Vertical) or 0 (Horizontal)");
		else if (direction == 1 && (posRow - length < 0)) throw new IllegalArgumentException("Invalid position. Car is out of bound vertically");
		else if (direction == 0 && (posCol + length > 6)) throw new IllegalArgumentException("Invalid position. Car is out of bound horizontally");
		else this.direction = direction;	
	}
	
	public boolean changePos(int col, int row) {
		if (posCol > 6 && posCol < 1 && posRow < 1 && posRow > 6) {
			posCol = col;
			posRow = row;
			
			return true;
		}
		return false;
	}

	public boolean equals(Car comparedCar) {
		if (this.carId == comparedCar.carId
			&& this.posCol == comparedCar.posCol
			&& this.posRow == comparedCar.posRow
			&& this.length == comparedCar.length
			&& this.direction == comparedCar.direction) {
			return true;
		}
		
		return false;
	}
	
	@Override
	public String toString() {
		return "Car "+carId+": "+posCol+", "+posRow+", "+length+", "+direction+".";
	}
	
	public int getCarId() {
		return carId;
	}
	
	public int getLength() {
		return length;
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
