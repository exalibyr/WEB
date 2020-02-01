package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.RegistrationForm;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    User createUser(RegistrationForm registrationForm) throws Exception;

    User getUser(Integer userId) throws SQLException;

    void updateUser(User user);

    List<User> getUsers(String name, String surname);

    Optional<User> getUser(String username);


}
