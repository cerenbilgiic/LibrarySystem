import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;

public class LibrarySystemUI extends JFrame {
    private JTextField txtMemberId, txtBookId;
    private JButton btnLoan, btnExit;
    private LoanDAO loanDAO;

    public LibrarySystemUI() {
        setTitle("Kütüphane Otomasyon Sistemi");
        setSize(950, 780); // İçerik arttığı için pencereyi biraz daha genişlettik
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 4 satır, 2 sütunluk ana şablon
        setLayout(new GridLayout(4, 2, 15, 15));
        loanDAO = new LoanDAO();

        // ---------------------------------------------------------
        // 1. KUTU: ÜYE İŞLEMLERİ (Aktifleştirildi)
        // ---------------------------------------------------------
        JPanel pnlMember = new JPanel(new GridLayout(4, 2, 5, 5));
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

        JButton btnAddMember = new JButton("Üye Ekle");
        pnlMember.add(btnAddMember);
        pnlMember.add(new JButton("Üye Ara"));
        add(pnlMember);

        // ---------------------------------------------------------
        // 2. KUTU: KİTAP İŞLEMLERİ (Aktifleştirildi ve BookDAO'ya bağlandı)
        // ---------------------------------------------------------
        JPanel pnlBook = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlBook.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Kitap İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));

        pnlBook.add(new JLabel(" ISBN:"));
        JTextField txtIsbn = new JTextField();
        pnlBook.add(txtIsbn);

        pnlBook.add(new JLabel(" Kitap Adı:"));
        JTextField txtBookName = new JTextField();
        pnlBook.add(txtBookName);

        pnlBook.add(new JLabel(" Yazar ID / Kategori ID:"));
        JPanel pnlBookIds = new JPanel(new GridLayout(1, 2, 5, 0));
        JTextField txtAuthorId = new JTextField();
        JTextField txtCatId = new JTextField();
        pnlBookIds.add(txtAuthorId);
        pnlBookIds.add(txtCatId);
        pnlBook.add(pnlBookIds);

        JButton btnAddBook = new JButton("Kitap Ekle");
        btnAddBook.setBackground(new Color(153, 204, 153));
        pnlBook.add(btnAddBook);
        pnlBook.add(new JButton("Kitap Ara"));
        add(pnlBook);

        // ---------------------------------------------------------
        // 3. KUTU: ÖDÜNÇ VERME VE İADE PANELİ
        // ---------------------------------------------------------
        JPanel pnlLoan = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlLoan.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ödünç Verme ve İade", TitledBorder.LEFT, TitledBorder.TOP));
        pnlLoan.add(new JLabel(" Üye Numarası: "));
        txtMemberId = new JTextField();
        pnlLoan.add(txtMemberId);
        pnlLoan.add(new JLabel(" Kitap Numarası: "));
        txtBookId = new JTextField();
        pnlLoan.add(txtBookId);
        pnlLoan.add(new JLabel(" İşlem Tipi:"));
        pnlLoan.add(new JComboBox<>(new String[]{"Ödünç", "İade"}));
        btnLoan = new JButton("Kitabı Ödünç Ver");
        btnLoan.setBackground(new Color(153, 187, 238));
        pnlLoan.add(btnLoan);
        pnlLoan.add(new JButton("İade Al"));
        add(pnlLoan);

        // ---------------------------------------------------------
        // 4. KUTU: KATEGORİ YÖNETİMİ (Aktifleştirildi)
        // ---------------------------------------------------------
        JPanel pnlCategory = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlCategory.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Kategori Yönetimi", TitledBorder.LEFT, TitledBorder.TOP));
        pnlCategory.add(new JLabel(" Kategori Adı:"));
        JTextField txtCatName = new JTextField();
        pnlCategory.add(txtCatName);
        pnlCategory.add(new JLabel(" Açıklama:"));
        JTextField txtCatDesc = new JTextField();
        pnlCategory.add(txtCatDesc);
        pnlCategory.add(new JLabel("")); // Boşluk
        pnlCategory.add(new JLabel(""));
        JButton btnAddCat = new JButton("Kategori Ekle");
        pnlCategory.add(btnAddCat);
        pnlCategory.add(new JButton("Kategori Düzenle"));
        add(pnlCategory);

        // ---------------------------------------------------------
        // 5. KUTU: YAZAR İŞLEMLERİ PANELİ
        // ---------------------------------------------------------
        JPanel pnlAuthor = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlAuthor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Yazar İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        pnlAuthor.add(new JLabel(" Yazar Adı: "));
        JTextField txtAuthorName = new JTextField();
        pnlAuthor.add(txtAuthorName);
        pnlAuthor.add(new JLabel(" Yazar Soyadı: "));
        JTextField txtAuthorSurname = new JTextField();
        pnlAuthor.add(txtAuthorSurname);
        pnlAuthor.add(new JLabel(" Biyografi: "));
        JTextField txtAuthorBio = new JTextField();
        pnlAuthor.add(txtAuthorBio);
        JButton btnSaveAuthor = new JButton("Yazar Ekle");
        btnSaveAuthor.setBackground(new Color(153, 204, 153));
        pnlAuthor.add(btnSaveAuthor);
        pnlAuthor.add(new JButton("Yazar Düzenle"));
        add(pnlAuthor);

        // ---------------------------------------------------------
        // 6. KUTU: CEZA İŞLEMLERİ PANELİ
        // ---------------------------------------------------------
        JPanel pnlFine = new JPanel(new FlowLayout());
        pnlFine.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Para Cezası İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        JButton btnCheckFine = new JButton("Ceza Hesapla");
        btnCheckFine.setPreferredSize(new Dimension(150, 40));
        pnlFine.add(btnCheckFine);
        add(pnlFine);

        // ---------------------------------------------------------
        // 7. KUTU: RAPORLAR PANELİ
        // ---------------------------------------------------------
        JPanel pnlReports = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlReports.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Raporlar", TitledBorder.LEFT, TitledBorder.TOP));
        pnlReports.add(new JButton("Ödünç Raporu"));
        pnlReports.add(new JButton("Stok Raporu"));
        add(pnlReports);

        // ---------------------------------------------------------
        // 8. KUTU: ÇIKIŞ PANELİ
        // ---------------------------------------------------------
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExit = new JButton("ÇIKIŞ");
        btnExit.setBackground(new Color(211, 47, 47));
        btnExit.setForeground(Color.BLACK);
        pnlFooter.add(btnExit);
        add(pnlFooter);


        // ==================================================================
        // DİNAMİK BUTON İŞLEVLERİ (ACTION LISTENERS)
        // ==================================================================

        // 1. KİTAP EKLE BUTONU (BookDAO'ya Bağlandı)
        btnAddBook.addActionListener(e -> {
            try {
                String isbn = txtIsbn.getText();
                String name = txtBookName.getText();
                int aId = Integer.parseInt(txtAuthorId.getText());
                int cId = Integer.parseInt(txtCatId.getText());

                if (isbn.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "ISBN ve Kitap Adı boş bırakılamaz!");
                    return;
                }

                // Arkadaşının yazdığı books sınıfını ve BookDAO'yu kullanıyoruz
                books newBook = new books(0, isbn, name, LocalDate.now(), aId, cId);
                BookDAO bDao = new BookDAO();
                bDao.addBook(newBook);

                JOptionPane.showMessageDialog(this, "Kitap başarıyla veritabanına eklendi!");
                txtIsbn.setText(""); txtBookName.setText(""); txtAuthorId.setText(""); txtCatId.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Yazar ID ve Kategori ID sayısal olmalıdır!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Veritabanı bağlantı hatası!");
            }
        });

        // 2. ÜYE EKLE BUTONU (Arka planı eklenecek)
        btnAddMember.addActionListener(e -> {
            String name = txtMemName.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Üye Adı boş olamaz!");
            } else {
                JOptionPane.showMessageDialog(this, "Üye bilgileri arayüzden başarıyla alındı!\n(Backend veritabanı bağlantısı bekleniyor)");
                txtMemName.setText(""); txtMemSurname.setText(""); txtMemEmail.setText("");
            }
        });

        // 3. KATEGORİ EKLE BUTONU (Arka planı eklenecek)
        btnAddCat.addActionListener(e -> {
            if (txtCatName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kategori adı giriniz!");
            } else {
                JOptionPane.showMessageDialog(this, "Kategori arayüzden alındı!\n(Backend veritabanı bağlantısı bekleniyor)");
                txtCatName.setText(""); txtCatDesc.setText("");
            }
        });

        // 4. YAZAR EKLE BUTONU
        btnSaveAuthor.addActionListener(e -> {
            String name = txtAuthorName.getText();
            String surname = txtAuthorSurname.getText();
            String bio = txtAuthorBio.getText();

            if (name.isEmpty() || surname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen Ad ve Soyad alanlarını doldurunuz!");
                return;
            }

            AuthorDAO dao = new AuthorDAO();
            if (dao.addAuthor(name, surname, bio)) {
                JOptionPane.showMessageDialog(this, "Yazar başarıyla eklendi!");
                txtAuthorName.setText(""); txtAuthorSurname.setText(""); txtAuthorBio.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Kayıt sırasında veritabanı hatası oluştu!");
            }
        });

        // 5. ÖDÜNÇ VER BUTONU
        btnLoan.addActionListener(e -> {
            try {
                int mId = Integer.parseInt(txtMemberId.getText());
                int bId = Integer.parseInt(txtBookId.getText());

                boolean sonuc = loanDAO.issueLoan(mId, bId);

                if (sonuc) JOptionPane.showMessageDialog(this, "Kitap teslimi başarılı.");
                else JOptionPane.showMessageDialog(this, "Kitap teslim başarısız. Stok yetersiz veya numaralar yanlış.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli sayısal bir numara giriniz.");
            }
        });

        // 6. CEZA HESAPLA BUTONU
        btnCheckFine.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Sorgulanacak Ödünç Numarasını giriniz:");
            if (input != null && !input.isEmpty()) {
                try {
                    int loanId = Integer.parseInt(input);
                    double fine = loanDAO.calculateAndGetFine(loanId);
                    if (fine > 0) JOptionPane.showMessageDialog(this, "Borç: " + fine + " TL");
                    else JOptionPane.showMessageDialog(this, "Gecikme bulunmamaktadır.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Geçersiz Numara!");
                }
            }
        });

        // ÇIKIŞ BUTONU
        btnExit.addActionListener(e -> System.exit(0));

        setVisible(true);
    }
}
