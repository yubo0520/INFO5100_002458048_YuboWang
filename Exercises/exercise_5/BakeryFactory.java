public class BakeryFactory {
    public Bread createBread(String type) {
        switch (type.toLowerCase()) {
            case "sweet":
                return new SweetBread();
            case "sourdough":
                return new SourdoughBread();
            default:
                throw new IllegalArgumentException("Unknown bread type");
        }
    }
} 