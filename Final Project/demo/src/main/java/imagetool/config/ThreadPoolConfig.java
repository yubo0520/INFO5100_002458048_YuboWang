package imagetool.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Thread pool configuration class for managing concurrent tasks
public class ThreadPoolConfig {
    // Create a fixed thread pool with size equal to available CPU cores
    private static final ExecutorService executorService = Executors.newFixedThreadPool(
        Runtime.getRuntime().availableProcessors()
    );
    
    // Get the singleton executor service instance
    public static ExecutorService getExecutorService() {
        return executorService;
    }
} 