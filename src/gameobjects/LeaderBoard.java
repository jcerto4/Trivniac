package gameobjects;

import java.util.ArrayList;

import classes.Player;
import db.DatabaseManager;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LeaderBoard extends VBox{
 
	private String gameMode;
	
	public LeaderBoard(String gameMode) {
		
		this.gameMode = gameMode;
		buildLeaderboard();
		styleLeaderboard();
	}
	
	public void refreshLeaderboard() {
	
		new Thread(() -> {
			
			ArrayList<Player> topPlayers = DatabaseManager.getTopPlayers(gameMode);
			Platform.runLater(() -> {
				
				getChildren().clear();
				buildRows(topPlayers);

			});
			
			
		}).start();
		
	}
	
	private void buildLeaderboard() {
		
		ArrayList<Player> topPlayers = DatabaseManager.getTopPlayers(gameMode);
		buildRows(topPlayers);
	}
	
	private void styleLeaderboard() {
		
		setSpacing(10);
		setPadding(new Insets(10));
		setMaxWidth(300);
		//setMaxHeight(300);
		setEffect(new DropShadow(10, Color.BLACK));
		//setAlignment(Pos.CENTER_LEFT);
		setStyle("-fx-border-color: white;" +
		          "-fx-border-width: 2;" +
		          "-fx-border-style: solid;");
	}
	
	private void buildRows(ArrayList<Player> topPlayers) {
		
		HBox header = new HBox(10);
		header.setAlignment(Pos.CENTER_RIGHT);
		
		Label rankHeader = new Label("Rank");
		Label usernameHeader = new Label("User");
		Label scoreHeader = new Label("Score");
		
		Font headerFont = Font.font("Verdana", FontWeight.EXTRA_BOLD, 20);
		rankHeader.setFont(headerFont);
		usernameHeader.setFont(headerFont);
		scoreHeader.setFont(headerFont);
		
		rankHeader.setPrefWidth(70);
		usernameHeader.setPrefWidth(75);
		scoreHeader.setPrefWidth(70);
		  
		rankHeader.setTextFill(Color.WHITE);
		usernameHeader.setTextFill(Color.WHITE);
		scoreHeader.setTextFill(Color.WHITE);
		 
		header.getChildren().addAll(rankHeader, usernameHeader, scoreHeader);
		getChildren().add(header);
		
		for(int i = 0; i < topPlayers.size(); i++) {
			
			Player player = topPlayers.get(i);
			player.setRank(i + 1);
			
			HBox row = new HBox(10);
			row.setAlignment(Pos.CENTER_RIGHT);
			
			Label rank = new Label(String.valueOf(player.getRank()));
			Label username = new Label(player.getUsername());
			Label score = new Label(String.valueOf(player.getHighScore()));
			
			Font font = Font.font("Verdana", FontWeight.BOLD, 16);
			rank.setFont(font);
			username.setFont(font);
			score.setFont(font);
			
			rank.setPrefWidth(70);
			username.setPrefWidth(75);
			score.setPrefWidth(70);
			
			rank.setTextFill(Color.WHITE);
			username.setTextFill(Color.WHITE);
			score.setTextFill(Color.WHITE);
			
			row.getChildren().addAll(rank, username, score);
			
			getChildren().add(row);
			
			
		}
		
	}
	
}
