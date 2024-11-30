import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BookParser {

    // Write BookShelf to XML
    public static void writeToXml(BookShelf bookShelf, String filename) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("bookShelf");
        doc.appendChild(root);
        
        for (Book book : bookShelf.getBooks()) {
            root.appendChild(createBookElement(doc, book));
        }

        transformToFile(doc, filename);
    }

    // Read BookShelf from XML
    public static BookShelf readFromXml(String filename) throws Exception {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File(filename));
            doc.getDocumentElement().normalize();

            BookShelf bookShelf = new BookShelf();
            List<Book> books = new ArrayList<>();

            // Parse book elements
            NodeList bookNodes = doc.getElementsByTagName("book");
            for (int i = 0; i < bookNodes.getLength(); i++) {
                Node bookNode = bookNodes.item(i);
                if (bookNode.getNodeType() == Node.ELEMENT_NODE) {
                    books.add(parseBookElement((Element) bookNode));
                }
            }

            bookShelf.setBooks(books);
            return bookShelf;
        } catch (Exception e) {
            throw new Exception("Error reading from XML: " + e.getMessage(), e);
        }
    }

    // Write BookShelf to JSON
    public static void writeToJson(BookShelf bookShelf, String filename) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filename), bookShelf);
    }

    // Read BookShelf from JSON
    public static BookShelf readFromJson(String filename) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(new File(filename), BookShelf.class);
        } catch (IOException e) {
            throw new IOException("Error reading from JSON: " + e.getMessage(), e);
        }
    }

    // Create book element for XML
    private static Element createBookElement(Document doc, Book book) {
        Element bookElement = doc.createElement("book");

        appendChildWithText(doc, bookElement, "title", book.getTitle());
        appendChildWithText(doc, bookElement, "publishedYear", String.valueOf(book.getPublishedYear()));
        appendChildWithText(doc, bookElement, "numberOfPages", String.valueOf(book.getNumberOfPages()));

        Element authorsElement = doc.createElement("authors");
        for (String author : book.getAuthors()) {
            appendChildWithText(doc, authorsElement, "author", author);
        }
        bookElement.appendChild(authorsElement);

        return bookElement;
    }

    // Parse book element from XML
    private static Book parseBookElement(Element bookElement) {
        Book book = new Book();

        book.setTitle(getTagValue(bookElement, "title"));
        book.setPublishedYear(Integer.parseInt(getTagValue(bookElement, "publishedYear")));
        book.setNumberOfPages(Integer.parseInt(getTagValue(bookElement, "numberOfPages")));

        List<String> authors = new ArrayList<>();
        NodeList authorNodes = bookElement.getElementsByTagName("author");
        for (int i = 0; i < authorNodes.getLength(); i++) {
            authors.add(authorNodes.item(i).getTextContent());
        }
        book.setAuthors(authors);

        return book;
    }

    private static void appendChildWithText(Document doc, Element parent, String tagName, String text) {
        Element element = doc.createElement(tagName);
        element.setTextContent(text);
        parent.appendChild(element);
    }

    // Save DOM to XML file
    private static void transformToFile(Document doc, String filename) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(filename));
        transformer.transform(source, result);
    }

    // Get tag value
    private static String getTagValue(Element parent, String tagName) {
        return parent.getElementsByTagName(tagName).item(0).getTextContent();
    }
}
