package com.philips.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesLoader {
    private Properties properties;

    public void loadProperties(String filepath) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filepath);
        properties = new Properties();
        properties.load(fileInputStream);
    }

    public String getProperties(String name) {
        String prop = null;
        try {

            String temp = properties.getProperty(name);
            prop = temp;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return prop;
    }


}
