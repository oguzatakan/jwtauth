package com.atakanoguz.jwtauth.controller;

import com.atakanoguz.jwtauth.entity.RefreshToken;
import com.atakanoguz.jwtauth.entity.User;
import com.atakanoguz.jwtauth.request.LoginRequest;
import com.atakanoguz.jwtauth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserController userController;
    private final TokenService tokenService;

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshTokens(@Valid @RequestBody RefreshToken refreshToken) {

        Map<String, String> newTokens = tokenService.refreshTokens(refreshToken.getToken());

        return ResponseEntity.ok(newTokens);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok().body(tokenService.login(loginRequest));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logout(@Valid @RequestBody RefreshToken refreshToken) {

        tokenService.deleteRefreshToken(refreshToken.getToken());

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<User> signup(@Valid @RequestBody User user) {
        return userController.saveUser(user);
    }

}
