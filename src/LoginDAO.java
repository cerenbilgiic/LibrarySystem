import java.sql.*;

public class LoginDAO {
    public boolean checkLogin(String username, String password) {
        // SQL: Kullanıcı adı ve şifresi eşleşen bir kullanıcı var mı ve çalışan mı?
        // Kütüphane üyelerinin (Kütüphane Üyesi) sisteme giriş yapmasını engelliyoruz.
        String sql = "SELECT role FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String role = rs.getString("role");
                // Rolü "Kütüphane Üyesi" olanlar giriş yapamaz.
                // Kütüphane Çalışanı , Çalışan , Yönetici vs. giriş yapabilir.
                if (role != null && (role.equalsIgnoreCase("Kütüphane Üyesi") || role.equalsIgnoreCase("Üye"))) {
                    return false; // Üyelerin giriş izni yok
                }
                return true; // Kullanıcı bulundu ve üye değil (çalışan), giriş yapabilir
            }
            return false;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    // Kullanıcı adına göre Adı ve Soyadı bulan metot
    public String getUserFullName(String username) {
        String sql = "SELECT first_name, last_name FROM users WHERE username = ?";

        try (Connection conn = DBConnection.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
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