package screens;

import classes.Player;
import db.DatabaseManager;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
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

public class Login extends BorderPane{

	private Button btnLogin = new Button("Login");
	private Button btnBack = new Button("Go Back");
	private Button btnExit = new Button("Exit");
	private Stage loginStage;
	
	public Login() {
		
		setBackground();
		createTopSection();
		createCenterSection();
		createBottomSection();
		createExitButtonListeners();
		createBackButtonListeners();
		styleButtons();
		showLoginScreen();
		
	}
	private void createExitButtonListeners() {
		btnExit.setOnAction(e -> close());
	}
	
	private void createBackButtonListeners() {
		btnBack.setOnAction(e -> {
			loginStage.close();
			new Welcome();
		});
	}
	
	
	private void createTopSection() {

		Image image = new Image("file:images/trivniac_logo.png");
		ImageView logo = new ImageView(image);
		
		logo.setFitWidth(500);
		logo.setPreserveRatio(true);
		
		StackPane topCtn = new StackPane(logo);
		
		//topCtn.setPadding(new Insets(10));
		
		topCtn.setAlignment(Pos.TOP_CENTER);
		
		this.setTop(topCtn);

		
	}
	
	private void createCenterSection() {
		
		Text title = new Text("Login to Trivniac");
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
		
		
		
		
		btnLogin.setOnAction(e -> {
			if(userNameEntry.getText().isEmpty() || passwordEntry.getText().isEmpty()) {
				showAlert(Alert.AlertType.WARNING, "Empty Field", "Please enter both username and password");
			}else {
				Player player = DatabaseManager.getPlayer(userNameEntry.getText());
				
				if(player == null) {
					showAlert(AlertType.ERROR, "Login Error", "No account found with this username");	
				}else {
					if(passwordEntry.getText().equals(player.getPassword())) {
						loginStage.close();
						//new GameModeSelection(player);
					}else {
						showAlert(AlertType.ERROR, "Login Error", "Incorrect Password");
					}
				}
			}
			
			
		});
			
			
		
		
		HBox btnCtn = new HBox(btnLogin);
		btnCtn.setAlignment(Pos.CENTER);
		btnCtn.setPadding(new Insets(0, 0, 0, 100));
		
		VBox registerCtn = new VBox(10, titleCtn, userCtn, passCtn, btnCtn);
		registerCtn.setAlignment(Pos.CENTER);
		
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
		
		Image backgroundImage = new Image("file:images/register_background.jpg");
		
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
		alert.initOwner(loginStage);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
		
		
		
	}
	
	
	private void styleButtons() {
		
		btnLogin.setFont(Font.font("Arial", 20));
		
		btnExit.setPrefSize(100, 50);
		btnExit.setFont(Font.font("Arial", 14));
		
		btnBack.setPrefSize(100, 50);
		btnBack.setFont(Font.font("Arial", 14));
		
		createHoverEffect(btnLogin);
		createHoverEffect(btnExit);
		createHoverEffect(btnBack);
	}	
	
	private void showLoginScreen() {
		Scene scene = new Scene(this, 1200, 1000);
		loginStage = new Stage();
		loginStage.setTitle("Register Screen");
		loginStage.setScene(scene);
		loginStage.show();
	}
	
	private void close() {
		loginStage.close();
	}

}
