package com.ticketpro.movie_service.service;

import com.ticketpro.movie_service.dto.BookingRequestDTO;
import com.ticketpro.movie_service.dto.BookingResponseDTO;
import com.ticketpro.movie_service.dto.PaymentVerificationDTO;

public interface BookingService {
    BookingResponseDTO initiateBooking(BookingRequestDTO dto);
    void verifyPayment(PaymentVerificationDTO dto);
}