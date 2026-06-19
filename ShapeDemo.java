public class ShapeDemo {

    // Method to print the area of each shape using dynamic binding
    public static void printAreas(Shape[] shapes) {
        System.out.println("--- Printing Shape Areas (Dynamic Binding) ---");

        for (Shape s : shapes) {
            if (s != null) {
                System.out.printf(
                    "%s -> Area: %.2f%n",
                    s.getClass().getSimpleName(),
                    s.getArea()
                );
            }
        }
    }

    // Method to find and return the shape with the largest area
    public static Shape largest(Shape[] shapes) {
        if (shapes == null || shapes.length == 0) {
            return null;
        }

        // Start by assuming the first shape is the largest
        Shape largestShape = shapes[0];

        for (int i = 1; i < shapes.length; i++) {
            if (shapes[i] != null &&
                shapes[i].getArea() > largestShape.getArea()) {

                largestShape = shapes[i];
            }
        }

        return largestShape;
    }

    public static void main(String[] args) {

        System.out.println("=== PART D: POLYMORPHISM & DYNAMIC BINDING DEMO ===\n");

        try {

            // Create an array of different shapes
            Shape[] shapes = new Shape[3];

            shapes[0] = new Circle(5.0, "Red", true);
            shapes[1] = new Rectangle(4.0, 6.0, "Blue", false);
            shapes[2] = new Triangle(3.0, 4.0, 5.0, "Green", true);

            // Test printAreas method
            printAreas(shapes);
            System.out.println();

            // Test largest method
            Shape biggest = largest(shapes);

            System.out.println("--- Largest Shape Found ---");
            System.out.println(biggest);

            if (biggest != null) {
                System.out.printf(
                    "With an Area of: %.2f%n",
                    biggest.getArea()
                );
            }

        } catch (InvalidShapeException e) {
            System.out.println("Initialization Error: " + e.getMessage());
        }

        System.out.println("\n=== PART C: EXCEPTION CATCHING DEMO ===\n");

        // Demonstrate catching the custom exception
        try {
            System.out.println(
                "Attempting to create an invalid Triangle (sides 1, 2, 10)..."
            );

            Shape invalidTriangle =
                new Triangle(1, 2, 10, "Yellow", false);

            System.out.println(invalidTriangle);

        } catch (InvalidShapeException e) {
            System.out.println("[CAUGHT EXPECTED EXCEPTION SUCCESSFULLY]");
            System.out.println("Exception message: " + e.getMessage());
        }
    }
}