import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class BookApp {

    public static Catalog catalog;

    // Command Line Interface
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        catalog = new Catalog();

        boolean running = true;

        while (running) {
            printMenu();
            int choice = promptForInt(input, "Enter your choice: ");

            switch (choice) {
                case 1:
                    addBook(input, catalog);
                    break;
                case 2:
                    displayAllBooks(catalog);
                    break;
                case 3:
                    searchBooks(input, catalog);
                    break;
                case 4:
                    sortBooks(input, catalog);
                    break;
                case 5:
                    displayStatistics(catalog);
                    break;
                case 6:
                    displayLastBooks(catalog);
                    break;
                case 7:
                    loadCatalog(input, catalog);
                    break;
                case 8:
                    saveCatalog(input, catalog);
                    break;
                case 9:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println();
        }

        input.close();
    }

    public static void printMenu() {
        System.out.println("========== BOOK CATALOG MENU ==========");
        System.out.println("1. Add Book");
        System.out.println("2. Display All Books");
        System.out.println("3. Search Books");
        System.out.println("4. Sort Books");
        System.out.println("5. Display Statistics");
        System.out.println("6. Display Last Added Books");
        System.out.println("7. Load Catalog from File");
        System.out.println("8. Save Catalog to File");
        System.out.println("9. Exit");
        System.out.println("=======================================");
    }

    public static void addBook(Scanner input, Catalog catalog) {
        System.out.println("Add Book");
        System.out.println("1. Printed Book");
        System.out.println("2. Audio Book");

        int typeChoice = promptForInt(input, "Choose book type: ");

        String title = promptForString(input, "Enter title: ");
        String author = promptForString(input, "Enter author: ");
        String genre = promptForString(input, "Enter genre: ");

        if (typeChoice == 1) {
            int pages = promptForInt(input, "Enter number of pages: ");
            PrintedBook printedBook = new PrintedBook(title, author, genre, pages);
            catalog.addBook(printedBook);
            System.out.println("Printed book added successfully.");

        } else if (typeChoice == 2) {
            double minutes = promptForDouble(input, "Enter duration in minutes: ");
            AudioBook audioBook = new AudioBook(title, author, genre, minutes);
            catalog.addBook(audioBook);
            System.out.println("Audio book added successfully.");

        } else {
            System.out.println("Invalid book type.");
        }
    }

    public static void displayAllBooks(Catalog catalog) {
        System.out.println("All Books:");
        catalog.displayAll();
    }

    public static void searchBooks(Scanner input, Catalog catalog) {
        System.out.println("Search Books By:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Genre");
        System.out.println("4. Book Type");

        int choice = promptForInt(input, "Choose search option: ");
        List<Book> results;

        switch (choice) {
            case 1:
                String title = promptForString(input, "Enter title keyword: ");
                results = catalog.searchByTitle(title);
                printBookList(results);
                break;

            case 2:
                String author = promptForString(input, "Enter author: ");
                results = catalog.searchByAuthor(author);
                printBookList(results);
                break;

            case 3:
                String genre = promptForString(input, "Enter genre: ");
                results = catalog.searchByGenre(genre);
                printBookList(results);
                break;

            case 4:
                System.out.println("1. PRINTED");
                System.out.println("2. AUDIO");
                int typeChoice = promptForInt(input, "Choose book type: ");

                if (typeChoice == 1) {
                    results = catalog.searchByBookType(BookType.PRINTED);
                    printBookList(results);

                } else if (typeChoice == 2) {
                    results = catalog.searchByBookType(BookType.AUDIO);
                    printBookList(results);

                } else {
                    System.out.println("Invalid book type.");
                }
                break;

            default:
                System.out.println("Invalid search option.");
        }
    }

    public static void sortBooks(Scanner input, Catalog catalog) {
        System.out.println("Sort Books By:");
        System.out.println("1. Title");
        System.out.println("2. Author");
        System.out.println("3. Genre");
        System.out.println("4. Cost");

        int choice = promptForInt(input, "Choose sort option: ");
        List<Book> sortedBooks;

        switch (choice) {
            case 1:
                sortedBooks = catalog.sortByTitle();
                printBookList(sortedBooks);
                break;

            case 2:
                sortedBooks = catalog.sortByAuthor();
                printBookList(sortedBooks);
                break;

            case 3:
                sortedBooks = catalog.sortByGenre();
                printBookList(sortedBooks);
                break;

            case 4:
                sortedBooks = catalog.sortByCost();
                printBookList(sortedBooks);
                break;

            default:
                System.out.println("Invalid sort option.");
        }
    }

    public static void displayStatistics(Catalog catalog) {
        System.out.println("Catalog Statistics:\n");
        catalog.displayStatistics();

        Map<String, Integer> genreCounts = catalog.numberBooksPerGenre();
        if (!genreCounts.isEmpty()) {
            System.out.println("\nBooks per Genre:");
            for (String genre : genreCounts.keySet()) {
                System.out.println(genre + ": " + genreCounts.get(genre));
            }
        }
    }

    public static void displayLastBooks(Catalog catalog) {
        System.out.println("Last 6 Books:");
        printBookList(catalog.getLastSixBooks());

        System.out.println("Last 3 Printed Books:");
        List<Book> printedBooks = catalog.getLastThree(BookType.PRINTED);
        printBookList(printedBooks);
        if (printedBooks.isEmpty()) {
            System.out.println("No printed books found.");
        }

        System.out.println("Last 3 Audio Books:");
        List<Book> audioBooks = catalog.getLastThree(BookType.AUDIO);
        printBookList(audioBooks);
        if (audioBooks.isEmpty()) {
            System.out.println("No audio books found.");
        }
    }

    public static void saveCatalog(Scanner input, Catalog catalog) {
        String fileName = promptForString(input, "Enter file name to save: ");
        catalog.saveToFile(fileName);
    }

    public static void loadCatalog(Scanner input, Catalog catalog) {
        String fileName = promptForString(input, "Enter file name to load: ");
        catalog.loadFromFile(fileName);
    }

    public static void printBookList(List<? extends BookInterface> books) {
        if (books == null || books.isEmpty()) {
            System.out.println("No matching books found.");
            return;
        }

        for (BookInterface book : books) {
            System.out.println(book);
        }
    }

    public static String promptForString(Scanner input, String message) {
        System.out.print(message);
        return input.nextLine().trim();
    }

    public static int promptForInt(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            String userInput = input.nextLine();

            try {
                return Integer.parseInt(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please try again.");
            }
        }
    }

    public static double promptForDouble(Scanner input, String message) {
        while (true) {
            System.out.print(message);
            String userInput = input.nextLine();

            try {
                return Double.parseDouble(userInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
}
