import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;

public class LibrarySystemUI extends JFrame {

    private JPanel cardPanel;
    private CardLayout cardLayout;

    // Form alanları
    private JTextField txtMemberName, txtMemberSurname, txtMemberEmail, txtMemberPass;
    private JTextField txtIsbn, txtBookName, txtAuthorName, txtAuthorSurname,txtBiography;
    private JTextField txtMemberId, txtBookId,txtCategoryId, txtAuthorId, txtCatNameIn , txtCategoryDescrption;
    private JComboBox<String> selectCategory;
    private JButton btnSearchBook;

    JTextField txtBookAuthorName;
    JTextField txtBookAuthorSurname;

    // DAO - (Dosyaların projesinde mevcut olduğu varsayılıyor)
    private LoanDAO loanDAO = new LoanDAO();
    private MemberDAO memberDAO = new MemberDAO();
    private BookDAO bookDAO = new BookDAO();
    private AuthorDAO authorDAO = new AuthorDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    private String loggedUserName = "";


    // Giriş sonrası çalışan Constructor
    public LibrarySystemUI(String name) {
        this.loggedUserName = name;
        initUI();
    }

    // Direkt çalıştırma için varsayılan Constructor
    public LibrarySystemUI() {
        initUI();
    }

    private void initUI() {
        setTitle("SafeHaven | Kütüphane Otomasyon Sistemi");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- 1. CARDLAYOUT VE ANA PANEL HAZIRLIĞI ---
        // CRITICAL: Önce layout nesnesi oluşturulmalı!
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);

        // --- 2. WELCOME PANELİ ---
        JPanel pnlWelcome = new JPanel(new GridBagLayout());
        pnlWelcome.setBackground(Color.WHITE);

        // HATA DÜZELTME: txtMemberName.getText() yerine constructor'dan gelen loggedUserName kullanıldı.
        JLabel lblWelcome = new JLabel("<html><div style='text-align: center;'>"
                + "<h1 style='color: #2d3436; font-family: sans-serif;'>KÜTÜPHANE SİSTEMİNE HOŞ GELDİN</h1>"
                + "<h2 style='color: #0984e3; font-family: sans-serif;'>" + loggedUserName.toUpperCase() + "</h2>"
                + "<p style='color: #636e72; margin-top: 20px;'>Lütfen soldaki menüden işlem seçiniz.</p>"
                + "</div></html>");
        pnlWelcome.add(lblWelcome);

       //CARDPANELE PANELLERİ EKLEME
        cardPanel.add(pnlWelcome, "pnlWelcome");
        cardPanel.add(createMemberPanel(), "pnlMember");
        cardPanel.add(createBookPanel(), "pnlBook");
        cardPanel.add(createLoanPanel(), "pnlLoan");
        cardPanel.add(createAuthorPanel(), "pnlAuthor");
        cardPanel.add(createCategoryPanel(),"pnlCategory");

