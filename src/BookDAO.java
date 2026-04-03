import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class BookDAO {

    // 1. Yeni Kitap Ekleme (Senin MySQL sütun isimlerine göre: name, publish_year)
    public void addBook(books book) {
        // BURASI KRİTİK: Sütun isimlerini senin Workbench'teki (name ve publish_year) yaptık
        String sql = "INSERT INTO books (isbn, name, publish_year, author_id, category_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getBook_name()); // Java nesnesinden adı alıyoruz
            pstmt.setObject(3, book.getPurchase_date()); // Java nesnesinden tarihi alıyoruz
            pstmt.setInt(4, book.getAuthor_id());
            pstmt.setInt(5, book.getCategory_id());

            pstmt.executeUpdate();
            System.out.println("Kitap veritabanına başarıyla eklendi!");

        } catch (Exception e) {
            System.out.println("Kitap eklenirken hata oluştu!");
            e.printStackTrace();
        }
    }

    // 2. Kitap Arama (Yine senin sütun isimlerine göre: name, publish_year)
    public books getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                // MySQL'deki 'name' sütunundan veriyi çekip Java 'books' nesnesine koyuyoruz
                return new books(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("name"), // Workbench'teki sütun adı
                        rs.getObject("publish_year", LocalDate.class), // Workbench'teki sütun adı
                        rs.getInt("author_id"),
                        rs.getInt("category_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
