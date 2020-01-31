package com.excalibur.myBlog.utils;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationUtils {

    private static final String ERROR_TEMPLATE = "error";
    private static final String ERROR_REDIRECT = "redirect:/error";
    public static final String ERROR_URN = "/error";
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

}
