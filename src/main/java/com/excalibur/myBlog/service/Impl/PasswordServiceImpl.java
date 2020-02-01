package com.excalibur.myBlog.service.Impl;

import com.excalibur.myBlog.service.PasswordService;
import com.excalibur.myBlog.dao.Password;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService {

    @Autowired
    private PasswordRepository passwordRepository;

    @Override
    public Password getLastUserPassword(User user) throws UsernameNotFoundException {
        return passwordRepository.findByUserOrderByCreatedDateTimeDesc(user)
                .orElseThrow(() -> new UsernameNotFoundException("PasswordServiceImpl.getLastUserPassword(User user): No data found"));
    }

    @Override
    public int createUserPassword(Password password) {
        return passwordRepository.savePassword(password);
    }
}
