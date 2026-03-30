import java.time.LocalDate;

public class loans { // Sınıf adını Java standartlarına uygun olarak büyük harfle başlattık
    private int id;
    private LocalDate loanDate; // Alt çizgi yerine camelCase kullandık
    private LocalDate dueDate;
    private LocalDate returnDate;
    private String status;
    private int userId;
    private int bookId;

    // 1. ORİJİNAL CONSTRUCTOR (Veritabanından tam veri çekerken kullanılacak)
    public loans(int id, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate, String status, int userId, int bookId) {
        this.id = id;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.status = status;
        this.userId = userId;
        this.bookId = bookId;
    }

    // 2. YENİ EKLENEN CONSTRUCTOR (LoanFactory'nin sorunsuz çalışması için gereken 5 parametreli metot)
    public loans(LocalDate loanDate, LocalDate dueDate, String status, int userId, int bookId) {
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.status = status;
        this.userId = userId;
        this.bookId = bookId;
        // id'yi veritabanı otomatik verecek, returnDate (iade tarihi) ise henüz iade edilmediği için boş.
    }

    // --- GETTER VE SETTER METOTLARI ---

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDate getLoanDate() { return loanDate; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getBookId() { return bookId; }
    public void setBookId(int bookId) { this.bookId = bookId; }
}
