import org.junit.jupiter.api.*;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

public class BookDAOTest {

    private BookDAO bookDAO;
    private AuthorDAO authorDAO;
    private final String testIsbn = "9999999999999";
    private final String testAuthorName = "TESTYAZAR";
    private final String testAuthorSurname = "TESTSOYAD";

    @BeforeEach
    void setUp() {
        bookDAO = new BookDAO();
        authorDAO = new AuthorDAO();

        try {
            books exBook = bookDAO.getBookByIsbn(testIsbn);
            if (exBook != null) bookDAO.deleteBook(exBook.getId());

            authors exAuthor = authorDAO.getAuthorByName(testAuthorName, testAuthorSurname);
            if (exAuthor != null) authorDAO.DeleteAuthor(exAuthor.getAuthor_name());
        } catch (Exception e) {
            System.out.println("Ön temizlik atlandı veya veri yok.");
        }
    }

    @Test
    void testBookLifecycle() {
        System.out.println("ADIM 1: Yazar kontrolü ve ekleme (Foreign Key kuralı için)...");
       
        if (!authorDAO.isAuthorNameExists(testAuthorName)) {
            authorDAO.addAuthor(testAuthorName, testAuthorSurname, "Test Biyografi");
        }

        authors dummyAuthor = authorDAO.getAuthorByName(testAuthorName, testAuthorSurname);
        assertNotNull(dummyAuthor, "Sahte yazar eklenemedi veya bulunamadı!");

        System.out.println("ADIM 2: Yeni kitap ekleme işlemi test ediliyor...");
        books dummyBook = new books(0, testIsbn, "Test Kitabı", LocalDate.now(), dummyAuthor.getId(), 1, testAuthorName, testAuthorSurname, 5, "Test Yayınları");
        boolean addResult = bookDAO.addBook(dummyBook);
        assertTrue(addResult, "Kitap veritabanına başarıyla eklenmeliydi!");

        System.out.println("ADIM 3: Eklenen ISBN'nin sistemde var olup olmadığı kontrol ediliyor...");
        boolean existsResult = bookDAO.isIsbnExists(testIsbn);
        assertTrue(existsResult, "Test ISBN'si veritabanında bulunmalıydı!");
    }

    @AfterEach
    void tearDown() {

        try {
            books testBook = bookDAO.getBookByIsbn(testIsbn);
            if (testBook != null) {
                bookDAO.deleteBook(testBook.getId());
                System.out.println("Temizlik 1: Test kitabı başarıyla silindi.");
            }

            authors testAuthor = authorDAO.getAuthorByName(testAuthorName, testAuthorSurname);
            if (testAuthor != null) {
                authorDAO.DeleteAuthor(testAuthor.getAuthor_name());
                System.out.println("Temizlik 2: Test yazarı başarıyla silindi.\n");
            }
        } catch (Exception e) {
            System.out.println("Temizlik sırasında hata: " + e.getMessage());
        }
    }
}
