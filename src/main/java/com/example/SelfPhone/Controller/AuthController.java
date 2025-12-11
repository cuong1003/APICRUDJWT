package com.example.SelfPhone.Controller;

import com.example.SelfPhone.Db.Dto.LoginRequest;
import com.example.SelfPhone.Db.Dto.LoginRespone;
import com.example.SelfPhone.Db.Entity.RefreshToken;
import com.example.SelfPhone.Db.Entity.User;
import com.example.SelfPhone.Db.Repository.RefreshTokenRepository;
import com.example.SelfPhone.Db.Repository.UserRepository;
import com.example.SelfPhone.Service.AuthService;
import com.example.SelfPhone.Service.JwtSerice;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
    @Autowired
    JwtSerice jwtSerice;
    @Autowired
    RefreshTokenRepository refreshTokenRepository;
    @Autowired
    UserRepository userRepository;


    @PostMapping("/login")
    public LoginRespone login(@RequestBody LoginRequest loginRequest) {
        User user = authService.validateLogin(loginRequest.getUsername(), loginRequest.getPassword());
        if (user != null) {
            String accessToken = jwtSerice.generateAccessToken(user.getUsername(), user.getRole());
            String refreshToken = jwtSerice.generateRefreshToken(user.getUsername());
            refreshTokenRepository.save(new RefreshToken(user.getId(), refreshToken));
            return new LoginRespone(user.getUsername(), accessToken, refreshToken, "ok!", true);
        }
        return new LoginRespone(null, null, null, "fail!", false);
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.substring(7);
        authService.bannedToken(token);
        return "Đăng xuất thành công!";
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            return ResponseEntity.status(401).build();
        }
        String refreshToken = header.substring(7);
        String username = jwtSerice.getUsernameInToken(refreshToken);
        Integer userId = userRepository.findUserIdByUsername(username).getId();
        Optional<RefreshToken> savedRToken = refreshTokenRepository.findAllByUserIdAndToken(userId, refreshToken);
        if (savedRToken.isPresent()) {
            String newAccessTolen = jwtSerice.generateAccessToken(username, userRepository.findRoleByUsername(username).getRole());
            return ResponseEntity.ok(new LoginRespone(username, newAccessTolen, refreshToken, "Đã cấp accessToken mới!", true));
        }
        return ResponseEntity.status(401).build();

    }

}
