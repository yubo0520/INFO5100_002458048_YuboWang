package partB;
import java.util.Random;

public class Utility {
    private static final Random random = new Random();
    
    public static boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }
    
    public static void randomSleep() {
        try {
            Thread.sleep(random.nextInt(401) + 100);
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted");
        }
    }
} 