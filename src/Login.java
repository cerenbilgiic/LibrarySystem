import javax.swing.*;
import java.awt.*;

public class Login extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword; // Güvenlik için şifre alanı
    private JButton btnLogin;

    public Login() {
        setTitle("Kütüphane Sistemi - Giriş");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ekranın ortasında açılır
        setLayout(new GridLayout(3, 2, 10, 10));

        // Bileşenleri ekleyelim
        add(new JLabel(" Kullanıcı Adı:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel(" Şifre:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Giriş Yap");
        add(new JLabel("")); // Boşluk için
        add(btnLogin);
        btnLogin.addActionListener(e -> {
            String email = txtUsername.getText();
            // (Güvenlik kısmında ufak bir düzenleme: getPassword() kullanımı string'e doğru çevrilmelidir)
            String password = new String(txtPassword.getPassword());

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Tüm alanları doldurun!");
                return;
            }

            LoginDAO dao = new LoginDAO();

            if (dao.checkLogin(email, password)) {
                JOptionPane.showMessageDialog(this, "Giriş Başarılı!");

                // 1- Veritabanına gidip kişinin adını ve soyadını soruyoruz
                String adSoyad = dao.getUserFullName(email);

                this.dispose();

                // 2- Boş kurucu yerine ANA EKRANA ismini parametre olarak gönderdiğimiz yeni kurucuyu çalıştırıyoruz:
                new LibrarySystemUI(adSoyad).setVisible(true);

            } else {
                JOptionPane.showMessageDialog(this, "Hatalı Kullanıcı Adı veya Şifre!", "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });


        setVisible(true);
    }
}