package imagetool.utils;

import javafx.scene.control.Alert;

// For displaying JavaFX alerts
public class AlertUtils {
    // Shows an alert dialog 
    private static void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Shows an error alert dialog 
    public static void showErrorAlert(String title, String content) {
        showAlert(Alert.AlertType.ERROR, title, content);
    }

    // Shows a warning alert dialog 
    public static void showWarningAlert(String title, String content) {
        showAlert(Alert.AlertType.WARNING, title, content);
    }
} 