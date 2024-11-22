public class Main {
    public static void main(String[] args) {
        BakeryFactory factory = new BakeryFactory();
        BakeryManager bakeryManager = BakeryManager.getInstance();
        
        Customer customer1 = new Customer("John");
        Customer customer2 = new Customer("Mary");
        Customer customer3 = new Customer("Peter");
        Customer customer4 = new Customer("Sarah");
        
        System.out.println("===== Bakery Order System =====\n");
        
        processOrder(factory, bakeryManager, "sweet", "001", customer1);
        processOrder(factory, bakeryManager, "sourdough", "002", customer2);
        processOrder(factory, bakeryManager, "sweet", "003", customer3);
        processOrder(factory, bakeryManager, "sourdough", "004", customer4);
        processOrder(factory, bakeryManager, "sweet", "005", customer1);
    }
    
    private static void processOrder(BakeryFactory factory, BakeryManager bakeryManager, 
                                   String breadType, String orderId, Customer customer) {
        System.out.println("\n-------------------\n");
        Bread bread = factory.createBread(breadType);
        BakeryOrder order = new BakeryOrder(orderId, bread);
        order.addObserver(customer);
        
        bakeryManager.addOrder(order);
        order.processBread();
    }
}
