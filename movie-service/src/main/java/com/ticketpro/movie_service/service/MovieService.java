package com.ticketpro.movie_service.service;

import com.ticketpro.movie_service.dto.MovieRequestDTO;
import com.ticketpro.movie_service.dto.MovieResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface MovieService {
    MovieResponseDTO addMovie(MovieRequestDTO dto);

    //MovieResponseDTO addMovie(MovieRequestDTO dto);
    List<MovieResponseDTO> getMoviesByTitle(String title);

    List<MovieResponseDTO> getMoviesByCityAndDateRange(Long cityId, LocalDate startDate, LocalDate endDate);
    List<MovieResponseDTO> getMoviesByCinema(Long cinemaId);

}

