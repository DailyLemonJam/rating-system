package com.leverx.ratingsystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeService {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${confirmationCode.lifetimeHours}")
    private Long confirmationCodeLifetimeHours;

    public void save(String confirmationCode, String email) {
        redisTemplate.opsForValue().set(confirmationCode, email, confirmationCodeLifetimeHours, TimeUnit.HOURS);
    }

    public String get(String confirmationCode) {
        return redisTemplate.opsForValue().get(confirmationCode);
    }

    public Boolean exists(String confirmationCode) {
        return redisTemplate.hasKey(confirmationCode);
    }

    public void delete(String confirmationCode) {
        redisTemplate.delete(confirmationCode);
    }

}
