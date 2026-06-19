public class Triangle extends Shape {
    private double side1;
    private double side2;
    private double side3;

    public Triangle(double side1, double side2, double side3, String color, boolean filled) throws InvalidShapeException {
        super(color, filled);
        
        // Rule 1: Check for positive dimensions
        if (side1 <= 0 || side2 <= 0 || side3 <= 0) {
            throw new InvalidShapeException("All triangle sides must be positive.");
        }
        
        // Rule 2: Check Triangle Inequality Theorem
        if ((side1 + side2 <= side3) || (side1 + side3 <= side2) || (side2 + side3 <= side1)) {
            throw new InvalidShapeException("The given sides do not form a valid triangle (Triangle Inequality Violation).");
        }

        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }

    @Override
    public double getArea() {
        double s = getPerimeter() / 2.0; // Semi-perimeter
        return Math.sqrt(s * (s - side1) * (s - side2) * (s - side3));
    }

    @Override
    public double getPerimeter() {
        return side1 + side2 + side3;
    }

    @Override
    public void resize(double factor) throws InvalidShapeException {
        if (factor <= 0) {
            throw new InvalidShapeException("Resize factor must be positive. Given: " + factor);
        }
        this.side1 *= factor;
        this.side2 *= factor;
        this.side3 *= factor;
    }

    @Override
    public String toString() {
        return "Triangle[" + super.toString() + ", side1=" + side1 + ", side2=" + side2 + ", side3=" + side3 + "]";
    }
}