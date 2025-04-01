package powerups;

import javafx.scene.control.Button;
import screens.QuestionScreen;

public class DoubleChance extends PowerUp{
	
	private QuestionScreen questionScreen;
	
	public DoubleChance() {
		super("file:images/double_chance.png");
	}
	
	public void setQuestionScreen(QuestionScreen questionScreen) {
		this.questionScreen = questionScreen;
	}
	
	public void activatePowerUp() {
		
		if(!isUsed) {
			questionScreen.enableDoubleChance();
			getButton().setDisable(true);
			isUsed = true;
		}
		
	}
	
}
