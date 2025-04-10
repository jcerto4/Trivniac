package gamemodes;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import classes.Player;
import classes.Question;
import db.DatabaseManager;
import gameobjects.LeaderBoard;
import gameobjects.Timer;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.stage.Stage;
import screens.GameModeSelection;
import screens.GameOver;

public class Blitz extends BorderPane{

	
	private int gameID;
	private Player player;
	private Stage blitzStage;
	private Timer timer;
	
	private String[] categories = {"History", "Sports", "Geography", "Science", "Pop-Culture", "Wild"};
	private Question question;
	
	private Text questionText;
	private Button option1, option2, option3, option4;
	
	private int score;
	
	private ArrayList<Integer> usedQuestions = new ArrayList<>();
	
	private Media correctSoundMedia;
	private MediaPlayer correctSoundPlayer;
	private Media incorrectSoundMedia;
	private MediaPlayer incorrectSoundPlayer;
	private Media blitzMusicMedia;
	private MediaPlayer blitzMusicPlayer;
	
	private Button btnBack = new Button("Go Back");
	
	
	
	public Blitz(int gameID, Player player) {
		
		this.gameID = gameID;
		this.player = player;
		
		question = DatabaseManager.getRandomQuestion("History");
		
		usedQuestions.add(question.getQuestionID());
		
		setBackground();
		loadIncorrectSound();
		loadCorrectSound();
		loadBlitzMusic();
		createTopSection();
		createCenterSection();
		createBottomSection();
		createOptionButtonListeners();
		createBackButtonListeners();
		styleButtons();
		
		
		showBlitzScreen();
		
	}
	
	private void createBackButtonListeners() {
		btnBack.setOnAction(e -> {
			timer.stopTickingSound();
			blitzStage.close();
			new GameModeSelection(player);
		});
	}
	
	private void createOptionButtonListeners() {
		option1.setOnAction(e -> checkAnswer(1));
	    option2.setOnAction(e -> checkAnswer(2));
	    option3.setOnAction(e -> checkAnswer(3));
	    option4.setOnAction(e -> checkAnswer(4));
	}
	
	private void createTopSection() {
		
		timer = new Timer(30, () -> {
			timer.stopTickingSound();
			blitzStage.close();
			new GameOver(player, score, "Blitz");
		});
		
		timer.setAlignment(Pos.CENTER);
		this.setTop(timer);
		timer.startTimer();
		
		
	}
	
	private void createCenterSection() {
		
	
		questionText = new Text(question.getQuestionText());
		questionText.setFont(Font.font("Verdana", 28));
		questionText.setFill(Color.WHITE);
		questionText.setWrappingWidth(800);
		questionText.setTextAlignment(TextAlignment.CENTER);
		
		VBox questionCtn = new VBox(questionText);
		questionCtn.setAlignment(Pos.CENTER);
		questionCtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10; -fx-background-radius: 10;");
		questionCtn.setMaxWidth(300);
		
		String[] options = question.getOptions();
		option1 = new Button(options[0]);
		option2 = new Button(options[1]);
		option3 = new Button(options[2]);
		option4 = new Button(options[3]);
		
		VBox optionsCtn = new VBox(20, option1, option2, option3, option4);
		optionsCtn.setAlignment(Pos.CENTER);
		
		VBox centerCtn = new VBox(20, questionCtn, optionsCtn);
		centerCtn.setAlignment(Pos.CENTER);
		
		this.setCenter(centerCtn);
		
	}
	
	private void createBottomSection() {
		
		HBox btmCtn = new HBox(btnBack);
		btmCtn.setAlignment(Pos.CENTER_LEFT);
		btmCtn.setPadding(new Insets(20));
		this.setBottom(btmCtn);
	}
	
	private void checkAnswer(int selectedAnswer) {
		
		if(selectedAnswer == question.getCorrectAnswer()) {
			score += 10;
			DatabaseManager.updateScore(gameID, score);
			timer.setSecondsLeft(timer.getSecondsLeft() + 5);
			playCorrectSound();
		}else {
			playIncorrectSound();
		}
		
		showNextQuestion();
		
	}
	
