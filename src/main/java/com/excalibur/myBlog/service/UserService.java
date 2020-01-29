package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.form.RegistrationForm;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService {

    User createUser(RegistrationForm registrationForm) throws Exception;

    Optional<User> findUserById(Integer userId);

    void updateUser(User user);

    Optional<List<User>> findUsersByNameOrSurname(String name, String surname);

    Optional<User> getUser(String username);


}
