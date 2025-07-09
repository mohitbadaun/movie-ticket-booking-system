package com.ticketpro.movie_service.service.impl.booking.payment;

import com.ticketpro.movie_service.entity.Booking;
import com.ticketpro.movie_service.entity.Payment;
import com.ticketpro.movie_service.repository.PaymentRepository;
import com.ticketpro.movie_service.service.support.PaymentLinkGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentLinkService {

    private final PaymentProviderFactory factory;
    private final PaymentRepository paymentRepo;

    public String generate(Long bookingId) {
        String ref = UUID.randomUUID().toString();


        String referenceId = UUID.randomUUID().toString();
        PaymentProvider provider = factory.getProvider(getType(bookingId));

        String link = provider.generatePaymentLink(String.valueOf(bookingId), referenceId);

        paymentRepo.save(Payment.builder()
                .booking(Booking.builder().id(bookingId).build())
                .paymentLink(ref)
                .paymentStatus("INITIATED")
                .build());

        return link;


    }

    private PaymentProviderType getType(Long bookingId) {
        return PaymentProviderType.RAZORPAY;
    }
}

