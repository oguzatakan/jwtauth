package com.atakanoguz.jwtauth.util;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import static com.atakanoguz.jwtauth.util.SecurityConstant.KEY;


@Component
public class JwtTokenUtil {

    private final Algorithm algorithm = Algorithm.HMAC256(KEY.getBytes());


}
