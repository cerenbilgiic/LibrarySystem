import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MemberDAOTest {

    private MemberDAO memberDAO;
    private final String testTC = "88888888888"; // Gerçek veriyle karışmaması için sahte TC

    @BeforeEach
    void setUp() {
        memberDAO = new MemberDAO();
     
        try {
            member exMember = memberDAO.getMemberByTC(testTC);
            if (exMember != null) {
                memberDAO.DeleteMember(exMember.getId());
            }
        } catch (Exception e) {
            System.out.println("Ön temizlik hatası: " + e.getMessage());
        }
    }

    @Test
    void testMemberLifecycle() {
        System.out.println("ADIM 1: Yeni üye ekleme işlemi test ediliyor...");
        boolean addResult = memberDAO.addMember("TestAd", "TestSoyad", testTC);
        assertTrue(addResult, "Üye veritabanına başarıyla eklenmeliydi!");

        System.out.println("ADIM 2: Eklenen TC'nin sistemde var olup olmadığı kontrol ediliyor...");
        boolean existsResult = memberDAO.isTcExists(testTC);
        assertTrue(existsResult, "Test TC'si veritabanında bulunmalıydı!");
    }

    @AfterEach
    void tearDown() {
      
        try {
            member testMember = memberDAO.getMemberByTC(testTC);
            if (testMember != null) {
                memberDAO.DeleteMember(testMember.getId());
                System.out.println("Temizlik: Test üyesi veritabanından silindi.\n");
            }
        } catch (Exception e) {
            System.out.println("Temizlik sırasında hata: " + e.getMessage());
        }
    }
}
