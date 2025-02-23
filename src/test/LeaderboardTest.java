package test;

import classes.Player;
import db.DatabaseManager;
import gameobjects.LeaderBoard;
import gameobjects.Wheel;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class LeaderboardTest extends Application{

	
	
public void start(Stage stage) {
		
		
		BorderPane testScreen = new BorderPane();
		LeaderBoard leaderboard = new LeaderBoard("Classic");
		Button btnRefresh = new Button("Refresh");
		btnRefresh.setOnAction(e -> leaderboard.refreshLeaderboard());
		
		Button btnInsert = new Button("Insert");
		btnInsert.setOnAction(e -> {
			DatabaseManager.insertNewPlayer("Mike1", "Mike123");
			Player mike = DatabaseManager.getPlayer("Mike1");
			
			int gameID = DatabaseManager.startNewGame(mike, "Classic");
			DatabaseManager.updateScore(gameID, 180);
		});
		
		HBox btnCtn = new HBox(10, btnRefresh, btnInsert);
		btnCtn.setAlignment(Pos.CENTER);
		
		testScreen.setCenter(leaderboard);
		testScreen.setBottom(btnCtn);
		
		Scene scene = new Scene(testScreen, 600, 600);
		
		stage.setTitle("Leaderboard Test");
		stage.setScene(scene);
		stage.show();
		
	}

	
	public static void main(String[] args) {
		launch(args);
	}
	

}
