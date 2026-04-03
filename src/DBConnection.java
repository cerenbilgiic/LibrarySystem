import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/LibrarySystem";
    private static final String USER = "root";
    private static final String PASSWORD = "Ceren2004";

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL Driver bulunamadı!");
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }


}