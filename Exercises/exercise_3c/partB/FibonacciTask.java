package partB;
public class FibonacciTask implements Runnable {
    private final int position;
    
    public FibonacciTask(int position) {
        this.position = position;
    }
    
    @Override
    public void run() {
        long prev = 0;
        long current = 1;
        
        if (position == 1) {
            System.out.printf("[%tT] %s: Fibonacci Calculator: No.%d = %d%n",
                System.currentTimeMillis(),
                Thread.currentThread().getName(),
                position,
                prev);
            Utility.randomSleep();
            return;
        }
        
        for (int i = 2; i <= position; i++) {
            if (i == position) {
                System.out.printf("[%tT] %s: Fibonacci Calculator: No.%d = %d%n",
                    System.currentTimeMillis(),
                    Thread.currentThread().getName(),
                    position,
                    current);
                Utility.randomSleep();
            }
            long next = prev + current;
            prev = current;
            current = next;
        }
    }
} 