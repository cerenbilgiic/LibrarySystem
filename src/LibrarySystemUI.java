import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.awt.event.ActionListener;

public class LibrarySystemUI extends JFrame {
    private JTextField txtMemberId, txtBookId , txtAuthorName ,txtAuthorSurname;
    private JTextField txtIsbn, txtBookName;
    private JComboBox<String> selectCategory;
    private JButton btnLoan, btnExit;
    private LoanDAO loanDAO;

    public LibrarySystemUI() {
        setTitle("Kütüphane Otomasyon Sistemi");
        setSize(950, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane tabs = new JTabbedPane();

        setLayout(new GridLayout(5, 2, 15, 15));
        loanDAO = new LoanDAO();

        // Henüz kodlanmamış butonlar için genel uyarı mesajı
        ActionListener comingSoon = e -> {
            JButton source = (JButton) e.getSource();
            JOptionPane.showMessageDialog(this,
                    source.getText() + " modülü Faz-2 kapsamında geliştirilecektir.",
                    "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        };

        //GİRİŞ İŞLEMLERİ
        JTextField txtUsername = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JButton btnLogin = new JButton("Giriş Yap");

        btnLogin.addActionListener(e -> {
            // 1. Kullanıcıdan verileri al (Hata: newString birleşik olmamalı)
            String email = txtUsername.getText();
            String pass = new String(txtPassword.getPassword());

            // 2. Boş alan kontrolü (Profesyonel bir dokunuş)
            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen tüm alanları doldurun!", "Uyarı", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 3. Veritabanı kontrolü (DAO Katmanı)
            LoginDAO loginDao = new LoginDAO();

            // checkLogin metodunun SQLException fırlatma ihtimaline karşı try-catch gerekebilir
            try {
                if (loginDao.checkLogin(email, pass)) {
                    JOptionPane.showMessageDialog(this, "Hoş geldiniz, " + email);

                    this.dispose(); // Giriş penceresini kapat

                    // Ana ekranı başlat (Sınıf isminin LibrarySystemUI olduğundan emin ol)
                    new LibrarySystemUI().setVisible(true);

                } else {
                    JOptionPane.showMessageDialog(this, "Hatalı Giriş Bilgileri!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Veritabanı bağlantı hatası: " + ex.getMessage());
            }
        });

        // ÜYE İŞLEMLERİ
        JPanel pnlMember = new JPanel(new GridLayout(5, 2, 5, 5));

        pnlMember.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Üye İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        pnlMember.add(new JLabel(" Adı:"));
        JTextField txtMemName = new JTextField();
        pnlMember.add(txtMemName);

        pnlMember.add(new JLabel(" Soyadı:"));
        JTextField txtMemSurname = new JTextField();
        pnlMember.add(txtMemSurname);

        pnlMember.add(new JLabel(" E-Posta:"));
        JTextField txtMemEmail = new JTextField();
        pnlMember.add(txtMemEmail);


        pnlMember.add(new JLabel(" Şifre:"));
        JTextField txtMemPass = new JTextField();
        pnlMember.add(txtMemPass);

        JButton btnAddMember = new JButton("Üye Ekle");
        pnlMember.add(btnAddMember);

        JButton btnSearchMember = new JButton("Üye Ara");
        btnSearchMember.addActionListener(comingSoon);

        pnlMember.add(btnSearchMember);
        add(pnlMember);

        //Üye ekleme işlemi.

        btnAddMember.addActionListener(e -> {
            try {
                member m = new member(
                        0,
                        txtMemName.getText(),
                        txtMemSurname.getText(),
                        txtMemEmail.getText(),
                        txtMemPass.getText(),
                        "member",
                        LocalDate.now(),
                        LocalDate.now(),
                        5
                );

                JOptionPane.showMessageDialog(this, "Üye başarıyla eklendi!");

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Hata oluştu!");
            }
        });

        // KİTAP İŞLEMLERİ

        JPanel pnlBook = new JPanel(new GridLayout(4, 4, 5, 5));
        pnlBook.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Kitap İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));


        pnlBook.add(new JLabel(" ISBN:"));
        txtIsbn = new JTextField();
        pnlBook.add(txtIsbn);

        pnlBook.add(new JLabel(" Kitap Adı:"));
        txtBookName = new JTextField();
        pnlBook.add(txtBookName);

        pnlBook.add(new JLabel(" Yazar Adı:"));
        txtAuthorName = new JTextField();
        pnlBook.add(txtAuthorName);

        pnlBook.add(new JLabel(" Yazar Soyadı:"));
        txtAuthorSurname = new JTextField();
        pnlBook.add(txtAuthorSurname);

        String[] categories = {"Roman" , "Bilim Kurgu", "Tarih", "Yazılım" , "Biyografi" , "Otobiyografi"};
        JComboBox<String>selectCategory = new JComboBox<>(categories);

        pnlBook.add(new JLabel("Kategori Seçiniz: "));
        pnlBook.add(selectCategory);

        JButton btnAddBook = new JButton("Kitap Ekle");
        btnAddBook.setBackground(new Color(153, 187, 238));
        pnlBook.add(btnAddBook);

        JButton btnSearchBook = new JButton("Kitap Ara");
        pnlBook.add(btnSearchBook);
        add(pnlBook);

        JPanel pnlDelete = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlBook.add(pnlDelete);

        JButton btnDeleteBook = new JButton("Kitap Sil");
        btnDeleteBook.setBackground(new Color(153, 187, 238));
        pnlBook.add(btnDeleteBook);

        //Kitap ekleme işlemi.
        btnAddBook.addActionListener(e -> {
            try {
                String isbn = txtIsbn.getText();
                String bookName = txtBookName.getText();
                String authorName = txtAuthorName.getText();
                String authorSurname = txtAuthorName.getText();
                String selectedCat = (String) selectCategory.getSelectedItem();

                int catId = 1;
                if (selectedCat.equals("Roman")) catId = 1;
                else if (selectedCat.equals("Bilim Kurgu")) catId = 2;
                else if (selectedCat.equals("Tarih")) catId = 3;
                else if (selectedCat.equals("Yazılım")) catId = 4;
                else if (selectedCat.equals("Biyografi")) catId = 5;

                books b = new books(0, txtIsbn.getText(), txtBookName.getText(), LocalDate.now(),1,catId,authorName,authorSurname,selectedCat);

                new BookDAO().addBook(b);

                JOptionPane.showMessageDialog(this, "Kitap eklendi!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Girişleri kontrol edin!"); }
        });

        //Kitap Silme İşlemi.
        btnDeleteBook.addActionListener(e -> {
            String bookName = txtBookName.getText();

            if (bookName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek kitabı önce aratın!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    bookName + " isimli kitabı silmek istediğinize emin misiniz?",
                    "Silme Onayı", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                books b = new BookDAO().getBookByName(bookName);
                if (b != null) {
                    boolean success = new BookDAO().deleteBook(b.getId());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Kitap başarıyla silindi!");
                        txtIsbn.setText("");
                        txtBookName.setText("");
                        txtAuthorName.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Kitap silinemedi! (Ödünç verilmiş olabilir)");
                    }
                }
            }
        });

        //ÖDÜNÇ VERME VE İADE

        JPanel pnlLoan = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlLoan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ödünç Verme ve İade", TitledBorder.LEFT, TitledBorder.TOP));


        pnlLoan.add(new JLabel(" Üye No: "));
        txtMemberId = new JTextField();
        pnlLoan.add(txtMemberId);

        pnlLoan.add(new JLabel(" Kitap No: "));
        txtBookId = new JTextField();
        pnlLoan.add(txtBookId);
        pnlLoan.add(new JLabel(" İşlem Tipi:"));

        pnlLoan.add(new JComboBox<>(new String[]{"Ödünç", "İade"}));
        btnLoan = new JButton("Kitabı Ödünç Ver");

        btnLoan.setBackground(new Color(153, 187, 238));
        pnlLoan.add(btnLoan);

        JButton btnReturnLoan = new JButton("İade Al");
        btnReturnLoan.addActionListener(comingSoon);

        pnlLoan.add(btnReturnLoan);
        add(pnlLoan);



        // KATEGORİ YÖNETİMİ

        JPanel pnlCategory = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlCategory.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Kategori Yönetimi", TitledBorder.LEFT, TitledBorder.TOP));


        pnlCategory.add(new JLabel(" Kategori Adı:"));
        JTextField txtCatNameIn = new JTextField();
        pnlCategory.add(txtCatNameIn);

        pnlCategory.add(new JLabel(" Açıklama:"));
        JTextField txtCatDescIn = new JTextField();
        pnlCategory.add(txtCatDescIn);
        pnlCategory.add(new JLabel("")); pnlCategory.add(new JLabel(""));

        JButton btnAddCat = new JButton("Kategori Ekle");
        btnAddCat.setBackground(new Color(153, 204, 153));
        pnlCategory.add(btnAddCat);

        JButton btnEditCat = new JButton("Kategori Düzenle");
        pnlCategory.add(btnEditCat);
        add(pnlCategory);

        //Kategori Ekleme
        btnAddCat.addActionListener(e -> {
            if (new CategoryDAO().addCategory(txtCatNameIn.getText(), txtCatDescIn.getText()))
                JOptionPane.showMessageDialog(this, "Kategori eklendi!");
        });



        // YAZAR İŞLEMLERİ

        JPanel pnlAuthor = new JPanel(new GridLayout(3, 4, 5, 5));
        pnlAuthor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Yazar İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));


