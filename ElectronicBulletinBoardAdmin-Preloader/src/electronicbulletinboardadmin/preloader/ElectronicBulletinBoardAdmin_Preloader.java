/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicbulletinboardadmin.preloader;

import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 *
 * @author Muskan
 */
public class ElectronicBulletinBoardAdmin_Preloader extends Preloader {

    Stage stage;
    private Pane splashLayout;
    private static final int SPLASH_WIDTH = 600;
    private static final int SPLASH_HEIGHT = 400;
    ProgressIndicator pi = new ProgressIndicator();

    @Override
    public void init() {
        ImageView splash = new ImageView(new Image("/img/splash.jpg"));
        splashLayout = new Pane();
        pi.setProgress(-1);
        pi.setPrefSize(150, 150);
        pi.setLayoutX(90);
        pi.setLayoutY(160);
        splashLayout.getChildren().addAll(splash, pi);
        splashLayout.setEffect(new DropShadow());
    }

    @Override
    public void start(Stage initStage) throws Exception {
        Scene splashScene = new Scene(splashLayout);
        initStage.initStyle(StageStyle.UNDECORATED);
        final Rectangle2D bounds = Screen.getPrimary().getBounds();
        initStage.setScene(splashScene);
        initStage.setX(bounds.getMinX() + bounds.getWidth() / 2 - SPLASH_WIDTH / 2);
        initStage.setY(bounds.getMinY() + bounds.getHeight() / 2 - SPLASH_HEIGHT / 2);
        initStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/icon.png")));
        initStage.show();

        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final int max = 48;
                for (int i = 1; i <= max; i++) {
                    Thread.sleep(80);
                    updateProgress(i, max);
                }
                return null;
            }

        };
        pi.progressProperty().bind(task.progressProperty());
        pi.setStyle("-fx-progress-color:black;");
        new Thread(task).start();
        task.stateProperty().addListener((observableValue, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                initStage.toFront();
                FadeTransition fadeSplash = new FadeTransition(Duration.seconds(0.7), splashLayout);
                fadeSplash.setFromValue(1.0);
                fadeSplash.setToValue(0.0);
                fadeSplash.setOnFinished(actionEvent -> initStage.hide());
                fadeSplash.play();
            }
        });
        
    }
}