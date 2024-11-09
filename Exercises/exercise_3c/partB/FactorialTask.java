package partB;
public class FactorialTask implements Runnable {
    private final int number;
    
    public FactorialTask(int number) {
        this.number = number;
    }
    
    @Override
    public void run() {
        long result = 1;
        for (int i = 1; i <= number; i++) {
            result *= i;
        }
        System.out.printf("[%tT] %s: Factorial Calculator: %d! = %d%n",
            System.currentTimeMillis(),
            Thread.currentThread().getName(),
            number,
            result);
        Utility.randomSleep();
    }
} 