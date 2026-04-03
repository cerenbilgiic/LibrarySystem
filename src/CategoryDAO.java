import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // 1. Yeni Kategori Ekleme
    public boolean addCategory(String categoryName, String description) {
        String sql = "INSERT INTO categories (category_name, description) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, categoryName);
            pstmt.setString(2, description);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Kategori Güncelleme (Hata veren eksik metot buydu!)
    public boolean updateCategory(int id, String name, String description) {
        String sql = "UPDATE categories SET category_name = ?, description = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, id);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. ID ile Tek Bir Kategori Getirme (Diğer hata veren eksik metot buydu!)
    public categories getCategoryById(int id) {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // categories sınıfına uygun nesne üretiliyor
                return new categories(
                        rs.getInt("id"), 
                        rs.getString("category_name"), 
                        rs.getString("description")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // 4. Tüm Kategorileri Listeleme
    public List<categories> getAllCategories() {
        List<categories> list = new ArrayList<>();
        String sql = "SELECT * FROM categories";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(new categories(rs.getInt("id"), rs.getString("category_name"), rs.getString("description")));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}
