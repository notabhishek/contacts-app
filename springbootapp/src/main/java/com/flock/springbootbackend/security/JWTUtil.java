package com.flock.springbootbackend.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.flock.springbootbackend.utils.Constants;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTUtil {

    @Value(Constants.AuthContants.JWT_SECRET)
    private String secret;

    public String generateToken(String email) throws IllegalArgumentException, JWTCreationException {
        return JWT.create()
                .withSubject(Constants.AuthContants.USER_DETAILS)
                .withClaim(Constants.CommonConstants.EMAIL, email)
                .withIssuedAt(new Date())
                .withIssuer(Constants.CommonConstants.CONTACTS_APP)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrieveSubject(String token)throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(Constants.AuthContants.USER_DETAILS)
                .withIssuer(Constants.CommonConstants.CONTACTS_APP)
                .build();
        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(Constants.CommonConstants.EMAIL).asString();
    }

}

