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

    //kitap ekleme
    public boolean addBook(books book) {
        String sql = "INSERT INTO books (isbn, name, publish_year,author_id, category_id,stock,publishier) VALUES ( ?, ?, ?, ?,?,?,?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getBook_name()); // Java nesnesinden adı alıyoruz
            pstmt.setObject(3, book.getPurchase_date()); // Java nesnesinden tarihi alıyoruz
            pstmt.setInt(4, book.getAuthorId());
            pstmt.setInt(5, book.getCategoryId());
            pstmt.setInt(6,book.getStock());
            pstmt.setString(7,book.getPublishier());

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Kitap eklenirken hata oluştu!");
            e.printStackTrace();
        }
        return false;
    }

    //kitap silme
    public boolean deleteBook(int bookId) {
        Connection conn = null;

        try {
            conn = DBConnection.getConnection();

            // Aktif ödünç kaydı kontrolü
            String checkSql = "SELECT COUNT(*) FROM loans WHERE book_id = ? AND status != 'iade edildi'";
            PreparedStatement psCheck = conn.prepareStatement(checkSql);
            psCheck.setInt(1, bookId);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Kitap hala ödünçte, silinemez!");
                return false;
            }

            // cezayı siler
            String deleteFines = "DELETE FROM fines WHERE loan_id IN (SELECT id FROM loans WHERE book_id = ?)";
            PreparedStatement psFines = conn.prepareStatement(deleteFines);
            psFines.setInt(1, bookId);
            psFines.executeUpdate();

            // ödünç kaydını siler
            String deleteLoans = "DELETE FROM loans WHERE book_id = ?";
            PreparedStatement psLoans = conn.prepareStatement(deleteLoans);
            psLoans.setInt(1, bookId);
            psLoans.executeUpdate();

            //kitabı siler
            String deleteBook = "DELETE FROM books WHERE id = ?";
            PreparedStatement psBook = conn.prepareStatement(deleteBook);
            psBook.setInt(1, bookId);

            return psBook.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    //toplam kayıtlı kitap sayısı
    public int getTotalBookCount() {
        String sql = "SELECT COUNT(*) FROM books";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return rs.getInt(1);
        } catch (SQLException e) { e.printStackTrace(); }
        return 0;
    }
    //kitap arama.
    public books getBookByIsbn(String isbn) {
        String sql = "SELECT b.id, b.isbn, b.name, b.publish_year, " +
                "b.author_id, b.category_id, b.stock, b.publishier, " +
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
                        rs.getInt("stock"),
                        rs.getString("publishier")
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
    public boolean updateBook(int bookId, String isbn, String name, int authorId, int categoryId, int stock, String publishier) {
        String sql = "UPDATE books SET isbn = ?, name = ?, author_id = ?, category_id = ?, stock = ? , publishier= ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            pstmt.setString(2, name);
            pstmt.setInt(3, authorId);
            pstmt.setInt(4, categoryId);
            pstmt.setInt(5, stock);
            pstmt.setString(6, publishier);
            pstmt.setInt(7, bookId);

            return pstmt.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Güncelleme Hatası: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}