package imagetool.service;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.collections.ObservableList;
import imagetool.utils.AlertUtils;
import imagetool.ui.ImageContainer;

public class ValidationService {
    // Image container instance for validation
    private final ImageContainer imageContainer;

    public ValidationService(ImageContainer imageContainer) {
        this.imageContainer = imageContainer;
    }

    public boolean validateUploadStatus() {
        ObservableList<Node> children = imageContainer.getContainer().getChildren();
        // Check if no image is uploaded
        if (children.isEmpty()) {
            AlertUtils.showWarningAlert("Warning", "Please upload an image first");
            return true;
        }

        // Checks if container only contains welcome message
        Node firstChild = children.get(0);
        if (firstChild instanceof VBox && 
            !((VBox) firstChild).getChildren().isEmpty() && 
            ((VBox) firstChild).getChildren().get(0) instanceof Label && 
            ((Label) ((VBox) firstChild).getChildren().get(0)).getText().equals("Welcome to Image Management Tool")) {
            AlertUtils.showWarningAlert("Warning", "Please upload an image first");
            return true;
        }

        return false;
    }

    public boolean validateFormat(String format) {
        // Checks if format is null or empty
        if (format == null || format.trim().isEmpty()) {
            AlertUtils.showWarningAlert("Warning", "Please select a format for conversion");
            return true;
        }
        return false;
    }
} 