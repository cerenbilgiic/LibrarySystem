import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    public boolean addMember(String first_name , String last_name ,String tc ) {
        String sql = "INSERT INTO users (first_name , last_name , tc, role) VALUES (?, ?, ?, 'Kütüphane Üyesi')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, tc);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // username üzerinden üye arama işlemi
    public member getMemberByTC(String tc) {
        String sql = "SELECT * FROM users WHERE tc = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tc.trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                String role = rs.getString("role");
                if (role == null || (!role.equalsIgnoreCase("Kütüphane Üyesi") && !role.equalsIgnoreCase("Üye"))) {
                    return null; // Yalnızca kütüphane üyeleri gelsin
                }

                java.sql.Timestamp dbDate = rs.getTimestamp("created_at");
                LocalDate creationDate = (dbDate != null) ? dbDate.toLocalDateTime().toLocalDate() : LocalDate.now();

                return new member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("tc"),
                        rs.getString("role"),
                        creationDate,
                        rs.getInt("maxAllowedbooks")
                );
            }
        } catch (Exception e) {
            System.out.println("----- KULLANICI ADI İLE ÜYE ARAMADA HATA -----");
            e.printStackTrace();
        }
        return null;
    }

    //üye silme işlemi.

    public boolean DeleteMember(int id ) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Silme başarılıysa true döner

        } catch (SQLException e) {
            System.out.println("Üye silinirken SQL hatası oluştu!");
            e.printStackTrace();
            return false;
        }
    }

    //üye güncelleme
    public boolean updateMember(int id , String first_name , String last_name , String tc ) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, tc = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, tc);
            pstmt.setInt(4,id);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Güncelleme Hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}