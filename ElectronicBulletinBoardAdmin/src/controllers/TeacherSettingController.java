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
import pojo.Teacher;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class TeacherSettingController implements Initializable {

    @FXML
    TextField fnamefld, lnamefld, unamefld, passfld, rpassfld;
    @FXML
    Button addEditBtn, editbtn, delbtn;
    @FXML
    Label msglbl;
    @FXML
    TextField filterField;

    @FXML
    TableView<Teacher> table;
    @FXML
    TableColumn id, fname, lname, uname;

    @FXML
    public void addPressed() {
        /**
         * if values are being added
         */

        if (addEditBtn.getText().equals("Add") || addEditBtn.getText() == "Add") {
            boolean done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`teacher` \n"
                    + "(`firstname`, `lastname`, `username`, `password`)\n"
                    + " VALUES ('" + fnamefld.getText() + "', '" + lnamefld.getText() + "', '" + unamefld.getText() + "', '" + passfld.getText() + "');");
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
            boolean done = Database.executeUpdate("UPDATE `electronic_bulletin_board`.`teacher` SET\n"
                    + " `firstname`='" + fnamefld.getText() + "', \n"
                    + "`lastname`='" + lnamefld.getText() + "', \n"
                    + "`username`='" + unamefld.getText() + "', \n"
                    + "`password`='" + passfld.getText() + "' \n"
                    + "WHERE \n"
                    + "`idteacher`='" + StuffHolder.getTeacher().getId() + "';");

            if (done) {
                msglbl.setText("Successfully updated");
            } else {
                msglbl.setText("Failed. Please try again.");
            }
        }

        populate();
    }

    /**
     * when edit button is pressed
     */
    @FXML
    public void editPressed() {

        fnamefld.setText(StuffHolder.getTeacher().getFirstname());
        lnamefld.setText(StuffHolder.getTeacher().getLastname());
        unamefld.setText(StuffHolder.getTeacher().getUsername());
        passfld.setText(StuffHolder.getTeacher().getPassword());
        rpassfld.setText(StuffHolder.getTeacher().getPassword());

        addEditBtn.setText("Edit");
    }

    @FXML
    public void cancelPressed() {

        fnamefld.setText("");
        lnamefld.setText("");
        unamefld.setText("");
        passfld.setText("");
        rpassfld.setText("");

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
     *
     * to populate teacher table
     */
    public void populate() {
        try {
            ObservableList<Teacher> observableList = FXCollections.observableArrayList();

            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.teacher");

            while (rs.next()) {
                Teacher ad = new Teacher();
                ad.setId(rs.getInt("idteacher"));
                ad.setFirstname(rs.getString("firstname"));
                ad.setLastname(rs.getString("lastname"));
                ad.setUsername(rs.getString("username"));
                ad.setPassword(rs.getString("password"));
                observableList.add(ad);
            }

            id.setCellValueFactory(new PropertyValueFactory<Teacher, String>("id"));
            fname.setCellValueFactory(new PropertyValueFactory<Teacher, String>("firstname"));
            lname.setCellValueFactory(new PropertyValueFactory<Teacher, String>("lastname"));
            uname.setCellValueFactory(new PropertyValueFactory<Teacher, String>("username"));

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Teacher> filteredData = new FilteredList<>(observableList, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(teacher -> {
                    // If filter text is empty, display all bulletinBoards.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (teacher.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (teacher.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (teacher.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (String.valueOf(teacher.getId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Teacher> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            table.setItems(sortedData);
        } catch (SQLException ex) {
            Logger.getLogger(AdminSettingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //when mouse is released from table
    @FXML
    public void mouseReleasedFromTable() {

        if (table.getSelectionModel().getSelectedIndex() > -1) {
            editbtn.setDisable(false);
            delbtn.setDisable(false);
            Teacher ad = table.getSelectionModel().getSelectedItem();
            StuffHolder.setTeacher(ad);
        } else {
            editbtn.setDisable(true);
            delbtn.setDisable(true);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        populate();
    }

}
