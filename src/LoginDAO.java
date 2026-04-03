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
}