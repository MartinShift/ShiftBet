package com.example.shiftbet.domain.config;

import com.example.shiftbet.domain.repository.UserRepository;
import com.example.shiftbet.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.client.RestTemplate;

import java.util.Properties;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final AuthenticationConfiguration authConfiguration;
    private final UserService userService;
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authConfiguration.getAuthenticationManager();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder builder = http.getSharedObject(AuthenticationManagerBuilder.class);
        AuthenticationManager manager = builder.build();

        http
                .authorizeHttpRequests(auth -> {
                    auth
                            .requestMatchers("/admin/**").hasRole("ADMIN")
                            .requestMatchers("/user/**").hasAnyRole("USER","ADMIN")
                            .requestMatchers("/main/**","/auth/**","/css/**","/images/**","/js/**","/locale/**","/").permitAll()
                            .anyRequest().authenticated();
                })
                .formLogin(login -> {
                    login.loginPage("/auth/login")
                            .loginProcessingUrl("/auth/submitLogin")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/main/view", true)
                            .permitAll();
                })
                .logout(logout -> {
                    logout.invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID")
                            .clearAuthentication(true)
                            .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                            .logoutSuccessUrl("/auth/login");
                })
                .exceptionHandling(handling -> {
                    handling
                            .accessDeniedPage("/403");
                })
                .rememberMe(rememberMe -> {
                    rememberMe.alwaysRemember(true)
                            .key("asdqwafasfasfasf")
                            .userDetailsService(userService)
                            .tokenValiditySeconds(7200);
                })
                .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .csrf(csrf->csrf.disable())
                .authenticationManager(manager);

        return http.build();
    }
}
