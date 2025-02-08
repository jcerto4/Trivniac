package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import classes.Player;
import classes.Question;

public class DatabaseManager {

	private static final String DB_URL = "jdbc:mysql://localhost:3306/trivniac";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
	    
    
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
        					results.getString("password"),
        					results.getInt("high_score")
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
    	
    	String query = "INSERT INTO player (username, password, high_score) VALUES (?, ?, ?)";
    	
    	try(Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
    		PreparedStatement statement = connection.prepareStatement(query)){
    		
    		statement.setString(1, username);
    		statement.setString(2, password);
    		statement.setInt(3, 0);

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
    		  
    		  statement.setInt(1,  playerID);
    		  statement.setString(2, gameMode);
    		  
         try(ResultSet results = statement.executeQuery()){
    		  if(results.next()) {
    			  return results.getInt(1);
    		  }
         }	  
    	  }catch(SQLException e) {
    		  System.out.println("Error Getting High Score For " + gameMode);
    		  e.printStackTrace();
    	  }
    	  
    	  return 0;
    }
    
//--------------------------------------------------------------------------------------------------
//Game Creation, Game Round Tracking, Saving Game Results 
    
    
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
    
   
    
    public static void saveGameRound(int gameID, int questionID, boolean isCorrect) {
    	
    	if(gameID == -1) {
    		System.out.println("Error Locating Game");
    		return;
    	}
    	
    	String query = "INSERT INTO game_rounds (game_id, questions_id, is_correct) VALUES (?, ?, ?)";
        
    	try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
   	         PreparedStatement statement = connection.prepareStatement(query)) {

   	        statement.setInt(1, gameID);
   	        statement.setInt(2, questionID);
   	        statement.setBoolean(3, isCorrect);

   	        statement.executeUpdate();
   	   
   	    } catch (SQLException e) {
   	        System.out.println("Error Saving Game Round");
   	        e.printStackTrace();
   	    }
    	
    }
  
    public static void saveGameResult(int gameID, int newScore) {
    	    
    	if(gameID == -1) {
    		System.out.println("Error Locating Game");
    		return;
    	}
    	
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
