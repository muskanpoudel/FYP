/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import javafx.stage.Stage;

/**
 *
 * @author Muskan
 */
public class StuffHolder {

    public static String LogInScreen = "/fxml/LogInScreen.fxml";
    public static String HomeScreen = "/fxml/HomeScreen.fxml";
    public static String passwordRecovery = "/fxml/forgotPassword.fxml";

    static Stage stageMAin;
    static Stage popupStage;

    //admin info
    static String username;
    static String password;
    static int pin;

    public static Stage getStageMAin() {
        return stageMAin;
    }

    public static void setStageMAin(Stage stageMAin) {
        StuffHolder.stageMAin = stageMAin;
    }

    //admin info
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        StuffHolder.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        StuffHolder.password = password;
    }

    public static int getPin() {
        return pin;
    }

    public static void setPin(int pin) {
        StuffHolder.pin = pin;
    }

    public static Stage getPopupStage() {
        return popupStage;
    }

    public static void setPopupStage(Stage popupStage) {
        StuffHolder.popupStage = popupStage;
    }
    

}
