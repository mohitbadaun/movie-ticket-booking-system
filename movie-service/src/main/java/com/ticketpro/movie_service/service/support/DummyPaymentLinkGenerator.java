package com.ticketpro.movie_service.service.support;

import org.springframework.stereotype.Component;

@Component
public class DummyPaymentLinkGenerator implements PaymentLinkGenerator {

    @Override
    public String generate(String paymentRef) {
        return "https://pay.mock/" + paymentRef;
    }
}
