import java.time.LocalDate;

public class books {
    private int id;
    private String isbn;
    private String name;
    private LocalDate publish_year;
    private int author_id;
    private int category_id;

    public books(int id , String isbn , String name , LocalDate publish_year , int author_id , int category_id){
        this.id = id;
        this.isbn = isbn;
        this.name = name;
        this.publish_year = publish_year;
        this.author_id =author_id;
        this.category_id = category_id;
    }
    public int getId(){return id ;}
    public void setId(int id){this.id = id ;}

    public String getIsbn(){return isbn ;}
    public void setIsbn(String isbn){this.isbn = isbn ;}

    public String getName(){return name ;}
    public void setName(String name){this.name = name;}

    public LocalDate getPublish_year(){return publish_year ;}
    public void setPublish_year(LocalDate publish_year){this.publish_year = publish_year ;}

    public int getAuthor_id(){return author_id ;}
    public void setAuthor_id(int author_id){this.author_id = author_id ;}

    public int getCategory_id(){return category_id ;}
    public void setCategory_id(int category_id){this.category_id = category_id ;}
}
