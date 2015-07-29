/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import electronicbulletinboardadmin.ElectronicBulletinBoardAdmin;
import helperClasses.LocalUtility;
import helperClasses.StuffHolder;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class HomeScreenController implements Initializable {

    FXMLLoader loader;
    @FXML
    ToggleButton dashtogglebtn, menutogglebtn;
    @FXML
    StackPane stackPane;
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();

    @FXML
    public void logout() throws Exception {
        FXMLLoader popuploader = new FXMLLoader(getClass().getResource(StuffHolder.confirmpopup));
        Pane popupPane = (Pane) popuploader.load(getClass().getResourceAsStream(StuffHolder.confirmpopup));
        PopupController popcon = (PopupController) popuploader.getController();
        popcon.setPopupImage(new Image("/img/logout.png"));
        popcon.setPopupTitle("Conformation");
        popcon.setPopupMsg("Are you sure you want to log out?");
        popcon.popupOkBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    loadMainPane(StuffHolder.LogInScreen);
                    StuffHolder.getPopupStage().close();
                } catch (Exception ex) {
                    Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        Scene sc = new Scene(popupPane);
        Stage stageHere = new Stage();
        stageHere.setScene(sc);
        stageHere.setTitle("Logout?");
        stageHere.initOwner(StuffHolder.getStageMAin());
        stageHere.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        stageHere.initModality(Modality.APPLICATION_MODAL);
        stageHere.setResizable(false);
        StuffHolder.setPopupStage(stageHere);
        stageHere.showAndWait();

    }

    @FXML
    public void toggleBtnDash() {
        try {
            if (menutogglebtn.isSelected()) {
                menutogglebtn.setSelected(false);
            }
            stackPane.getChildren().clear();
            stackPane.getChildren().add(FXMLLoader.load(getClass().getResource(StuffHolder.dashboard)));
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    public void toggleBtnMenu() {
        try {
            if (dashtogglebtn.isSelected()) {
                dashtogglebtn.setSelected(false);
            }
            stackPane.getChildren().clear();
            stackPane.getChildren().add(FXMLLoader.load(getClass().getResource(StuffHolder.menu)));
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            dashtogglebtn.setSelected(true);
            stackPane.getChildren().add(FXMLLoader.load(getClass().getResource(StuffHolder.dashboard)));
        } catch (IOException ex) {
            Logger.getLogger(HomeScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //muskan edited------------------------------------
    private void loadMainPane(String fxmlPath) throws IOException, Exception {
        loader = new FXMLLoader(getClass().getResource(fxmlPath));
        //loader.setLocation(Class.class.getResource(ScreenNavigator.MainGUI));
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(fxmlPath));
        Scene sc = new Scene(mainPane, visualBounds.getWidth(), visualBounds.getHeight());
        Properties pro = LocalUtility.getProperty();
        String css = ElectronicBulletinBoardAdmin.class.getResource(pro.getProperty("cssfile").toString()).toExternalForm();
        sc.getStylesheets().clear();
        sc.getStylesheets().add(css);
        Stage stageHere = StuffHolder.getStageMAin();
        stageHere.setScene(sc);
        StuffHolder.setStageMAin(stageHere);
    }
    //---------------------------------

}
