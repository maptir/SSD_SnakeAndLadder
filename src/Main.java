import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import snakeandladder.Game;
import ui.ConsoleUI;
import ui.GameUI;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = (Parent) FXMLLoader.load(getClass().getResource("/ui/GameUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Choose Game Mode");
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// ConsoleUI ui = new ConsoleUI();
		Game game = new Game(2);

		launch(args);
	}
}
