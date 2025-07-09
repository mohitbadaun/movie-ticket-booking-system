package com.ticketpro.movie_service.dto;


import com.ticketpro.movie_service.entity.Movie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
public class MovieResponseDTO {
    private Long id;
    private String title;
    private String language;
    private Integer durationMinutes;
    private String genre;
    private Double rating;
    private LocalDate releaseDate;
}
