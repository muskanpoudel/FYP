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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class BulletinController implements Initializable {

    StackPane sp = StuffHolder.getSpane();

    @FXML
    public void addPressed() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.BulletinAddingScreen);
    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        sp.getChildren().clear();
        sp.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
