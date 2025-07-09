package com.ticketpro.movie_service.service.impl.booking;

import com.ticketpro.movie_service.dto.BookingRequestDTO;
import com.ticketpro.movie_service.dto.BookingResponseDTO;
import com.ticketpro.movie_service.entity.Booking;
import com.ticketpro.movie_service.service.impl.booking.payment.PaymentLinkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingInitiator {

    private final BookingValidator validator;
    private final SeatBookingProcessor seatBookingProcessor;
    private final BookingCreator bookingCreator;
    private final PaymentLinkService paymentLinkService;

    public BookingResponseDTO initiate(BookingRequestDTO request) {
        validator.validate(request);
        seatBookingProcessor.lockSeats(request.getSeatIds(), request.getUserId());

        Booking booking = bookingCreator.create(request);

        String paymentLink = paymentLinkService.generate(booking.getId());

        return BookingResponseDTO.builder()
                .bookingId(booking.getId())
                .totalAmount(booking.getTotalPrice())
                .paymentLink(paymentLink)
                .build();
    }
}
