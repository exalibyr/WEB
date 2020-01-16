package com.excalibur.myBlog.controller.service;

import com.excalibur.myBlog.dao.VerificationData;
import org.springframework.beans.factory.annotation.Autowired;
import com.excalibur.myBlog.repository.VerificationDataRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerificationDataService {

    @Autowired
    private VerificationDataRepository verificationDataRepository;

    public Optional<VerificationData> verifyUser(String login, String password){
        return verificationDataRepository.findByLoginAndPassword(login, password);
    }


    public VerificationData findByLogin(String login){
        return verificationDataRepository.findByLogin(login);
    }

    public int saveData(VerificationData verificationData) {
        return verificationDataRepository.saveVerificationData(verificationData);
    }

}
