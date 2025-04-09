package screens;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.function.Consumer;

import classes.Question;
import db.DatabaseManager;
import gameobjects.Timer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import powerups.DoubleChance;
import powerups.EliminateTwo;
import powerups.StopTimer;

public class QuestionScreen extends BorderPane{
	
	private String category;
	private Stage questionStage;
	private Timer timer;
	private Question question;
	private int correctAnswer;
	private Consumer<Boolean> onQuestionAnswered;
	private int gameID;
	
	private Button option1;
	private Button option2;
	private Button option3;
	private Button option4;
	
	String[] options;
	
	private Media questionMusicMedia;
	private MediaPlayer questionMusicPlayer;
	
	
	private Media incorrectSoundMedia;
	private MediaPlayer incorrectSoundPlayer;
	
	private Media correctSoundMedia;
	private MediaPlayer correctSoundPlayer;
	
	private boolean doubleChanceActive = false;
	private boolean doubleChanceUsed = false;
	
	public QuestionScreen(int gameID, String category, Consumer<Boolean> onQuestionAnswered, DoubleChance doubleChance, EliminateTwo elimTwo, StopTimer stopTimer) {
				
		this.gameID = gameID;
		this.category = category;
		question = DatabaseManager.getRandomQuestion(category);
		correctAnswer = question.getCorrectAnswer();
		options = question.getOptions();
		
		this.onQuestionAnswered = onQuestionAnswered;
		
		doubleChance.setQuestionScreen(this);
		elimTwo.setQuestionScreen(this);
		stopTimer.setQuestionScreen(this);

		loadQuestionMusic();
		playQuestionMusic();
		
		loadIncorrectSound();
		loadCorrectSound();
		
		setBackground();
		createTopSection();
		createCenterSection();
		createBottomSection(doubleChance, elimTwo, stopTimer);
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
	
	public void eliminateTwo() {
		
		ArrayList<Integer> incorrectChoices = new ArrayList<>();
		
		for(int i = 0; i < 4; i++) {
			if(i + 1 != correctAnswer) {
				incorrectChoices.add(i);
			}
		}
		
		Button[] buttons = {option1, option2, option3, option4};
		
		Collections.shuffle(incorrectChoices);
		
		buttons[incorrectChoices.get(0)].setDisable(true);
		buttons[incorrectChoices.get(1)].setDisable(true);
		
	}
	
	public void stopTimer() {
		timer.stopTimer();
	}
	
	private void checkAnswer(int selectedOption) {
		
		if(doubleChanceActive && !doubleChanceUsed && selectedOption != correctAnswer) {
			
			doubleChanceUsed = true;
			
			disableOptionButton(selectedOption);
			
			return;
			
		}
		
		stopQuestionMusic();
		timer.stopTickingSound();
		timer.stopTimer();
		disableOptionButtons();
		
		boolean isCorrect = (selectedOption == correctAnswer);
		
		highlightOptions(option1, correctAnswer == 1);
		highlightOptions(option2, correctAnswer == 2);
		highlightOptions(option3, correctAnswer == 3);
		highlightOptions(option4, correctAnswer == 4);
		
		if(isCorrect) {
			playCorrectSound();
			
		}else {
			playIncorrectSound();
		}
		
		new Timeline(new KeyFrame(Duration.seconds(2), e->{
			stopCorrectSound();
			stopIncorrectSound();
			onQuestionAnswered.accept(isCorrect);
			questionStage.close();
		})).play();
		
	}
	
	
	private void createTopSection() {
		
		timer = new Timer(25, () -> {
			int forcedIncorrect = (correctAnswer == 1 ? 2 : 1);
			checkAnswer(forcedIncorrect);
		});
		
		timer.setAlignment(Pos.CENTER);
		this.setTop(timer);
		timer.startTimer();
	}
	
	private void createCenterSection() {
		
		Text questionText = new Text(question.getQuestionText());
		questionText.setFont(Font.font("Verdana", 28));
		questionText.setFill(Color.WHITE);
		questionText.setWrappingWidth(800);
		questionText.setTextAlignment(TextAlignment.CENTER);
		
		VBox questionCtn = new VBox(questionText);
		questionCtn.setAlignment(Pos.CENTER);
		questionCtn.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5); -fx-padding: 10; -fx-background-radius: 10;");
		questionCtn.setMaxWidth(850);
		
		String[] options = question.getOptions();
		option1 = new Button(options[0]);
		option2 = new Button(options[1]);
		option3 = new Button(options[2]);
		option4 = new Button(options[3]);
		
		option1.setWrapText(true);
		option2.setWrapText(true);
		option3.setWrapText(true);
		option4.setWrapText(true);
		
		option1.setTextAlignment(TextAlignment.CENTER);
		option2.setTextAlignment(TextAlignment.CENTER);
		option3.setTextAlignment(TextAlignment.CENTER);
		option4.setTextAlignment(TextAlignment.CENTER);
		
		VBox optionsCtn = new VBox(20, option1, option2, option3, option4);
		optionsCtn.setAlignment(Pos.CENTER);
		
		VBox centerCtn = new VBox(20, questionCtn, optionsCtn);
		centerCtn.setAlignment(Pos.CENTER);
		
		this.setCenter(centerCtn);
		
	}
	
