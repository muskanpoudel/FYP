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
public class BulletinController implements Initializable {

    StackPane sp = StuffHolder.getSpane();

    @FXML
    TableView<BulletinInfo> tableView;
    @FXML
    TableColumn bullId, bullTitle, nbName, nbLocation, pubDate, bullType;
    @FXML
    Button editBulletin, deleteBulletin;

    @FXML
    TextField filterField;

    ObservableList<BulletinInfo> observableList = FXCollections.observableArrayList();

    @FXML
    public void addPressed() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.BulletinAddingScreen);
    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        sp.getChildren().clear();
        sp.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
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
        BulletinInfo bi = tableView.getSelectionModel().getSelectedItem();
        StuffHolder.setBulletinInformation(bi);
        if (tableView.getSelectionModel().getSelectedIndex() > -1) {
//            viewBulletin.setDisable(false);
            editBulletin.setDisable(false);
            deleteBulletin.setDisable(false);
        } else {
//            viewBulletin.setDisable(true);
            editBulletin.setDisable(true);
            deleteBulletin.setDisable(true);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ObservableList<BulletinInfo> observableList = FXCollections.observableArrayList();

            //for image content of content feeder
            ResultSet rs = Database.executeQuery("SELECT DISTINCT contentimage.idcontentimage, contentimage.title, contentimage.publish_date ,noticeboard.noticeboardname, noticeboard.noticeboardlocation"
                    + " FROM electronic_bulletin_board.contentimage, electronic_bulletin_board.noticeboard, electronic_bulletin_board.noticeboard_content\n"
                    + "where contentimage.idcontentimage = noticeboard_content.idcontent and \n"
                    + "noticeboard.idnoticeboard = noticeboard_content.idnoticeboard and \n"
                    + "noticeboard_content.idcontenttype = 1 and\n"
                    + "contentimage.idcontentfeeder = " + StuffHolder.getCurrentUser().getUserid());
            while (rs.next()) {
                BulletinInfo bi = new BulletinInfo();
                bi.setBulletinId("CFCI" + String.valueOf(rs.getInt("idcontentimage")));
                bi.setTitle(rs.getString("title"));
                bi.setNoticeboardName(rs.getString("noticeboardname"));
                bi.setNoticeboardLocation(rs.getString("noticeboardlocation"));
                bi.setDate(String.valueOf(rs.getDate("publish_date")));
                bi.setType("Image");
                observableList.add(bi);
            }
            rs.close();

            //for headline content of content feeder
            rs = Database.executeQuery("SELECT DISTINCT contentheadline.idcontentheadline, contentheadline.title, contentheadline.publish_date ,noticeboard.noticeboardname, noticeboard.noticeboardlocation"
                    + " FROM electronic_bulletin_board.contentheadline, electronic_bulletin_board.noticeboard, electronic_bulletin_board.noticeboard_content\n"
                    + "where contentheadline.idcontentheadline = noticeboard_content.idcontent and \n"
                    + "noticeboard.idnoticeboard = noticeboard_content.idnoticeboard and \n"
                    + "noticeboard_content.idcontenttype = 2 and\n"
                    + "contentheadline.idcontentfeeder = " + StuffHolder.getCurrentUser().getUserid());
            while (rs.next()) {
                BulletinInfo bi = new BulletinInfo();
                bi.setBulletinId("CFCH" + String.valueOf(rs.getInt("idcontentheadline")));
                bi.setTitle(rs.getString("title"));
                bi.setNoticeboardName(rs.getString("noticeboardname"));
                bi.setNoticeboardLocation(rs.getString("noticeboardlocation"));
                bi.setDate(String.valueOf(rs.getDate("publish_date")));
                bi.setType("Headline");
                observableList.add(bi);
            }
            rs.close();
            //for text content of content feeder
            rs = Database.executeQuery("SELECT DISTINCT contenttext.idcontenttext, contenttext.title, contenttext.publish_date ,noticeboard.noticeboardname, noticeboard.noticeboardlocation"
                    + " FROM electronic_bulletin_board.contenttext, electronic_bulletin_board.noticeboard, electronic_bulletin_board.noticeboard_content\n"
                    + "where contenttext.idcontenttext = noticeboard_content.idcontent and \n"
                    + "noticeboard.idnoticeboard = noticeboard_content.idnoticeboard and \n"
                    + "noticeboard_content.idcontenttype = 3 and\n"
                    + "contenttext.idcontentfeeder = " + StuffHolder.getCurrentUser().getUserid());
            while (rs.next()) {
                BulletinInfo bi = new BulletinInfo();
                bi.setBulletinId("CFCT" + String.valueOf(rs.getInt("idcontenttext")));
                bi.setTitle(rs.getString("title"));
                bi.setNoticeboardName(rs.getString("noticeboardname"));
                bi.setNoticeboardLocation(rs.getString("noticeboardlocation"));
                bi.setDate(String.valueOf(rs.getDate("publish_date")));
                bi.setType("Text");
                observableList.add(bi);
            }
            rs.close();

            bullId.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("bulletinId"));
            bullTitle.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("title"));
            nbName.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("noticeboardName"));
            nbLocation.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("noticeboardLocation"));
            pubDate.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("date"));
            bullType.setCellValueFactory(new PropertyValueFactory<BulletinInfo, String>("type"));

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
                    }else if (bulletinInfo.getBulletinId().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<BulletinInfo> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(tableView.comparatorProperty());

            tableView.setItems(sortedData);

        } catch (SQLException ex) {
            Logger.getLogger(BulletinController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
