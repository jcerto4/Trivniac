package gameobjects;

import java.util.ArrayList;

import classes.Player;
import db.DatabaseManager;
import javafx.application.Platform;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class LeaderBoard extends VBox{
 
	//private String gameMode;
	private TableView<Player> leaderboard;
	ArrayList<Player> topPlayers = new ArrayList<>();
	ObservableList<Player> observableList;
	
	private SimpleIntegerProperty rank;
	private SimpleStringProperty username;
	private SimpleStringProperty gameMode;
	private SimpleIntegerProperty score;
	
	private TableColumn<Player, Integer> rankCol = new TableColumn<>("Rank");
	private TableColumn<Player, String> usernameCol = new TableColumn<>("User");
	private TableColumn<Player, String> gameModeCol = new TableColumn<>("Game Mode");
	private TableColumn<Player, Integer> scoreCol = new TableColumn<>("High Score"); 
	
	public LeaderBoard(SimpleStringProperty gameMode) {
		
		this.gameMode = gameMode;
		buildLeaderboard();
	}
	
	public void refreshLeaderboard() {
		new Thread(() -> {
			ArrayList<Player> updatedPlayers = DatabaseManager.getTopPlayers(gameMode);
			Platform.runLater(() -> {
				observableList.setAll(updatedPlayers);
				leaderboard.refresh();
			});
		}).start();
	}
	
	private void buildLeaderboard() {
		
		leaderboard = new TableView<>();
		leaderboard.getColumns().addAll(rankCol, usernameCol, gameModeCol, scoreCol);
		
		topPlayers = DatabaseManager.getTopPlayers(gameMode);
		for(int i = 0; i < topPlayers.size(); i++) {
			topPlayers.get(i).setRank(i + 1);
		}
		leaderboard.setItems(observableList);
		
		
		
		this.getChildren().add(leaderboard);
	}
	
}
