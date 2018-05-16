package settings;

public class Settings {
	public static enum Difficulty {
		EASY,
		MEDIUM,
		HARD
	}
	
	public static final int BOARD_SIZE = 6;
	public static final int MAX_CARS = 11;
	public static final int RED_CAR_ID = 1;
	public static final int RED_CAR_ROW = 2;
	public static final int RED_CAR_LENGTH = 2;
	public static final int CAR_MIN_LENGTH = 2;
	public static final int CAR_MAX_LENGTH = 3;
	public static final int MOVEMENT_COST = 1; 	// Each car's move counts as 1
	public static final int HASHCODE_PRIME = 31;
    public static final int UI_FRAME_WIDTH = 1100;
    public static final int UI_FRAME_HEIGHT = 800;
    public static final int UI_BLOCK_SIZE = 91;
    public static final String PATH_UI_IMAGES = "gui/images/";
	public static final String PATH_PUZZLES = "puzzles/";
	public static final String PATH_HIGHSCORE_FILE = "highscore.txt";
	public static final int MAX_LEVELS = 7;
}
