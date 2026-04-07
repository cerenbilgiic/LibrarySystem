import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    // ÜYE EKLE
    public boolean addMember(String first_name , String last_name ,String email ,String password) {
        String sql = "INSERT INTO users (first_name , last_name , email , password) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setString(4, password);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Üye arama işlemi.
    public member getMemberByName(String first_name, String last_name) {
        String sql = "SELECT * FROM users " +
                "WHERE LOWER(first_name) LIKE LOWER(?) " +
                "AND LOWER(last_name) LIKE LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Kullanıcının ismini ve soyismini % (joker karakter) işaretleriyle sararak
            // veritabanını daha esnek aramaya zorluyoruz.
            pstmt.setString(1, "%" + first_name.trim() + "%");
            pstmt.setString(2, "%" + last_name.trim() + "%");

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                // GİZLİ HATA ÖNLEYİCİ (Null Kontrolü):
                // Eğer created_at veritabanında null ise kodun çökmemesini garanti altına alıyoruz.
                java.sql.Timestamp dbDate = rs.getTimestamp("created_at");
                LocalDate creationDate = (dbDate != null) ? dbDate.toLocalDateTime().toLocalDate() : LocalDate.now();

                return new member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        creationDate, // <-- Hata çıkartan kısım düzeltildi.
                        rs.getInt("maxAllowedbooks")
                );
            }

        } catch (Exception e) {
            // Eğer yine bir hata çıkarsa, program size konsoldan haber versin
            System.out.println("----- ÜYE ARAMADA BİR HATA ÇIKTI -----");
            e.printStackTrace();
        }

        return null;
    }


    // e-posta üzerinden üye arama işlemi
    public member getMemberByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, email.trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                java.sql.Timestamp dbDate = rs.getTimestamp("created_at");
                LocalDate creationDate = (dbDate != null) ? dbDate.toLocalDateTime().toLocalDate() : LocalDate.now();

                return new member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        creationDate,
                        rs.getInt("maxAllowedbooks")
                );
            }
        } catch (Exception e) {
            System.out.println("----- E-POSTA İLE ÜYE ARAMADA HATA -----");
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
    public boolean updateMember(int id , String first_name , String last_name , String email , String password) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, email = ?, password = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, email);
            pstmt.setString(4,password);
            pstmt.setInt(5,id);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Güncelleme Hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}