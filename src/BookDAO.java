import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    private Connection connection;

    public BookDAO() throws Exception {
        // Singleton bağlantımızı kullanıyoruz
        this.connection = DBConnection.getConnection();
    }

    // Veritabanına yeni kitap ekleme
    public void addBook(books book) {
        String sql = "INSERT INTO books (isbn, name, publish_year, author_id, category_id) VALUES (?, ?, ?, ?, ?)";
        //SQL kaçağını önlemek adına kullanılır.

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, book.getIsbn());
            statement.setString(2, book.getName());
            statement.setDate(3, Date.valueOf(book.getPublish_year()));
            statement.setInt(4, book.getAuthor_id());
            statement.setInt(5, book.getCategory_id());
            //insert,update ve delete için kullanılır.
            statement.executeUpdate();
            System.out.println("Kitap başarıyla eklendi!");
        }
        //Hata var ise hatayı çözmek yerine direkt gösterir.
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
