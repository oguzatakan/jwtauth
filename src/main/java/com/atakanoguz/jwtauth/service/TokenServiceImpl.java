package com.atakanoguz.jwtauth.service;

import com.atakanoguz.jwtauth.entity.RefreshToken;
import com.atakanoguz.jwtauth.entity.User;
import com.atakanoguz.jwtauth.exception.ApiRequestException;
import com.atakanoguz.jwtauth.repository.RefreshTokenRepository;
import com.atakanoguz.jwtauth.repository.UserRepository;
import com.atakanoguz.jwtauth.request.LoginRequest;
import com.atakanoguz.jwtauth.util.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.util.Map;

import static com.atakanoguz.jwtauth.util.SecurityConstant.EXPIRES_MONTH_REFRESH_TOKEN;


@Service
@RequiredArgsConstructor
@Transactional
public class TokenServiceImpl implements TokenService {

    private final RefreshTokenRepository tokenRepo;
    private final UserRepository userRepo;
    private final JwtTokenUtil jwtUtil;


    private RefreshToken createRefreshToken(User user) {

        RefreshToken refreshToken = new RefreshToken();

        // Generate refresh token
        String token;

        // Until the refresh token is unique
        do {

            token = jwtUtil.generateRefreshToken();

        } while (tokenRepo.existsRefreshTokenByToken(token));


        refreshToken.setUser(user);
        refreshToken.setToken(token);
        refreshToken.setExpiryDate(LocalDate.now().plusMonths(EXPIRES_MONTH_REFRESH_TOKEN));

        tokenRepo.save(refreshToken);

        return refreshToken;
    }

    @Override
    public Map<String, String> createTokens(String userName) {

        User user = userRepo.findByUserName(userName);

        String access_token = jwtUtil.createJwtToken(user);

        RefreshToken refreshToken = createRefreshToken(user);

        return Map.of("access_token", access_token,
                "refresh_token", refreshToken.getToken());

    }

    @Override
    public Map<String, String> createTokens(User user) {


        String access_token = jwtUtil.createJwtToken(user);

        RefreshToken refreshToken = createRefreshToken(user);

        return Map.of("access_token", access_token,
                "refresh_token", refreshToken.getToken());

    }

    @Override
    public UsernamePasswordAuthenticationToken getAuthToken(String token) {
        return jwtUtil.getAuthToken(token);
    }

    @Override
    public Map<String, String> refreshTokens(String refreshToken) {

        RefreshToken token = tokenRepo.findByToken(refreshToken);

        if (token != null) {

            tokenRepo.delete(token);

            return createTokens(token.getUser());
        } else
            throw new ApiRequestException("Invalid refresh token");

    }

    @Override
    public void deleteRefreshToken(String token) {

        RefreshToken refreshToken = tokenRepo.findByToken(token);

        if (refreshToken != null)
            tokenRepo.delete(refreshToken);

        else
            throw new ApiRequestException("Invalid refresh token");

    }


    @Override
    public void cleanExpiredTokens() {

        LocalDate now = LocalDate.now();

        tokenRepo.deleteAllByExpiryDateBefore(now);
    }

    @Override
    public Map<String, String> login(LoginRequest loginRequest) {
        return createTokens(userRepo.findByUserName(loginRequest.getUsername()));
    }
}
