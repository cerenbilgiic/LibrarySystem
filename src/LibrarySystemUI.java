import javax.swing.Timer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.LocalDate;


public class LibrarySystemUI extends JFrame {


    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JTextField txtMemberName, txtMemberSurname, txtMemberTC;
    private JTextField txtIsbn, txtBookName, txtAuthorName, txtAuthorSurname,txtBiography,txtPublishier;
    private JTextField txtMemberId, txtBookId,txtCategoryId, txtAuthorId, txtCatNameIn , txtStock;
    private JButton btnSearchBook;
    private JComboBox<String> selectCategory;
    private JTextField txtEmployeeName, txtEmployeeSurname , txtEmployeeTC, txtEmployeePass,txtEmployeeId;
    private JLabel lblClock;

    JTextField txtBorrowDate = new JTextField(10);
    JTextField txtDueDate = new JTextField(10);
    JTextField txtReturnDate = new JTextField(10);

    JTextField txtBookAuthorName;
    JTextField txtBookAuthorSurname;

    private JLabel lblTotalBooks;
    private JLabel lblTotalAuthors;
    private JLabel lblTotalMembers;
    private JLabel lblTotalEmployees;

    private LoanDAO loanDAO = new LoanDAO();
    private MemberDAO memberDAO = new MemberDAO();
    private BookDAO bookDAO = new BookDAO();
    private AuthorDAO authorDAO = new AuthorDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    private String loggedTC = "";

    public void startClock() {
        Timer timer = new Timer(1000, e -> {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();

            lblClock.setText( dtf.format(now));
        });

        // Zamanlayıcıyı başlatıyoruz
        timer.start();
    }

    public LibrarySystemUI(String name) {
        this.loggedTC = name;
        initUI();
    }
    public LibrarySystemUI() {
        initUI();
    }

