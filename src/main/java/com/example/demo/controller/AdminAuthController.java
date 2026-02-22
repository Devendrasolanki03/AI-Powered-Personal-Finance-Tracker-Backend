////package com.example.demo.controller;
////
////import java.util.HashMap;
////import java.util.Map;
////
////import org.springframework.http.ResponseEntity;
////import org.springframework.web.bind.annotation.*;
////
////import com.example.demo.security.JwtUtil;
////import com.example.demo.entity.Role;
////
////@CrossOrigin(origins = "http://localhost:3000")
////@RestController
////@RequestMapping("/api/admin/auth")
////public class AdminAuthController {
////    
////    private final JwtUtil jwtUtil;  // ✅ FIXED: Was JwtTokenProvider
////    
////    public AdminAuthController(JwtUtil jwtUtil) {
////        this.jwtUtil = jwtUtil;
////    }
////    
////    @PostMapping("/login")
////    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
////        String email = credentials.get("email");
////        String password = credentials.get("password");
////        
////        // Demo admin check - TODO: Replace with database lookup
////        if ("admin@financeai.com".equals(email) && "admin123".equals(password)) {
////            
////            // ✅ Generate JWT with ADMIN role
////            String token = jwtUtil.generateToken(email, Role.ADMIN);
////            
////            Map<String, Object> response = new HashMap<>();
////            response.put("token", token);
////            
////            Map<String, String> user = new HashMap<>();
////            user.put("email", email);
////            user.put("name", "Admin User");
////            user.put("role", "ADMIN");
////            
////            response.put("user", user);
////            
////            return ResponseEntity.ok(response);
////        }
////        
////        return ResponseEntity.status(401).body("Invalid credentials");
////    }
////}
//
//package com.example.demo.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.example.demo.security.JwtUtil;
//import com.example.demo.entity.Role;
//
//@RestController
//@RequestMapping("/api/admin/auth")
//@CrossOrigin(origins = "http://localhost:3000")
//public class AdminAuthController {
//
//    private final JwtUtil jwtUtil;
//
//    public AdminAuthController(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
//
//        String email = credentials.get("email");
//        String password = credentials.get("password");
//
//        // TEMP ADMIN LOGIN (replace with DB later)
//        if ("admin@financeai.com".equals(email) && "admin123".equals(password)) {
//
//            String token = jwtUtil.generateToken(email, Role.ADMIN);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("token", token);
//
//            Map<String, String> user = new HashMap<>();
//            user.put("email", email);
//            user.put("name", "Admin User");
//            user.put("role", "ADMIN");
//
//            response.put("user", user);
//
//            return ResponseEntity.ok(response);
//        }
//
//        return ResponseEntity.status(401).body("Invalid credentials");
//    }
//}



package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.exception.InvalidRequestException;
import com.example.demo.service.AuthService;

@RestController
@RequestMapping("/api/admin/auth")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://ai-powered-finance-tracker.netlify.app"
})
public class AdminAuthController {

    private final AuthService authService;

    public AdminAuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO dto) {
        try {
            AuthResponseDTO response = authService.login(dto);

            // ✅ Sirf ADMIN role wale login kar sakte hain
            if (!"ADMIN".equals(response.getRole())) {
                return ResponseEntity.status(403).body("Access denied. Admin only.");
            }

            return ResponseEntity.ok(response);

        } catch (InvalidRequestException e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
