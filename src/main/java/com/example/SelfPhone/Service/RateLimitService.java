package com.example.SelfPhone.Service;

import com.example.SelfPhone.Db.Entity.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@Service
public class RateLimitService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public boolean isAllowedToRateLimit(Integer userId) {
        String key = "RateLimit:" + userId;
        long now = Instant.now().getEpochSecond();
        long start = now - 60;
        //Xoa tu 0 -> start
        redisTemplate.opsForZSet().removeRangeByScore(key, 0, start);
        //dem cac phan tu dang luu
        Long count = redisTemplate.opsForZSet().size(key);
        if (count != null && count > 50) {
            return false;
        }
        redisTemplate.opsForZSet().add(key, String.valueOf(userId + System.nanoTime()),now);
        redisTemplate.expire(key, Duration.ofSeconds(70));
        return true;
    }
}
