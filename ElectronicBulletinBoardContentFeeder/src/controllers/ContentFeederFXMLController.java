/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.Database;
import helperClasses.StuffHolder;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class ContentFeederFXMLController implements Initializable {

    @FXML
    Label errorlbl;
    @FXML TextField username;
    @FXML PasswordField password;

    @FXML
    public void loginPressed() throws Exception {
        ResultSet rs = Database.executeQuery("select username, password from electronic_bulletin_board.contentfeeders where username=\"" + username.getText() + "\" and password = \"" + password.getText() + "\";");
        if (!rs.isBeforeFirst()) {
            errorlbl.setText("Authentication failed. Please try again.");
            errorlbl.setVisible(true);
            
        } else {
            errorlbl.setVisible(false);
            loadMainPane(StuffHolder.homePage);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    //muskan edited------------------------------------
    private void loadMainPane(String fxmlPath) throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        //loader.setLocation(Class.class.getResource(ScreenNavigator.MainGUI));
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(fxmlPath));
        Scene sc = new Scene(mainPane);
        Stage stageHere = StuffHolder.getMainStage();
        stageHere.setScene(sc);
        stageHere.centerOnScreen();
        StuffHolder.setMainStage(stageHere);
    }
    //---------------------------------

}
