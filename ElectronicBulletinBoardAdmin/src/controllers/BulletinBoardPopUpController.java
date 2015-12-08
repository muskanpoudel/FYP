/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.Database;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class BulletinBoardPopUpController implements Initializable {

    @FXML
    TextField Bbname, Bblocation;
    @FXML Label statusLbl;

    @FXML
    public void addBulletinBoard() {
        boolean stat = addIntoDatabase(Bbname.getText(), Bblocation.getText());
        if (stat) {
            statusLbl.setText("Updated Successfully!!!");
            statusLbl.setStyle("-fx-text-fill:green");
        } else {
            statusLbl.setText("Error in updating! Please Try Again!");
            statusLbl.setStyle("-fx-text-fill:red");
        }
    }

    public boolean addIntoDatabase(String BBname, String BBlocation) {
        boolean stat = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`noticeboard` "
                + "(`noticeboardname`, `noticeboardlocation`) VALUES"
                + " ('" + BBname + "', '" + BBlocation + "')");
        return stat;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
