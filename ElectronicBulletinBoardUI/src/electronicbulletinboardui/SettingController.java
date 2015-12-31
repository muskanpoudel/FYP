/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicbulletinboardui;

import helperClasses.Database;
import helperClasses.StuffHolder;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class SettingController implements Initializable {

    @FXML
    ComboBox cbox;

    /**
     * Initializes the controller class.
     */
    /**
     * For setting page
     */
    @FXML
    public void ApplyPressed() {
        StuffHolder.getStagePopup().close();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.noticeboard where stat = 'free';");

        try {
            while (rs.next()) {
                cbox.getItems().add(rs.getString("noticeboardname") + "-" + rs.getString("noticeboardlocation"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(SettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
