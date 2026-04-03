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

        // ==================================================================
        // ORTAK "YAPIM AŞAMASINDA" BİLDİRİMİ (Ölü butonları canlandırmak için)
        // ==================================================================
        ActionListener comingSoon = e -> {
            JButton source = (JButton) e.getSource();
            JOptionPane.showMessageDialog(this,
                    source.getText() + " modülü Faz-2 (Phase 2) kapsamında geliştirilecektir.\nŞu an yapım aşamasındadır.",
                    "Geliştirme Aşamasında", JOptionPane.INFORMATION_MESSAGE);
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
        btnSearchMember.addActionListener(comingSoon); // Tıklanınca uyarı versin
        pnlMember.add(btnSearchMember);

        add(pnlMember);

        // ---------------------------------------------------------
        // 2. KUTU: KİTAP İŞLEMLERİ
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

        JButton btnSearchBook = new JButton("Kitap Ara");
        btnSearchBook.addActionListener(comingSoon); // Tıklanınca uyarı versin
        pnlBook.add(btnSearchBook);

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

        JButton btnReturnLoan = new JButton("İade Al");
        btnReturnLoan.addActionListener(comingSoon); // Tıklanınca uyarı versin
        pnlLoan.add(btnReturnLoan);

        add(pnlLoan);

        // ---------------------------------------------------------
        // 4. KUTU: KATEGORİ YÖNETİMİ
        // ---------------------------------------------------------
        JPanel pnlCategory = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlCategory.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Kategori Yönetimi", TitledBorder.LEFT, TitledBorder.TOP));
        pnlCategory.add(new JLabel(" Kategori Adı:"));
        JTextField txtCatName = new JTextField();
        pnlCategory.add(txtCatName);
        pnlCategory.add(new JLabel(" Açıklama:"));
        JTextField txtCatDesc = new JTextField();
        pnlCategory.add(txtCatDesc);
        pnlCategory.add(new JLabel(""));
        pnlCategory.add(new JLabel(""));

        JButton btnAddCat = new JButton("Kategori Ekle");
        btnAddCat.setBackground(new Color(153, 204, 153));
        pnlCategory.add(btnAddCat);

        JButton btnEditCat = new JButton("Kategori Düzenle");
        btnEditCat.addActionListener(comingSoon); // Tıklanınca uyarı versin
        pnlCategory.add(btnEditCat);

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

        JButton btnEditAuthor = new JButton("Yazar Düzenle");
        pnlAuthor.add(btnEditAuthor);

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

        JButton btnLoanReport = new JButton("Ödünç Raporu");
        btnLoanReport.addActionListener(comingSoon); // Tıklanınca uyarı versin
        pnlReports.add(btnLoanReport);

        JButton btnStockReport = new JButton("Stok Raporu");
        btnStockReport.addActionListener(comingSoon); // Tıklanınca uyarı versin
        pnlReports.add(btnStockReport);

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
        // GERÇEK VERİTABANI İŞLEMLERİ (ÇALIŞAN BUTONLAR)
        // ==================================================================

        // KİTAP EKLE BUTONU (BookDAO)
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

        // ÜYE EKLE BUTONU (UserDAO için yer tutucu)
        btnAddMember.addActionListener(e -> {
            String name = txtMemName.getText();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Üye Adı boş olamaz!");
            } else {
                JOptionPane.showMessageDialog(this, "Üye bilgileri alındı. (UserDAO bekleniyor...)");
                txtMemName.setText(""); txtMemSurname.setText(""); txtMemEmail.setText("");
            }
        });

        // KATEGORİ EKLE BUTONU (CategoryDAO)
        btnAddCat.addActionListener(e -> {
            String catName = txtCatName.getText();
            String catDesc = txtCatDesc.getText();

            if (catName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Kategori adı boş bırakılamaz!");
                return;
            }

            CategoryDAO cDao = new CategoryDAO();
            if (cDao.addCategory(catName, catDesc)) {
                JOptionPane.showMessageDialog(this, "Kategori başarıyla veritabanına eklendi!");
                txtCatName.setText("");
                txtCatDesc.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Kategori eklenirken veritabanı hatası oluştu!");
            }
        });

        // YAZAR EKLE BUTONU (AuthorDAO)
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

        // YAZAR DÜZENLE BUTONU (Profesyonel UX Tasarımı - Eski bilgileri getirir)
        btnEditAuthor.addActionListener(e -> {
            String idInput = JOptionPane.showInputDialog(this, "Düzenlemek istediğiniz yazarın ID numarasını giriniz:");

            if (idInput != null && !idInput.isEmpty()) {
                try {
                    int authorId = Integer.parseInt(idInput);
                    AuthorDAO dao = new AuthorDAO();

                    authors existingAuthor = dao.getAuthorById(authorId);

                    if (existingAuthor != null) {
                        JTextField fieldName = new JTextField(existingAuthor.getAuthor_name());
                        JTextField fieldSurname = new JTextField(existingAuthor.getAuthor_surname());
                        JTextField fieldBio = new JTextField(existingAuthor.getBiography());

                        Object[] message = {
                                "Yazar Adı:", fieldName,
                                "Yazar Soyadı:", fieldSurname,
                                "Biyografi:", fieldBio
                        };

                        int option = JOptionPane.showConfirmDialog(this, message,
                                "Yazar Bilgilerini Güncelle (ID: " + authorId + ")",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (option == JOptionPane.OK_OPTION) {
                            if (dao.updateAuthor(authorId, fieldName.getText(), fieldSurname.getText(), fieldBio.getText())) {
                                JOptionPane.showMessageDialog(this, "Yazar başarıyla güncellendi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(this, "Güncelleme başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Girdiğiniz ID ("+ authorId +") numarasına ait bir yazar bulunamadı!", "Bulunamadı", JOptionPane.WARNING_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Lütfen geçerli bir sayısal ID numarası giriniz!", "Geçersiz Giriş", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ÖDÜNÇ VER BUTONU (LoanDAO)
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

        setVisible(true);
    }
}
