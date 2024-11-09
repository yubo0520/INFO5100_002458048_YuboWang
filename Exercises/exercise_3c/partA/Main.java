package partA;
public class Main {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new PrimeCalculator(), "PrimeThread");
        Thread thread2 = new Thread(new FibonacciCalculator(), "FibonacciThread");
        Thread thread3 = new Thread(new FactorialCalculator(), "FactorialThread");
        
        thread1.start();
        thread2.start();
        thread3.start();
    }
} 