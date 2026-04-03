import java.sql.*;
import java.time.LocalDate;

public class MemberDAO {
    // Veritabanı bağlantı bilgilerini DBConnection sınıfından alacağız

    public boolean saveMember(member yeniUye) {
        String sql = "INSERT INTO users (first_name, last_name, email, password, role, created_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, yeniUye.getFirst_name());
            ps.setString(2, yeniUye.getLast_name());
            ps.setString(3, yeniUye.getEmail());
            ps.setString(4, yeniUye.getPassword()); // Şimdilik default bir şifre
            ps.setString(5, "Member"); // Role otomatik olarak Member
            ps.setDate(6, java.sql.Date.valueOf(yeniUye.getCreated_date()));

            int sonuc = ps.executeUpdate();
            return sonuc > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}