import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField txtEmployeeTC;
    private JPasswordField txtPassword; // Güvenlik için şifre alanı
    private JButton btnLogin;

    public Login() {
        setTitle("Kütüphane Sistemi - Giriş");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında açılır
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel(" TC Kimlik No:"));
        txtEmployeeTC = new JTextField();
        add(txtEmployeeTC);

        add(new JLabel(" Şifre:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Giriş Yap");
        add(new JLabel("")); // Boşluk için
        add(btnLogin);
        btnLogin.addActionListener(e -> {
            String tc = txtEmployeeTC.getText();
            String password = new String(txtPassword.getPassword());

            if (tc.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun!");
                return;
            }

            LoginDAO dao = new LoginDAO();

            if (dao.checkLogin(tc, password)) {
                JOptionPane.showMessageDialog(this, "Giriş Başarılı!");

                //veri tabanından kişinin adı ve soyadı alınır, giriş ekranına yansıtılmak için.
                String adSoyad = dao.getUserFullName(tc);

                this.dispose();
                new LibrarySystemUI(adSoyad).setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "Hatalı TC Kimlik Numarası veya Şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });


        setVisible(true);
    }
}