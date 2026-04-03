import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;
import java.awt.event.ActionListener;

public class LibrarySystemUI extends JFrame {
    private JTextField txtMemberId, txtBookId;
    private JButton btnLoan, btnExit;
    private LoanDAO loanDAO;

    public LibrarySystemUI() {
        setTitle("Kütüphane Otomasyon Sistemi");
        setSize(950, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(4, 2, 15, 15));
        loanDAO = new LoanDAO();

        // Ölü butonlar için genel uyarı
        ActionListener comingSoon = e -> {
            JButton source = (JButton) e.getSource();
            JOptionPane.showMessageDialog(this,
                    source.getText() + " modülü Faz-2 kapsamında geliştirilecektir.",
                    "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        };

        // --- 1. ÜYE İŞLEMLERİ ---
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
        JButton btnSearchMember = new JButton("Üye Ara");
        btnSearchMember.addActionListener(comingSoon);
        pnlMember.add(btnSearchMember);
        add(pnlMember);

        // --- 2. KİTAP İŞLEMLERİ ---
        JPanel pnlBook = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlBook.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Kitap İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        pnlBook.add(new JLabel(" ISBN:"));
        JTextField txtIsbn = new JTextField();
        pnlBook.add(txtIsbn);
        pnlBook.add(new JLabel(" Kitap Adı:"));
        JTextField txtBookName = new JTextField();
        pnlBook.add(txtBookName);
        pnlBook.add(new JLabel(" Yazar/Kat ID:"));
        JPanel pnlBookIds = new JPanel(new GridLayout(1, 2, 5, 0));
        JTextField txtAuthorId = new JTextField();
        JTextField txtCatId = new JTextField();
        pnlBookIds.add(txtAuthorId);
        pnlBookIds.add(txtCatId);
        pnlBook.add(pnlBookIds);
        JButton btnAddBook = new JButton("Kitap Ekle");
        btnAddBook.setBackground(new Color(153, 204, 153));
        pnlBook.add(btnAddBook);
        JButton btnSearchBook = new JButton("Kitap Ara");
        btnSearchBook.addActionListener(comingSoon);
        pnlBook.add(btnSearchBook);
        add(pnlBook);

        // --- 3. ÖDÜNÇ VERME VE İADE ---
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
        JButton btnReturnLoan = new JButton("İade Al");
        btnReturnLoan.addActionListener(comingSoon);
        pnlLoan.add(btnReturnLoan);
        add(pnlLoan);

        // --- 4. KATEGORİ YÖNETİMİ ---
        JPanel pnlCategory = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlCategory.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Kategori Yönetimi", TitledBorder.LEFT, TitledBorder.TOP));
        pnlCategory.add(new JLabel(" Kategori Adı:"));
        JTextField txtCatNameInput = new JTextField();
        pnlCategory.add(txtCatNameInput);
        pnlCategory.add(new JLabel(" Açıklama:"));
        JTextField txtCatDescInput = new JTextField();
        pnlCategory.add(txtCatDescInput);
        pnlCategory.add(new JLabel("")); 
        pnlCategory.add(new JLabel(""));
        JButton btnAddCat = new JButton("Kategori Ekle");
        btnAddCat.setBackground(new Color(153, 204, 153));
        pnlCategory.add(btnAddCat);
        JButton btnEditCat = new JButton("Kategori Düzenle");
        pnlCategory.add(btnEditCat);
        add(pnlCategory);

        // --- 5. YAZAR İŞLEMLERİ ---
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
        JButton btnEditAuthor = new JButton("Yazar Düzenle");
        pnlAuthor.add(btnEditAuthor);
        add(pnlAuthor);

        // --- 6, 7, 8: CEZA, RAPOR, ÇIKIŞ ---
        JPanel pnlFine = new JPanel(new FlowLayout());
        pnlFine.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Ceza İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        JButton btnCheckFine = new JButton("Ceza Hesapla");
        pnlFine.add(btnCheckFine);
        add(pnlFine);

        JPanel pnlReports = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlReports.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Raporlar", TitledBorder.LEFT, TitledBorder.TOP));
        JButton btnLRep = new JButton("Ödünç Raporu"); btnLRep.addActionListener(comingSoon);
        JButton btnSRep = new JButton("Stok Raporu"); btnSRep.addActionListener(comingSoon);
        pnlReports.add(btnLRep); pnlReports.add(btnSRep);
        add(pnlReports);

        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExit = new JButton("ÇIKIŞ"); btnExit.setBackground(new Color(211, 47, 47));
        pnlFooter.add(btnExit); add(pnlFooter);

        // ================= ACTION LISTENERS =================

        // KATEGORİ DÜZENLE (Yeni Akıllı Sistem)
        btnEditCat.addActionListener(e -> {
            String idInput = JOptionPane.showInputDialog(this, "Düzenlenecek Kategori ID numarasını giriniz:");
            if (idInput != null && !idInput.isEmpty()) {
                try {
                    int catId = Integer.parseInt(idInput);
                    CategoryDAO dao = new CategoryDAO();
                    categories existing = dao.getCategoryById(catId);
                    
                    if (existing != null) {
                        JTextField fName = new JTextField(existing.getCategory_name());
                        JTextField fDesc = new JTextField(existing.getDescription());
                        Object[] msg = {"Kategori Adı:", fName, "Açıklama:", fDesc};

                        int option = JOptionPane.showConfirmDialog(this, msg, "Kategori Güncelle (ID: " + catId + ")", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            if (dao.updateCategory(catId, fName.getText(), fDesc.getText())) {
                                JOptionPane.showMessageDialog(this, "Kategori başarıyla güncellendi!");
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Kategori bulunamadı!");
                    }
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Geçersiz ID!"); }
            }
        });

        // YAZAR DÜZENLE (Mevcut Akıllı Sistem)
        btnEditAuthor.addActionListener(e -> {
            String idInput = JOptionPane.showInputDialog(this, "Düzenlenecek Yazar ID numarasını giriniz:");
            if (idInput != null && !idInput.isEmpty()) {
                try {
                    int authorId = Integer.parseInt(idInput);
                    AuthorDAO dao = new AuthorDAO();
                    authors existing = dao.getAuthorById(authorId);
                    if (existing != null) {
                        JTextField fName = new JTextField(existing.getAuthor_name());
                        JTextField fSurname = new JTextField(existing.getAuthor_surname());
                        JTextField fBio = new JTextField(existing.getBiography());
                        Object[] msg = {"Ad:", fName, "Soyad:", fSurname, "Biyografi:", fBio};

                        int option = JOptionPane.showConfirmDialog(this, msg, "Yazar Güncelle (ID: " + authorId + ")", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            if (dao.updateAuthor(authorId, fName.getText(), fSurname.getText(), fBio.getText())) {
                                JOptionPane.showMessageDialog(this, "Yazar güncellendi!");
                            }
                        }
                    } else { JOptionPane.showMessageDialog(this, "Yazar bulunamadı!"); }
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Geçersiz ID!"); }
            }
        });

        // DİĞER EKLEME İŞLEMLERİ
        btnAddCat.addActionListener(e -> {
            if (new CategoryDAO().addCategory(txtCatNameInput.getText(), txtCatDescInput.getText())) {
                JOptionPane.showMessageDialog(this, "Kategori eklendi!");
                txtCatNameInput.setText(""); txtCatDescInput.setText("");
            }
        });

        btnSaveAuthor.addActionListener(e -> {
            if (new AuthorDAO().addAuthor(txtAuthorName.getText(), txtAuthorSurname.getText(), txtAuthorBio.getText())) {
                JOptionPane.showMessageDialog(this, "Yazar eklendi!");
                txtAuthorName.setText(""); txtAuthorSurname.setText(""); txtAuthorBio.setText("");
            }
        });

        btnLoan.addActionListener(e -> {
            try {
                if (loanDAO.issueLoan(Integer.parseInt(txtMemberId.getText()), Integer.parseInt(txtBookId.getText()))) {
                    JOptionPane.showMessageDialog(this, "Ödünç verildi.");
                } else { JOptionPane.showMessageDialog(this, "Hata!"); }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Sayısal ID girin!"); }
        });

        btnCheckFine.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Ödünç No:");
            if (input != null) JOptionPane.showMessageDialog(this, "Borç: " + loanDAO.calculateAndGetFine(Integer.parseInt(input)) + " TL");
        });

        btnExit.addActionListener(e -> System.exit(0));
        setVisible(true);
    }
}
