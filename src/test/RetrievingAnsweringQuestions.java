package test;

import java.util.Scanner;

import classes.Question;
import db.DatabaseManager;

public class RetrievingAnsweringQuestions {

	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		while(true) {
		System.out.println("Enter a category (History, Sports, Geography, Science, Pop-Culture, Wild) or 'exit' to leave: ");
		String category = input.nextLine();
		
		if(category.equals("exit")) {
			input.close();
			break;
		}
		
		Question question = DatabaseManager.getRandomQuestion(category);
		
		
		if(question != null) {
			System.out.println(question);
		}else {
			System.out.println("Where are you!");
		}
		
		System.out.println("Enter your choice as an integr (1, 2, 3, 4): ");
		int userChoice = input.nextInt();
		input.nextLine();
		
		if(userChoice == question.getCorrectAnswer()) {
			System.out.println("Correct!");
		}else {
			System.out.println("Incorrect!");
		}
		
		}

	}

}
