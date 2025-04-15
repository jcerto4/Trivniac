package screens;

import java.io.File;

import classes.Player;
import db.DatabaseManager;
import gamemodes.Blitz;
import gamemodes.Classic;
import gamemodes.Survival;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameOver extends BorderPane{

	
	private int score;
	private Player player;
	private String gameMode;
	private Button btnAgain = new Button("Play Again");
	private Button btnMode = new Button("New Mode");
	private Button btnExit = new Button("Exit");
	private Button btnLeaderboard = new Button("Leaderboards");
	private Stage gameOverStage;
	
	private Media gameOverSoundMedia;
	private MediaPlayer gameOverSoundPlayer;
	
	private Media highScoreMedia;
	private MediaPlayer highScorePlayer;
	
	private Media logoutSoundMedia;
	private MediaPlayer logoutSoundPlayer;
	
	private Media classicSoundMedia;
	private MediaPlayer classicSoundPlayer;
	
	private Media survivalSoundMedia;
	private MediaPlayer survivalSoundPlayer;
	
	private Media blitzSoundMedia;
	private MediaPlayer blitzSoundPlayer;
	
	private Media lbSoundMedia;
	private MediaPlayer lbSoundPlayer;
	
	private Media modeSoundMedia;
	private MediaPlayer modeSoundPlayer;
	
	
	public GameOver(Player player, int score, String gameMode) {
		
		
		this.player = player;
		this.score = score;
		this.gameMode = gameMode;
		loadGameOverSound();
		loadClassicSound();
		loadSurvivalSound();
		loadBlitzSound();
		loadHighScoreSound();
		loadLeaderboardSound();
		loadModeSound();
		setBackground();
		createTopSection();
		createCenterSection();
		createBottomSection();
		createPlayAgainButtonListeners();
		createModeButtonListeners();
		createLeaderboardButtonListeners();
		createExitButtonListeners();
		styleButtons();
		showGameOverScreen();		
	}
	
	private void createLeaderboardButtonListeners() {
		
		btnLeaderboard.setOnAction(e -> {
			playLeaderboardSound();
			close();
			new LeaderboardScreen(player);
		});
		
	}
	
	private void createPlayAgainButtonListeners() {
		
		btnAgain.setOnAction(e -> {
			int gameID = DatabaseManager.startNewGame(player, gameMode);
			disableButtons();
			switch(gameMode) {
				case "Classic":
					playClassicSound();
					Timeline timeLine1 = new Timeline(new KeyFrame(Duration.seconds(0.75), event -> {
						close();
						new Classic(gameID, player);
						
					}));
						timeLine1.play();
					break;
				case "Survival":
					playSurvivalSound();
					Timeline timeLine2 = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
						close();
						new Survival(gameID, player);
						
				}));
					timeLine2.play();
					break;
				case "Blitz":
					playBlitzSound();
					Timeline timeLine3 = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
						close();
						new Blitz(gameID, player);
						
					}));
					
					timeLine3.play();
			}
			
		});
	}
	
	private void createModeButtonListeners() {
		
		btnMode.setOnAction(e -> {
			playModeSound();
			close();
			new GameModeSelection(player);
		});
	}
	
	private void createExitButtonListeners() {
		
		btnExit.setOnAction(e -> close());
	}

	private void createTopSection() {
		
		disableButtons();
		
		Label subHeader;

		HBox headerCtn = new HBox();
		headerCtn.setAlignment(Pos.CENTER);
		
		int highScore = DatabaseManager.getPlayerHighScore(player.getPlayerID(), gameMode);
		
		if(highScore == score && highScore != 0) {
			playHighScoreSound();
			subHeader = new Label("NEW High Score! Congrats " + player.getUsername());
		}else {
			playGameOverSound();
			subHeader = new Label("Better Luck Next Time!");
		}
		
		subHeader.setStyle(	
				"-fx-background-color: transparent;" +
				"-fx-text-fill: white;" +
				"-fx-font-size: 36px;" +
				"-fx-font-family: 'Poppins';" +  
				"-fx-font-weight: bold;" 
			);
		
		headerCtn.getChildren().add(subHeader);
		headerCtn.setPadding(new Insets(50, 0, 0, 0));
		
		FadeTransition fade = new FadeTransition(Duration.seconds(2), headerCtn);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.play();
		
		enableButtons();
		
		this.setTop(headerCtn);	
		
		
	}
	
	private void createCenterSection() {
		
		VBox centerCtn = new VBox(20, btnAgain, btnMode);
		
		centerCtn.setAlignment(Pos.CENTER);
		
		this.setCenter(centerCtn);
		
	}
	
	private void createBottomSection() {
	
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		HBox bottomCtn = new HBox(btnLeaderboard, spacer, btnExit);
		bottomCtn.setAlignment(Pos.CENTER);
		bottomCtn.setPadding(new Insets(20));
		
		this.setBottom(bottomCtn);
		
	}
	
	
	private void styleButtons() {
		
		btnExit.setFont(Font.font("Verdana", 14));
		
		btnAgain.setPrefSize(400, 100);
		btnMode.setPrefSize(400, 100);
		
		btnExit.setPrefSize(100, 50);
		
		createHoverEffect(btnAgain);
		createHoverEffect(btnMode);
		createHoverEffect(btnExit);
		createHoverEffect(btnLeaderboard);
		
		btnAgain.setStyle(
				"-fx-background-color: linear-gradient(#ff9500, #ff6f00); "
				+ "-fx-text-fill: white; "
				+ "-fx-font-weight: bold; "
				+ "-fx-background-radius: 16; "
				+ "-fx-border-radius: 16; "
				+ "-fx-font-size: 28px;");
		
		btnMode.setStyle(
				"-fx-background-color: linear-gradient(#2196f3, #1565c0); "
				+ "-fx-text-fill: white;  "
				+ "-fx-font-weight: bold; "
				+ "-fx-background-radius: 16; "
				+ "-fx-border-radius: 16; "
				+ "-fx-font-size: 28px;");
		
		btnExit.setStyle(
				"-fx-background-color: transparent; "
				+ "-fx-border-color: white; "
				+ "-fx-text-fill: white; "
				+ "-fx-background-radius: 8; "
				+ "-fx-border-radius: 8; "
				+ "-fx-border-width: 2;");
		
		btnLeaderboard.setStyle(
			"-fx-background-color: transparent;" +
			"-fx-border-color: white;" +
			"-fx-text-fill: white;" +
			"-fx-font-weight: bold;" +
			"-fx-border-radius: 12;" +
			"-fx-background-radius: 12;" +
			"-fx-border-width: 2.5px;" +
			"-fx-font-size: 16px;" +
			"-fx-cursor: hand;" +
			"-fx-padding: 8 20 8 20;" +
			"-fx-font-family: 'Verdana';"
			);
		
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
	
	private void disableButtons() {
		
		btnAgain.setDisable(true);
		btnMode.setDisable(true);
		btnExit.setDisable(true);
		btnLeaderboard.setDisable(true);
		
	}
	
	private void enableButtons() {
	
		btnAgain.setDisable(false);
		btnMode.setDisable(false);
		btnExit.setDisable(false);
		btnLeaderboard.setDisable(false);

	}
	
	private void loadGameOverSound() {
		String soundURL = "sounds/game_over.mp3";
		gameOverSoundMedia = new Media(new File(soundURL).toURI().toString());
		gameOverSoundPlayer = new MediaPlayer(gameOverSoundMedia);
	}
	
	private void playGameOverSound() {
		gameOverSoundPlayer.seek(Duration.ZERO);
		gameOverSoundPlayer.play();
	}
	
	private void loadHighScoreSound() {
		String soundURL = "sounds/high_score_sound.mp3";
		highScoreMedia = new Media(new File(soundURL).toURI().toString());
		highScorePlayer = new MediaPlayer(highScoreMedia);
	}
	
	private void playHighScoreSound() {
		highScorePlayer.seek(Duration.ZERO);
		highScorePlayer.play();
	}
	
	private void loadClassicSound() {
		String soundURL = "sounds/classic_sound.mp3";
		classicSoundMedia = new Media(new File(soundURL).toURI().toString());
		classicSoundPlayer = new MediaPlayer(classicSoundMedia);
	}
	
	private void loadSurvivalSound() {
		String soundURL = "sounds/survival_sound.mp3";
		survivalSoundMedia = new Media(new File(soundURL).toURI().toString());
		survivalSoundPlayer = new MediaPlayer(survivalSoundMedia);
	}
	
	private void loadBlitzSound() {
		String soundURL = "sounds/blitz_sound.mp3";
		blitzSoundMedia = new Media(new File(soundURL).toURI().toString());
		blitzSoundPlayer = new MediaPlayer(blitzSoundMedia);
	}
	
	private void loadLeaderboardSound() {
		String soundURL = "sounds/leaderboard_sound.mp3";
		lbSoundMedia = new Media(new File(soundURL).toURI().toString());
		lbSoundPlayer = new MediaPlayer(lbSoundMedia);
	}
	
	private void loadModeSound() {
		String soundURL = "sounds/mode_sound.mp3";
		modeSoundMedia = new Media(new File(soundURL).toURI().toString());
		modeSoundPlayer = new MediaPlayer(modeSoundMedia);
	}
	
	private void playClassicSound() {
		classicSoundPlayer.seek(Duration.ZERO);
		classicSoundPlayer.play();
	}
	
	private void playSurvivalSound() {
		survivalSoundPlayer.seek(Duration.ZERO);
		survivalSoundPlayer.play();
	}
	
	private void playBlitzSound() {
		blitzSoundPlayer.seek(Duration.ZERO);
		blitzSoundPlayer.play();
	}
	
	private void playLeaderboardSound() {
		lbSoundPlayer.seek(Duration.ZERO);
		lbSoundPlayer.play();
	}
	
	private void playModeSound() {
		modeSoundPlayer.seek(Duration.ZERO);
		modeSoundPlayer.play();
	}
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/over_background.png");
		
		ImageView background = new ImageView(backgroundImage);
		background.setTranslateY(-70);
		
		getChildren().add(0, background);
		
	}
	
	private void showGameOverScreen() {
		Scene scene = new Scene(this, 1000, 700);
		gameOverStage = new Stage();
		gameOverStage.setTitle("Game Over Screen");
		gameOverStage.setScene(scene);
		gameOverStage.show();
	}
	
	private void close() {
		gameOverStage.close();
	}
	
	
	
	
}
