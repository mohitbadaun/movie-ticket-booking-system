package com.ticketpro.movie_service.repository;

import com.ticketpro.movie_service.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;


public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);


    @Query("SELECT DISTINCT s.movie FROM Show s " +
            "WHERE s.screen.cinema.city.id = :cityId " +
            "AND DATE(s.startTime) BETWEEN :startDate AND :endDate")
    List<Movie> findMoviesByCityAndDateRange(@Param("cityId") Long cityId,
                                             @Param("startDate") LocalDate startDate,
                                             @Param("endDate") LocalDate endDate);

    @Query("SELECT DISTINCT s.movie FROM Show s " +
            "WHERE s.screen.cinema.id = :cinemaId")
    List<Movie> findMoviesByCinemaId(@Param("cinemaId") Long cinemaId);
}
