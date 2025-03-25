package gamemodes;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import classes.Player;
import classes.Question;
import db.DatabaseManager;
import gameobjects.LeaderBoard;
import gameobjects.Timer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import javafx.stage.Stage;
import screens.GameOver;

public class Blitz extends BorderPane{

	
	private int gameID;
	private Player player;
	private Stage blitzStage;
	private Timer timer;
	private LeaderBoard leaderboard;
	
	private String[] categories = {"History", "Sports", "Geography", "Science", "Pop-Culture", "Wild"};
	private Question question;
	
	private Text questionText;
	private Button option1, option2, option3, option4;
	
	private int score;
	
	private ArrayList<Question> usedQuestions = new ArrayList<>();
	
	private Media correctSoundMedia;
	private MediaPlayer correctSoundPlayer;
	private Media incorrectSoundMedia;
	private MediaPlayer incorrectSoundPlayer;
	private Media blitzMusicMedia;
	private MediaPlayer blitzMusicPlayer;
	
	
	
	public Blitz(int gameID, Player player) {
		
		this.gameID = gameID;
		this.player = player;
		
		question = DatabaseManager.getRandomQuestion("History");
		
		usedQuestions.add(question);
		
		leaderboard = new LeaderBoard("Blitz");
		loadIncorrectSound();
		loadCorrectSound();
		loadBlitzMusic();
		createTopSection();
		createCenterSection();
		createRightSection();
		createOptionButtonListeners();
		styleButtons();
		
		
		showBlitzScreen();
		
	}
	
	private void createOptionButtonListeners() {
		option1.setOnAction(e -> checkAnswer(1));
	    option2.setOnAction(e -> checkAnswer(2));
	    option3.setOnAction(e -> checkAnswer(3));
	    option4.setOnAction(e -> checkAnswer(4));
	}
	
	private void createTopSection() {
		
		timer = new Timer(30, () -> {
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
	
	private void createRightSection() {
		leaderboard.setPrefSize(400, 300);
		leaderboard.setAlignment(Pos.CENTER_RIGHT);
		leaderboard.setPadding(new Insets(0, 20, 0, 0));
		this.setRight(leaderboard);
	}
	
	private void checkAnswer(int selectedAnswer) {
		
		if(selectedAnswer == question.getCorrectAnswer()) {
			score += 10;
			DatabaseManager.updateScore(gameID, score);
			leaderboard.refreshLeaderboard();
			timer.setSecondsLeft(timer.getSecondsLeft() + 10);
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
		}while(usedQuestions.contains(question));
		
		usedQuestions.add(question);
		
		questionText.setText(question.getQuestionText());
		
		String[] options = question.getOptions();
		option1.setText(options[0]);
		option2.setText(options[1]);
		option3.setText(options[2]);
		option4.setText(options[3]);
		
	}
	
	
	
	private void styleButtons() {
		
		option1.setPrefSize(300, 50);
		option2.setPrefSize(300, 50);
		option3.setPrefSize(300, 50);
		option4.setPrefSize(300, 50);
		
		option1.setFont(Font.font("Verdana", 18));
		option2.setFont(Font.font("Verdana", 18));
		option3.setFont(Font.font("Verdana", 18));
		option4.setFont(Font.font("Verdana", 18));
		
		questionText.setFont(Font.font("Verdana", 22));
		questionText.setFill(Color.WHITE);
		questionText.setWrappingWidth(400);
		questionText.setTextAlignment(TextAlignment.CENTER);
		
		
		
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
	
	private void showBlitzScreen() {
		Scene scene = new Scene(this, 1000, 700);
		blitzStage = new Stage();
		blitzStage.setTitle("Blitz Mode");
		blitzStage.setScene(scene);
		blitzStage.show();
	}
}