    private void initUI() {
        lblClock = new JLabel("Saat yükleniyor...");
        lblClock.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 14));
        lblClock.setHorizontalAlignment(SwingConstants.RIGHT);

        setTitle("Kütüphane Otomasyon Sistemi");
        setSize(1100, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        cardPanel.setBackground(Color.WHITE);

        JPanel pnlWelcome = new JPanel(new BorderLayout(20, 20));
        pnlWelcome.setBackground(new Color(245, 245, 250));
        pnlWelcome.setBorder(new EmptyBorder(40, 40, 40, 40));

        // hoşgeldin mesajı
        JLabel lblWelcome = new JLabel("<html><div style='text-align: center; border-bottom: 2px solid #0984e3; padding-bottom: 10px;'>"
                + "<h1 style='color: #2d3436; font-family: Segoe UI, sans-serif; margin-bottom: 5px;'>KÜTÜPHANE YÖNETİM SİSTEMİNE HOŞ GELDİNİZ</h1>"
                + "<h2 style='color: #0984e3; font-family: Segoe UI, sans-serif;'>Giriş Yapan: " + loggedTC.toUpperCase() + "</h2>"
                + "</div></html>", SwingConstants.CENTER);
        pnlWelcome.add(lblWelcome, BorderLayout.NORTH);

        // güncel veri paneli
        JPanel statsPanel = new JPanel(new GridLayout(2, 2, 25, 25));
        statsPanel.setOpaque(false);

        statsPanel.add(createStatCard("Toplam Kitap", String.valueOf(bookDAO.getTotalBookCount()), "📚", new Color(52, 152, 219)));
        statsPanel.add(createStatCard("Toplam Yazar", String.valueOf(authorDAO.getTotalAuthorCount()), "✍️", new Color(155, 89, 182)));
        statsPanel.add(createStatCard("Toplam Üye", String.valueOf(memberDAO.getTotalMemberCount()), "👥", new Color(46, 204, 113)));
        statsPanel.add(createStatCard("Toplam Çalışan", String.valueOf(employeeDAO.getTotalEmployeeCount()), "👨‍💼", new Color(230, 126, 34)));

        pnlWelcome.add(statsPanel, BorderLayout.CENTER);

        JLabel lblHint = new JLabel("Lütfen sol menüden yapmak istediğiniz işlemi seçiniz.", SwingConstants.CENTER);
        lblHint.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblHint.setForeground(new Color(108, 117, 125));
        pnlWelcome.add(lblHint, BorderLayout.SOUTH);

        cardPanel.add(pnlWelcome, "pnlWelcome");
        cardPanel.add(createMemberPanel(), "pnlMember");
        cardPanel.add(createBookPanel(), "pnlBook");
        cardPanel.add(createLoanPanel(), "pnlLoan");
        cardPanel.add(createAuthorPanel(), "pnlAuthor");
        cardPanel.add(createEmployeePanel(),"pnlEmployee");

        //sidebar
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

        sidebar.add(createMenuButton("Çalışan İşlemleri" , "pnlEmployee"));
        sidebar.add(Box.createRigidArea(new Dimension(0,10)));

        sidebar.add(Box.createVerticalGlue()); //daha orantılı boşluklar bırakmayı sağlar.

        JButton btnExit = new JButton("GÜVENLİ ÇIKIŞ");
        btnExit.setBackground(new Color(211, 47, 47));
        btnExit.setForeground(Color.BLACK);
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.addActionListener(e -> System.exit(0));
        sidebar.add(btnExit);

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.add(sidebar, BorderLayout.WEST);
        mainContainer.add(cardPanel, BorderLayout.CENTER);

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(new EmptyBorder(5, 10, 5, 20));
        headerPanel.add(lblClock);

        mainContainer.add(headerPanel, BorderLayout.NORTH);

        add(mainContainer);

        cardLayout.show(cardPanel, "pnlWelcome");

        startClock();
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
        panel.setBorder(BorderFactory.createTitledBorder("ÜYE YÖNETİM SİSTEMİ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtMemberName = new JTextField(15);
        txtMemberSurname = new JTextField(15);
        txtMemberTC = new JTextField(15);

        addComponent(panel, new JLabel("Adı:"), 0, 0, gbc);
        addComponent(panel, txtMemberName, 1, 0, gbc);

        addComponent(panel, new JLabel("Soyadı:"), 0, 1, gbc);
        addComponent(panel, txtMemberSurname, 1, 1, gbc);

        addComponent(panel, new JLabel("Üye TC: "), 0, 2, gbc);
        addComponent(panel, txtMemberTC, 1, 2, gbc);

        JButton btnAdd = new JButton("Üye Ekle");
        btnAdd.setBackground(new Color(116, 185, 255));
        btnAdd.addActionListener(e -> {
            String tc= txtMemberTC.getText().trim();
            String memberName = txtMemberName.getText().trim();
            String memberSurname= txtMemberSurname.getText().trim();

            if (memberName.isEmpty()) {JOptionPane.showMessageDialog(this, "Üye Adı boş olamaz!");return;}
            if (memberSurname.isEmpty()) {JOptionPane.showMessageDialog(this, "Üye Soyadı boş olamaz!");return;}
            if (tc.isEmpty()) {JOptionPane.showMessageDialog(this, "TC Kimlik No boş olamaz!");return;}
            if (tc.length() != 11) {JOptionPane.showMessageDialog(this, "Hata: TC Kimlik No tam olarak 11 karakterden (haneden) oluşmalıdır!", "Geçersiz TC", JOptionPane.WARNING_MESSAGE);return;}
            if (memberDAO.isTcExists(tc)) {
                JOptionPane.showMessageDialog(this,
                        "Bu TC Kimlik No zaten kayıtlı!",
                        "Hata",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (memberDAO.addMember(txtMemberName.getText(), txtMemberSurname.getText(), txtMemberTC.getText()))
                JOptionPane.showMessageDialog(this, "Üye başarıyla eklendi!");

            refreshDashboardStats();

           txtMemberName.setText("");
           txtMemberSurname.setText("");
           txtMemberTC.setText("");
        });
        addComponent(panel, btnAdd, 1, 4, gbc);

        //Üye arama işlemi.
        JButton btnSearchMember = new JButton("Üye Ara");
        btnSearchMember.setBackground(new Color(116, 185, 255));
        btnSearchMember.addActionListener(e -> {
            String searchTC = JOptionPane.showInputDialog(this, "Üyenin TC Kimlik Numarasını Giriniz:");
            if (searchTC != null && !searchTC.trim().isEmpty()) {
            try {
            member member = new MemberDAO().getMemberByTC(searchTC.trim());
               if (member != null) {
                 JOptionPane.showMessageDialog(this, "Üye bulundu ve kutucuklara dolduruldu.");
                 txtMemberName.setText(member.getFirst_name());
                 txtMemberSurname.setText(member.getLast_name());
                 txtMemberTC.setText(member.getTC());
               } else {JOptionPane.showMessageDialog(this, "Böyle bir üye kaydı bulunamadı!");}
            }
            catch (Exception ex){
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());}
            }
        });
        addComponent(panel, btnSearchMember, 0, 4, gbc);

        //Üye silme işlemi.
        JButton btnDeleteMember = new JButton("Üye Sil");
        btnDeleteMember.setBackground(new Color(116,185,255));

        btnDeleteMember.addActionListener(e ->{
            String tc = txtMemberTC.getText().trim();
            if (tc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek üyeyi önce aratın!");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    txtMemberName.getText() + "  adlı üyeyi silmek istediğinize emin misiniz?",
                    "Silme Onayı", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                member member = new MemberDAO().getMemberByTC(tc);
                if (member != null) {
                     boolean success = new MemberDAO().DeleteMember(member.getId());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Üye başarıyla silindi!");

                        refreshDashboardStats();

                        txtMemberName.setText("");
                        txtMemberSurname.setText("");
                        txtMemberTC.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Üye silinemedi!");
                    }
                }
            }
        } );
        addComponent(panel, btnDeleteMember, 2, 4, gbc);

        //Üye düzenleme işlemi
        JButton btnupdateMember = new JButton("Üye Güncelle");
        btnupdateMember.setBackground(new Color(116, 185, 255));
        btnupdateMember.addActionListener(e -> {
        String searchTC = JOptionPane.showInputDialog(this, "Düzenlenecek Üyenin TC Kimlik Numarasını Girin:");

        if (searchTC != null && !searchTC.trim().isEmpty()) {
        try {
        member exMember = new MemberDAO().getMemberByTC(searchTC.trim());
            if (exMember != null) {
                JTextField editName = new JTextField(exMember.getFirst_name());
                JTextField editSurname = new JTextField(exMember.getLast_name());
                JTextField editTC = new JTextField(exMember.getTC());

                Object[] message = {
                        "Adı :", editName,
                        "Soyadı: ", editSurname,
                        "TC: ", editTC,
                };
        int option = JOptionPane.showConfirmDialog(this, message, "Üye Bilgilerini Güncelle", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
    try {
    boolean success = new MemberDAO().updateMember(
    exMember.getId(),
    txtMemberName.getText(),
    txtMemberSurname.getText(),
    txtMemberTC.getText()
    );

    if (success) {
    JOptionPane.showMessageDialog(this, "Üye başarıyla güncellendi!");
   } else {
   JOptionPane.showMessageDialog(this, "Güncelleme başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
   }
    } catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Teknik bir hata oluştu: " + ex.getMessage());}
    }
    } else {
    JOptionPane.showMessageDialog(this, "Üye bulunamadı!");}
    } catch (Exception ex) {
    JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());}
    }
    });
    addComponent(panel, btnupdateMember, 1, 6, gbc);
    return panel;
    }

    //KİTAP İŞLEMLERİ
    private int getCategoryId(String category) {
        switch (category) {
            case "Roman": return 1;
            case "Masal": return 2;
            case "Biyografi": return 3;
            case "Otobiyografi": return 4;
            case "Yazılım": return 5;
            case "Bilim Kurgu" : return 6;
            case "Fantastik" : return 7;
            case "Suç" : return 8;
            case "Şiir" : return 9;
            case "Tarih" : return 10;
            case "Felsefe" : return 11;
            case "Psikoloji" : return 12;
            case "Din/Teoloji" : return 13;
            case "Çizgi Roman" : return 14;
            case "Eğitim" : return 15;
            case "Romantik": return 16;
            case "Türk Klasikleri" : return 17;
            default: return 0;
        }
    }

    private String getCategoryNameById(int id) {
        switch (id) {
            case 1: return "Roman";
            case 2: return "Masal";
            case 3: return "Biyografi";
            case 4: return "Otobiyografi";
            case 5: return "Yazılım";
            case 6 : return "Bilim Kurgu";
            case 7: return "Fantastik" ;
            case 8 : return "Suç" ;
            case 9: return "Şiir";
            case 10 : return "Tarih";
            case 11 : return "Felsefe" ;
            case 12 : return "Psikoloji";
            case 13 : return "Din/Teoloji" ;
            case 14 : return "Çizgi Roman" ;
            case 15 : return "Eğitim" ;
            case 16 : return "Romantik";
            case 17:return "Türk Klasikleri";
            default: return null;
        }
    }
    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("KİTAP ENVANTER YÖNETİMİ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtIsbn = new JTextField(15);
        txtBookName = new JTextField(15);
        txtBookAuthorName = new JTextField(15);
        txtBookAuthorSurname = new JTextField(15);
        txtStock = new JTextField(15);
        txtPublishier = new JTextField(15);

        int publish_year = LocalDate.now().getYear();
        SpinnerModel yearModel = new SpinnerNumberModel(publish_year, 1800, publish_year + 1, 1);
        JSpinner spinYear = new JSpinner(yearModel);

        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinYear, "#");
        spinYear.setEditor(editor);

        String[] categories = {"Roman", "Masal", "Biyografi", "Otobiyografi", "Yazılım","Bilim Kurgu","Fantastik","Suç","Şiir","Tarih","Felsefe","Psikoloji","Din/Teoloji","Çizgi Roman","Eğitim","Romantik","Türk Klasikleri"};
        selectCategory = new JComboBox<>(categories);


        selectCategory = new JComboBox<>(categories);
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

        addComponent(panel, new JLabel("Kitap Stoğu: "), 0, 5, gbc);
        addComponent(panel, txtStock, 1, 5, gbc);

        addComponent(panel, new JLabel("Yayın Yılı:"), 0, 6, gbc);
        addComponent(panel, spinYear, 1, 6, gbc);

        addComponent(panel,new JLabel("Yayın Evi: "),0,7,gbc);
        addComponent(panel, txtPublishier,1,7,gbc);

        //  kitap ekleme işlemi
        JButton btnAddBook = new JButton("Kitabı Kaydet");
        btnAddBook.setBackground(new Color(116, 185, 255));

        btnAddBook.addActionListener(e -> {

            int publishYear = (int) spinYear.getValue();
            LocalDate pDate = LocalDate.of(publishYear, 1, 1);

            String bookname = txtBookName.getText().trim();
            String isbn = txtIsbn.getText().trim();
            String authorName = txtBookAuthorName.getText().trim();
            String authorSurname = txtBookAuthorSurname.getText().trim();
            String stockInput = txtStock.getText().trim();
            int stockAmount = stockInput.isEmpty() ? 1 : Integer.parseInt(stockInput);
            String publishier = txtPublishier.getText().trim();

            if (bookname.isEmpty()) {JOptionPane.showMessageDialog(this, "Kitap Adı boş olamaz!");return;}
            if (authorName.isEmpty()) {JOptionPane.showMessageDialog(this, "Yazar Adı boş olamaz!");return;}
            if (isbn.isEmpty()) {JOptionPane.showMessageDialog(this, "ISBN boş olamaz!");return;}
            if (isbn.length() != 13) {JOptionPane.showMessageDialog(this, "Hata: ISBN tam olarak 13 karakterden (haneden) oluşmalıdır!", "Geçersiz ISBN", JOptionPane.WARNING_MESSAGE);return;}
            if (bookDAO.isIsbnExists(isbn)) {
                JOptionPane.showMessageDialog(this,
                        "Bu ISBN zaten kayıtlı!",
                        "Hata",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
            authors author = authorDAO.getAuthorByName(authorName, authorSurname);
            if (author == null) {
                JOptionPane.showMessageDialog(this,
                        "Yazar bulunamadı!",
                        "Hata",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            try {
                String categoryName = (String) selectCategory.getSelectedItem();
                int categoryId = getCategoryId(categoryName);
                books book = new books(
                        0,
                        isbn,
                        txtBookName.getText().trim(),
                        pDate,
                        author.getId(),
                        categoryId,
                        authorName,
                        authorSurname,
                        stockAmount,
                        publishier
                );
                boolean sonuc = bookDAO.addBook(book);
                if (sonuc) {
                    JOptionPane.showMessageDialog(this, "Kitap kaydedildi!");

                    refreshDashboardStats();

                    txtIsbn.setText("");
                    txtBookName.setText("");
                    txtBookAuthorName.setText("");
                    txtBookAuthorSurname.setText("");
                    selectCategory.setSelectedIndex(0);
                    txtStock.setText("");
                    txtPublishier.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Kayıt başarısız!");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
            }
        });

        addComponent(panel, btnAddBook, 1, 8, gbc);

    //kitap arama
        JButton btnSearchBook = new JButton("Kitap Ara");
        btnSearchBook.setBackground(new Color(116, 185, 255));
        btnSearchBook.addActionListener(e -> {

            String searchIsbn = JOptionPane.showInputDialog(this, "Aramak istediğiniz kitabın ISBN kodunu girin:");

            if (searchIsbn == null || searchIsbn.trim().isEmpty()) return;

            books foundBook = bookDAO.getBookByIsbn(searchIsbn.trim());

            if (foundBook != null) {

                txtIsbn.setText(foundBook.getIsbn());
                txtBookName.setText(foundBook.getBook_name());
                txtBookAuthorName.setText(foundBook.getAuthorName());
                txtBookAuthorSurname.setText(foundBook.getAuthorSurname());

                selectCategory.setSelectedItem(getCategoryNameById(foundBook.getCategoryId()));
                txtStock.setText(String.valueOf(foundBook.getStock()));
                txtPublishier.setText(foundBook.getPublishier());

                JOptionPane.showMessageDialog(this, "Kitap bulundu!");
            } else {
                JOptionPane.showMessageDialog(this, "Kitap bulunamadı!");
            }
        });
        addComponent(panel, btnSearchBook, 0, 8, gbc);

        //kitap silme işlemi
        JButton btndeleteBook = new JButton("Kitabı Sil");
        btndeleteBook.setBackground(new Color(116, 185, 255));
        btndeleteBook.addActionListener(e -> {

            String bookName = txtBookName.getText().trim();
            String isbn = txtIsbn.getText().trim();

            if (bookName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Önce kitap seç!");
                return;
            }

            books b = bookDAO.getBookByIsbn(isbn);

            if (b != null) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Silmek istediğinize emin misiniz?",
                        "Onay",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {

                    boolean success = bookDAO.deleteBook(b.getId());

                    if (success) {
                        JOptionPane.showMessageDialog(this, "Silindi!");

                        refreshDashboardStats();

                        txtIsbn.setText("");
                        txtBookName.setText("");
                        txtBookAuthorName.setText("");
                        txtBookAuthorSurname.setText("");
                        selectCategory.setSelectedIndex(0);
                        txtStock.setText("");
                        txtPublishier.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Silinemedi!");
                    }
                }
            }
        });
        addComponent(panel, btndeleteBook, 3, 8, gbc);


        //kitap düzenleme işlemi
        JButton btnupdateBook = new JButton("Kitap Güncelle");
        btnupdateBook.setBackground(new Color(116, 185, 255));
        btnupdateBook.addActionListener(e -> {
            String searchTitle = JOptionPane.showInputDialog(this, "Düzenlenecek Kitabın ISBN'sini Girin:");

            if (searchTitle != null && !searchTitle.trim().isEmpty()) {
                books exBook = bookDAO.getBookByIsbn(searchTitle.trim());
                if (exBook != null) {

                    int existingYear = exBook.getPurchase_date().getYear();
                    SpinnerModel yearModelEdit = new SpinnerNumberModel(existingYear, 1800, 2100, 1);
                    JSpinner spinYearEdit = new JSpinner(yearModelEdit);
                    JSpinner.NumberEditor editorEdit = new JSpinner.NumberEditor(spinYearEdit, "#");
                    spinYearEdit.setEditor(editorEdit);
                    JTextField txtIsbnEdit = new JTextField(exBook.getIsbn());
                    JTextField txtNameEdit = new JTextField(exBook.getBook_name());
                    JTextField txtAuthNameEdit = new JTextField(exBook.getAuthorName());
                    JTextField txtAuthSurnameEdit = new JTextField(exBook.getAuthorSurname());
                    JTextField txtPublishierEdit = new JTextField(exBook.getPublishier());

                    String[] categoriesList = {"Roman", "Masal", "Biyografi", "Otobiyografi", "Yazılım" , "Bilim Kurgu", "Fantastik" , "Suç","Şiir","Tarih", "Felsefe", "Psikoloji" ,"Din/Teoloji","Çizgi Roman", "Eğitim","Romantik","Türk Klasikleri"};
                    JComboBox<String> comboCatEdit = new JComboBox<>(categoriesList);
                    comboCatEdit.setSelectedItem(getCategoryNameById(exBook.getCategoryId()));
                    String foundCat = exBook.getCategory_name();
                    if (foundCat != null) {
                        for (int i = 0; i < comboCatEdit.getItemCount(); i++) {
                            if (comboCatEdit.getItemAt(i).toString().equalsIgnoreCase(foundCat.trim())) {
                                comboCatEdit.setSelectedIndex(i);
                                break;
                            }
                        }
                    }
                    JTextField txtStockEdit = new JTextField(String.valueOf(exBook.getStock()));


                    Object[] message = {
                            "ISBN:", txtIsbnEdit,
                            "Kitap Adı:", txtNameEdit,
                            "Yazar Adı:", txtAuthNameEdit,
                            "Yazar Soyadı:", txtAuthSurnameEdit,
                            "Kategori:", comboCatEdit,
                            "Kitap Stoğu:", txtStockEdit,
                            "Yayın Tarihi: ",spinYearEdit,
                            "Yayın Evi: ", txtPublishierEdit
                    };

                    int option = JOptionPane.showConfirmDialog(this, message, "Kitap Bilgilerini Güncelle", JOptionPane.OK_CANCEL_OPTION);
                    if (option == JOptionPane.OK_OPTION) {
                        int updatedYear = (int) spinYearEdit.getValue();
                        String publishierEdit = txtPublishierEdit.getText();
                        try {
                            String updatedIsbn = txtIsbnEdit.getText().trim();
                            if (updatedIsbn.length() != 13) {
                                JOptionPane.showMessageDialog(this, "Hata: ISBN tam olarak 13 karakterden (haneden) oluşmalıdır!", "Geçersiz ISBN", JOptionPane.WARNING_MESSAGE);
                                return;
                            }

                            authors newAuthor = authorDAO.getAuthorByName(txtAuthNameEdit.getText().trim(), txtAuthSurnameEdit.getText().trim());

                            if (newAuthor == null) {
                                JOptionPane.showMessageDialog(this, "Hata: Girdiğiniz kitap sistemde kayıtlı değil! Önce kitabı ekleyin.");
                                return;
                            }
                            int newStockAmount = Integer.parseInt(txtStockEdit.getText().trim());

                            boolean success = bookDAO.updateBook(
                                    exBook.getId(),
                                    updatedIsbn,
                                    txtNameEdit.getText(),
                                    newAuthor.getId(),
                                    getCategoryId((String)comboCatEdit.getSelectedItem()),
                                    newStockAmount,
                                    publishierEdit

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
        addComponent(panel, btnupdateBook, 1, 9, gbc);
        return panel;
    }

    private JPanel createLoanPanel() {
        // panelleri dikey olarak ikiye böler
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 10));
        panel.setBackground(Color.WHITE);

        // ödünç verme işlemi
        JPanel borrowPanel = new JPanel(new GridBagLayout());
        borrowPanel.setBorder(BorderFactory.createTitledBorder("KİTAP ÖDÜNÇ VERME"));
        GridBagConstraints gbcB = new GridBagConstraints();
        gbcB.insets = new Insets(5, 5, 5, 5);
        gbcB.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtBorrow_MemberTC = new JTextField(15);
        JTextField txtBorrow_IsbnInput = new JTextField(15);
        JTextField txtBorrow_BorrowDate = new JTextField(LocalDate.now().toString());
        JTextField txtBorrow_DueDate = new JTextField(LocalDate.now().plusDays(15).toString());
        txtBorrow_BorrowDate.setEditable(false);
        txtBorrow_DueDate.setEditable(false);

        addComponent(borrowPanel, new JLabel("Üye TC:"), 0, 0, gbcB);
        addComponent(borrowPanel, txtBorrow_MemberTC, 1, 0, gbcB)
        ;
        addComponent(borrowPanel, new JLabel("Kitap ISBN:"), 0, 1, gbcB);
        addComponent(borrowPanel, txtBorrow_IsbnInput, 1, 1, gbcB);

        addComponent(borrowPanel, new JLabel("Ödünç Tarihi:"), 0, 2, gbcB);
        addComponent(borrowPanel, txtBorrow_BorrowDate, 1, 2, gbcB);

        addComponent(borrowPanel, new JLabel("Teslim Tarihi:"), 0, 3, gbcB);
        addComponent(borrowPanel, txtBorrow_DueDate, 1, 3, gbcB);

        JButton btnBorrow = new JButton("Ödünç İşlemini Tamamla");
        btnBorrow.setBackground(new Color(116, 185, 255));
        btnBorrow.setBackground(new Color(116, 185, 255));
        btnBorrow.addActionListener(e -> {
            try {
                String tc = txtBorrow_MemberTC.getText().trim();
                String Isbn = txtBorrow_IsbnInput.getText().trim();
                if (tc.isEmpty() || Isbn.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Lütfen TC Kimlik No ve Kitap ISBN giriniz!");
                    return;
                }

                member m = memberDAO.getMemberByTC(tc);
                if (m == null) {
                    JOptionPane.showMessageDialog(this, "Üye bulunamadı (Geçersiz TC Kimlik Numarası)!");
                    return;
                }

                books b = bookDAO.getBookByIsbn(Isbn);
                if (b == null) {
                    JOptionPane.showMessageDialog(this, "Kitap bulunamadı (Geçersiz ISBN)!");
                    return;
                }

                // Üyenin kitap alma hakkını kontrol et
                if (m.getMaxAllowedbooks() <= 0) {
                    JOptionPane.showMessageDialog(this, "Hata: Üyenin kitap alma hakkı dolmuştur! (Maksimum 5 kitap)", "Limit Dolu", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (loanDAO.issueLoan(m.getId(), b.getId())) {
                    JOptionPane.showMessageDialog(this, "Kitap başarıyla ödünç verildi!");
                    txtBorrow_MemberTC.setText("");
                    txtBorrow_IsbnInput.setText("");

                }
                else {
                    JOptionPane.showMessageDialog(this, "İşlem başarısız! Kitap stokta olmayabilir.");
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Bir hata oluştu: " + ex.getMessage()); }
        });
        addComponent(borrowPanel, btnBorrow, 1, 4, gbcB);


        //iade alma işlemi.
        JPanel returnPanel = new JPanel(new GridBagLayout());
        returnPanel.setBorder(BorderFactory.createTitledBorder("KİTAP İADE ALMA"));
        GridBagConstraints gbcR = new GridBagConstraints();
        gbcR.insets = new Insets(5, 5, 5, 5);
        gbcR.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtR_MemberTC = new JTextField(15);
        JTextField txtR_IsbnInput = new JTextField(15);
        JTextField txtR_ReturnDate = new JTextField(LocalDate.now().toString());
        txtR_ReturnDate.setEditable(false);

        addComponent(returnPanel, new JLabel("Üye TC:"), 0, 0, gbcR);
        addComponent(returnPanel, txtR_MemberTC, 1, 0, gbcR);

        addComponent(returnPanel, new JLabel("Kitap ISBN:"), 0, 1, gbcR);
        addComponent(returnPanel, txtR_IsbnInput, 1, 1, gbcR);

        addComponent(returnPanel, new JLabel("İade Alınan Tarih:"), 0, 2, gbcR);
        addComponent(returnPanel, txtR_ReturnDate, 1, 2, gbcR);

        JButton btnReturn = new JButton("İade İşlemini Tamamla");
        btnReturn.addActionListener(e -> {
            try {
                String tc = txtR_MemberTC.getText().trim();
                String bIsbn = txtR_IsbnInput.getText().trim();
                if (tc.isEmpty() || bIsbn.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Lütfen TC Kimlik ve Kitap ISBN giriniz!");
                    return;
                }

                member m = memberDAO.getMemberByTC(tc);
                if (m == null) {
                    JOptionPane.showMessageDialog(this, "Üye bulunamadı (Geçersiz TC Kimlik Numarası)!");
                    return;
                }

                books b = bookDAO.getBookByIsbn(bIsbn);
                if (b == null) {
                    JOptionPane.showMessageDialog(this, "Kitap bulunamadı (Geçersiz ISBN)!");
                    return;
                }

                double pendingFine = loanDAO.getPendingFineAmount(m.getId(), b.getId());

                if (pendingFine == -1.0) {
                    JOptionPane.showMessageDialog(this, "Aktif bir ödünç kaydı bulunamadı veya hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (pendingFine > 0) {
                    Object[] options = {"Ödeme Yapıldı İade Al", "Tamam"};
                    int secim = JOptionPane.showOptionDialog(this,
                            "Bu kitap için son teslim tarihi aşılmış!\nGecikme Cezası: " + pendingFine + " TL",
                            "Ceza Uyarısı",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null,
                            options,
                            options[0]);

                    if (secim == 0) { // Ödeme yapıldı iade al tıklandı
                        if (loanDAO.processReturn(m.getId(), b.getId(), pendingFine)) {
                            JOptionPane.showMessageDialog(this, "İade işlemi ve ceza tahsilatı başarıyla tamamlandı!");
                            txtR_MemberTC.setText("");
                            txtR_IsbnInput.setText("");
                        } else {
                            JOptionPane.showMessageDialog(this, "İade sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                    }
                } else {
                    // Ceza yoksa doğrudan iade edilir
                    if (loanDAO.processReturn(m.getId(), b.getId(), 0.0)) {
                        JOptionPane.showMessageDialog(this, "İade işlemi başarıyla tamamlandı!\nHerhangi bir ceza bulunmuyor.");
                        txtR_MemberTC.setText("");
                        txtR_IsbnInput.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "İade sırasında bir hata oluştu.", "Hata", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) { JOptionPane.showMessageDialog(this, "Bir hata oluştu: " + ex.getMessage()); }
        });
        addComponent(returnPanel, btnReturn, 1, 3, gbcR);

        panel.add(borrowPanel);
        panel.add(returnPanel);

        return panel;
    }


    //YAZAR İŞLEMLERİ

    //yazar ekleme işlemi
    private JPanel createAuthorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("YAZAR İŞLEMLERİ"));
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
        btnAddAuthor.setBackground(new Color(116, 185, 255));
        btnAddAuthor.addActionListener(e -> {

            String authorName = txtAuthorName.getText().trim();
            String authorSurname = txtAuthorSurname.getText().trim();
            if (authorDAO.isAuthorNameExists(authorName)) {
                JOptionPane.showMessageDialog(this,
                        "Bu Yazar zaten kayıtlı!",
                        "Hata",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (authorName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Yazar Adı boş olamaz!");
                return;
            }

            try {
                String name = txtAuthorName.getText();
                String surname = txtAuthorSurname.getText();
                String biography = txtBiography.getText();

                if (authorDAO.addAuthor(name, surname, biography)) {
                    JOptionPane.showMessageDialog(this, "Yazar başarıyla kaydedildi.");
                    refreshDashboardStats();
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
                        refreshDashboardStats();
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

        //Yazar düzenleme işlemi
        JButton btnupdateAuthor = new JButton("Yazar Güncelle");
        btnupdateAuthor.setBackground(new Color(116, 185, 255));
        btnupdateAuthor.addActionListener(e -> {
            String searchTitle = JOptionPane.showInputDialog(this, "Düzenlenecek Yazar Adını Girin:");

            if (searchTitle != null && !searchTitle.trim().isEmpty()) {
            try {
            String[] parts = searchTitle.trim().split(" ");
            String name= parts[0];
            String surname = parts.length > 1 ? parts[1] : " ";
            authors exAuthor = new AuthorDAO().getAuthorByName(name,surname);

            if (exAuthor != null) {
                JTextField txtAuthorName = new JTextField(exAuthor.getAuthor_name());
                JTextField txtAuthorSurname  = new JTextField(exAuthor.getAuthor_surname());
                JTextField txtBiography = new JTextField(exAuthor.getBiography());
                Object[] message = {
                        "Adı :", txtAuthorName,
                        "Soyadı: ", txtAuthorSurname ,
                        "Biyografi: ", txtBiography
                        };

                int option = JOptionPane.showConfirmDialog(this, message, "Yazar Bilgilerini Güncelle", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    try {
                        boolean success = new AuthorDAO().updateAuthor(
                                exAuthor.getId(),
                                txtAuthorName.getText(),
                                txtAuthorSurname.getText(),
                                txtBiography.getText()
                        );
                        if (success) {
                            JOptionPane.showMessageDialog(this, "Yazar başarıyla güncellendi!");
                        } else {
                            JOptionPane.showMessageDialog(this, "Güncelleme başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Teknik bir hata oluştu: " + ex.getMessage());
                    }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Yazar bulunamadı!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
                }
            }
        });
        addComponent(panel, btnupdateAuthor, 1, 4, gbc);
        return panel;
    }

    //ÇALIŞAN İŞLEMLERİ

    //çalışan ekleme işlemi.
    private JPanel createEmployeePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("ÇALIŞAN YÖNETİM SİSTEMİ"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

       txtEmployeeName = new JTextField(15);
        txtEmployeeSurname = new JTextField(15);
        txtEmployeeTC = new JTextField(15);
        txtEmployeePass = new JTextField(15);

        addComponent(panel, new JLabel("Adı:"), 0, 0, gbc);
        addComponent(panel, txtEmployeeName, 1, 0, gbc);
        addComponent(panel, new JLabel("Soyadı:"), 0, 1, gbc);
        addComponent(panel, txtEmployeeSurname, 1, 1, gbc);
        addComponent(panel, new JLabel("Çalışan TC:"), 0, 2, gbc);
        addComponent(panel, txtEmployeeTC, 1, 2, gbc);
        addComponent(panel, new JLabel("Şifre:"), 0, 3, gbc);
        addComponent(panel, txtEmployeePass, 1, 3, gbc);

        JButton btnAddEmployee = new JButton("Çalışan Ekle");
        btnAddEmployee.setBackground(new Color(116, 185, 255));
        btnAddEmployee.addActionListener(e -> {
            if (employeeDAO.addEmployee(txtEmployeeName.getText(), txtEmployeeSurname.getText(), txtEmployeeTC.getText(), txtEmployeePass.getText()))
                JOptionPane.showMessageDialog(this, "Çalışan başarıyla eklendi!");
            refreshDashboardStats();

            txtEmployeeName.setText("");
            txtEmployeeSurname.setText("");
            txtEmployeeTC.setText("");
            txtEmployeePass.setText("");

            String employeeName = txtEmployeeName.getText().trim();
            String employeeSurname = txtEmployeeSurname.getText().trim();
            String employeetc = txtEmployeeTC.getText().trim();
            String employeePassword = txtEmployeePass.getText().trim();
            if (employeeName.isEmpty()) {JOptionPane.showMessageDialog(this, "Çalışan Adı boş olamaz!");return;}
            if (employeeSurname.isEmpty()) {JOptionPane.showMessageDialog(this, "Çalışan Soyadı boş olamaz!");return;}
            if (employeetc.isEmpty()) {JOptionPane.showMessageDialog(this, "TC Kimlik No boş olamaz!");return;}
            if (employeetc.length() != 11) {JOptionPane.showMessageDialog(this, "Hata: TC Kimlik No tam olarak 11 karakterden (haneden) oluşmalıdır!", "Geçersiz TC", JOptionPane.WARNING_MESSAGE);return;}
            if (employeePassword.isEmpty()) {JOptionPane.showMessageDialog(this, "Şifre boş olamaz!");return;}
            if (employeeDAO.isTcExists(employeetc)) {
                JOptionPane.showMessageDialog(this,
                        "Bu TC Kimlik No zaten kayıtlı!",
                        "Hata",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }
        });
        addComponent(panel, btnAddEmployee, 1, 4, gbc);

        //Çalışan arama işlemi.

        JButton btnSearchEmployee = new JButton("Çalışan Ara");
        btnSearchEmployee.setBackground(new Color(116, 185, 255));
        btnSearchEmployee.addActionListener(e -> {
            String searchTC = JOptionPane.showInputDialog(this, "Çalışanın TC Kimlik Numarasını Giriniz:");
            if (searchTC != null && !searchTC.trim().isEmpty()) {
                try {
                    employees employees = new EmployeeDAO().getEmployeeByTC(searchTC.trim());
                    if (employees != null) {
                        JOptionPane.showMessageDialog(this, "Çalışan bulundu ve kutucuklara dolduruldu.");
                        txtEmployeeName.setText(employees.getFirst_name());
                        txtEmployeeSurname.setText(employees.getLast_name());
                        txtEmployeeTC.setText(employees.getTC());
                        txtEmployeePass.setText(employees.getPassword());
                    } else {
                        JOptionPane.showMessageDialog(this, "Böyle bir çalışan kaydı bulunamadı!");
                    }
                }
                catch (Exception ex){
                    JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
                }
            }
        });
        addComponent(panel, btnSearchEmployee, 0, 4, gbc);

        //Çalışan silme işlemi.

        JButton btnDeleteEmployee = new JButton("Çalışanı Sil");
        btnDeleteEmployee.setBackground(new Color(116,185,255));

        btnDeleteEmployee.addActionListener(e ->{

            String tc = txtEmployeeTC.getText().trim();

            if (tc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Lütfen silinecek çalışanı önce aratın!");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    txtEmployeeName .getText()+ "  adlı çalışanı silmek istediğinize emin misiniz?",
                    "Silme Onayı", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                employees employees = new EmployeeDAO().getEmployeeByTC(tc);
                if (employees != null) {
                    boolean success = new EmployeeDAO().DeleteEmployee(employees.getId());
                    if (success) {
                        JOptionPane.showMessageDialog(this, "Çalışan başarıyla silindi!");
                        refreshDashboardStats();
                      txtEmployeeName.setText("");
                        txtEmployeeSurname.setText("");
                        txtEmployeeTC.setText("");
                        txtEmployeePass.setText("");
                    } else {
                        JOptionPane.showMessageDialog(this, "Çalışan silinemedi!");
                    }
                }
            }
        } );
        addComponent(panel, btnDeleteEmployee, 2, 4, gbc);
        //çalışan düzenleme işlemi

        JButton btnupdateEmployee = new JButton("Çalışan Güncelle");
        btnupdateEmployee.setBackground(new Color(116, 185, 255));
        btnupdateEmployee.addActionListener(e -> {
            String searchTC = JOptionPane.showInputDialog(this, "Düzenlenecek Çalışanın TC Kimlik Numarasını Girin:");

            if (searchTC != null && !searchTC.trim().isEmpty()) {
                try {
                    employees exEmployee = new EmployeeDAO().getEmployeeByTC(searchTC.trim());

                    if (exEmployee != null) {
                        JTextField txtEmployeeName = new JTextField(exEmployee.getFirst_name());
                        JTextField txtEmployeeSurname = new JTextField(exEmployee.getLast_name());
                        JTextField txtEmployeeTC = new JTextField(exEmployee.getTC());
                        JTextField txtEmployeePassword = new JTextField(exEmployee.getPassword());

                        Object[] message = {
                                "Adı :", txtEmployeeName,
                                "Soyadı: ", txtEmployeeSurname,
                                "TC: ", txtEmployeeTC,
                                "Şifre: ", txtEmployeePass
                        };

                        int option = JOptionPane.showConfirmDialog(this, message, "Çalışan Bilgilerini Güncelle", JOptionPane.OK_CANCEL_OPTION);
                        if (option == JOptionPane.OK_OPTION) {
                            try {
                                boolean success = new EmployeeDAO().updateEmployee(
                                        exEmployee.getId(),
                                        txtEmployeeName.getText(),
                                        txtEmployeeSurname.getText(),
                                        txtEmployeeTC.getText(),
                                        txtEmployeePassword.getText()
                                );

                                if (success) {
                                    JOptionPane.showMessageDialog(this, "Çalışan başarıyla güncellendi!");
                                } else {
                                    JOptionPane.showMessageDialog(this, "Güncelleme başarısız oldu!", "Hata", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(this, "Teknik bir hata oluştu: " + ex.getMessage());
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(this, "Çalışan bulunamadı!");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Hata: " + ex.getMessage());
                }
            }
        });
        addComponent(panel, btnupdateEmployee, 1, 6, gbc);

        return panel;


    }


    private void addComponent(JPanel panel, Component comp, int x, int y, GridBagConstraints gbc) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(comp, gbc);
    }

    private JPanel createStatCard(String title, String value, String icon, Color color) {
        JPanel card = new JPanel(new BorderLayout(15, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 8, 0, 0, color),
                new EmptyBorder(25, 25, 25, 25)
        ));

        JLabel lblIcon = new JLabel(icon);
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 45));
        lblIcon.setForeground(color);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTitle.setForeground(new Color(127, 140, 141));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValue.setForeground(new Color(44, 62, 80));

        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setOpaque(false);
        textPanel.add(lblTitle);
        textPanel.add(lblValue);

        card.add(lblIcon, BorderLayout.WEST);
        card.add(textPanel, BorderLayout.CENTER);

        return card;
    }

    public void refreshDashboardStats() {
        try {
            // Veritabanından güncel sayıları çekiyoruz (DAO sınıflarınızda bu metotlar olmalı)
            int totalBooks = bookDAO.getTotalBookCount();
            int totalAuthors = authorDAO.getTotalAuthorCount();
            int totalMembers = memberDAO.getTotalMemberCount();
            int totalEmployees = employeeDAO.getTotalEmployeeCount();

            lblTotalBooks.setText(String.valueOf(totalBooks));
            lblTotalAuthors.setText(String.valueOf(totalAuthors));
            lblTotalMembers.setText(String.valueOf(totalMembers));
            lblTotalEmployees.setText(String.valueOf(totalEmployees));


        } catch (Exception e) {
            System.out.println("Panel güncellenirken hata: " + e.getMessage());
        }

    }

}