        pnlAuthor.add(new JLabel(" Yazar Adı: "));
        JTextField txtAuthorName = new JTextField();
        pnlAuthor.add(txtAuthorName);

        pnlAuthor.add(new JLabel(" Yazar Soyadı: "));
        JTextField txtAuthorSurname = new JTextField();
        pnlAuthor.add(txtAuthorSurname);

        pnlAuthor.add(new JLabel(" Biyografi: "));
        JTextField txtAuthorBiography = new JTextField();
        pnlAuthor.add(txtAuthorBiography);

        JButton btnAddAuthor= new JButton("Yazar Ekle");
        btnAddAuthor.setBackground(new Color(153, 204, 153));
        pnlAuthor.add(btnAddAuthor);

        JButton btnEditAuthor = new JButton("Yazar Düzenle");
        pnlAuthor.add(btnEditAuthor);
        add(pnlAuthor);

        JButton btnSearchAuthor = new JButton("Yazar Ara");
        pnlAuthor.add(btnSearchAuthor);
        add(pnlAuthor);

        JButton btnDeleteAuthor = new JButton("Yazar Sil");
        pnlAuthor.add(btnDeleteAuthor);
        add(pnlAuthor);


        //Yazar ekleme.
        btnAddAuthor.addActionListener(e -> {
            if (new AuthorDAO().addAuthor(txtAuthorName.getText(), txtAuthorSurname.getText(), txtAuthorBiography.getText()))
                JOptionPane.showMessageDialog(this, "Yazar eklendi!");
        });

