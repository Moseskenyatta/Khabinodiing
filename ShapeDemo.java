import java.util.Arrays;

// ==========================================
// 1. THE MAIN APPLICATION ENGINE
// ==========================================
public class ShapeDemo {

    public static void main(String[] args) {
        try {
            Shape[] shapes = {
                new Circle("Red", true, 5.0),
                new Rectangle("Blue", false, 4.0, 6.0),
                new Triangle("Green", true, 3.0, 4.0, 5.0)
            };

            printAreas(shapes);

            Shape big = largest(shapes);
            System.out.println("\n--- Largest Shape Object ---");
            if (big != null) {
                System.out.println(big + " with an area of: " + String.format("%.2f", big.getArea()));
            }
        } catch (InvalidShapeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void printAreas(Shape[] shapes) {
        System.out.println("--- Polymorphic Tracking: Areas ---");
        for (Shape shape : shapes) {
            if (shape != null) {
                System.out.println(shape + " has an area of: " + String.format("%.2f", shape.getArea()));
            }
        }
    }

    public static Shape largest(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            return null;
        }

        Shape largestShape = shapes;
        for (Shape shape : shapes) {
            if (shape != null && largestShape != null) {
                if (shape.getArea() > largestShape.getArea()) {
                    largestShape = shape;
                }
            }
        }
        return largestShape;
    }
}

// ==========================================
// 2. THE CUSTOM EXCEPTION
// ==========================================
class InvalidShapeException extends Exception {
    public InvalidShapeException(String message) {
        super(message);
    }
}

// ==========================================
// 3. THE ABSTRACT BASE CLASS
// ==========================================
abstract class Shape {
    private String color;
    private boolean filled;

    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public String getColor() { return color; }
    public boolean isFilled() { return filled; }

    public abstract double getArea();

    @Override
    public String toString() {
        return String.format("Shape[Color=%s, Filled=%b]", color, filled);
    }
}

// ==========================================
// 4. CONCRETE SHAPE SUBCLASSES
// ==========================================

class Circle extends Shape {
    private double radius;

    public Circle(String color, boolean filled, double radius) throws InvalidShapeException {
        super(color, filled);
        if (radius <= 0) {
            throw new InvalidShapeException("Radius must be greater than zero.");
        }
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public String toString() {
        return String.format("Circle[Radius=%.1f, %s]", radius, super.toString());
    }
}

class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(String color, boolean filled, double width, double height) throws InvalidShapeException {
        super(color, filled);
        if (width <= 0 || height <= 0) {
            throw new InvalidShapeException("Width and height must be greater than zero.");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public String toString() {
        return String.format("Rectangle[Width=%.1f, Height=%.1f, %s]", width, height, super.toString());
    }
}

class Triangle extends Shape {
    private double s1, s2, s3;

    public Triangle(String color, boolean filled, double s1, double s2, double s3) throws InvalidShapeException {
        super(color, filled);
        if (s1 <= 0 || s2 <= 0 || s3 <= 0) {
            throw new InvalidShapeException("All sides must be greater than zero.");
        }
        if ((s1 + s2 <= s3) || (s1 + s3 <= s2) || (s2 + s3 <= s1)) {
            throw new InvalidShapeException("The given sides do not form a valid triangle.");
        }
        this.s1 = s1;
        this.s2 = s2;
        this.s3 = s3;
    }

    @Override
    public double getArea() {
        double s = (s1 + s2 + s3) / 2.0;
        return Math.sqrt(s * (s - s1) * (s - s2) * (s - s3));
    }

    @Override
    public String toString() {
        return String.format("Triangle[Sides=%.1f, %.1f, %.1f, %s]", s1, s2, s3, super.toString());
    }
}