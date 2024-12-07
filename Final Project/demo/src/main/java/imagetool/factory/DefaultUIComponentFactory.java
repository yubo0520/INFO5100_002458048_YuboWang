package imagetool.factory;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;
import java.util.Map;

import imagetool.utils.ImageMetadataExtractor;

// Default implementation of UI component factory that creates all UI components
public class DefaultUIComponentFactory implements UIComponentFactory {
    
    // Constants for default UI styling
    private static final String DEFAULT_FONT = "Arial";
    private static final int DEFAULT_FONT_SIZE = 12;
    private static final int DEFAULT_SPACING = 10;
    private static final int DEFAULT_PADDING = 10;
    
    // Creates a styled button 
    @Override
    public Button createButton(String text, String style) {
        Button button = new Button(text);    
        button.setStyle(style);               
        return button;                       
    }
    
    // Creates a format selection dropdown 
    @Override
    public ComboBox<String> createFormatComboBox() {
        ComboBox<String> comboBox = new ComboBox<>();          
        comboBox.getItems().addAll("PNG", "JPG", "GIF", "BMP"); 
        comboBox.setPromptText("Select format");                
        comboBox.setStyle("-fx-font-size: 14px; " +
                "-fx-background-radius: 5; " +
                "-fx-border-radius: 5; " +
                "-fx-border-color: #ced4da; "); 
        return comboBox;                                       
    }
    
    /**
     * Creates a container with image thumbnail and filename
     * @param file image file
     * @param thumbnail thumbnail
     * @return configured VBox container
     */
    @Override
    public VBox createImageThumbnailBox(File file, Image thumbnail) {
        ImageView imageView = createImageView(thumbnail);
        Label nameLabel = createNameLabel(file.getName());
        
        VBox imageBox = createContainerBox();
        imageBox.getChildren().addAll(imageView, nameLabel);
        imageBox.setUserData(file);

        return imageBox;
    }
    
    /**
     * Creates a container displaying image properties
     * @param file image file
     * @return configured VBox container
     */
    @Override
    public VBox createPropertiesBox(File file) {
        VBox propertyBox = new VBox(8);
        propertyBox.setPadding(new Insets(15));
        propertyBox.setStyle("-fx-background-color: #f8f9fa; -fx-border-color: #e9ecef; " +
                "-fx-border-radius: 5; -fx-background-radius: 5;");

        Map<String, String> properties = ImageMetadataExtractor.extractMetadata(file);
        properties.forEach((key, value) -> propertyBox.getChildren().add(createPropertyLabel(key, value)));

        return propertyBox;
    }

    // Helper methods for UI component creation
    private ImageView createImageView(Image thumbnail) {
        // Configures ImageView with smooth scaling and aspect ratio preservation
        ImageView imageView = new ImageView(thumbnail);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        return imageView;
    }

    private Label createNameLabel(String name) {
        // Creates and styles label for file name display    
        Label label = new Label(name);
        label.setFont(Font.font(DEFAULT_FONT, DEFAULT_FONT_SIZE));
        label.setTextFill(Color.DARKSLATEGRAY);
        return label;
    }

    private VBox createContainerBox() {
        // Creates and configures VBox container with default styling
        VBox box = new VBox(DEFAULT_SPACING);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(DEFAULT_PADDING));
        box.setStyle("-fx-background-color: transparent;");
        return box;
    }

    private Label createPropertyLabel(String key, String value) {
        // Creates and styles label for property display with word wrap
        Label label = new Label(key + ": " + value);
        label.setFont(Font.font(DEFAULT_FONT, DEFAULT_FONT_SIZE));
        label.setWrapText(true);
        label.setTextFill(Color.DARKSLATEGRAY);
        return label;
    }
} 