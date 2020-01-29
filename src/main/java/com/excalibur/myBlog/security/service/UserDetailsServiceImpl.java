package com.excalibur.myBlog.security.service;


import com.excalibur.myBlog.service.PasswordService;
import com.excalibur.myBlog.dao.Password;
import com.excalibur.myBlog.repository.UserRepository;
import com.excalibur.myBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            Optional<com.excalibur.myBlog.dao.User> user = userService.getUser(username);
            if (user.isPresent()) {
                Optional<Password> password = passwordService.getLastUserPassword(user.get());
                if (password.isPresent()) {
                    return new org.springframework.security.core.userdetails.User(user.get().getUsername(),
                            password.get().getValue(),
                            user.get().getRoles());
                } else {
                    throw new SecurityException("password not found");
                }
            } else {
                throw new SecurityException("username not found");
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        }
    }




}
