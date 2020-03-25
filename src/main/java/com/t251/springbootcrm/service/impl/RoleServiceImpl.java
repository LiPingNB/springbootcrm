package com.t251.springbootcrm.service.impl;

import com.t251.springbootcrm.entity.Role;
import com.t251.springbootcrm.repository.RoleRepository;
import com.t251.springbootcrm.service.IRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author world
 */
@Service
public class RoleServiceImpl implements IRoleService {
    @Resource
    private RoleRepository roleRepository;
    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}
