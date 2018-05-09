package search;

public class moves {
	
	private int CarId;
	private int Direction;//up: 1, down: -1,  right : 0, left : 2;
	private int numofMove;
	public moves(int CarId, int Direction, int numofMove)
	{
		this.CarId = CarId;
		this.Direction = Direction;
		this.numofMove = numofMove;
	}
	
	public int getId()
	{
		return this.CarId;
	}
	
	public int getDir()
	{
		return this.Direction;
	}
	
	public int numofMove()
	{
		return this.numofMove;
	}
}
