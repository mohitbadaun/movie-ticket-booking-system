package com.ticketpro.movie_service.service.impl.booking;

import com.ticketpro.movie_service.dto.BookingRequestDTO;
import com.ticketpro.movie_service.entity.*;
import com.ticketpro.movie_service.repository.*;
import com.ticketpro.movie_service.service.support.BookingCalculator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingCreator {

    private final BookingRepository bookingRepo;
    private final BookingSeatRepository bookingSeatRepo;
    private final ShowRepository showRepo;
    private final UserRepository userRepo;
    private final ShowSeatRepository showSeatRepo;
    private final BookingCalculator bookingCalculator;

    public Booking create(BookingRequestDTO request) {
        User user = userRepo.findById(request.getUserId()).orElseThrow();
        Show show = showRepo.findById(request.getShowId()).orElseThrow();
        List<ShowSeat> seats = showSeatRepo.findAllById(request.getSeatIds());
        BigDecimal total = bookingCalculator.calculateTotal(seats);

        Booking booking = bookingRepo.save(Booking.builder()
                .user(user)
                .show(show)
                .totalPrice(total)
                .status("PENDING")
                .build());

        for (ShowSeat seat : seats) {
            bookingSeatRepo.save(BookingSeat.builder()
                    .booking(booking)
                    .showSeat(seat)
                    .build());
        }

        return booking;
    }
}

