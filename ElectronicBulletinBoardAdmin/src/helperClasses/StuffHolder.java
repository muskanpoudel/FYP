/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Muskan
 */
public class StuffHolder {

    public static String LogInScreen = "/fxml/LogInScreen.fxml";
    public static String HomeScreen = "/fxml/HomeScreen.fxml";
    public static String msgpopup = "/fxml/MsgPopup.fxml";
    public static String confirmpopup = "/fxml/confirmPopUp.fxml";
    public static String dashboard = "/fxml/DashBoard.fxml";
    public static String menu = "/fxml/Menu.fxml";

    public static String BulletinBoardPage = "/fxml/BulletinBoardPage.fxml";
    public static String ContentFeederPage = "/fxml/ContentFeederPage.fxml";
    public static String BulletinPage = "/fxml/BulletinsPage.fxml";
    public static String SettingPage = "/fxml/GeneralSettingPage.fxml";
    public static String SusContentFeederPage = "/fxml/SusContentFeederPage.fxml";
    public static String SmsSettingPage = "/fxml/SMSSettingPage.fxml";

    public static String BulletinBoardpopup = "/fxml/BulletinBoardPopUp.fxml";
    public static String BulletinBoardInformation = "/fxml/BulletinBoardViewPage.fxml";

    public static String BulletinAddingScreen = "/fxml/BulletinAddingScreen.fxml";
    public static String AddBulletinTxtAndImage = "/fxml/AddBulletinTxtAndImg.fxml";
    public static String AddBulletinImage = "/fxml/AddBulletinImg.fxml";
    public static String AddBulletinVideo = "/fxml/AddBulletinVideo.fxml";
    public static String AddBulletinLink = "/fxml/AddBulletinLink.fxml";
    public static String AddBulletinPowerPoint = "/fxml/AddBulletinPowerPoint.fxml";

    public static String ContentFeederAddingScreen = "/fxml/ContentFeederAddingPage.fxml";
    public static String ContentFeederViewScreen = "/fxml/contentFeederViewPage.fxml";

    static Stage stageMAin;
    static Stage popupStage;

    //admin info
    static String username;
    static String password;
    static int pin;

    //StackPanes
    static StackPane mainHomeScreenStackPane;
    static StackPane menuScreenStackPane;

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

    public static StackPane getMainHomeScreenStackPane() {
        return mainHomeScreenStackPane;
    }

    public static void setMainHomeScreenStackPane(StackPane mainHomeScreenStackPane) {
        StuffHolder.mainHomeScreenStackPane = mainHomeScreenStackPane;
    }

    public static StackPane getMenuScreenStackPane() {
        return menuScreenStackPane;
    }

    public static void setMenuScreenStackPane(StackPane menuScreenStackPane) {
        StuffHolder.menuScreenStackPane = menuScreenStackPane;
    }

}
