package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.Password;
import com.excalibur.myBlog.dao.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PasswordService {

    Optional<Password> getLastUserPassword(User user);

    int createUserPassword(Password password);

}