        //Yazar arama.
        btnSearchAuthor.addActionListener(e -> {
            String NameIn = JOptionPane.showInputDialog(this, "Aranacak Yazar Adını Girin:");

            if (NameIn != null && !NameIn.trim().isEmpty()) {
                try {

                    String[] parts = NameIn.trim().split(" ");

                    String name = parts[0];
                    String surname = parts.length > 1 ? parts[1] : "";

                    authors author = new AuthorDAO().getAuthorByName(name, surname);

                    if (author != null) {
                        txtAuthorName.setText(author.getAuthor_name());
                        txtAuthorSurname.setText(author.getAuthor_surname());
                        txtAuthorBiography.setText(author.getBiography());
                        JOptionPane.showMessageDialog(this, "Yazar bulundu ve kutucuklara dolduruldu.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Yazar bulunamadı!");
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
                }
            }
        });

        //Yazar silme.
        btnDeleteAuthor.addActionListener(e -> {
            String fullName = txtAuthorName.getText().trim() + " " + txtAuthorSurname.getText().trim();

            if (fullName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek yazarı önce aratın!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    fullName + " isimli yazarı silmek istediğinize emin misiniz?",
                    "Silme Onayı", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                authors author = new AuthorDAO().getAuthorByName(txtAuthorName.getText(), txtAuthorSurname.getText());
                if (author != null) {
                    boolean success = new AuthorDAO().DeleteAuthor(author.getAuthor_name());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Yazar başarıyla silindi!");
                        txtAuthorName.setText("");
                        txtAuthorSurname.setText("");
                        txtAuthorBiography.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Kitap silinemedi! (Ödünç verilmiş olabilir)");
                    }
                }
            }
        });

        // CEZA, RAPOR, ÇIKIŞ

        JPanel pnlFine = new JPanel(new FlowLayout());
        pnlFine.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ceza İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        JButton btnCheckFine = new JButton("Ceza Hesapla");
        pnlFine.add(btnCheckFine);
        add(pnlFine);

