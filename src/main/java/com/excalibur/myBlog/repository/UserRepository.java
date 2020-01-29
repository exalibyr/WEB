package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.dao.VerificationData;
import com.excalibur.myBlog.repository.Extended.ExtendedUserRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>, ExtendedUserRepository {

    Optional<List<User>> findByNameOrSurname(String name, String surname);

    Optional<User> findByVerificationData(VerificationData verificationData);

    Optional<User> findByUsername(String username);

}
