package gameoutput;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import classes.Player;
import db.DatabaseManager;

public class CSVManager {
	
	
	public static void writePlayerCSVData(String filename, Player player) {
		
		try {
			File file = new File(filename);
			
			PrintWriter output = new PrintWriter(file);
			
			System.out.println("Writing File...");
			
			writePlayerData(output, player);
		
			output.close();
			System.out.println("File Written!");
		}catch(IOException ex) {
			System.out.println("Error opening file for output");
		}
	}
	
	public static void writeAllCSVData(String filename) {
		
		try {
			File file = new File(filename);
			
			PrintWriter output = new PrintWriter(file);
			
			System.out.println("Writing File...");
			
			writeAllData(output);
		
			output.close();
			System.out.println("File Written!");
		}catch(IOException ex) {
			System.out.println("Error opening file for output");
		}
	}
	
	
	
	
	
	private static void writePlayerData(PrintWriter output, Player player) {
		
		output.println("Game Mode,Username,Score");
		
		String[] gameModes = {"Classic", "Survival", "Blitz"};
		
		for(int i = 0; i < gameModes.length; i++) {
			
			ArrayList<Integer> scores = DatabaseManager.getAllScoresForPlayer(player.getPlayerID(), gameModes[i]); 
			
			if(i == 1 || i == 2) {
				output.println("---------------------");
			}
			
			for(int x = 0; x < scores.size(); x++) {
				
				output.printf("%s,%s,%d%n", gameModes[i], player.getUsername(), scores.get(x));
				
			}
			
		}
		
	}
	
	private static void writeAllData(PrintWriter output) {
		
		output.println("Game Mode,Rank,Username,Score");
		
		String[] gameModes = {"Classic", "Survival", "Blitz"};
		
		for(int i = 0; i < gameModes.length; i++) {
			
			ArrayList<Player> topPlayers = DatabaseManager.getTopPlayers(gameModes[i]); 
			
			if(i == 1 || i == 2) {
				output.println("---------------------");
			}
			
			for(int x = 0; x < topPlayers.size(); x++) {
				
				topPlayers.get(x).setRank(x + 1);
				
				output.printf("%s,%d,%s,%d%n", 
						gameModes[i], 
						topPlayers.get(x).getRank(), 
						topPlayers.get(x).getUsername(), 
						topPlayers.get(x).getHighScore());
			}
			
		}
		
		
		
	}
	
}