        JPanel pnlReports = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlReports.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Raporlar", TitledBorder.LEFT, TitledBorder.TOP));
        JButton btnLoanRep = new JButton("Ödünç Raporu"); btnLoanRep.addActionListener(comingSoon);
        JButton btnStockRep = new JButton("Stok Raporu"); btnStockRep.addActionListener(comingSoon);
        pnlReports.add(btnLoanRep); pnlReports.add(btnStockRep);
        add(pnlReports);

        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExit = new JButton("ÇIKIŞ"); btnExit.setBackground(new Color(211, 47, 47));
        pnlFooter.add(btnExit); add(pnlFooter);

        // ================= ACTION LISTENERS (ETKİLEŞİMLER) =================

        // KİTAP ARA
        btnSearchBook.addActionListener(e -> {
            String NameIn = JOptionPane.showInputDialog(this, "Aranacak Kitap Adını Girin:");
            if (NameIn != null && !NameIn.isEmpty()) {
                try {
                    books book = new BookDAO().getBookByName(NameIn);
                    if (book != null) {
                        txtIsbn.setText(book.getIsbn());
                        txtBookName.setText(book.getBook_name());
                        JOptionPane.showMessageDialog(this, "Kitap bulundu ve kutucuklara dolduruldu.");
                    } else { JOptionPane.showMessageDialog(this, "Kitap bulunamadı!"); }
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Geçersiz Kitap Adı!"); }
            }
        });

        // KATEGORİ DÜZENLE
        btnEditCat.addActionListener(e -> {
            String idIn = JOptionPane.showInputDialog(this, "Düzenlenecek Kategori ID:");
            if (idIn != null && !idIn.isEmpty()) {
                try {
                    CategoryDAO dao = new CategoryDAO();
                    categories exCat = dao.getCategoryById(Integer.parseInt(idIn));
                    if (exCat != null) {
                        JTextField fN = new JTextField(exCat.getCategory_name());
                        JTextField fD = new JTextField(exCat.getDescription());
                        Object[] msg = {"Ad:", fN, "Açıklama:", fD};
                        if (JOptionPane.showConfirmDialog(this, msg, "Kategori Güncelle", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                            if (dao.updateCategory(exCat.getId(), fN.getText(), fD.getText()))
                                JOptionPane.showMessageDialog(this, "Güncellendi!");
                        }
                    } else { JOptionPane.showMessageDialog(this, "Bulunamadı!"); }
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Hata!"); }
            }
        });

        // YAZAR DÜZENLE
        btnEditAuthor.addActionListener(e -> {
            String idIn = JOptionPane.showInputDialog(this, "Düzenlenecek Yazar ID:");
            if (idIn != null && !idIn.isEmpty()) {
                try {
                    AuthorDAO dao = new AuthorDAO();
                    authors exAut = dao.getAuthorById(Integer.parseInt(idIn));
                    if (exAut != null) {
                        JTextField fN = new JTextField(exAut.getAuthor_name());
                        JTextField fS = new JTextField(exAut.getAuthor_surname());
                        JTextField fB = new JTextField(exAut.getBiography());
                        Object[] msg = {"Ad:", fN, "Soyad:", fS, "Bio:", fB};
                        if (JOptionPane.showConfirmDialog(this, msg, "Yazar Güncelle", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                            if (dao.updateAuthor(exAut.getId(), fN.getText(), fS.getText(), fB.getText()))
                                JOptionPane.showMessageDialog(this, "Güncellendi!");
                        }
                    } else { JOptionPane.showMessageDialog(this, "Bulunamadı!"); }
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Hata!"); }
            }
        });

        // DİĞER
        btnLoan.addActionListener(e -> {
            try {
                if (loanDAO.issueLoan(Integer.parseInt(txtMemberId.getText()), Integer.parseInt(txtBookId.getText())))
                    JOptionPane.showMessageDialog(this, "Başarılı!");
                else JOptionPane.showMessageDialog(this, "Hata!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Sayı girin!"); }
        });

        btnCheckFine.addActionListener(e -> {
            String in = JOptionPane.showInputDialog(this, "Ödünç No:");
            if (in != null) JOptionPane.showMessageDialog(this, "Borç: " + loanDAO.calculateAndGetFine(Integer.parseInt(in)) + " TL");
        });

        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}