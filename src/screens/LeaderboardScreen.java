package screens;

import java.io.File;
import java.time.LocalDate;

import classes.Player;
import gameobjects.LeaderBoard;
import gameoutput.CSVManager;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LeaderboardScreen extends BorderPane{

	private Player player;
	private Stage leaderboardStage;
	
	private Button btnSave = new Button("Export My Stats");
	private Button btnSaveAll = new Button("Export Leaderboards");
	private Button btnBack = new Button("Go Back");
	
	private Label classicLabel;
	private Label survivalLabel;
	private Label blitzLabel;
	
	private Media fileSound;
	private MediaPlayer fileSoundPlayer;
	
	private LeaderBoard classicLB = new LeaderBoard("Classic");
	private LeaderBoard survivalLB = new LeaderBoard("Survival");
	private LeaderBoard blitzLB = new LeaderBoard("Blitz");
	
	public LeaderboardScreen(Player player) {
		
		this.player = player;
		
		loadFileSound();
		setBackground();
		createCenterSection();
		createBottomSection();
		createSaveButtonListeners();
		createSaveAllButtonListeners();
		createBackButtonListeners();
		styleButtons();
		
		showLeaderboardScreen();
		
	}
	
	private void createBackButtonListeners() {
		
		btnBack.setOnAction(e -> {
			leaderboardStage.close();
			new GameModeSelection(player);
		});
		
	}
	
	private void createSaveButtonListeners() {
		
		btnSave.setOnAction(e -> savePlayerStats());
		
	}
	
	private void createSaveAllButtonListeners() {
		
		btnSaveAll.setOnAction(e -> saveAllLeaderboards());
	}
	
	private void createCenterSection() {
		
		classicLabel = new Label("Classic Mode");
		survivalLabel = new Label("Survival Mode");
		blitzLabel = new Label("Blitz Mode");
		
		classicLabel.setAlignment(Pos.CENTER);
		survivalLabel.setAlignment(Pos.CENTER);
		blitzLabel.setAlignment(Pos.CENTER);
		
		VBox classicCtn = new VBox(10, classicLabel, classicLB);
		VBox survivalCtn = new VBox(10, survivalLabel, survivalLB);
		VBox blitzCtn = new VBox(10, blitzLabel, blitzLB);
		
		classicCtn.setAlignment(Pos.CENTER);
		survivalCtn.setAlignment(Pos.CENTER);
		blitzCtn.setAlignment(Pos.CENTER);
		
		HBox lbCtn = new HBox(10, classicCtn, survivalCtn, blitzCtn);
		lbCtn.setAlignment(Pos.CENTER);
		
		setCenter(lbCtn);
		
	}
	
	private void createBottomSection() {
		
		Region spacerLeft = new Region();
		Region spacerRight = new Region();
		HBox.setHgrow(spacerLeft, Priority.ALWAYS);
		HBox.setHgrow(spacerRight, Priority.ALWAYS);
		
		HBox backCtn = new HBox(btnBack);
		backCtn.setAlignment(Pos.BOTTOM_LEFT);
		backCtn.setPadding(new Insets(20));
		
		HBox btnCtn = new HBox(10, btnSave, btnSaveAll);
		btnCtn.setAlignment(Pos.CENTER);
		btnCtn.setPadding(new Insets(0, 70, 100, 0));
		
		HBox bottomCtn = new HBox(backCtn, spacerLeft, btnCtn, spacerRight);
		
		this.setBottom(bottomCtn);
	}
	
	private void savePlayerStats() {
		
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Save Your Stats");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("CSV Files", "*.csv")
			);
		
		fileChooser.setInitialFileName(player.getUsername() + "_scores_" + LocalDate.now() + ".csv");
		
		File file = fileChooser.showSaveDialog(leaderboardStage);
		
		if(file != null) {
			CSVManager.writePlayerCSVData(file.getAbsolutePath(), player);
			playFileSound();
		}
		
	}
	
	private void saveAllLeaderboards() {
		
		FileChooser fileChooser = new FileChooser();
		
		fileChooser.setTitle("Save Your Stats");
		fileChooser.getExtensionFilters().add(
				new FileChooser.ExtensionFilter("CSV Files", "*.csv")
			);
		
		fileChooser.setInitialFileName("trivniac_leaderboards_" + LocalDate.now() + ".csv");
		
		File file = fileChooser.showSaveDialog(leaderboardStage);
		
		if(file != null) {
			CSVManager.writeAllCSVData(file.getAbsolutePath());
			playFileSound();
		}
		
		
	}
	
	private void styleButtons() {
		
		String labelStyle = (
				"-fx-background-color: rgba(0,0,0,0.5);" +
			    "-fx-text-fill: white;" +
			    "-fx-font-weight: bold;" +
			    "-fx-padding: 4 8 4 8;" +
			    "-fx-background-radius: 8;"
			  );
		
		classicLabel.setStyle(labelStyle);
		survivalLabel.setStyle(labelStyle);
		blitzLabel.setStyle(labelStyle);
		
		
		createHoverEffect(btnSave);
		createHoverEffect(btnSaveAll);
		createHoverEffect(btnBack);
		
		String fileBtnStyle = (
				"-fx-background-color: linear-gradient(#3c3f41, #232526);" +
				"-fx-text-fill: white;" +
				"-fx-font-size: 18px;" +
				"-fx-font-weight: bold;" +
				"-fx-padding: 8 16 8 16;" +
				"-fx-background-radius: 10;" 
				); 
		
		btnSave.setStyle(fileBtnStyle);
		btnSaveAll.setStyle(fileBtnStyle);
		
		String backStyle = (
				"-fx-background-color: rgba(0,0,0,0.4);" +
				"-fx-text-fill: white;" +
				"-fx-font-size: 14px;" +
				"-fx-font-weight: bold;" +
				"-fx-padding: 6 14 6 14;" +
				"-fx-background-radius: 8;" +
				"-fx-border-color: white;" +
				"-fx-border-width: 1;" +
				"-fx-border-radius: 8;"
			);
		
		btnBack.setStyle(backStyle);
		
		
		
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
	
	private void loadFileSound() {
		
		String soundURL = "sounds/file_sound.mp3";
		fileSound = new Media(new File(soundURL).toURI().toString());
		fileSoundPlayer = new MediaPlayer(fileSound);
	}
	
	private void playFileSound() {
		fileSoundPlayer.seek(Duration.ZERO);
		fileSoundPlayer.play();
	}
	
	private void stopFileSound() {
		fileSoundPlayer.stop();
	}
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/leaderboard_background.png");
		
		ImageView background = new ImageView(backgroundImage);
		
		background.setFitWidth(1000);
		background.setFitHeight(800);
		
		this.getChildren().add(0, background);
	}
	
	private void showLeaderboardScreen() {
		Scene scene = new Scene(this, 1000, 700);
		leaderboardStage = new Stage();
		leaderboardStage.setTitle("Leaderboard Screen");
		leaderboardStage.setScene(scene);
		leaderboardStage.show();
	}
	
	
}
