/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.CheckInternetConnection;
import helperClasses.Database;
import helperClasses.GroupValidator;
import helperClasses.LocalUtility;
import helperClasses.NumberValidator;
import helperClasses.NumericStringValidator;
import helperClasses.ShowPopup;
import helperClasses.StuffHolder;
import helperClasses.Validator;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pojo.CurrentAdmin;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class LogInScreenController implements Initializable {

//    FXMLLoader loader;
    @FXML
    Label errorlbl, internetlbl, databaselbl;
    @FXML
    ImageView internetIcon, databaseIcon;
    @FXML
    TextField usernamefld;
    @FXML
    PasswordField passwordfld, pinfld;

    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    Validator validator = new Validator();
    GroupValidator gv = new GroupValidator();

    ShowPopup sp = new ShowPopup(); //for popups

    public void checkInternetDatabase() {
        if (CheckInternetConnection.testInet()) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    internetIcon.setImage(LocalUtility.imageFactory("/img/yesInternet.png"));
                    internetlbl.setStyle("-fx-text-fill:#2ecc71");
                    internetlbl.setText("Internet Access.");
                }
            });
        }

        if (Database.connectDB() != null || !(Database.connectDB()).equals(null)) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    databaseIcon.setImage(LocalUtility.imageFactory("/img/yesdatabase.png"));
                    databaselbl.setStyle("-fx-text-fill:#2ecc71");
                    databaselbl.setText("Connected to database.");
                }
            });
        }

    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        new Thread() {
            @Override
            public void run() {
                checkInternetDatabase();
            }
        }.start();

        validator.addValidator(new NumberValidator(pinfld, Boolean.TRUE));
        validator.addValidator(new NumericStringValidator(usernamefld, Boolean.TRUE));
        validator.addValidator(new NumericStringValidator(passwordfld, Boolean.TRUE));
        gv.add(usernamefld);
        gv.add(passwordfld);
        gv.add(pinfld);

    }

    @FXML
    public void LogInOkPressed() {
        if (gv.validate() && validator.validate()) {
            try {
                //if database is sucessfully connected info about admin is gathered
                ResultSet rs = Database.executeQuery("select * from electronic_bulletin_board.admin where username=\"" + usernamefld.getText() + "\" and pin=" + pinfld.getText() + " and password = \"" + passwordfld.getText() + "\";");
                if (!rs.isBeforeFirst()) {
                    errorlbl.setText("Authentication failed. Please try again.");
                    errorlbl.setVisible(true);
                } else {
                    errorlbl.setVisible(false);
                    while (rs.next()) {
                        CurrentAdmin ca = new CurrentAdmin();
                        ca.setAdminid(rs.getInt("admin_id"));
                        ca.setUsername(rs.getString("username"));
                        ca.setFirstName(rs.getString("first_name"));
                        ca.setLastName(rs.getString("last_name"));
                        StuffHolder.setThisAdmin(ca);
                    }
                    loadMainPane(StuffHolder.HomeScreen);
                }

            } catch (SQLException ex) {
                Logger.getLogger(LogInScreenController.class.getName()).log(Level.SEVERE, null, ex);
                errorlbl.setText("Authentication failed. Please try again.");
                errorlbl.setVisible(true);
            } catch (Exception ex) {
                Logger.getLogger(LogInScreenController.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

    }

    @FXML
    public void showRecoveryPopup() throws IOException {
        sp.showMessagePopup(new Image("/img/help.png"), "Password Recovery", "Your password and pin number has been sent to your email a*******45@gmail.com. Please make sure of it.", "Forgot Password!!!");
    }

    //muskan edited------------------------------------
    private void loadMainPane(String fxmlPath) throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        //loader.setLocation(Class.class.getResource(ScreenNavigator.MainGUI));
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(fxmlPath));
        Scene sc = new Scene(mainPane, visualBounds.getWidth(), visualBounds.getHeight());
        Stage stageHere = StuffHolder.getStageMAin();
        stageHere.setScene(sc);
        StuffHolder.setStageMAin(stageHere);
    }
    //---------------------------------

}
