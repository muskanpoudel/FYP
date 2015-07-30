/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import controllers.HomeScreenController;
import controllers.PopupController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Muskan
 */
public class ShowPopup {

    public Button okButton = null;

    public void showMessagePopup(Image PopupIcon, String title, String Message, String stageTitle) throws IOException {
        FXMLLoader popuploader = new FXMLLoader(getClass().getResource(StuffHolder.msgpopup));
        Pane popupPane = (Pane) popuploader.load(getClass().getResourceAsStream(StuffHolder.msgpopup));
        PopupController popcon = (PopupController) popuploader.getController();
        popcon.setPopupImage(PopupIcon);
        popcon.setPopupTitle(title);
        popcon.setPopupMsg(Message);
        Scene sc = new Scene(popupPane);
        Stage stageHere = new Stage();
        stageHere.setScene(sc);
        stageHere.setTitle(stageTitle);
        stageHere.initOwner(StuffHolder.getStageMAin());
        stageHere.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        stageHere.initModality(Modality.APPLICATION_MODAL);
        stageHere.setResizable(false);
        StuffHolder.setPopupStage(stageHere);
        stageHere.showAndWait();
    }

    public void showConfirmPopup(Image PopupIcon, String title, String Message, String stageTitle) throws IOException {
        FXMLLoader popuploader = new FXMLLoader(getClass().getResource(StuffHolder.confirmpopup));
        Pane popupPane = (Pane) popuploader.load(getClass().getResourceAsStream(StuffHolder.confirmpopup));
        PopupController popcon = (PopupController) popuploader.getController();
        popcon.setPopupImage(PopupIcon);
        popcon.setPopupTitle(title);
        popcon.setPopupMsg(Message);
        okButton = popcon.popupOkBtn;
        Scene sc = new Scene(popupPane);
        Stage stageHere = new Stage();
        stageHere.setScene(sc);
        stageHere.setTitle(stageTitle);
        stageHere.initOwner(StuffHolder.getStageMAin());
        stageHere.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        stageHere.initModality(Modality.APPLICATION_MODAL);
        stageHere.setResizable(false);
        StuffHolder.setPopupStage(stageHere);
        stageHere.show();
    }

}
