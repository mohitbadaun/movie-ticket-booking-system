package com.ticketpro.movie_service.service;


import com.ticketpro.movie_service.dto.CinemaShowDTO;

import java.time.LocalDate;
import java.util.List;

public interface CinemaService {
    List<CinemaShowDTO> getCinemasWithShowsByMovieAndDate(Long movieId, LocalDate date);
    List<CinemaShowDTO> getCinemasWithShowsByMovieCityAndDate(Long movieId, Long cityId, LocalDate date);
}
