package com.ticketpro.movie_service.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CinemaShowDTO {
    private Long cinemaId;
    private String cinemaName;
    private String address;
    private List<ShowDTO> shows;
}
