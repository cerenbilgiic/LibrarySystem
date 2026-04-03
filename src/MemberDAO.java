import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    private Connection connection;

    public MemberDAO() {
        this.connection = connection;
    }

    // ÜYE EKLE
    public boolean addMember(member member) {

        String sql = "INSERT INTO members " +
                "(first_name, last_name, email, role, max_allowed_books) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, member.getId());
            ps.setString(2, member.getFirst_name());
            ps.setString(3, member.getLast_name());
            ps.setString(4, member.getEmail());
            ps.setString(6, member.getRole());
            ps.setInt(9, member.getMaxAllowedbooks());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Member ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    // ID ile ÜYE BUL
    public member getMemberById(int id) {

        String sql = "SELECT * FROM members WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                return new member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getDate("created_date").toLocalDate(),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getInt("max_allowed_books")
                );
            }

        } catch (SQLException e) {
            System.out.println("Member bulma hatası: " + e.getMessage());
        }

        return null;
    }

    // TÜM ÜYELER
    public List<member> getAllMembers() {

        List<member> list = new ArrayList<>();
        String sql = "SELECT * FROM members";

        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {

                list.add(new member(
                        rs.getInt("id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getDate("created_date").toLocalDate(),
                        rs.getDate("membership_date").toLocalDate(),
                        rs.getInt("max_allowed_books")
                ));
            }

        } catch (SQLException e) {
            System.out.println("Listeleme hatası: " + e.getMessage());
        }

        return list;
    }

    // ÜYE GÜNCELLE
    public boolean updateMember(member member) {

        String sql = "UPDATE members SET first_name=?, last_name=?, email=?, password=?, role=? " +
                "membership_date=?, max_allowed_books=? WHERE id=?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, member.getFirst_name());
            ps.setString(2, member.getLast_name());
            ps.setString(3, member.getEmail());
            ps.setString(4, member.getPassword());
            ps.setDate(6, Date.valueOf(member.getMembershipDate()));
            ps.setInt(7, member.getMaxAllowedbooks());
            ps.setInt(8, member.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    // ÜYE SİL
    public boolean deleteMember(int id) {

        String sql = "DELETE FROM members WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Silme hatası: " + e.getMessage());
            return false;
        }
    }

}