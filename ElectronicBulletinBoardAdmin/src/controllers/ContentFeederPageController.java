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
import pojo.ContentFeeders;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class ContentFeederPageController implements Initializable {

    StackPane menuPageStackPane = StuffHolder.getMenuScreenStackPane();

    @FXML
    TableColumn id, fname, lname, uname, mail, stat;
    @FXML
    TableView<ContentFeeders> table;
    @FXML
    Button viewCF, editCF, delCF, susCF;
    @FXML
    TextField filterField;

    @FXML
    public void addNewContentFeeder() throws IOException {
        StuffHolder.setEditcontentFeeder(false);
        loadDesiredPageFromMenu(StuffHolder.ContentFeederAddingScreen);
    }

    @FXML
    public void viewContentFeeder() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.ContentFeederViewScreen);
    }

    //when mouse is released from table
    @FXML
    public void mouseReleasedFromTable() {

        if (table.getSelectionModel().getSelectedIndex() > -1) {
            viewCF.setDisable(false);
            editCF.setDisable(false);
            delCF.setDisable(false);
            susCF.setDisable(false);
            ContentFeeders cf = table.getSelectionModel().getSelectedItem();
            StuffHolder.setContetFeeders(cf);
        } else {
            viewCF.setDisable(true);
            editCF.setDisable(true);
            delCF.setDisable(true);
            susCF.setDisable(true);
        }

    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        menuPageStackPane.getChildren().clear();
        menuPageStackPane.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @FXML
    public void editPressed() throws IOException {
        StuffHolder.setEditcontentFeeder(true);
        loadDesiredPageFromMenu(StuffHolder.ContentFeederAddingScreen);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ObservableList<ContentFeeders> observableList = FXCollections.observableArrayList();

            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.contentfeeders");

            while (rs.next()) {
                ContentFeeders cf = new ContentFeeders();
                cf.setUserid(rs.getInt("idContentFeeders"));
                cf.setUsername(rs.getString("username"));
                cf.setFirstname(rs.getString("firstname"));
                cf.setLastname(rs.getString("lastname"));
                cf.setEmail(rs.getString("email"));
                cf.setStatus(rs.getString("status"));
                observableList.add(cf);
            }

            id.setCellValueFactory(new PropertyValueFactory<ContentFeeders, String>("userid"));
            uname.setCellValueFactory(new PropertyValueFactory<ContentFeeders, String>("username"));
            mail.setCellValueFactory(new PropertyValueFactory<ContentFeeders, String>("email"));
            fname.setCellValueFactory(new PropertyValueFactory<ContentFeeders, String>("firstname"));
            lname.setCellValueFactory(new PropertyValueFactory<ContentFeeders, String>("lastname"));
            stat.setCellValueFactory(new PropertyValueFactory<ContentFeeders, String>("status"));

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<ContentFeeders> filteredData = new FilteredList<>(observableList, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(contentFeeders -> {
                    // If filter text is empty, display all bulletinBoards.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (contentFeeders.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (contentFeeders.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (contentFeeders.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (contentFeeders.getStatus().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (String.valueOf(contentFeeders.getUserid()).toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (contentFeeders.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<ContentFeeders> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedData);

        } catch (SQLException ex) {
            Logger.getLogger(ContentFeederPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
