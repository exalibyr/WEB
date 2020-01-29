package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.User;
import com.excalibur.myBlog.repository.Extended.ExtendedUserRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer>, ExtendedUserRepository {

    Optional<List<User>> findByNameOrSurname(String name, String surname);

    Optional<User> findByUsername(String username);

}
