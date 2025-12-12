package com.example.SelfPhone.Controller;

import com.example.SelfPhone.Service.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CaptchaController {
    
    @Autowired
    private RecaptchaService recaptchaService;
    
    @PostMapping("/verify-captcha")
    public ResponseEntity<?> verifyCaptcha(@RequestBody Map<String, String> payload) {
        String token = payload.get("token");
        
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest()
                .body(Map.of("success", false, "message", "Token is required"));
        }
        
        boolean isValid = recaptchaService.verify(token);
        
        if (isValid) {
            return ResponseEntity.ok()
                .body(Map.of("success", true, "message", "Captcha verified successfully!"));
        } else {
            return ResponseEntity.status(400)
                .body(Map.of("success", false, "message", "Invalid captcha"));
        }
    }
}
