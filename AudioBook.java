import java.util.List;

// INHERITANCE: AudioBook inherits from Book
public class AudioBook extends Book {

    private double minutes;

    // Constructor
    public AudioBook(String title, String author, String genre, double minutes) {
        super(title, author, genre, BookType.AUDIO);
        this.minutes = minutes;
    }

    // Getter
    public double getMinutes() {
        return minutes;
    }

    // Setter
    public void setMinutes(double minutes) {
        this.minutes = minutes;
    }

    // POLYMORPHISM: same method name, different behavior depending on object type
    @Override
    public double getCost() {
        return minutes * (PAGECOST / 2);
    }

    // Average duration across audiobooks
    public static double averageDuration(List<AudioBook> audiobooks) {
        if (audiobooks == null || audiobooks.isEmpty()) {
            return 0;
        }

        double total = 0;
        for (AudioBook book : audiobooks) {
            total += book.getMinutes();
        }

        return total / audiobooks.size();
    }

    // Implemented in the catalog since each book doesn't need to know about the other books
    public List<Book> getLastThree(BookType type) {
        return BookApp.catalog.getLastThree(type);
    }
}
