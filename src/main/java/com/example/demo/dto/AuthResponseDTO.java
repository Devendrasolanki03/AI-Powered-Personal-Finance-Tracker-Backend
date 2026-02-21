package com.example.demo.dto;

public class AuthResponseDTO {

    private String token;
    private String message;

    // User Info (Frontend Needs)
    private String name;
    private String email;
    private String role;
    private String provider;

    public AuthResponseDTO() {}

    public AuthResponseDTO(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public AuthResponseDTO(String token, String message,
                           String name, String email,
                           String role, String provider) {
        this.token = token;
        this.message = message;
        this.name = name;
        this.email = email;
        this.role = role;
        this.provider = provider;
    }

    // getters & setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }
}
