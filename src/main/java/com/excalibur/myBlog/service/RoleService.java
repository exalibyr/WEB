package com.excalibur.myBlog.service;


import com.excalibur.myBlog.dao.Role;
import com.excalibur.myBlog.dao.User;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public interface RoleService {

    boolean matchWithDatabase(Set<String> roleNames);

    Set<Role> getAllowedRoles(String roleName) throws Exception;

    void giveAdminRole(User user);

    void giveUserRole(User user);

}
