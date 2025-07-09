package com.ticketpro.movie_service.service.mapper;


import com.ticketpro.movie_service.dto.CinemaShowDTO;
import com.ticketpro.movie_service.dto.SeatAvailabilityDTO;
import com.ticketpro.movie_service.dto.ShowDTO;
import com.ticketpro.movie_service.entity.Cinema;
import com.ticketpro.movie_service.entity.Seat;
import com.ticketpro.movie_service.entity.Show;
import com.ticketpro.movie_service.entity.ShowSeat;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CinemaShowMapper {

    public List<CinemaShowDTO> mapByCinemaGroupedShows(Map<Cinema, List<Show>> groupedShows) {
        return groupedShows.entrySet().stream()
                .map(entry -> mapCinemaWithShows(entry.getKey(), entry.getValue()))
                .toList();
    }

    public CinemaShowDTO mapCinemaWithShows(Cinema cinema, List<Show> shows) {
        List<ShowDTO> showDTOs = shows.stream()
                .map(this::mapShow)
                .toList();

        return CinemaShowDTO.builder()
                .cinemaId(cinema.getId())
                .cinemaName(cinema.getName())
                .address(cinema.getAddress())
                .shows(showDTOs)
                .build();
    }

    public ShowDTO mapShow(Show show) {
        List<SeatAvailabilityDTO> seatDTOs = show.getShowSeats().stream()
                .map(this::mapSeatAvailability)
                .toList();

        return ShowDTO.builder()
                .showId(show.getId())
                .screenId(show.getScreen().getId())
                .screenName(show.getScreen().getName())
                .startTime(show.getStartTime())
                .endTime(show.getEndTime())
                .seats(seatDTOs)
                .build();
    }

    public SeatAvailabilityDTO mapSeatAvailability(ShowSeat showSeat) {
        Seat seat = showSeat.getSeat();
        return SeatAvailabilityDTO.builder()
                .seatId(showSeat.getId()) // ✅ Use show_seat ID
                .seatNumber(seat.getSeatNumber())
                .seatType(seat.getSeatType())
                .price(showSeat.getFinalPrice())
                .isBooked(showSeat.getIsBooked())
                .build();
    }

}

