/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.StuffHolder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class HopePageController implements Initializable {

    @FXML
    ToggleButton bulletinBoardtogglebtn, bulletintogglebtn;
    @FXML
    StackPane stackPane;

    @FXML
    public void showBulletinBoard() throws IOException {
        bulletinBoardtogglebtn.setSelected(true);
        bulletintogglebtn.setSelected(false);
        loadDesiredPageFromMenu(StuffHolder.BulletinBoardPage);
    }

    @FXML
    public void showBulletin() throws IOException {
        bulletinBoardtogglebtn.setSelected(false);
        bulletintogglebtn.setSelected(true);
        loadDesiredPageFromMenu(StuffHolder.BulletinPage);
    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        stackPane.getChildren().clear();
        stackPane.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
        StuffHolder.setSpane(stackPane);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            bulletinBoardtogglebtn.setSelected(true);
            loadDesiredPageFromMenu(StuffHolder.BulletinBoardPage);
        } catch (IOException ex) {
            Logger.getLogger(HopePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
