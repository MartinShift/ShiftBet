package com.example.shiftbet.web.controllers;

import com.example.shiftbet.domain.aspect.Loggable;
import com.example.shiftbet.domain.entity.Role;
import com.example.shiftbet.domain.entity.User;
import com.example.shiftbet.domain.service.AuthService;
import com.example.shiftbet.domain.service.RoleService;
import com.example.shiftbet.domain.service.UserService;
import com.example.shiftbet.web.dto.GoogleTokenResponse;
import com.example.shiftbet.web.dto.LoginRequest;
import com.example.shiftbet.web.dto.RegistrationRequest;
import com.example.shiftbet.web.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.UUID;

@Controller
public class AuthController {

    private final UserService userService;

    private final AuthService authService;

    @Autowired
    public AuthController(UserService userService, RoleService roleService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @GetMapping("/auth/login")
    public String Login(Model model)
    {
        model.addAttribute("loginRequest", new LoginRequest());
        return "/auth/login";
    }

    @PostMapping("/auth/submitLogin")
    public String submitLogin(LoginRequest request)
    {
       authService.authenticate(request.getEmail(),request.getPassword());
        return "redirect:/main/view";
    }
    @GetMapping("/auth/register")
    public String getRegisterForm(Model model)
    {
        model.addAttribute("registrationRequest",new RegistrationRequest());
        return "/auth/register";
    }

    @PostMapping("/auth/submitRegister")
    @Loggable
    public String registerUser(RegistrationRequest request, Model model) {
        if (userService.emailExists(request.getEmail())) {
            return "redirect:/auth/register?message=email_exists";
        }

        userService.create(request.getEmail(),request.getPassword());
        model.addAttribute("loginRequest",new LoginRequest(request.getEmail(),request.getPassword()));
        return "/auth/login-submit";
    }

    @GetMapping("/auth/google")
    public String googleSignIn() {
        return "redirect:https://accounts.google.com/o/oauth2/auth?" +
                "client_id=1049877901129-ej5n9oouamij0386ofspbsebemeaseme.apps.googleusercontent.com" +
                "&redirect_uri=http://localhost:8081/auth/callback" +
                "&response_type=code" +
                "&scope=email%20profile";
    }

    @GetMapping("/auth/callback")
    @Loggable
    public String googleCallback(@RequestParam("code") String code, Model model)
    {
        String tokenResponse = authService.exchangeCodeForAccessToken(code);
        UserInfo userInfo = authService.getUserInfo(tokenResponse);
        String email = userInfo.getEmail();
        var password = UUID.randomUUID().toString();
        if (userService.emailExists(email)) {
            User user = userService.loadUserByUsername(email);
            user.setPassword(new BCryptPasswordEncoder().encode(password));
            userService.save(user);
        } else {
            userService.create(email, new BCryptPasswordEncoder().encode(password));
        }
        model.addAttribute("loginRequest",new LoginRequest(email,password));
        return "/auth/login-submit";
    }
}