        // --- 4. SIDEBAR (SOL MENÜ) ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(45, 52, 54));
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        JLabel lblTitle = new JLabel("YÖNETİM PANELİ");
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblTitle);
        sidebar.add(Box.createRigidArea(new Dimension(0, 30)));

        sidebar.add(createMenuButton("Ana Sayfa", "pnlWelcome"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createMenuButton("Üye İşlemleri", "pnlMember"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createMenuButton("Kitap İşlemleri", "pnlBook"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createMenuButton("Ödünç / İade", "pnlLoan"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createMenuButton("Yazar İşlemleri", "pnlAuthor"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(createMenuButton("Kategori İşlemleri", "pnlCategory"));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));


        sidebar.add(Box.createVerticalGlue());

        JButton btnExit = new JButton("GÜVENLİ ÇIKIŞ");
        btnExit.setBackground(new Color(211, 47, 47));
        btnExit.setForeground(Color.BLACK);
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.addActionListener(e -> System.exit(0));
        sidebar.add(btnExit);

        // --- 5. ANA YERLEŞİM ---
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.add(sidebar, BorderLayout.WEST);
        mainContainer.add(cardPanel, BorderLayout.CENTER);

        add(mainContainer);

        // Başlangıç ekranını göster
        cardLayout.show(cardPanel, "pnlWelcome");
    }

    private JButton createMenuButton(String text, String cardName) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 45));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setFocusPainted(false);
        btn.addActionListener(e -> cardLayout.show(cardPanel, cardName));
        return btn;
    }

    //ÜYE İŞLEMLERİ

    //üye ekleme işlemi.

    private JPanel createMemberPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Üye Yönetim Sistemi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMemberName = new JTextField(15);
        txtMemberSurname = new JTextField(15);
        txtMemberEmail = new JTextField(15);
        txtMemberPass = new JTextField(15);

        addComponent(panel, new JLabel("Adı:"), 0, 0, gbc);
        addComponent(panel, txtMemberName, 1, 0, gbc);
        addComponent(panel, new JLabel("Soyadı:"), 0, 1, gbc);
        addComponent(panel, txtMemberSurname, 1, 1, gbc);
        addComponent(panel, new JLabel("E-Posta:"), 0, 2, gbc);
        addComponent(panel, txtMemberEmail, 1, 2, gbc);
        addComponent(panel, new JLabel("Şifre:"), 0, 3, gbc);
        addComponent(panel, txtMemberPass, 1, 3, gbc);

        JButton btnAdd = new JButton("Üye Ekle");
        btnAdd.addActionListener(e -> {
            if (memberDAO.addMember(txtMemberName.getText(), txtMemberSurname.getText(), txtMemberEmail.getText(), txtMemberPass.getText()))
                JOptionPane.showMessageDialog(this, "Üye başarıyla eklendi!");
        });
        addComponent(panel, btnAdd, 1, 4, gbc);
        return panel;
    }


    //KİTAP İŞLEMLERİ

    //kitap ekleme işlemi
    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Kitap Envanter Yönetimi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtIsbn = new JTextField(15);
        txtBookName = new JTextField(15);
        txtBookAuthorName = new JTextField(15);
        txtBookAuthorSurname = new JTextField(15);


        selectCategory = new JComboBox<>();
        addComponent(panel, new JLabel("ISBN:"), 0, 0, gbc);
        addComponent(panel, txtIsbn, 1, 0, gbc);

        addComponent(panel, new JLabel("Kitap Adı:"), 0, 1, gbc);
        addComponent(panel, txtBookName, 1, 1, gbc);

        addComponent(panel, new JLabel("Yazar Adı: "), 0, 2, gbc);
        addComponent(panel, txtBookAuthorName, 1, 2, gbc);


        addComponent(panel, new JLabel("Yazar Soyadı: "), 0, 3, gbc);
        addComponent(panel, txtBookAuthorSurname, 1, 3, gbc);

        addComponent(panel, new JLabel("Kategori:"), 0, 4, gbc);
        addComponent(panel, selectCategory, 1, 4, gbc);
        // Kaydet Butonu
        JButton btnAddBook = new JButton("Kitabı Kaydet");
        btnAddBook.addActionListener(e -> {
            String isbn = txtIsbn.getText().trim();
            String authorName = txtBookAuthorName.getText().trim();
            String authorSurname = txtBookAuthorSurname.getText().trim();

            if (bookDAO.isIsbnExists(isbn)) {
                JOptionPane.showMessageDialog(this,
                        "Bu ISBN numarasına sahip bir kitap zaten kayıtlı!",
                        "Mükerrer Kayıt", JOptionPane.WARNING_MESSAGE);
                return;
            }
            authors author = authorDAO.getAuthorByName(authorName, authorSurname);
            if (author == null) {
                JOptionPane.showMessageDialog(this,
                        "Hata: Bu yazar sistemde kayıtlı değil!",
                        "Yazar Bulunamadı", JOptionPane.ERROR_MESSAGE);
                return;
            }
            // Kayıt İşlemi
            try {
                books book = new books(
                        0,
                        isbn,
                        txtBookName.getText().trim(),
                        LocalDate.now(),
                        author.getId(),
                        1,
                        authorName,
                        authorSurname,
                        (String) selectCategory.getSelectedItem()
                );

                boolean sonuc = bookDAO.addBook(book);
                if (sonuc) {
                    JOptionPane.showMessageDialog(null, "Kitap başarıyla kaydedildi!", "Başarılı", JOptionPane.INFORMATION_MESSAGE);
                    txtIsbn.setText("");
                    txtBookName.setText("");
                    txtBookAuthorName.setText("");
                    txtBookAuthorSurname.setText("");
                    selectCategory.setSelectedIndex(0);

                    txtIsbn.getParent().revalidate();
                    txtIsbn.getParent().repaint();

                    txtIsbn.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(this, "Kitap kaydedilemedi. Lütfen veritabanınızı kontrol edin.", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Beklenmeyen bir hata oluştu: " + ex.getMessage(), "Hata", JOptionPane.ERROR_MESSAGE);
            }
        });
        addComponent(panel, btnAddBook, 1, 5, gbc);


        //kitap arama işlemi.

        JButton btnSearch = new JButton("Kitap Ara");
        btnSearch.setBackground(new Color(116, 185, 255));
        btnSearch.addActionListener(e -> {
            String searchTitle = JOptionPane.showInputDialog(this, "Kitap adı girin:");
            if (searchTitle != null && !searchTitle.trim().isEmpty()) {
                books foundBook = bookDAO.getBookByName(searchTitle);
                if (foundBook != null) {
                    txtIsbn.setText(foundBook.getIsbn());
                    txtBookName.setText(foundBook.getBook_name());
                    txtBookAuthorName.setText(foundBook.getAuthorName());
                    txtBookAuthorSurname.setText(foundBook.getAuthorSurname());
                    System.out.println("VERİTABANINDAN GELEN KATEGORİ: " + foundBook.getCategory_name());
                    if (selectCategory != null) {
                        selectCategory.setSelectedItem(foundBook.getCategory_name());
                    }
                    JOptionPane.showMessageDialog(this, "Kitap bulundu ve kutucuklara dolduruldu.");
                } else {
                    JOptionPane.showMessageDialog(this, "Kitap bulunamadı!");
                }
            }
        });
        addComponent(panel, btnSearch, 0, 5, gbc);

        //kitap silme işlemi
        JButton btnDelete = new JButton("Kitap Sil");
        btnDelete.setBackground(new Color(116, 185, 255));
        btnDelete.addActionListener(e -> {
            String bookName = txtBookName.getText().trim();

            if (bookName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek kitabı önce aratın!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    bookName + " isimli kitabı silmek istediğinize emin misiniz?",
                    "Silme Onayı", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                // Kitabı ismine göre çekiyoruz
                books b = new BookDAO().getBookByName(bookName);

                if (b != null) {
                    boolean success = new BookDAO().deleteBook(b.getId());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Kitap başarıyla silindi!");
                        txtIsbn.setText("");
                        txtBookName.setText("");
                        txtAuthorName.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Kitap silinemedi! Bu kitap birisine ödünç verilmiş veya bir cezaya konu olmuş olabilir (Foreign Key engellemesi).", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Silinmek istenen kitap veritabanında bulunamadı! Lütfen aradığınız ismi tam ve doğru yazdığınızdan emin olun.", "Bulunamadı", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        addComponent(panel, btnDelete, 2, 5, gbc);


        //kitap düzenleme işlemi

        JButton btnupdateBook = new JButton("Kitap Güncelle");
        btnupdateBook.setBackground(new Color(116, 185, 255));
        btnupdateBook.addActionListener(e -> {
            String searchTitle = JOptionPane.showInputDialog(this, "Düzenlenecek Kitap Adını Girin:");

            if (searchTitle != null && !searchTitle.trim().isEmpty()) {
                books exBook = bookDAO.getBookByName(searchTitle.trim());
                if (exBook != null) {

                    JTextField txtIsbnEdit = new JTextField(exBook.getIsbn());
                    JTextField txtNameEdit = new JTextField(exBook.getBook_name());
                    JTextField txtAuthNameEdit = new JTextField(exBook.getAuthorName());
                    JTextField txtAuthSurnameEdit = new JTextField(exBook.getAuthorSurname());
                    JComboBox<String> comboCatEdit = new JComboBox<>();
                    comboCatEdit.setSelectedItem(exBook.getCategory_name());

                    Object[] message = {
                            "ISBN:", txtIsbnEdit,
                            "Kitap Adı:", txtNameEdit,
                            "Yazar Adı:", txtAuthNameEdit,
                            "Yazar Soyadı:", txtAuthSurnameEdit,
                            "Kategori:", comboCatEdit
                    };

                    int option = JOptionPane.showConfirmDialog(this, message, "Kitap Bilgilerini Güncelle", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {
                            authors newAuthor = authorDAO.getAuthorByName(txtAuthNameEdit.getText().trim(), txtAuthSurnameEdit.getText().trim());

                            if (newAuthor == null) {
                                JOptionPane.showMessageDialog(this, "Hata: Girdiğiniz yazar sistemde kayıtlı değil! Önce yazarı ekleyin.");
                                return;
                            }
                            boolean success = bookDAO.updateBook(
                                    exBook.getId(),
                                    txtIsbnEdit.getText(),
                                    txtNameEdit.getText(),
                                    newAuthor.getId(),
                                    1
                            );

                            if (success) {
                                JOptionPane.showMessageDialog(this, "Kitap başarıyla güncellendi!");
                            } else {
                                JOptionPane.showMessageDialog(this, "Güncelleme başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Teknik bir hata oluştu: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Kitap bulunamadı!");
                }
            }
        });
        addComponent(panel, btnupdateBook, 3, 5, gbc);
        return panel;
    }

    private JPanel createCategoryPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Kategori Yönetimi"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        txtCatNameIn = new JTextField(15);
        txtCategoryDescrption = new JTextField(15);

        addComponent(panel, new JLabel("Kategori Adı:"), 0, 0, gbc);
        addComponent(panel, txtCatNameIn, 1, 0, gbc);

        addComponent(panel, new JLabel("Kategori Açıklaması:"), 0, 1, gbc);
        addComponent(panel, txtCategoryDescrption, 1, 1, gbc);

        //kategori ekleme

        JButton btnAddCategory = new JButton("Kategoriyi Kaydet");
        btnAddCategory.addActionListener(e -> {
            try {
                String category_name = txtCatNameIn.getText();
                String category_description = txtCategoryDescrption.getText();

                if (categoryDAO.addCategory(category_name , category_description)) {
                    JOptionPane.showMessageDialog(this, "Kategori başarıyla kaydedildi.");
                    txtCatNameIn.setText("");
                    txtCategoryDescrption.setText("");

                    if (selectCategory != null) {
                        selectCategory.addItem(category_name);
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "Kategori kaydedilemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata oluştu: " + ex.getMessage());
            }

        });

        addComponent(panel, btnAddCategory, 1, 2, gbc);

        //kategori arama
        JButton btnSearchCategory = new JButton("Kategori Ara");
        btnSearchCategory.setBackground(new Color(116, 185, 255));
        btnSearchCategory.addActionListener(e -> {
            String searchTitle = JOptionPane.showInputDialog(this, "Kategori adı girin:");

            if (searchTitle != null && !searchTitle.trim().isEmpty()) {
                try {
                    String[] parts = searchTitle.trim().split(" ");

                    String name = parts[0];
                    String surname = parts.length > 1 ? parts[1] : " ";

                    categories c = new CategoryDAO().getCategoryByName(name);

                    if (c != null) {
                        JOptionPane.showMessageDialog(this, "Kategori bulundu ve kutucuklara dolduruldu.");
                       txtCatNameIn.setText(c.getCategory_name());
                       txtCategoryDescrption.setText(c.getDescription());

                    } else {
                        JOptionPane.showMessageDialog(this, "Kategori bulunamadı!");
                    }
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());

                }
            }
        });
        addComponent(panel, btnSearchCategory, 0, 2, gbc);

        //kategori silme.

        JButton btndeleteCategory = new JButton("Kategori Sil");
        btndeleteCategory.setBackground(new Color(116, 185, 255));
        btndeleteCategory.addActionListener(e -> {
            String categoryName = txtCatNameIn.getText().trim();
            if (categoryName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek kategoriyi önce aratın!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    categoryName + " isimli kategoriyi silmek istediğinize emin misiniz?",
                    "Silme Onayı", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                categories c = new CategoryDAO().getCategoryByName(categoryName);
                if (c != null) {
                    boolean success = new CategoryDAO().deleteCategory(c.getId());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Kategori başarıyla silindi!");
                        txtCatNameIn.setText("");
                        txtCategoryDescrption.setText("");

                        if (selectCategory != null) {
                            selectCategory.removeItem(categoryName);
                        }
                    }

                    else {
                        JOptionPane.showMessageDialog(this,
                                "Kategori silinemedi!\nBunun sebebi sistemde hala bu kategoriye kaydedilmiş kitap(lar) olmasıdır.\nLütfen önce bu kategorideki kitapları silin veya kategorilerini başka bir şeyle değiştirin.",
                                "İşlem Engellendi", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Silinmek istenen kategori veritabanında bulunamadı! Lütfen aradığınız ismi tam ve doğru yazdığınızdan emin olun.", "Bulunamadı", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        addComponent(panel, btndeleteCategory, 2, 2, gbc);

        //kategori düzenleme
        JButton btnupdateCategory = new JButton("Kategori Güncelle");
        btnupdateCategory.setBackground(new Color(116, 185, 255));
        btnupdateCategory.addActionListener(e -> {
            String searchTitle = JOptionPane.showInputDialog(this, "Düzenlenecek Kategorinin Adını Girin:");

            if (searchTitle != null && !searchTitle.trim().isEmpty()) {
                categories exCategory = new CategoryDAO().getCategoryByName(searchTitle);
                if (exCategory != null) {

                    JTextField txtCategory_name = new JTextField(exCategory.getCategory_name());
                    JTextField txtDescription = new JTextField(exCategory.getDescription());

                    Object[] message = {
                            "Kategori Adı:",txtCategory_name,
                            "Açıklama :" , txtDescription
                    };

                    int option = JOptionPane.showConfirmDialog(this, message, "Kategori Bilgilerini Güncelle", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        try {

                            boolean success = new CategoryDAO().updateCategory(

                                    exCategory.getId(),
                                    txtCategory_name.getText(),
                                    txtDescription.getText()
                            );

                            if (success) {
                                JOptionPane.showMessageDialog(this, "Kategori başarıyla güncellendi!");

                                if (selectCategory != null) {
                                    selectCategory.removeItem(searchTitle);
                                    selectCategory.addItem(txtCategory_name.getText().trim());
                                }

                                } else {
                                JOptionPane.showMessageDialog(this, "Güncelleme başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(this, "Teknik bir hata oluştu: " + ex.getMessage());
                        }
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Kategori bulunamadı!");
                }
            }
        });
        addComponent(panel, btnupdateCategory, 3, 2, gbc);
        return panel;


    }

    private JPanel createLoanPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 50));
        panel.setBorder(BorderFactory.createTitledBorder("Ödünç Verme & İade İşlemleri"));
        txtMemberId = new JTextField(10);
        txtBookId = new JTextField(10);
        JButton btnAction = new JButton("İşlemi Tamamla");
        panel.add(new JLabel("Üye ID:"));
        panel.add(txtMemberId);
        panel.add(new JLabel("Kitap ID:"));
        panel.add(txtBookId);
        panel.add(btnAction);
        btnAction.addActionListener(e -> {
            try {
                if (loanDAO.issueLoan(Integer.parseInt(txtMemberId.getText()), Integer.parseInt(txtBookId.getText())))
                    JOptionPane.showMessageDialog(this, "İşlem Başarılı!");
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Lütfen geçerli ID giriniz!"); }
        });
        return panel;
    }

    //YAZAR İŞLEMLERİ

    //yazar ekleme işlemi
    private JPanel createAuthorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Yazar İşlemleri"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtAuthorName = new JTextField(15);
        txtAuthorSurname = new JTextField(15);
        txtBiography = new JTextField(15);

        addComponent(panel, new JLabel("Yazar Adı:"), 0, 0, gbc);
        addComponent(panel, txtAuthorName, 1, 0, gbc);
        addComponent(panel, new JLabel("Yazar Soyadı: "), 0, 1, gbc);
        addComponent(panel, txtAuthorSurname, 1, 1, gbc);
        addComponent(panel, new JLabel("Biyografi:"), 0, 2, gbc);
        addComponent(panel, txtBiography, 1, 2, gbc);

        JButton btnAddAuthor = new JButton("Yazarı Kaydet");
        btnAddAuthor.addActionListener(e -> {
            try {
                String name = txtAuthorName.getText();
                String surname = txtAuthorSurname.getText();
                String biography = txtBiography.getText();

                if (authorDAO.addAuthor(name, surname, biography)) {
                    JOptionPane.showMessageDialog(this, "Yazar başarıyla kaydedildi.");
                    txtAuthorName.setText("");
                    txtAuthorSurname.setText("");
                    txtBiography.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Yazar kaydedilemedi!", "Hata", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata oluştu: " + ex.getMessage());
            }
        });
        addComponent(panel, btnAddAuthor, 1, 3, gbc);

        //Yazar arama işlemi.

        JButton btnSearchAuthor = new JButton("Yazar Ara");
        btnSearchAuthor.setBackground(new Color(116, 185, 255));
        btnSearchAuthor.addActionListener(e -> {
            String searchTitle = JOptionPane.showInputDialog(this, "Yazar adı girin:");

            if (searchTitle != null && !searchTitle.trim().isEmpty()) {
                try {
                    String[] parts = searchTitle.trim().split(" ");

                    String name = parts[0];
                    String surname = parts.length > 1 ? parts[1] : " ";

                    authors a = new AuthorDAO().getAuthorByName(name, surname);

                    if (a != null) {
                        JOptionPane.showMessageDialog(this, "Yazar bulundu ve kutucuklara dolduruldu.");
                        txtAuthorName.setText(a.getAuthor_name());
                        txtAuthorSurname.setText(a.getAuthor_surname());
                        txtBiography.setText(a.getBiography());
                    } else {
                        JOptionPane.showMessageDialog(this, "Yazar bulunamadı!");
                    }
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());

                }
            }
        });
        addComponent(panel, btnSearchAuthor, 0, 3, gbc);

        //Yazar silme işlemi.

        JButton btnDeleteAuthor = new JButton("Yazar Sil");
        btnDeleteAuthor.setBackground(new Color(116,185,255));

        btnDeleteAuthor.addActionListener(e ->{

            String name = txtAuthorName.getText().trim();
            String surname = txtAuthorSurname.getText().trim();

            if (name.isEmpty() || surname.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek yazarı önce aratın!");
                return;
            }

            String fullname = name + " " + surname;
            int confirm = JOptionPane.showConfirmDialog(this,
                    fullname + " isimli yazarı silmek istediğinize emin misiniz?",
                    "Silme Onayı", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                authors a = new AuthorDAO().getAuthorByName(txtAuthorName.getText(), txtAuthorSurname.getText());
                if (a != null) {
                    boolean success = new AuthorDAO().DeleteAuthor(a.getAuthor_name());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Yazar başarıyla silindi!");
                        txtAuthorName.setText("");
                        txtAuthorSurname.setText("");
                        txtBiography.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Yazar silinemedi!");
                    }
                }
            }
        } );
        addComponent(panel, btnDeleteAuthor, 2, 3, gbc);
        return panel;
    }

    private void addComponent(JPanel p, Component c, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x; gbc.gridy = y;
        p.add(c, gbc);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) { }
        SwingUtilities.invokeLater(() -> new LibrarySystemUI("Yönetici").setVisible(true));
    }
}