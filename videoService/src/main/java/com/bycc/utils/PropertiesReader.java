package com.bycc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wanghaidong on 2017/5/18.
 */
public class PropertiesReader {
    public static String readProperty(String propertyName){
        Properties prop = new Properties();
        InputStream inputStream = PropertiesReader.class.getClassLoader().getResourceAsStream("streamServer.properties");
        try {
            prop.load(inputStream);
            return prop.getProperty(propertyName).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
