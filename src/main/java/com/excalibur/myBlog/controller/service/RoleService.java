package com.excalibur.myBlog.controller.service;


import com.excalibur.myBlog.dao.Role;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.utils.Environment;

import java.util.List;
import java.util.Set;

public interface RoleService {

    boolean matchWithDatabase(Set<String> roleNames);

    Set<Role> getAllowedRoles(String roleName);

    void giveAdminRole(User user);

    void giveUserRole(User user);

}
