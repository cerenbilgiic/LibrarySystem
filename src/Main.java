import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Senin arayüz sınıfının adı neyse onu buraya yaz:
                LibrarySystemUI gui = new LibrarySystemUI();
                gui.setVisible(true); // Pencereyi görünür yap
            }
        });
    }
}