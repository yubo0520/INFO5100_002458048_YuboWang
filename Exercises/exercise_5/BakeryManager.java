import java.util.ArrayList;
import java.util.List;

public class BakeryManager {
    private static BakeryManager instance;
    private List<BakeryOrder> orders;
    
    private BakeryManager() {
        orders = new ArrayList<>();
    }
    
    public static BakeryManager getInstance() {
        if (instance == null) {
            instance = new BakeryManager();
        }
        return instance;
    }
    
    public void addOrder(BakeryOrder order) {
        orders.add(order);
        System.out.println("Order recorded, Order ID: " + order.getOrderId());
    }
} 