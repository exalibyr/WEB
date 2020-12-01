package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.dao.wrapper.UserWrapper;
import com.excalibur.myBlog.form.RegistrationForm;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface UserService {

    User createUser(RegistrationForm registrationForm) throws Exception;

    User getUser(Integer userId) throws SQLException;

    void updateUser(User user);

    void updateUser(Map<String, Object> payload) throws Exception;

    void updateUsers(List<Object> payload) throws Exception;

    List<User> getUsers(String name, String surname);

    List<User> getUsers();

    User getUser(String username) throws UsernameNotFoundException;

    List<UserWrapper> getUserWrappers(String name, String surname);

    UserWrapper getUserWrapper(String encryptedId) throws SQLException;

}
