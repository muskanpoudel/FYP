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

    static Stage stageMain;
    static Stage stagePopup;

    public static Stage getStageMain() {
        return stageMain;
    }

    public static void setStageMain(Stage stageMain) {
        StuffHolder.stageMain = stageMain;
    }

    public static Stage getStagePopup() {
        return stagePopup;
    }

    public static void setStagePopup(Stage stagePopup) {
        StuffHolder.stagePopup = stagePopup;
    }
    
    

}
