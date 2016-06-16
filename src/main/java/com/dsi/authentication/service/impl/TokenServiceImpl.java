package com.dsi.authentication.service.impl;

import com.dsi.authentication.resource.LoginResource;
import com.dsi.authentication.util.Constants;
import com.dsi.authentication.util.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.log4j.Logger;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

/**
 * Created by sabbir on 6/15/16.
 */
public class TokenServiceImpl {

    private static final Logger logger = Logger.getLogger(TokenServiceImpl.class);

    public String createToken(String id, String issuer, String subject, long time) {
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(Utils.getTokenSecretKey(Constants.SECRET_KEY));
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (time >= 0) {
            long expMillis = nowMillis + time;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    public Claims parseToken(String accessToken) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(Utils.getTokenSecretKey(Constants.SECRET_KEY)))
                    .parseClaimsJws(accessToken).getBody();

        } catch (Exception e){
            logger.error("Failed to parse token: " + e.getMessage());
        }
        return claims;
    }
}
