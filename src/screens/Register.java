package screens;

import classes.Player;
import db.DatabaseManager;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Register extends BorderPane{
	
	private Button btnRegister = new Button("Register");
	private Button btnBack = new Button("Go Back");
	private Button btnExit = new Button("Exit");
	private Stage registerStage;
	
	public Register() {
		
		setBackground();
		//createTopSection();
		createCenterSection();
		createBottomSection();
		createExitButtonListeners();
		createBackButtonListeners();
		styleButtons();
		showRegisterScreen();
		
	}
	private void createExitButtonListeners() {
		btnExit.setOnAction(e -> close());
	}
	
	private void createBackButtonListeners() {
		btnBack.setOnAction(e -> {
			registerStage.close();
			new Welcome();
		});
	}
	
	
	private void createTopSection() {
	
	}
	
	private void createCenterSection() {
		
		Text title = new Text("Create an Account to Play");
		title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 24));
		title.setFill(Color.WHITE);
		HBox titleCtn = new HBox(title);
		titleCtn.setAlignment(Pos.CENTER);
		
		Label userNameLabel = new Label("Username: ");
		userNameLabel.setTextFill(Color.WHITE);
		userNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		TextField userNameEntry = new TextField();
		userNameEntry.setPrefSize(250, 30);
		HBox userCtn = new HBox(10, userNameLabel, userNameEntry);
		userCtn.setAlignment(Pos.CENTER);
		
		Label passwordLabel = new Label("Password: ");
		passwordLabel.setTextFill(Color.WHITE);
		passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		PasswordField passwordEntry = new PasswordField();
		passwordEntry.setPrefSize(250, 30);
		HBox passCtn = new HBox(10, passwordLabel, passwordEntry);
		passCtn.setAlignment(Pos.CENTER);
		
		
		
		
		btnRegister.setOnAction(e -> {
			if(userNameEntry.getText().isEmpty() || passwordEntry.getText().isEmpty()) {
				showAlert(Alert.AlertType.WARNING, "Empty Field", "Please enter both username and password");
			}else {
				Player player = DatabaseManager.getPlayer(userNameEntry.getText());
				
				if(player == null) {
					DatabaseManager.insertNewPlayer(userNameEntry.getText(), passwordEntry.getText());
					Player newUser = DatabaseManager.getPlayer(userNameEntry.getText());
					registerStage.close();
					new GameModeSelection(newUser);
				} else {
					showAlert(AlertType.ERROR, "Registration Error", "This username is already taken");
				}
			}
			
			
		});
			
			
		
		
		HBox btnCtn = new HBox(btnRegister);
		btnCtn.setAlignment(Pos.CENTER);
		btnCtn.setPadding(new Insets(0, 0, 0, 100));
		
		VBox registerCtn = new VBox(10, titleCtn, userCtn, passCtn, btnCtn);
		registerCtn.setAlignment(Pos.CENTER);
		registerCtn.setTranslateY(100);

		
		this.setCenter(registerCtn);
		
	}
	
	private void createBottomSection() {
	
		HBox exitCtn = new HBox(20, btnBack, btnExit);
		exitCtn.setAlignment(Pos.BOTTOM_RIGHT);
		exitCtn.setPadding(new Insets(10, 10, 10, 10));
		this.setBottom(exitCtn);
		
	}
	
	private void createHoverEffect(Button button) {
		
		DropShadow shadow = new DropShadow(10, Color.BLACK);
		
		button.setOnMouseEntered(e -> {
			ScaleTransition scale = new ScaleTransition(Duration.millis(200), button);
			scale.setToX(1.05);
			scale.setToY(1.05);
			scale.play();
			button.setEffect(shadow);
		});
		
		button.setOnMouseExited(e -> {
			ScaleTransition scale = new ScaleTransition(Duration.millis(200), button);
			scale.setToX(1);
			scale.setToY(1);
			scale.play();
			button.setEffect(null);
		});
		
	}
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/register_background.png");
		
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
		
		BackgroundImage registerBackground = new BackgroundImage(
			backgroundImage,
			BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,
			backgroundSize
			);
		this.setBackground(new Background(registerBackground));
	}
	
	private void showAlert(Alert.AlertType type, String title, String message) {
		
		Alert alert = new Alert(type);
		alert.initOwner(registerStage);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
		
		
		
	}
	
	
	private void styleButtons() {
		
		btnRegister.setFont(Font.font("Arial", 20));
		
		btnExit.setPrefSize(100, 50);
		btnExit.setFont(Font.font("Arial", 14));
		
		btnBack.setPrefSize(100, 50);
		btnBack.setFont(Font.font("Arial", 14));
		
		createHoverEffect(btnRegister);
		createHoverEffect(btnExit);
		createHoverEffect(btnBack);
		
		btnRegister.setStyle(
				"-fx-background-color: linear-gradient(to bottom, #f7c47b, #e3a752);" +  
			    "-fx-text-fill: #3b2f26;" +                                              
			    "-fx-font-weight: bold;" +
			    "-fx-background-radius: 8;" +
			    "-fx-border-radius: 8;" +
			    "-fx-border-color: #b67c43;" +
			    "-fx-border-width: 1;"
			   );

		btnExit.setStyle(
				"-fx-background-color: linear-gradient(to bottom, #cc8e8e, #a76d6d);" +  
				"-fx-text-fill: #2a2a2a;" +
				"-fx-font-weight: bold;" +
				"-fx-background-radius: 6;" +
				"-fx-border-radius: 6;" +
				"-fx-border-color: #8a8a8a;" +
				"-fx-border-width: 1;"
				);
		
		btnBack.setStyle(	
				"-fx-background-color: linear-gradient(to bottom, #a6c2cb, #8fa9b3);" +  
				"-fx-text-fill: #2a2a2a;" +
				"-fx-font-weight: bold;" +
				"-fx-background-radius: 6;" +
				"-fx-border-radius: 6;" +
				"-fx-border-color: #8a8a8a;" +
				"-fx-border-width: 1;"
				);
		
	}	
	
	private void showRegisterScreen() {
		Scene scene = new Scene(this, 1000, 700);
		registerStage = new Stage();
		registerStage.setTitle("Register Screen");
		registerStage.setScene(scene);
		registerStage.show();
	}
	
	private void close() {
		registerStage.close();
	}

	
}
