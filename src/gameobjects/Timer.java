package gameobjects;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

/*
Timer Object Adapted From Professor Squires GameTimer COMP 271 BrookdaleCC 
 */


public class Timer extends HBox{
	
		private Text timerText;
		private int startSeconds;
		private int secondsLeft;
		private Timeline timeline;
		private boolean useTimer = true;
	
		private Runnable onTimerEnd;
		
		private Media clockMedia;
		private MediaPlayer clockMediaPlayer;
	
		public Timer(int seconds, Runnable onTimerEnd) {
			loadTimerSound();
			this.onTimerEnd = onTimerEnd;
			buildTimer(seconds);
		}
	
		private void buildTimer(int seconds) {
			this.startSeconds = seconds;
			this.secondsLeft = seconds;
	
			timerText = new Text(Integer.toString(seconds));
			this.getChildren().add(timerText);
			timerText.setTextAlignment(TextAlignment.CENTER);
	
			styleText();
			this.setAlignment(Pos.CENTER);
	
		}
	
		private void styleText() {
			timerText.setFont(Font.font("Impact", 80));
			timerText.setFill(Color.WHITE);
			DropShadow dropShadow = new DropShadow();
			timerText.setEffect(dropShadow);
		}
	
		public void startTimer() {
			if(timeline != null) {
				timeline.stop();
			}
			
			if (useTimer) {
				refreshTimerDisplay();
	
				timeline = new Timeline();
				timeline.setCycleCount(Timeline.INDEFINITE);
				timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
					// KeyFrame event handler
					@Override
					public void handle(ActionEvent event) {
						updateTimer();
	
					}
				}));
	
				timeline.playFromStart();
			}
		}
	
		private void updateTimer() {
			secondsLeft--;
			
			timerText.setText(Integer.toString(secondsLeft));
	
			if(secondsLeft <= 10) {
				playTickingSound();
				timerText.setFill(Color.RED);
			}else {
				stopTickingSound();
				clockMediaPlayer.seek(Duration.ZERO);
				timerText.setFill(Color.WHITE);
				
			}
			
			if (secondsLeft <= 0) {
				stopTickingSound();
				clockMediaPlayer.seek(Duration.ZERO);
				stopTimer();
				timeline.stop();
				if(onTimerEnd != null) {
					onTimerEnd.run();
				}
			}
	
		}
	
		public void refreshTimerDisplay() {
			secondsLeft = startSeconds;
			timerText.setText(Integer.toString(startSeconds));
		}
	
		public void stopTimer() {
			timeline.stop();
		}
		
		public int getSecondsLeft() {
			return secondsLeft;
		}
		
		public void setSecondsLeft(int seconds) {
			this.secondsLeft = seconds;
		}
		
		private void loadTimerSound() {
			String soundURL = "sounds/ticking_clock.mp3";
			clockMedia = new Media(new File(soundURL).toURI().toString());
			clockMediaPlayer = new MediaPlayer(clockMedia);
		}
		
		private void playTickingSound() {
			clockMediaPlayer.seek(Duration.ZERO);
			clockMediaPlayer.play();
		}
		
		public void stopTickingSound() {
			clockMediaPlayer.stop();
		}
		
	
	}
