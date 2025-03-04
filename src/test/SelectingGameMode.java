package test;

import javafx.application.Application;
import javafx.stage.Stage;
import screens.Welcome;

public class SelectingGameMode extends Application{

	
	
	public void start(Stage primaryStage) throws Exception {
		new Welcome();
		
	}


	public static void main(String[] args) {
		launch(args);

	}



}
