package com.ticketpro.movie_service.service.impl;

import com.ticketpro.movie_service.dto.MovieRequestDTO;
import com.ticketpro.movie_service.dto.MovieResponseDTO;
import com.ticketpro.movie_service.entity.Language;
import com.ticketpro.movie_service.entity.Movie;
import com.ticketpro.movie_service.repository.LanguageRepository;
import com.ticketpro.movie_service.repository.MovieRepository;
import com.ticketpro.movie_service.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final LanguageRepository languageRepository;

    @Override
    public MovieResponseDTO addMovie(MovieRequestDTO dto) {
        Language lang = languageRepository.findById(dto.getLanguageId())
                .orElseThrow(() -> new ResourceAccessException("Language not found"));

        Movie movie = Movie.builder()
                .title(dto.getTitle())
                .language(lang)
                .durationMinutes(dto.getDurationMinutes())
                .genre(dto.getGenre())
                .rating(dto.getRating())
                .releaseDate(dto.getReleaseDate())
                .build();

        Movie saved = movieRepository.save(movie);
        return toDto(saved);
    }

    @Override
    public List<MovieResponseDTO> getMoviesByTitle(String title) {
        return movieRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieResponseDTO> getMoviesByCityAndDateRange(Long cityId, LocalDate startDate, LocalDate endDate) {
        return movieRepository.findMoviesByCityAndDateRange(cityId, startDate, endDate)
                .stream().map(this::toDto).collect(Collectors.toList());
    }

    @Override
    public List<MovieResponseDTO> getMoviesByCinema(Long cinemaId) {
        return movieRepository.findMoviesByCinemaId(cinemaId)
                .stream().map(this::toDto).collect(Collectors.toList());
    }


    private MovieResponseDTO toDto(Movie movie) {
        return MovieResponseDTO.builder()
                .id(movie.getId())
                .title(movie.getTitle())
                .language(movie.getLanguage().getName())
                .durationMinutes(movie.getDurationMinutes())
                .genre(movie.getGenre())
                .rating(movie.getRating())
                .releaseDate(movie.getReleaseDate())
                .build();
    }
}
