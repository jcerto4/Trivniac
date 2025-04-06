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
 
	private String gameMode;
	private TableView<Player> leaderboard;
	ArrayList<Player> topPlayers = new ArrayList<>();
	ObservableList<Player> observableList;
	
	private TableColumn<Player, Integer> rankCol = new TableColumn<>("Rank");
	private TableColumn<Player, String> usernameCol = new TableColumn<>("User");
	private TableColumn<Player, String> gameModeCol = new TableColumn<>("Mode");
	private TableColumn<Player, Integer> scoreCol = new TableColumn<>("Score"); 
	
	public LeaderBoard(String gameMode) {
		
		this.gameMode = gameMode;
		buildLeaderboard();
		styleLeaderboard();
	}
	
	public void refreshLeaderboard() {
		new Thread(() -> {
			ArrayList<Player> updatedPlayers = DatabaseManager.getTopPlayers(gameMode);
			
			for(int i = 0; i < updatedPlayers.size(); i++) {
				updatedPlayers.get(i).setRank(i + 1);
			}
			
			Platform.runLater(() -> {
				observableList.setAll(updatedPlayers);
				leaderboard.refresh();
			});
		}).start();
	}
	
	private void buildLeaderboard() {
		
		leaderboard = new TableView<>();
		
		leaderboard.setPrefSize(400, 400);
		
		rankCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRank()).asObject());
		usernameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
		gameModeCol.setCellValueFactory(cellData -> new SimpleStringProperty(gameMode));
		scoreCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getHighScore()).asObject());
		
		rankCol.setStyle("-fx-alignment: CENTER;");
		usernameCol.setStyle("-fx-alignment: CENTER;");
		gameModeCol.setStyle("-fx-alignment: CENTER;");
		scoreCol.setStyle("-fx-alignment: CENTER;");
		
		leaderboard.getColumns().addAll(rankCol, usernameCol, gameModeCol, scoreCol);
		
		leaderboard.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		
		topPlayers = DatabaseManager.getTopPlayers(gameMode);
		
		for(int i = 0; i < topPlayers.size(); i++) {
			topPlayers.get(i).setRank(i + 1);
		}
		
		observableList = FXCollections.observableArrayList(topPlayers);
		
		leaderboard.setItems(observableList);
		
		
		this.getChildren().add(leaderboard);
	}
	
	private void styleLeaderboard() {
		
		leaderboard.setStyle(
				 "-fx-background-color: transparent;" +
				 "-fx-border-radius: 12;" +
				 "-fx-background-radius: 12;" +
				 "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 10, 0.2, 0, 5);" +
				 "-fx-font-family: 'Segoe UI';" +
				 "-fx-font-size: 14px;"
				);
		
		
	}
	
}
