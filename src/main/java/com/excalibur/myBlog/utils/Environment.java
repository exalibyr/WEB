package com.excalibur.myBlog.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class Environment {

    private static String fileServerDomain;
    private static String defaultAvatarURI;
    private static String databaseURL;
    private static String databaseLogin;
    private static String databasePassword;
    public enum UserRole {
        admin,
        user,
        guest
    }
    private static Set<String> userRolesString;

    static {
        Properties properties = new Properties();
        try (InputStream stream = new FileInputStream("src/main/resources/application.properties")) {
            properties.load(stream);
            defaultAvatarURI = properties.getProperty("fileServer.avatar.default.path");
            fileServerDomain = properties.getProperty("fileServer.domain");
            databaseURL = properties.getProperty("spring.datasource.url");
            databaseLogin = properties.getProperty("spring.datasource.username");
            databasePassword = properties.getProperty("spring.datasource.password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        userRolesString = Arrays.stream(UserRole.values()).map(Enum::toString).collect(Collectors.toSet());
    }

    public static String getFileServerDomain() {
        return fileServerDomain;
    }

    public static void setFileServerDomain(String fileServerDomain) {
        Environment.fileServerDomain = fileServerDomain;
    }

    public static String getDefaultAvatarURI() {
        return defaultAvatarURI;
    }

    public static void setDefaultAvatarURI(String defaultAvatarURI) {
        Environment.defaultAvatarURI = defaultAvatarURI;
    }

    public static Set<String> getUserRolesString() {
        return userRolesString;
    }

    public static void setUserRolesString(Set<String> userRolesString) {
        Environment.userRolesString = userRolesString;
    }

    public static String getDatabaseURL() {
        return databaseURL;
    }

    public static void setDatabaseURL(String databaseURL) {
        Environment.databaseURL = databaseURL;
    }

    public static String getDatabaseLogin() {
        return databaseLogin;
    }

    public static void setDatabaseLogin(String databaseLogin) {
        Environment.databaseLogin = databaseLogin;
    }

    public static String getDatabasePassword() {
        return databasePassword;
    }

    public static void setDatabasePassword(String databasePassword) {
        Environment.databasePassword = databasePassword;
    }
}
