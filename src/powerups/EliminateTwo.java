package powerups;

import java.io.File;

import javafx.scene.control.Tooltip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import screens.QuestionScreen;

public class EliminateTwo extends PowerUp{
	
	private QuestionScreen questionScreen;
	
	private Media eliminateTwoMedia;
	private MediaPlayer eliminateTwoPlayer;
	
	public EliminateTwo() {
		super("file:images/eliminate_two.png");
		loadEliminateTwoSound();
		tooltip = new Tooltip("Eliminate Two");
		Tooltip.install(getButton(), tooltip);
	}
	
	public void setQuestionScreen(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
	}
	
	private void loadEliminateTwoSound() {
		String soundURL = "sounds/eliminate_two_sound.mp3";
		eliminateTwoMedia = new Media(new File(soundURL).toURI().toString());
		eliminateTwoPlayer = new MediaPlayer(eliminateTwoMedia);
	}
	
	private void playEliminateTwoSound() {
		eliminateTwoPlayer.seek(Duration.ZERO);
		eliminateTwoPlayer.play();
	}
	
	public void activatePowerUp() {
		
		if(!isUsed) {
			playEliminateTwoSound();
			questionScreen.eliminateTwo();
			getButton().setDisable(true);
			isUsed = true;
		}
		
	}

}
