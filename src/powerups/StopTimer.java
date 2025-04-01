package powerups;

import screens.QuestionScreen;

public class StopTimer extends PowerUp{

	private QuestionScreen questionScreen;
	
	public StopTimer() {
		super("file:images/stop_timer.png");
	}
	
	public void setQuestionScreen(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
	}
	
	public void activatePowerUp() {
		
		if(!isUsed) {
			questionScreen.enableStopTimer();
			questionScreen.stopTimer();
			getButton().setDisable(true);
			isUsed = true;
		}
		
	}
}
