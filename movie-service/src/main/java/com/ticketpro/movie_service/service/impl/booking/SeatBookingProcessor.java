package com.ticketpro.movie_service.service.impl.booking;

import com.ticketpro.movie_service.entity.ShowSeat;
import com.ticketpro.movie_service.repository.ShowSeatRepository;
import com.ticketpro.movie_service.service.support.SeatLockManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatBookingProcessor {

    private final ShowSeatRepository showSeatRepo;
    private final SeatLockManager seatLockManager;

    public List<ShowSeat> lockSeats(List<Long> seatIds, Long userId) {
        List<ShowSeat> seats = showSeatRepo.findAllById(seatIds);

        List<Long> alreadyBooked = seats.stream()
                .filter(ShowSeat::getIsBooked)
                .map(ShowSeat::getId)
                .toList();

        if (!alreadyBooked.isEmpty()) {
            throw new RuntimeException("Seats already booked: " + alreadyBooked);
        }

        seatLockManager.lockSeats(seatIds, userId);
        return seats;
    }
}
