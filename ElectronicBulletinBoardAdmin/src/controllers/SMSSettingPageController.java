/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.SmsSender;
import helperClasses.StuffHolder;
import helperClasses.propertyFileHandler;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class SMSSettingPageController implements Initializable {

    @FXML
    TextField hostfld, portfld, unamefld, passfld;
    @FXML
    Label msglbl;

    @FXML
    public void sendsms() throws IOException {
        new Thread() {
            @Override
            public void run() {
                try {
                    new SmsSender("EBBS", "9779841422029", "Hi. This is test messge from Electronic Bulletin Board System.");
                    new SmsSender("EBBS", "9779849758932", "Hi. This is test messge from Electronic Bulletin Board System.");
                } catch (IOException ex) {
                    Logger.getLogger(SMSSettingPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

    }

    @FXML
    public void goBack() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.menu);
    }

    //to change panes
    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        StuffHolder.getMenuScreenStackPane().getChildren().clear();
        StuffHolder.getMenuScreenStackPane().getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @FXML
    public void save() throws IOException {
        try {
            propertyFileHandler.updateProperties("smshostip", hostfld.getText());
            propertyFileHandler.updateProperties("smshostport", portfld.getText());
            propertyFileHandler.updateProperties("smsusername", unamefld.getText());
            propertyFileHandler.updateProperties("smspassword", passfld.getText());
            msglbl.setText("Successfully Updated!!!");
        } catch (Exception e) {
            msglbl.setText("Something went wrong!!! Please try again.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Properties prop = propertyFileHandler.getProperty();
            hostfld.setText(prop.getProperty("smshostip"));
            portfld.setText(prop.getProperty("smshostport"));
            unamefld.setText(prop.getProperty("smsusername"));
            passfld.setText(prop.getProperty("smspassword"));
        } catch (IOException ex) {
            Logger.getLogger(SMSSettingPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
