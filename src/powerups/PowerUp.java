package powerups;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class PowerUp{
	
	protected boolean isUsed = false;
	private Button button;
	private ImageView imageView;
	
	public PowerUp(String imagePath) {
		this.imageView = new ImageView(new Image(imagePath));
		this.button = new Button();
		this.button.setGraphic(imageView);
		this.button.setOnAction(e -> activatePowerUp());
		
		imageView.setFitWidth(50);
		imageView.setPreserveRatio(true);
	}
	
	public abstract void activatePowerUp();
	
	public Button getButton() {
		return button;
	}
	
}
