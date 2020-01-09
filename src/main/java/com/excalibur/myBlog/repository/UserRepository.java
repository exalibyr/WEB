package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.File;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.dao.VerificationData;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {

    Optional<List<User>> findByNameOrSurname(String name, String surname);

    Optional<User> findByVerificationData(VerificationData verificationData);

}
