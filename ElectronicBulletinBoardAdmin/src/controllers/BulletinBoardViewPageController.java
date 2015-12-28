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
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class BulletinBoardViewPageController implements Initializable {

    @FXML
    Label name, location, pendingInfo, shownInfo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(StuffHolder.getBulletinboardinfo().getName());
        location.setText(StuffHolder.getBulletinboardinfo().getLocation());
        shownInfo.setText("5");
        pendingInfo.setText("10");
    }

}
