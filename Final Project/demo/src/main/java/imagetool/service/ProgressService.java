package imagetool.service;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import imagetool.ui.UIComponentManager;
import java.util.concurrent.CountDownLatch;

public class ProgressService {
    // Logger instance for this class
    private static final Logger logger = LoggerFactory.getLogger(ProgressService.class);
    // Reference to UI component manager
    private final UIComponentManager uiManager;

    public ProgressService(UIComponentManager uiManager) {
        this.uiManager = uiManager;
    }

    // Updates progress indicator based on task completion status
    public void updateProgress(CountDownLatch latch, int success, boolean showSuccess) {
        if (latch.getCount() == 0) {
            Platform.runLater(() -> {
                if (success > 0 && showSuccess) {
                    showSuccessIndicator();
                } else {
                    uiManager.getProgressIndicator().setVisible(false);
                }
            });
        }
    }

    // Displays a temporary success checkmark and reverts back to progress indicator
    private void showSuccessIndicator() {
        Label checkMark = new Label("✓");
        checkMark.setStyle("-fx-text-fill: #28a745; -fx-font-size: 20px;");
        checkMark.setPrefWidth(30);
        checkMark.setAlignment(Pos.CENTER);
        
        HBox controlBox = uiManager.getControlBox();
        int progressIndex = controlBox.getChildren().indexOf(uiManager.getProgressIndicator());
        controlBox.getChildren().set(progressIndex, checkMark);
        
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                Platform.runLater(() -> {
                    uiManager.getProgressIndicator().setVisible(false);
                    controlBox.getChildren().set(
                        controlBox.getChildren().indexOf(checkMark), 
                        uiManager.getProgressIndicator()
                    );
                });
            } catch (InterruptedException e) {
                logger.error("Thread interrupted: {}", e.getMessage(), e);
            }
        }).start();
    }

    // Hides the progress indicator on the UI thread
    public void hideProgress() {
        Platform.runLater(() -> uiManager.getProgressIndicator().setVisible(false));
    }
} 