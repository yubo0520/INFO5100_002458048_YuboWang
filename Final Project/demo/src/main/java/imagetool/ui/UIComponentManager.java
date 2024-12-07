package imagetool.ui;

import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import imagetool.factory.UIComponentFactory;
import java.io.File;

public class UIComponentManager {
    // Core UI components
    private final UIComponentFactory uiFactory;
    private final ProgressIndicator progressIndicator;
    private final ComboBox<String> formatComboBox;
    private final HBox controlBox;

    public UIComponentManager(UIComponentFactory uiFactory) {
        this.uiFactory = uiFactory;
        
        this.controlBox = new HBox(40);
        controlBox.setAlignment(Pos.CENTER);
        controlBox.setPadding(new Insets(20, 40, 20, 40));
        controlBox.setStyle("-fx-background-color: white; -fx-border-color: #e9ecef; -fx-border-width: 1 0 0 0;");
        
        // Loading spinner
        this.progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);
        progressIndicator.setPrefSize(30, 30);
        
        // Format selection dropdown
        this.formatComboBox = uiFactory.createFormatComboBox();
        formatComboBox.setStyle("-fx-font-size: 14px; -fx-background-radius: 5; " +
                "-fx-border-radius: 5; -fx-border-color: #ced4da;");
        formatComboBox.setPrefWidth(150);
    }
    
    // Creates image card with thumbnail and info
    public HBox createImageCard(File file, Image thumbnail) {
        // Main card container
        HBox cardBox = new HBox(20);
        cardBox.setPadding(new Insets(15));
        cardBox.setPrefWidth(600);
        cardBox.setStyle("-fx-background-color: white; -fx-border-color: #e9ecef; " +
                "-fx-border-radius: 5; -fx-background-radius: 5;");

        // Thumbnail section
        VBox imageBox = uiFactory.createImageThumbnailBox(file, thumbnail);
        imageBox.setPrefWidth(280);
        imageBox.setMinWidth(280);
        imageBox.setMaxWidth(280);

        // Info section
        VBox propertyBox = uiFactory.createPropertiesBox(file);
        propertyBox.setPrefWidth(280);
        propertyBox.setMinWidth(280);
        propertyBox.setMaxWidth(280);

        cardBox.getChildren().addAll(imageBox, propertyBox);
        cardBox.setUserData(file);
        cardBox.setAlignment(Pos.CENTER);
        
        return cardBox;
    }

    // Getter methods
    public HBox getControlBox() { return controlBox; }
    public ProgressIndicator getProgressIndicator() { return progressIndicator; }
    public ComboBox<String> getFormatComboBox() { return formatComboBox; }
    public UIComponentFactory getUIComponentFactory() { return uiFactory; }
} 