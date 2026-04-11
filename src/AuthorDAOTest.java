import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorDAOTest {

    private AuthorDAO authorDAO;
    private final String testAuthorName = "JUNITAD";
    private final String testAuthorSurname = "JUNITSOYAD";

    @BeforeEach
    void setUp() {
        authorDAO = new AuthorDAO();
      
        try {
           
            if (authorDAO.isAuthorNameExists(testAuthorName)) {
                authorDAO.DeleteAuthor(testAuthorName);
            }
        } catch (Exception e) {
            System.out.println("Ön temizlik atlandı veya veri yok.");
        }
    }

    @Test
    void testAuthorLifecycle() {
        System.out.println("ADIM 1: Yeni yazar ekleme işlemi test ediliyor...");
        boolean addResult = authorDAO.addAuthor(testAuthorName, testAuthorSurname, "JUnit Test Biyografisi");
        assertTrue(addResult, "Yazar veritabanına başarıyla eklenmeliydi!");

        System.out.println("ADIM 2: Eklenen yazarın sistemde var olup olmadığı kontrol ediliyor...");
        boolean existsResult = authorDAO.isAuthorNameExists(testAuthorName);
        assertTrue(existsResult, "Test yazarı veritabanında bulunmalıydı!");

        System.out.println("ADIM 3: Yazar bilgileri veritabanından nesne olarak çekiliyor...");
        authors dummyAuthor = authorDAO.getAuthorByName(testAuthorName, testAuthorSurname);
        assertNotNull(dummyAuthor, "Yazar nesnesi veritabanından eksiksiz getirilebilmeliydi!");
    }

    @AfterEach
    void tearDown() {
      
        try {
            if (authorDAO.isAuthorNameExists(testAuthorName)) {
                authorDAO.DeleteAuthor(testAuthorName);
                System.out.println("Temizlik: Test yazarı veritabanından silindi.\n");
            }
        } catch (Exception e) {
            System.out.println("Temizlik sırasında hata: " + e.getMessage());
        }
    }
}
