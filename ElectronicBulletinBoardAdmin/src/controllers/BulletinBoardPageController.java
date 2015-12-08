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
    TableView table;
    @FXML
    TableColumn bbId, bbName, bbLocation;

    @FXML
    public void addBulletinBoard() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.BulletinBoardpopup);
    }

    @FXML
    public void viewBulletinInfo() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.BulletinBoardInformation);
    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        sp.getChildren().clear();
        sp.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ObservableList<BulletinBoards> observableList = FXCollections.observableArrayList();

            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.noticeboard");

            while (rs.next()) {
                BulletinBoards bb = new BulletinBoards();
                bb.setId(rs.getInt("idnoticeboard"));
                bb.setName(rs.getString("noticeboardname"));
                bb.setLocation(rs.getString("noticeboardlocation"));
                observableList.add(bb);
            }

            bbId.setCellValueFactory(new PropertyValueFactory<ContentFeeders, String>("id"));
            bbName.setCellValueFactory(new PropertyValueFactory<ContentFeeders, String>("name"));
            bbLocation.setCellValueFactory(new PropertyValueFactory<ContentFeeders, String>("location"));

            table.setItems(observableList);

        } catch (SQLException ex) {
            Logger.getLogger(ContentFeederPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
