package powerups;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public abstract class PowerUp{
	
	protected boolean isUsed = false;
	private Button button;
	private ImageView imageView;
	
	public PowerUp(String imagePath) {
		this.imageView = new ImageView(new Image(imagePath));
		this.button = new Button();
		this.button.setGraphic(imageView);
		this.button.setOnAction(e -> activatePowerUp());
		
		imageView.setFitWidth(200);
		imageView.setFitHeight(200);
		imageView.setPreserveRatio(false);
		//imageView.setStyle("-fx-background-color: transparent;");
		button.setStyle("-fx-background-color: transparent; -fx-padding: 0;");
		
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
	
	public abstract void activatePowerUp();
	
	public Button getButton() {
		return button;
	}
	
}
