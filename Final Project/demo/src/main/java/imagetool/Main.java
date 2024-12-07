package imagetool;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    private final MainUI mainUI = new MainUI();

    @Override
    // Initialize and display the primary application window
    public void start(Stage primaryStage) {
        Scene scene = mainUI.createScene();
        primaryStage.setTitle("Image Management Tool");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    // Perform cleanup operations when the application is closing
    public void stop() {
        mainUI.cleanup();
    }

    public static void main(String[] args) {
        launch(args);
    }
}