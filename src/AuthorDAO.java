import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AuthorDAO {

    // 1. Veritabanına Yeni Yazar Ekleme İşlemi (CREATE)
    public boolean addAuthor(String authorName, String authorSurname, String biography) {
        // SQL Ekleme Sorgumuz:
        String sql = "INSERT INTO authors (author_name, author_surname, biography) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, authorName);
            pstmt.setString(2, authorSurname);
            pstmt.setString(3, biography);

            int result = pstmt.executeUpdate();
            return result > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Sistemdeki Tüm Yazarları Getirme İşlemi (READ)
    public List<authors> getAllAuthors() {
        List<authors> authorList = new ArrayList<>();
        String sql = "SELECT * FROM authors";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
               
                int id = rs.getInt("id");
                String name = rs.getString("author_name");
                String surname = rs.getString("author_surname");
                String bio = rs.getString("biography");

                //'authors' sınıfının beklediği 4 bilgiyi sırasıyla veriyoruz 
                authors newAuthor = new authors(id, name, surname, bio);

                authorList.add(newAuthor);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return authorList;
    }
}
