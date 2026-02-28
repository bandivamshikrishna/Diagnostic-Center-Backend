package com.dc.config;

import com.dc.repository.APILoggingRepository;
import com.dc.repository.UserAuthTokenRepository;
import com.dc.serviceImpl.UserAuthServiceImpl;
import com.dc.utils.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class UserAuthConfig {

    private static final List<String> publicURLs = List.of("/api/user/login",
            "/api/user/validate-token");

    public static List<String> getPublicURLs(){
        return publicURLs;
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET","POST", "PUT", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthenticationManager authenticationManager,
                                                   JWTUtils jwtUtils, LogoutHandler logoutHandler,
                                                   UserAuthTokenRepository userAuthTokenRepository,
                                                   APILoggingRepository apiLoggingRepository) throws Exception {

        JWTValidationFilter jwtValidationFilter = new JWTValidationFilter(authenticationManager);
        JWTRefreshFilter jwtRefreshFilter = new JWTRefreshFilter(authenticationManager,jwtUtils,userAuthTokenRepository);
        APILoggingFilter apiLoggingFilter = new APILoggingFilter(apiLoggingRepository);


        return  http
                .cors(
                        cors -> cors.configurationSource(corsConfigurationSource())
                )
        .authorizeHttpRequests(
                auth -> auth
                        .requestMatchers(
                                 publicURLs.toArray(new String[0]))
                        .permitAll()
                        .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtRefreshFilter, JWTValidationFilter.class)
                .addFilterAfter(apiLoggingFilter, JWTRefreshFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/user/logout")
                        .addLogoutHandler(logoutHandler)
                        .logoutSuccessHandler(((request,
                                                response,
                                                authentication) -> {
                            response.setContentType("application/json");
                            response.setStatus(HttpServletResponse.SC_OK);
                            response.getWriter().write("{\"message\": \"User Logged Out Successfully..\"}");

                        })))
                        .build();
    }



    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(UserAuthServiceImpl userAuthService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userAuthService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(@Lazy DaoAuthenticationProvider daoAuthenticationProvider, @Lazy JWTAuthenticationProvider jwtAuthenticationProvider){
        return new ProviderManager(Arrays.asList(daoAuthenticationProvider,jwtAuthenticationProvider));
    }

    @Bean
    public JWTAuthenticationProvider jwtAuthenticationProvider(JWTUtils jwtUtils,UserAuthServiceImpl userAuthService){
        return new JWTAuthenticationProvider(userAuthService,jwtUtils);
    }

}



