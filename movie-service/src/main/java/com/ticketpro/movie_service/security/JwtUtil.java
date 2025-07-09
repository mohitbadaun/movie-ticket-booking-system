package com.ticketpro.movie_service.security;

import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.net.URL;
import java.security.interfaces.RSAPublicKey;

public class JwtUtil {

    public DecodedJWT verifyToken(String token, String jwksUrl, String issuer, String audience) {
        try {


            // Create JWKS provider
            JwkProvider provider = new UrlJwkProvider(new URL(jwksUrl));

            // Decode token without verifying
            DecodedJWT jwt = JWT.decode(token);

            // Get public key from JWKS using key ID (kid)
            Jwk jwk = provider.get(jwt.getKeyId());
            RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();

            // Build RSA algorithm
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

            // Create verifier
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
                    .withAudience(audience)
                    .build();

            // Verify token
            return verifier.verify(token);

        } catch (Exception e) {
            throw new JWTVerificationException("JWT verification failed: " + e.getMessage());
        }
    }
}
