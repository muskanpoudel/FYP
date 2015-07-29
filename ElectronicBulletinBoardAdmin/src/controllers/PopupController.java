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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 *
 * @author Muskan
 */
public class PopupController implements Initializable {

    @FXML
    ImageView popupimg;
    @FXML
    Text popupTitle;
    @FXML
    Label popupMsg;
    @FXML
    Button popupOkBtn;

    @FXML
    public void hidePopup() {
        StuffHolder.getPopupStage().close();
    }

    public void setPopupImage(Image IconForpopup) {
        popupimg.setImage(IconForpopup);
    }

    public void setPopupTitle(String TitleForPopup) {
        popupTitle.setText(TitleForPopup);
    }

    public void setPopupMsg(String MessageForPopup) {
        popupMsg.setText(MessageForPopup);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
