package com.ticketpro.movie_service.service.impl;

import com.ticketpro.movie_service.dto.MovieRequestDTO;
import com.ticketpro.movie_service.dto.MovieResponseDTO;
import com.ticketpro.movie_service.entity.Language;
import com.ticketpro.movie_service.entity.Movie;
import com.ticketpro.movie_service.repository.LanguageRepository;
import com.ticketpro.movie_service.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    private MovieRequestDTO requestDTO;
    private Language language;

    @BeforeEach
    void setUp() {
        language = new Language();
        language.setId(1L);
        language.setName("Hindi");

        requestDTO = new MovieRequestDTO();
        requestDTO.setTitle("Inception");
        requestDTO.setLanguageId(1L);
        requestDTO.setDurationMinutes(148);
        requestDTO.setGenre("Sci-Fi");
        requestDTO.setRating(8.8);
        requestDTO.setReleaseDate(LocalDate.of(2010, 7, 16));
    }

    @Test
    void shouldAddMovieSuccessfully() {
        // Arrange
        Movie savedMovie = Movie.builder()
                .id(1L)
                .title("Inception")
                .language(language)
                .durationMinutes(148)
                .genre("Sci-Fi")
                .rating(8.8)
                .releaseDate(LocalDate.of(2010, 7, 16))
                .build();

        when(languageRepository.findById(1L)).thenReturn(Optional.of(language));
        when(movieRepository.save(any(Movie.class))).thenReturn(savedMovie);

        MovieResponseDTO response = movieService.addMovie(requestDTO);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("Inception");
        assertThat(response.getLanguage()).isEqualTo("Hindi");
        assertThat(response.getRating()).isEqualTo(8.8);
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    void shouldThrowExceptionWhenLanguageNotFound() {
        // Arrange
        when(languageRepository.findById(1L)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> movieService.addMovie(requestDTO))
                .isInstanceOf(ResourceAccessException.class)
                .hasMessage("Language not found");

        verify(movieRepository, never()).save(any());
    }
}
