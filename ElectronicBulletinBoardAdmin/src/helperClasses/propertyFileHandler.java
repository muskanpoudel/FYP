/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helperClasses;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author Muskan
 */
public class propertyFileHandler {

    /**
     * returns property file
     */
    public static Properties getProperty() throws FileNotFoundException, IOException {
        Properties prop = new Properties();
        InputStream input = null;
        input = new FileInputStream(System.getProperty("user.home") + "/AppData/EBBS/Admin.properties");
        // load a properties file
        prop.load(input);
        return prop;
    }

    /**
     * to save changes in properties file
     */
    public static void SaveProperties(String ip, String port, String hostIp, String hostPort, String smsusername, String smspassword) throws FileNotFoundException, IOException {
        FileOutputStream fileOut = null;
        Properties properties = new Properties();
        properties.setProperty("ip", ip);
        properties.setProperty("port", port);
        properties.setProperty("smsusername", smsusername);
        properties.setProperty("smspassword", smspassword);
        properties.setProperty("smshostip", smspassword);
        properties.setProperty("smshostport", smspassword);
        File file = new File(System.getProperty("user.home") + "/AppData/EBBS/Admin.properties");
        fileOut = new FileOutputStream(file);
        properties.store(fileOut, "FilePath");
        fileOut.close();
    }

    /**
     * updating property file
     */
    public static void updateProperties(String key, String value) throws FileNotFoundException, IOException {
        FileInputStream in = new FileInputStream(System.getProperty("user.home") + "/AppData/EBBS/Admin.properties");
        Properties props = new Properties();
        props.load(in);
        in.close();

        FileOutputStream out = new FileOutputStream(System.getProperty("user.home") + "/AppData/EBBS/Admin.properties");
        props.setProperty(key, value);
        props.store(out, null);
        out.close();
    }
}