	private void showNextQuestion() {
		
		int random = new Random().nextInt(categories.length);
		
		do {
			question = DatabaseManager.getRandomQuestion(categories[random]);
		}while(usedQuestions.contains(question.getQuestionID()));
		
		usedQuestions.add(question.getQuestionID());
		
		questionText.setText(question.getQuestionText());
		
		String[] options = question.getOptions();
		option1.setText(options[0]);
		option2.setText(options[1]);
		option3.setText(options[2]);
		option4.setText(options[3]);
		
	}
	
	
	
	private void styleButtons() {
		
		option1.setPrefSize(400, Region.USE_COMPUTED_SIZE);
		option2.setPrefSize(400, Region.USE_COMPUTED_SIZE);
		option3.setPrefSize(400, Region.USE_COMPUTED_SIZE);
		option4.setPrefSize(400, Region.USE_COMPUTED_SIZE);
		btnBack.setPrefSize(100, 40);
		
		option1.setWrapText(true);
		option2.setWrapText(true);
		option3.setWrapText(true);
		option4.setWrapText(true);
		
		option1.setTextAlignment(TextAlignment.CENTER);
		option2.setTextAlignment(TextAlignment.CENTER);
		option3.setTextAlignment(TextAlignment.CENTER);
		option4.setTextAlignment(TextAlignment.CENTER);

		questionText.setFont(Font.font("Verdana", 24));
		questionText.setFill(Color.WHITE);
		questionText.setWrappingWidth(400);
		questionText.setTextAlignment(TextAlignment.CENTER);
		
		createHoverEffect(option1);
		createHoverEffect(option2);
		createHoverEffect(option3);
		createHoverEffect(option4);
		createHoverEffect(btnBack);
		
		String style = (
			"-fx-background-color: linear-gradient(to bottom, #ffe680, #ffcc00);" +
			"-fx-text-fill: #1a1a1a;" +
			 "-fx-font-weight: bold;" +
			 "-fx-font-size: 20px;" +
			 "-fx-background-radius: 6;" +
			 "-fx-border-radius: 6;" +
			 "-fx-border-color: #333;" +
			 "-fx-border-width: 2;"	
			);
		
		option1.setStyle(style);
		option2.setStyle(style);
		option3.setStyle(style);
		option4.setStyle(style);
		
		btnBack.setStyle(
				"-fx-background-color: linear-gradient(#616161, #424242);" +
				"-fx-text-fill: white;" +
				"-fx-font-weight: bold;" +
				"-fx-background-radius: 12;" 
			);
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
	
	private void loadIncorrectSound() {
		String soundURL = "sounds/wrong_answer.mp3";
		incorrectSoundMedia = new Media(new File(soundURL).toURI().toString());
		incorrectSoundPlayer = new MediaPlayer(incorrectSoundMedia);
	}
	
	private void playIncorrectSound() {
		
		incorrectSoundPlayer.seek(Duration.ZERO);
		incorrectSoundPlayer.play();
	}
	
	private void stopIncorrectSound() {
		incorrectSoundPlayer.stop();
	}
	
	private void loadCorrectSound() {
		String soundURL = "sounds/correct_answer.mp3";
		correctSoundMedia = new Media(new File(soundURL).toURI().toString());
		correctSoundPlayer = new MediaPlayer(correctSoundMedia);
	}
	
	private void playCorrectSound() {
		correctSoundPlayer.seek(Duration.ZERO);
		correctSoundPlayer.play();
	}
	
	private void stopCorrectSound() {
		correctSoundPlayer.stop();
	}
	
	private void loadBlitzMusic() {
		String soundURL = "sounds/question_music.mp3";
		blitzMusicMedia = new Media(new File(soundURL).toURI().toString());
		blitzMusicPlayer = new MediaPlayer(blitzMusicMedia);
	}
	
	private void playBlitzMusic() {
		blitzMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		blitzMusicPlayer.play();
	}
	
	private void stopBlitzMusic() {
		blitzMusicPlayer.stop();
	}
	
	private void setBackground() {
		
		Image backgroundImage = new Image("file:images/blitz_background.png");
		
		ImageView background = new ImageView(backgroundImage);
		
		background.setFitWidth(1000);
		background.setFitHeight(800);
		
		this.getChildren().add(0, background);
		
	}
	
	private void showBlitzScreen() {
		Scene scene = new Scene(this, 1000, 700);
		blitzStage = new Stage();
		blitzStage.setTitle("Blitz Mode");
		blitzStage.setScene(scene);
		blitzStage.show();
	}
}
