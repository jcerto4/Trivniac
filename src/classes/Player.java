package classes;

public class Player {
	
	private int playerID;
	private String username;
	private String password;
	private int highScore;
	private int rank;
	
	public Player(int playerID, String username, String password) {
		this.playerID = playerID;
		this.username = username;
		this.password = password;
	}
	
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
	
	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public String toString() {
		return username + " highest score is " + highScore;
	}
	
}
