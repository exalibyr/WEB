package com.excalibur.myBlog.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class WebAppConfiguration {

    private static String url;

    static {
        Properties properties = new Properties();
        try (InputStream stream = new FileInputStream("src/main/resources/properties/webApp.properties")) {
            properties.load(stream);
            url = properties.getProperty("application.url");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUrl() {
        return url;
    }
}
