package com.example.demo.controller;


import com.example.demo.dto.AuthResponseDTO;
import com.example.demo.dto.LoginRequestDTO;
import com.example.demo.dto.RegisterRequestDTO;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import com.example.demo.service.AuthService;
import com.example.demo.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/otp")
@CrossOrigin(origins = {
    "http://localhost:3000",
    "http://localhost:5173",
    "https://ai-powered-finance-tracker.netlify.app"
})
public class OtpController {

    private final OtpService otpService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;

    public OtpController(OtpService otpService,
                         UserRepository userRepository,
                         JwtUtil jwtUtil,
                         AuthService authService,
                         PasswordEncoder passwordEncoder) {
        this.otpService = otpService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    // ── STEP 1: Send OTP ───────────────────────────────────────────────
    // POST /api/auth/otp/send
    @PostMapping("/send")
    public ResponseEntity<?> sendOtp(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String purpose = body.get("purpose"); // "LOGIN" or "REGISTER"

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Email is required"));
        }
        if (purpose == null || purpose.isBlank()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Purpose is required"));
        }

        try {
            otpService.sendOtp(email.toLowerCase().trim(), purpose.toUpperCase());
            return ResponseEntity.ok(Map.of(
                "message", "OTP sent successfully to " + email,
                "email", email
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // ── STEP 2A: Verify OTP for LOGIN ──────────────────────────────────
    // POST /api/auth/otp/verify-login
    @PostMapping("/verify-login")
    public ResponseEntity<?> verifyOtpLogin(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String otpCode = body.get("otp");

        try {
            otpService.verifyOtp(email.toLowerCase().trim(), otpCode, "LOGIN");

            // OTP valid — generate JWT
            User user = userRepository.findByEmail(email.toLowerCase().trim())
                .orElseThrow(() -> new RuntimeException("User not found"));

            String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

            return ResponseEntity.ok(Map.of(
                "token", token,
                "message", "Login successful",
                "name", user.getName(),
                "email", user.getEmail(),
                "role", user.getRole().toString()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // ── STEP 2B: Verify OTP for REGISTER ──────────────────────────────
    // POST /api/auth/otp/verify-register
    @PostMapping("/verify-register")
    public ResponseEntity<?> verifyOtpRegister(@RequestBody Map<String, Object> body) {
        String email = (String) body.get("email");
        String otpCode = (String) body.get("otp");
        String name = (String) body.get("name");
        String password = (String) body.get("password");
        String city = (String) body.getOrDefault("city", "");
        String state = (String) body.getOrDefault("state", "");
        String country = (String) body.getOrDefault("country", "India");

        try {
            // Verify OTP
            otpService.verifyOtp(email.toLowerCase().trim(), otpCode, "REGISTER");

            // Create user via AuthService
            RegisterRequestDTO dto = new RegisterRequestDTO();
            dto.setName(name);
            dto.setEmail(email);
            dto.setPassword(password);
            dto.setCity(city);
            dto.setState(state);
            dto.setCountry(country);

            AuthResponseDTO response = authService.register(dto);

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}
