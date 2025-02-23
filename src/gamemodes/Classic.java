package gamemodes;

import java.io.File;

import classes.Player;
import db.DatabaseManager;
import gameobjects.LeaderBoard;
import gameobjects.Wheel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
	private LeaderBoard leaderboard;
	private int lives = 3;
	private int score = 0;
	private int gameID;
	private Player player;
	
	private Media loseLifeMedia;
	private MediaPlayer loseLifePlayer;
	
	
	public Classic() {
		loadLoseLifeSound();
		wheel = new Wheel();
		leaderboard = new LeaderBoard("Classic");
		createSpinButtonListeners();
		createTopSection();
		createCenterSection();
		createRightSection();
		styleButtons();
		showClassicMode();
	}
	
	public Classic(int gameID, Player player) {
		
		this.gameID = gameID;
		this.player = player;
		wheel = new Wheel();
		leaderboard = new LeaderBoard("Classic");
		createTopSection();
		createCenterSection();
		createRightSection();
		createSpinButtonListeners();
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
	
	private void createRightSection() {
		leaderboard.setAlignment(Pos.CENTER_RIGHT);
		leaderboard.setPadding(new Insets(0, 10, 0, 0));
		this.setRight(leaderboard);
	}
	
	
	private void showQuestionScreen(String category) {
		new QuestionScreen(gameID, category, (Boolean isCorrect) -> {
			handleQuestionResult(isCorrect);
		});
	}
	
	private void handleQuestionResult(boolean isCorrect) {
		if(isCorrect) {
			score += 5;
			DatabaseManager.updateScore(gameID, score);
			btnSpin.setDisable(false);
		}else {
			if(!livesCtn.getChildren().isEmpty()) {
				playLoseLife();
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
	
	private void loadLoseLifeSound() {
		String soundURL = "sounds/lose_life.mp3";
		loseLifeMedia = new Media(new File(soundURL).toURI().toString());
		loseLifePlayer = new MediaPlayer(loseLifeMedia);
	}
	
	private void playLoseLife() {
		loseLifePlayer.play();
	}
	
	private void showClassicMode() {
		Scene scene = new Scene(this, 1400, 1000);
		classicStage = new Stage();
		classicStage.setTitle("Classic Mode");
		classicStage.setScene(scene);
		classicStage.show();
	}
	
	
}
