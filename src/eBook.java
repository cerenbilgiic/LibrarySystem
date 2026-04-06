//bu class dijital kitap erişimi içindir.

import java.time.LocalDate;

public class eBook extends books {
    //MB cinsinden dosya boyutunu belirtir.
    private double fileSize;
    //PDF,DOCX vb. cinsinden format belirtir.
    private String format;

    public eBook(int id , String isbn , String name , LocalDate publish_year , int author_id ,int category_id , String author_name, String author_surname, String category_name ,double fileSize, String format,int stock) {
        // Üst sınıf olan Book'un constructor'ına verileri gönderir
        super(id,  isbn,name,publish_year,author_id,category_id, author_name, author_surname, stock);
        this.fileSize = fileSize;
        this.format = format;
    }

    public void download() {
        System.out.println(getBook_name() + format + " formatında indiriliyor...");
    }

    public double getFileSize() { return fileSize; }
    public void setFileSize(double fileSize) { this.fileSize = fileSize; }

    public String getFormat() { return format; }
    public void setFormat(String format) { this.format = format; }
}
