public class Rectangle extends Shape {
    private double width;
    private double height;

    public Rectangle(double width, double height, String color, boolean filled) throws InvalidShapeException {
        super(color, filled);
        if (width <= 0 || height <= 0) {
            throw new InvalidShapeException("Width and height must be positive.");
        }
        this.width = width;
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public void resize(double factor) throws InvalidShapeException {
        if (factor <= 0) {
            throw new InvalidShapeException("Resize factor must be positive. Given: " + factor);
        }
        this.width *= factor;
        this.height *= factor;
    }

    @Override
    public String toString() {
        return "Rectangle[" + super.toString() + ", width=" + width + ", height=" + height + "]";
    }
}