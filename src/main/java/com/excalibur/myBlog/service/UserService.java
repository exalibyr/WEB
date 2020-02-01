package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.RegistrationForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    User createUser(RegistrationForm registrationForm) throws Exception;

    Optional<User> getUser(Integer userId);

    void updateUser(User user);

    Optional<List<User>> getUsers(String name, String surname);

    Optional<User> getUser(String username);


}
