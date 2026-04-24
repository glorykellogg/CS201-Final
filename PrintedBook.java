import java.util.List;

// INHERITANCE: PrintedBook is a subclass of Book.
// It inherits common book fields and behavior from Book.
public class PrintedBook extends Book {

    // ENCAPSULATION: pages is private and accessed through methods.
    private int pages;

    // Constructor
    public PrintedBook(String title, String author, String genre, int pages) {
        super(title, author, genre, BookType.PRINTED);
        this.pages = pages;
    }

    // Getter
    public int getPages() {
        return pages;
    }

    // Setter
    public void setPages(int pages) {
        this.pages = pages;
    }

    // POLYMORPHISM: this class provides its own version of getCost().
    @Override
    public double getCost() {
        return pages * PAGECOST;
    }

    // Average pages across printed books
    public static double averagePages(List<PrintedBook> books) {
        if (books == null || books.isEmpty()) {
            return 0;
        }

        int total = 0;
        for (PrintedBook b : books) {
            total += b.getPages();
        }

        return (double) total / books.size();
    }

    // Implemented in the catalog since each book doesn't need to know about the other books
    public List<Book> getLastThree(BookType type) {
        return BookApp.catalog.getLastThree(type);
    }
}