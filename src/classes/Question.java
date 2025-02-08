package classes;

public class Question {

	private int questionID;
	private String category;
	private String questionText;
	private String[] options;
	private int correctAnswer;
	
	public Question(int questionID, String category, String questionText, String option1, String option2, String option3, String option4, int correctAnswer) {
		this.questionID = questionID;
		this.category = category;
		this.questionText = questionText;
		this.options = new String[] {option1, option2, option3, option4};
		this.correctAnswer = correctAnswer;
	}

	public int getQuestionID() {
		return questionID;
	}

	public String getCategory() {
		return category;
	}

	public String getQuestionText() {
		return questionText;
	}

	public String[] getOptions() {
		return options;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}
	
	public String toString() {
		return "Category: " + category + "\n" + 
				"Question: " + questionText + "\n" + 
				"1. " + options[0] + "\n" +
				"2. " + options[1] + "\n" +
				"3. " + options[2] + "\n" +
				"4. " + options[3] + "\n";
	}
}
