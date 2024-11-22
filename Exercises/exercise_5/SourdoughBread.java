public class SourdoughBread implements Bread {
    public String getName() { return "Sourdough Bread"; }
    public double getPrice() { return 12.0; }
    
    public void bake() {
        System.out.println("Baking tasty " + getName());
        System.out.println("Using natural yeast for fermentation");
        System.out.println(getName() + " is ready!");
    }
} 