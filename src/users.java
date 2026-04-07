import java.time.LocalDate;

public abstract class users {
    protected int id;
    protected String first_name;
    protected String last_name;
    protected String username;
    protected String password;
    protected String role;
    protected LocalDate created_at;

    public users(int id, String first_name, String last_name, String username, String password, String role, LocalDate created_at) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.username = username;
        this.password = password;
        this.role = role;
        this.created_at = created_at;
    }
    public abstract boolean login();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDate created_date) {
        this.created_at = created_date;
    }

    public String getFullName() {
        return first_name + " " + last_name;
    }


}
