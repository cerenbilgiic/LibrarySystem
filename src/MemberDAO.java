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
    public member getMemberByName(String first_name, String last_name ) {

        String sql = "SELECT * FROM users " +
                "WHERE LOWER(TRIM(first_name)) = LOWER(TRIM(?)) " +
                "AND LOWER(TRIM(last_name)) = LOWER(TRIM(?))";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, first_name.trim());
            pstmt.setString(2, last_name.trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                return new member(
                 rs.getInt("id")  ,
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("role"),
                        rs.getTimestamp("created_at").toLocalDateTime().toLocalDate(),
                rs.getInt("maxAllowedbooks")

                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}