/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helperClasses.Database;
import helperClasses.StuffHolder;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import pojo.Teacher;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class LoginInterfaceController implements Initializable {
    
    @FXML
    TextField unamefld;
    @FXML
    TextField passfld;
    @FXML
    Label errorlbl;
    
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    
    @FXML
    public void login() {
        try {
            //if database is sucessfully connected info about admin is gathered
            ResultSet rs = Database.executeQuery("select * from electronic_bulletin_board.teacher where username=\"" + unamefld.getText() + "\" and password = \"" + passfld.getText() + "\";");
            if (!rs.isBeforeFirst()) {
                errorlbl.setText("Authentication failed. Please try again.");
                errorlbl.setVisible(true);
            } else {
                errorlbl.setVisible(false);
                while (rs.next()) {
                    Teacher t = new Teacher();
                    t.setId(rs.getInt("idteacher"));
                    t.setFirstname(rs.getString("firstname"));
                    t.setLastname(rs.getString("lastname"));
                    t.setUsername(rs.getString("username"));
                    t.setPassword(rs.getString("password"));
                    
                    StuffHolder.setThisTeacher(t);
                }
                loadMainPane(StuffHolder.HomePage);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(LoginInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            errorlbl.setText("Authentication failed. Please try again.");
            errorlbl.setVisible(true);
        } catch (Exception ex) {
            Logger.getLogger(LoginInterfaceController.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    @FXML
    public void cancel() {
        
    }

    //muskan edited------------------------------------
    private void loadMainPane(String fxmlPath) throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        //loader.setLocation(Class.class.getResource(ScreenNavigator.MainGUI));
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(fxmlPath));
        Scene sc = new Scene(mainPane);
        Stage stageHere = StuffHolder.getStageMain();
        stageHere.setResizable(false);
        stageHere.setScene(sc);
        stageHere.centerOnScreen();
        StuffHolder.setStageMain(stageHere);
    }
    //---------------------------------

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}
