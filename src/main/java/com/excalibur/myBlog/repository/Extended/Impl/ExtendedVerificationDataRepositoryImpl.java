package com.excalibur.myBlog.repository.Extended.Impl;

import com.excalibur.myBlog.dao.VerificationData;
import com.excalibur.myBlog.repository.Extended.ExtendedVerificationDataRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class ExtendedVerificationDataRepositoryImpl implements ExtendedVerificationDataRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public int saveVerificationData(VerificationData verificationData) {
        Query query = entityManager.createNativeQuery("INSERT INTO user_verification_data (user_id, user_login, user_password) VALUES (:userId, :login, :password)");
        query.setParameter("userId", verificationData.getUser().getId());
        query.setParameter("login", verificationData.getLogin());
        query.setParameter("password", verificationData.getPassword());
        return query.executeUpdate();
    }
}
