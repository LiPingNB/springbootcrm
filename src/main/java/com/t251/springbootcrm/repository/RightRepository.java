package com.t251.springbootcrm.repository;

import com.t251.springbootcrm.entity.Right;
import com.t251.springbootcrm.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RightRepository extends JpaRepository<Right,String> {
    public List<Right>findRightsByRoles(Role role);
}
