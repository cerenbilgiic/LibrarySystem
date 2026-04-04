import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.SQLException;

public class BookDAO {
    public void addBook(books book) {
        String sql = "INSERT INTO books (isbn, name, publish_year,author_id, category_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getBook_name()); // Java nesnesinden adı alıyoruz
            pstmt.setObject(3, book.getPurchase_date()); // Java nesnesinden tarihi alıyoruz
            pstmt.setInt(4,book.getAuthorId());
            pstmt.setInt(5, book.getCategoryId());

            pstmt.executeUpdate();
            System.out.println("Kitap veritabanına başarıyla eklendi!");

        } catch (Exception e) {
            System.out.println("Kitap eklenirken hata oluştu!");
            e.printStackTrace();
        }
    }

    // 2. Kitap Arama
    public books getBookByName(String name) {
        // LOWER kullanarak harf duyarlılığını ortadan kaldırıyoruz
        String sql = "SELECT b.*, a.author_name, a.author_surname, c.category_name " +
                "FROM books b " +
                "LEFT JOIN authors a ON b.author_id = a.id " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE LOWER(b.name) = LOWER(?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name.trim()); // Boşlukları temizliyoruz
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String fullName = rs.getString("author_name") + " " + rs.getString("author_surname");
                String categoryName = rs.getString("category_name");
                return new books(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getObject("publish_year", LocalDate.class),
                        rs.getInt("author_id"),
                        rs.getInt("category_id"),
                        fullName,
                        "bilinmiyor",
                        categoryName
                );
            }
        } catch (Exception e) {
            // Hatanın ne olduğunu anlamak için burası çok önemli!
            System.out.println("Arama sırasında teknik hata: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
    public boolean deleteBook(int bookId) {
        // Veritabanından ID'ye göre silen SQL sorgusu
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Eğer en az 1 satır silindiyse true döner

        } catch (SQLException e) {
            System.out.println("Silme hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}