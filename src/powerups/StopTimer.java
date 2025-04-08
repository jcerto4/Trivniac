package powerups;

import java.io.File;

import javafx.scene.control.Tooltip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import screens.QuestionScreen;

public class StopTimer extends PowerUp{

	private QuestionScreen questionScreen;
	
	private Media stopTimerMedia;
	private MediaPlayer stopTimerPlayer;
	
	public StopTimer() {
		super("file:images/stop_timer.png");
		loadStopTimerSound();
		tooltip = new Tooltip("Stop Timer");
		Tooltip.install(getButton(), tooltip);
	}
	
	public void setQuestionScreen(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
	}
	
	private void loadStopTimerSound() {
		String soundURL = "sounds/stop_timer_sound.mp3";
		stopTimerMedia = new Media(new File(soundURL).toURI().toString());
		stopTimerPlayer = new MediaPlayer(stopTimerMedia);
	}
	
	private void playStopTimerSound() {
		stopTimerPlayer.seek(Duration.ZERO);
		stopTimerPlayer.play();
	}
	
	public void activatePowerUp() {
		
		if(!isUsed) {
			playStopTimerSound();
			questionScreen.stopTimer();
			getButton().setDisable(true);
			isUsed = true;
		}
		
	}
}
