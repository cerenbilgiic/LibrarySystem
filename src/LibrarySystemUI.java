import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LibrarySystemUI extends JFrame {
    private JTextField txtMemberId, txtBookId;
    private JButton btnLoan, btnExit;
    private LoanDAO loanDAO;

    public LibrarySystemUI() {
        setTitle("Kütüphane Otomasyon Sistemi");
        setSize(900, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 4 satır, 2 sütunluk ana şablon (Toplam 8 kutu alacak)
        setLayout(new GridLayout(4, 2, 15, 15));
        loanDAO = new LoanDAO();

        // 1. KUTU: ÜYE İŞLEMLERİ PANELİ
        add(createSectionPanel("Üye İşlemleri", new String[]{"Üye Ekle", "Üye Ara", "Üye Düzenle"}));

        // 2. KUTU: KİTAP İŞLEMLERİ PANELİ
        JPanel pnlBook = createSectionPanel("Kitap İşlemleri", new String[]{"Kitap Ekle", "Kitap Ara", "Kitap Düzenle"});
        pnlBook.add(new JLabel("Kategori:"));
        pnlBook.add(new JComboBox<>(new String[]{"Roman", "Bilim", "Tarih", "Yazılım", "Masal", "Biyografi", "Siyaset", "Kişisel Gelişim"}));
        add(pnlBook);

        // 3. KUTU: ÖDÜNÇ VERME VE İADE PANELİ
        JPanel pnlLoan = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlLoan.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Ödünç Verme ve İade", TitledBorder.LEFT, TitledBorder.TOP));

        pnlLoan.add(new JLabel("Üye Numarası: "));
        txtMemberId = new JTextField();
        pnlLoan.add(txtMemberId);

        pnlLoan.add(new JLabel("Kitap Numarası: "));
        txtBookId = new JTextField();
        pnlLoan.add(txtBookId);

        pnlLoan.add(new JLabel(" İşlem Tipi:"));
        pnlLoan.add(new JComboBox<>(new String[]{"Ödünç", "İade"}));

        btnLoan = new JButton("Kitabı Ödünç Ver");
        btnLoan.setBackground(new Color(153, 187, 238));
        pnlLoan.add(btnLoan);
        pnlLoan.add(new JButton("İade Al"));
        add(pnlLoan);

        // 4. KUTU: KATEGORİ YÖNETİMİ
        add(createSectionPanel("Kategori Yönetimi", new String[]{"Kategori Ekle", "Kategori Düzenle"}));

        // 5. KUTU: YAZAR İŞLEMLERİ PANELİ (Yeni Tasarımımız)
        JPanel pnlAuthor = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlAuthor.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Yazar İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));

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

        JButton btnEditAuthor = new JButton("Yazar Düzenle");
        pnlAuthor.add(btnEditAuthor);

        add(pnlAuthor);

        // 6. KUTU: CEZA İŞLEMLERİ PANELİ
        JPanel pnlFine = new JPanel(new FlowLayout());
        pnlFine.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Para Cezası İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        JButton btnCheckFine = new JButton("Ceza Hesapla");
        btnCheckFine.setPreferredSize(new Dimension(150, 40));
        pnlFine.add(btnCheckFine);
        add(pnlFine);

        // 7. KUTU: RAPORLAR PANELİ
        JPanel pnlReports = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlReports.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Raporlar", TitledBorder.LEFT, TitledBorder.TOP));
        pnlReports.add(new JButton("Ödünç Raporu"));
        pnlReports.add(new JButton("Stok Raporu"));
        add(pnlReports);

        // 8. KUTU: ÇIKIŞ PANELİ
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExit = new JButton("ÇIKIŞ");
        btnExit.setBackground(new Color(211, 47, 47));
        btnExit.setForeground(Color.BLACK);
        pnlFooter.add(btnExit);
        add(pnlFooter);


        // ------------------------------------------------------------------
        // BUTON İŞLEVLERİ (ACTION LISTENERS) - Tıklanınca Ne Olacak?
        // ------------------------------------------------------------------

        // YAZAR EKLE BUTONU (Yeni Eklenen Görev)
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
                // Ekrana başarıyla eklendikten sonra kutuların içini temizle
                txtAuthorName.setText("");
                txtAuthorSurname.setText("");
                txtAuthorBio.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Kayıt sırasında veritabanı hatası oluştu!");
            }
        });

        // ÖDÜNÇ VER BUTONU
        btnLoan.addActionListener(e -> {
            try {
                int mId = Integer.parseInt(txtMemberId.getText());
                int bId = Integer.parseInt(txtBookId.getText());

                // Hata düzeltildi: Büyük I yerine küçük i kullanıldı (issueLoan)
                boolean sonuc = loanDAO.issueLoan(mId, bId);

                if (sonuc) JOptionPane.showMessageDialog(this, "Kitap teslimi başarılı.");
                else JOptionPane.showMessageDialog(this, "Kitap teslim başarısız. Stok yetersiz veya üye/kitap numarası yanlış.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli sayısal bir numara giriniz.");
            }
        });

        // CEZA HESAPLA BUTONU
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

        // Ekranı görünür yap
        setVisible(true);
    }

    // Arayüzdeki diğer butonları hızlıca oluşturmak için cerenin yazdığı yardımcı metot
    private JPanel createSectionPanel(String title, String[] buttons) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), title, TitledBorder.LEFT, TitledBorder.TOP));
        for (String b : buttons) {
            panel.add(new JButton(b));
        }
        return panel;
    }

    private void handleLoanAction() {
        try {
            int mId = Integer.parseInt(txtMemberId.getText());
            int bId = Integer.parseInt(txtBookId.getText());
            // Hata düzeltildi: Büyük I yerine küçük i kullanıldı (issueLoan)
            if (loanDAO.issueLoan(mId, bId)) JOptionPane.showMessageDialog(this, "İşlem Başarılı!");
            else JOptionPane.showMessageDialog(this, "Hata: Stok yetersiz!");
        } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Geçersiz ID!"); }
    }

    private void handleFineAction() {
        String input = JOptionPane.showInputDialog(this, "Loan ID:");
        if (input != null) {
            double fine = loanDAO.calculateAndGetFine(Integer.parseInt(input));
            JOptionPane.showMessageDialog(this, "Ceza: " + fine + " TL");
        }
    }
}
