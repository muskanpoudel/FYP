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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    TableView table;

    @FXML
    public void addNewContentFeeder() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.ContentFeederAddingScreen);
    }

    @FXML
    public void viewContentFeeder() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.ContentFeederViewScreen);
    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        menuPageStackPane.getChildren().clear();
        menuPageStackPane.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
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

            table.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(ContentFeederPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
