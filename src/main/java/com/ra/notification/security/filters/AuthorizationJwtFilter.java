package com.ra.notification.security.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ra.notification.dto.response.ValidateTokenResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ra.notification.util.Constants;
import com.ra.notification.util.JwtUtil;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthorizationJwtFilter extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthorizationJwtFilter.class);

    private final JwtUtil jwtUtil;

    public AuthorizationJwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!hasToken(request)) {
            LOGGER.info("Empty Token");
            filterChain.doFilter(request, response);
            return;
        }
       
        String token = getToken(request);

        if(!jwtUtil.validateJwtToken(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        setAuthentication(token, request);

        filterChain.doFilter(request, response);
    }

    private void setAuthentication(String token, HttpServletRequest request) {

        String email = jwtUtil.getUserNameFromJwtToken(token);

        LOGGER.info(request.getHeader("authority"));
        LOGGER.info(request.getHeader(Constants.HEADER));

        String[] authorities = { request.getHeader("authority") };

        List<SimpleGrantedAuthority> simpleAuthorities = new ArrayList<>();
        simpleAuthorities.add(new SimpleGrantedAuthority(authorities[0]));

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
            email,
            null,
            simpleAuthorities);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    private boolean hasToken(HttpServletRequest request) {
        String header = request.getHeader(Constants.HEADER);
        return !ObjectUtils.isEmpty(header) && header.startsWith(Constants.TOKEN_PREFIX);
    }

    private String getToken(HttpServletRequest request) {
        String header = request.getHeader(Constants.HEADER);
        return header.split("\\s")[1].trim();
    }
}