package powerups;

import screens.QuestionScreen;

public class EliminateTwo extends PowerUp{
	
	private QuestionScreen questionScreen;
	
	public EliminateTwo() {
		super("file:images/eliminate_two.png");
	}
	
	public void setQuestionScreen(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
	}
	
	public void activatePowerUp() {
		
		if(!isUsed) {
			questionScreen.enableEliminateTwo();
			questionScreen.eliminateTwo();
			getButton().setDisable(true);
			isUsed = true;
		}
		
	}

}
