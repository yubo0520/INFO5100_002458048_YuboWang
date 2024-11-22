public class SweetBread implements Bread {
    public String getName() { return "Sweet Bread"; }
    public double getPrice() { return 8.0; }
    
    public void bake() {
        System.out.println("Baking delicious " + getName());
        System.out.println("Adding sugar and butter");
        System.out.println(getName() + " is ready!");
    }
} 