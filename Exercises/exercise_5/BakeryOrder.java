import java.util.ArrayList;
import java.util.List;

public class BakeryOrder {
    private String orderId;
    private Bread bread;
    private List<BakeryObserver> observers;
    
    public BakeryOrder(String orderId, Bread bread) {
        this.orderId = orderId;
        this.bread = bread;
        this.observers = new ArrayList<>();
    }
    
    public void addObserver(BakeryObserver observer) {
        observers.add(observer);
    }
    
    public void notifyObservers(String message) {
        for (BakeryObserver observer : observers) {
            observer.update(message);
        }
    }
    
    public String getOrderId() {
        return orderId;
    }
    
    public void processBread() {
        System.out.println("\nStarting to make bread...");
        bread.bake();
        notifyObservers("Your " + bread.getName() + " is being baked");
        
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        notifyObservers("Your " + bread.getName() + " is ready! Total price: $" + bread.getPrice());
    }
} 