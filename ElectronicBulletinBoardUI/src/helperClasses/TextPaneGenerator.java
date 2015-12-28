/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;

/**
 *
 * @author Muskan
 */
public class TextPaneGenerator extends FlowPane {

    public TextPaneGenerator(String title, String text) {

        setPrefSize(211, 185);
        Label titleLbl = new Label(title);
        Label txtLbl = new Label(text);

        if (title.contains("Computing")) {
            setStyle("-fx-background-color: #E54D66; -fx-text-fill:white");
            titleLbl.setStyle("-fx-background-color: #CC344D; -fx-text-fill:white");
        } else if (title.contains("BBA")) {
            setStyle("-fx-background-color: #14CDD1");
            titleLbl.setStyle("-fx-background-color: #11A9AC; -fx-text-fill:white");
        } else if (title.contains("Networking")) {
            setStyle("-fx-background-color: #27AE60");
            titleLbl.setStyle("-fx-background-color: #2ECC71; -fx-text-fill:white");
        } else if (title.contains("Multimedia")) {
            setStyle("-fx-background-color: #D35400");
            titleLbl.setStyle("-fx-background-color: #E67E22; -fx-text-fill:white");
        }

        titleLbl.setPrefSize(211, 44);
        titleLbl.setFont(Font.font("Segoe UI", 18));
        titleLbl.setAlignment(Pos.CENTER);

        txtLbl.setPrefSize(211, 140);
        txtLbl.setStyle("-fx-text-fill:white");
        txtLbl.setFont(Font.font("Segoe UI", 16));
        txtLbl.setWrapText(true);
        txtLbl.setAlignment(Pos.CENTER);

        getChildren().add(titleLbl);
        getChildren().add(txtLbl);

    }

}
