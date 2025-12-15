package com.example.SelfPhone.Controller;

import com.example.SelfPhone.Service.GenerateCaptchaService;
import com.example.SelfPhone.Service.RecaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.awt.image.BufferedImage;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CaptchaController {

    @Autowired
    private RecaptchaService recaptchaService;
    @Autowired
    private GenerateCaptchaService generateCaptchaService;

    @PostMapping("/google-captcha")
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

    @GetMapping("/captcha")
    public String captcha(Model model, HttpSession session) {
        String code = generateCaptchaService.generateCaptchaCode();
        BufferedImage image = generateCaptchaService.generateCaptchaImage(code);
        String binaryCaptcha = generateCaptchaService.binaryConvert(image);
        model.addAttribute("binaryCaptcha", binaryCaptcha);
        session.setAttribute("captcha", code);
        return "/generateCaptcha";
    }

    @PostMapping("/captcha")
    public String validcaptcha(Model model, HttpSession session, HttpServletRequest request) {
        String code = request.getParameter("captchaInput").trim();
        if (code != null && !code.isEmpty() && code.equals(session.getAttribute("captcha"))) {
            model.addAttribute("successMessage", "Xác minh captcha thành công");
        }
        return "redirect:/generateCaptcha";
    }

}
