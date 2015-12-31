package helperClasses;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class Database {

    public static Connection connectDB() {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/electronic_bulletin_board?user=muskan&password=muskan");

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            ex.printStackTrace();
        }

        return conn;
    }

    public static ResultSet executeQuery(String sql) {
        ResultSet rs = null;
        try {
            Connection conn = connectDB();
            Statement stat = conn.createStatement();
            rs = stat.executeQuery(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + e);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return rs;
    }

    public static boolean executeUpdate(String sql) {
        int updated = 0;
        try {
            Connection conn = connectDB();
            Statement stat = conn.createStatement();
            updated = stat.executeUpdate(sql);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage() + "\n" + e);
            e.printStackTrace();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            e.printStackTrace();
        }
        if (updated == 1) {
            return true;
        } else {
            return false;
        }

    }
}
