package screens;

import java.io.File;

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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Login extends BorderPane{

	private Button btnLogin = new Button("Login");
	private Button btnBack = new Button("Back");
	private Label userNameLabel = new Label("Username: ");
	private Label passwordLabel = new Label("Password: ");
	private Stage loginStage;
	
	private Media entrySoundMedia;
	private MediaPlayer entrySoundPlayer;
	private Media errorSoundMedia;
	private MediaPlayer errorSoundPlayer;
	private Media backSoundMedia;
	private MediaPlayer backSoundPlayer;
	
	public Login() {
		
		setBackground();
		loadEntrySound();
		loadErrorSound();
		loadBackSound();
		createCenterSection();
		createBottomSection();
		createBackButtonListeners();
		styleButtons();
		showLoginScreen();
		
	}
	
	private void createBackButtonListeners() {
		btnBack.setOnAction(e -> {
			playBackSound();
			loginStage.close();
			new Welcome();
		});
	}
	
	private void createCenterSection() {
		
		Text title = new Text("Login to Trivniac");
		title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 24));
		title.setFill(Color.WHITE);
		HBox titleCtn = new HBox(title);
		titleCtn.setAlignment(Pos.CENTER);
		
		userNameLabel.setTextFill(Color.WHITE);
		userNameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		TextField userNameEntry = new TextField();
		userNameEntry.setPrefSize(250, 30);
		HBox userCtn = new HBox(10, userNameLabel, userNameEntry);
		userCtn.setAlignment(Pos.CENTER);
		
		passwordLabel.setTextFill(Color.WHITE);
		passwordLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		PasswordField passwordEntry = new PasswordField();
		passwordEntry.setPrefSize(250, 30);
		HBox passCtn = new HBox(10, passwordLabel, passwordEntry);
		passCtn.setAlignment(Pos.CENTER);
		
		
		
		
		btnLogin.setOnAction(e -> {
			if(userNameEntry.getText().isEmpty() || passwordEntry.getText().isEmpty()) {
				playErrorSound();
				showAlert(Alert.AlertType.WARNING, "Empty Field", "Please enter both username and password");
			}else {
				Player player = DatabaseManager.getPlayer(userNameEntry.getText());
				
				if(player == null) {
					playErrorSound();
					showAlert(AlertType.ERROR, "Login Error", "No account found with this username");	
				}else {
					if(passwordEntry.getText().equals(player.getPassword())) {
						playEntrySound();
						close();
						new GameModeSelection(player);
					}else {
						playErrorSound();
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
		registerCtn.setTranslateX(-40);
		registerCtn.setTranslateY(100);
		
		this.setCenter(registerCtn);
		
	}
	
	private void createBottomSection() {
		
		HBox btmCtn = new HBox(btnBack);
		btmCtn.setAlignment(Pos.BOTTOM_LEFT);
		btmCtn.setPadding(new Insets(20));
		this.setBottom(btmCtn);
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
		alert.initOwner(loginStage);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
		
	}
	
	
	private void styleButtons() {
		
		btnLogin.setFont(Font.font("Arial", 20));
		
		
		btnBack.setPrefSize(100, 50);
		btnBack.setFont(Font.font("Arial", 14));
		
		createHoverEffect(btnLogin);
		createHoverEffect(btnBack);
		
		btnLogin.setStyle(
				"-fx-background-color: linear-gradient(to bottom, #f7c47b, #e3a752);" +  
			    "-fx-text-fill: #3b2f26;" +                                              
			    "-fx-font-weight: bold;" +
			    "-fx-background-radius: 8;" +
			    "-fx-border-radius: 8;" +
			    "-fx-border-color: #b67c43;" +
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
	
	
	private void loadEntrySound() {
		String soundURL = "sounds/entry_sound.mp3";
		entrySoundMedia = new Media(new File(soundURL).toURI().toString());
		entrySoundPlayer = new MediaPlayer(entrySoundMedia);
		
	}
	
	private void playEntrySound() {
		entrySoundPlayer.seek(Duration.ZERO);
		entrySoundPlayer.play();
	}
	
	private void loadErrorSound() {
		String soundURL = "sounds/error_sound.mp3";
		errorSoundMedia = new Media(new File(soundURL).toURI().toString());
		errorSoundPlayer = new MediaPlayer(errorSoundMedia);
	}
	
	private void playErrorSound() {
		errorSoundPlayer.seek(Duration.ZERO);
		errorSoundPlayer.play();
	}
	
	private void loadBackSound() {
		String soundURL = "sounds/go_back_sound.mp3";
		backSoundMedia = new Media(new File(soundURL).toURI().toString());
		backSoundPlayer = new MediaPlayer(backSoundMedia);
	}
	
	private void playBackSound() {
		backSoundPlayer.seek(Duration.ZERO);
		backSoundPlayer.play();
	}
	
	private void showLoginScreen() {
		Scene scene = new Scene(this, 1000, 700);
		loginStage = new Stage();
		loginStage.setTitle("Login Screen");
		loginStage.setScene(scene);
		loginStage.show();
	}
	
	private void close() {
		loginStage.close();
	}

}
