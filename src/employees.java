import java.time.LocalDate;

public class employees extends users {
    public employees(int id, String first_name , String last_name , String username , String password , String role , LocalDate created_at ){
        //user classının özelliklerinin girişi yapılıyor.
        super(id , first_name , last_name , username , password, role , created_at );
        this.role = "KÜTÜPHANE ÇALIŞANI";
    }

    @Override
    public boolean login() {
        System.out.println(getFullName() + " isimli çalışan başarıyla giriş yaptı.");
        return true;
    }

}
