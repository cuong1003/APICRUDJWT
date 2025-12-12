package com.example.SelfPhone.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RecaptchaService {
    @Value("${recaptcha.secret}")
    private String secretKey;

    public boolean verify(String token) {
        try {
            RestTemplate restTemplate = new RestTemplate();


            String url = "https://www.google.com/recaptcha/api/siteverify";
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("secret", secretKey);
            params.add("response", token);

            Map<String, Object> response = restTemplate.postForObject(
                    url,
                    params,
                    Map.class
            );
            if (response != null && response.containsKey("success")) {
                return (Boolean) response.get("success");
            }

            return false;

        } catch (Exception e) {
            System.err.println("Error verifying reCAPTCHA: " + e.getMessage());
            return false;
        }
    }
}
