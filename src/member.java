import java.time.LocalDate;

public class member extends users{
    //bu özellikler üyeye özel eklenenlerdir.
    private int maxAllowedbooks=5;// Alabileceği maksimum kitap sayısı

    public member(int id, String first_name , String last_name , String email , String password , String role , LocalDate created_at , int maxAllowedbooks){
       //user classının özelliklerinin girişi yapılıyor.
        super(id , first_name , last_name , email , password, role , created_at );
        role= "Kütüphane Üyesi";

        this.maxAllowedbooks=5;
    }

    public int getMaxAllowedbooks(){return maxAllowedbooks ;}
    public void setMaxAllowedbooks(int maxAllowedbooks){this.maxAllowedbooks=maxAllowedbooks ;}

    //Override ile member alt sınıfı users üst sınıfından miras aldığı özellikleri kendine göre yapılandırır.
    @Override
    public boolean login() {
        System.out.println(getFullName() + " isimli üye başarıyla giriş yaptı.");
        return true;
    }
}
