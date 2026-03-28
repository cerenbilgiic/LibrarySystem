import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static final String URL  = "jdbc:mysql://localhost:3306/LibrarySystem";
    public static final String USER  = "root";
    public static final String PASSWORD  = "Ceren2004";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
