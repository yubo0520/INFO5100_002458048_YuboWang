package imagetool.factory;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import java.io.File;
import javafx.scene.image.Image;

/**
 * UI Component Factory Interface - Defines interface for creating UI components using Factory Pattern
 */
public interface UIComponentFactory {
    Button createButton(String text, String style);
    ComboBox<String> createFormatComboBox();
    VBox createImageThumbnailBox(File file, Image thumbnail);
    VBox createPropertiesBox(File file);
} 