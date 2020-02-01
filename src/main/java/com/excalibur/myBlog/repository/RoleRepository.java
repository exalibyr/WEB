package com.excalibur.myBlog.repository;

import com.excalibur.myBlog.dao.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    Optional<Role> findByRoleName(String roleName);

    Optional<Set<Role>> findByPriorityGreaterThan(Integer priority);

}
