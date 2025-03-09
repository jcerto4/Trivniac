package gameobjects;

import java.io.File;
import java.util.Random;
import java.util.function.Consumer;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Wheel extends StackPane{
	
	private Canvas canvas;
	private double angle = 0;
	private double radius = 190;
	
	private Media spinMedia;
	private MediaPlayer spinPlayer; 
	
	public Wheel(){
		
		loadSpinSound();
		this.setPrefSize(400, 400);
		canvas = new Canvas(400, 400);
		drawWheel();
		drawArrow();
	}
	
	
	private void drawWheel() {
		
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		double centerX = canvas.getWidth() / 2;
		double centerY = canvas.getHeight() / 2;
		
		Color[] colors = {Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, Color.PURPLE, Color.PINK};
		String[] categories = {"History", "Sports", "Geography", "Science", "Pop-Culture", "Wild"};
		
		graphics.setFont(Font.font("Arial", FontWeight.BOLD, 20));
		graphics.setTextAlign(TextAlignment.CENTER);
		graphics.setTextBaseline(VPos.CENTER);
		
		for(int i = 0; i < 6; i++) {
			graphics.setFill(colors[i]);
			graphics.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, i * 60, 60, ArcType.ROUND);
		}
		
		for(int i = 0; i < 6; i++) {
			
			double angle = Math.toRadians(i * 60 + 30);
			double textX = centerX + (radius * 0.6) * Math.cos(angle);
			double textY = centerY - (radius * 0.6) * Math.sin(angle);
			
			graphics.setFill(Color.WHITE);
			graphics.fillText(categories[i], textX, textY);
		}
		
		this.getChildren().add(canvas);
	}
	
	private void drawArrow() {
		
		double arrowX = -radius - 7;	
		
		Polygon arrow = new Polygon();
		arrow.getPoints().addAll(
		    30.0, 0.0,
			0.0, -15.0,
			0.0, 15.0
		);
		arrow.setScaleX(2);
		arrow.setScaleY(2);
		arrow.setTranslateX(arrowX);
		arrow.setFill(Color.BLACK); 
		this.getChildren().add(arrow);
	}
	
	public void spinWheel(Consumer<String> selectedCategory) {
		
		playSpinSound();
		
		RotateTransition wheelAnimation = new RotateTransition(Duration.seconds(3), canvas);
		Random random = new Random();
		
		double rotations = 5 + random.nextDouble();
		double spinAngle = 360 * rotations;
		
		double startAngle = canvas.getRotate();
		double endAngle = startAngle + spinAngle;
		
		wheelAnimation.setFromAngle(startAngle);
		wheelAnimation.setToAngle(endAngle);
		wheelAnimation.setInterpolator(Interpolator.EASE_OUT);
		
		wheelAnimation.setOnFinished(e -> {
			stopSpinSound();
			String category = getSelectedCategory();
			selectedCategory.accept(category);
		});
		wheelAnimation.play();
		
		angle = endAngle;
	}
	
	private String getSelectedCategory() {
		//Normalize the spinAngle 0-360 degrees: (1900 % 360) = 100 degrees
		//Add 180 degrees to offset the angle to match the arrow placement on the West side
		//Calculate the index
		String[] categories = {"History", "Sports", "Geography", "Science", "Pop-Culture", "Wild"};
		double spinAngle = angle % 360;
		double finalAngle = (spinAngle + 180) % 360;
		int index = (int) (finalAngle / 60);
		return categories[index];
	}
	
	private void loadSpinSound() {
		String soundURL = "sounds/wheel_spin.mp3";
		spinMedia = new Media(new File(soundURL).toURI().toString());
		spinPlayer = new MediaPlayer(spinMedia);
	}
	
	private void playSpinSound() {
		spinPlayer.play();
	}
	
	private void stopSpinSound() {
		spinPlayer.stop();
	}
	
}
