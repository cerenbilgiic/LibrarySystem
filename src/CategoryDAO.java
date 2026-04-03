import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    // 1. Veritabanına Yeni Kategori Ekleme İşlemi (CREATE)
    public boolean addCategory(String categoryName, String description) {
        // SQL Ekleme Sorgumuz: categories tablosuna ad ve açıklama ekler
        String sql = "INSERT INTO categories (category_name, description) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);
            pstmt.setString(2, description);

            int result = pstmt.executeUpdate();
            return result > 0; // Başarılıysa true döner

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Sistemdeki Tüm Kategorileri Getirme İşlemi (READ)
    public List<categories> getAllCategories() {
        List<categories> categoryList = new ArrayList<>();
        String sql = "SELECT * FROM categories";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("category_name");
                String desc = rs.getString("description");

                'categories' sınıfından nesne üretiyoruz
                categories newCategory = new categories(id, name, desc);
                categoryList.add(newCategory);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return categoryList;
    }
}
