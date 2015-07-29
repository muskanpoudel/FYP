/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;

/**
 *
 * @author Muskan
 */
public class LocalUtility {

    public static Image imageFactory(String LinkToImageFile) {
        Image image = new Image(LinkToImageFile);
        return image;
    }

    public static Properties getProperty() {
        Properties prop = new Properties();
        InputStream input = null;
        try {
            input = new FileInputStream("config.properties");

            // load a properties file
            prop.load(input);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(LocalUtility.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LocalUtility.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prop;
    }

}
