package screens;

import java.io.File;

import classes.Question;
import db.DatabaseManager;
import gameobjects.Timer;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuestionScreen extends BorderPane{
	
	private Stage questionStage;
	private Timer timer;
	private Question question;
	private int correctAnswer;
	
	private Button option1;
	private Button option2;
	private Button option3;
	private Button option4;
	
	private Media questionMusicMedia;
	private MediaPlayer questionMusicPlayer;
	
	
	private Media incorrectSoundMedia;
	private MediaPlayer incorrectSoundPlayer;
	
	private Media correctSoundMedia;
	private MediaPlayer correctSoundPlayer;
	
	
	public QuestionScreen(String category) {
		
		question = DatabaseManager.getRandomQuestion(category);
		correctAnswer = question.getCorrectAnswer();
	
		loadQuestionMusic();
		playQuestionMusic();
		
		loadIncorrectSound();
		loadCorrectSound();
		
		setBackground();
		createTopSection();
		createCenterSection();
		createBottomSection();
		createOptionButtonListeners();
		styleButtons();
		showQuestionScreen();
		
	}
	
	private void createOptionButtonListeners() {
		option1.setOnAction(e -> checkAnswer(1));
	    option2.setOnAction(e -> checkAnswer(2));
	    option3.setOnAction(e -> checkAnswer(3));
	    option4.setOnAction(e -> checkAnswer(4));
	}
	
	private void createTopSection() {
		
		timer = new Timer(25, null);
		timer.setAlignment(Pos.CENTER);
		this.setTop(timer);
	}
	
	private void createCenterSection() {
		
		Text questionText = new Text(question.getQuestionText());
		questionText.setFont(Font.font("Georgia", 24));
		questionText.setFill(Color.WHITE);
		
		String[] options = question.getOptions();
		option1 = new Button(options[0]);
		option2 = new Button(options[1]);
		option3 = new Button(options[2]);
		option4 = new Button(options[3]);
		
		VBox optionsCtn = new VBox(20, option1, option2, option3, option4);
		optionsCtn.setAlignment(Pos.CENTER);
		
		VBox centerCtn = new VBox(30, questionText, optionsCtn);
		centerCtn.setAlignment(Pos.CENTER);
		
		this.setCenter(centerCtn);
		
	}
	
	private void createBottomSection() {
		//Eventually will house the powerups
		
		
	}
	
	private void styleButtons() {
		
		createHoverEffect(option1);
		createHoverEffect(option2);
		createHoverEffect(option3);
		createHoverEffect(option4);
		
		option1.setPrefSize(500, 100);
		option2.setPrefSize(500, 100);
		option3.setPrefSize(500, 100);
		option4.setPrefSize(500, 100);
		
		
		
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
	
	private void loadQuestionMusic() {
		String soundURL = "sounds/question_music.mp3";
		questionMusicMedia = new Media(new File(soundURL).toURI().toString());
		questionMusicPlayer = new MediaPlayer(questionMusicMedia);
	}
	
	private void playQuestionMusic() {
		questionMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		questionMusicPlayer.play();
	}
	
	private void stopQuestionMusic() {
		questionMusicPlayer.stop();
	}
	
	private void loadIncorrectSound() {
		String soundURL = "sounds/incorrect_sound.mp3";
		incorrectSoundMedia = new Media(new File(soundURL).toURI().toString());
		incorrectSoundPlayer = new MediaPlayer(incorrectSoundMedia);
	}
	
	private void playIncorrectSound() {
		incorrectSoundPlayer.play();
	}
	
	private void stopIncorrectSound() {
		incorrectSoundPlayer.stop();
	}
	
	private void loadCorrectSound() {
		String soundURL = "sounds/correct_sound.mp3";
		correctSoundMedia = new Media(new File(soundURL).toURI().toString());
		correctSoundPlayer = new MediaPlayer(correctSoundMedia);
	}
	
	private void playCorrectSound() {
		correctSoundPlayer.play();
	}
	
	private void stopCorrectSound() {
		correctSoundPlayer.stop();
	}
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/question_background.jpg");
		
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
		
		BackgroundImage welcomeBackground = new BackgroundImage(
			backgroundImage,
			BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,
			backgroundSize
			);
		this.setBackground(new Background(welcomeBackground));
	}
	
	private void checkAnswer(int selectedOption) {
		if(selectedOption == correctAnswer) {
			//correct logic
		}else {
			//incorrect logic
		}
	}
	
	private void showQuestionScreen() {
		Scene scene = new Scene(this, 1200, 1000);
		questionStage = new Stage();
		questionStage.setTitle("Question Screen");
		questionStage.setScene(scene);
		questionStage.show();
	}
	
	
}
