package com.example.shiftbet.domain.repository;

import com.example.shiftbet.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long>
{
public User findByUsername(String username);
}
