/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.StuffHolder;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 *
 * @author Muskan
 */
public class PopupController implements Initializable {

    @FXML
    public void hidePopup() {
        StuffHolder.getPopupStage().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
