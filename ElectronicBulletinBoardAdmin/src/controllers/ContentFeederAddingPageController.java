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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class ContentFeederAddingPageController implements Initializable {

    @FXML
    TextField usernamefld, passwordfld, confirmpasswordfld, emailfld, firstnamefld, lastnamefld;
    @FXML
    ComboBox statuscombo;
    @FXML
    Label statusLbl;

    @FXML
    public void addPressed() {
        String status = String.valueOf(statuscombo.getSelectionModel().getSelectedItem());
        boolean stat = addIntoDatabase(usernamefld.getText(), passwordfld.getText(), emailfld.getText(), firstnamefld.getText(), lastnamefld.getText(), status);
        if (stat) {
            statusLbl.setText("Updated Successfully!!!");
            statusLbl.setStyle("-fx-text-fill:green");
        } else {
            statusLbl.setText("Error in updating! Please Try Again!");
            statusLbl.setStyle("-fx-text-fill:red");
        }
    }

    public boolean addIntoDatabase(String username, String password, String email, String firstname, String lastname, String status) {
        boolean stat = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`contentfeeders` "
                + "(`username`, `password`, `email`, `firstname`, `lastname`, `status`) VALUES"
                + " ('" + username + "', '" + password + "', '" + email + "', '" + firstname + "', '" + lastname + "', '" + status + "')");
        return stat;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
