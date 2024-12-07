package imagetool.service;

import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import javafx.application.Platform;
import java.io.File;
import java.util.List;
import imagetool.utils.AlertUtils;
import imagetool.ui.ImageContainer;
import imagetool.ui.UIComponentManager;

public class ImageUploadService {
    // Service for handling image upload operations
    private final ImageContainer imageContainer;
    private final UIComponentManager uiManager;

    public ImageUploadService(ImageContainer imageContainer, UIComponentManager uiManager) {
        this.imageContainer = imageContainer;
        this.uiManager = uiManager;
    }

    public List<File> showFileChooser() {
        // Configures and displays file chooser dialog for image selection
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Images");
        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.bmp")
        );
        return fileChooser.showOpenMultipleDialog(null);
    }

    public void processImageFile(File file, boolean clearContainer) {
        Platform.runLater(() -> {
            try {
                // Clear existing images if requested
                if (clearContainer) {
                    imageContainer.getContainer().getChildren().clear();
                }
                
                // Validates file accessibility
                if (!file.exists() || !file.canRead()) {
                    AlertUtils.showErrorAlert("Error", "File not accessible: " + file.getName());
                    return;
                }

                // Create thumbnail with fixed dimensions
                Image thumbnail = new Image(file.toURI().toString(), 100, 100, true, true);
                
                // Verifies image integrity
                if (thumbnail.isError()) {
                    AlertUtils.showErrorAlert("Error", "Corrupted file: " + file.getName());
                    return;
                }

                // Add image card to container
                imageContainer.getContainer().getChildren().add(
                    uiManager.createImageCard(file, thumbnail)
                );
            } catch (Exception e) {
                AlertUtils.showErrorAlert("Error", "Error processing file: " + file.getName());
            }
        });
    }
} 