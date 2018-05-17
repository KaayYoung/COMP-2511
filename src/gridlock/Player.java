package gridlock;

import settings.Settings;

public class Player {
	private String playerName;
	private int score;
	public Player(String playerName, int score) {
		this.playerName = playerName;
		this.score = score;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getPlayerName() {
		return playerName;
	}
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	public boolean equals(Player other) {
		if (getPlayerName().equals(other.getPlayerName())) {
			return true;
		}
		return false;
	}
}
