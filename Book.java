public class Book {
    private String isbn;
    private String title;
    private String author;
    private boolean available; // Matched directly to the assignment skeleton

    // Constructor 1: Takes only ISBN and Title (Defaults author and availability)
    public Book(String isbn, String title) {
        this.isbn = isbn;
        this.title = title;
        this.author = "Unknown Author";
        this.available = true; // Automatically available when first added
    }

    // Constructor 2: Overloaded (Takes all three textual properties)
    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.available = true; 
    }

    // --- Accessors (Getters) and Mutators (Setters) ---

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    // Standard getter name for a boolean property matching 'available'
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // --- Meaningful toString() Override ---
    @Override
    public String toString() {
        return "Book [ISBN=" + isbn + ", Title='" + title + "', Author='" + author + "', Status=" + (available ? "Available" : "On Loan") + "]";
    }
}