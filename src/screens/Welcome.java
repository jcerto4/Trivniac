package screens;

import java.io.File;

import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Welcome extends BorderPane{

	private Stage welcomeStage;
	private Button btnRegister = new Button("Register");
	private Button btnLogin = new Button("Login");
	private Button btnExit = new Button("Exit");
	
	private Media welcomeMusicMedia;
	private MediaPlayer welcomeMusicPlayer;
	
	private Media buttonMedia;
	private MediaPlayer buttonPlayer;
	
	public Welcome() {
		
		loadWelcomeMusic();
		loadButtonSound();
		playWelcomeMusic();
		//createTopSection();
		createCenterSection();
		createBottomSection();
		setBackground();
		createRegisterButtonListeners();
		createLoginButtonListeners();
		createExitButtonListeners();
		styleButtons();
		showWelcomeScreen();
	}
	
	
	private void createRegisterButtonListeners() {
		btnRegister.setOnAction(event -> {
				stopWelcomeMusic();
				playButtonSound();
				new Register();
				close();
		});
		
	}
	private void createLoginButtonListeners() {
		btnLogin.setOnAction(event -> {
				stopWelcomeMusic();	
				playButtonSound();
				new Login();
				close();
		});
	}
	
	private void createExitButtonListeners() {
		btnExit.setOnAction(event -> close());
	}
	
	private void createTopSection() {
		
	}
	
	
	private void createCenterSection() {
		
		VBox btnCtn = new VBox(20, btnRegister, btnLogin);
		btnCtn.setPadding(new Insets(375, 0, 0, 0));
		btnCtn.setAlignment(Pos.CENTER);
		this.setCenter(btnCtn);
	}
	
	private void createBottomSection() {
		
		Label volumeLabel = new Label("\uD83D\uDD0A");
		volumeLabel.setFont(new Font(20));
		volumeLabel.setTextFill(Color.WHITE);
		volumeLabel.setMinWidth(30);
		
		Slider volumeSlider = new Slider(0, 1, 0.5);
		volumeSlider.setMajorTickUnit(0.25);
		volumeSlider.setBlockIncrement(0.1);
		volumeSlider.setPrefWidth(150);
		
		welcomeMusicPlayer.volumeProperty().bind(volumeSlider.valueProperty());
		
		volumeSlider.valueProperty().addListener((obersable, oldValue, newValue) -> {
			if(newValue.doubleValue() == 0.0) {
				volumeLabel.setText("\uD83D\uDD07");
			}else {
				volumeLabel.setText("\uD83D\uDD0A");
			}
			
			
		});
		
		HBox volumeCtn = new HBox(volumeLabel, volumeSlider);
		volumeCtn.setAlignment(Pos.CENTER_LEFT);
		
		
		
		Region spacer = new Region();
		HBox.setHgrow(spacer, Priority.ALWAYS);
		
		
		HBox bottomCtn = new HBox(volumeCtn, spacer, btnExit);
		
		btnExit.setTranslateY(-5);
		btnExit.setTranslateX(-5);
		
		bottomCtn.setAlignment(Pos.BOTTOM_RIGHT);
		bottomCtn.setPadding(new Insets(10, 10, 10, 10));
		
		this.setBottom(bottomCtn);
		
	}
	
	
	private void showWelcomeScreen() {
		Scene scene = new Scene(this, 1000, 700);
		welcomeStage = new Stage();
		welcomeStage.setTitle("Welcome Screen");
		welcomeStage.setScene(scene);
		welcomeStage.show();
	}
	
	private void close() {
		welcomeStage.close();
	}
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/welcome_background.png");
		
//		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
//		
//		BackgroundImage welcomeBackground = new BackgroundImage(
//			backgroundImage,
//			BackgroundRepeat.NO_REPEAT,
//			BackgroundRepeat.NO_REPEAT,
//			BackgroundPosition.DEFAULT,
//			backgroundSize
//			);
//		this.setBackground(new Background(welcomeBackground));
		
		ImageView background = new ImageView(backgroundImage);
		background.setY(-68);
		
		getChildren().add(0, background);
	}
	
	private void styleButtons() {
		
		 String registerStyle = 
				 "-fx-background-color: #F7B733;" +
                 "-fx-text-fill: white;" +
                 "-fx-font-size: 28px;" +
                 "-fx-font-weight: bold;" +
                 "-fx-background-radius: 15px;" +
                 "-fx-border-radius: 15px;" +
                 "-fx-border-color: #8B6508;" +
                 "-fx-border-width: 2px;"
                 + "-fx-cursor: hand;";
		 
		 
		 String loginStyle = 
				 "-fx-background-color: #C0392B;" +
                 "-fx-text-fill: white;" +
                 "-fx-font-size: 28px;" +
                 "-fx-font-weight: bold;" +
                 "-fx-background-radius: 15px;" +
                 "-fx-border-radius: 15px;" +
                 "-fx-border-color: #8B0000;" +
                 "-fx-border-width: 2px;" +
                 "-fx-cursor: hand;";
		 
		
		btnRegister.setStyle(registerStyle);
		btnLogin.setStyle(loginStyle);
		 
		 
		btnExit.setPrefSize(100, 50);
		btnRegister.setPrefSize(500, 100);
		btnLogin.setPrefSize(500, 100);
		
		btnRegister.setFont(Font.font("Arial", 24));
		btnLogin.setFont(Font.font("Arial", 24));
		btnExit.setFont(Font.font("Arial", 14));
		
		createHoverEffect(btnRegister);
		createHoverEffect(btnLogin);
		createHoverEffect(btnExit);
		
//		btnExit.setStyle(
//			"-fx-background-color: linear-gradient(to bottom, #d9d9d9, #bfbfbf);" +
//			"-fx-text-fill: #2a2a2a;" +
//			"-fx-font-weight: bold;" +
//			"-fx-background-radius: 8;" +
//			"-fx-border-radius: 8;" +
//			"-fx-border-color: #7a7a7a;" +
//		    "-fx-border-width: 1;" +
//			"-fx-cursor: hand;"
//		);
		
		String backStyle = (
				"-fx-background-color: rgba(0,0,0,0.4);" +
				"-fx-text-fill: white;" +
				"-fx-font-size: 14px;" +
				"-fx-font-weight: bold;" +
				"-fx-padding: 6 14 6 14;" +
				"-fx-background-radius: 8;" +
				"-fx-border-color: white;" +
				"-fx-border-width: 1;" +
				"-fx-border-radius: 8;" +
				"-fx-cursor: hand;"
			);
		
		btnExit.setStyle(backStyle);
	
	}
	
	private void createHoverEffect(Button button) {
		
		DropShadow shadow = new DropShadow(10, Color.BLACK);
		
		button.setOnMouseEntered(e -> {
			ScaleTransition scale = new ScaleTransition(Duration.millis(200), button);
			scale.setToX(1.1);
			scale.setToY(1.1);
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
	
	private void loadWelcomeMusic() {
		String soundURL = "sounds/welcome_music.mp3";
		welcomeMusicMedia = new Media(new File(soundURL).toURI().toString());
		welcomeMusicPlayer = new MediaPlayer(welcomeMusicMedia);
	}
	
	private void loadButtonSound() {
		String soundURL = "sounds/welcome_buttons_sound.mp3";
		buttonMedia = new Media(new File(soundURL).toURI().toString());
		buttonPlayer = new MediaPlayer(buttonMedia);
	}
	
	private void playWelcomeMusic() {
		welcomeMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		welcomeMusicPlayer.play();
	}
	
	private void stopWelcomeMusic() {
		welcomeMusicPlayer.stop();
	}
	
	private void playButtonSound() {
		buttonPlayer.seek(Duration.ZERO);
		buttonPlayer.play();
	}

	
}
