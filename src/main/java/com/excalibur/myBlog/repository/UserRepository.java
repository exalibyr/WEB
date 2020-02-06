package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.Extended.ExtendedUserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>, ExtendedUserRepository {

    Optional<List<User>> findByNameOrSurname(String name, String surname);

    Optional<User> findByUsername(String username);

}
