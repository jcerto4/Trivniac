package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	 private static final String DB_URL = "jdbc:mysql://localhost:3306/triviac_db";
	 private static final String USER = "root"; 
	 private static final String PASSWORD = "root"; 
	
	public static void main(String[] args) {
		 try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
	            if (connection != null) {
	                System.out.println("Connection to MySQL successful!");
	            } else {
	                System.out.println("Connection failed!");
	            }
	        } catch (SQLException e) {
	            System.err.println("MySQL Connection Error: " + e.getMessage());
	        }


	}

}
