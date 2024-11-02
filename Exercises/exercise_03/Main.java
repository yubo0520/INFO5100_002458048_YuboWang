import java.io.*;

public class Main {
  public static void main(String[] args) {
      // polymorphism example
      Shape[] shapes = new Shape[4];
      shapes[0] = new Triangle("Green", 5, 6, 7);
      shapes[1] = new Rectangle("Black", 4, 5);
      shapes[2] = new Circle("Red", 2);
      shapes[3] = new Square("Blue", 3);
      
      for (Shape shape : shapes) {
          System.out.println("shape: " + shape.getName());
          System.out.println("color: " + shape.getColor());
          System.out.println("area: " + String.format("%.1f", shape.calculateArea()));
          System.out.println("perimeter: " + String.format("%.1f", shape.calculatePerimeter()));
          System.out.println();
      }

      //Serialization
      try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Shapes.ser"))) {
          out.writeObject(shapes);
          System.out.println("Serialization completed");
      } catch (IOException i) {
          System.err.println("Error while serializing");
          i.printStackTrace();
      }

      //Deserialization 
      Shape[] deserializedShapes = null;
      try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("Shapes.ser"))) {
          deserializedShapes = (Shape[]) in.readObject();
          System.out.println("Deserialization completed");
          
          // Verify deserialized results
          if (deserializedShapes != null) {
              System.out.println("\nDeserialized objects:");
              for (Shape shape : deserializedShapes) {
                  System.out.println("shape: " + shape.getName());
                  System.out.println("color: " + shape.getColor());
                  System.out.println();
              }
          }
      } catch (Exception e) {
          System.err.println("Error while deserializing");
          e.printStackTrace();
      }
  }
}