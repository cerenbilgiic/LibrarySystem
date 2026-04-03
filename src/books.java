import java.time.LocalDate;

public class books {
    private int id;
    private String isbn;
    private String bookName; // Burada isim nasılsa getter da öyle olmalı
    private LocalDate purchaseDate;
    private int authorId;
    private int categoryId;

    public books(int id, String isbn, String bookName, LocalDate purchaseDate, int authorId, int categoryId) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = bookName;
        this.purchaseDate = purchaseDate;
        this.authorId = authorId;
        this.categoryId = categoryId;
    }

    public int getId() { return id; }
    public String getIsbn() { return isbn; }
    public String getBook_name() { return bookName; } // UI'da b.getBook_name() dediğin için burayı böyle yapabilirsin
    public LocalDate getPurchase_date() { return purchaseDate; }
    public int getAuthor_id() { return authorId; }
    public int getCategory_id() { return categoryId; }
}
