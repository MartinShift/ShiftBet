package com.example.shiftbet.domain.service;

import com.example.shiftbet.web.dto.GoogleTokenResponse;
import com.example.shiftbet.web.dto.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AuthService {

    private final RestTemplate restTemplate;
    private final String GOOGLE_USER_INFO_URL = "https://www.googleapis.com/oauth2/v3/userinfo";

    private final AuthenticationManager authenticationManager;
    @Autowired
    public AuthService(RestTemplate restTemplate, AuthenticationManager authenticationManager) {
        this.restTemplate = restTemplate;
        this.authenticationManager = authenticationManager;
    }

    public String exchangeCodeForAccessToken(String code) {
        String clientId = "1049877901129-ej5n9oouamij0386ofspbsebemeaseme.apps.googleusercontent.com";
        String clientSecret = "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";
        String redirectUri = "http://localhost:8081/auth/callback";
        String tokenUrl = "https://oauth2.googleapis.com/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        String requestBody = "code=" + code +
                "&client_id=" + clientId +
                "&client_secret=" + clientSecret +
                "&redirect_uri=" + redirectUri +
                "&grant_type=authorization_code";
        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);
        GoogleTokenResponse response = restTemplate.postForObject(tokenUrl, request, GoogleTokenResponse.class);
        return response.getAccessToken();
    }
    public UserInfo getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<UserInfo> response = restTemplate.exchange(
                GOOGLE_USER_INFO_URL,
                HttpMethod.GET,
                entity,
                UserInfo.class
        );

        return response.getBody();
    }
    public boolean authenticate(String email,String password)
    {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(email, password);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return true;
    }
}
