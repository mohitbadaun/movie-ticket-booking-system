package com.ticketpro.movie_service.service.impl.booking.payment;


import com.ticketpro.movie_service.entity.Booking;

public interface PaymentProvider {
    String generatePaymentLink(String bookingId, String referenceId);
}
