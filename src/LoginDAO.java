import java.sql.*;

public class LoginDAO {
    public boolean checkLogin(String email, String password) {
        // SQL: Email ve şifresi eşleşen bir kullanıcı var mı?
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // Eğer bir satır döndüyse (kullanıcı bulunduysa) true döner.

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // E-postaya göre Adı ve Soyadı bulan metot
    public String getUserFullName(String email) {
        String sql = "SELECT first_name, last_name FROM users WHERE email = ?";

        try (Connection conn = DBConnection.getConnection(); // (Siz DBConnection diye de kullanıyor olabilirsiniz, ona dikkat edin)
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getString("first_name") + " " + rs.getString("last_name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "YÖNETİCİ"; // Veritabanında hata olursa veya adını bulamazsa varsayılan olarak bu yazar.
    }

}