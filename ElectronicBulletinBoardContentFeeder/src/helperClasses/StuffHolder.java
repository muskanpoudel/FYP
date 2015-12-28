/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pojo.BulletinInfo;
import pojo.CurrentUser;

/**
 *
 * @author Muskan
 */
public class StuffHolder {

    public static String homePage = "/fxml/HopePage.fxml";
    public static String BulletinBoardPage = "/fxml/BulletinBoard.fxml";
    public static String BulletinPage = "/fxml/bulletin.fxml";

    public static String BulletinAddingScreen = "/fxml/BulletinAddingPage.fxml";
    public static String AddBulletinImage = "/fxml/contentAddimage.fxml";
    public static String AddBulletinHeadline = "/fxml/contentAddHeadline.fxml";
    public static String AddBulletinText = "/fxml/contentAddText.fxml";

    static Stage MainStage;
    static StackPane spane;
    static CurrentUser currentUser;

    //is edit bulletin pressed
    static boolean editbulletin = false;
    //storing bulletin info
    static BulletinInfo bulletinInformation;

    public static Stage getMainStage() {
        return MainStage;
    }

    public static void setMainStage(Stage MainStage) {
        StuffHolder.MainStage = MainStage;
    }

    public static StackPane getSpane() {
        return spane;
    }

    public static void setSpane(StackPane spane) {
        StuffHolder.spane = spane;
    }

    public static CurrentUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(CurrentUser currentUser) {
        StuffHolder.currentUser = currentUser;
    }

    public static boolean isEditbulletin() {
        return editbulletin;
    }

    public static void setEditbulletin(boolean editbulletin) {
        StuffHolder.editbulletin = editbulletin;
    }

    public static BulletinInfo getBulletinInformation() {
        return bulletinInformation;
    }

    public static void setBulletinInformation(BulletinInfo bulletinInformation) {
        StuffHolder.bulletinInformation = bulletinInformation;
    }

}
