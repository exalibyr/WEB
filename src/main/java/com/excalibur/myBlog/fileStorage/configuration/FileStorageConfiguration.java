package com.excalibur.myBlog.fileStorage.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FileStorageConfiguration {

    private static String fileStorageURL;
    private static String defaultAvatarURN;
    private static String welcomeURN;
    private static String errorURN;
    private static String defaultAvatarURI;
    private static String welcomeURI;
    private static String errorURI;

    static {
        Properties properties = new Properties();
        try (InputStream stream = new FileInputStream("src/main/resources/properties/fileStorage.properties")) {
            properties.load(stream);
            defaultAvatarURN = properties.getProperty("fileStorage.avatar.default.path");
            fileStorageURL = properties.getProperty("fileStorage.url");
            welcomeURN = properties.getProperty("fileStorage.welcome.path");
            errorURN = properties.getProperty("fileStorage.error.path");
        } catch (IOException e) {
            e.printStackTrace();
        }
        defaultAvatarURI = fileStorageURL + defaultAvatarURN;
        welcomeURI = fileStorageURL + welcomeURN;
        errorURI = fileStorageURL + errorURN;
    }

    public static String getFileStorageURL() {
        return fileStorageURL;
    }

    public static String getDefaultAvatarURN() {
        return defaultAvatarURN;
    }

    public static String getWelcomeURN() {
        return welcomeURN;
    }

    public static String getErrorURN() {
        return errorURN;
    }

    public static String getDefaultAvatarURI() {
        return defaultAvatarURI;
    }

    public static String getWelcomeURI() {
        return welcomeURI;
    }

    public static String getErrorURI() {
        return errorURI;
    }
}
