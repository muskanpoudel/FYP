/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicbulletinboardadmin;

//import electronicbulletinboardadmin.preloader.ElectronicBulletinBoardAdmin_Preloader;
import helperClasses.LocalUtility;
import helperClasses.StuffHolder;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Muskan
 */
public class ElectronicBulletinBoardAdmin extends Application {

   // Rectangle2D visualBounds = Screen.getPrimary().getVisualBounds();
    FXMLLoader loader;

    @Override
    public void start(Stage stage) throws Exception {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3850);
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            showMainStage(stage);
                        }
                    });

                } catch (Exception ex) {
                   // Logger.getLogger(ElectronicBulletinBoardAdmin_Preloader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.start();

    }

    public void showMainStage(Stage stage) {
        try {
            Scene scene = createScene(loadMainPane());
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Electronic Bulletin Board System. Admin Interface.");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
            StuffHolder.setStageMAin(stage);
            stage.show();
        } catch (Exception ex) {
            Logger.getLogger(ElectronicBulletinBoardAdmin.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Pane loadMainPane() throws IOException, Exception {

        loader = new FXMLLoader(getClass().getResource("/fxml/LogInScreen.fxml"));
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(
                "/fxml/LogInScreen.fxml"
        )
        );

        return mainPane;

    }

    public Scene createScene(Pane pane) {
     //   Scene scene = new Scene(pane, visualBounds.getWidth(), visualBounds.getHeight());
        Scene scene = new Scene(pane);
        Properties pro = LocalUtility.getProperty();
        String css = ElectronicBulletinBoardAdmin.class.getResource(pro.getProperty("cssfile").toString()).toExternalForm();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(css);
        return scene;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
