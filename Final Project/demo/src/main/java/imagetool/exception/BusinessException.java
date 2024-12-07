package imagetool.exception;

// Custom exception class for handling business logic related errors
public class BusinessException extends RuntimeException {
  
    public BusinessException(String message) {
        super(message);
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
} 

