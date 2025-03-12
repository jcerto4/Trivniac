package gamemodes;

import java.io.File;

import classes.Player;
import gameobjects.LeaderBoard;
import gameobjects.Timer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Blitz extends BorderPane{

	
	private int gameID;
	private Player player;
	private Stage blitzStage;
	private Timer timer;
	private LeaderBoard leaderboard;
	
	private Media correctSoundMedia;
	private MediaPlayer correctSoundPlayer;
	private Media incorrectSoundMedia;
	private MediaPlayer incorrectSoundPlayer;
	private Media blitzMusicMedia;
	private MediaPlayer blitzMusicPlayer;
	
	
	
	public Blitz(int gameID, Player player) {
		
		this.gameID = gameID;
		this.player = player;
		
		leaderboard = new LeaderBoard("Blitz");
		loadIncorrectSound();
		loadCorrectSound();
		loadBlitzMusic();
		
		createTopSection();
		createCenterSection();
		createRightSection();
		styleButtons();
		
		
		showBlitzScreen();
		
	}
	
	private void createTopSection() {
		
		timer = new Timer(60, () -> {
			
		});
		
		timer.setAlignment(Pos.CENTER);
		this.setTop(timer);
		timer.startTimer();
		
		
	}
	
	private void createCenterSection() {
		
		
		Text holder = new Text("Coming Soon");
		
		HBox centerCtn = new HBox(holder);
		
		centerCtn.setAlignment(Pos.CENTER);
		
		this.setCenter(centerCtn);
		
	}
	
	private void createRightSection() {
		leaderboard.setAlignment(Pos.CENTER_RIGHT);
		leaderboard.setPadding(new Insets(0, 20, 0, 0));
		this.setRight(leaderboard);
	}
	
	
	private void styleButtons() {
		
	}
	
	private void loadIncorrectSound() {
		String soundURL = "sounds/incorrect_sound.mp3";
		incorrectSoundMedia = new Media(new File(soundURL).toURI().toString());
		incorrectSoundPlayer = new MediaPlayer(incorrectSoundMedia);
	}
	
	private void playIncorrectSound() {
		incorrectSoundPlayer.play();
	}
	
	private void stopIncorrectSound() {
		incorrectSoundPlayer.stop();
	}
	
	private void loadCorrectSound() {
		String soundURL = "sounds/correct_sound.mp3";
		correctSoundMedia = new Media(new File(soundURL).toURI().toString());
		correctSoundPlayer = new MediaPlayer(correctSoundMedia);
	}
	
	private void playCorrectSound() {
		correctSoundPlayer.play();
	}
	
	private void stopCorrectSound() {
		correctSoundPlayer.stop();
	}
	
	private void loadBlitzMusic() {
		String soundURL = "sounds/question_music.mp3";
		blitzMusicMedia = new Media(new File(soundURL).toURI().toString());
		blitzMusicPlayer = new MediaPlayer(blitzMusicMedia);
	}
	
	private void playBlitzMusic() {
		blitzMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		blitzMusicPlayer.play();
	}
	
	private void stopBlitzMusic() {
		blitzMusicPlayer.stop();
	}
	
	private void showBlitzScreen() {
		Scene scene = new Scene(this, 1000, 700);
		blitzStage = new Stage();
		blitzStage.setTitle("Blitz Mode");
		blitzStage.setScene(scene);
		blitzStage.show();
	}
}
