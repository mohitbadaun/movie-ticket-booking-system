package com.ticketpro.movie_service.service.support;

import java.util.List;

public interface SeatLockManager {
    void lockSeats(List<Long> seatIds, Long userId);
    void releaseLock(Long seatId);
}
