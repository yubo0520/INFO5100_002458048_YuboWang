package imagetool;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import imagetool.ui.ControlPanel;
import imagetool.ui.ImageContainer;
import imagetool.ui.WelcomeGuide;


public class MainUI {

    private final ImageContainer imageContainer;
    
    private final ControlPanel controlPanel;
    
    private final WelcomeGuide welcomeGuide;

    // Initializes the main UI components 
    public MainUI() {
        imageContainer = new ImageContainer();
        controlPanel = new ControlPanel(imageContainer);
        welcomeGuide = new WelcomeGuide();
    }

    // Creates and configures the main scene with all UI components
    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: white;");
        
        imageContainer.addWelcomeGuide(welcomeGuide.createWelcomeBox());
        root.setCenter(imageContainer.getScrollPane());
        root.setBottom(controlPanel.getControlBox());

        return new Scene(root, 800, 550);
    }

    // Performs cleanup operations before application shutdown
    public void cleanup() {
        controlPanel.cleanup();
    }
} 