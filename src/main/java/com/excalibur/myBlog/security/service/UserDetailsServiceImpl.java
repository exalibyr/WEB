package com.excalibur.myBlog.security.service;


import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.service.PasswordService;
import com.excalibur.myBlog.dao.Password;
import com.excalibur.myBlog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordService passwordService;

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userService.getUser(username);
            Password password = passwordService.getLastUserPassword(user);
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    password.getValue(),
                    user.getRoles()
            );
        } catch (Exception e){
            e.printStackTrace();
            throw new UsernameNotFoundException(e.getMessage());
        }
    }




}
