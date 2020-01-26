package com.excalibur.myBlog.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class Environment {

    public static final String ERROR_TEMPLATE = "error-page";
    public static final String ERROR_REDIRECT = "redirect:/error";
    private static String fileStorageURL;
    private static String defaultAvatarURN;
    private static String welcomeURN;
    private static String errorURN;
    private static String defaultAvatarURI;
    private static String welcomeURI;
    private static String errorURI;
    private static String databaseURL;
    private static String databaseLogin;
    private static String databasePassword;
    public enum UserRole {
        admin,
        user,
        guest
    }
    public enum Mode {
        create,
        edit,
        delete,
        view
    }
    private static Set<String> userRolesString;

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
        userRolesString = Arrays.stream(UserRole.values()).map(Enum::toString).collect(Collectors.toSet());
    }

    public static String getFileStorageURL() {
        return fileStorageURL;
    }


    public static String getDefaultAvatarURN() {
        return defaultAvatarURN;
    }


    public static Set<String> getUserRolesString() {
        return userRolesString;
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

    public static String getWelcomeURN() {
        return welcomeURN;
    }

    public static String getDefaultAvatarURI() {
        return defaultAvatarURI;
    }

    public static String getWelcomeURI() {
        return welcomeURI;
    }

    public static String getErrorURN() {
        return errorURN;
    }

    public static String getErrorURI() {
        return errorURI;
    }
}
