package com.excalibur.myBlog.repository.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConfiguration {

    // metadata params
    public static final String FK_TABLE_NAME_LABEL = "fktable_name";
    public static final String FK_COLUMN_NAME_LABEL = "fkcolumn_name";
    public static final String PK_TABLE_NAME_LABEL = "pktable_name";
    public static final String PK_COLUMN_NAME_LABEL = "pkcolumn_name";
    public static final String TABLE_NAME_LABEL = "TABLE_NAME";
    public static final String COLUMN_NAME_LABEL = "COLUMN_NAME";
    public static final String TYPE_NAME_LABEL = "TYPE_NAME";
    public static final String DATA_TYPE_LABEL = "DATA_TYPE";

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
