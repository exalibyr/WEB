package com.excalibur.myBlog.repository.Extended;

import com.excalibur.myBlog.dao.Role;
import com.excalibur.myBlog.dao.User;

import java.sql.ResultSet;
import java.util.Optional;
import java.util.Set;

public interface ExtendedUserRepository {

    ResultSet findByNameSurname(String name, String surname);

    Integer saveUser(User user);

    Integer saveUserRoles(User user);

    Integer updateUser(User user);

}
