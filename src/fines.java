public class fines {
    private int id;
    private double amount;
    private boolean is_paid;
    private int loan_id;
    private int user_id;

    public fines(int id , double amount , boolean is_paid , int loan_id , int user_id){
        this.id=id;
        this.amount=amount;
        this.is_paid=is_paid;
        this.loan_id=loan_id;
        this.user_id=user_id;
    }

    public int getId(){return id ;}
    public void setId(int id){this.id=id ;}

    public double getAmount(){return amount ;}
    public void setAmount(double amount){this.amount=amount ;}

    public boolean getIs_paid(){return is_paid ;}
    public void setIs_paid(boolean is_paid){this.is_paid=is_paid ;}

    public int getLoan_id(){return loan_id ;}
    public void setLoan_id(int loan_id){this.loan_id = loan_id ;}

    public int getUser_id(){return user_id ;}
    public void setUser_id(int user_id){this.user_id=user_id ;}

}
