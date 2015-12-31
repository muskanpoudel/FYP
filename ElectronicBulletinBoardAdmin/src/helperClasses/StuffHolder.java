/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pojo.Admin;
import pojo.BulletinBoards;
import pojo.BulletinInfo;
import pojo.ContentFeeders;
import pojo.CurrentAdmin;
import pojo.Student;
import pojo.Teacher;

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
    public static String adminSettingPage = "/fxml/AdminSetting.fxml";
    public static String studentSettingPage = "/fxml/StudentSetting.fxml";
    public static String teacherSettingPage = "/fxml/TeacherSetting.fxml";

    public static String SusContentFeederPage = "/fxml/SusContentFeederPage.fxml";
    public static String SmsSettingPage = "/fxml/SMSSettingPage.fxml";

    public static String BulletinBoardpopup = "/fxml/BulletinBoardPopUp.fxml";
    public static String BulletinBoardInformation = "/fxml/BulletinBoardViewPage.fxml";

    public static String BulletinAddingScreen = "/fxml/BulletinAddingScreen.fxml";
    public static String AddBulletinImage = "/fxml/AddBulletinImg.fxml";
    public static String AddBulletinHeadline = "/fxml/AddBulletinHeadline.fxml";
    public static String AddBulletinText = "/fxml/AddBulletinText.fxml";

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

    //storing admin data
    static CurrentAdmin thisAdmin;

    //is edit bulletinboard pressed?
    static boolean editbulletinboard = false;
    //storing bb information
    static BulletinBoards bulletinboardinfo;
    //is edit contentFeeder pressed?
    static boolean editcontentFeeder = false;
    //storing contentFeeder information
    static ContentFeeders ContetFeeders;
    //is edit bulletin pressed
    static boolean editbulletin = false;
    //storing bulletin info
    static BulletinInfo bulletinInformation;

    //storing selected admin data for setting page
    static Admin admin;
    //storing selected teacher data for setting page
    static Teacher teacher;
    //storing selected Student data for setting page
    static Student student;

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

    public static CurrentAdmin getThisAdmin() {
        return thisAdmin;
    }

    public static void setThisAdmin(CurrentAdmin thisAdmin) {
        StuffHolder.thisAdmin = thisAdmin;
    }

    //bulletinboards
    public static boolean isEditbulletinboard() {
        return editbulletinboard;
    }

    public static void setEditbulletinboard(boolean editbulletinboard) {
        StuffHolder.editbulletinboard = editbulletinboard;
    }

    public static BulletinBoards getBulletinboardinfo() {
        return bulletinboardinfo;
    }

    public static void setBulletinboardinfo(BulletinBoards bulletinboardinfo) {
        StuffHolder.bulletinboardinfo = bulletinboardinfo;
    }

    //content feeders
    public static boolean isEditcontentFeeder() {
        return editcontentFeeder;
    }

    public static void setEditcontentFeeder(boolean editcontentFeeder) {
        StuffHolder.editcontentFeeder = editcontentFeeder;
    }

    public static ContentFeeders getContetFeeders() {
        return ContetFeeders;
    }

    public static void setContetFeeders(ContentFeeders ContetFeeders) {
        StuffHolder.ContetFeeders = ContetFeeders;
    }

    //bulletins
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

    public static Admin getAdmin() {
        return admin;
    }

    public static void setAdmin(Admin admin) {
        StuffHolder.admin = admin;
    }

    public static Teacher getTeacher() {
        return teacher;
    }

    public static void setTeacher(Teacher teacher) {
        StuffHolder.teacher = teacher;
    }

    public static Student getStudent() {
        return student;
    }

    public static void setStudent(Student student) {
        StuffHolder.student = student;
    }

}
