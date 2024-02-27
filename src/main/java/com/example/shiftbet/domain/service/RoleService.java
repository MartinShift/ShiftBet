package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.entity.Role;
import com.example.shiftbet.domain.repository.RoleRepository;
import com.example.shiftbet.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService  {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role find(String name)
    {
        return roleRepository.findByAuthority(name);
    }

}
