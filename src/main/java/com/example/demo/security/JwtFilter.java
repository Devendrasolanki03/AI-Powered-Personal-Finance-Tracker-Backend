//package com.example.demo.security;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import io.jsonwebtoken.Claims;
//
//import java.io.IOException;
//import java.util.List;
//
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    private final JwtUtil jwtUtil;
//
//    public JwtFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) {
//        String path = request.getServletPath();
//        return path.startsWith("/api/auth") || path.startsWith("/oauth2");
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain chain)
//            throws ServletException, IOException {
//
//        if (SecurityContextHolder.getContext().getAuthentication() == null) {
//
//            String header = request.getHeader("Authorization");
//
//            if (header != null && header.startsWith("Bearer ")) {
//
//                String token = header.substring(7);
//
//                try {
//                    if (!jwtUtil.isTokenExpired(token)) {
//
//                        Claims claims = jwtUtil.extractClaims(token);
//
//                        String email = claims.getSubject();
//                        String role = claims.get("role", String.class); // USER / ADMIN
//
//                        UsernamePasswordAuthenticationToken auth =
//                                new UsernamePasswordAuthenticationToken(
//                                        email,
//                                        null,
//                                        List.of(
//                                            new SimpleGrantedAuthority("ROLE_" + role)
//                                        )
//                                );
//
//                        SecurityContextHolder.getContext().setAuthentication(auth);
//                    }
//
//                } catch (Exception e) {
//                    SecurityContextHolder.clearContext();
//                }
//            }
//        }
//
//        chain.doFilter(request, response);
//    }
//}



package com.example.demo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/api/auth")
            || path.startsWith("/api/admin/auth")  // ✅ Admin login bypass
            || path.startsWith("/oauth2");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        if (SecurityContextHolder.getContext().getAuthentication() == null) {

            String header = request.getHeader("Authorization");

            if (header != null && header.startsWith("Bearer ")) {

                String token = header.substring(7);

                try {
                    if (!jwtUtil.isTokenExpired(token)) {

                        Claims claims = jwtUtil.extractClaims(token);

                        String email = claims.getSubject();
                        String role = claims.get("role", String.class);

                        UsernamePasswordAuthenticationToken auth =
                                new UsernamePasswordAuthenticationToken(
                                        email,
                                        null,
                                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                                );

                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }

                } catch (Exception e) {
                    SecurityContextHolder.clearContext();
                }
            }
        }

        chain.doFilter(request, response);
    }
}