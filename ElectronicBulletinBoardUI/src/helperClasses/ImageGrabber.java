/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

/**
 *
 * @author Muskan
 */
public class ImageGrabber {

    BufferedImage image = null;  //Buffered image coming from database
    InputStream fis = null; //Inputstream

    public ArrayList<Image> sendImageList() throws SQLException, IOException {
        ArrayList<Image> images = new ArrayList();
        ResultSet rs1 = Database.executeQuery("SELECT image FROM electronic_bulletin_board.admincontentimage\n"
                + "where expire_date >= CURDATE();");

        while (rs1.next()) {
            fis = rs1.getBinaryStream("image");
            image = javax.imageio.ImageIO.read(fis);  //create the BufferedImaged
            Image imgfx = SwingFXUtils.toFXImage(image, null); //converting to fx image

            images.add(imgfx);
        }

        return images;
    }

}
