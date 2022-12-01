package com.datawise.satisactual.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JwtTokenValidator {

    public void validateJwtToken(HttpServletRequest request, HttpServletResponse response, boolean isRefreshValidation, String jwtKey) {

        String token = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);
        String refresh = request.getHeader(SecurityConstants.REFRESH_HEADER);
        log.info("Authorization Token: {}", token);

        if (token != null && !token.contains("Basic")) {
            try {
                SecretKey key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

                Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(isRefreshValidation ? refresh : token).getBody();

                String username = String.valueOf(claims.get(SecurityConstants.USER_NAME));
                String authorities = (String) claims.get(SecurityConstants.AUTHORITIES);

                Authentication auth = new UsernamePasswordAuthenticationToken(username, null, getRoles(authorities));
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (ExpiredJwtException ex) {
                log.info("Token expired!");
                throw new BadCredentialsException("Token expired!");
            } catch (Exception e) {
                throw new BadCredentialsException("Invalid Token received!");
            }
        }
    }

    private List<GrantedAuthority> getRoles(String authorities) {
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        String[] roles = authorities.split(",");
        for (String role : roles) {
            grantedAuthorityList.add(new SimpleGrantedAuthority(role.replaceAll("\\s+", "")));
        }

        return grantedAuthorityList;
    }
}
