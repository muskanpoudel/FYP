/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.Database;
import helperClasses.StuffHolder;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class ContentAddTextController implements Initializable {

    @FXML
    FlowPane flowPane;
    @FXML
    TextArea msgArea;
    @FXML
    ComboBox combofld, combosem;
    @FXML
    DatePicker postDate, expireDate;
    @FXML
    Label infoLbl, titleOfPage;
    @FXML
    Button addupdateBtn;

    ArrayList<CheckBox> checkLists = new ArrayList<>();

    String title = null;

    @FXML
    public void donepressed() throws SQLException {
        title = combofld.getSelectionModel().getSelectedItem().toString() + " (" + combosem.getSelectionModel().getSelectedItem().toString() + ")";
        int noticeboardID = 0, contentId = 0;
        boolean done = false;

        java.util.Date postdate
                = java.util.Date.from(postDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlpostDate = new java.sql.Date(postdate.getTime());
        java.util.Date expiredate
                = java.util.Date.from(expireDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlexpireDate = new java.sql.Date(expiredate.getTime());

        if (StuffHolder.isEditbulletin()) {
            done = Database.executeUpdate("UPDATE `electronic_bulletin_board`.`contenttext` SET "
                    + "`publish_date`='" + (java.sql.Date) sqlpostDate + "', "
                    + "`expire_date`='" + (java.sql.Date) sqlexpireDate + "', "
                    + "`title`='" + title + "', "
                    + "`text`='" + msgArea.getText() + "' WHERE "
                    + "`idcontenttext`='" + StuffHolder.getBulletinInformation().getBulletinId().substring(4) + "';");
        } else {
            done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`contenttext` "
                    + "(`idcontentfeeder`, `publish_date`, `expire_date`, `title`, `text`) VALUES "
                    + "('" + StuffHolder.getCurrentUser().getUserid() + "', '" + (java.sql.Date) sqlpostDate + "', '" + (java.sql.Date) sqlexpireDate + "', '" + title + "', '" + msgArea.getText() + "');");
        }
        ResultSet rs2 = Database.executeQuery("SELECT idcontenttext FROM \n"
                + "electronic_bulletin_board.contenttext where \n"
                + "idcontentfeeder= " + StuffHolder.getCurrentUser().getUserid() + " and \n"
                + "publish_date='" + (java.sql.Date) sqlpostDate + "' and \n"
                + "expire_date = '" + (java.sql.Date) sqlexpireDate + "' and \n"
                + "title = \"" + title + "\";");

        while (rs2.next()) {
            contentId = rs2.getInt("idcontenttext");
        }
        done = Database.executeUpdate("DELETE FROM `electronic_bulletin_board`.`noticeboard_content` \n"
                + "WHERE `idcontenttype` = '3' and `idcontent` = '" + contentId + "';");

        for (int i = 0; i < checkLists.size(); i++) {
            if (checkLists.get(i).isSelected()) {
                String[] bulletinBoardParts = checkLists.get(i).getText().split("\\(");
                ResultSet rs = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard where noticeboardname=\"" + bulletinBoardParts[0] + "\"");
                while (rs.next()) {
                    noticeboardID = rs.getInt("idnoticeboard");
                }
                done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`noticeboard_content` "
                        + "(`idnoticeboard`, `idcontenttype`, `idcontent`) VALUES"
                        + " (" + noticeboardID + ", " + 3 + ", " + contentId + ")");

            }
        }
        if (done) {
            infoLbl.setText("Successfully uploaded!!!");
        } else {
            infoLbl.setText("Unsucessful... try again!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.noticeboard");

            while (rs.next()) {
                CheckBox cb = new CheckBox(rs.getString("noticeboardname") + " (" + rs.getString("noticeboardlocation") + ")");
                flowPane.getChildren().add(cb);
                checkboxes.add(cb);
                checkLists.add(cb);
            }

            if (StuffHolder.isEditbulletin()) {
                titleOfPage.setText("Update your text from this page");
                addupdateBtn.setText("Update");

                ResultSet rs2;
                rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.contenttext where idcontenttext = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                rs2 = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard_content where idcontenttype = 3 and idcontent = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));

                while (rs.next()) {
                    String[] titlePieces = rs.getString("title").split("\\(");
                    String[] sem = titlePieces[1].split("\\)");
                    msgArea.setText(rs.getString("text"));
                    combofld.setValue(titlePieces[0].replaceAll("\\s+", ""));
                    combosem.setValue(sem[0]);
                    expireDate.setValue(rs.getDate("expire_date").toLocalDate());
                    postDate.setValue(rs.getDate("publish_date").toLocalDate());
                }

                while (rs2.next()) {
                    rs = Database.executeQuery("select noticeboardname, noticeboardlocation from electronic_bulletin_board.noticeboard where idnoticeboard = " + rs2.getInt("idnoticeboard"));

                    while (rs.next()) {
                        String checkboxtext = rs.getString("noticeboardname") + " (" + rs.getString("noticeboardlocation") + ")";

                        for (int i = 0; i < checkboxes.size(); i++) {
                            if (checkboxtext == checkboxes.get(i).getText() || checkboxtext.equals(checkboxes.get(i).getText())) {
                                checkboxes.get(i).setSelected(true);
                            }
                        }
                    }
                }

            } else {
                titleOfPage.setText("Add Content Feeder's Information");
                titleOfPage.setText("Add");
            }

        } catch (SQLException ex) {
            Logger.getLogger(ContentAddimageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
