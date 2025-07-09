package com.ticketpro.movie_service.service.support;

import com.ticketpro.movie_service.entity.ShowSeat;

import java.math.BigDecimal;
import java.util.List;

public interface BookingCalculator {
    BigDecimal calculateTotal(List<ShowSeat> seats);
}
