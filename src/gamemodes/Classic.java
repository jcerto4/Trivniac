package gamemodes;

import java.io.File;

import classes.Player;
import db.DatabaseManager;
import gameobjects.LeaderBoard;
import gameobjects.Wheel;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import powerups.DoubleChance;
import powerups.EliminateTwo;
import powerups.StopTimer;
import screens.GameModeSelection;
import screens.GameOver;
import screens.QuestionScreen;
import screens.Welcome;

public class Classic extends BorderPane{
	
	private Stage classicStage;
	private Wheel wheel;
	private Button btnSpin = new Button("SPIN");
	private HBox livesCtn = new HBox();

	private int lives = 3;
	private int score = 0;
	private int gameID;
	private Player player;
	
	private Media loseLifeMedia;
	private MediaPlayer loseLifePlayer;
	
	private DoubleChance doubleChance;
	private EliminateTwo elimTwo;
	private StopTimer stopTimer;
	
	private Button btnBack = new Button("Back");
	
	private Media backSoundMedia;
	private MediaPlayer backSoundPlayer;
	
		
	public Classic(int gameID, Player player) {
		
		loadLoseLifeSound();
		loadBackSound();
		this.gameID = gameID;
		this.player = player;
		wheel = new Wheel();
		
		doubleChance = new DoubleChance();
		elimTwo = new EliminateTwo();
		stopTimer = new StopTimer();
		
		setBackground();
		createTopSection();
		createCenterSection();
		
		createBottomSection();
		createSpinButtonListeners();
		createBackButtonListeners();
		styleButtons();
		showClassicMode();
	}
	
	private void createBackButtonListeners() {
		btnBack.setOnAction(e -> {
			playBackSound();
			classicStage.close();
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
		
		livesCtn.setAlignment(Pos.CENTER);
		livesCtn.setSpacing(20);
		
		for(int i = 0; i < 3; i++) {
			ImageView brain = new ImageView(new Image("file:images/brain.png"));
			brain.setFitWidth(125);
			brain.setFitHeight(125);
			livesCtn.getChildren().add(brain);
		}
		
		this.setTop(livesCtn);
		
	}
	
	private void createCenterSection() {
		
		VBox centerCtn = new VBox(10, wheel, btnSpin);
		centerCtn.setAlignment(Pos.CENTER);
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
			score += 10;
			DatabaseManager.updateScore(gameID, score);
			btnSpin.setDisable(false);
		}else {
			if(!livesCtn.getChildren().isEmpty()) {
				playLoseLife();
				animateLifeLoss(livesCtn.getChildren().get(0));
				lives--;
			}
			
			if(lives > 0) {
				btnSpin.setDisable(false);
			}else {
				
				Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1.75), e -> {
					
					classicStage.close();
					new GameOver(player, score, "Classic");
					
				}));
				
				timeLine.play();
			}
		}
	}
	
	private void animateLifeLoss(Node node) {
		
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), node);
		fadeOut.setFromValue(1.0);
		fadeOut.setToValue(0.0);
		fadeOut.setOnFinished(e -> livesCtn.getChildren().remove(0));
		fadeOut.play();
		
	}
	
	private void styleButtons() {
		
		btnSpin.setPrefSize(200, 50);
		btnBack.setPrefSize(100, 40);
		btnSpin.setFont(Font.font("Georgia", 32));
		createHoverEffect(btnSpin);
		createHoverEffect(btnBack);
		
		btnSpin.setStyle( 
				"-fx-background-color: linear-gradient(to bottom, #ffe066, #ffcc00);" +
			    "-fx-text-fill: #4b3600;" +
			    "-fx-background-radius: 15;" +
			    "-fx-border-radius: 15;" +
			    "-fx-border-color: #b38f00;" +
			    "-fx-border-width: 2;"
			   );
		
		btnBack.setStyle(
				"-fx-background-color: linear-gradient(#616161, #424242);" +
				"-fx-text-fill: white;" +
				"-fx-font-weight: bold;" +
				"-fx-background-radius: 12;" 
			);
		
	}
	
	private void loadLoseLifeSound() {
		String soundURL = "sounds/lose_life.mp3";
		loseLifeMedia = new Media(new File(soundURL).toURI().toString());
		loseLifePlayer = new MediaPlayer(loseLifeMedia);
	}
	
	private void playLoseLife() {
		loseLifePlayer.seek(Duration.ZERO);
		loseLifePlayer.play();
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
		
		Image backgroundImage = new Image("file:images/classic_background.png");
		
		ImageView background = new ImageView(backgroundImage);
		
		background.setFitWidth(1000);
		background.setFitHeight(800);
		
		this.getChildren().add(0, background);
		
	}
	
	private void showClassicMode() {
		Scene scene = new Scene(this, 1000, 700);
		classicStage = new Stage();
		classicStage.setTitle("Classic Mode");
		classicStage.setScene(scene);
		classicStage.show();
	}
	
	
}
