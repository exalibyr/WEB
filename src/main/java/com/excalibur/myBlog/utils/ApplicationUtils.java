package com.excalibur.myBlog.utils;

import com.excalibur.myBlog.dao.Publication;
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

    private static final TextEncryptor ENCRYPTOR = Encryptors.text(
            EncryptionConfiguration.getPassword(),
            EncryptionConfiguration.getSalt()
    );
    public static final String CREATE_FILE_CALLBACK = "/fileStorage/file/create";
    private static final String ERROR_TEMPLATE = "error";
    public static final String ERROR_URN = "/" + ERROR_TEMPLATE;
    private static final String ERROR_REDIRECT = "redirect:" + ERROR_URN;
    private static Set<String> userRolesString;
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
        if (user.getId() == null || user.getAvatarUrl() == null) {
            return FileStorageConfiguration.getDefaultAvatarURI();
        } else {
            return FileStorageConfiguration.getFileStorageURL() + "/user/" + user.getId() + "/avatar/" + user.getAvatarUrl();
        }
    }

    public static TextEncryptor getEncryptor() {
        return ENCRYPTOR;
    }

}
