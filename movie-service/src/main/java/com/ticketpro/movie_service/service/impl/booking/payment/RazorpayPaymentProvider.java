package com.ticketpro.movie_service.service.impl.booking.payment;


import com.ticketpro.movie_service.entity.Booking;
import org.springframework.stereotype.Component;

@Component("RAZORPAY")
public class RazorpayPaymentProvider implements PaymentProvider {
    @Override
    public String generatePaymentLink(String bookingId, String referenceId) {
        return "https://razorpay.com/pay/" + referenceId;
    }
}
