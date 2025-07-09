package com.ticketpro.movie_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SeatAvailabilityDTO {
    private Long seatId;
    private String seatNumber;
    private String seatType;
    private Double price;
    private Boolean isBooked;
}
