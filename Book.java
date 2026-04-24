import java.util.Map;

// ABSTRACTION: Book is an abstract class.
// It represents the common blueprint for all books,
// but leaves specific cost calculation to subclasses.
public abstract class Book implements BookInterface {

    // ENCAPSULATION: these fields are private, so they cannot be accessed directly from outside the class.
    private final String title;
    private final String author;
    private final String genre;
    private final BookType bookType;

    // Protected so subclasses can use it in their own cost calculations.
    protected final double PAGECOST = .5;

    // Constructor
    public Book(String title, String author, String genre, BookType bookType) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.bookType = bookType;
        // Cost is computed internally
    }


    // ENCAPSULATION: getters provide controlled access to private data.
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public BookType getBookType() {
        return bookType;
    }

    // ABSTRACTION: subclasses must provide their own implementation of how cost is calculated.
    public abstract double getCost();

    // POLYMORPHISM: this method calls getCost(), and Java will use the correct subclass version at runtime.
    @Override
    public String toString() {
        return "Title: " + title +
                ", Author: " + author +
                ", Genre: " + genre +
                ", Type: " + bookType +
                ", Cost: $" + String.format("%.2f", getCost());
    }

    // Implemented in the catalog since it doesn't make sense for each book to know about the other books
    @Override
    public Map<String, Integer> numberBooksPerGenre() {
        return BookApp.catalog.numberBooksPerGenre();
    }

    // Implemented in the catalog since each book doesn't need to know about the other books
    @Override
    public double getTotalCost() {
        return BookApp.catalog.getTotalCost();
    }
}