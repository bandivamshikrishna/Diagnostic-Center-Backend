package com.dc.utils;

import com.dc.config.UserAuthConfig;
import com.dc.exception.TokenException;
import com.dc.repository.UserAuthTokenRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JWTValidationFilter extends OncePerRequestFilter {

    private final AuthenticationManager authenticationManager;

    public JWTValidationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromRequest(request);
        List<String> publicURLs = UserAuthConfig.getPublicURLs();

        if(publicURLs.contains(request.getServletPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        if(token != null) {
        JWTAuthenticationToken jwtAuthenticationToken = new JWTAuthenticationToken(token);
        try {
            Authentication authentication = authenticationManager.authenticate(jwtAuthenticationToken);
            if (authentication.isAuthenticated())
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            catch(TokenException e){
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"message\":\"Invalid Token\"}");
                return;
            }
        }


        filterChain.doFilter(request,response);
    }


    public static String getTokenFromRequest(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(bearerToken != null && bearerToken.startsWith("Bearer "))
            return bearerToken.substring(7);
        return null;
    }
}
