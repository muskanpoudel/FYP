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
public class BulletinAddingScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    StackPane addBulletinStackPane;
    @FXML
    ToggleButton imageTB, headlineTB, textTB;

    @FXML
    public void imageScreen() throws IOException {
        imageTB.setSelected(true);
        headlineTB.setSelected(false);
        textTB.setSelected(false);

        loadDesiredPageFromMenu(StuffHolder.AddBulletinImage);
    }

    @FXML
    public void headlineScreen() throws IOException {
        imageTB.setSelected(false);
        headlineTB.setSelected(true);
        textTB.setSelected(false);

        loadDesiredPageFromMenu(StuffHolder.AddBulletinHeadline);
    }

    @FXML
    public void textScreen() throws IOException {
        imageTB.setSelected(false);
        headlineTB.setSelected(false);
        textTB.setSelected(true);

        loadDesiredPageFromMenu(StuffHolder.AddBulletinText);

    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        addBulletinStackPane.getChildren().clear();
        addBulletinStackPane.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            imageTB.setSelected(true);
            imageScreen();
        } catch (IOException ex) {
            Logger.getLogger(BulletinAddingScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
