/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import javafx.stage.Stage;
import pojo.Message;
import pojo.Teacher;

/**
 *
 * @author Muskan
 */
public class StuffHolder {

    public static String HomePage = "/fxml/homePage.fxml";
    public static String LoginPage = "/fxml/LoginInterface.fxml";

    static Stage stageMain;
    static Teacher thisTeacher;

    static boolean editMessage;
    static Message teacherMessage;

    public static Stage getStageMain() {
        return stageMain;
    }

    public static void setStageMain(Stage stageMain) {
        StuffHolder.stageMain = stageMain;
    }

    public static Teacher getThisTeacher() {
        return thisTeacher;
    }

    public static void setThisTeacher(Teacher thisTeacher) {
        StuffHolder.thisTeacher = thisTeacher;
    }

    public static boolean isEditMessage() {
        return editMessage;
    }

    public static void setEditMessage(boolean editMessage) {
        StuffHolder.editMessage = editMessage;
    }

    public static Message getTeacherMessage() {
        return teacherMessage;
    }

    public static void setTeacherMessage(Message teacherMessage) {
        StuffHolder.teacherMessage = teacherMessage;
    }

}
