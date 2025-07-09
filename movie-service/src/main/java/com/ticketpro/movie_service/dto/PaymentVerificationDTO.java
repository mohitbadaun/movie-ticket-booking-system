package com.ticketpro.movie_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PaymentVerificationDTO {
    private Long bookingId;
    private String paymentRef; // mocked reference
}