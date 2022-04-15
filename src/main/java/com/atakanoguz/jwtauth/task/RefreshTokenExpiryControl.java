package com.atakanoguz.jwtauth.task;

import com.atakanoguz.jwtauth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefreshTokenExpiryControl {

    private final TokenService tokenService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cleanExpiredRefreshTokens() {
        tokenService.cleanExpiredTokens();
    }

}
