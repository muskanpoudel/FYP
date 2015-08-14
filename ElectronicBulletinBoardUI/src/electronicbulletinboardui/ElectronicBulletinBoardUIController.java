/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicbulletinboardui;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.Interpolator;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.TranslateTransitionBuilder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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
    @FXML VBox timeDateVbox;
    @FXML Label textMarquee1, textMarquee2;
    
//    @FXML
//    TextField textMarquee;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    //    final GraphicsContext gc = canvas.getGraphicsContext2D();
    //    final ArcClock blueClock = new ArcClock(20, BLUE1, BLUE2, 200);
              String message
         = "Earthrise at Christmas: "
         + "[Forty] years ago this Christmas, a turbulent world "
         + "looked to the heavens for a unique view of our home "
         + "planet. This photo of Earthrise over the lunar horizon "
         + "was taken by the Apollo 8 crew in December 1968, showing "
         + "Earth for the first time as it appears from deep space. "
         + "Astronauts Frank Borman, Jim Lovell and William Anders "
         + "had become the first humans to leave Earth orbit, "
         + "entering lunar orbit on Christmas Eve. In a historic live "
         + "broadcast that night, the crew took turns reading from "
         + "the Book of Genesis, closing with a holiday wish from "
         + "Commander Borman: \"We close with good night, good luck, "
         + "a Merry Christmas, and God bless all of you -- all of "
         + "you on the good Earth.\"";

//         textMarquee.setPrefWidth(message.length());
         textMarquee1.setText(message);
         textMarquee2.setText(message);
        
         // Provides the animated scrolling behavior for the text
         double textToMove = (message.length()*14)+1366;
         TranslateTransition transTransition1 = TranslateTransitionBuilder.create()
         .duration(new Duration(textToMove*5))
         .node(textMarquee1)
         .toX(-textToMove)
         .interpolator(Interpolator.LINEAR)
         .cycleCount(Timeline.INDEFINITE)
         .build();
         
         double textToMove2 = (message.length()*14)+1366;
         TranslateTransition transTransition2 = TranslateTransitionBuilder.create()
         .duration(new Duration(textToMove2*5))
         .node(textMarquee2)
         .toX(-textToMove2)
         .interpolator(Interpolator.LINEAR)
         .cycleCount(Timeline.INDEFINITE)
         .build();

         transTransition1.play();
         transTransition2.play();
         
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
        Clock c = new Clock();
        ArrayList clockList =new ArrayList();
        clockList = c.returnClock();
        
        clockPane.getChildren().add((Group)clockList.get(0));
        //lvlTime.setText((Label)clockList.get(0));
        timeDateVbox.getChildren().add((Label)clockList.get(1));
        Label date = new Label("8-14-2015");
        Label day = new Label("Sunday");
        date.setId("digitalClock");
        day.setId("digitalClock");
        timeDateVbox.getChildren().add(date);
        timeDateVbox.getChildren().add(day);
        
        
    }

}
