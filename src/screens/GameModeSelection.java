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
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameModeSelection extends BorderPane{

	private Player player;
	
	private Stage gameModeStage;
	private Button btnClassic = new Button("Classic");
	private Button btnSurvival = new Button("Survival");
	private Button btnBlitz = new Button("Blitz");
	private Button btnBack = new Button("Logout");
	
	private Media gameModeMusicMedia;
	private MediaPlayer gameModeMusicPlayer;
	
	private Media classicSoundMedia;
	private MediaPlayer classicSoundPlayer;
	
	private Media survivalSoundMedia;
	private MediaPlayer survivalSoundPlayer;
	
	private Media blitzSoundMedia;
	private MediaPlayer blitzSoundPlayer;
	
	
	public GameModeSelection(Player player) {
		
		this.player = player;
		
		loadClassicSound();
		loadSurvivalSound();
		loadBlitzSound();
		
		setBackground();
		createTopSection();
		createCenterSection();
		createBottomSection();
		createGameModeSelectionButtonListeners();
		createBackButtonListeners();
		styleButtons();
		
		showGameModeSelection();
	}
	
	private void createGameModeSelectionButtonListeners() {
				
		btnClassic.setOnAction(e -> {
			disableButtons();
			playClassicSound();
			
			Timeline timeLine = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {

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
		btnBack.setOnAction(event -> new Login());
	}
	
	private void createTopSection() {
		
		Label welcome = new Label("Welcome " + player.getUsername());
		welcome.setFont(Font.font("Verdana", FontWeight.BOLD, 48));
		welcome.setTextFill(Color.WHITE);
		
		Label header = new Label("Choose Your Game Mode");
		header.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
		header.setTextFill(Color.WHITE);
		
		VBox headerCtn = new VBox(10, welcome, header);
		
		headerCtn.setPadding(new Insets(50, 0, 0, 0));
		headerCtn.setAlignment(Pos.CENTER);
		
		this.setTop(headerCtn);
	}
	
	
	private void createCenterSection() {
		
		VBox modeCtn = new VBox(20, btnClassic, btnSurvival, btnBlitz);
		modeCtn.setAlignment(Pos.CENTER);
		this.setCenter(modeCtn);
		
	}
	
	private void createBottomSection() {
		
		HBox bottomCtn = new HBox(btnBack);
		bottomCtn.setAlignment(Pos.CENTER_RIGHT);
		bottomCtn.setPadding(new Insets(30));
		
		this.setBottom(bottomCtn);
		
	}
	
	
	private void showGameModeSelection() {
		Scene scene = new Scene(this, 1000, 700);
		gameModeStage = new Stage();
		gameModeStage.setTitle("Welcome Screen");
		gameModeStage.setScene(scene);
		gameModeStage.show();
	}
	
	private void close() {
		gameModeStage.close();
	}
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/mode_background.jpg");
		
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
		
		 String buttonStyle = "-fx-background-color: #007BFF;" +
                 "-fx-text-fill: white;" +
                 "-fx-font-size: 28px;" +
                 "-fx-font-weight: bold;" +
                 "-fx-background-radius: 15px;" +
                 "-fx-border-radius: 15px;" +
                 "-fx-border-color: white;" +
                 "-fx-border-width: 2px;";
		
		btnClassic.setStyle(buttonStyle);
		btnSurvival.setStyle(buttonStyle);
		btnBlitz.setStyle(buttonStyle);
		
		
		btnBack.setPrefSize(100, 50);
		
		btnClassic.setPrefSize(500, 100);
		btnSurvival.setPrefSize(500, 100);
		btnBlitz.setPrefSize(500, 100);
		
		btnClassic.setFont(Font.font("Arial", 28));
		btnSurvival.setFont(Font.font("Arial", 28));
		btnBlitz.setFont(Font.font("Arial", 28));

		btnBack.setFont(Font.font("Arial", 14));
		
		createHoverEffect(btnClassic);
		createHoverEffect(btnSurvival);
		createHoverEffect(btnBlitz);
		createHoverEffect(btnBack);
	
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
		btnClassic.setDisable(true);
		btnSurvival.setDisable(true);
		btnBlitz.setDisable(true);
		btnBack.setDisable(true);
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
	
	private void playClassicSound() {
		classicSoundPlayer.play();
	}
	
	private void playSurvivalSound() {
		survivalSoundPlayer.play();
	}
	
	private void playBlitzSound() {
		blitzSoundPlayer.play();
	}
	
	
}
