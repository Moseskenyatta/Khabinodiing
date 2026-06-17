public class LibraryDemo {
    public static void main(String[] args) {
        // 1. Initialize the library system instance
        Library library = new Library();

        // 2. Create and add 3 books (utilizing overloaded constructors)
        Book book1 = new Book("978-111", "Introduction to Java", "James Gosling");
        Book book2 = new Book("978-222", "Clean Code", "Robert Martin");
        Book book3 = new Book("978-333", "Design Patterns"); // Falls back to default author
        
        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);

        // 3. Create and register 2 members
        Member member1 = new Member("M101", "John Doe");
        Member member2 = new Member("M102", "Jane Smith");
        
        library.registerMember(member1);
        library.registerMember(member2);

        // ==========================================================
        // DISPLAY STATE BEFORE OPERATIONS
        // ==========================================================
        System.out.println("==================================================");
        System.out.println("          LIBRARY STATE: BEFORE OPERATIONS        ");
        System.out.println("==================================================");
        System.out.println(library);
        System.out.println("\n--- Initial Books Status ---");
        System.out.println(book1);
        System.out.println(book2);
        System.out.println(book3);
        System.out.println("\n--- Registered Members ---");
        System.out.println(member1);
        System.out.println(member2);
        System.out.println("==================================================\n");

        // ==========================================================
        // EXECUTING THE WORKFLOW SEQUENCE
        // ==========================================================
        System.out.println("--- Executing Library Operations ---\n");

        // Operation 1: Successful Lend
        System.out.println("[OP 1] Lending 'Introduction to Java' to John Doe (M101)...");
        boolean op1 = library.lendBook("M101", "978-111");
        System.out.println("Transaction Result: " + (op1 ? "SUCCESS" : "REJECTED") + "\n");

        // Operation 2: Another Successful Lend
        System.out.println("[OP 2] Lending 'Clean Code' to Jane Smith (M102)...");
        boolean op2 = library.lendBook("M102", "978-222");
        System.out.println("Transaction Result: " + (op2 ? "SUCCESS" : "REJECTED") + "\n");

        // Operation 3: MANDATORY BUSINESS RULE REJECTION CHECK
        // Attempting to lend 'Introduction to Java' to Jane even though John already has it checked out.
        System.out.println("[OP 3] Attempting to lend 'Introduction to Java' to Jane Smith (M102)...");
        boolean op3 = library.lendBook("M102", "978-111");
        System.out.println("Transaction Result: " + (op3 ? "SUCCESS" : "REJECTED (Enforced rule: Book is currently out on loan)"));
        System.out.println();

        // Operation 4: Successful Return
        System.out.println("[OP 4] Returning 'Introduction to Java' back to the library...");
        boolean op4 = library.returnBook("978-111");
        System.out.println("Transaction Result: " + (op4 ? "SUCCESS" : "REJECTED") + "\n");

        // ==========================================================
        // DISPLAY STATE AFTER OPERATIONS
        // ==========================================================
        System.out.println("==================================================");
        System.out.println("          LIBRARY STATE: AFTER OPERATIONS         ");
        System.out.println("==================================================");
        System.out.println(library);
        System.out.println("\n--- Final Books Status ---");
        System.out.println(book1); // Should show 'Available' again because it was returned in OP 4
        System.out.println(book2); // Should show 'On Loan' because Jane still holds it from OP 2
        System.out.println(book3); // Should show 'Available' (never borrowed)
        System.out.println("\n--- Registered Members ---");
        System.out.println(member1); // Active loan count should back down to 0
        System.out.println(member2); // Active loan count should be 1
        System.out.println("==================================================");
    }
}