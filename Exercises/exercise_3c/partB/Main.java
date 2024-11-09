package partB;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class Main {
    public static void main(String[] args) {
        ThreadFactory threadFactory = new ThreadFactory() {
            private int count = 1;
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "PoolThread-" + count++);
            }
        };
        
        ExecutorService threadPool = Executors.newFixedThreadPool(5, threadFactory);
        
        for (int i = 1; i <= 25; i++) {
            final int position = i;
            threadPool.submit(new PrimeTask(position));
        }
        
        for (int i = 1; i <= 50; i++) {
            final int position = i;
            threadPool.submit(new FibonacciTask(position));
        }
        
        for (int i = 1; i <= 50; i++) {
            final int position = i;
            threadPool.submit(new FactorialTask(position));
        }
        
        threadPool.shutdown();
    }
} 