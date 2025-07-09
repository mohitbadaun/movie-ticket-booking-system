package com.ticketpro.movie_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MovieRequestDTO {
    @NotBlank
    private String title;

    @NotNull
    private Long languageId;

    private Integer durationMinutes;
    private String genre;
    private Double rating;
    private LocalDate releaseDate;
}
