import java.sql.*;
import java.time.LocalDate;

public class LoanDAO {
    private String url = "jdbc:mysql://localhost:3306/LibrarySystem";
    private String user = "root";
    private String pass = "Ceren2004";
    private Connection LoanConn;

    public LoanDAO() {
        this.LoanConn = LoanConn;
    }

    public boolean IssueLoan(int member_id, int book_id) {
        Connection conn = null;
        try {
            LoanConn = DriverManager.getConnection(url, user, pass);
            conn.setAutoCommit(false);

            String stockCheck = "SELECT stock FROM books WHERE id = ?";
            PreparedStatement psStock = conn.prepareStatement(stockCheck);
            psStock.setInt(1, book_id);
            ResultSet rs = psStock.executeQuery();

            if (rs.next() && rs.getInt("stock") > 0) {
                String insertLoan = "INSERT INTO loans (user_id, book_id, loan_date, status) VALUES (?, ?, ?, 'Active')";
                PreparedStatement psLoan = conn.prepareStatement(insertLoan);
                psLoan.setInt(1, member_id);
                psLoan.setInt(2, book_id);
                psLoan.setDate(3, java.sql.Date.valueOf(LocalDate.now()));
                psLoan.executeUpdate();

                // 3. ADIM: Kitap stok sayısını 1 azalt
                String updateStock = "UPDATE books SET stock = stock - 1 WHERE id = ?";
                PreparedStatement psUpdate = conn.prepareStatement(updateStock);
                psUpdate.setInt(1, book_id);
                psUpdate.executeUpdate();

                LoanConn.commit();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            if (conn != null) try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            e.printStackTrace();
            return false;
        }
    }

    public double calculateAndGetFine(int loanId) {
        double fineAmount = 0.0;
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            // 1. Ödünç alma ve iade tarihlerini getir
            String sql = "SELECT loan_date, return_date FROM loans WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                LocalDate loanDate = rs.getDate("loan_date").toLocalDate();
                LocalDate today = LocalDate.now();

                // Örnek kural: 15 günü geçtiyse günlük 5 TL ceza
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(loanDate, today);
                if (daysBetween > 15) {
                    fineAmount = (daysBetween - 15) * 5.0;

                    // 2. Fines tablosuna bu cezayı kaydet (Eğer daha önce eklenmemişse)
                    String fineSql = "INSERT INTO fines (loan_id, amount, is_paid) VALUES (?, ?, false) " +
                            "ON DUPLICATE KEY UPDATE amount = ?";
                    PreparedStatement psFine = conn.prepareStatement(fineSql);
                    psFine.setInt(1, loanId);
                    psFine.setDouble(2, fineAmount);
                    psFine.setDouble(3, fineAmount);
                    psFine.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fineAmount;
    }
}