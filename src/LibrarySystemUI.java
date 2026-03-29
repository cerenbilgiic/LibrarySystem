import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.GridLayout;

public class LibrarySystemUI extends JFrame {
    private JTextField txtMemberId , txtBookId;
    private JButton btnLoan , btnExit;
    private LoanDAO loanDAO; //Veritabanı ile iletişim.

    public LibrarySystemUI(){
        setTitle("Kütüphane Otomasyon Sistemi");
        setSize(500,500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Çarpı işaretine basınca kapatmayı sağlar.
        setLocationRelativeTo(null); //Uygulamanın tam ortaya açılmasını sağlar.

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new JLabel("Üye Numarası: "));
        txtMemberId = new JTextField();
        add(txtMemberId);
        add(new JLabel("Kitap Numarası: "));
        txtBookId = new JTextField();
        add(txtBookId);

         loanDAO = new LoanDAO();

         btnLoan = new JButton("Kitabı ödünç ver");
         btnLoan.addActionListener(new ActionListener() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 int memberId = Integer.parseInt(txtMemberId.getText());
                 int bookId = Integer.parseInt(txtBookId.getText());

                 boolean sonuc = loanDAO.IssueLoan(memberId,bookId);

                 if (sonuc){
                     JOptionPane.showMessageDialog(null , "Kitap teslimi başarılı.");
                 }

                 else {
                     JOptionPane.showMessageDialog(null,"Kitap teslim başarısız.");
                 }
             }
         });
         add(btnLoan);

         btnExit = new JButton("ÇIKIŞ");
        btnExit.addActionListener(e -> System.exit(0)); //Lambda ile kısa çıkış sağlar.
        add(btnExit);

        setVisible(true);
    }


}
