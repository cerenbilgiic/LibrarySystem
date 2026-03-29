import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class LoanDAO {
    private Connection LoanConn;

    public LoanDAO() {
        this.LoanConn = LoanConn;
    }

    public boolean IssueLoan(int member_id, int book_id) {
        String insertLoan = "INSERT INTO loans (member_id , book_id  , loan_date , return_date)  VALUES (?,?,?,?)";
        String updateStock = "UPDATE books SET stock = stock-1 WHERE id = ?";

        try {
            //Alınan kitap kayıda eklendi.

            LoanConn.setAutoCommit(false);
            PreparedStatement psLoan = LoanConn.prepareStatement(insertLoan);
            psLoan.setInt(1,member_id);
            psLoan.setInt(2,book_id);
            psLoan.setDate(3, Date.valueOf(LocalDate.now()));
            psLoan.setDate(4,Date.valueOf(LocalDate.now().plusDays(15)));
            psLoan.executeUpdate();

            //Alınan kitap stoktan düşürüldü.

            PreparedStatement psStock = LoanConn.prepareStatement(updateStock);
            psStock.setInt(1,book_id);
            psStock.executeUpdate();

            LoanConn.commit();
            System.out.println("Kitap ödünç alma işlemi başarılı.");
            return true;
        }

        catch (SQLException e){
            try {LoanConn.rollback();} //Bir hata ile karşılaşılması durumunda verileri korumak için işlemleri geri sarark iptal eder.
            catch (SQLException ex){ex.printStackTrace();}
            System.out.println("Ödünç verme işlemi başarısız." + e.getMessage());
            return false;
        }
    }
}