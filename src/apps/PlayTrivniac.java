package apps;


import javafx.stage.Stage;
import screens.Welcome;
import javafx.application.Application;


public class PlayTrivniac extends Application{

	public void start(Stage stage) {
		new Welcome();
	}
	
	public static void main(String[] args) {
		launch(args);

	}

}
