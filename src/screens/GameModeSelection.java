package screens;

import java.io.File;

import classes.Player;
import db.DatabaseManager;
import gamemodes.Blitz;
import gamemodes.Classic;
import gamemodes.Survival;
import javafx.animation.ScaleTransition;
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
import javafx.stage.Stage;
import javafx.util.Duration;

public class GameModeSelection extends BorderPane{

	private Player player;
	
	private Stage gameModeStage;
	private Button btnClassic = new Button("Classic");
	private Button btnSurvival = new Button("Survival");
	private Button btnBlitz = new Button("Blitz");
	private Button btnExit = new Button("Exit");
	
	private Media gameModeMusicMedia;
	private MediaPlayer gameModeMusicPlayer;
	
	private Media entrySoundMedia;
	private MediaPlayer entrySoundPlayer;
	
	
	public GameModeSelection(Player player) {
		
		this.player = player;
		loadGameModeMusic();
		loadEntrySound();
		createTopSection();
		createCenterSection();
		createBottomSection();
		createGameModeSelectionButtonListeners();
		createExitButtonListeners();
		styleButtons();
		showGameModeSelection();
	}
	
	private void createGameModeSelectionButtonListeners() {
				
		btnClassic.setOnAction(e -> {
			playEntrySound();
			int gameID = DatabaseManager.startNewGame(player, "Classic");
			close();
			new Classic(gameID, player);
		});
		
		btnSurvival.setOnAction(e -> {
			playEntrySound();
			int gameID = DatabaseManager.startNewGame(player, "Survival");
			close();
			new Survival(gameID, player);
		});
		
		btnBlitz.setOnAction(e -> {
			playEntrySound();
			int gameID = DatabaseManager.startNewGame(player, "Blitz");
			close();
			//new Blitz(gameID, player);
		});
		
		
	}
	
	private void createExitButtonListeners() {
		btnExit.setOnAction(event -> close());
	}
	
	private void createTopSection() {
		
		
	}
	
	
	private void createCenterSection() {
		
		VBox modeCtn = new VBox(20, btnClassic, btnSurvival, btnBlitz);
		modeCtn.setAlignment(Pos.CENTER);
		this.setCenter(modeCtn);
		
	}
	
	private void createBottomSection() {
		
	
		
	}
	
	
	private void showGameModeSelection() {
		Scene scene = new Scene(this, 1200, 1000);
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
		
		btnExit.setPrefSize(100, 50);
		
		btnClassic.setPrefSize(500, 100);
		btnSurvival.setPrefSize(500, 100);
		btnBlitz.setPrefSize(500, 100);
		
		btnClassic.setFont(Font.font("Arial", 24));
		btnSurvival.setFont(Font.font("Arial", 24));
		btnBlitz.setFont(Font.font("Arial", 24));

		btnExit.setFont(Font.font("Arial", 14));
		
		createHoverEffect(btnClassic);
		createHoverEffect(btnSurvival);
		createHoverEffect(btnBlitz);
		createHoverEffect(btnExit);
	
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
	
	private void loadEntrySound() {
		String soundURL = "sounds/entry_sound.mp3";
		entrySoundMedia = new Media(new File(soundURL).toURI().toString());
		entrySoundPlayer = new MediaPlayer(entrySoundMedia);
	}
	
	private void playEntrySound() {
		entrySoundPlayer.play();
	}
	
	private void stopEntrySound() {
		entrySoundPlayer.stop();
	}
	
	private void loadGameModeMusic() {
		String soundURL = "sounds/welcome_music.mp3";
		gameModeMusicMedia = new Media(new File(soundURL).toURI().toString());
		gameModeMusicPlayer = new MediaPlayer(gameModeMusicMedia);
	}
	
	private void playGameModeMusic() {
		gameModeMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		gameModeMusicPlayer.play();
	}
	
	private void stopGameModeMusic() {
		gameModeMusicPlayer.stop();
	}
	
	
}
