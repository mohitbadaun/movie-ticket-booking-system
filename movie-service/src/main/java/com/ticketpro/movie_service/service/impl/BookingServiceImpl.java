package com.ticketpro.movie_service.service.impl;

import com.ticketpro.movie_service.dto.BookingRequestDTO;
import com.ticketpro.movie_service.dto.BookingResponseDTO;
import com.ticketpro.movie_service.dto.PaymentVerificationDTO;
import com.ticketpro.movie_service.service.BookingService;
import com.ticketpro.movie_service.service.impl.booking.BookingInitiator;
import com.ticketpro.movie_service.service.impl.booking.PaymentVerifier;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingInitiator bookingInitiator;
    private final PaymentVerifier paymentVerifier;

    @Override
    public BookingResponseDTO initiateBooking(BookingRequestDTO request) {
        return bookingInitiator.initiate(request);
    }

    @Override
    public void verifyPayment(PaymentVerificationDTO dto) {
        paymentVerifier.verify(dto);
    }
}
