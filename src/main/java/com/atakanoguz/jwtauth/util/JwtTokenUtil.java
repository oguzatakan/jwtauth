package com.atakanoguz.jwtauth.util;

import com.atakanoguz.jwtauth.entity.Role;
import com.atakanoguz.jwtauth.entity.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.atakanoguz.jwtauth.util.SecurityConstant.*;
import static java.util.Arrays.stream;


@Component
public class JwtTokenUtil {

    private final Algorithm algorithm = Algorithm.HMAC256(KEY.getBytes());

    private final JWTVerifier verifier = JWT.require(algorithm).build();

    public String createJwtToken(User user) {

        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        return JWT.create().withSubject(user.getUserName()).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRES_ACCESS_TOKEN))
                .withIssuer(ISSUER).withClaim("roles", roles).sign(algorithm);

    }

    public UsernamePasswordAuthenticationToken getAuthToken(String token) {

        DecodedJWT decodedJWT = verifier.verify(token);

        String username = decodedJWT.getSubject();

        String[] roles = decodedJWT.getClaim("roles").asArray(String.class);

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));

        return new UsernamePasswordAuthenticationToken(username,null,authorities);

    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString().replace("-","");
    }


}
