package gameobjects;

import java.util.Random;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Polygon;
import javafx.util.Duration;

public class Wheel extends Pane{
	
	private Canvas canvas;
	private double angle = 0;
	private double radius = 140;
	
	public Wheel(){
		
		canvas = new Canvas(300, 300);
		canvas.setTranslateX(canvas.getWidth() / 2);
		canvas.setTranslateY(canvas.getHeight() / 2);
		drawWheel();
		drawArrow();
	}
	
	
	private void drawWheel() {
		
		GraphicsContext graphics = canvas.getGraphicsContext2D();
		double centerX = canvas.getWidth() / 2;
		double centerY = canvas.getHeight() / 2;
		
		Color[] colors = {Color.YELLOW, Color.RED, Color.GREEN, Color.BLUE, Color.PURPLE, Color.ORANGE};
		
		for(int i = 0; i < 6; i++) {
			graphics.setFill(colors[i]);
			graphics.fillArc(centerX - radius, centerY - radius, radius * 2, radius * 2, i * 60, 60, ArcType.ROUND);
		}
		
		this.getChildren().add(canvas);
	}
	
	private void drawArrow() {
		
		double centerX = canvas.getTranslateX() + canvas.getWidth() / 2;
		double centerY = canvas.getTranslateY() + canvas.getHeight() / 2;
		
		double arrowX = centerX - radius - 20;
		
		
		Polygon arrow = new Polygon();
		arrow.getPoints().addAll(
		    0.0, 0.0,
			30.0, -15.0,
			30.0, 15.0
		);
		
		arrow.setLayoutX(arrowX);
		arrow.setLayoutY(centerY);
		arrow.setRotate(180);
		arrow.setFill(Color.BLACK); 
		this.getChildren().add(arrow);
	}
	
	public void spinWheel() {
		
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
			System.out.println(getSelectedCategory());
		});
		wheelAnimation.play();
		
		angle = endAngle;
	}
	
	public String getSelectedCategory() {
		//Normalize the spinAngle 0-360 degrees: (1900 % 360) = 100 degrees
		//Add 180 degrees to offset the angle to match the arrow placement on the West side
		//Calculate the index
		String[] categories = {"History", "Sports", "Geography", "Science", "Pop-Culture", "Wild"};
		double spinAngle = angle % 360;
		double finalAngle = (spinAngle + 180) % 360;
		int index = (int) (finalAngle / 60);
		return categories[index];
	}
	
}
