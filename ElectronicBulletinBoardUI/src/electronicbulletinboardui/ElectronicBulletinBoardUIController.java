/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicbulletinboardui;

import helperClasses.HeadlineGrabber;
import helperClasses.ImageGrabber;
import helperClasses.TextGrabber;
import helperClasses.TextPaneGenerator;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class ElectronicBulletinBoardUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    Canvas canvas;
    @FXML
    Pane clockPane, digitalClockPane;
    @FXML
    VBox timeDateVbox;
    @FXML
    Label textMarquee1, textMarquee2;
    @FXML
    ImageView imgView;
    @FXML
    VBox vbox;

    String message = "", message2 = "";

    TranslateTransition transTransition1, transTransition2;

//    @FXML
//    TextField textMarquee;
    //to update freaking UI for image
    public void updateImageView(final Image img) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                imgView.setImage(img);
            }
        });
    }

    public void updatetextContent(final String textContent) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                String[] parts = textContent.split("-");
                TextPaneGenerator textPane = new TextPaneGenerator(parts[0], parts[1]);
                vbox.getChildren().add(textPane);
            }
        });
    }

    public void clearVbox() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                vbox.getChildren().clear();
            }
        });
    }
    /*
     //to update freaking UI for headline
     public void updateHeadline(final String message1, final String message2) {
     Platform.runLater(new Runnable() {
     @Override
     public void run() {
     textMarquee1.setText(message1);
     textMarquee2.setText(message2);

     // Provides the animated scrolling behavior for the text
     double textToMove = (message.length() * 14) + 1366;
     transTransition1 = TranslateTransitionBuilder.create()
     .duration(new Duration(textToMove * 10))
     .node(textMarquee1)
     .toX(-textToMove)
     .interpolator(Interpolator.LINEAR)
     .cycleCount(transTransition1.INDEFINITE)
     .build();

     double textToMove2 = (message.length() * 14) + 1366;
     transTransition2 = TranslateTransitionBuilder.create()
     .duration(new Duration(textToMove2 * 10))
     .node(textMarquee2)
     .toX(-textToMove2)
     .interpolator(Interpolator.LINEAR)
     .cycleCount(transTransition2.INDEFINITE)
     .build();

     transTransition1.play();
     transTransition2.play();

     //                transTransition1.setOnFinished(new EventHandler<ActionEvent>() {
     //                    @Override
     //                    public void handle(ActionEvent event) {
     //                        //Thread.
     //                    }
     //                });

     }
     });
     }
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*  new Thread() {
         @Override
         public void run() {
         Platform.runLater(new Runnable() {
         @Override
         public void run() {
                       
         }
         });

         }
         }.start();*/
        Clock c = new Clock();
        ArrayList clockList = new ArrayList();
        clockList = c.returnClock();

        clockPane.getChildren().add((Group) clockList.get(0));
        //lvlTime.setText((Label)clockList.get(0));
        timeDateVbox.getChildren().add((Label) clockList.get(1));

        /**
         * for current date
         */
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date datetoday = new Date();
        Label date = new Label(dateFormat.format(datetoday));

        /**
         * for current date
         */
        String dayStore = "";
        int today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        System.err.println(today);
        switch (today) {
            case 1:
                dayStore = "Sunday";
                break;
            case 2:
                dayStore = "Monday";
                break;
            case 3:
                dayStore = "Tuesday";
                break;
            case 4:
                dayStore = "Wednesday";
                break;
            case 5:
                dayStore = "Thursday";
                break;
            case 6:
                dayStore = "Friday";
                break;
            case 7:
                dayStore = "Saturday";
                break;
        }
        Label day = new Label(dayStore);
        date.setId("digitalClock");
        day.setId("digitalClock");
        timeDateVbox.getChildren().add(date);
        timeDateVbox.getChildren().add(day);
        //    final GraphicsContext gc = canvas.getGraphicsContext2D();
        //    final ArcClock blueClock = new ArcClock(20, BLUE1, BLUE2, 200);

        try {
            /**
             * creates object of headlinGrabber after completion of showing
             * every headline
             */
            ArrayList<String> headlines = new HeadlineGrabber().getHeadline();
            ArrayList<Image> images = new ImageGrabber().sendImageList();
            ArrayList<String> texts = new TextGrabber().getText();

            new Thread() {
                @Override
                public void run() {

                    for (int i = 0; i < images.size(); i++) {

                        //System.out.println(images.get(i));
                        updateImageView(images.get(i));

                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(ElectronicBulletinBoardUIController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }.start();

            //imgView.setImage(images.get(i));
//            Thread.sleep(5000);
            /**
             * loops until all the headlines are not shown
             */
            new Thread() {
                @Override
                public void run() {
                    for (int i = 0; i < headlines.size(); i++) {
                        // message = headlines.get(i);
                        //message2 = headlines.get(i + 1);
                        //   updateHeadline(message, message2);

                    }
                }
            }.start();

            /**
             * loops until all the texts are not shown
             */
            new Thread() {
                @Override
                public void run() {
                    int count = 0;
                    for (int i = 0; i < texts.size(); i++) {
                        count++;
                        updatetextContent(texts.get(i));
                        System.out.println(texts.get(i));
                        if (count > 2) {
                            try {
                                Thread.sleep(5000);
                                count = 0;
                                clearVbox();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(ElectronicBulletinBoardUIController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
            }.start();

            // create an animation (update & render loop)
            /*   new AnimationTimer() {
             @Override
             public void handle(long now) {
             // update clocks
             blueClock.update(now);
            
             // clear screen
             gc.clearRect(0, 0, 300, 300);
            
             // gc.cl
             // draw blue clock
             blueClock.draw(gc);
             // save the origin or the current state
             // of the Graphics Context.
             gc.save();
            
             // reset Graphics Context to last saved point.
             // Translate x, y to (0,0)
             gc.restore();
            
             }
             }.start();*/
            System.out.println(
                    "This is the end of the line...");
        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace();
            message2 = message;
        }

    }

}
