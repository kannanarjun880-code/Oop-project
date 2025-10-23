import view.LoginForm;
import util.DatabaseConnection;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        try {
            DatabaseConnection.getConnection();
            System.out.println("Database connection successful!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Database connection failed: " + e.getMessage() + 
                "\nPlease ensure MySQL is running and database is created.", 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
