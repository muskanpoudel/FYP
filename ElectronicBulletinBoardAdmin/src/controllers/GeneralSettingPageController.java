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
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Muskan
 */
public class GeneralSettingPageController implements Initializable {
    
    @FXML
    ImageView imgView;
    @FXML
    Label imglbl;
    @FXML
    TextField headertxtfld;
    @FXML
    Label msglbl;
    
    File imgFile;
    byte[] bytes;
    
    @FXML
    public void browseImage() {
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
                imgFile = file;
                if (bytes != null) {
                    BufferedImage img;
                    try {
                        imglbl.setText("");
                        img = ImageIO.read(new ByteArrayInputStream(bytes));
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        ImageIO.write(img, "jpg", out);
                        ByteArrayInputStream inn = new ByteArrayInputStream(out.toByteArray());
                        imgView.setImage(new javafx.scene.image.Image(inn));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                imglbl.setText("**image too large.");
            }
        }
    }
    
    @FXML
    public void applyPressed() throws SQLException, FileNotFoundException {
        
        if (imgFile != null && (!"".equals(headertxtfld.getText()) || headertxtfld.getText() != "")) {
            ResultSet rs = Database.executeQuery("Select count(*) as countValue from `electronic_bulletin_board`.`organisation_table`;");
            while (rs.next()) {
                if (rs.getInt("countValue") > 0) {
                    PreparedStatement psmnt = Database.connectDB().prepareStatement("UPDATE `electronic_bulletin_board`.`organisation_table` SET "
                            + "`icon`= ? WHERE "
                            + "`title`= ?;");
                    
                    FileInputStream fis = new FileInputStream(imgFile);
                    psmnt.setBinaryStream(1, (InputStream) fis, (int) imgFile.length());
                    psmnt.setString(2, headertxtfld.getText());
                    int s = psmnt.executeUpdate();
                    if (s > 0) {
                        msglbl.setStyle("-fx-text-fill:green");
                        msglbl.setText("Updated Successfully");
                    } else {
                        msglbl.setStyle("-fx-text-fill:red");
                        msglbl.setText("Error. Please try again.");
                    }
                } else {
                    PreparedStatement psmnt = Database.connectDB().prepareStatement(
                            "INSERT INTO `electronic_bulletin_board`.`organisation_table` "
                            + "(`icon`, `title`) VALUES"
                            + " (?, ?)");
                    FileInputStream fis = new FileInputStream(imgFile);
                    psmnt.setBinaryStream(1, (InputStream) fis, (int) imgFile.length());
                    psmnt.setString(2, headertxtfld.getText());
                    int s = psmnt.executeUpdate();
                    if (s > 0) {
                        msglbl.setStyle("-fx-text-fill:green;");
                        msglbl.setText("Updated Successfully");
                    } else {
                        msglbl.setStyle("-fx-text-fill:red;");
                        msglbl.setText("Error. Please try again.");
                    }
                }
                
            }
            
        } else {
            msglbl.setText("Please Enter valid data first.");
            msglbl.setStyle("-fx-text-fill:red");
        }
        
    }
    
    @FXML
    public void studentSetting() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.studentSettingPage);
    }
    
    @FXML
    public void adminSetting() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.adminSettingPage);
    }
    
    @FXML
    public void teacherSetting() throws IOException {
        loadDesiredPageFromMenu(StuffHolder.teacherSettingPage);
    }

    //to change panes
    public void loadDesiredPageFromMenu(String PageName) throws IOException {
        StuffHolder.getMenuScreenStackPane().getChildren().clear();
        StuffHolder.getMenuScreenStackPane().getChildren().add(FXMLLoader.load(getClass().getResource(PageName)));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {
        // TODO
    }
}
