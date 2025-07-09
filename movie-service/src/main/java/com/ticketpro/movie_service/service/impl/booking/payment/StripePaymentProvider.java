package com.ticketpro.movie_service.service.impl.booking.payment;

import com.ticketpro.movie_service.entity.Booking;
import org.springframework.stereotype.Component;

@Component("STRIPE")
public class StripePaymentProvider implements PaymentProvider {
    @Override
    public String generatePaymentLink(String bookingId, String referenceId) {
        return "https://stripe.com/pay/" + referenceId;
    }
}
