package com.excalibur.myBlog.utils;


import com.excalibur.myBlog.dao.VerificationData;
import com.excalibur.myBlog.repository.VerificationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private VerificationDataRepository verificationDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try{
            VerificationData verificationData = verificationDataRepository.findByLogin(username);
            return new User(verificationData.getLogin(),
                    verificationData.getPassword(),
                    verificationData.getUser().getRoles());
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }




}
