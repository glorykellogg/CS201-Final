import java.io.*;
import java.util.*;

public class Catalog {

    private final ArrayList<Book> books;

    // Constructor
    public Catalog() {
        books = new ArrayList<>();
    }

    // Add a book
    public void addBook(Book book) {
        if (book != null) {
            books.add(book);
        }
    }

    // Search by title with partial match
    public List<Book> searchByTitle(String title) {
        List<Book> matches = new ArrayList<>();

        if (title == null) {
            return matches;
        }

        String lowerTitle = title.toLowerCase();

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(lowerTitle)) {
                matches.add(book);
            }
        }

        return matches;
    }

    // Search by author
    public List<Book> searchByAuthor(String author) {
        List<Book> matches = new ArrayList<>();

        if (author == null) {
            return matches;
        }

        for (Book book : books) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                matches.add(book);
            }
        }

        return matches;
    }

    // Search by genre
    public List<Book> searchByGenre(String genre) {
        List<Book> matches = new ArrayList<>();

        if (genre == null) {
            return matches;
        }

        for (Book book : books) {
            if (book.getGenre().equalsIgnoreCase(genre)) {
                matches.add(book);
            }
        }

        return matches;
    }

    // Search by book type
    public List<Book> searchByBookType(BookType bookType) {
        List<Book> matches = new ArrayList<>();

        if (bookType == null) {
            return matches;
        }

        for (Book book : books) {
            if (book.getBookType() == bookType) {
                matches.add(book);
            }
        }

        return matches;
    }

    // Sort by title
    public List<Book> sortByTitle() {
        List<Book> sortedBooks = new ArrayList<>(books);

        sortedBooks.sort(Comparator.comparing(Book::getTitle, String.CASE_INSENSITIVE_ORDER));

        return sortedBooks;
    }

    // Sort by author
    public List<Book> sortByAuthor() {
        List<Book> sortedBooks = new ArrayList<>(books);

        sortedBooks.sort(Comparator.comparing(Book::getAuthor, String.CASE_INSENSITIVE_ORDER));

        return sortedBooks;
    }

    // Sort by genre
    public List<Book> sortByGenre() {
        List<Book> sortedBooks = new ArrayList<>(books);

        sortedBooks.sort(Comparator.comparing(Book::getGenre, String.CASE_INSENSITIVE_ORDER));

        return sortedBooks;
    }

    // Sort by cost
    public List<Book> sortByCost() {
        List<Book> sortedBooks = new ArrayList<>(books);

        sortedBooks.sort(Comparator.comparingDouble(Book::getCost));

        return sortedBooks;
    }

    // Display statistics
    public void displayStatistics() {
        System.out.println("Total number of books: " + books.size());
        System.out.println("Total cost of all books: $" + String.format("%.2f", getTotalCost()));

        List<PrintedBook> printedBooks = new ArrayList<>();
        List<AudioBook> audioBooks = new ArrayList<>();

        for (Book book : books) {
            if (book instanceof PrintedBook) {
                printedBooks.add((PrintedBook) book);
            } else if (book instanceof AudioBook) {
                audioBooks.add((AudioBook) book);
            }
        }

        System.out.println("Average pages of printed books: " +
                String.format("%.2f", PrintedBook.averagePages(printedBooks)));
        System.out.println("Average duration of audiobooks: " +
                String.format("%.2f", AudioBook.averageDuration(audioBooks)));
    }

    // Get last 6 books added
    public List<Book> getLastSixBooks() {
        int size = books.size();
        return new ArrayList<>(books.subList(Math.max(0, size - 6), size));
    }

    public List<Book> getLastThree(BookType type) {
        List<Book> lastBooks = new ArrayList<>();
        for (int i = books.size() - 1; i >= 0; i--) {
            Book book = books.get(i);
            if (book.getBookType() == type) {
                lastBooks.add(book);

                if (lastBooks.size() == 3) {
                    break;
                }
            }
        }
        return lastBooks;
    }

        // Count books per genre
        public Map<String, Integer> numberBooksPerGenre() {
            Map<String, Integer> genreCounts = new HashMap<>();

            for (Book book : books) {
                String genre = book.getGenre();
                genreCounts.put(genre, genreCounts.getOrDefault(genre, 0) + 1);
            }

            return genreCounts;
        }

        // Total cost of all books
        // POLYMORPHISM: even though books are stored as Book objects, getCost() will call the
        // correct subclass version (PrintedBook or AudioBook).
        public double getTotalCost() {
            double total = 0.0;

            for (Book book : books) {
                total += book.getCost();
            }

            return total;
        }

        // Display all books
        public void displayAll () {
            if (books.isEmpty()) {
                System.out.println("No books in catalog.");
                return;
            }

            for (Book book : books) {
                System.out.println(book);
            }
        }

        // Save method
        public void saveToFile (String fileName){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

                // write header
                writer.write("Title,Author,Genre,BookType,Pages,Duration_Minutes,Cost");
                writer.newLine();

                for (Book book : books) {
                    writer.write(book.getTitle() + "," +
                            book.getAuthor() + "," +
                            book.getGenre() + ",");

                        if (book instanceof PrintedBook printed){
                            writer.write("Printed," + printed.getPages() + ",null,");
                        }

                        else if (book instanceof AudioBook audio){
                            writer.write("Audio,null," + audio.getMinutes() + ",");
                        }
                        else {
                            throw new RuntimeException("Unknown book type.");
                        }

                        writer.write(String.format("%.2f", book.getCost()));
                        writer.newLine();
                    }
                System.out.println("Catalog saved successfully.");

            } catch (IOException e) {
                System.out.println("Error saving file: " + e.getMessage());
            }
        }

        // Load method
        public void loadFromFile (String fileName){
            books.clear();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;

                // skip header
                reader.readLine();

                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", -1);

                    if (parts.length != 7) {
                        System.out.println("Skipping invalid line: " + line);
                        continue;
                    }

                    String title = parts[0];
                    String author = parts[1];
                    String genre = parts[2];
                    String type = parts[3];
                    String pagesStr = parts[4];
                    String durationStr = parts[5];
                    // cost is computed internally

                    if (type.equalsIgnoreCase("Printed")) {
                        int pages = Integer.parseInt(pagesStr);
                        books.add(new PrintedBook(title, author, genre, pages));

                    } else if (type.equalsIgnoreCase("Audio")) {
                        double duration = Double.parseDouble(durationStr);
                        books.add(new AudioBook(title, author, genre, duration));

                    } else {
                        System.out.println("Unknown type: " + type);
                    }
                }

                System.out.println("Catalog loaded successfully from " + fileName);

            } catch (IOException e) {
                System.out.println("Error loading file: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Error parsing numbers: " + e.getMessage());
            }
        }
    }