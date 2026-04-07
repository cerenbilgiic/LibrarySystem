import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    // ÇALIŞAN EKLEME
    public boolean addEmployee(String first_name , String last_name ,String username ,String password) {
        String sql = "INSERT INTO users (first_name , last_name , username , password, role) VALUES (?, ?, ?, ?, 'Kütüphane Çalışanı')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, username);
            pstmt.setString(4, password);
            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Çalışan arama işlemi.
    public employees getEmployeeByName(String first_name, String last_name) {
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

                return new employees(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        creationDate

                );
            }

        } catch (Exception e) {
            // Eğer yine bir hata çıkarsa, program size konsoldan haber versin
            System.out.println("----- ÇALIŞAN ARAMADA BİR HATA ÇIKTI -----");
            e.printStackTrace();
        }

        return null;
    }


    // username üzerinden çalışan arama işlemi
    public employees getEmployeeByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username.trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                java.sql.Timestamp dbDate = rs.getTimestamp("created_at");
                LocalDate creationDate = (dbDate != null) ? dbDate.toLocalDateTime().toLocalDate() : LocalDate.now();

                return new employees(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("role"),
                        creationDate
                );
            }
        } catch (Exception e) {
            System.out.println("----- ÇALIŞAN ARAMADA HATA -----");
            e.printStackTrace();
        }
        return null;
    }

    //üye silme işlemi.

    public boolean DeleteEmployee(int id ) {
        String sql = "DELETE FROM users WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Silme başarılıysa true döner

        } catch (SQLException e) {
            System.out.println("Çalışan silinirken SQL hatası oluştu!");
            e.printStackTrace();
            return false;
        }
    }

    //çalışan güncelleme
    public boolean updateEmployee(int id , String first_name , String last_name , String username , String password) {
        String sql = "UPDATE users SET first_name = ?, last_name = ?, username = ?, password = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, first_name);
            pstmt.setString(2, last_name);
            pstmt.setString(3, username);
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
