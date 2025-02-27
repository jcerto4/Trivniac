package test;

import javafx.application.Application;
import javafx.stage.Stage;
import screens.Login;
import screens.Register;
import screens.Welcome;

public class RegisterLoginWelcome extends Application{

	
	public void start(Stage primaryStage) throws Exception {
		
//		new Register();
//		new Login();
		new Welcome();
	}
	
	public static void main(String[] args) {
		launch(args);

	}

	

}
