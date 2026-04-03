public class UsersDAO {
    public boolean addMember(String name, String surname) {
        String sql = "INSERT INTO users (first_name, last_name) VALUES (?, ?)";
        return true;
    }
}