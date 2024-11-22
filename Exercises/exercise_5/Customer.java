public class Customer implements BakeryObserver {
    private String name;
    
    public Customer(String name) {
        this.name = name;
    }
    
    public void update(String message) {
        System.out.println(name + " received notification: " + message);
    }
} 