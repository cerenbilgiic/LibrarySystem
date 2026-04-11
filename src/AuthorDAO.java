import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorDAO {
    public boolean isAuthorNameExists(String AuthorName) {
        String sql = "SELECT COUNT(*) FROM authors WHERE author_name = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, AuthorName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // yazar ekleme işlemi
    public boolean addAuthor(String authorName, String authorSurname, String biography) {
        String sql = "INSERT INTO authors (author_name, author_surname, biography) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, authorName.toUpperCase());
            pstmt.setString(2, authorSurname.toUpperCase());
            pstmt.setString(3, biography);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Yazar arama.
    public authors getAuthorByName(String author_name, String author_surname) {

        String sql = "SELECT * FROM authors " +
                "WHERE LOWER(TRIM(author_name)) = LOWER(TRIM(?)) " +
                "AND LOWER(TRIM(author_surname)) = LOWER(TRIM(?))";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, author_name.trim());
            pstmt.setString(2, author_surname.trim());

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new authors(
                        rs.getInt("id"),
                        rs.getString("author_name"),
                        rs.getString("author_surname"),
                        rs.getString("biography")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    //Yazar silme.
    public boolean DeleteAuthor(String authorName) {
        String sql = "DELETE FROM authors WHERE author_name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, authorName);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Silme başarılıysa true döner

        } catch (SQLException e) {
            System.out.println("Yazar silinirken SQL hatası oluştu!");
            e.printStackTrace();
            return false;
        }
    }
    // Yazarı Güncelleme
    public boolean updateAuthor(int id, String authorName, String authorSurname, String biography) {
        String sql = "UPDATE authors SET author_name = ?, author_surname = ?, biography = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, authorName);
            pstmt.setString(2, authorSurname);
            pstmt.setString(3, biography);
            pstmt.setInt(4, id);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //kayıtlı toplam yazarı göstermeye yarar.
    public int getTotalAuthorCount() {
        String sql = "SELECT COUNT(*) FROM authors";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
}
