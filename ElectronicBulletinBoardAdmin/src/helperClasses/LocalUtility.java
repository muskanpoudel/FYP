/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import java.io.File;
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

}
