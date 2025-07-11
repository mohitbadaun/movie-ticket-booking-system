package com.ticketpro.movie_service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class ShowDTO {
    private Long showId;
    private Long screenId;
    private String screenName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<SeatAvailabilityDTO> seats;
}
