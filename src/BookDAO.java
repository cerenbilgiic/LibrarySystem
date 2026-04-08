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
        String sql = "INSERT INTO books (isbn, name, publish_year,author_id, category_id,stock) VALUES ( ?, ?, ?, ?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getBook_name()); // Java nesnesinden adı alıyoruz
            pstmt.setObject(3, book.getPurchase_date()); // Java nesnesinden tarihi alıyoruz
            pstmt.setInt(4, book.getAuthorId());
            pstmt.setString(5, book.getCategory_name());
            pstmt.setInt(6,book.getStock());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Kitap eklenirken hata oluştu!");
            e.printStackTrace();
        }
        return false;
    }

    // 2. Kitap Arama
    public books getBookByName(String bookName) {
        String sql = "SELECT b.id, b.isbn, b.name, b.publish_year, " +
                "b.author_id, b.category_id, b.stock, " +
                "a.author_name, a.author_surname, c.category_name " +
                "FROM books b " +
                "LEFT JOIN authors a ON b.author_id = a.id " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE LOWER(b.name) LIKE LOWER(?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookName.trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                books foundBook = new books(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getDate("publish_year").toLocalDate(),
                        rs.getInt("author_id"),
                        rs.getInt("category_id"),
                        rs.getString("author_name"),
                        rs.getString("author_surname"),
                        rs.getInt("stock")
                );

                foundBook.setCategory_name(rs.getString("category_name"));
                return foundBook;
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

    public int getTotalBookCount() {
        String sql = "SELECT COUNT(*) FROM books";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }

    public books getBookByIsbn(String isbn) {
        String sql = "SELECT b.id, b.isbn, b.name, b.publish_year, " +
                "b.author_id, b.category_id, b.stock, " +
                "a.author_name, a.author_surname, c.category_name " +
                "FROM books b " +
                "LEFT JOIN authors a ON b.author_id = a.id " +
                "LEFT JOIN categories c ON b.category_id = c.id " +
                "WHERE b.isbn = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn.trim());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                books foundBook = new books(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("name"),
                        rs.getDate("publish_year") != null ? rs.getDate("publish_year").toLocalDate() : LocalDate.now(),
                        rs.getInt("author_id"),
                        rs.getInt("category_id"),
                        rs.getString("author_name"),
                        rs.getString("author_surname"),
                        rs.getInt("stock")
                );

                foundBook.setCategory_name(rs.getString("category_name"));
                return foundBook;
            }

        } catch (Exception e) {
            System.out.println("Kitap getirme hatası (ISBN ile): " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    //kitap güncelleme
    public boolean updateBook(int bookId, String isbn, String name, int authorId, int categoryId, int stock) {
        String sql = "UPDATE books SET isbn = ?, name = ?, author_id = ?, category_id = ?, stock = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            pstmt.setString(2, name);
            pstmt.setInt(3, authorId);
            pstmt.setInt(4, categoryId);
            pstmt.setInt(5, stock);
            pstmt.setInt(6, bookId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Güncelleme Hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}