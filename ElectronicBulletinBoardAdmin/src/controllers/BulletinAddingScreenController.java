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
    ToggleButton txtAndImageTB, imageTB, videoTB, linkTB, powerPointTB;

    @FXML
    public void txtAndImageScreen() throws IOException {
        txtAndImageTB.setSelected(true);
        imageTB.setSelected(false);
        videoTB.setSelected(false);
        linkTB.setSelected(false);
        powerPointTB.setSelected(false);

        loadDesiredPageFromMenu(StuffHolder.AddBulletinTxtAndImage);
    }

    @FXML
    public void imageScreen() throws IOException {
        txtAndImageTB.setSelected(false);
        imageTB.setSelected(true);
        videoTB.setSelected(false);
        linkTB.setSelected(false);
        powerPointTB.setSelected(false);

        loadDesiredPageFromMenu(StuffHolder.AddBulletinImage);
    }

    @FXML
    public void videoScreen() throws IOException {
        txtAndImageTB.setSelected(false);
        imageTB.setSelected(false);
        videoTB.setSelected(true);
        linkTB.setSelected(false);
        powerPointTB.setSelected(false);

        loadDesiredPageFromMenu(StuffHolder.AddBulletinVideo);

    }

    @FXML
    public void linkScreen() throws IOException {
        txtAndImageTB.setSelected(false);
        imageTB.setSelected(false);
        videoTB.setSelected(false);
        linkTB.setSelected(true);
        powerPointTB.setSelected(false);

        loadDesiredPageFromMenu(StuffHolder.AddBulletinLink);
    }

    @FXML
    public void powerPointScreen() throws IOException {
        txtAndImageTB.setSelected(false);
        imageTB.setSelected(false);
        videoTB.setSelected(false);
        linkTB.setSelected(false);
        powerPointTB.setSelected(true);

        loadDesiredPageFromMenu(StuffHolder.AddBulletinPowerPoint);

    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        addBulletinStackPane.getChildren().clear();
        addBulletinStackPane.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
