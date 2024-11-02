import java.io.*;
// abstract class Shape implements Serializable
abstract class Shape implements Serializable {
    protected String color;
    public static final long SerialVerisionUID = 1L;

    public Shape(String color) {
        this.color = color;
    }
    
    // abstract method
    public abstract double calculateArea();
    public abstract double calculatePerimeter();
    public abstract String getName();

    // normal method
    public String getColor() {
        return color;
    }
}

// Triangle class
class Triangle extends Shape {
    private double side1, side2, side3;
    private static final String name = "Triangle";

    public Triangle(String color, double side1, double side2, double side3) {
        super(color);
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
    
    // override abstract method according to Heron's formula
    @Override
    public double calculateArea() {
        double s = (side1 + side2 + side3) / 2;
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }
    
    @Override
    public double calculatePerimeter() {
        return side1 + side2 + side3;
    }

    @Override
    public String getName() {
        return name;
    }
}

// Circle class
class Circle extends Shape {
    private double radius;
    public static final double PI = 3.14159;
    private static final String name = "Circle";
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    @Override
    public double calculateArea() {
        return PI * radius * radius;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * PI * radius;
    }

    @Override
    public String getName() {
        return name;
    }
}


class Rectangle extends Shape {
    protected double width, height;
    private static final String name = "Rectangle";

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }
    
    @Override
    public double calculateArea() {
        return width * height;
    }
    
    @Override
    public double calculatePerimeter() {
        return 2 * (width + height);
    }   
    
    @Override
    public String getName() {   
        return name;
    }
}


class Square extends Rectangle {
    private static final String name = "Square";

    public Square(String color, double side) {
        super(color, side, side);
    }

    @Override
    public String getName() {
        return name;
    }
}



