public interface Bread {
    String getName();
    double getPrice();
    void bake();
}

class SweetBread implements Bread {
    public String getName() { return "Sweet Bread"; }
    public double getPrice() { return 8.0; }
    
    public void bake() {
        System.out.println("Baking delicious " + getName());
        System.out.println("Adding sugar and butter...");
        System.out.println(getName() + " is ready!");
    }
}

class SourdoughBread implements Bread {
    public String getName() { return "Sourdough Bread"; }
    public double getPrice() { return 12.0; }
    
    public void bake() {
        System.out.println("Baking tasty " + getName());
        System.out.println("Using natural yeast for fermentation...");
        System.out.println(getName() + " is ready!");
    }
} 