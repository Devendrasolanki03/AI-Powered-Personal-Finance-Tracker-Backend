// package com.example.demo.config;

// import java.util.List;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.http.HttpStatus;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.config.Customizer;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
// import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
// import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.http.SessionCreationPolicy;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.web.SecurityFilterChain;
// import org.springframework.security.web.authentication.HttpStatusEntryPoint;
// import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
// import org.springframework.web.cors.*;

// import com.example.demo.security.JwtFilter;
// import com.example.demo.security.OAuth2LoginSuccessHandler;

// @Configuration
// @EnableMethodSecurity
// public class SecurityConfig {

//     private final JwtFilter jwtFilter;
//     private final OAuth2LoginSuccessHandler oauthSuccessHandler;

//     public SecurityConfig(JwtFilter jwtFilter,
//                           OAuth2LoginSuccessHandler oauthSuccessHandler) {
//         this.jwtFilter = jwtFilter;
//         this.oauthSuccessHandler = oauthSuccessHandler;
//     }

//     @Bean
//     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

//         http
//             .cors(Customizer.withDefaults())
//             .csrf(csrf -> csrf.disable())

//             .sessionManagement(session ->
//                 session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//             )

//             .authorizeHttpRequests(auth -> auth

//                 // 🔓 PUBLIC
//                 .requestMatchers(
//                         "/api/auth/**",
//                         "/api/admin/auth/**",
//                         "/oauth2/**",
//                         "/api/auth/otp/**"
//                 ).permitAll()

//                 // 👤 USER APIs
//                 .requestMatchers(
//                         "/api/finance/**",
//                         "/api/budgets/**",
//                         "/api/ai/**",
//                         "/api/expense-report/**",
//                         "/api/reports/**",
//                         "/api/dashboard/**",
//                         "/api/categories/**",
//                         "/api/user/**"
//                 ).hasAnyRole("USER", "ADMIN")

//                 // 👑 ADMIN APIs
//                 .requestMatchers("/api/admin/**")
//                 .hasRole("ADMIN")

//                 .anyRequest().authenticated()
//             )

//             // ✅ CRITICAL FIX - Return 401 instead of redirecting to OAuth
//             .exceptionHandling(exception -> exception
//                 .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
//             )

//             // ✅ OAuth only when explicitly used
//             .oauth2Login(oauth ->
//                 oauth.successHandler(oauthSuccessHandler)
//             )

//             .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//         return http.build();
//     }

//     @Bean
//     public PasswordEncoder passwordEncoder() {
//         return new BCryptPasswordEncoder();
//     }

//     @Bean
//     public AuthenticationManager authenticationManager(
//             AuthenticationConfiguration config) throws Exception {
//         return config.getAuthenticationManager();
//     }

//     @Bean
//     public CorsConfigurationSource corsConfigurationSource() {

//         CorsConfiguration config = new CorsConfiguration();
//       config.setAllowedOrigins(List.of(
//     "http://localhost:3000",
//     "http://localhost:5173",
//     "https://ai-powered-finance-tracker.netlify.app"  // ✅ Yeh hona chahiye
// ));
//         config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
//         config.setAllowedHeaders(List.of("*"));
//         config.setAllowCredentials(true);

//         UrlBasedCorsConfigurationSource source =
//                 new UrlBasedCorsConfigurationSource();
//         source.registerCorsConfiguration("/**", config);

//         return source;
//     }
// }

package com.example.demo.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.*;

import com.example.demo.security.JwtFilter;
import com.example.demo.security.OAuth2LoginSuccessHandler;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    // ✅ Env variable se aayega — hardcode nahi karna padega kabhi
    // Render pe set karo: ALLOWED_ORIGINS=https://ai-powered-finance-tracker.netlify.app
    @Value("${allowed.origins:http://localhost:3000,http://localhost:5173}")
    private String allowedOrigins;

    private final JwtFilter jwtFilter;
    private final OAuth2LoginSuccessHandler oauthSuccessHandler;

    public SecurityConfig(JwtFilter jwtFilter,
                          OAuth2LoginSuccessHandler oauthSuccessHandler) {
        this.jwtFilter = jwtFilter;
        this.oauthSuccessHandler = oauthSuccessHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())

            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )

            .authorizeHttpRequests(auth -> auth

                // 🔓 PUBLIC
                .requestMatchers(
                        "/api/auth/**",
                        "/api/admin/auth/**",
                        "/oauth2/**",
                        "/api/auth/otp/**"
                ).permitAll()

                // 👤 USER APIs
                .requestMatchers(
                        "/api/finance/**",
                        "/api/budgets/**",
                        "/api/ai/**",
                        "/api/expense-report/**",
                        "/api/reports/**",
                        "/api/dashboard/**",
                        "/api/categories/**",
                        "/api/user/**"
                ).hasAnyRole("USER", "ADMIN")

                // 👑 ADMIN APIs
                .requestMatchers("/api/admin/**")
                .hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )

            .oauth2Login(oauth ->
                oauth.successHandler(oauthSuccessHandler)
            )

            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // ✅ Comma-separated env variable se origins read karo
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        config.setAllowedOrigins(origins);

        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
