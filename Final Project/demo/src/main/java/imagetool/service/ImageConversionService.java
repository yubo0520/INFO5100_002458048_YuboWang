package imagetool.service;

import javafx.scene.layout.HBox;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageConversionService {
    private static final Logger logger = LoggerFactory.getLogger(ImageConversionService.class);
    // Stores mapping of original files to their converted versions
    private final Map<File, File> convertedFiles;
    
    public ImageConversionService() {
        this.convertedFiles = new HashMap<>();
    }
    
    // Converts image to specified format and stores in temporary directory
    public void convertImage(HBox cardBox, String format, File tempDir) throws IOException {
        File sourceFile = (File) cardBox.getUserData();
        String baseName = sourceFile.getName();
        File outputFile = new File(tempDir, baseName + "." + format);
        
        BufferedImage image = ImageIO.read(sourceFile);
        ImageIO.write(image, format, outputFile);
        
        convertedFiles.put(sourceFile, outputFile);
    }
    
    // Returns the converted file for a given original file
    public File getConvertedFile(File originalFile) {
        return convertedFiles.get(originalFile);
    }
    
    // Removes all temporary converted files
    public void cleanup() {
        convertedFiles.values().forEach(file -> {
            if (!file.delete()) {
                logger.warn("Failed to delete temporary file: {}", file.getAbsolutePath());
            }
        });
    }
} 