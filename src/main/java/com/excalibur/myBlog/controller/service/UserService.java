package com.excalibur.myBlog.controller.service;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.dao.VerificationData;
import com.excalibur.myBlog.repository.VerificationDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.excalibur.myBlog.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private VerificationDataRepository verificationDataRepository;

    public User registerNewUser(User user){
        user.setId(userRepository.saveUser(user));
        userRepository.saveUserRoles(user);
        verificationDataRepository.saveVerificationData(user.getVerificationData());
        return user;
    }

    public void deleteUser(User user){
        userRepository.delete(user);
    }

    public Optional<User> findUserById(Integer userId){
        return userRepository.findById(userId);
    }

    public void updateUser(User user){
        userRepository.updateUser(user);
    }

    public Optional<List<User>> findUsersByNameOrSurname(String name, String surname){
        return userRepository.findByNameOrSurname(name, surname);
    }

    public Optional<User> findUserByVerificationData(VerificationData verificationData){
        return userRepository.findByVerificationData(verificationData);
    }

}
