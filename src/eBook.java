//bu class dijital kitap erişimi içindir.

import java.time.LocalDate;

public class eBook extends books {
    //MB cinsinden dosya boyutunu belirtir.
    private double fileSize;
    //PDF,DOCX vb. cinsinden format belirtir.
    private String format;

    public eBook(int id , String isbn , String name , LocalDate publish_year , int author_id , int category_id ,double fileSize, String format) {
        // Üst sınıf olan Book'un constructor'ına verileri gönderir
        super(id, isbn,name,publish_year,author_id,category_id);
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
