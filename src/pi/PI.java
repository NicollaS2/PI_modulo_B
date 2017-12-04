package pi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

//@author Nicollas Ramires

public class PI extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("layout/login.fxml"));
		Scene scene = new Scene(root);
                Screen screen = Screen.getPrimary();
                javafx.geometry.Rectangle2D bounds = screen.getVisualBounds();
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
		scene.getStylesheets().add("pi/layout/estilo.css");
		stage.setTitle("Login no sistema...");
		stage.setScene(scene);
		stage.show();
		stage.getIcons().add(new Image("pi/img/atualcommerce_novo.png"));

	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
