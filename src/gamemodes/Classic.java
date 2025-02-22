package gamemodes;

import classes.Player;
import db.DatabaseManager;
import gameobjects.LeaderBoard;
import gameobjects.Wheel;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import screens.GameOver;
import screens.QuestionScreen;

public class Classic extends BorderPane{
	
	private Stage classicStage;
	private Wheel wheel;
	private Button btnSpin = new Button("SPIN");
	private HBox livesCtn = new HBox();
	private LeaderBoard leaderBoard;
	private int lives = 3;
	private int score = 0;
	private int gameID;
	private Player player;
	
	
//	public Classic() {
//		wheel = new Wheel();
//		createSpinButtonListeners();
//		createTopSection();
//		createCenterSection();
//		styleButtons();
//		showClassicMode();
//	}
	
	public Classic(int gameID, Player player) {
		
		this.gameID = gameID;
		this.player = player;
		wheel = new Wheel();
		createSpinButtonListeners();
		createTopSection();
		createCenterSection();
		styleButtons();
		showClassicMode();
	}
	
	private void createSpinButtonListeners() {
		btnSpin.setOnAction(e -> {
			
			btnSpin.setDisable(true);
			
			wheel.spinWheel((String selectedCategory) -> {
				showQuestionScreen(selectedCategory);
			});
			
		});
		
	}
	
	
	private void createTopSection() {
		
		livesCtn.setAlignment(Pos.CENTER);
		livesCtn.setSpacing(10);
		
		for(int i = 0; i < 3; i++) {
			Label brain = new Label("\uD83E\uDDE0");
			brain.setFont(Font.font("Segoe UI Emoji", 120));
			brain.setTextFill(Color.RED);
			livesCtn.getChildren().add(brain);
		}
		
		this.setTop(livesCtn);
		
	}
	
	private void createCenterSection() {
		
		VBox centerCtn = new VBox(10, wheel, btnSpin);
		centerCtn.setAlignment(Pos.CENTER);
		this.setCenter(centerCtn);
	}
	
	
	private void showQuestionScreen(String category) {
		new QuestionScreen(gameID, category, (Boolean isCorrect) -> {
			handleQuestionResult(isCorrect);
		});
	}
	
	private void handleQuestionResult(boolean isCorrect) {
		if(isCorrect) {
			DatabaseManager.saveGameResult(gameID, score+5);
			btnSpin.setDisable(false);
		}else {
			if(!livesCtn.getChildren().isEmpty()) {
				livesCtn.getChildren().remove(0);
				lives--;
			}
			
			if(lives > 0) {
				btnSpin.setDisable(false);
			}else {
				new GameOver();
			}
		}
	}
	
	private void styleButtons() {
		
		btnSpin.setPrefSize(250, 50);
		btnSpin.setFont(Font.font("Georgia", 32));
	}
	
	private void showClassicMode() {
		Scene scene = new Scene(this, 1200, 1000);
		classicStage = new Stage();
		classicStage.setTitle("Classic Mode");
		classicStage.setScene(scene);
		classicStage.show();
	}
	
	
}
