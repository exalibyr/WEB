package com.excalibur.myBlog.utils;

import com.excalibur.myBlog.Application;
import com.excalibur.myBlog.configuration.WebAppConfiguration;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.fileStorage.configuration.FileStorageConfiguration;
import com.excalibur.myBlog.security.configuration.EncryptionConfiguration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationUtils {

    public enum Endpoint {
        fileStorage
    }

    private static final TextEncryptor ENCRYPTOR = Encryptors.text(
            EncryptionConfiguration.getPassword(),
            EncryptionConfiguration.getSalt()
    );
    public static final String CREATE_FILE_CALLBACK = "/fileStorage/file/create";
    private static final String ERROR_TEMPLATE = "exception";
    public static final String ERROR_URN = "/" + ERROR_TEMPLATE;
    private static final String ERROR_REDIRECT = "redirect:" + ERROR_URN;
    private static Set<String> userRolesString;
    public enum Callback {
        createFile
    }
    public enum UserRole {
        admin,
        user,
        guest
    }
    public enum PageMode {
        create,
        edit,
        delete,
        view
    }

    static {
        userRolesString = Arrays.stream(UserRole.values()).map(Enum::toString).collect(Collectors.toSet());
    }

    public static Set<String> getUserRolesString() {
        return userRolesString;
    }

    public static String getErrorTemplate() {
        return ERROR_TEMPLATE;
    }

    public static String getErrorRedirect() {
        return ERROR_REDIRECT;
    }

    public static String getLocalDateTimeString(ZonedDateTime zonedDateTime) {
        return zonedDateTime
                .format(
                        DateTimeFormatter
                                .ofLocalizedDateTime(
                                        FormatStyle.LONG,
                                        FormatStyle.SHORT
                                )
                );

    }

    public static String getUserAvatarURI(User user) {
        if (user.getId() == null || user.getAvatar() == null) {
            return FileStorageConfiguration.getDefaultAvatarURI();
        } else {
            return FileStorageConfiguration.getFileStorageURL() + "/user/"
                    + ApplicationUtils.getEncryptor().encrypt(String.valueOf(user.getId())) + "/" + user.getAvatar();
        }
    }

    public static String getAvatarMethod(User user) {
        return FileStorageConfiguration.getFileStorageURL() + "/user/" + ApplicationUtils.getEncryptor().encrypt(String.valueOf(user.getId()));
    }

    public static String getCallbackURI(Callback callback) {
        switch (callback) {
            case createFile: return WebAppConfiguration.getUrl() + CREATE_FILE_CALLBACK;
            default: return null;
        }
    }

    public static TextEncryptor getEncryptor() {
        return ENCRYPTOR;
    }

    public static String getEncryptedID(Integer id) {
        return getEncryptor().encrypt(String.valueOf(id));
    }

    public static Integer getDecryptedID(String id) {
        return Integer.valueOf(getEncryptor().decrypt(id));
    }

    public static String getApiKey(Endpoint endpoint) {
        switch (endpoint) {
            case fileStorage: return getEncryptor().encrypt(FileStorageConfiguration.getToken());
            default: return null;
        }
    }

    public static boolean checkToken(Endpoint endpoint, String token) {
        switch (endpoint) {
            case fileStorage: return FileStorageConfiguration.getToken().equals(getEncryptor().decrypt(token));
            default: return false;
        }
    }
}
