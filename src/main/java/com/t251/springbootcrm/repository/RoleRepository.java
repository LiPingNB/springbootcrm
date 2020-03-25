package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.Role;
import com.t251.springbootcrm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    public Role findRoleByUsers(User user);
    public List<Role>findRolesByRoleNameLike(String roleName);
}
