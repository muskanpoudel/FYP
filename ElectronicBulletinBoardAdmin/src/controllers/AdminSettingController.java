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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import pojo.Admin;
import pojo.ContentFeeders;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class AdminSettingController implements Initializable {

    @FXML
    TextField fnamefld, lnamefld, unamefld, passfld, cpassfld, pinfld, cpinfld, emailfld;
    @FXML
    Button addEditBtn;
    @FXML
    Label msglbl;

    @FXML
    TableView<Admin> table;
    @FXML
    TableColumn id, fname, lname, uname, email;
    @FXML
    TextField filterField;
    @FXML
    Button editbtn, delbtn;

    @FXML
    public void addPressed() {
        /**
         * if values are being added
         */

        if (addEditBtn.getText().equals("Add") || addEditBtn.getText() == "Add") {
            boolean done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`admin` \n"
                    + "(`first_name`, `last_name`, `username`, `password`, `pin`, `email`)\n"
                    + " VALUES ('" + fnamefld.getText() + "', '" + lnamefld.getText() + "', '" + unamefld.getText() + "', '" + passfld.getText() + "', '" + pinfld.getText() + "', '" + emailfld.getText() + "');");
            if (done) {
                msglbl.setText("Successfully added");
            } else {
                msglbl.setText("Failed. Please try again.");
            }
        }

        /**
         * if values are being edited
         */
        if (addEditBtn.getText().equals("Edit") || addEditBtn.getText() == "Edit") {
            boolean done = Database.executeUpdate("UPDATE `electronic_bulletin_board`.`admin` SET\n"
                    + " `first_name`='" + fnamefld.getText() + "', \n"
                    + "`last_name`='" + lnamefld.getText() + "', \n"
                    + "`username`='" + unamefld.getText() + "', \n"
                    + "`password`='" + passfld.getText() + "', \n"
                    + "`pin`='" + pinfld.getText() + "', \n"
                    + "`email`='" + emailfld.getText() + "' \n"
                    + "WHERE \n"
                    + "`admin_id`='" + StuffHolder.getAdmin().getId() + "';");

            if (done) {
                msglbl.setText("Successfully updated");
            } else {
                msglbl.setText("Failed. Please try again.");
            }
        }

        populate();
    }

    //when mouse is released from table
    @FXML
    public void mouseReleasedFromTable() {

        if (table.getSelectionModel().getSelectedIndex() > -1) {
            editbtn.setDisable(false);
            delbtn.setDisable(false);
            Admin ad = table.getSelectionModel().getSelectedItem();
            StuffHolder.setAdmin(ad);
        } else {
            editbtn.setDisable(true);
            delbtn.setDisable(true);
        }

    }

    /**
     * when edit button is pressed
     */
    @FXML
    public void editPressed() {

        fnamefld.setText(StuffHolder.getAdmin().getFirstname());
        lnamefld.setText(StuffHolder.getAdmin().getLastname());
        emailfld.setText(StuffHolder.getAdmin().getEmail());
        unamefld.setText(StuffHolder.getAdmin().getUsername());
        passfld.setText(StuffHolder.getAdmin().getPassword());
        cpassfld.setText(StuffHolder.getAdmin().getPassword());
        pinfld.setText(StuffHolder.getAdmin().getPin());
        cpinfld.setText(StuffHolder.getAdmin().getPin());

        addEditBtn.setText("Edit");
    }

    @FXML
    public void cancelPressed() {
        fnamefld.setText("");
        lnamefld.setText("");
        emailfld.setText("");
        unamefld.setText("");
        passfld.setText("");
        cpassfld.setText("");
        pinfld.setText("");
        cpinfld.setText("");

        addEditBtn.setText("Add");
        msglbl.setText("");
    }

    @FXML
    public void goback() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.SettingPage);
    }

    //to change panes
    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        StuffHolder.getMenuScreenStackPane().getChildren().clear();
        StuffHolder.getMenuScreenStackPane().getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    /**
     * populate admin in the table
     */
    public void populate() {
        try {
            ObservableList<Admin> observableList = FXCollections.observableArrayList();

            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.admin");

            while (rs.next()) {
                Admin ad = new Admin();
                ad.setId(rs.getInt("admin_id"));
                ad.setFirstname(rs.getString("first_name"));
                ad.setLastname(rs.getString("last_name"));
                ad.setUsername(rs.getString("username"));
                ad.setPassword(rs.getString("password"));
                ad.setPin(rs.getString("pin"));
                ad.setEmail(rs.getString("email"));
                observableList.add(ad);
            }

            id.setCellValueFactory(new PropertyValueFactory<Admin, String>("id"));
            fname.setCellValueFactory(new PropertyValueFactory<Admin, String>("firstname"));
            lname.setCellValueFactory(new PropertyValueFactory<Admin, String>("lastname"));
            uname.setCellValueFactory(new PropertyValueFactory<Admin, String>("username"));
            email.setCellValueFactory(new PropertyValueFactory<Admin, String>("email"));

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Admin> filteredData = new FilteredList<>(observableList, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(admin -> {
                    // If filter text is empty, display all bulletinBoards.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (admin.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (admin.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (admin.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (admin.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (String.valueOf(admin.getId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Admin> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedData);
        } catch (SQLException ex) {
            Logger.getLogger(AdminSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populate();
    }

}
