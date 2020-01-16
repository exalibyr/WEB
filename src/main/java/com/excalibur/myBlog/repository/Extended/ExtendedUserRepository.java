package com.excalibur.myBlog.repository.Extended;

import com.excalibur.myBlog.dao.Role;
import com.excalibur.myBlog.dao.User;

import java.util.Optional;
import java.util.Set;

public interface ExtendedUserRepository {

    Integer saveUser(User user);

    Integer saveUserRoles(User user);

    Integer updateUser(User user);

}
