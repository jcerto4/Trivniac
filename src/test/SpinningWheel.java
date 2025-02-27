package test;

import javafx.stage.Stage;
import screens.Register;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.application.Application;
import javafx.geometry.Pos;
import gameobjects.Wheel;

public class SpinningWheel extends Application{

	
	public void start(Stage stage) {
		
		
		BorderPane testScreen = new BorderPane();
		
		Wheel wheel = new Wheel();
		Button btnSpin = new Button("Spin");
		
		//btnSpin.setOnAction(e -> wheel.spinWheel(String selectedCategory));
		
		HBox btnCtn = new HBox(btnSpin);
		btnCtn.setAlignment(Pos.CENTER);
		testScreen.setCenter(wheel);
		testScreen.setBottom(btnCtn);
	
		Scene scene = new Scene(testScreen, 600, 600);
		
		stage.setTitle("Spinning Wheel Test");
		stage.setScene(scene);
		stage.show();
		
	}

	
	public static void main(String[] args) {
		launch(args);
	}

}
