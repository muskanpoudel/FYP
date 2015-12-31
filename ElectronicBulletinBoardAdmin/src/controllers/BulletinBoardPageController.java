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
import pojo.BulletinBoards;
import pojo.ContentFeeders;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class BulletinBoardPageController implements Initializable {

    StackPane sp = StuffHolder.getMenuScreenStackPane();

    @FXML
    TableView<BulletinBoards> table;
    @FXML
    TableColumn bbId, bbName, bbLocation, bbStatus;
    @FXML
    Button viewBB, editBB, delBB;

    @FXML
    TextField filterField;

    ObservableList<BulletinBoards> observableList = FXCollections.observableArrayList();

    @FXML
    public void addBulletinBoard() throws IOException {
        StuffHolder.setEditbulletinboard(false);
        loadDesiredPageFromMenu(StuffHolder.BulletinBoardpopup);
    }

    @FXML
    public void viewBulletinInfo() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.BulletinBoardInformation);
    }

    @FXML
    public void editBulletinBoard() throws IOException {
        StuffHolder.setEditbulletinboard(true);
        loadDesiredPageFromMenu(StuffHolder.BulletinBoardpopup);
    }

    @FXML
    public void mouseReleasedFromTable() {

        if (table.getSelectionModel().getSelectedIndex() > -1) {
            viewBB.setDisable(false);
            editBB.setDisable(false);
            delBB.setDisable(false);
            BulletinBoards bb = table.getSelectionModel().getSelectedItem();
            StuffHolder.setBulletinboardinfo(bb);
        } else {
            viewBB.setDisable(true);
            editBB.setDisable(true);
            delBB.setDisable(true);
        }

    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        sp.getChildren().clear();
        sp.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.noticeboard");

            while (rs.next()) {
                BulletinBoards bb = new BulletinBoards();
                bb.setId(rs.getInt("idnoticeboard"));
                bb.setName(rs.getString("noticeboardname"));
                bb.setLocation(rs.getString("noticeboardlocation"));
                bb.setStatus(rs.getString("status"));
                observableList.add(bb);
            }

            bbId.setCellValueFactory(new PropertyValueFactory<BulletinBoards, String>("id"));
            bbName.setCellValueFactory(new PropertyValueFactory<BulletinBoards, String>("name"));
            bbLocation.setCellValueFactory(new PropertyValueFactory<BulletinBoards, String>("location"));
            bbStatus.setCellValueFactory(new PropertyValueFactory<BulletinBoards, String>("status"));

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<BulletinBoards> filteredData = new FilteredList<>(observableList, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(bulletinBoards -> {
                    // If filter text is empty, display all bulletinBoards.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (bulletinBoards.getName().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (bulletinBoards.getLocation().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (bulletinBoards.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<BulletinBoards> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedData);

        } catch (SQLException ex) {
            Logger.getLogger(ContentFeederPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
