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
public class AddBulletinHeadlineController implements Initializable {

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
        ResultSet rs2;

        java.util.Date postdate
                = java.util.Date.from(postDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlpostDate = new java.sql.Date(postdate.getTime());
        java.util.Date expiredate
                = java.util.Date.from(expireDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlexpireDate = new java.sql.Date(expiredate.getTime());

        if (StuffHolder.isEditbulletin()) {

            if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {
                done = Database.executeUpdate("UPDATE `electronic_bulletin_board`.`contentheadline` "
                        + "SET `headline`='" + headlinemsg.getText() + "'"
                        + ", `title`='" + titlefld.getText() + "' "
                        + ", `publish_date`='" + (java.sql.Date) sqlpostDate + "' "
                        + ", `expire_date`='" + (java.sql.Date) sqlexpireDate + "' "
                        + "WHERE `idcontentheadline`='" + StuffHolder.getBulletinInformation().getBulletinId().substring(4) + "';");
            } else {
                done = Database.executeUpdate("UPDATE `electronic_bulletin_board`.`admincontentheadline` "
                        + "SET `headline`='" + headlinemsg.getText() + "'"
                        + ", `title`='" + titlefld.getText() + "' "
                        + ", `publish_date`='" + (java.sql.Date) sqlpostDate + "' "
                        + ", `expire_date`='" + (java.sql.Date) sqlexpireDate + "' "
                        + "WHERE `idcontentheadline`='" + StuffHolder.getBulletinInformation().getBulletinId().substring(4) + "';");
            }

            if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {
                rs2 = Database.executeQuery("SELECT idcontentheadline FROM \n"
                        + "electronic_bulletin_board.contentheadline where \n"
                        + "idcontentfeeder= " + StuffHolder.getThisAdmin().getAdminid() + " and \n"
                        + "publish_date='" + (java.sql.Date) sqlpostDate + "' and \n"
                        + "expire_date = '" + (java.sql.Date) sqlexpireDate + "' and \n"
                        + "title = \"" + titlefld.getText() + "\";");
            } else {
                rs2 = Database.executeQuery("SELECT idcontentheadline FROM \n"
                        + "electronic_bulletin_board.admincontentheadline where \n"
                        + "idcontentfeeder= " + StuffHolder.getThisAdmin().getAdminid() + " and \n"
                        + "publish_date='" + (java.sql.Date) sqlpostDate + "' and \n"
                        + "expire_date = '" + (java.sql.Date) sqlexpireDate + "' and \n"
                        + "title = \"" + titlefld.getText() + "\";");
            }

        } else {
            done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`admincontentheadline` \n"
                    + "(`idcontentfeeder`, `publish_date`, `expire_date`, `headline`, `title`)\n"
                    + " VALUES ('" + StuffHolder.getThisAdmin().getAdminid() + "', '" + (java.sql.Date) sqlpostDate + "', '" + (java.sql.Date) sqlexpireDate + "', '" + headlinemsg.getText() + "', '" + titlefld.getText() + "');");

            rs2 = Database.executeQuery("SELECT idcontentheadline FROM \n"
                    + "electronic_bulletin_board.admincontentheadline where \n"
                    + "idcontentfeeder= " + StuffHolder.getThisAdmin().getAdminid() + " and \n"
                    + "publish_date='" + (java.sql.Date) sqlpostDate + "' and \n"
                    + "expire_date = '" + (java.sql.Date) sqlexpireDate + "' and \n"
                    + "title = \"" + titlefld.getText() + "\";");
        }

        while (rs2.next()) {
            contentId = rs2.getInt("idcontentheadline");
        }

        if (StuffHolder.isEditbulletin()) {
            if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {
                done = Database.executeUpdate("DELETE FROM `electronic_bulletin_board`.`noticeboard_content` \n"
                        + "WHERE `idcontenttype` = '2' and `idcontent` = '" + contentId + "';");
            } else {
                done = Database.executeUpdate("DELETE FROM `electronic_bulletin_board`.`noticeboard_admincontent` \n"
                        + "WHERE `idcontenttype` = '2' and `idcontent` = '" + contentId + "';");
            }
        }

        for (int i = 0; i < checkLists.size(); i++) {
            if (checkLists.get(i).isSelected()) {
                String[] bulletinBoardParts = checkLists.get(i).getText().split("\\(");
                ResultSet rs = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard where noticeboardname=\"" + bulletinBoardParts[0] + "\"");

                while (rs.next()) {
                    noticeboardID = rs.getInt("idnoticeboard");
                }

                /**
                 * if mode is set to edit
                 */
                if (StuffHolder.isEditbulletin()) {

                    /**
                     * first check if admin is editing admin's post or content
                     * feeder's
                     */
                    if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {    //content feeder
                        done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`noticeboard_content` "
                                + "(`idnoticeboard`, `idcontenttype`, `idcontent`) VALUES"
                                + " (" + noticeboardID + ", " + 2 + ", " + contentId + ")");
                    } else {    //admin
                        done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`noticeboard_admincontent` "
                                + "(`idnoticeboard`, `idcontenttype`, `idcontent`) VALUES"
                                + " (" + noticeboardID + ", " + 2 + ", " + contentId + ")");
                    }

                    /**
                     * if mode is set to add... this is admin interface so
                     * values will be saved to admin-table by default.
                     */
                } else {
                    done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`noticeboard_admincontent` "
                            + "(`idnoticeboard`, `idcontenttype`, `idcontent`) VALUES"
                            + " (" + noticeboardID + ", " + 2 + ", " + contentId + ")");
                }
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
                checkboxes.add(cb);
                checkLists.add(cb);
            }

            if (StuffHolder.isEditbulletin()) {
                titleOfPage.setText("Update your headline from this page");
                addupdateBtn.setText("Update");

                ResultSet rs2;
                if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {
                    rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.contentheadline where idcontentheadline = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                    rs2 = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard_content where idcontenttype = 2 and idcontent = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                } else {
                    rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.admincontentheadline where idcontentheadline = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                    rs2 = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard_admincontent where idcontenttype = 2 and idcontent = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                }

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
            Logger.getLogger(AddBulletinHeadlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
