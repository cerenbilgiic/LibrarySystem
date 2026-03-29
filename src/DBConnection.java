import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    private static final String URL  = "jdbc:mysql://localhost:3306/LibrarySystem";
    private static final String USER  = "root";
    private static final String PASSWORD  = "Ceren2004";

    private DBConnection() {}

    public static Connection getConnection() throws Exception {

        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}