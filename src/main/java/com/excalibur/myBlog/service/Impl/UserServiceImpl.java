package com.excalibur.myBlog.service.Impl;

import com.excalibur.myBlog.service.PasswordService;
import com.excalibur.myBlog.service.RoleService;
import com.excalibur.myBlog.service.UserService;
import com.excalibur.myBlog.dao.Password;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.RegistrationForm;
import com.excalibur.myBlog.utils.ApplicationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.excalibur.myBlog.repository.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordService passwordService;

    @Override
    public User createUser(RegistrationForm registrationForm) throws Exception {
        if (roleService.matchWithDatabase(ApplicationUtils.getUserRolesString())) {
            User newUser = registrationForm.getUser();
            Password userPassword = registrationForm.getPassword();
            userPassword.setUser(newUser);
            List<Password> passwords = new ArrayList<>();
            passwords.add(userPassword);
            newUser.setPasswords(passwords);
            newUser.setRoles(roleService.getAllowedRoles(ApplicationUtils.UserRole.user.toString()));
            newUser.setId(userRepository.saveUser(newUser));
            userRepository.saveUserRoles(newUser);
            passwordService.createUserPassword(userPassword);
            return newUser;
        } else {
            throw new SQLException("Roles not matched to database");
        }
    }

    @Override
    public Optional<User> getUser(Integer userId){
        return userRepository.findById(userId);
    }

    @Override
    public void updateUser(User user){
        userRepository.updateUser(user);
    }

    @Override
    public Optional<List<User>> getUsers(String name, String surname){
        return userRepository.findByNameOrSurname(name, surname);
    }

    @Override
    public Optional<User> getUser(String username) {
        return userRepository.findByUsername(username);
    }
}
