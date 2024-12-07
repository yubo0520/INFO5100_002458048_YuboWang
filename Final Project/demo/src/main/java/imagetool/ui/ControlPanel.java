package imagetool.ui;

import javafx.scene.layout.HBox;
import javafx.scene.control.*;

import imagetool.factory.DefaultUIComponentFactory;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javafx.collections.ObservableList;
import javafx.scene.Node;

import java.util.List;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import imagetool.service.ImageConversionService;
import imagetool.service.ImageUploadService;
import imagetool.service.ProgressService;
import imagetool.service.ValidationService;
import imagetool.utils.FileUtils;
import imagetool.constants.AppConstants;
import imagetool.config.ThreadPoolConfig;

// Main control panel class for image processing UI
public class ControlPanel {
    private final ImageContainer imageContainer;
    private final UIComponentManager uiManager;
    private final ImageConversionService conversionService;
    private final ImageUploadService uploadService;
    private final ProgressService progressService;
    private final ValidationService validationService;
    private final ExecutorService executorService;
    private static final Logger logger = LoggerFactory.getLogger(ControlPanel.class);

    // Initializes UI components and services
    public ControlPanel(ImageContainer imageContainer) {
        this.imageContainer = imageContainer;
        this.uiManager = new UIComponentManager(new DefaultUIComponentFactory());
        this.conversionService = new ImageConversionService();
        this.uploadService = new ImageUploadService(imageContainer, uiManager);
        this.progressService = new ProgressService(uiManager);
        this.validationService = new ValidationService(imageContainer);
        this.executorService = ThreadPoolConfig.getExecutorService();
        
        setupControls();
    }

    // Sets up control buttons and layout
    private void setupControls() {
        Button uploadButton = createUploadButton();
        Button convertButton = createConvertButton();

        uiManager.getControlBox().getChildren().addAll(
            uploadButton,
            uiManager.getFormatComboBox(),
            convertButton,
            uiManager.getProgressIndicator()
        );
    }

    public HBox getControlBox() {
        return uiManager.getControlBox();
    }

    public void cleanup() {
        conversionService.cleanup();
        executorService.shutdown();
    }

    private Button createUploadButton() {
        Button uploadButton = uiManager.getUIComponentFactory().createButton("Upload Image", 
                ButtonStyles.UPLOAD_BUTTON_STYLE);
        
        uploadButton.setOnMouseEntered(e -> 
            uploadButton.setStyle(ButtonStyles.UPLOAD_BUTTON_HOVER_STYLE)
        );
        uploadButton.setOnMouseExited(e -> 
            uploadButton.setStyle(ButtonStyles.UPLOAD_BUTTON_STYLE)
        );
        
        uploadButton.setOnAction(e -> handleUpload());
        return uploadButton;
    }

    private Button createConvertButton() {
        Button convertButton = uiManager.getUIComponentFactory().createButton("Convert",
                ButtonStyles.CONVERT_BUTTON_STYLE);
        
        convertButton.setOnMouseEntered(e -> 
            convertButton.setStyle(ButtonStyles.CONVERT_BUTTON_HOVER_STYLE)
        );
        convertButton.setOnMouseExited(e -> 
            convertButton.setStyle(ButtonStyles.CONVERT_BUTTON_STYLE)
        );
        
        convertButton.setOnAction(e -> {
            // Validates image upload status
            if (validationService.validateUploadStatus()) {
                return;
            }

            // Validates if a format is selected for conversion
            String selectedFormat = uiManager.getFormatComboBox().getValue();
            if (validationService.validateFormat(selectedFormat)) {
                return;
            }

            // If all checks pass, continue with conversion logic
            handleConvert();
        });
        
        return convertButton;
    }

    // Handles image upload process with multi-threading
    private void handleUpload() {
        List<File> files = uploadService.showFileChooser();
        if (files != null) {
            uiManager.getProgressIndicator().setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
            uiManager.getProgressIndicator().setVisible(true);

            CountDownLatch latch = new CountDownLatch(files.size());
            AtomicInteger successCount = new AtomicInteger(0);

            files.forEach(file -> executorService.submit(() -> {
                try {
                    uploadService.processImageFile(file, files.indexOf(file) == 0);
                    successCount.incrementAndGet();
                } catch (Exception e) {
                    logger.error("Error processing file: {}", e.getMessage(), e);
                } finally {
                    latch.countDown();
                    progressService.updateProgress(latch, successCount.get(), false);
                }
            }));
        }
    }

    // Processes image conversion with progress tracking
    private void handleConvert() {
        // Validates image upload status
        if (validationService.validateUploadStatus()) {
            return;
        }

        // Validates if a format is selected for conversion
        String selectedFormat = uiManager.getFormatComboBox().getValue();
        if (validationService.validateFormat(selectedFormat)) {
            return;
        }

        // If all checks pass, continue with conversion logic
        uiManager.getProgressIndicator().setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        uiManager.getProgressIndicator().setVisible(true);

        ObservableList<Node> nodes = imageContainer.getContainer().getChildren();
        CountDownLatch latch = new CountDownLatch(nodes.size());
        AtomicInteger successCount = new AtomicInteger(0);

        String format = selectedFormat.toLowerCase();
        File tempDir = createTempDirectory();

        nodes.forEach(node -> {
            if (node instanceof HBox cardBox) {
                executorService.submit(() -> {
                    try {
                        conversionService.convertImage(cardBox, format, tempDir);
                        Platform.runLater(() -> addDownloadButton(cardBox, (File)cardBox.getUserData()));
                        successCount.incrementAndGet();
                    } catch (Exception e) {
                        logger.error("Error converting image: {}", e.getMessage(), e);
                    } finally {
                        latch.countDown();
                        progressService.updateProgress(latch, successCount.get(), true);
                    }
                });
            }
        });
    }

    // Handles downloading of converted image files
    private void handleDownload(File originalFile) {
        File convertedFile = conversionService.getConvertedFile(originalFile);
        if (convertedFile != null && convertedFile.exists()) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save Converted Image");
            fileChooser.setInitialFileName(convertedFile.getName());
            
            File saveFile = fileChooser.showSaveDialog(null);
            if (saveFile != null) {
                try {
                    Files.copy(convertedFile.toPath(), saveFile.toPath(), 
                              StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    logger.error("Error saving file: {}", e.getMessage(), e);
                }
            }
        }
    }

    // Creates temporary directory for converted files
    private File createTempDirectory() {
        return FileUtils.createTempDirectory(AppConstants.TEMP_DIR_NAME);
    }

    // Adds download button to image card after conversion
    private void addDownloadButton(HBox cardBox, File sourceFile) {
        Button downloadButton = new Button("Download");
        downloadButton.setStyle(ButtonStyles.DOWNLOAD_BUTTON_STYLE);
        
        downloadButton.setOnMouseEntered(e -> 
            downloadButton.setStyle(ButtonStyles.DOWNLOAD_BUTTON_HOVER_STYLE)
        );
        downloadButton.setOnMouseExited(e -> 
            downloadButton.setStyle(ButtonStyles.DOWNLOAD_BUTTON_STYLE)
        );
        
        downloadButton.setOnAction(e -> handleDownload(sourceFile));
        cardBox.getChildren().add(downloadButton);
    }
} 