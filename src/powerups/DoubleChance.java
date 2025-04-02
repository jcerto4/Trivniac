package powerups;

import java.io.File;

import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import screens.QuestionScreen;

public class DoubleChance extends PowerUp{
	
	private QuestionScreen questionScreen;
	
	private Media doubleChanceMedia;
	private MediaPlayer doubleChancePlayer;
	
	public DoubleChance() {
		super("file:images/double_chance.png");
		//loadDoubleChanceSound();
	}
	
	public void setQuestionScreen(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
	}
	
	private void loadDoubleChanceSound() {
		String soundURL = "sounds/double_chance_sound.mp3";
		doubleChanceMedia = new Media(new File(soundURL).toURI().toString());
		doubleChancePlayer = new MediaPlayer(doubleChanceMedia);
	}
	
	private void playDoubleChanceSound() {
		doubleChancePlayer.seek(Duration.ZERO);
		doubleChancePlayer.play();
	}
	
	public void activatePowerUp() {
		
		if(!isUsed) {
			//playDoubleChanceSound();
			questionScreen.enableDoubleChance();
			getButton().setDisable(true);
			isUsed = true;
		}
		
	}
	
}
