package com.atakanoguz.jwtauth.service;

import com.atakanoguz.jwtauth.entity.User;
import com.atakanoguz.jwtauth.request.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Map;

public interface TokenService {

    Map<String, String> createTokens(String userName);

    Map<String, String> createTokens(User user);

    UsernamePasswordAuthenticationToken getAuthToken(String token);

    Map<String, String> refreshTokens(String refreshToken);

    void deleteRefreshToken(String token);

    void cleanExpiredTokens();

    Map<String, String > login(LoginRequest loginRequest);
}
