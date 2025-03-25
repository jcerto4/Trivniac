package gamemodes;

import java.io.File;

import classes.Player;
import db.DatabaseManager;
import gameobjects.LeaderBoard;
import gameobjects.Wheel;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import screens.GameModeSelection;
import screens.GameOver;
import screens.QuestionScreen;
import screens.Welcome;

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
	
	private Button btnBack = new Button("Go Back");
	
		
	public Classic(int gameID, Player player) {
		
		loadLoseLifeSound();
		this.gameID = gameID;
		this.player = player;
		wheel = new Wheel();
		leaderboard = new LeaderBoard("Classic");
		setBackground();
		createTopSection();
		createCenterSection();
		createRightSection();
		createBottomSection();
		createSpinButtonListeners();
		createBackButtonListeners();
		styleButtons();
		showClassicMode();
	}
	
	private void createBackButtonListeners() {
		btnBack.setOnAction(e -> {
			classicStage.close();
			new GameModeSelection(player);
		});
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
	
	private void createRightSection() {
		leaderboard.setAlignment(Pos.CENTER_RIGHT);
		leaderboard.setPadding(new Insets(0, 20, 0, 0));
		this.setRight(leaderboard);
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
		});
	}
	
	private void handleQuestionResult(boolean isCorrect) {
		if(isCorrect) {
			score += 5;
			DatabaseManager.updateScore(gameID, score);
			leaderboard.refreshLeaderboard();
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
		btnSpin.setFont(Font.font("Georgia", 32));
	}
	
	private void loadLoseLifeSound() {
		String soundURL = "sounds/lose_life.mp3";
		loseLifeMedia = new Media(new File(soundURL).toURI().toString());
		loseLifePlayer = new MediaPlayer(loseLifeMedia);
	}
	
	private void playLoseLife() {
		//stopLoseLife();
		loseLifePlayer.seek(Duration.ZERO);
		loseLifePlayer.play();
	}
	
	private void stopLoseLife() {
		loseLifePlayer.stop();
	}
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/classic_background.jpg");
		
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
