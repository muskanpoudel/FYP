/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.Database;
import helperClasses.StuffHolder;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import pojo.BulletinInfo;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class BulletinsPageController implements Initializable {

    @FXML
    TableView<BulletinInfo> table;
    @FXML
    TableColumn bid, btitle, nbname, nblocation, cfid, cfname, btype, pubdate;
    @FXML
    Button viewBulletin, editBulletin, deleteBulletin;
    @FXML
    TextField filterField;

    StackPane menuPageStackPane = StuffHolder.getMenuScreenStackPane();

    ObservableList<BulletinInfo> observableList = FXCollections.observableArrayList();

    public void showBulletinAddingPage() throws IOException {
        StuffHolder.setEditbulletin(false);
        loadDesiredPageFromMenu(StuffHolder.BulletinAddingScreen);
    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        menuPageStackPane.getChildren().clear();
        menuPageStackPane.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @FXML
    public void editPressed() throws IOException {

        if (StuffHolder.getBulletinInformation().getType() == "Image") {
            loadDesiredPageFromMenu(StuffHolder.AddBulletinImage);
        } else if (StuffHolder.getBulletinInformation().getType() == "Headline") {
            loadDesiredPageFromMenu(StuffHolder.AddBulletinHeadline);
        } else if (StuffHolder.getBulletinInformation().getType() == "Text") {
            loadDesiredPageFromMenu(StuffHolder.AddBulletinText);
        }

    }

    //when mouse is released from table
    @FXML
    public void mouseReleasedFromTable() {
        StuffHolder.setEditbulletin(true);
        BulletinInfo bi = table.getSelectionModel().getSelectedItem();
        StuffHolder.setBulletinInformation(bi);
        if (table.getSelectionModel().getSelectedIndex() > -1) {
            viewBulletin.setDisable(false);
            editBulletin.setDisable(false);
            deleteBulletin.setDisable(false);
        } else {
            viewBulletin.setDisable(true);
            editBulletin.setDisable(true);
            deleteBulletin.setDisable(true);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ObservableList<BulletinInfo> observableList = FXCollections.observableArrayList();
            //for image content of content feeder
            ResultSet rs = Database.executeQuery("SELECT DISTINCT contentfeeders.idContentFeeders, contentfeeders.firstname, contentfeeders.lastname, contentimage.idcontentimage, contentimage.title, contentimage.publish_date ,noticeboard.noticeboardname, noticeboard.noticeboardlocation \n"
                    + "FROM electronic_bulletin_board.contentimage, electronic_bulletin_board.noticeboard, electronic_bulletin_board.noticeboard_content, electronic_bulletin_board.contentfeeders\n"
                    + "where contentimage.idcontentimage = noticeboard_content.idcontent and \n"
                    + "noticeboard.idnoticeboard = noticeboard_content.idnoticeboard and \n"
                    + "contentfeeders.idContentFeeders = contentimage.idcontentfeeder;");
            while (rs.next()) {
                BulletinInfo bi = new BulletinInfo();
                bi.setBulletinId("CFCI" + String.valueOf(rs.getInt("idcontentimage")));
                bi.setTitle(rs.getString("title"));
                bi.setNoticeboardName(rs.getString("noticeboardname"));
                bi.setNoticeboardLocation(rs.getString("noticeboardlocation"));
                bi.setDate(String.valueOf(rs.getDate("publish_date")));
                bi.setType("Image");
                bi.setContentFeederId(String.valueOf(rs.getInt("idContentFeeders")));
                bi.setContentFeederName(rs.getString("firstname") + " " + rs.getString("lastname"));
                observableList.add(bi);
            }
            //for image content of Admin
            rs = Database.executeQuery("SELECT DISTINCT admin.first_name, admin.last_name, admin.admin_id, admincontentimage.idcontentimage, admincontentimage.title, admincontentimage.publish_date ,noticeboard.noticeboardname, noticeboard.noticeboardlocation \n"
                    + "FROM electronic_bulletin_board.admincontentimage, electronic_bulletin_board.noticeboard, electronic_bulletin_board.noticeboard_admincontent, electronic_bulletin_board.admin\n"
                    + "where admincontentimage.idcontentimage = noticeboard_admincontent.idcontent and \n"
                    + "noticeboard.idnoticeboard = noticeboard_admincontent.idnoticeboard and \n"
                    + "admin.admin_id = admincontentimage.idcontentfeeder;");
            while (rs.next()) {
                BulletinInfo bi = new BulletinInfo();
                bi.setBulletinId("ADCI" + String.valueOf(rs.getInt("idcontentimage")));
                bi.setTitle(rs.getString("title"));
                bi.setNoticeboardName(rs.getString("noticeboardname"));
                bi.setNoticeboardLocation(rs.getString("noticeboardlocation"));
                bi.setDate(String.valueOf(rs.getDate("publish_date")));
                bi.setType("Image");
                bi.setContentFeederId(String.valueOf(rs.getInt("admin_id")));
                bi.setContentFeederName(rs.getString("first_name") + " " + rs.getString("last_name"));
                observableList.add(bi);
            }
            rs.close();

            //for headline content of content feeder
            rs = Database.executeQuery("SELECT DISTINCT contentfeeders.idContentFeeders, contentfeeders.firstname, contentfeeders.lastname, contentheadline.idcontentheadline, contentheadline.title, contentheadline.publish_date ,noticeboard.noticeboardname, noticeboard.noticeboardlocation \n"
                    + "FROM electronic_bulletin_board.contentheadline, electronic_bulletin_board.noticeboard, electronic_bulletin_board.noticeboard_content, electronic_bulletin_board.contentfeeders\n"
                    + "where contentheadline.idcontentheadline = noticeboard_content.idcontent and \n"
                    + "noticeboard.idnoticeboard = noticeboard_content.idnoticeboard and \n"
                    + "contentfeeders.idContentFeeders = contentheadline.idcontentfeeder;");
            while (rs.next()) {
                BulletinInfo bi = new BulletinInfo();
                bi.setBulletinId("CFCH" + String.valueOf(rs.getInt("idcontentheadline")));
                bi.setTitle(rs.getString("title"));
                bi.setNoticeboardName(rs.getString("noticeboardname"));
                bi.setNoticeboardLocation(rs.getString("noticeboardlocation"));
                bi.setDate(String.valueOf(rs.getDate("publish_date")));
                bi.setType("Headline");
                bi.setContentFeederId(String.valueOf(rs.getInt("idContentFeeders")));
                bi.setContentFeederName(rs.getString("firstname") + " " + rs.getString("lastname"));
                observableList.add(bi);
            }
            rs.close();

            //for headline content of Admin
            rs = Database.executeQuery("SELECT DISTINCT admin.first_name, admin.last_name, admin.admin_id, admincontentheadline.idcontentheadline, admincontentheadline.title, admincontentheadline.publish_date ,noticeboard.noticeboardname, noticeboard.noticeboardlocation \n"
                    + "FROM electronic_bulletin_board.admincontentheadline, electronic_bulletin_board.noticeboard, electronic_bulletin_board.noticeboard_admincontent, electronic_bulletin_board.admin\n"
                    + "where admincontentheadline.idcontentheadline = noticeboard_admincontent.idcontent and \n"
                    + "noticeboard.idnoticeboard = noticeboard_admincontent.idnoticeboard and \n"
                    + "admin.admin_id = admincontentheadline.idcontentfeeder;");
            while (rs.next()) {
                BulletinInfo bi = new BulletinInfo();
                bi.setBulletinId("ADCH" + String.valueOf(rs.getInt("idcontentheadline")));
                bi.setTitle(rs.getString("title"));
                bi.setNoticeboardName(rs.getString("noticeboardname"));
                bi.setNoticeboardLocation(rs.getString("noticeboardlocation"));
                bi.setDate(String.valueOf(rs.getDate("publish_date")));
                bi.setType("Headline");
                bi.setContentFeederId(String.valueOf(rs.getInt("admin_id")));
                bi.setContentFeederName(rs.getString("first_name") + " " + rs.getString("last_name"));
                observableList.add(bi);
            }

            //for text content of content feeder
            rs = Database.executeQuery("SELECT DISTINCT contentfeeders.idContentFeeders, contentfeeders.firstname, contentfeeders.lastname, contenttext.idcontenttext, contenttext.title, contenttext.publish_date ,noticeboard.noticeboardname, noticeboard.noticeboardlocation \n"
                    + "FROM electronic_bulletin_board.contenttext, electronic_bulletin_board.noticeboard, electronic_bulletin_board.noticeboard_content, electronic_bulletin_board.contentfeeders\n"
                    + "where contenttext.idcontenttext = noticeboard_content.idcontent and \n"
                    + "noticeboard.idnoticeboard = noticeboard_content.idnoticeboard and \n"
                    + "contentfeeders.idContentFeeders = contenttext.idcontentfeeder;");
            while (rs.next()) {
                BulletinInfo bi = new BulletinInfo();
                bi.setBulletinId("CFCT" + String.valueOf(rs.getInt("idcontenttext")));
                bi.setTitle(rs.getString("title"));
                bi.setNoticeboardName(rs.getString("noticeboardname"));
                bi.setNoticeboardLocation(rs.getString("noticeboardlocation"));
                bi.setDate(String.valueOf(rs.getDate("publish_date")));
                bi.setType("Text");
                bi.setContentFeederId(String.valueOf(rs.getInt("idContentFeeders")));
                bi.setContentFeederName(rs.getString("firstname") + " " + rs.getString("lastname"));
                observableList.add(bi);
            }
            rs.close();

            //for text content of Admin
            rs = Database.executeQuery("SELECT DISTINCT admin.first_name, admin.last_name, admin.admin_id, admincontenttext.idcontenttext, admincontenttext.title, admincontenttext.publish_date ,noticeboard.noticeboardname, noticeboard.noticeboardlocation \n"
                    + "FROM electronic_bulletin_board.admincontenttext, electronic_bulletin_board.noticeboard, electronic_bulletin_board.noticeboard_admincontent, electronic_bulletin_board.admin\n"
                    + "where admincontenttext.idcontenttext = noticeboard_admincontent.idcontent and \n"
                    + "noticeboard.idnoticeboard = noticeboard_admincontent.idnoticeboard and \n"
                    + "admin.admin_id = admincontenttext.idcontentfeeder;");
            while (rs.next()) {
                BulletinInfo bi = new BulletinInfo();
                bi.setBulletinId("ADCT" + String.valueOf(rs.getInt("idcontenttext")));
                bi.setTitle(rs.getString("title"));
                bi.setNoticeboardName(rs.getString("noticeboardname"));
                bi.setNoticeboardLocation(rs.getString("noticeboardlocation"));
                bi.setDate(String.valueOf(rs.getDate("publish_date")));
                bi.setType("Text");
                bi.setContentFeederId(String.valueOf(rs.getInt("admin_id")));
                bi.setContentFeederName(rs.getString("first_name") + " " + rs.getString("last_name"));
                observableList.add(bi);
            }
            rs.close();
            bid.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("bulletinId"));
            btitle.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("title"));
            nbname.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("noticeboardName"));
            nblocation.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("noticeboardLocation"));
            pubdate.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("date"));
            btype.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("type"));
            cfid.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("contentFeederId"));
            cfname.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("contentFeederName"));

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<BulletinInfo> filteredData = new FilteredList<>(observableList, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(bulletinInfo -> {
                    // If filter text is empty, display all bulletinBoards.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (bulletinInfo.getDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (bulletinInfo.getNoticeboardLocation().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (bulletinInfo.getNoticeboardName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (bulletinInfo.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (bulletinInfo.getType().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (bulletinInfo.getContentFeederName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (bulletinInfo.getBulletinId().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<BulletinInfo> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedData);

        } catch (SQLException ex) {
            Logger.getLogger(BulletinsPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
