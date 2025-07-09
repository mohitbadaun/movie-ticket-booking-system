package com.ticketpro.movie_service.service.impl.booking.payment;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class PaymentProviderFactory {

    private final Map<String, PaymentProvider> providers;

    public PaymentProvider getProvider(PaymentProviderType type) {
        PaymentProvider provider = providers.get(type.name());
        if (provider == null) {
            throw new IllegalArgumentException("No payment provider found for type: " + type);
        }
        return provider;
    }
}
