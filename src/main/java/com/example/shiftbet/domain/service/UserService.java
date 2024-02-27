package com.example.shiftbet.domain.service;

import com.example.shiftbet.domain.entity.Role;
import com.example.shiftbet.domain.entity.User;
import com.example.shiftbet.domain.repository.RoleRepository;
import com.example.shiftbet.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User findById(long id)
    {
        return userRepository.findById(id).orElse(null);
    }
    public void save(User user)
    {
        userRepository.save(user);
    }
    public boolean emailExists(String username)
    {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
         return userRepository.findByUsername(username);
    }

    public User loadUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return loadUserByUsername(currentPrincipalName);
    }
    public void create(String email, String password)
    {
        User user = new User();
        user.setUsername( email);
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        user.setBalance(0);
        user.setEnabled(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        user.setCredentialsNonExpired(true);
        Role userRole = roleRepository.findByAuthority("ROLE_USER");

        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);

    }
}
