import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            BookShelf bookShelf = createSampleBookShelf();
            BookParser.writeToXml(bookShelf, "books.xml");
            BookParser.writeToJson(bookShelf, "books.json");

            // Parse XML
            System.out.println("------- Parse XML -------");
            bookShelf = BookParser.readFromXml("books.xml");
            printBookShelf(bookShelf);

            // Add new book and reprint(dynamically)
            Book newBook = new Book();
            newBook.setTitle("Smart Java");
            newBook.setPublishedYear(2022);
            newBook.setNumberOfPages(500);
            newBook.setAuthors(Arrays.asList("DOJO", "Join"));
            bookShelf.getBooks().add(newBook);

            System.out.println("\n------- XML after adding new book -------");
            printBookShelf(bookShelf);
            BookParser.writeToXml(bookShelf, "books.xml");

            // Parse JSON
            System.out.println("\n------- Parse JSON -------");
            bookShelf = BookParser.readFromJson("books.json");
            printBookShelf(bookShelf);

            // Add new book and reprint
            bookShelf.getBooks().add(newBook);
            System.out.println("\n------- JSON after adding new book -------");
            printBookShelf(bookShelf);
            BookParser.writeToJson(bookShelf, "books.json");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static BookShelf createSampleBookShelf() {
        BookShelf bookShelf = new BookShelf();
        List<Book> books = new ArrayList<>();

        Book book1 = new Book();
        book1.setTitle("Modern Java in Action: Lambdas, Streams, Functional and Reactive");
        book1.setPublishedYear(2017);
        book1.setNumberOfPages(435);
        book1.setAuthors(Arrays.asList("Robert C. Martin", "Joshua Bloch"));

        Book book2 = new Book();
        book2.setTitle("Java Concurrency in Practice");
        book2.setPublishedYear(2014);
        book2.setNumberOfPages(395);
        book2.setAuthors(Arrays.asList("Herbert chilli", "Richard Helm", "Ralph Johnson"));

        Book book3 = new Book();
        book3.setTitle("The Complete Reference");
        book3.setPublishedYear(2021);
        book3.setNumberOfPages(1234);
        book3.setAuthors(Arrays.asList("Thomas H. Cormen", "Josef Dojo"));

        Book book4 = new Book();
        book4.setTitle("Effective Java");
        book4.setPublishedYear(2018);
        book4.setNumberOfPages(592);
        book4.setAuthors(Arrays.asList("Alan Mycroft", "Mario Fosco"));

        books.add(book1);
        books.add(book2);
        books.add(book3);
        books.add(book4);
        bookShelf.setBooks(books);

        return bookShelf;
    }

    private static void printBookShelf(BookShelf bookShelf) {
        for (Book book : bookShelf.getBooks()) {
            System.out.println("Title: " + book.getTitle());
            System.out.println("Published Year: " + book.getPublishedYear());
            System.out.println("Number of Pages: " + book.getNumberOfPages());
            System.out.println("Authors: " + String.join(", ", book.getAuthors()));
            System.out.println();
        }
    }
}
