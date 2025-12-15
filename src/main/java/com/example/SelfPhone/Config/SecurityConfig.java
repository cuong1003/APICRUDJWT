package com.example.SelfPhone.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Autowired
    checkValidToken checkValidToken;
    @Autowired
    RateLimitFilter rateLimitFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(x -> x
                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/generateCaptcha.html").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/google-captcha").permitAll()
                .requestMatchers(HttpMethod.GET, "/captcha").permitAll()
                .requestMatchers(HttpMethod.POST, "/captcha").permitAll()
                .requestMatchers(HttpMethod.POST, "/phones/add/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.PUT, "/phones/update/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/phones/delete/**").hasAuthority("SCOPE_ADMIN")
                .requestMatchers(HttpMethod.GET, "/phones/**").hasAnyAuthority("SCOPE_USER", "SCOPE_ADMIN")
                .anyRequest().authenticated());

        httpSecurity.oauth2ResourceServer(auth -> auth.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));

        httpSecurity.addFilterBefore(checkValidToken, BearerTokenAuthenticationFilter.class);

        httpSecurity.addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        return httpSecurity.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec key = new SecretKeySpec(
                jwtSecret.getBytes(StandardCharsets.UTF_8),
                "HmacSHA512");
        return NimbusJwtDecoder
                .withSecretKey(key)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

}