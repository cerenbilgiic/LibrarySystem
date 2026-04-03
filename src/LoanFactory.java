import java.time.LocalDate;

public class LoanFactory {

    // Bu metot, karmaşık Loan nesnesini tek bir yerden standartlara uygun üretmemizi sağlar.
    public static loans createNewLoan(int userId, int bookId) {
        LocalDate today = LocalDate.now();
        LocalDate dueDate = today.plusDays(15); // Standart 15 gün ödünç süresi

        // Yeni bir Loan (Ödünç) nesnesi oluşturulup geri döndürülüyor
        return new loans(today, dueDate, "Aktif", userId, bookId);
    }
}
