package com.dc.utils;

import com.dc.exception.TokenException;
import com.dc.repository.UserAuthTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTRefreshFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtils jwtUtils;
    private final UserAuthTokenRepository userAuthTokenRepository;


    public JWTRefreshFilter(AuthenticationManager authenticationManager,JWTUtils jwtUtils,
                            UserAuthTokenRepository userAuthTokenRepository){
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userAuthTokenRepository = userAuthTokenRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        if (!request.getServletPath().equals("/api/user/refreshToken")){
            filterChain.doFilter(request,response);
            return;
        }

        String refreshToken = getRefreshTokenFromCookie(request);
        if(refreshToken == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\":\"Invalid Token\"}");
            return;
        }

        Boolean isValidToken = userAuthTokenRepository.findByToken(refreshToken)
                .map(t->!t.getTokenRevoked()).orElse(false);
        if(!isValidToken){
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"message\":\"Invalid Token\"}");
            return;
        }

        JWTAuthenticationToken jwtAuthenticationToken = new JWTAuthenticationToken(refreshToken);
        Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);
        if (authentication.isAuthenticated()){
            String accessToken = jwtUtils.generateToken(authentication.getName(),true);
            response.setHeader("Authorization","Bearer "+accessToken);
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }


    public String getRefreshTokenFromCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies == null)
            return null;
        for(Cookie cookie: cookies){
            if(cookie.getName().equals("refreshToken"))
                return cookie.getValue();
        }
        return null;
    }
}
