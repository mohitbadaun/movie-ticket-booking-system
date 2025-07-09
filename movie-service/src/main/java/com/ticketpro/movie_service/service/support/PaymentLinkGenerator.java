package com.ticketpro.movie_service.service.support;

public interface PaymentLinkGenerator {
    String generate(String paymentRef);
}
