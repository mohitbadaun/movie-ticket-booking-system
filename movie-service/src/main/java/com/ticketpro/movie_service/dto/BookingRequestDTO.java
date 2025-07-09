package com.ticketpro.movie_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class BookingRequestDTO {
    private Long userId;
    private Long showId;
    private List<Long> seatIds;
}
