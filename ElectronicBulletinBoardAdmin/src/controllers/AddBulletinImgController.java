/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.Database;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class AddBulletinImgController implements Initializable {

    @FXML
    FlowPane flowPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.noticeboard");

            while (rs.next()) {
                CheckBox cb = new CheckBox(rs.getString("noticeboardname") + " (" + rs.getString("noticeboardlocation") + ")");
                flowPane.getChildren().add(cb);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddBulletinHeadlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
