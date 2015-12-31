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
import pojo.Student;
import pojo.Teacher;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class StudentSettingController implements Initializable {

    @FXML
    TextField fnamefld, lnamefld, sessionfld, yearfld, semfld, groupfld, emailfld, pnofld;
    @FXML
    Button addEditBtn, editbtn, delbtn;
    @FXML
    Label msglbl;
    @FXML
    TextField filterField;
    @FXML
    TableView<Student> table;
    @FXML
    TableColumn id, fname, lname, session, year, sem, group, email, pno;

    @FXML
    public void addPressed() {
        /**
         * if values are being added
         */

        if (addEditBtn.getText().equals("Add") || addEditBtn.getText() == "Add") {
            boolean done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`student` \n"
                    + "(`firstname`, `lastname`, `session`, `year`, `semister`, `Group`, `email`, `phoneno`)\n"
                    + " VALUES ('" + fnamefld.getText() + "',"
                    + " '" + lnamefld.getText() + "', "
                    + "'" + sessionfld.getText() + "', "
                    + "'" + yearfld.getText() + "', "
                    + "'" + semfld.getText() + "', "
                    + "'" + groupfld.getText() + "', "
                    + "'" + emailfld.getText() + "', "
                    + "'" + pnofld.getText() + "');");
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
            boolean done = Database.executeUpdate("UPDATE `electronic_bulletin_board`.`student` SET\n"
                    + " `firstname`='" + fnamefld.getText() + "', \n"
                    + "`lastname`='" + lnamefld.getText() + "', \n"
                    + "`session`='" + sessionfld.getText() + "', \n"
                    + "`year`='" + yearfld.getText() + "', \n"
                    + "`semister`='" + semfld.getText() + "', \n"
                    + "`Group`='" + groupfld.getText() + "', \n"
                    + "`email`='" + emailfld.getText() + "', \n"
                    + "`phoneno`='" + pnofld.getText() + "' \n"
                    + "WHERE \n"
                    + "`idstudent`='" + StuffHolder.getStudent().getId() + "';");

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

        fnamefld.setText(StuffHolder.getStudent().getFirstname());
        lnamefld.setText(StuffHolder.getStudent().getLastname());
        sessionfld.setText(StuffHolder.getStudent().getSession());
        yearfld.setText(StuffHolder.getStudent().getYear());
        semfld.setText(StuffHolder.getStudent().getSemister());
        groupfld.setText(StuffHolder.getStudent().getGroup());
        emailfld.setText(StuffHolder.getStudent().getEmail());
        pnofld.setText(StuffHolder.getStudent().getPhoneno());

        addEditBtn.setText("Edit");
    }

    @FXML
    public void cancelPressed() {

        fnamefld.setText("");
        lnamefld.setText("");
        sessionfld.setText("");
        yearfld.setText("");
        semfld.setText("");
        groupfld.setText("");
        emailfld.setText("");
        pnofld.setText("");

        addEditBtn.setText("Add");
        msglbl.setText("");
    }

    //when mouse is released from table
    @FXML
    public void mouseReleasedFromTable() {

        if (table.getSelectionModel().getSelectedIndex() > -1) {
            editbtn.setDisable(false);
            delbtn.setDisable(false);
            Student ad = table.getSelectionModel().getSelectedItem();
            StuffHolder.setStudent(ad);
        } else {
            editbtn.setDisable(true);
            delbtn.setDisable(true);
        }

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
     * to populate student table
     */
    public void populate() {
        try {
            ObservableList<Student> observableList = FXCollections.observableArrayList();

            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.student");

            while (rs.next()) {
                Student ad = new Student();
                ad.setId(rs.getInt("idstudent"));
                ad.setFirstname(rs.getString("firstname"));
                ad.setLastname(rs.getString("lastname"));
                ad.setSession(rs.getString("session"));
                ad.setYear(rs.getString("year"));
                ad.setSemister(rs.getString("semister"));
                ad.setGroup(rs.getString("Group"));
                ad.setEmail(rs.getString("email"));
                ad.setPhoneno(rs.getString("phoneno"));
                observableList.add(ad);
            }

            id.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
            fname.setCellValueFactory(new PropertyValueFactory<Student, String>("firstname"));
            lname.setCellValueFactory(new PropertyValueFactory<Student, String>("lastname"));
            session.setCellValueFactory(new PropertyValueFactory<Student, String>("session"));
            year.setCellValueFactory(new PropertyValueFactory<Student, String>("year"));
            sem.setCellValueFactory(new PropertyValueFactory<Student, String>("semister"));
            group.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
            email.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
            pno.setCellValueFactory(new PropertyValueFactory<Student, String>("phoneno"));

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Student> filteredData = new FilteredList<>(observableList, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(student -> {
                    // If filter text is empty, display all bulletinBoards.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();

                    if (student.getFirstname().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (student.getLastname().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (student.getEmail().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (student.getGroup().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (student.getPhoneno().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (student.getSemister().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (student.getSession().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (student.getYear().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (String.valueOf(student.getId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Student> sortedData = new SortedList<>(filteredData);

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
