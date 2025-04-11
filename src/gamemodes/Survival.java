package gamemodes;

import java.io.File;
import java.util.Random;

import classes.Player;
import db.DatabaseManager;
import gameobjects.LeaderBoard;
import gameobjects.Wheel;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
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
import javafx.util.Duration;
import powerups.DoubleChance;
import powerups.EliminateTwo;
import powerups.StopTimer;
import screens.GameModeSelection;
import screens.GameOver;
import screens.QuestionScreen;

public class Survival extends BorderPane{

	private Stage survivalStage;
	private Wheel wheel;
	private Button btnSpin = new Button("SPIN");

	private int score = 0;
	private int gameID;
	private Player player;
	private int answerStreak = 0;
	private double multiplier = 1.0;
	private Label streakTracker = new Label("Current Answer Streak: " + answerStreak);
	private Label multiplierTracker = new Label("Score: x" + multiplier);
	
	private Button btnBack = new Button("Go Back");
	
	private DoubleChance doubleChance;
	private EliminateTwo elimTwo;
	private StopTimer stopTimer;
	
	private Media backSoundMedia;
	private MediaPlayer backSoundPlayer;
		
	public Survival(int gameID, Player player) {
		
		this.gameID = gameID;
		this.player = player;
		wheel = new Wheel();
		
		
		doubleChance = new DoubleChance();
		elimTwo = new EliminateTwo();
		stopTimer = new StopTimer();
		
		loadBackSound();
		setBackground();
		createTopSection();
		createCenterSection();
		createBottomSection();
		createSpinButtonListeners();
		createBackButtonListeners();
		styleButtons();
		showSurvivalMode();
	}
	
	private void createBackButtonListeners() {
		btnBack.setOnAction(e -> {
			playBackSound();
			survivalStage.close();
			new GameModeSelection(player);
		});
	}
	
	private void createSpinButtonListeners() {
		btnSpin.setOnAction(e -> {
			
			btnSpin.setDisable(true);
			btnBack.setDisable(true);
			
			wheel.spinWheel((String selectedCategory) -> {
				showQuestionScreen(selectedCategory);
			});
			
		});
		
	}
	
	
	private void createTopSection() {
		
		VBox streakCtn = new VBox(10, streakTracker, multiplierTracker);
		streakCtn.setAlignment(Pos.CENTER);
		streakCtn.setTranslateY(30);
		this.setTop(streakCtn);
		
	}
	
	private void createCenterSection() {
		
		VBox centerCtn = new VBox(10, wheel, btnSpin);
		centerCtn.setAlignment(Pos.CENTER);
		centerCtn.setTranslateY(20);
		this.setCenter(centerCtn);
	}
	
	private void createBottomSection() {
		
		HBox btmCtn = new HBox(btnBack);
		btmCtn.setAlignment(Pos.CENTER_LEFT);
		btmCtn.setPadding(new Insets(20));
		this.setBottom(btmCtn);
	}
	
	
	private void showQuestionScreen(String category) {
		new QuestionScreen(gameID, category, (Boolean isCorrect) -> {
			handleQuestionResult(isCorrect);
		}, doubleChance, elimTwo, stopTimer);
	}
	
	private void handleQuestionResult(boolean isCorrect) {
		
		if(isCorrect) {
			score = (int) (10 * multiplier);
			answerStreak++;
			multiplier++;
			DatabaseManager.updateScore(gameID, score);
			btnSpin.setDisable(false);
			updateStreakTracker();
		}else {
			survivalStage.close();
			new GameOver(player, score, "Survival");
		}
	
	}
	private void styleButtons() {
		
		streakTracker.setFont(Font.font("Verdana", 36));
		multiplierTracker.setFont(Font.font("Verdana", 16));
		
		btnSpin.setPrefSize(250, 50);
		btnBack.setPrefSize(100, 40);

		btnSpin.setFont(Font.font("Georgia", 32));
		
		createHoverEffect(btnSpin);
		createHoverEffect(btnBack);
		
		btnSpin.setStyle(
			"-fx-background-color: linear-gradient(to bottom, #ffaa3c, #cc6600);" +
			"-fx-text-fill: #fff5e6;" +
			"-fx-background-radius: 10;" +
			"-fx-border-radius: 10;" +
			"-fx-border-color: #5c2e00;" +
			"-fx-border-width: 2;"
		);
		
		btnBack.setStyle(
				"-fx-background-color: linear-gradient(#616161, #424242);" +
				"-fx-text-fill: white;" +
				"-fx-font-weight: bold;" +
				"-fx-background-radius: 12;" 
			);
		
		String labelStyle = (
				"-fx-background-color: rgba(0,0,0,0.5);" +
			    "-fx-text-fill: white;" +
			    "-fx-font-weight: bold;" +
			    "-fx-padding: 4 8 4 8;" +
			    "-fx-background-radius: 8;"
			  );
		
		streakTracker.setStyle(labelStyle);
		multiplierTracker.setStyle(labelStyle);
		
		
		
	}
	
	private void loadBackSound() {
		String soundURL = "sounds/go_back_sound.mp3";
		backSoundMedia = new Media(new File(soundURL).toURI().toString());
		backSoundPlayer = new MediaPlayer(backSoundMedia);
	}
	
	private void playBackSound() {
		backSoundPlayer.seek(Duration.ZERO);
		backSoundPlayer.play();
	}
	
	private void updateStreakTracker() {
		streakTracker.setText("Current Answer Streak: " + answerStreak);
		multiplierTracker.setText("Multiplier x" + multiplier);
	}
	
	private void createHoverEffect(Button button) {
		
		DropShadow shadow = new DropShadow(10, Color.BLACK);
		
		button.setOnMouseEntered(e -> {
			ScaleTransition scale = new ScaleTransition(Duration.millis(200), button);
			scale.setToX(1.05);
			scale.setToY(1.05);
			scale.play();
			button.setEffect(shadow);
		});
		
		button.setOnMouseExited(e -> {
			ScaleTransition scale = new ScaleTransition(Duration.millis(200), button);
			scale.setToX(1);
			scale.setToY(1);
			scale.play();
			button.setEffect(null);
		});
		
	}
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/survival_background.png");
		
		ImageView background = new ImageView(backgroundImage);
		
		background.setFitWidth(1000);
		background.setFitHeight(800);
		
		this.getChildren().add(0, background);
		
	}
	
	private void showSurvivalMode() {
		Scene scene = new Scene(this, 1000, 700);
		survivalStage = new Stage();
		survivalStage.setTitle("Survival Mode");
		survivalStage.setScene(scene);
		survivalStage.show();
	}
	
	
}
	
	
	

