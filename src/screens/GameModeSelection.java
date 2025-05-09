package screens;

import java.io.File;

import classes.Player;
import db.DatabaseManager;
import gamemodes.Blitz;
import gamemodes.Classic;
import gamemodes.Survival;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
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
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameModeSelection extends BorderPane{

	private Player player;
	
	private Stage gameModeStage;
	private Button btnClassic = new Button("Classic");
	private Button btnSurvival = new Button("Survival");
	private Button btnBlitz = new Button("Blitz");
	private Button btnBack = new Button("Logout");
	private Button btnLeaderboard = new Button("Leaderboards");
	
	private Media classicSoundMedia;
	private MediaPlayer classicSoundPlayer;
	
	private Media survivalSoundMedia;
	private MediaPlayer survivalSoundPlayer;
	
	private Media blitzSoundMedia;
	private MediaPlayer blitzSoundPlayer;
	
	private Media lbSoundMedia;
	private MediaPlayer lbSoundPlayer;
	
	private Media logoutSoundMedia;
	private MediaPlayer logoutSoundPlayer;
	
	public GameModeSelection(Player player) {
		
		this.player = player;
		
		loadClassicSound();
		loadSurvivalSound();
		loadBlitzSound();
		loadLeaderboardSound();
		loadLogoutSound();
		
		setBackground();
		createTopSection();
		createCenterSection();
		createBottomSection();
		createGameModeSelectionButtonListeners();
		createBackButtonListeners();
		createLeaderboardButtonListeners();
		styleButtons();
		
		showGameModeSelection();
	}
	
	private void createLeaderboardButtonListeners() {
		
		btnLeaderboard.setOnAction(e -> {
			playLeaderboardSound();
			close();
			new LeaderboardScreen(player);
		});
		
	}
	
	private void createGameModeSelectionButtonListeners() {
				
		btnClassic.setOnAction(e -> {
			disableButtons();
			playClassicSound();
			
			Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.75), event -> {

				int gameID = DatabaseManager.startNewGame(player, "Classic");
				close();
				new Classic(gameID, player);
				
			}));
				timeLine.play();
		});
		
		btnSurvival.setOnAction(e -> {
			disableButtons();
			playSurvivalSound();
			
			Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
				
				int gameID = DatabaseManager.startNewGame(player, "Survival");
				close();
				new Survival(gameID, player);
				
			}));
			
			timeLine.play();
		});
		
		btnBlitz.setOnAction(e -> {
			disableButtons();
			playBlitzSound();
			
			Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(3), event -> {
				int gameID = DatabaseManager.startNewGame(player, "Blitz");
				close();
				new Blitz(gameID, player);
				
			}));
			
			timeLine.play();
		});
		
	}

	private void createBackButtonListeners() {
		btnBack.setOnAction(event -> {
			playLogoutSound();
			close();
			new Welcome();
		});
	}
	
	private void createTopSection() {
		
		Label user = new Label("Signed in as: " + player.getUsername());
		user.setTextFill(Color.WHITE);
		
		user.setStyle("-fx-background-color: rgba(0, 0, 0, 0.35);"
                 + "-fx-background-radius: 8;"
                 + "-fx-padding: 4 10 4 10;"
                 + "-fx-text-fill: white;"
                 + "-fx-font-size: 12px;"
                 + "-fx-font-weight: bold;");
		
		HBox userCtn = new HBox(user);
		userCtn.setAlignment(Pos.TOP_RIGHT);
		userCtn.setPadding(new Insets(20));
		
		this.setTop(userCtn);
	}
	
	private void createCenterSection() {
		
		
		Label header = new Label("Select Your Game Mode");
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 42));
		header.setTextFill(Color.WHITE);
		
		ImageView classicIcon = new ImageView(new Image("file:images/classic_icon.png"));
		ImageView survivalIcon = new ImageView(new Image("file:images/survival_icon.png"));
		ImageView blitzIcon = new ImageView(new Image("file:images/blitz_icon.png"));
		
		classicIcon.setFitHeight(150);
		classicIcon.setFitWidth(150);
		survivalIcon.setFitHeight(150);
		survivalIcon.setFitWidth(150);
		blitzIcon.setFitHeight(150);
		blitzIcon.setFitWidth(150);
		
		classicIcon.setPreserveRatio(true);
		survivalIcon.setPreserveRatio(true);
		blitzIcon.setPreserveRatio(true);
		
		Tooltip classicTip = new Tooltip("Classic Mode: You have 3 lives. Try to answer as many questions as you can before losing them all!");
		Tooltip survivalTip = new Tooltip("Survival Mode: You only get 1 life. One wrong answer and it's game over!");
		Tooltip blitzTip = new Tooltip("Blitz Mode: 30 seconds to score big! +5s for correct, -3s for wrong.");
		
		HBox headerCtn = new HBox(header);
		HBox classicCtn = new HBox(10, classicIcon, btnClassic);
		HBox survivalCtn = new HBox(10, survivalIcon, btnSurvival);
		HBox blitzCtn = new HBox(10, blitzIcon, btnBlitz);
		
		Tooltip.install(classicIcon, classicTip);
		Tooltip.install(survivalIcon, survivalTip);
		Tooltip.install(blitzIcon, blitzTip);
		
		headerCtn.setAlignment(Pos.CENTER);
		classicCtn.setAlignment(Pos.CENTER);
		survivalCtn.setAlignment(Pos.CENTER);
		blitzCtn.setAlignment(Pos.CENTER);
		
		
		VBox modeCtn = new VBox(classicCtn, survivalCtn, blitzCtn);
		modeCtn.setAlignment(Pos.CENTER);
		VBox centerCtn = new VBox(headerCtn, modeCtn);
		centerCtn.setAlignment(Pos.CENTER);
		this.setCenter(centerCtn);
		
	}
	
	private void createBottomSection() {
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		HBox bottomCtn = new HBox(btnLeaderboard, spacer, btnBack);
		bottomCtn.setAlignment(Pos.CENTER_RIGHT);
		bottomCtn.setPadding(new Insets(20));
		
		this.setBottom(bottomCtn);
		
	}
	
	
	private void showGameModeSelection() {
		Scene scene = new Scene(this, 1000, 700);
		gameModeStage = new Stage();
		gameModeStage.setTitle("Game Mode Selection Screen");
		gameModeStage.setScene(scene);
		gameModeStage.show();
	}
	
	private void close() {
		gameModeStage.close();
	}
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/mode_background.png");
		
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
		
		BackgroundImage gameModeBackground = new BackgroundImage(
			backgroundImage,
			BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,
			backgroundSize
			);
		this.setBackground(new Background(gameModeBackground));
	}
	
	private void styleButtons() {
		
		
		btnClassic.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-family: 'Verdana'; -fx-font-size: 28px; -fx-background-radius: 15; -fx-font-weight: bold; -fx-cursor: hand;");
		btnSurvival.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-family: 'Verdana'; -fx-font-size: 28px; -fx-background-radius: 15; -fx-font-weight: bold; -fx-cursor: hand;");
		btnBlitz.setStyle("-fx-background-color: #FF5722; -fx-text-fill: white; -fx-font-family: 'Verdana'; -fx-font-size: 28px; -fx-background-radius: 15; -fx-font-weight: bold; -fx-cursor: hand;");
		
		btnBack.setStyle(
				"-fx-background-color: transparent;" +        
			    "-fx-text-fill: white;" +
			    "-fx-background-radius: 12;" +
			    "-fx-border-radius: 12;" +
			    "-fx-border-color: white;" +
			    "-fx-border-width: 2.5px;" +
			    "-fx-font-weight: bold;" +
			    "-fx-font-size: 16px;" +
			    "-fx-cursor: hand;" +
			    "-fx-padding: 8 20 8 20;" +
			    "-fx-font-family: 'Verdana';"
			    );
		
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
		
		btnClassic.setPrefSize(500, 100);
		btnSurvival.setPrefSize(500, 100);
		btnBlitz.setPrefSize(500, 100);

		//btnBack.setPadding(new Insets(20));
		
		createHoverEffect(btnClassic);
		createHoverEffect(btnSurvival);
		createHoverEffect(btnBlitz);
		createHoverEffect(btnBack);
		createHoverEffect(btnLeaderboard);
		
	
	}
	
	private void createHoverEffect(Button button) {
		
		DropShadow shadow = new DropShadow(10, Color.BLACK);
		
		button.setOnMouseEntered(e -> {
			ScaleTransition scale = new ScaleTransition(Duration.millis(200), button);
			scale.setToX(1.1);
			scale.setToY(1.1);
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
		btnClassic.setDisable(true);
		btnSurvival.setDisable(true);
		btnBlitz.setDisable(true);
		btnBack.setDisable(true);
		btnLeaderboard.setDisable(true);
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
	
	private void loadLogoutSound() {
		String soundURL = "sounds/logout_sound.mp3";
		logoutSoundMedia = new Media(new File(soundURL).toURI().toString());
		logoutSoundPlayer = new MediaPlayer(logoutSoundMedia);
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
	
	private void playLogoutSound() {
		logoutSoundPlayer.seek(Duration.ZERO);
		logoutSoundPlayer.play();
	}
	
	
}
