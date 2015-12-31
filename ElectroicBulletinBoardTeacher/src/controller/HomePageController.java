/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import helperClasses.Database;
import helperClasses.StuffHolder;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pojo.Message;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class HomePageController implements Initializable {
    
    @FXML
    Label msglbl, welcomelbl;
    @FXML
    TextField titlefld, filterField;
    @FXML
    DatePicker postDate, expireDate;
    @FXML
    TextArea msgArea;
    @FXML
    Button addbtn, editbtn, delbtn;
    @FXML
    TableView<Message> table;
    @FXML
    TableColumn id, title, pdate, edate;
    
    @FXML
    public void logoutPressed() throws Exception {
        loadMainPane(StuffHolder.LoginPage);
    }
    
    @FXML
    public void addPressed() {
        
        java.util.Date postdate
                = java.util.Date.from(postDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlpostDate = new java.sql.Date(postdate.getTime());
        java.util.Date expiredate
                = java.util.Date.from(expireDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlexpireDate = new java.sql.Date(expiredate.getTime());
        
        if (addbtn.getText().equals("Add") || addbtn.getText() == "Add") {
            boolean done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`teachermessage` \n"
                    + "(`title`, `postDate`, `expireDate`, `message`, `idTeacher`)\n"
                    + " VALUES\n"
                    + " ('" + titlefld.getText() + "', \n"
                    + "'" + (java.sql.Date) sqlpostDate + "', \n"
                    + "'" + (java.sql.Date) sqlexpireDate + "', \n"
                    + "'" + msgArea.getText() + "\n Staff: " + StuffHolder.getThisTeacher().getFirstname() + " " + StuffHolder.getThisTeacher().getLastname() + "', \n"
                    + "'" + StuffHolder.getThisTeacher().getId() + "');");
            
            if (done) {
                msglbl.setText("Added Successfully!!!");
                populate();
            } else {
                msglbl.setText("Failed. Please try again!!!");
            }
            
        } else if (addbtn.getText().equals("Edit") || addbtn.getText() == "Edit") {
            
            boolean done = Database.executeUpdate("UPDATE `electronic_bulletin_board`.`teachermessage` \n"
                    + "SET \n"
                    + "`title`='" + titlefld.getText() + "', \n"
                    + "`postDate`='" + (java.sql.Date) sqlpostDate + "', \n"
                    + "`expireDate`='" + (java.sql.Date) sqlexpireDate + "', \n"
                    + "`message`='" + msgArea.getText() + "\n Staff: " + StuffHolder.getThisTeacher().getFirstname() + " " + StuffHolder.getThisTeacher().getLastname() + "', \n"
                    + "`idTeacher`='" + StuffHolder.getThisTeacher().getId() + "' \n"
                    + "WHERE \n"
                    + "`idTeacherMessage`='" + StuffHolder.getTeacherMessage().getId() + "';");
            if (done) {
                msglbl.setText("Updated Successfully!!!");
                populate();
            } else {
                msglbl.setText("Failed. Please try again!!!");
            }
        }
    }
    
    @FXML
    public void cancelPressed() {
        titlefld.setText("");
        postDate.getEditor().clear();
        expireDate.getEditor().clear();
        msgArea.setText("");
        addbtn.setText("Add");
        msglbl.setText("");
    }
    
    @FXML
    public void editPressed() {
//        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MMMM-dd");
//        final LocalDate dt = LocalDate.parse(StuffHolder.getTeacherMessage().getPostDate(), dtf);
//        final DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern("yyyy-MMMM-dd");
//        final LocalDate dt2 = LocalDate.parse(StuffHolder.getTeacherMessage().getExpireDate(), dtf2);

        LocalDate myDate = LocalDate.parse(StuffHolder.getTeacherMessage().getPostDate());
        LocalDate myDate2 = LocalDate.parse(StuffHolder.getTeacherMessage().getExpireDate());
        
        titlefld.setText(StuffHolder.getTeacherMessage().getTitle());
        postDate.setValue(myDate);
        expireDate.setValue(myDate2);
        msgArea.setText(StuffHolder.getTeacherMessage().getMessage());
        addbtn.setText("Edit");
    }
    
    @FXML
    public void deletePressed() {
    }

    //when mouse is released from table
    @FXML
    public void mouseReleasedFromTable() {
        StuffHolder.setEditMessage(true);
        Message bi = table.getSelectionModel().getSelectedItem();
        StuffHolder.setTeacherMessage(bi);
        if (table.getSelectionModel().getSelectedIndex() > -1) {
            editbtn.setDisable(false);
            delbtn.setDisable(false);
        } else {
            editbtn.setDisable(true);
            delbtn.setDisable(true);
        }
        
    }

    //muskan edited------------------------------------
    private void loadMainPane(String fxmlPath) throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        //loader.setLocation(Class.class.getResource(ScreenNavigator.MainGUI));
        Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(fxmlPath));
        Scene sc = new Scene(mainPane);
        Stage stageHere = StuffHolder.getStageMain();
        stageHere.setScene(sc);
        StuffHolder.setStageMain(stageHere);
    }
    //---------------------------------

    /**
     * to populate tableview
     *
     */
    public void populate() {
        /**
         * populate teacher field
         */
        ObservableList<Message> observableList = FXCollections.observableArrayList();
        
        ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.teachermessage where idteacher = " + StuffHolder.getThisTeacher().getId() + ";");
        try {
            while (rs.next()) {
                Message ms = new Message();
                ms.setId(rs.getInt("idTeacherMessage"));
                ms.setTitle(rs.getString("title"));
                ms.setPostDate(String.valueOf(rs.getString("postDate")));
                ms.setExpireDate(String.valueOf(rs.getString("expireDate")));
                ms.setMessage(rs.getString("message"));
                observableList.add(ms);
            }
            
            id.setCellValueFactory(new PropertyValueFactory<Message, String>("id"));
            title.setCellValueFactory(new PropertyValueFactory<Message, String>("title"));
            pdate.setCellValueFactory(new PropertyValueFactory<Message, String>("postDate"));
            edate.setCellValueFactory(new PropertyValueFactory<Message, String>("expireDate"));

            // 1. Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Message> filteredData = new FilteredList<>(observableList, p -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            filterField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(message -> {
                    // If filter text is empty, display all bulletinBoards.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // Compare first name and last name of every person with filter text.
                    String lowerCaseFilter = newValue.toLowerCase();
                    
                    if (String.valueOf(message.getId()).toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (message.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (message.getPostDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    } else if (message.getExpireDate().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches last name.
                    }
                    return false; // Does not match.
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Message> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            sortedData.comparatorProperty().bind(table.comparatorProperty());
            
            table.setItems(sortedData);
        } catch (SQLException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /**
         * to convert first character to string
         */
        String fname = StuffHolder.getThisTeacher().getFirstname();
        String lname = StuffHolder.getThisTeacher().getLastname();
        
        fname = Character.toUpperCase(fname.charAt(0)) + fname.substring(1);
        lname = Character.toUpperCase(lname.charAt(0)) + lname.substring(1);
        
        String teachername = fname + " " + lname;
        
        welcomelbl.setText("Welcome, " + teachername);
        
        populate();
        
    }
    
}
