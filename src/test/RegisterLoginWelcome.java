package test;

import javafx.application.Application;
import javafx.stage.Stage;
import screens.Login;
import screens.Register;

public class RegisterLoginWelcome extends Application{

	
	public void start(Stage primaryStage) throws Exception {
		
		//new Register();
		new Login();
	}
	
	public static void main(String[] args) {
		launch(args);

	}

	

}
