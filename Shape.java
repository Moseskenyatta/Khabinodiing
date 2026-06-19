public abstract class Shape {
    protected String color = "white";
    protected boolean filled;

    // Default Constructor
    public Shape() {
        // Keeps the default field values (color = "white", filled = false)
    }

    // Parameterized Constructor
    public Shape(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    // Abstract methods to be implemented by subclasses
    public abstract double getArea();
    public abstract double getPerimeter();
    public abstract void resize(double factor) throws InvalidShapeException;

    // Overridden toString() Method
    @Override 
    public String toString() {
        return "Shape[color=" + color + ", filled=" + filled + "]";
    }
}