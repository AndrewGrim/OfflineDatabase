import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // loads the fxml file for the interface, and basically like python root setup
        Parent root = FXMLLoader.load(getClass().getResource("gui.fxml"));
        primaryStage.setTitle("Monster Hunter Database");
        primaryStage.getIcons().add(new Image("/images/OfflineDatabase.png"));
        primaryStage.setScene(new Scene(root, 900, 600));
        primaryStage.show(); // neccessary for the program to display
    }

    public static void main(String[] args) {
        launch(args);
    }
}
