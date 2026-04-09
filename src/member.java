import java.time.LocalDate;

public class member extends users {

    private int maxAllowedbooks = 5;

    public member(int id, String first_name , String last_name ,
                  String tc , String role ,
                  LocalDate created_at , int maxAllowedbooks){

        super(id , first_name , last_name , tc ,null , role , created_at);

        this.role = "Kütüphane Üyesi";
        this.maxAllowedbooks = maxAllowedbooks;
    }

    public int getMaxAllowedbooks() {
        return maxAllowedbooks;
    }

    @Override
    public boolean login() {
        System.out.println(getFullName() + " isimli üye başarıyla giriş yaptı.");
        return true;
    }
}