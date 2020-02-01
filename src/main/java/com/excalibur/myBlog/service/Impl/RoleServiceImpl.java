package com.excalibur.myBlog.service.Impl;

import com.excalibur.myBlog.service.RoleService;
import com.excalibur.myBlog.dao.Role;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public boolean matchWithDatabase(Set<String> roleNames) {
        List<Role> databaseRoles = (List<Role>) roleRepository.findAll();
        for (Role role : databaseRoles) {
            String roleName = role.getRoleName();
            boolean matched = false;
            for (String localRoleName : roleNames) {
                if (localRoleName.equals(roleName)) matched = true;
            }
            if (!matched) return false;
        }
        return true;
    }

    @Override
    public void giveAdminRole(User user) {

    }

    @Override
    public void giveUserRole(User user) {

    }

    @Override
    public Set<Role> getAllowedRoles(String roleName) throws Exception {
        Role role = roleRepository.findByRoleName(roleName)
                .orElseThrow(() -> new SQLException("RoleServiceImpl.getAllowedRoles(String roleName): Role not found"));
        Set<Role> allowedRoles = roleRepository.findByPriorityGreaterThan(role.getPriority())
                .orElseThrow(() -> new SQLException("RoleServiceImpl.getAllowedRoles(String roleName): No allowed roles found"));
        allowedRoles.add(role);
        return allowedRoles;
    }
}
