package com.sujal.API_monitoring.Security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sujal.API_monitoring.service.JWTService;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;
    private final UserDetailsService userDetailsService;


    public JWTAuthenticationFilter(JWTService jwtService, UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);


        try{

        String email = jwtService.extractUsername(token);
        System.out.println("JWT EMAIL = " + email);


        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                  
             System.out.println(
            "USER DETAILS = " + userDetails.getUsername()
        );

        System.out.println(
            "AUTHORITIES = " + userDetails.getAuthorities()
        );

        System.out.println(
            "TOKEN VALID = " +
            jwtService.isTokenValid(token, userDetails)
        );

            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                 System.out.println(
                "SECURITY CONTEXT = " +
                SecurityContextHolder.getContext()
                        .getAuthentication()
            );
            }

        }
    } catch (JwtException e) {
        
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("applicaton/json");
        response.getWriter().write("{\"message\":\"Invalid or expired Token\"}");

        return;

    }
        filterChain.doFilter(request, response);
        
    }


    
}
