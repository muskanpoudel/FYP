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
public class TextGrabber {

    public ArrayList<String> getText() throws SQLException {
        ArrayList<String> texts = new ArrayList();

        ResultSet rs1 = Database.executeQuery("SELECT title, text FROM electronic_bulletin_board.contenttext\n"
                + "WHERE expire_date >= CURDATE();");
        ResultSet rs2 = Database.executeQuery("SELECT title, text FROM electronic_bulletin_board.admincontenttext\n"
                + "WHERE expire_date >= CURDATE();");

        while (rs1.next()) {
            texts.add(rs1.getString("title") + "-" + rs1.getString("text").replace("\n", "").replace("\r", ""));
        }
        while (rs2.next()) {
            texts.add(rs2.getString("title") + "-" + rs2.getString("text").replace("\n", "").replace("\r", ""));
        }

        return texts;

    }
}
