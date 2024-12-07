package imagetool.ui;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

public class ImageContainer {
    private final VBox container;
    private final ScrollPane scrollPane;

    public ImageContainer() {
        // Setup vertical layout container
        container = new VBox(20);
        container.setPadding(new Insets(20));
        container.setStyle("-fx-background-color: white;");

        // Sets up scrollable wrapper
        scrollPane = new ScrollPane(container);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent; -fx-background: transparent;");
        scrollPane.setPadding(new Insets(10));
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public VBox getContainer() {
        return container;
    }

    public void addWelcomeGuide(VBox welcomeBox) {
        container.getChildren().add(welcomeBox);
    }
} 