	private void createBottomSection(DoubleChance doubleChance, EliminateTwo elimTwo, StopTimer stopTimer) {
		
		HBox powerupCtn = new HBox(20);
		
		powerupCtn.setAlignment(Pos.CENTER);
		
		powerupCtn.getChildren().addAll(doubleChance.getButton(), elimTwo.getButton(), stopTimer.getButton());
		
		this.setBottom(powerupCtn);
		
		
	}
	
	public void enableDoubleChance() {
		this.doubleChanceActive = true;
	}
	
	private void disableOptionButton(int selectedOption) {
		
		switch(selectedOption) {
			case 1:
				option1.setDisable(true);
				break;
			case 2:
				option2.setDisable(true);
				break;
			case 3:
				option3.setDisable(true);
				break;
			case 4:
				option4.setDisable(true);
		}
		
	}
	
	private void styleButtons() {
		
		createHoverEffect(option1);
		createHoverEffect(option2);
		createHoverEffect(option3);
		createHoverEffect(option4);
		
		option1.setPrefSize(450, Region.USE_COMPUTED_SIZE);
		option2.setPrefSize(450, Region.USE_COMPUTED_SIZE);
		option3.setPrefSize(450, Region.USE_COMPUTED_SIZE);
		option4.setPrefSize(450, Region.USE_COMPUTED_SIZE);
		
//		option1.setFont(Font.font("Georgia", 32));
//		option2.setFont(Font.font("Georgia", 32));
//		option3.setFont(Font.font("Georgia", 32));
//		option4.setFont(Font.font("Georgia", 32));
		
		String style = (
			"-fx-background-color: rgba(0, 0, 0, 0.35);" +
			"-fx-background-radius: 20;" +
			"-fx-border-color: white;" +
			"-fx-border-width: 2;" +
		    "-fx-border-radius: 20;" +
			"-fx-background-radius: 20;" +
		    "-fx-text-fill: white;" +
		    "-fx-font-size: 24px;" +
		    "-fx-font-weight: bold;" +
		    "-fx-font-family: 'Segoe UI';" +
		    "-fx-padding: 12 24 12 24;" +
		    "-fx-cursor: hand;"
		);
		
		option1.setStyle(style);
		option2.setStyle(style);
		option3.setStyle(style);
		option4.setStyle(style);
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
		
		Image backgroundImage = getBackgroundImage();
		
		BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, false, true);
		
		BackgroundImage questionBackground = new BackgroundImage(
			backgroundImage,
			BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,
			backgroundSize
			);
		this.setBackground(new Background(questionBackground));
	}
	
	private Image getBackgroundImage() {
		switch(category) {
			case "History":
				return new Image("file:images/history_background.png");
			case "Sports":
				return new Image("file:images/sports_background.png");
			case "Geography":
				return new Image("file:images/geography_background.png");
			case "Science":
				return new Image("file:images/science_background.png");
			case "Pop-Culture":
				return new Image("file:images/pop_background.png");
			case "Wild":
				return new Image("file:images/wild_background.png");
		}
		
		return null;
	}
	
	private void highlightOptions(Button button, boolean isCorrect) {
		if(isCorrect) {
			button.setStyle(
				"-fx-background-radius: 20;" +
				"-fx-border-color: white;" +
				"-fx-border-width: 2;" +
				"-fx-border-radius: 20;" +
				"-fx-text-fill: white;" +
				"-fx-font-size: 24px;" +
				"-fx-font-weight: bold;" +
				"-fx-font-family: 'Segoe UI';" +
				"-fx-padding: 12 24 12 24;" +
				"-fx-background-color: green;"	
				);
		}else {
			button.setStyle(	
					"-fx-background-radius: 20;" +
					"-fx-border-color: white;" +
					"-fx-border-width: 2;" +
					"-fx-border-radius: 20;" +
					"-fx-text-fill: white;" +
					"-fx-font-size: 24px;" +
					"-fx-font-weight: bold;" +
					"-fx-font-family: 'Segoe UI';" +
					"-fx-padding: 12 24 12 24;" +
					"-fx-background-color: red;"	
				);
		}
		
		scaleCorrectAnswer(button, isCorrect);
	}
	
	private void scaleCorrectAnswer(Button button, boolean isCorrect) {
		
		if(isCorrect) {
		ScaleTransition scale = new ScaleTransition(Duration.millis(200), button);
		scale.setToX(1.2);
		scale.setToY(1.2);
		scale.play();
		}
		
	}
	
	private void disableOptionButtons() {
		option1.setDisable(true);
		option2.setDisable(true);
		option3.setDisable(true);
		option4.setDisable(true);
	}
	
	private void showQuestionScreen() {
		
		Scene scene = new Scene(this, 1000, 750);
		questionStage = new Stage();
		questionStage.setTitle("Question Screen");
		questionStage.setScene(scene);
		questionStage.show();
	}
	
	
}
