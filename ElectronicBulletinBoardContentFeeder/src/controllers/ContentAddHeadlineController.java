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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class ContentAddHeadlineController implements Initializable {

    @FXML
    TextField titlefld;
    @FXML
    TextArea headlinemsg;
    @FXML
    DatePicker postDate, expireDate;
    @FXML
    FlowPane flowPane;
    @FXML
    Label msglbl, titleOfPage;
    @FXML
    Button addupdateBtn;

    ArrayList<CheckBox> checkLists = new ArrayList<>();

    @FXML
    public void donePressed() throws SQLException {
        int noticeboardID = 0, contentId = 0;
        boolean done = false;
        java.util.Date postdate
                = java.util.Date.from(postDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlpostDate = new java.sql.Date(postdate.getTime());
        java.util.Date expiredate
                = java.util.Date.from(expireDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlexpireDate = new java.sql.Date(expiredate.getTime());

        if (StuffHolder.isEditbulletin()) {

            done = Database.executeUpdate("UPDATE `electronic_bulletin_board`.`contentheadline` "
                    + "SET `headline`='" + headlinemsg.getText() + "'"
                    + ", `title`='" + titlefld.getText() + "' "
                    + ", `publish_date`='" + (java.sql.Date) sqlpostDate + "' "
                    + ", `expire_date`='" + (java.sql.Date) sqlexpireDate + "' "
                    + "WHERE `idcontentheadline`='" + StuffHolder.getBulletinInformation().getBulletinId().substring(4) + "';");

        } else {
            done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`contentheadline` \n"
                    + "(`idcontentfeeder`, `publish_date`, `expire_date`, `headline`, `title`)\n"
                    + " VALUES ('" + StuffHolder.getCurrentUser().getUserid() + "', '" + (java.sql.Date) sqlpostDate + "', '" + (java.sql.Date) sqlexpireDate + "', '" + headlinemsg.getText() + "', '" + titlefld.getText() + "');");
        }
        ResultSet rs2 = Database.executeQuery("SELECT idcontentheadline FROM \n"
                + "electronic_bulletin_board.contentheadline where \n"
                + "idcontentfeeder= " + StuffHolder.getCurrentUser().getUserid() + " and \n"
                + "publish_date='" + (java.sql.Date) sqlpostDate + "' and \n"
                + "expire_date = '" + (java.sql.Date) sqlexpireDate + "' and \n"
                + "title = \"" + titlefld.getText() + "\";");

        while (rs2.next()) {
            contentId = rs2.getInt("idcontentheadline");
        }
        done = Database.executeUpdate("DELETE FROM `electronic_bulletin_board`.`noticeboard_content` \n"
                + "WHERE `idcontenttype` = '2' and `idcontent` = '" + contentId + "';");

        for (int i = 0; i < checkLists.size(); i++) {
            if (checkLists.get(i).isSelected()) {
                String[] bulletinBoardParts = checkLists.get(i).getText().split("\\(");
                ResultSet rs = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard where noticeboardname=\"" + bulletinBoardParts[0] + "\"");
                while (rs.next()) {
                    noticeboardID = rs.getInt("idnoticeboard");
                }
                done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`noticeboard_content` "
                        + "(`idnoticeboard`, `idcontenttype`, `idcontent`) VALUES"
                        + " (" + noticeboardID + ", " + 2 + ", " + contentId + ")");

            }
        }
        if (done) {
            if (StuffHolder.isEditbulletin()) {
                msglbl.setText("Successfully Changed Values!!!");
            } else {
                msglbl.setText("Successfully Uploaded!!!");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //for storing checkbox as soon as screen loads
        ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.noticeboard");

            while (rs.next()) {
                CheckBox cb = new CheckBox(rs.getString("noticeboardname") + " (" + rs.getString("noticeboardlocation") + ")");
                flowPane.getChildren().add(cb);
                checkLists.add(cb);
                checkboxes.add(cb);
            }

            /**
             * if edit bulletin is pressed instead of add
             */
            if (StuffHolder.isEditbulletin()) {
                titleOfPage.setText("Update your headline from this page");
                addupdateBtn.setText("Update");

                ResultSet rs2;
                rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.contentheadline where idcontentheadline = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                rs2 = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard_content where idcontenttype = 2 and idcontent = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));

                while (rs.next()) {
                    titlefld.setText(rs.getString("title"));
                    headlinemsg.setText(rs.getString("headline"));
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
