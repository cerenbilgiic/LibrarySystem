import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class LibrarySystemUI extends JFrame {
    private JTextField txtMemberId , txtBookId;
    private JButton btnLoan , btnExit;
    private LoanDAO loanDAO; //Veritabanı ile iletişim.

    public LibrarySystemUI(){
        setTitle("Kütüphane Otomasyon Sistemi");
        setSize(900,750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Çarpı işaretine basınca kapatmayı sağlar.
        setLocationRelativeTo(null); //Uygulamanın tam ortaya açılmasını sağlar.

        setLayout(new GridLayout(4, 2, 15, 15));
        loanDAO = new LoanDAO();

        //ÜYE İŞLEMLERİ PANELİ
        add(createSectionPanel("Üye İşlemleri", new String[]{"Üye Ekle", "Üye Ara", "Üye Düzenle"})); //String kullanılmasının sebebi buton isimlerinin bir dizi gibi sıralanmasıdır.

        //KİTAP İŞLEMLERİ PANELİ
        JPanel pnlBook = createSectionPanel("Kitap İşlemleri", new String[]{"Kitap Ekle" , "Kitap Ara" , "Kitap Düzenle"});
        pnlBook.add(new JLabel("Kategori:"));
        pnlBook.add(new JComboBox<>(new String[]{"Roman", "Bilim", "Tarih", "Yazılım" , "Masal" , "Biyografi" , "Siyaset" , "Kişisel Gelişim"}));
        add(pnlBook);

        //ÖDÜNÇ VERME VE İADE PANELİ

        JPanel pnlLoan = new JPanel(new GridLayout(4, 2, 5, 5));
        pnlLoan.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Ödünç Verme ve İade", TitledBorder.LEFT, TitledBorder.TOP));

        pnlLoan.add(new JLabel("Üye Numarası: "));
        txtMemberId = new JTextField();
        pnlLoan.add(txtMemberId);

        pnlLoan.add(new JLabel("Kitap Numarası: "));
        txtBookId = new JTextField();
        pnlLoan.add(txtBookId); // pnlLoan içine eklendi

        pnlLoan.add(new JLabel(" İşlem Tipi:"));
        pnlLoan.add(new JComboBox<>(new String[]{"Ödünç", "İade"}));

        btnLoan = new JButton("Kitabı Ödünç Ver");
        btnLoan.setBackground(new Color(153, 187, 238));
        pnlLoan.add(btnLoan);
        pnlLoan.add(new JButton("İade Al"));
        add(pnlLoan);

        //KATEGORİ YÖNETİMİ
        add(createSectionPanel("Kategori Yönetimi", new String[]{"Kategori Ekle", "Kategori Düzenle"}));

        //YAZAR İŞLEMLERİ
        add(createSectionPanel("Yazar İşlemleri", new String[]{"Yazar Ekle", "Yazar Düzenle"}));

        //CEZA İŞLEMLERİ PANELİ
        JPanel pnlFine = new JPanel(new FlowLayout());
        pnlFine.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Para Cezası İşlemleri", TitledBorder.LEFT, TitledBorder.TOP));
        JButton btnCheckFine = new JButton("Ceza Hesapla");
        btnCheckFine.setPreferredSize(new Dimension(150, 40));
        pnlFine.add(btnCheckFine);
        add(pnlFine);

        //RAPORLAR VE ÇIKIŞ PANELİ
        JPanel pnlReports = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnlReports.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Raporlar", TitledBorder.LEFT, TitledBorder.TOP));
        pnlReports.add(new JButton("Ödünç Raporu"));
        pnlReports.add(new JButton("Stok Raporu"));
        add(pnlReports);

        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnExit = new JButton("ÇIKIŞ");
        btnExit.setBackground(new Color(211, 47, 47));
        btnExit.setForeground(Color.BLACK);
        pnlFooter.add(btnExit);

        // PANELLERİ ANA PENCEREYE EKLEME
        add(pnlLoan);
        add(pnlFine);
        add(pnlFooter);

        btnLoan.addActionListener(e -> {
            try {
                int mId = Integer.parseInt(txtMemberId.getText());
                int bId = Integer.parseInt(txtBookId.getText());
                boolean sonuc = loanDAO.IssueLoan(mId, bId);
                if (sonuc) JOptionPane.showMessageDialog(this, "Kitap teslimi başarılı.");
                else JOptionPane.showMessageDialog(this, "Kitap teslim başarısız.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Lütfen geçerli sayısal bir numara giriniz.");
            }
        });

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

        btnExit.addActionListener(e -> System.exit(0)); //Programı kapatmayı sağlar.

        setVisible(true); //Panelin ekranda görünmesini sağlar.
    }
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
            if (loanDAO.IssueLoan(mId, bId)) JOptionPane.showMessageDialog(this, "İşlem Başarılı!");
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

