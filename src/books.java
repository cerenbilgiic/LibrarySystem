import java.time.LocalDate;

public class books {
    private int id;
    private String isbn;
    private String bookName; // Burada isim nasılsa getter da öyle olmalı
    private LocalDate purchaseDate;
    private int authorId;
    private int categoryId;
    private String authorName;
    private String authorSurname;
    private String categoryName;

    public books(int id, String isbn, String bookName, LocalDate purchaseDate,int authorId, int categoryId,String authorName, String authorSurname ,String categoryName) {
        this.id = id;
        this.isbn = isbn;
        this.bookName = bookName;
        this.purchaseDate = purchaseDate;
        this.authorId=authorId;
        this.categoryId=categoryId;
        this.authorName = authorName;
        this.authorSurname=authorSurname;
        this.categoryName = categoryName;
    }

    public int getId() { return id; }
    public String getIsbn() { return isbn; }
    public String getBook_name() { return bookName; } // UI'da b.getBook_name() dediğin için burayı böyle yapabilirsin
    public LocalDate getPurchase_date() { return purchaseDate; }
    public int getAuthorId(){return authorId ;}
    public int getCategoryId(){return categoryId ;}
    public String getAuthorName(){return authorName; }
    public String getAuthorSurname(){return authorSurname; }
    public String getCategory_name() { return categoryName; }
}
