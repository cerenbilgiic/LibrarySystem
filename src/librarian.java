import java.time.LocalDate;

public class  librarian extends users{
    private int employee_id;

    public librarian(int id , String first_name , String last_name , String email , String password , String role, LocalDate created_date ,int employee_id){

        super(id,first_name,last_name,email,password,role,created_date);
        this.employee_id=employee_id;
    }

    public int getEmployee_id(){return employee_id ;}
    public void setEmployee_id(int employee_id){this.employee_id=employee_id ;}

    @Override
    public boolean login() {
        System.out.println("Personel Numarası: " + employee_id + " ile yönetim paneline giriş yapıldı.");
        return true;
    }

    public void issueLoan() {
        System.out.println("Ödünç verme işlemi başlatıldı.");
    }

    public void registerMember() {
        System.out.println("Yeni üye kaydı yapılıyor...");
    }
}
