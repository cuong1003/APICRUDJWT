package com.example.SelfPhone.Service;

import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Service
public class GenerateCaptchaService {

    private static final int CAPTCHA_WIDTH = 200;
    private static final int CAPTCHA_HEIGHT = 50;
    private static final int CAPTCHA_LENGTH = 6;
    private static final int THRESHOLD = 128;
    private static final Random RANDOM = new Random();

    public String generateCaptchaCode() {
        String text = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder captchaCode = new StringBuilder();
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            captchaCode.append(text.charAt(RANDOM.nextInt(text.length())));
        }
        return captchaCode.toString();
    }

    public BufferedImage generateCaptchaImage(String captchaCode) {
        BufferedImage image = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);

        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < 5; i++) {
            g2d.drawLine(0, RANDOM.nextInt(CAPTCHA_HEIGHT), CAPTCHA_WIDTH, RANDOM.nextInt(CAPTCHA_HEIGHT));
        }
        for (int i = 0; i < 100; i++) {
            g2d.fillRect(RANDOM.nextInt(CAPTCHA_WIDTH), RANDOM.nextInt(CAPTCHA_HEIGHT), 1, 1);
        }
        g2d.setColor(Color.BLACK);
        Font font = new Font("Arial", Font.BOLD, 35);
        g2d.setFont(font);
        int x = 15;
        for (char c : captchaCode.toCharArray()) {

            double rotateAngle = RANDOM.nextDouble() * 0.4 - 0.2; // Xoay +/- 0.2 radian
            g2d.rotate(rotateAngle, x, CAPTCHA_HEIGHT / 2);

            g2d.drawString(String.valueOf(c), x + RANDOM.nextInt(5), 40 + RANDOM.nextInt(5) - 5);

            g2d.rotate(-rotateAngle, x, CAPTCHA_HEIGHT / 2);
            x += 30; // Tăng khoảng cách ký tự
        }
        g2d.dispose();

        return image;
    }

    public String binaryConvert(BufferedImage bufferedImage) {
        StringBuilder binaryData = new StringBuilder();
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                Color color = new Color(bufferedImage.getRGB(x, y));
                int grayValue = (color.getRed() + color.getGreen() + color.getBlue()) / 3;

                if (grayValue < THRESHOLD) {
                    binaryData.append('1');
                } else {
                    binaryData.append('0');
                }
            }
        }
        return binaryData.toString();
    }
}
