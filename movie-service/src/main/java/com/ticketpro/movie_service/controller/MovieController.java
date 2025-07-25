package com.ticketpro.movie_service.controller;

import com.ticketpro.movie_service.dto.MovieRequestDTO;
import com.ticketpro.movie_service.dto.MovieResponseDTO;
import com.ticketpro.movie_service.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @PostMapping("/add")
    public ResponseEntity<MovieResponseDTO> addMovie(@Valid @RequestBody MovieRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(movieService.addMovie(dto));
    }

    @GetMapping("/by-city")
    public ResponseEntity<List<MovieResponseDTO>> getMoviesByCityAndDateRange(
            @RequestParam Long cityId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {

        if (endDate == null) {
            endDate = startDate;
        }

        return ResponseEntity.ok(movieService.getMoviesByCityAndDateRange(cityId, startDate, endDate));
    }

    @GetMapping("/by-cinema/{cinemaId}")
    public ResponseEntity<List<MovieResponseDTO>> getMoviesByCinema(@PathVariable Long cinemaId) {
        return ResponseEntity.ok(movieService.getMoviesByCinema(cinemaId));
    }
}

