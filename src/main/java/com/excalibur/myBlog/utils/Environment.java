package com.excalibur.myBlog.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {

    public static String fileServerDomain;
    public static String defaultAvatarURI;

    static {
        Properties properties = new Properties();
        try (InputStream stream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(stream);
            defaultAvatarURI = properties.getProperty("fileServer.avatar.default.path");
            fileServerDomain = properties.getProperty("fileServer.domain");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
