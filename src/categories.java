public class categories {
    private int id;
    private String category_name;
    private String description;

    public categories(int id , String category_name , String description){
        this.id = id;
        this.category_name = category_name;
        this.description = description;
    }

    public int getId(){return id ;}
    public void setId(int id){this.id = id ;}

    public String getCategory_name(){return category_name ;}
    public void setCategory_name(String category_name){this.category_name = category_name ;}

    public String getDescription(){return description ;}
    public void setDescription(String description){this.description = description ;}
}
