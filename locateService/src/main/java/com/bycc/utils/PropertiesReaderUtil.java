package com.bycc.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wanghaidong on 2017/5/18.
 */
public class PropertiesReaderUtil {
    public static String readProperty(String propertyName){
        Properties prop = new Properties();
        InputStream inputStream = PropertiesReaderUtil.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            prop.load(inputStream);
            return prop.getProperty(propertyName).trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
