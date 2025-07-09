package com.ticketpro.movie_service.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BookingResponseDTO {
    private Long bookingId;
    private BigDecimal totalAmount;
    private String paymentLink;
}