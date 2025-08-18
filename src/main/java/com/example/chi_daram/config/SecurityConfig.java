package com.example.chi_daram.config;

import com.example.chi_daram.security.CustomUserDetailsService;
import com.example.chi_daram.security.jwt.JwtAuthenticationFilter;
import com.example.chi_daram.security.jwt.AuthEntryPointJwt;
import com.example.chi_daram.security.jwt.JwtUtils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {


    private final CustomUserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;
    private final AuthEntryPointJwt unauthorizedHandler;

    @Value("${frontend.urls}")
    private List<String> frontendUrls;

    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtUtils jwtUtils, AuthEntryPointJwt unauthorizedHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.unauthorizedHandler = unauthorizedHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return new JwtAuthenticationFilter(jwtUtils, userDetailsService);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // غیرفعال کردن CSRF برای APIهای RESTful
                .exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        // مسیرهای احراز هویت (ثبت نام و ورود) عمومی هستند
                        .requestMatchers("/api/auth/**").permitAll()
                        // مسیرهای Swagger UI و API Docs عمومی هستند
                        .requestMatchers(
                                "/v3/api-docs/**", // API definition endpoint
                                "/swagger-ui.html", // Main Swagger UI page
                                "/swagger-ui/**", // Swagger UI resources (JS, CSS, etc.)
                                "/api-docs/**", // Alternative API docs path
                                "/webjars/**" // Resources used by Swagger UI
                        ).permitAll()
                        // سایر درخواست‌ها نیاز به احراز هویت دارند
                        .anyRequest().authenticated()
                )
                // اضافه کردن پیکربندی CORS
                .cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Bean برای پیکربندی CORS
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(frontendUrls); // استفاده از لیست از application.properties
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // اعمال تنظیمات CORS برای تمام مسیرها
        return source;
    }
}