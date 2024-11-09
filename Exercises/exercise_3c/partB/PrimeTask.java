package partB;
public class PrimeTask implements Runnable {
    private final int position;
    
    public PrimeTask(int position) {
        this.position = position;
    }
    
    @Override
    public void run() {
        int count = 0;
        int number = 2;
        
        while (count < position) {
            if (Utility.isPrime(number)) {
                count++;
                if (count == position) {
                    System.out.printf("[%tT] %s: Prime Calculator: No.%d = %d%n",
                        System.currentTimeMillis(),
                        Thread.currentThread().getName(),
                        position,
                        number);
                    Utility.randomSleep();
                }
            }
            number++;
        }
    }
} 