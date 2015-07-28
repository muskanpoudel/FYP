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
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class HomeScreenController implements Initializable {
    
    FXMLLoader loader;
    Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    
    @FXML
    public void GoBack() throws Exception {
        loadMainPane(StuffHolder.LogInScreen);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

     //muskan edited------------------------------------
    private void loadMainPane(String fxmlPath) throws IOException, Exception {
        loader = new FXMLLoader(getClass().getResource(fxmlPath));
        //loader.setLocation(Class.class.getResource(ScreenNavigator.MainGUI));
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(fxmlPath));
        Scene sc = new Scene(mainPane, visualBounds.getWidth(), visualBounds.getHeight());
        Stage stageHere = StuffHolder.getStageMAin();
        stageHere.setScene(sc);
        StuffHolder.setStageMAin(stageHere);
    }
    //---------------------------------

}
