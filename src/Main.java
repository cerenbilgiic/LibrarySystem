import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("Veritabanı bağlantısı başarıyla kuruldu.");
            }

        } catch (Exception e) {
            System.out.println("Bağlantı sırasında bir hata oluştu!");
            e.printStackTrace();
        }
    }
}
