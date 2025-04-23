package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import classes.Player;
import classes.Question;

public class DatabaseManager {

//	private static final String DB_URL = "jdbc:mysql://localhost:3306/trivniac";
//    private static final String USER = "root";
//    private static final String PASSWORD = "root";
	
	private static final String DB_URL = "jdbc:mysql://trivniac-db-trivniac.b.aivencloud.com:28380/trivniac?ssl-mode=REQUIRED";
	private static final String USER = "avnadmin";
	private static final String PASSWORD = "AVNS_JWAugiCHt_zwaoTko6R";
	    
    
//Question Fetching and Question Instantiation 
    
    public static Question getRandomQuestion(String category) {
    	
    	String query = "SELECT * FROM questions WHERE category = ? ORDER BY RAND() LIMIT 1";
    	
    	try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    		PreparedStatement statement = connection.prepareStatement(query)){
    		
    		statement.setString(1, category);
    		
    	try(ResultSet results = statement.executeQuery()){	
    		if(results.next()) {
    			return new Question(
    					results.getInt("question_id"),
    					results.getString("category"),
    					results.getString("question"),
    					results.getString("option_1"),
    					results.getString("option_2"),
    					results.getString("option_3"),
    					results.getString("option_4"),
    					results.getInt("answer")
    				);
    		}
    		
    	}
    		
    		}catch(SQLException e) {
    			System.out.println("Error fetching questions");
    			e.printStackTrace();
    		}
    	return null;
    }
    
//--------------------------------------------------------------------------------------------------------
//Player Logging In, Player Registering, Fetching Player High Score
    
    
    public static Player getPlayer(String username) {
    	
    	String query = "SELECT * FROM player WHERE username = ?";
    	
    	try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        		PreparedStatement statement = connection.prepareStatement(query)){
        		
        		statement.setString(1, username);
        		
        	try(ResultSet results = statement.executeQuery()){
        		if(results.next()) {
        			return new Player(
        					results.getInt("player_id"),
        					results.getString("username"),
        					results.getString("password")
        				); 
        		}else {
        			System.out.println("No player found with username: " + username);
        			return null;
        		}
        		
        	}	

        		}catch(SQLException e) {
        			System.out.println("SQL Error Getting Player!");
        			e.printStackTrace();
        		}
    	return null;
        		
    }
    
    
    public static void insertNewPlayer(String username, String password) {
    	
    	String query = "INSERT INTO player (username, password) VALUES (?, ?)";
    	
    	try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    		PreparedStatement statement = connection.prepareStatement(query)){
    		
    		statement.setString(1, username);
    		statement.setString(2, password);

    		statement.executeUpdate();
   
			System.out.println("Insertion Complete!");
    	
    		}catch(SQLException e) {
    			System.out.println("Error Inserting New Player");
    			e.printStackTrace();
    		}	
    		
    }
    
    public static int getPlayerHighScore(int playerID, String gameMode) {
    	
    	String query = "SELECT MAX(score) FROM game_results WHERE player_id = ? AND game_mode = ?";
    	
    	try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    			PreparedStatement statement = connection.prepareStatement(query)){
    		
    		statement.setInt(1, playerID);
    		statement.setString(2, gameMode);
    		
    		try(ResultSet results = statement.executeQuery()){
    			while(results.next()) {
    				return results.getInt(1);
    			}
    		}
    		
    
    	}catch(SQLException e) {
    		System.out.println("Error retrieving all scores for player " + playerID + " in mode " + gameMode);
    		e.printStackTrace();
    	}
    	
    	return 0;
    	
    }
    
    public static ArrayList<Integer> getAllScoresForPlayer(int playerID, String gameMode){
    	
    	ArrayList<Integer> scores = new ArrayList<>();
    	
    	String query = "SELECT score FROM game_results WHERE player_id = ? AND game_mode = ? ORDER BY score DESC";
    	
    	try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    			PreparedStatement statement = connection.prepareStatement(query)){
    		
    		statement.setInt(1, playerID);
    		statement.setString(2, gameMode);
    		
    		try(ResultSet results = statement.executeQuery()){
    			while(results.next()) {
    				scores.add(results.getInt("score"));
    			}
    		}
    		
    
    	}catch(SQLException e) {
    		System.out.println("Error retrieving all scores for player " + playerID + " in mode " + gameMode);
    		e.printStackTrace();
    	}
    	
    	return scores;
    	
    }
    
    public static ArrayList<Player> getTopPlayers(String gameMode) {
    	
    	ArrayList<Player> topPlayers = new ArrayList<>();
    	String query = "SELECT p.player_id, p.username, p.password, MAX(g.score) AS high_score " +
    					"FROM player p JOIN game_results g " + 
    					"ON p.player_id = g.player_id " +
    					"WHERE g.game_mode = ? " +
    					"GROUP BY p.player_id, p.username, p.password " +
    					"ORDER BY high_score DESC LIMIT 10";
    	
    	try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    			PreparedStatement statement = connection.prepareStatement(query)){
    		
    		statement.setString(1, gameMode);
    		
    		try(ResultSet results = statement.executeQuery()){
    			while(results.next()) {
    				Player player = new Player(
    						results.getInt("player_id"),
    						results.getString("username"),
    						results.getString("password"),
    						results.getInt("high_score")
    					);
    				topPlayers.add(player);
    			}
    		}
    		
    		
    	}catch(SQLException e) {
    		System.out.println("Error fetching top players for game mode: " + gameMode);
    		e.printStackTrace();
    	}
    	
    	return topPlayers;
    }
    
//--------------------------------------------------------------------------------------------------
//Game Creation, Game Session Round Tracking, Updating Game Session Score 
    
    
//When this method is called: Save the game_id in Java and use it to track the game session data
    public static int startNewGame(Player player, String gameMode) {
    	
    	String query = "INSERT INTO game_results (player_id, score, game_mode) VALUES (?, 0, ?)";
    	
    	 try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    	         PreparedStatement insertStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)){

    	        insertStatement.setInt(1, player.getPlayerID());
    	        insertStatement.setString(2, gameMode);
    	        insertStatement.executeUpdate();
    	        
    	      try(ResultSet results = insertStatement.getGeneratedKeys()){
    	        if(results.next()) {
    	        	return results.getInt(1);
    	        }
    	        
    	      }    

    	 } catch (SQLException e) {
    	        System.out.println("Error Starting New Game");
    	        e.printStackTrace();
    	    }
    	 return -1;
    	}
      
    public static void updateScore(int gameID, int newScore) {
    	
    	String query = "UPDATE game_results SET score = ? WHERE game_id = ?";

    	    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    	         PreparedStatement statement = connection.prepareStatement(query)) {

    	        statement.setInt(1, newScore);
    	        statement.setInt(2, gameID);

    	        statement.executeUpdate();
    	   
    	    } catch (SQLException e) {
    	        System.out.println("Error Saving Game Results");
    	        e.printStackTrace();
    	    }
    	}
    
}
