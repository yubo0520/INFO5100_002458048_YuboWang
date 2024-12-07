package imagetool.ui;

import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

// Welcome guide UI component for the Image Management Tool
public class WelcomeGuide {
    public VBox createWelcomeBox() {
        VBox welcomeBox = new VBox(30);
        welcomeBox.setAlignment(Pos.CENTER);
        welcomeBox.setPadding(new Insets(50));
        welcomeBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        welcomeBox.setMaxWidth(Double.MAX_VALUE);
        welcomeBox.setStyle("-fx-background-color: white; -fx-border-color: #e9ecef; " +
                "-fx-border-radius: 10; -fx-background-radius: 10;");

        // Creates and styles the main title
        Label titleLabel = new Label("Welcome to Image Management Tool");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.DARKSLATEGRAY);
        titleLabel.setWrapText(true);

        // Create a container for the instruction steps
        // Uses a light gray background with rounded corners
        VBox instructionsBox = new VBox(15);
        instructionsBox.setAlignment(Pos.CENTER);
        instructionsBox.setPadding(new Insets(30));
        instructionsBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #e9ecef; " +
                "-fx-border-radius: 8; -fx-background-radius: 8;");
        instructionsBox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        instructionsBox.setMaxWidth(Double.MAX_VALUE);

        // Creates and adds numbered instruction steps
        VBox steps = new VBox(12);
        steps.setAlignment(Pos.CENTER);
        steps.getChildren().addAll(
            createStepLabel("1. Click 'Upload Image' to select one or more image files"),
            createStepLabel("2. View image thumbnails and properties in cards"),
            createStepLabel("3. Select target format from the dropdown menu"),
            createStepLabel("4. Click 'Convert' to convert all images"),
            createStepLabel("5. Choose a directory to save converted images")
        );

        instructionsBox.getChildren().add(steps);
        welcomeBox.getChildren().addAll(titleLabel, instructionsBox);
        return welcomeBox;
    }

    // Creates a styled instruction step label
    private Label createStepLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 16));
        label.setTextFill(Color.DARKSLATEGRAY);
        label.setWrapText(true);
        return label;
    }
} 