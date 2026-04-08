import java.time.LocalDate;

public class LoanFactory {

    //  nesnenin tek bir yerden standartlara uygun üretilmesini sağlar.
    public static loans createNewLoan(int userId, int bookId) {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(15); // 15 gün ödünç süresi

        // Yeni bir Ödünç nesnesi oluşturulup geri döndürülüyor
        return new loans(today, dueDate, "Ödünç Verildi", userId, bookId);
    }
}
