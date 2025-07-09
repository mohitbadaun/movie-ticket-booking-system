package com.ticketpro.movie_service.service.support;

import com.ticketpro.movie_service.entity.ShowSeat;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Component
public class SimpleBookingCalculator implements BookingCalculator {

    @Override
    public BigDecimal calculateTotal(List<ShowSeat> seats) {
        return seats.stream()
                .map(seat -> Optional.ofNullable(seat.getFinalPrice())
                        .map(BigDecimal::valueOf)
                        .orElse(BigDecimal.ZERO))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
