//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

import java.sql.Connection;
class Test{
    public static void main(String[] args) {
        try {
            Connection conn = DBConnection.getConnection();
            System.out.println("BAĞLANTI BAŞARILI!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
