package com.ticketpro.movie_service.service.support;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SeatLockManagerRedisImpl implements SeatLockManager {

    private final RedisTemplate<String, String> redisTemplate;

    private static final Duration TTL = Duration.ofMinutes(5);

    @Override
    public void lockSeats(List<Long> seatIds, Long userId) {
        for (Long seatId : seatIds) {
            String key = "seat_lock:" + seatId;
            Boolean acquired = redisTemplate.opsForValue().setIfAbsent(key, userId.toString(), TTL);
            if (Boolean.FALSE.equals(acquired)) {
                throw new RuntimeException("Seat " + seatId + " already locked");
            }
        }
    }

    @Override
    public void releaseLock(Long seatId) {
        redisTemplate.delete("seat_lock:" + seatId);
    }
}
