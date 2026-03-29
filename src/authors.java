public class authors {
    private int id;
    private String author_name;
    private String author_surname;
    private String biography;

    //Bu kod bloğu constructordur. Bu sayede bir yazarın her özelliğini tek tek tanımalamak gerekmez.

    public authors(int id, String author_name, String author_surname, String biography){
        this.id = id;
        this.author_name = author_name;
        this.author_surname = author_surname;
        this.biography = biography;

    }
//verileri doğrudan dışarıya açmak yerine kontrollü erişmeyi sağlayan kod bloğu.

    public int getId() {return id ;}
    public void setId(int id) {this.id = id ;}

    public String getAuthor_name(){return author_name ;}
    public void setAuthor_name(String author_name){this.author_name = author_name ;}

    public String getAuthor_surname(){return author_surname ;}
    public void setAuthor_surname(String author_surname){this.author_surname = author_surname ;}

    public String getBiography(){return biography ;}
    public void setBiography(String biography){this.biography = biography ;}

}

