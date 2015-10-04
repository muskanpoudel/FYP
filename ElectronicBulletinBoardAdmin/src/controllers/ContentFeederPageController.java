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
public class ContentFeederPageController implements Initializable {

    StackPane menuPageStackPane = StuffHolder.getMenuScreenStackPane();

    @FXML
    public void addNewContentFeeder() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.ContentFeederAddingScreen);
    }

    @FXML
    public void viewContentFeeder() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.ContentFeederViewScreen);
    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        menuPageStackPane.getChildren().clear();
        menuPageStackPane.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
