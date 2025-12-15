package com.example.SelfPhone.Controller;

import com.example.SelfPhone.Service.GenerateCaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.image.BufferedImage;

@Controller
public class GenerateCaptchaController {
    @Autowired
    private GenerateCaptchaService generateCaptchaService;

    @GetMapping("/captcha")
    public String captcha(Model model, HttpSession session) {
        String code = generateCaptchaService.generateCaptchaCode();
        BufferedImage image = generateCaptchaService.generateCaptchaImage(code);
        String binaryCaptcha = generateCaptchaService.binaryConvert(image);
        model.addAttribute("binaryCaptcha", binaryCaptcha);
        session.setAttribute("captcha", code);
        return "generateCaptcha";
    }

    @PostMapping("/captcha")
    public String validcaptcha(RedirectAttributes redirectAttributes, HttpSession session, HttpServletRequest request) {
        String code = request.getParameter("captchaInput");

        if (code == null || code.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Vui lòng nhập mã captcha");
            return "redirect:/captcha";
        }

        code = code.trim();
        String storedCaptcha = (String) session.getAttribute("captcha");

        if (storedCaptcha != null && code.equals(storedCaptcha)) {
            redirectAttributes.addFlashAttribute("successMessage", "Xác minh captcha thành công");
            // Xóa captcha đã sử dụng
            session.removeAttribute("captcha");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Mã captcha không chính xác");
        }

        return "redirect:/captcha";
    }
}
