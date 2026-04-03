import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.time.LocalDate;


public class LibrarySystemUI extends JFrame {
    private JTextField txtMemberId , txtBookId;
    private JButton btnLoan , btnExit;
    private LoanDAO loanDAO; //Veritabanı ile iletişim.
    private JButton btnUserAdd;

import java.awt.event.ActionListener;

public class LibrarySystemUI extends JFrame {
    private JTextField txtMemberId, txtBookId;
    private JTextField txtIsbn, txtBookName, txtAuthorId, txtCatId; // Kitap ara için bunları yukarı taşıdık
    private JButton btnLoan, btnExit;
    private LoanDAO loanDAO;


    public LibrarySystemUI() {
        setTitle("Kütüphane Otomasyon Sistemi");
        setSize(950, 780);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(4, 2, 15, 15));
        loanDAO = new LoanDAO();


        //ÜYE İŞLEMLERİ PANELİ
        add(createSectionPanel("Üye İşlemleri", new String[]{"Üye Ekle", "Üye Ara", "Üye Düzenle"})); //String kullanılmasının sebebi buton isimlerinin bir dizi gibi sıralanmasıdır.
        //KİTAP İŞLEMLERİ PANELİ
        JPanel pnlBook = createSectionPanel("Kitap İşlemleri", new String[]{"Kitap Ekle" , "Kitap Ara" , "Kitap Düzenle"});
        pnlBook.add(new JLabel("Kategori:"));
        pnlBook.add(new JComboBox<>(new String[]{"Roman", "Bilim", "Tarih", "Yazılım" , "Masal" , "Biyografi" , "Siyaset" , "Kişisel Gelişim"}));

        // Henüz kodlanmamış butonlar için genel uyarı mesajı
        ActionListener comingSoon = e -> {
            JButton source = (JButton) e.getSource();
            JOptionPane.showMessageDialog(this,
                    source.getText() + " modülü Faz-2 kapsamında geliştirilecektir.",
                    "Bilgi", JOptionPane.INFORMATION_MESSAGE);
        };

        // ---------------------------------------------------------
        // 1. KUTU: ÜYE İŞLEMLERİ
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
        JButton btnSearchMember = new JButton("Üye Ara");
        btnSearchMember.addActionListener(comingSoon);
        pnlMember.add(btnSearchMember);
        add(pnlMember);

        // ---------------------------------------------------------
        // 2. KUTU: KİTAP İŞLEMLERİ
        // ---------------------------------------------------------
        JPanel pnlBook = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlBook.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Kitap İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        pnlBook.add(new JLabel(" ISBN:"));
        txtIsbn = new JTextField();
        pnlBook.add(txtIsbn);
        pnlBook.add(new JLabel(" Kitap Adı:"));
        txtBookName = new JTextField();
        pnlBook.add(txtBookName);
        pnlBook.add(new JLabel(" Yazar/Kat ID:"));
        JPanel pnlBookIds = new JPanel(new GridLayout(1, 2, 5, 0));
        txtAuthorId = new JTextField();
        txtCatId = new JTextField();
        pnlBookIds.add(txtAuthorId);
        pnlBookIds.add(txtCatId);
        pnlBook.add(pnlBookIds);
        JButton btnAddBook = new JButton("Kitap Ekle");
        btnAddBook.setBackground(new Color(153, 204, 153));
        pnlBook.add(btnAddBook);
        JButton btnSearchBook = new JButton("Kitap Ara");
        pnlBook.add(btnSearchBook);
        add(pnlBook);

        // ---------------------------------------------------------
        // 3. KUTU: ÖDÜNÇ VERME VE İADE
        // ---------------------------------------------------------
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

        // ---------------------------------------------------------
        // 4. KUTU: KATEGORİ YÖNETİMİ
        // ---------------------------------------------------------
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

        // ---------------------------------------------------------
        // 5. YAZAR İŞLEMLERİ
        // ---------------------------------------------------------
        JPanel pnlAuthor = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlAuthor.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Yazar İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        pnlAuthor.add(new JLabel(" Yazar Adı: "));
        JTextField txtAuthN = new JTextField();
        pnlAuthor.add(txtAuthN);
        pnlAuthor.add(new JLabel(" Yazar Soyadı: "));
        JTextField txtAuthS = new JTextField();
        pnlAuthor.add(txtAuthS);
        pnlAuthor.add(new JLabel(" Biyografi: "));
        JTextField txtAuthB = new JTextField();
        pnlAuthor.add(txtAuthB);
        JButton btnAddAuth = new JButton("Yazar Ekle");
        btnAddAuth.setBackground(new Color(153, 204, 153));
        pnlAuthor.add(btnAddAuth);
        JButton btnEditAuth = new JButton("Yazar Düzenle");
        pnlAuthor.add(btnEditAuth);
        add(pnlAuthor);

        // ---------------------------------------------------------
        // 6, 7, 8: CEZA, RAPOR, ÇIKIŞ
        // ---------------------------------------------------------
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

        // KİTAP ARA (Yeni!)
        btnSearchBook.addActionListener(e -> {
            String idIn = JOptionPane.showInputDialog(this, "Aranacak Kitap ID girin:");
            if (idIn != null && !idIn.isEmpty()) {
                try {
                    books b = new BookDAO().getBookById(Integer.parseInt(idIn));
                    if (b != null) {
                        txtIsbn.setText(b.getIsbn());
                        txtBookName.setText(b.getBook_name());
                        txtAuthorId.setText(String.valueOf(b.getAuthor_id()));
                        txtCatId.setText(String.valueOf(b.getCategory_id()));
                        JOptionPane.showMessageDialog(this, "Kitap bulundu ve kutucuklara dolduruldu.");
                    } else { JOptionPane.showMessageDialog(this, "Kitap bulunamadı!"); }
                } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Geçersiz ID!"); }
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
        btnEditAuth.addActionListener(e -> {
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

        // EKLEME İŞLEMLERİ
        btnAddBook.addActionListener(e -> {
            try {
                books b = new books(0, txtIsbn.getText(), txtBookName.getText(), LocalDate.now(), Integer.parseInt(txtAuthorId.getText()), Integer.parseInt(txtCatId.getText()));
                new BookDAO().addBook(b);
                JOptionPane.showMessageDialog(this, "Kitap eklendi!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Girişleri kontrol edin!"); }
        });

        btnAddCat.addActionListener(e -> {
            if (new CategoryDAO().addCategory(txtCatNameIn.getText(), txtCatDescIn.getText()))
                JOptionPane.showMessageDialog(this, "Kategori eklendi!");
        });

        btnAddAuth.addActionListener(e -> {
            if (new AuthorDAO().addAuthor(txtAuthN.getText(), txtAuthS.getText(), txtAuthB.getText()))
                JOptionPane.showMessageDialog(this, "Yazar eklendi!");
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
