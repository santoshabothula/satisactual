package com.datawise.satisactual.auth;

import com.datawise.satisactual.utils.JwtTokenValidator;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@NoArgsConstructor
public class JwtTokenValidationFilter extends OncePerRequestFilter {

    private String jwtKey;

    public JwtTokenValidationFilter(String jwtKey) {
        this.jwtKey = jwtKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            logger.info("Request received for Jwt token validation");
            JwtTokenValidator validator = new JwtTokenValidator();
            validator.validateJwtToken(request, response, jwtKey);
            filterChain.doFilter(request, response);
        } catch (BadCredentialsException ex) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(ex.getMessage());
        }
    }
}
