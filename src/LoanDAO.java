import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoanDAO {

    public LoanDAO() {
    }

    // 1. Ödünç Verme İşlemi
    public boolean issueLoan(int userId, int bookId) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. ADIM: Kitabın stoğunu kontrol et
            String stockCheck = "SELECT stock FROM books WHERE id = ?";
            PreparedStatement psStock = conn.prepareStatement(stockCheck);
            psStock.setInt(1, bookId);
            ResultSet rs = psStock.executeQuery();

            if (rs.next() && rs.getInt("stock") > 0) {
                // 2. ADIM: Loans tablosuna kaydet
                String insertLoan = "INSERT INTO loans (user_id, book_id, loan_date, due_date, status) VALUES (?, ?, ?, ?, 'Aktif')";
                PreparedStatement psLoan = conn.prepareStatement(insertLoan);

                // --- FACTORY PATTERN BURADA DEVREYE GİRİYOR! ---
                // Tarihleri ve temel bilgileri kendi yazdığımız Factory sınıfından çekiyoruz
                loans newLoan = LoanFactory.createNewLoan(userId, bookId);

                psLoan.setInt(1, newLoan.getUserId());
                psLoan.setInt(2, newLoan.getBookId());
                psLoan.setDate(3, java.sql.Date.valueOf(newLoan.getLoanDate()));
                psLoan.setDate(4, java.sql.Date.valueOf(newLoan.getDueDate()));
                psLoan.executeUpdate();

                // 3. ADIM: Kitap stok sayısını 1 azalt
                String updateStock = "UPDATE books SET stock = stock - 1 WHERE id = ?";
                PreparedStatement psUpdate = conn.prepareStatement(updateStock);
                psUpdate.setInt(1, bookId);
                psUpdate.executeUpdate();

                conn.commit();
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }

    // 2. Ceza Hesaplama ve Fines Tablosuna Ekleme İşlemi
    public double calculateAndGetFine(int loanId) {
        double fineAmount = 0.0;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT loan_date, return_date FROM loans WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, loanId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                java.sql.Date dbLoanDate = rs.getDate("loan_date");
                if (dbLoanDate != null) {
                    LocalDate loanDate = dbLoanDate.toLocalDate();
                    LocalDate today = LocalDate.now();

                    long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(loanDate, today);

                    if (daysBetween > 15) {
                        fineAmount = (daysBetween - 15) * 5.0;

                        String fineSql = "INSERT INTO fines (loan_id, amount, is_paid) VALUES (?, ?, false) " +
                                "ON DUPLICATE KEY UPDATE amount = ?";
                        PreparedStatement psFine = conn.prepareStatement(fineSql);
                        psFine.setInt(1, loanId);
                        psFine.setDouble(2, fineAmount);
                        psFine.setDouble(3, fineAmount);
                        psFine.executeUpdate();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fineAmount;
    }

    // 3. İade Alma İşlemi
    public double returnBook(int userId, int bookId) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. ADIM: Aktif ödünç kaydını bul
            String findLoan = "SELECT id FROM loans WHERE user_id = ? AND book_id = ? AND status = 'Aktif'";
            PreparedStatement psFind = conn.prepareStatement(findLoan);
            psFind.setInt(1, userId);
            psFind.setInt(2, bookId);
            ResultSet rsFind = psFind.executeQuery();

            if (rsFind.next()) {
                int loanId = rsFind.getInt("id");

                // 2. ADIM: İade tarihini ve durumu güncelle
                String updateLoan = "UPDATE loans SET return_date = ?, status = 'İade Edildi' WHERE id = ?";
                PreparedStatement psUpdateLoan = conn.prepareStatement(updateLoan);
                psUpdateLoan.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                psUpdateLoan.setInt(2, loanId);
                psUpdateLoan.executeUpdate();

                // 3. ADIM: Kitap stok sayısını 1 artır
                String updateStock = "UPDATE books SET stock = stock + 1 WHERE id = ?";
                PreparedStatement psUpdateStock = conn.prepareStatement(updateStock);
                psUpdateStock.setInt(1, bookId);
                psUpdateStock.executeUpdate();

                conn.commit();

                // 4. ADIM: Ceza hesapla
                // Not: conn.commit() yapıldığı için farklı connection açan metodu çağırmak güvenlidir
                double fine = calculateAndGetFine(loanId);

                return fine;
            } else {
                return -1.0; // Aktif ödünç kaydı bulunamadı
            }
        } catch (Exception e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return -1.0;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }
}
