package imagetool.utils;

import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.GpsDirectory;
import java.io.File;
import java.util.Map;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Utility class for extracting image metadata
public class ImageMetadataExtractor {
    private static final Logger logger = LoggerFactory.getLogger(ImageMetadataExtractor.class);

    // Extract metadata from image file and return as Map
    public static Map<String, String> extractMetadata(File imageFile) {
        Map<String, String> properties = new LinkedHashMap<>();

        try {
            properties.put("File Name", imageFile.getName());

            // Calculate and store file size
            long bytes = imageFile.length();
            if (bytes > 1024 * 1024) {
                properties.put("File Size", String.format("%.2f MB", bytes / (1024.0 * 1024.0)));
            } else {
                properties.put("File Size", String.format("%.2f KB", bytes / 1024.0));
            }

            // Get image dimensions
            BufferedImage image = ImageIO.read(imageFile);
            if (image != null) {
                properties.put("Width", image.getWidth() + " pixels");
                properties.put("Height", image.getHeight() + " pixels");
            }

            // Read all metadata
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            logger.info("Reading metadata from: {}", imageFile.getName());

            // Extract camera info from EXIF
            ExifIFD0Directory exifIFD0 = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
            if (exifIFD0 != null) {
                logger.info("Found EXIF IFD0 directory");
                
                if (exifIFD0.containsTag(ExifIFD0Directory.TAG_MAKE)) {
                    String make = exifIFD0.getString(ExifIFD0Directory.TAG_MAKE);
                    properties.put("Camera Maker", make);
                    logger.info("Camera Maker: {}", make);
                }
                
                if (exifIFD0.containsTag(ExifIFD0Directory.TAG_MODEL)) {
                    String model = exifIFD0.getString(ExifIFD0Directory.TAG_MODEL);
                    properties.put("Camera Model", model);
                    logger.info("Camera Model: {}", model);
                }
            } else {
                logger.info("No EXIF IFD0 directory found");
            }

            // Extract GPS coordinates if available
            GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
            if (gpsDirectory != null) {
                logger.info("Found GPS directory");
                if (gpsDirectory.containsTag(GpsDirectory.TAG_LATITUDE) &&
                        gpsDirectory.containsTag(GpsDirectory.TAG_LONGITUDE)) {
                    String latitude = gpsDirectory.getDescription(GpsDirectory.TAG_LATITUDE);
                    String longitude = gpsDirectory.getDescription(GpsDirectory.TAG_LONGITUDE);
                    properties.put("Location", String.format("Lat: %s, Long: %s", latitude, longitude));
                    logger.info("Location found: {}, {}", latitude, longitude);
                } else {
                    logger.info("No GPS coordinates found");
                }
            } else {
                logger.info("No GPS directory found");
            }

            // Log all metadata tags for debugging
            for (Directory directory : metadata.getDirectories()) {
                for (com.drew.metadata.Tag tag : directory.getTags()) {
                    logger.info(tag.toString());
                }
            }

        } catch (Exception e) {
            logger.error("Failed to extract metadata: {}", e.getMessage(), e);
            properties.put("Error", "Cannot read metadata: " + e.getMessage());
        }

        return properties;
    }
}