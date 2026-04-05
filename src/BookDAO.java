import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.SQLException;

public class BookDAO {
    public boolean isIsbnExists(String isbn) {
        String sql = "SELECT COUNT(*) FROM books WHERE isbn = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, isbn);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean addBook(books book) {
        String sql = "INSERT INTO books (isbn, name, publish_year,author_id, category_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getBook_name()); // Java nesnesinden adı alıyoruz
            pstmt.setObject(3, book.getPurchase_date()); // Java nesnesinden tarihi alıyoruz
            pstmt.setInt(4, book.getAuthorId());
            pstmt.setInt(5, book.getCategoryId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                System.out.println("Kitap veritabanına başarıyla eklendi!");
                return true;
            }

            return affectedRows > 0;

        } catch (Exception e) {
            System.out.println("Kitap eklenirken hata oluştu!");
            e.printStackTrace();
        }
        return false;
    }

    // 2. Kitap Arama
    public books getBookByName(String bookName) {
        String sql = "SELECT b.id, b.isbn, b.name, b.publish_year, " +
                "b.author_id, b.category_id, " +
                "a.author_name, a.author_surname, c.category_name " +
                "FROM books b " +
                "LEFT JOIN authors a ON b.author_id = a.id " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE b.name = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookName.trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new books(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("name"), // Artık başarıyla eşleşiyor
                        rs.getObject("publish_year", LocalDate.class),
                        rs.getInt("author_id"), // Artık SELECT'ten geldiği için çökmez
                        rs.getInt("category_id"), // Artık SELECT'ten geldiği için çökmez
                        rs.getString("author_name"),
                        rs.getString("author_surname"),
                        rs.getString("category_name")
                );
            }
        } catch (Exception e) {
            System.out.println("Kitap getirme hatası: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


    //kitap silme
    public boolean deleteBook(int bookId) {
        // Veritabanından ID'ye göre silen SQL sorgusu
        String sql = "DELETE FROM books WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Silme hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    //kitap güncelleme
    public boolean updateBook(int bookId, String isbn, String name, int authorId, int categoryId) {
        String sql = "UPDATE books SET isbn = ?, name = ?, author_id = ?, category_id = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            pstmt.setString(2, name);
            pstmt.setInt(3, authorId);
            pstmt.setInt(4, categoryId);
            pstmt.setInt(5, bookId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Güncelleme Hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}