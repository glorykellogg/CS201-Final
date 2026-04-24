import java.util.Map;

// ABSTRACTION: the interface defines behaviors that book-related classes must support.
public interface BookInterface {

    // Default method to display all books
    default void displayAll() {
        System.out.println("Displaying all books...");
    }

    // Returns number of books per genre
    Map<String, Integer> numberBooksPerGenre();

    // Returns total cost of all books
    double getTotalCost();
}