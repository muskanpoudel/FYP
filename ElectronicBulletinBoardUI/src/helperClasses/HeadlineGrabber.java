/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Muskan
 */
public class HeadlineGrabber {

    public ArrayList<String> getHeadline() throws SQLException {
        ArrayList<String> headlines = new ArrayList();

        ResultSet rs1 = Database.executeQuery("SELECT * FROM electronic_bulletin_board.contentheadline\n"
                + "WHERE expire_date >= CURDATE();");
        ResultSet rs2 = Database.executeQuery("SELECT * FROM electronic_bulletin_board.admincontentheadline\n"
                + "WHERE expire_date >= CURDATE();");

        while (rs1.next()) {
            headlines.add(rs1.getString("headline").replace("\n", "").replace("\r", ""));
        }
        while (rs2.next()) {
            headlines.add(rs2.getString("headline").replace("\n", "").replace("\r", ""));
        }

        return headlines;

    }

}
