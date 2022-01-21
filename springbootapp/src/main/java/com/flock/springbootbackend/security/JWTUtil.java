package com.flock.springbootbackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.flock.springbootbackend.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value(Utils.AuthContants.JWT_SECRET)
    private String secret;

    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject(Utils.AuthContants.USER_DETAILS)
                .withClaim(Utils.CommonConstants.EMAIL, email)
                .withIssuedAt(new Date())
                .withIssuer(Utils.CommonConstants.CONTACTSAPP)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(Utils.AuthContants.USER_DETAILS)
                .withIssuer(Utils.CommonConstants.CONTACTSAPP)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(Utils.CommonConstants.EMAIL).asString();
    }

}

