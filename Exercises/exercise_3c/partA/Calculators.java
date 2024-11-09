package partA;
import java.util.Random;

class PrimeCalculator implements Runnable {
    @Override
    public void run() {
        int count = 0;
        int number = 2;
        Random random = new Random();
        
        while (count < 25) {
            if (isPrime(number)) {
                count++;
                System.out.printf("[%tT] %s: Prime Calculator: Found No.%d prime = %d%n",
                    System.currentTimeMillis(),
                    Thread.currentThread().getName(),
                    count,
                    number);
                
                try {
                    Thread.sleep(random.nextInt(401) + 100);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }
            number++;
        }
    }
    
    private boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}

class FibonacciCalculator implements Runnable {
    @Override
    public void run() {
        long prev = 0;
        long current = 1;
        Random random = new Random();
        
        System.out.printf("[%tT] %s: Fibonacci Calculator: No.1 = %d%n",
            System.currentTimeMillis(),
            Thread.currentThread().getName(),
            prev);
        System.out.printf("[%tT] %s: Fibonacci Calculator: No.2 = %d%n",
            System.currentTimeMillis(),
            Thread.currentThread().getName(),
            current);
        
        for (int i = 3; i <= 50; i++) {
            long next = prev + current;
            System.out.printf("[%tT] %s: Fibonacci Calculator: No.%d = %d%n",
                System.currentTimeMillis(),
                Thread.currentThread().getName(),
                i,
                next);
            
            try {
                Thread.sleep(random.nextInt(401) + 100);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
            
            prev = current;
            current = next;
        }
    }
}

class FactorialCalculator implements Runnable {
    @Override
    public void run() {
        Random random = new Random();
        
        for (int i = 1; i <= 50; i++) {
            long result = 1;
            for (int j = 1; j <= i; j++) {
                result = result * j;
            }
            
            System.out.printf("[%tT] %s: Factorial Calculator: %d! = %d%n",
                System.currentTimeMillis(),
                Thread.currentThread().getName(),
                i,
                result);
            
            try {
                Thread.sleep(random.nextInt(401) + 100);
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
} 