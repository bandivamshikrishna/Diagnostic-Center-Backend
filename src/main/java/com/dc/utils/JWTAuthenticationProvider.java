package com.dc.utils;

import com.dc.entity.UserAuthEntity;
import com.dc.exception.TokenException;
import com.dc.serviceImpl.UserAuthServiceImpl;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class JWTAuthenticationProvider implements AuthenticationProvider {

    private final UserAuthServiceImpl userAuthService;
    private final JWTUtils jwtUtils;


    public JWTAuthenticationProvider( UserAuthServiceImpl userAuthService,JWTUtils jwtUtils){
        this.userAuthService = userAuthService;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = ((JWTAuthenticationToken) authentication).getToken();
        String email = jwtUtils.getEmailFromToken(token);
        if(email == null){
            throw new TokenException("jwt token","Invalid JWT Token");
        }

        UserAuthEntity userAuthEntity = userAuthService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userAuthEntity,null,userAuthEntity.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JWTAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
