import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private List<Book> books;
    private List<Member> members;

    public Library() {
        this.books = new ArrayList<>();
        this.members = new ArrayList<>();
    }

    public void addBook(Book book) {
        books.add(book);
    }

    public void registerMember(Member member) {
        members.add(member);
    }

    public List<Book> searchBookByTitle(String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    // ==========================================
    // PASTE THE TWO METHODS HERE:
    // ==========================================
    
    public boolean lendBook(String memberId, String isbn) {
        Book targetBook = null;
        Member targetMember = null;

        for (Book b : books) {
            if (b.getIsbn().equals(isbn)) {
                targetBook = b;
                break;
            }
        }

        for (Member m : members) {
            if (m.getMemberId().equals(memberId)) {
                targetMember = m;
                break;
            }
        }

        if (targetBook != null && targetMember != null && targetBook.isAvailable()) {
            LocalDate today = LocalDate.now();
            LocalDate dueDate = today.plusDays(14); 

            Loan newLoan = new Loan(targetBook, targetMember, today, dueDate);
            targetMember.addLoan(newLoan);
            targetBook.setAvailable(false); 
            return true;
        }
        
        return false; 
    }

    public boolean returnBook(String isbn) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn) && !book.isAvailable()) {
                
                for (Member member : members) {
                    Loan targetLoan = null;
                    for (Loan loan : member.getCurrentLoans()) {
                        if (loan.getBook().getIsbn().equals(isbn)) {
                            targetLoan = loan;
                            break;
                        }
                    }
                    if (targetLoan != null) {
                        member.removeLoan(targetLoan);
                        break;
                    }
                }
                
                book.setAvailable(true); 
                return true;
            }
        }
        return false; 
    }

    @Override
    public String toString() {
        return "Library System [Total Books=" + books.size() + ", Registered Members=" + members.size() + "]";
    }
}