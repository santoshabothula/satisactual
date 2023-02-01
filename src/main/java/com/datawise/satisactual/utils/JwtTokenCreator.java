package com.datawise.satisactual.utils;

import com.datawise.satisactual.model.JwtTokenDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenCreator {

    @Value("${application.jwt.key}")
    private String jwtKey;

    @Value("${application.jwt.issuer}")
    private String issuer;

    @Value("${application.jwt.expiration-limit}")
    private Integer expirationLimit;

    @Value("${application.jwt.subject}")
    private String subject;

    public void generateToken(HttpServletRequest request, JwtTokenDetails tokenDetails) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Date validTillDate = new Date((new Date()).getTime() + expirationLimit);
        Date issueDate = new Date();
        if (authentication != null) {
            String username = authentication.getName();
            SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

            String jwtToken = Jwts.builder()
                    .setIssuer(issuer)
                    .setIssuedAt(issueDate)
                    .setExpiration(validTillDate)
                    .setSubject(subject)
                    .claim(SecurityConstants.USER_NAME, username)
                    .claim(SecurityConstants.AUTHORITIES, "TEST_ROLE")
                    .signWith(key)
                    .compact();

            String refreshToken = Jwts.builder()
                    .setIssuer(issuer)
                    .setExpiration(new Date(validTillDate.getTime() * expirationLimit))
                    .setSubject(subject)
                    .claim(SecurityConstants.USER_NAME, username)
                    .claim(SecurityConstants.AUTHORITIES, "TEST_ROLE")
                    .signWith(key)
                    .compact();

            tokenDetails.setRefreshToken(refreshToken);
            log.info("Refresh Token successfully generated: {}", refreshToken);

            tokenDetails.setToken(jwtToken);
            tokenDetails.setValidTillDate(validTillDate);
            tokenDetails.setIssuedBy("");
            tokenDetails.setIssuedDate(issueDate);
            log.info("Token successfully generated: {}", jwtToken);
        }
    }

}
