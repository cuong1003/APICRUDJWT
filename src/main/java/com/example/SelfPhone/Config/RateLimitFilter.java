package com.example.SelfPhone.Config;

import com.example.SelfPhone.Db.Repository.UserRepository;
import com.example.SelfPhone.Service.JwtSerice;
import com.example.SelfPhone.Service.RateLimitService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class RateLimitFilter extends OncePerRequestFilter {
    @Autowired
    private RateLimitService rateLimitService;
    @Autowired
    private JwtSerice jwtSerice;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Integer userId = userRepository.findRoleByUsername(jwtSerice.getUsernameInToken(token)).getId();
            if(!rateLimitService.isAllowedToRateLimit(userId)) {
                response.setStatus(429);
                response.setContentType("application/json");
                response.getWriter().write("{\"error\": \"Too many requests. Please try again later.\"}");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
