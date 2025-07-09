package com.ticketpro.movie_service.service.impl;

import com.ticketpro.movie_service.dto.CinemaShowDTO;
import com.ticketpro.movie_service.dto.SeatAvailabilityDTO;
import com.ticketpro.movie_service.dto.ShowDTO;
import com.ticketpro.movie_service.entity.Cinema;
import com.ticketpro.movie_service.entity.Seat;
import com.ticketpro.movie_service.entity.Show;
import com.ticketpro.movie_service.repository.ShowRepository;
import com.ticketpro.movie_service.service.CinemaService;
import com.ticketpro.movie_service.service.mapper.CinemaShowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final ShowRepository showRepository;
    private final CinemaShowMapper cinemaShowMapper;


    @Override
    public List<CinemaShowDTO> getCinemasWithShowsByMovieAndDate(Long movieId, LocalDate date) {
        List<Show> shows = showRepository.findByMovieIdAndDate(movieId, date);

        Map<Cinema, List<Show>> groupedByCinema = shows.stream()
                .collect(Collectors.groupingBy(show -> show.getScreen().getCinema()));

        return groupedByCinema.entrySet().stream().map(entry -> {
            Cinema cinema = entry.getKey();
            List<ShowDTO> showDTOs = entry.getValue().stream().map(show -> ShowDTO.builder()
                    .showId(show.getId())
                    .screenId(show.getScreen().getId())
                    .screenName(show.getScreen().getName())
                    .startTime(show.getStartTime())
                    .endTime(show.getEndTime())
                    .build()).toList();

            return CinemaShowDTO.builder()
                    .cinemaId(cinema.getId())
                    .cinemaName(cinema.getName())
                    .address(cinema.getAddress())
                    .shows(showDTOs)
                    .build();
        }).toList();
    }


    @Override
    public List<CinemaShowDTO> getCinemasWithShowsByMovieCityAndDate(Long movieId, Long cityId, LocalDate date) {
        List<Show> shows = showRepository.findByMovieIdCityAndDate(movieId, cityId, date);

        Map<Cinema, List<Show>> groupedByCinema = shows.stream()
                .collect(Collectors.groupingBy(show -> show.getScreen().getCinema()));

        return cinemaShowMapper.mapByCinemaGroupedShows(groupedByCinema);
    }
}
