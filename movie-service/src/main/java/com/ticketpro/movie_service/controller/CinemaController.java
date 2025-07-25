package com.ticketpro.movie_service.controller;

import com.ticketpro.movie_service.dto.CinemaShowDTO;
import com.ticketpro.movie_service.service.CinemaService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/cinemas")
@RequiredArgsConstructor
public class CinemaController {

    private final CinemaService cinemaService;


    @GetMapping("/by-movie-city-date")
    public ResponseEntity<List<CinemaShowDTO>> getCinemasAndShowsByMovieCityAndDate(
            @RequestParam Long movieId,
            @RequestParam Long cityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(
                cinemaService.getCinemasWithShowsByMovieCityAndDate(movieId, cityId, date));
    }

}

