package screens;

import java.io.File;

import classes.Player;
import db.DatabaseManager;
import gamemodes.Blitz;
import gamemodes.Classic;
import gamemodes.Survival;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
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
	private Button btnLogout = new Button("Logout");
	private Button btnExit = new Button("Exit");
	private Stage gameOverStage;
	
	private Media gameOverSoundMedia;
	private MediaPlayer gameOverSoundPlayer;
	
	private Media highScoreMedia;
	private MediaPlayer highScorePlayer;
	
	public GameOver(Player player, int score, String gameMode) {
		
		
		this.player = player;
		this.score = score;
		this.gameMode = gameMode;
		loadGameOverSound();
		loadHighScoreSound();
		setBackground();
		createTopSection();
		createCenterSection();
		createBottomSection();
		createPlayAgainButtonListeners();
		createModeButtonListeners();
		createLogoutButtonListeners();
		createExitButtonListeners();
		styleButtons();
		showGameOverScreen();		
	}
	
	private void createPlayAgainButtonListeners() {
		
		btnAgain.setOnAction(e -> {
			int gameID = DatabaseManager.startNewGame(player, gameMode);
			close();
			
			switch(gameMode) {
				case "Classic":
					new Classic(gameID, player);
					break;
				case "Survival":
					new Survival(gameID, player);
					break;
				case "Blitz":
					new Blitz(gameID, player);
			}
			
		});
	}
	
	private void createModeButtonListeners() {
		
		btnMode.setOnAction(e -> {
			close();
			new GameModeSelection(player);
		});
	}

	private void createLogoutButtonListeners() {
		
		btnLogout.setOnAction(e -> {
			close();
			new Welcome();
		});
		
	}
	
	private void createExitButtonListeners() {
		
		btnExit.setOnAction(e -> close());
	}

	private void createTopSection() {
		
		String header = "GAME OVER";
		Label subHeader;

		HBox headerCtn = new HBox();
		headerCtn.setAlignment(Pos.CENTER);
		
		for(int i = 0; i < header.length(); i++) {
			
			char letter = header.charAt(i);
			Text text = new Text(String.valueOf(letter));
			text.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
			text.setFill(Color.WHITE);
			text.setTranslateY(-300);
			
			TranslateTransition fallTrans = new TranslateTransition(Duration.seconds(1 + Math.random()), text);
			fallTrans.setToY(0);
			fallTrans.play();
			
			headerCtn.getChildren().add(text);
		}

		int highScore = DatabaseManager.getPlayerHighScore(player.getPlayerID(), gameMode);
		
		if(highScore == score && highScore != 0) {
			playHighScoreSound();
			subHeader = new Label("NEW High Score! Congrats " + player.getUsername());
			headerCtn.getChildren().add(subHeader);
		}else {
			playGameOverSound();
			subHeader = new Label("Better Luck Next Time!");
		}
		
		subHeader.setTextFill(Color.WHITE);
		subHeader.setFont(Font.font("Verdana", FontWeight.BOLD, 24));
		subHeader.setOpacity(0);
		
		
		VBox topCtn = new VBox(10, headerCtn, subHeader);
		topCtn.setAlignment(Pos.CENTER);
		topCtn.setPadding(new Insets(30, 0, 0, 0));
		
		this.setTop(topCtn);	
		
		PauseTransition delay = new PauseTransition(Duration.seconds(1));
		delay.setOnFinished(e -> {
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), subHeader);
			fadeIn.setToValue(1);
			fadeIn.play();
		});
		delay.play();
		
		
	}
	
	private void createCenterSection() {
		
		VBox centerCtn = new VBox(20, btnAgain, btnMode);
		
		centerCtn.setAlignment(Pos.CENTER);
		
		this.setCenter(centerCtn);
		
	}
	
	private void createBottomSection() {
	
		HBox logoutCtn = new HBox(10, btnLogout, btnExit);
		
		logoutCtn.setPadding(new Insets(20));
		logoutCtn.setAlignment(Pos.CENTER_RIGHT);
		
		this.setBottom(logoutCtn);
		
	}
	
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/over_background.png");
		
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
		
		BackgroundImage gameOverBackground = new BackgroundImage(
			backgroundImage,
			BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,
			backgroundSize
			);
		this.setBackground(new Background(gameOverBackground));
	}
	
	private void styleButtons() {
		
		btnAgain.setFont(Font.font("Verdana", 28));
		btnMode.setFont(Font.font("Verdana", 28));
		
		btnLogout.setFont(Font.font("Verdana", 14));
		btnExit.setFont(Font.font("Verdana", 14));
		
		
		btnAgain.setPrefSize(400, 100);
		btnMode.setPrefSize(400, 100);
		
		btnLogout.setPrefSize(100, 50);
		btnExit.setPrefSize(100, 50);
		
		createHoverEffect(btnAgain);
		createHoverEffect(btnMode);
		createHoverEffect(btnLogout);
		createHoverEffect(btnExit);
		
		btnAgain.setStyle("-fx-background-color: linear-gradient(#ff9800, #f57c00); -fx-text-fill: white; -fx-background-radius: 12;");
		btnMode.setStyle("-fx-background-color: linear-gradient(#2979ff, #1565c0); -fx-text-fill: white; -fx-background-radius: 12;");
		btnLogout.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-background-radius: 8; -fx-border-color: red; -fx-border-radius: 8; -fx-border-width: 2;");
		btnExit.setStyle("-fx-background-color: transparent; -fx-border-color: red; -fx-text-fill: red; -fx-background-radius: 8; -fx-border-radius: 8; -fx-border-width: 2;");
		
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
		btnLogout.setDisable(true);
		btnExit.setDisable(true);
		
	}
	
	private void enableButtons() {
	
		btnAgain.setDisable(false);
		btnMode.setDisable(false);
		btnLogout.setDisable(false);
		btnExit.setDisable(false);
	}
	
	private void loadGameOverSound() {
		String soundURL = "sounds/game_over.mp3";
		gameOverSoundMedia = new Media(new File(soundURL).toURI().toString());
		gameOverSoundPlayer = new MediaPlayer(gameOverSoundMedia);
	}
	
	private void playGameOverSound() {
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
