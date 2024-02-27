package com.example.shiftbet.domain.repository;

import com.example.shiftbet.domain.entity.Role;
import com.example.shiftbet.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository  extends JpaRepository<Role,Long>
{
public Role findByAuthority(String authority);
}