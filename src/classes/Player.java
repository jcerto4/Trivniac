package classes;

public class Player {
	
	private int playerID;
	private String username;
	private String password;
	private int highScore;
	
	public Player(int playerID, String username, String password, int highScore) {
		this.playerID = playerID;
		this.username = username;
		this.password = password;
		this.highScore = highScore;
	}
	
	public int getPlayerID() {
		return playerID;
	}

	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}

	public int getHighScore() {
		return highScore;
	}
	
	public String toString() {
		return username + " highest score is " + highScore;
	}
	
}
