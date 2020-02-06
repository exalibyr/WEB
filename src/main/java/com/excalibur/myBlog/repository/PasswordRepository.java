package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.Password;
import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.Extended.ExtendedPasswordRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordRepository extends CrudRepository<Password, Integer>, ExtendedPasswordRepository {

    Optional<Password> findByUserOrderByCreatedDateTimeDesc(User user);

}
