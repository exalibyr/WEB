package com.excalibur.myBlog.repository.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfiguration {

    private static String databaseURL;
    private static String databaseLogin;
    private static String databasePassword;

    static {
        Properties properties = new Properties();
        try (InputStream stream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(stream);
            databaseURL = properties.getProperty("spring.datasource.url");
            databaseLogin = properties.getProperty("spring.datasource.username");
            databasePassword = properties.getProperty("spring.datasource.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getDatabaseURL() {
        return databaseURL;
    }

    public static String getDatabaseLogin() {
        return databaseLogin;
    }

    public static String getDatabasePassword() {
        return databasePassword;
    }
}
