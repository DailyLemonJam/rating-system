package com.leverx.ratingsystem.service.confirmation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ConfirmationCodeServiceImpl implements ConfirmationCodeService {
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${confirmationCode.lifetimeHours}")
    private Long confirmationCodeLifetimeHours;

    @Override
    public void save(String confirmationCode, String email) {
        redisTemplate.opsForValue().set(confirmationCode, email, confirmationCodeLifetimeHours, TimeUnit.HOURS);
    }

    @Override
    public String get(String confirmationCode) {
        return redisTemplate.opsForValue().get(confirmationCode);
    }

    @Override
    public Boolean exists(String confirmationCode) {
        return redisTemplate.hasKey(confirmationCode);
    }

    @Override
    public void delete(String confirmationCode) {
        redisTemplate.delete(confirmationCode);
    }

}
