import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    // 1. Veritabanına Yeni Yazar Ekleme İşlemi (CREATE)
    public boolean addAuthor(String authorName, String authorSurname, String biography) {
        String sql = "INSERT INTO authors (author_name, author_surname, biography) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, authorName);
            pstmt.setString(2, authorSurname);
            pstmt.setString(3, biography);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Sistemdeki Tüm Yazarları Getirme İşlemi (READ ALL)
    public List<authors> getAllAuthors() {
        List<authors> authorList = new ArrayList<>();
        String sql = "SELECT * FROM authors";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                authorList.add(new authors(
                        rs.getInt("id"),
                        rs.getString("author_name"),
                        rs.getString("author_surname"),
                        rs.getString("biography")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return authorList;
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
    // Yazarı Güncelleme İşlemi (UPDATE)
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

    // 4. (YENİ) Sadece Girilen ID'ye Ait Yazarı Getirme İşlemi (READ SINGLE)
    public authors getAuthorById(int id) {
        String sql = "SELECT * FROM authors WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { // Yazar bulunduysa nesne olarak döndür
                return new authors(
                        rs.getInt("id"), rs.getString("author_name"),
                        rs.getString("author_surname"), rs.getString("biography")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null; // Yazar yoksa boş (null) döner
    }
}
