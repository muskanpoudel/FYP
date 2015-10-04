/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.StuffHolder;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class MenuController implements Initializable {

    @FXML
    ImageView menuImgBulletin, menuImgUser, menuImgInfo, menuImgSms, menuImgSusUser, menuImgSetting;
    @FXML
    TreeView treeView;
    @FXML
    StackPane menuPageStackPane;
    @FXML
    Label menuBtnBulletinBoardlbl, menuBtnContentFeederslbl, menuBtnBulletinslbl, menuBtnGeneralSettinglbl, menuBtnSusContentFeederlbl, menuBtnSMSSettinglbl;
    @FXML
    ImageView gobackImg;

    @FXML
    public void bulletinMouseEnter() {
        menuImgBulletin.setImage(ImageMaker("BB2.png"));
        menuBtnBulletinBoardlbl.setVisible(true);
    }

    @FXML
    public void bulletinMouseExit() {
        menuImgBulletin.setImage(ImageMaker("BB1.png"));
        menuBtnBulletinBoardlbl.setVisible(false);
    }

    @FXML
    public void userMouseEnter() {
        menuImgUser.setImage(ImageMaker("user2.png"));
        menuBtnContentFeederslbl.setVisible(true);
    }

    @FXML
    public void userMouseExit() {
        menuImgUser.setImage(ImageMaker("user1.png"));
        menuBtnContentFeederslbl.setVisible(false);
    }

    @FXML
    public void infoMouseEnter() {
        menuImgInfo.setImage(ImageMaker("bulletin2.png"));
        menuBtnBulletinslbl.setVisible(true);
    }

    @FXML
    public void infoMouseExit() {
        menuImgInfo.setImage(ImageMaker("bulletin1.png"));
        menuBtnBulletinslbl.setVisible(false);
    }

    @FXML
    public void smsMouseEnter() {
        menuImgSms.setImage(ImageMaker("sms2.png"));
        menuBtnSMSSettinglbl.setVisible(true);
    }

    @FXML
    public void smsMouseExit() {
        menuImgSms.setImage(ImageMaker("sms1.png"));
        menuBtnSMSSettinglbl.setVisible(false);

    }

    @FXML
    public void susUserMouseEnter() {
        menuImgSusUser.setImage(ImageMaker("susUser2.png"));
        menuBtnSusContentFeederlbl.setVisible(true);
    }

    @FXML
    public void susUserMouseExit() {
        menuImgSusUser.setImage(ImageMaker("susUser1.png"));
        menuBtnSusContentFeederlbl.setVisible(false);
    }

    @FXML
    public void settingMouseEnter() {
        menuImgSetting.setImage(ImageMaker("setting2.png"));
        menuBtnGeneralSettinglbl.setVisible(true);
    }

    @FXML
    public void settingMouseExit() {
        menuImgSetting.setImage(ImageMaker("setting1.png"));
        menuBtnGeneralSettinglbl.setVisible(false);
    }

    @FXML
    public void goBackPressed() {
        gobackImg.setImage(ImageMaker("goBackWhite.png"));
    }

    @FXML
    public void goBackReleased() {
        gobackImg.setImage(ImageMaker("goBack.png"));
    }

    //creates and returns image
    public Image ImageMaker(String name) {
        Image img = new Image("/img/" + name);
        return img;
    }

    @FXML
    public void goToBulletinBoardPage() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.BulletinBoardPage);
    }

    @FXML
    public void goToContentFeederPage() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.ContentFeederPage);
    }

    @FXML
    public void goToBulletinPage() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.BulletinPage);
    }

    @FXML
    public void goToSettingPage() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.SettingPage);
    }

    @FXML
    public void goToSusContentFeederPage() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.SusContentFeederPage);
    }

    @FXML
    public void goToSmsSettingPage() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.SmsSettingPage);
    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        menuPageStackPane.getChildren().clear();
        menuPageStackPane.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    public ImageView returnDesiredImageView(String imageName, double height, double width) {
        ImageView imgVw = new ImageView(ImageMaker(imageName));
        imgVw.setFitHeight(height);
        imgVw.setFitWidth(width);
        return imgVw;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TreeItem<String> treeItemRoot = new TreeItem<>("Menu", returnDesiredImageView("Treemenu.png", 20, 20));
        TreeItem<String> nodeItemA = new TreeItem<>("Bulletin Board", returnDesiredImageView("BB.png", 20, 20));
        TreeItem<String> nodeItemB = new TreeItem<>("Content Feeder/Teacher", returnDesiredImageView("user.png", 20, 20));
        TreeItem<String> nodeItemC = new TreeItem<>("Bulletin", returnDesiredImageView("info.png", 20, 20));
        TreeItem<String> nodeItemD = new TreeItem<>("SMS Settings", returnDesiredImageView("smsTree.png", 20, 20));
        TreeItem<String> nodeItemE = new TreeItem<>("Suspent Content Feeder", returnDesiredImageView("susUser.png", 20, 20));
        TreeItem<String> nodeItemF = new TreeItem<>("Setting", returnDesiredImageView("setting.png", 20, 20));
        treeItemRoot.getChildren().addAll(nodeItemA, nodeItemB, nodeItemC, nodeItemD, nodeItemE, nodeItemF);

        treeView.setRoot(treeItemRoot);
        StuffHolder.setMenuScreenStackPane(menuPageStackPane);

    }

}
