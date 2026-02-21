package com.example.demo.security;




import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.entity.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.User;
import java.util.UUID;
import java.io.IOException;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepo;

    public OAuth2LoginSuccessHandler(JwtUtil jwtUtil, UserRepository userRepo) {
        this.jwtUtil = jwtUtil;
        this.userRepo = userRepo;
    }

//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication)
//            throws IOException, ServletException {
//
//        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
//        String email = oauthUser.getAttribute("email");
//        String name = oauthUser.getAttribute("name");
//
//        // Save user if not exists
//        
//
//        User user = userRepo.findByEmail(email).orElseGet(() -> {
//            User u = new User();
//            u.setEmail(email);
//            u.setName(name);
//            u.setRole(Role.USER);
//            u.setProvider("GOOGLE");
//
//            // ✅ VERY IMPORTANT FOR GOOGLE USERS
//            u.setPassword(UUID.randomUUID().toString());
//
//            return userRepo.save(u);
//        });
//
//        // Generate JWT
//        String token = jwtUtil.generateToken(email, user.getRole());
//
//        // Redirect to React with token
//        response.sendRedirect("http://localhost:3000/oauth-success?token=" + token);
//    }
    
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request,
//                                        HttpServletResponse response,
//                                        Authentication authentication) throws IOException {
//
//        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
//        String email = oauthUser.getAttribute("email");
//        String name = oauthUser.getAttribute("name");
//
//        // 🔥 Save user if not exists
//        User user = userRepo.findByEmail(email).orElseGet(() -> {
//            User u = new User();
//            u.setEmail(email);
//            u.setName(name);
//            u.setRole(Role.USER);
//            u.setProvider("GOOGLE");
//            u.setPassword(UUID.randomUUID().toString()); // random password
//            return userRepo.save(u);
//        });
//
//        // 🔐 Generate JWT
//        String token = jwtUtil.generateToken(email, user.getRole());
//
//        // ✅ Return JSON response
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.setContentType("application/json");
//        response.setCharacterEncoding("UTF-8");
//
//        response.getWriter().write("""
//        {
//          "message": "Google Login Successful",
//          "token": "%s",
//          "user": {
//            "email": "%s",
//            "name": "%s",
//            "role": "%s",
//            "provider": "GOOGLE"
//          }
//        }
//        """.formatted(token, email, name, user.getRole()));
//    }
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        OAuth2User oauthUser = (OAuth2User) authentication.getPrincipal();
        String email = oauthUser.getAttribute("email");
        String name = oauthUser.getAttribute("name");

        User user = userRepo.findByEmail(email).orElseGet(() -> {
            User u = new User();
            u.setEmail(email);
            u.setName(name);
            u.setRole(Role.USER);
            u.setProvider("GOOGLE");
            u.setPassword(UUID.randomUUID().toString());
            return userRepo.save(u);
        });

        String token = jwtUtil.generateToken(email, user.getRole());

        // 🔥 MUST REDIRECT (NOT JSON)
        response.sendRedirect(
            "http://localhost:3000/oauth-success?token=" + token
        );
    }

}
