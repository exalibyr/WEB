package com.excalibur.myBlog.repository.Extended.Impl;

import com.excalibur.myBlog.dao.Password;
import com.excalibur.myBlog.repository.Extended.ExtendedPasswordRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

public class ExtendedPasswordRepositoryImpl implements ExtendedPasswordRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public int saveNewPassword(Password password) {
        Query query = entityManager.createNativeQuery(
                "INSERT INTO password (value, created_date_time, user_id) " +
                        "VALUES (:value, :dateTime, :userId)"
        );
        query.setParameter("userId", password.getUser().getId());
        query.setParameter("value", password.getValue());
        query.setParameter("dateTime", password.getCreatedDateTime());
        return query.executeUpdate();
    }
}
