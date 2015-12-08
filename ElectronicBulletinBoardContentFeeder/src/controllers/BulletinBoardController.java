/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class BulletinBoardController implements Initializable {

    @FXML
    FlowPane flowPane;

    public Pane returnCustomizedPane() {
        Pane pane = new Pane();
        pane.setPrefSize(275, 120);
        pane.setStyle("-fx-background-color:#c7b29b");
        Image img = new Image("/img/tv.png");
        ImageView imgView = new ImageView(img);
        imgView.setFitHeight(80);
        imgView.setFitWidth(80);
        imgView.setLayoutX(14);
        imgView.setLayoutY(20);
        pane.getChildren().add(imgView);
        return pane;
    }

    @Override
    public void initialize(java.net.URL location, ResourceBundle resources) {

        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.noticeboard");
            if (!rs.isBeforeFirst()) {
                System.out.println("No bulletin boards found... please try again...");
            } else {
                while (rs.next()) {
                    Pane bbPane = returnCustomizedPane();
                    Label id = new Label("ID: "+String.valueOf(rs.getInt("idnoticeboard")));
                    Label name = new Label("Name: "+rs.getString("noticeboardname"));
                    Label place = new Label("Location: "+rs.getString("noticeboardlocation"));
                    id.setFont(Font.font("System", FontWeight.BOLD, 12));
                    name.setFont(Font.font("System", FontWeight.BOLD, 12));
                    place.setFont(Font.font("System", FontWeight.BOLD, 12));
                    id.setLayoutX(106);
                    id.setLayoutY(25);
                    name.setLayoutX(106);
                    name.setLayoutY(50);
                    place.setLayoutX(106);
                    place.setLayoutY(75);

                    bbPane.getChildren().add(id);
                    bbPane.getChildren().add(name);
                    bbPane.getChildren().add(place);

                    flowPane.getChildren().add(bbPane);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(BulletinBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
