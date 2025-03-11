package gamemodes;

import java.io.File;
import java.util.Random;

import classes.Player;
import db.DatabaseManager;
import gameobjects.LeaderBoard;
import gameobjects.Wheel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import screens.GameOver;
import screens.QuestionScreen;

public class Survival extends BorderPane{

	private Stage survivalStage;
	private Wheel wheel;
	private Button btnSpin = new Button("SPIN");
	private LeaderBoard leaderboard;
	private int score = 0;
	private int gameID;
	private Player player;
	private int answerStreak = 0;
	Text streakTracker = new Text("Current Answer Streak: " + answerStreak);
	
	private Media loseLifeMedia;
	private MediaPlayer loseLifePlayer;
		
	public Survival(int gameID, Player player) {
		
		this.gameID = gameID;
		this.player = player;
		wheel = new Wheel();
		leaderboard = new LeaderBoard("Survival");
		createTopSection();
		createCenterSection();
		createRightSection();
		createSpinButtonListeners();
		styleButtons();
		showSurvivalMode();
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
		
		VBox streakCtn = new VBox(streakTracker);
		streakCtn.setAlignment(Pos.CENTER);
		this.setTop(streakCtn);
		
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
			score += 10;
			answerStreak++;
			DatabaseManager.updateScore(gameID, score);
			btnSpin.setDisable(false);
			updateStreakTracker();
		}else {
			survivalStage.close();
			new GameOver(player, score, "Survival");
		}
	
	}
	private void styleButtons() {
		
		streakTracker.setFont(Font.font("Georgia", 36));
		
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
	
	private void updateStreakTracker() {
		streakTracker.setText("Current Answer Streak: " + answerStreak);
	}
	
	private void showSurvivalMode() {
		Scene scene = new Scene(this, 1000, 700);
		survivalStage = new Stage();
		survivalStage.setTitle("Survival Mode");
		survivalStage.setScene(scene);
		survivalStage.show();
	}
	
	
}
	
	
	

