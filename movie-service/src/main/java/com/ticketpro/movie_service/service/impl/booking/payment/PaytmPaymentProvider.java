package com.ticketpro.movie_service.service.impl.booking.payment;

import com.ticketpro.movie_service.entity.Booking;
import org.springframework.stereotype.Component;

@Component("PAYTM")
public class PaytmPaymentProvider implements PaymentProvider {
    @Override
    public String generatePaymentLink(String bookingId, String referenceId) {
        return "https://paytm.com/pay/" + referenceId;
    }
}

