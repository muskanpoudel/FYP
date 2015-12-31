/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import helperClasses.Database;
import helperClasses.StuffHolder;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class AddBulletinImgController implements Initializable {

    @FXML
    FlowPane flowPane;
    byte[] bytes;
    @FXML
    private ImageView imageviewer;
    @FXML
    Label imagelbl, titleOfPage;
    @FXML
    TextField imgTitle;
    @FXML
    DatePicker postDate, expireDate;
    @FXML
    Button addupdateBtn;

    File imgFile;
    ArrayList<CheckBox> checkLists = new ArrayList<>();

    StackPane menuPageStackPane = StuffHolder.getMenuScreenStackPane();

    public void photoChooser(ActionEvent evt) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Images (.png, .jpg, .bmp)", "*.jpg", "*.png", "*.gif", "*.bmp");
        fileChooser.getExtensionFilters().addAll(extFilter);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {

            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    // Writes to this byte array output stream
                    bos.write(buf, 0, readNum);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            bytes = bos.toByteArray();
            int ImageSize = bos.toByteArray().length;

            if (ImageSize < 410624) {
                imgTitle.setText(file.getName());
                imgFile = file;
                if (bytes != null) {
                    BufferedImage img;
                    try {
                        imagelbl.setText("");
                        img = ImageIO.read(new ByteArrayInputStream(bytes));
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", out);
                        ByteArrayInputStream inn = new ByteArrayInputStream(out.toByteArray());
                        imageviewer.setImage(new javafx.scene.image.Image(inn));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                imagelbl.setText("**image too large.");
            }
        }

    }

    @FXML
    public void addImageToDatabase() throws SQLException, FileNotFoundException {
        int noticeboardID = 0, contentId = 0;
        boolean done = false;
        ResultSet rs2;

        java.util.Date postdate
                = java.util.Date.from(postDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlpostDate = new java.sql.Date(postdate.getTime());
        java.util.Date expiredate
                = java.util.Date.from(expireDate.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());
        java.sql.Date sqlexpireDate = new java.sql.Date(expiredate.getTime());

        PreparedStatement psmnt = null;

        /*
         * if bulletin is in  the process of being EDITED
         */
        if (StuffHolder.isEditbulletin()) {
            if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {
                psmnt = Database.connectDB().prepareStatement("UPDATE `electronic_bulletin_board`.`contentimage` SET "
                        + "`publish_date`='" + (java.sql.Date) sqlpostDate + "', "
                        + "`expire_date`='" + (java.sql.Date) sqlexpireDate + "', "
                        + "`title`='" + imgTitle.getText() + "', "
                        + "`image`= ? WHERE "
                        + "`idcontentimage`='" + StuffHolder.getBulletinInformation().getBulletinId().substring(4) + "';");

                FileInputStream fis = new FileInputStream(imgFile);
                psmnt.setBinaryStream(1, (InputStream) fis, (int) imgFile.length());
            } else {
                psmnt = Database.connectDB().prepareStatement("UPDATE `electronic_bulletin_board`.`admincontentimage` SET "
                        + "`publish_date`='" + (java.sql.Date) sqlpostDate + "', "
                        + "`expire_date`='" + (java.sql.Date) sqlexpireDate + "', "
                        + "`title`='" + imgTitle.getText() + "', "
                        + "`image`= ? WHERE "
                        + "`idcontentimage`='" + StuffHolder.getBulletinInformation().getBulletinId().substring(4) + "';");

                FileInputStream fis = new FileInputStream(imgFile);
                psmnt.setBinaryStream(1, (InputStream) fis, (int) imgFile.length());
            }

            /**
             * image is uploaded... now its turn for bulletin board
             */
            if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {
                rs2 = Database.executeQuery("SELECT idcontentimage FROM \n"
                        + "electronic_bulletin_board.contentimage where \n"
                        + "idcontentfeeder= " + StuffHolder.getThisAdmin().getAdminid() + " and \n"
                        + "publish_date='" + (java.sql.Date) sqlpostDate + "' and \n"
                        + "expire_date = '" + (java.sql.Date) sqlexpireDate + "' and \n"
                        + "title = \"" + imgTitle.getText() + "\";");
                while (rs2.next()) {
                    contentId = rs2.getInt("idcontentimage");

                }
            } else {
                rs2 = Database.executeQuery("SELECT idcontentimage FROM \n"
                        + "electronic_bulletin_board.admincontentimage where \n"
                        + "idcontentfeeder= " + StuffHolder.getThisAdmin().getAdminid() + " and \n"
                        + "publish_date='" + (java.sql.Date) sqlpostDate + "' and \n"
                        + "expire_date = '" + (java.sql.Date) sqlexpireDate + "' and \n"
                        + "title = \"" + imgTitle.getText() + "\";");
                while (rs2.next()) {
                    contentId = rs2.getInt("idcontentimage");

                }
            }
        } else {
            /*
             * if bulletin is in  the process of being ADDED
             */
            psmnt = Database.connectDB().prepareStatement(
                    "INSERT INTO `electronic_bulletin_board`.`admincontentimage` "
                    + "(`idcontentfeeder`, `publish_date`, `expire_date`, `image`, `title`, `contentTypeID`) VALUES"
                    + " (?, ?, ?, ?, ?, ?)");
            FileInputStream fis = new FileInputStream(imgFile);
            psmnt.setInt(1, StuffHolder.getThisAdmin().getAdminid());
            psmnt.setDate(2, (java.sql.Date) sqlpostDate);
            psmnt.setDate(3, (java.sql.Date) sqlexpireDate);
            psmnt.setBinaryStream(4, (InputStream) fis, (int) imgFile.length());
            psmnt.setString(5, imgTitle.getText());
            psmnt.setInt(6, 1);

            int s = psmnt.executeUpdate();
            if (s > 0) {
                done = true;
            } else {
                done = false;
            }

            rs2 = Database.executeQuery("SELECT idcontentimage FROM \n"
                    + "electronic_bulletin_board.admincontentimage where \n"
                    + "idcontentfeeder= " + StuffHolder.getThisAdmin().getAdminid() + " and \n"
                    + "publish_date='" + (java.sql.Date) sqlpostDate + "' and \n"
                    + "expire_date = '" + (java.sql.Date) sqlexpireDate + "' and \n"
                    + "title = \"" + imgTitle.getText() + "\";");

            while (rs2.next()) {
                contentId = rs2.getInt("idcontentimage");

            }
        }

        if (StuffHolder.isEditbulletin()) {
            if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {
                done = Database.executeUpdate("DELETE FROM `electronic_bulletin_board`.`noticeboard_content` \n"
                        + "WHERE `idcontenttype` = '1' and `idcontent` = '" + contentId + "';");
            } else {
                done = Database.executeUpdate("DELETE FROM `electronic_bulletin_board`.`noticeboard_admincontent` \n"
                        + "WHERE `idcontenttype` = '1' and `idcontent` = '" + contentId + "';");
            }
        }

        for (int i = 0; i < checkLists.size(); i++) {
            if (checkLists.get(i).isSelected()) {
                String[] bulletinBoardParts = checkLists.get(i).getText().split("\\(");
                ResultSet rs = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard where noticeboardname=\"" + bulletinBoardParts[0] + "\"");

                while (rs.next()) {
                    noticeboardID = rs.getInt("idnoticeboard");
                }

                if (StuffHolder.isEditbulletin()) {
                    if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {
                        done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`noticeboard_content` "
                                + "(`idnoticeboard`, `idcontenttype`, `idcontent`) VALUES"
                                + " (" + noticeboardID + ", " + 1 + ", " + contentId + ")");
                    } else {
                        done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`noticeboard_admincontent` "
                                + "(`idnoticeboard`, `idcontenttype`, `idcontent`) VALUES"
                                + " (" + noticeboardID + ", " + 1 + ", " + contentId + ")");
                    }
                } else {
                    done = Database.executeUpdate("INSERT INTO `electronic_bulletin_board`.`noticeboard_admincontent` "
                            + "(`idnoticeboard`, `idcontenttype`, `idcontent`) VALUES"
                            + " (" + noticeboardID + ", " + 1 + ", " + contentId + ")");
                }
            }
        }
        if (done) {
            if (StuffHolder.isEditbulletin()) {
                imagelbl.setText("Successfully Changed Values!!!");
            } else {
                imagelbl.setText("Successfully Uploaded!!!");
            }
        } else {
            imagelbl.setText("Unsucessfull to upload image.");
        }
    }

    @FXML
    public void goBack() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.BulletinPage);
    }

    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        menuPageStackPane.getChildren().clear();
        menuPageStackPane.getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        BufferedImage image = null;  //Buffered image coming from database
        InputStream fis = null; //Inputstream

        //for storing checkbox as soon as screen loads
        ArrayList<CheckBox> checkboxes = new ArrayList<CheckBox>();
        try {
            ResultSet rs = Database.executeQuery("SELECT * FROM electronic_bulletin_board.noticeboard");

            while (rs.next()) {
                CheckBox cb = new CheckBox(rs.getString("noticeboardname") + " (" + rs.getString("noticeboardlocation") + ")");
                flowPane.getChildren().add(cb);
                checkboxes.add(cb);
                checkLists.add(cb);
            }

            if (StuffHolder.isEditbulletin()) {
                titleOfPage.setText("Update your image from this page");
                addupdateBtn.setText("Update");
                imgTitle.setText(StuffHolder.getBulletinInformation().getTitle());

                ResultSet rs2;
                if (StuffHolder.getBulletinInformation().getBulletinId().charAt(0) == 'C') {
                    rs = Database.executeQuery("SELECT image, publish_date, expire_date FROM electronic_bulletin_board.contentimage where idcontentimage = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                    rs2 = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard_content where idcontenttype = 1 and idcontent = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                } else {//problem found --> only last char of id is taken.. eg it takes only 8 if it is 18
                    rs = Database.executeQuery("SELECT image, publish_date, expire_date FROM electronic_bulletin_board.admincontentimage where idcontentimage = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                    rs2 = Database.executeQuery("SELECT idnoticeboard FROM electronic_bulletin_board.noticeboard_admincontent where idcontenttype = 1 and idcontent = " + StuffHolder.getBulletinInformation().getBulletinId().substring(4));
                }
                while (rs.next()) {
                    fis = rs.getBinaryStream("image");
                    image = javax.imageio.ImageIO.read(fis);  //create the BufferedImaged
                    Image imgfx = SwingFXUtils.toFXImage(image, null); //converting to fx image
                    imageviewer.setImage(imgfx);
                    expireDate.setValue(rs.getDate("expire_date").toLocalDate());
                    postDate.setValue(rs.getDate("publish_date").toLocalDate());
                }

                while (rs2.next()) {
                    rs = Database.executeQuery("select noticeboardname, noticeboardlocation from electronic_bulletin_board.noticeboard where idnoticeboard = " + rs2.getInt("idnoticeboard"));

                    while (rs.next()) {
                        String checkboxtext = rs.getString("noticeboardname") + " (" + rs.getString("noticeboardlocation") + ")";

                        for (int i = 0; i < checkboxes.size(); i++) {
                            if (checkboxtext == checkboxes.get(i).getText() || checkboxtext.equals(checkboxes.get(i).getText())) {
                                checkboxes.get(i).setSelected(true);
                            }
                        }
                    }
                }

            } else {
                titleOfPage.setText("Add Content Feeder's Information");
                addupdateBtn.setText("Add");
            }

        } catch (SQLException ex) {
            Logger.getLogger(AddBulletinHeadlineController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AddBulletinImgController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
