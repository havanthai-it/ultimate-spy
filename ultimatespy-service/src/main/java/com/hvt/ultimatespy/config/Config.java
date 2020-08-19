package com.hvt.ultimatespy.config;

import org.springframework.context.annotation.Configuration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

@Configuration
public class Config {

    private static final Logger logger = Logger.getLogger(Config.class.getName());
    public static Properties prop;

    static {
        try {
            prop = new Properties();
            InputStream is = Config.class.getClassLoader().getResourceAsStream("application.properties");
            if (is != null) {
                prop.load(is);
            } else {
                throw new FileNotFoundException("[CONFIG] Configuration file was not found!");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "", e);
        }
    }

}
