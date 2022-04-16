package com.atakanoguz.jwtauth.repository;

import com.atakanoguz.jwtauth.entity.RefreshToken;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    RefreshToken findByToken(String token);

    boolean existsRefreshTokenByToken(String token);

    void deleteAllByExpiryDateBefore(LocalDate expiryDate);

}
