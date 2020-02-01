package com.excalibur.myBlog.service;

import com.excalibur.myBlog.dao.Password;
import com.excalibur.myBlog.dao.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public interface PasswordService {

    Password getLastUserPassword(User user) throws UsernameNotFoundException;

    int createUserPassword(Password password);

}
