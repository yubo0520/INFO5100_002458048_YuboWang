package imagetool.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    
    // Creates a temporary directory
    public static File createTempDirectory(String dirName) {
        File tempDir = new File(System.getProperty("java.io.tmpdir"), dirName);
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            logger.warn("Failed to create temporary directory: {}", tempDir.getAbsolutePath());
            throw new RuntimeException("Failed to create temporary directory");
        }
        return tempDir;
    }
    
    // Copies a file from source to destination, replacing if exists
    public static void copyFile(File source, File destination) throws IOException {
        Files.copy(source.toPath(), destination.toPath(), StandardCopyOption.REPLACE_EXISTING);
    }
    
    // Deletes a file and logs a warning if deletion fails
    public static void deleteFile(File file) {
        if (file != null && file.exists() && !file.delete()) {
            logger.warn("Failed to delete file: {}", file.getAbsolutePath());
        }
    }
} 