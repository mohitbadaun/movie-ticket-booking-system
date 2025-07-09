package com.ticketpro.movie_service.repository;


import com.ticketpro.movie_service.entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository extends JpaRepository<Show, Long> {

    @Query("SELECT s FROM Show s " +
            "JOIN FETCH s.screen sc " +
            "JOIN FETCH sc.cinema c " +
            "WHERE s.movie.id = :movieId " +
            "AND DATE(s.startTime) = :date")
    List<Show> findByMovieIdAndDate(Long movieId, LocalDate date);

    @Query("""
                SELECT s FROM Show s
                JOIN FETCH s.screen sc
                JOIN FETCH sc.cinema c
                WHERE s.movie.id = :movieId
                AND c.city.id = :cityId
                AND DATE(s.startTime) = :date
            """)
    List<Show> findByMovieIdCityAndDate(Long movieId, Long cityId, LocalDate date);

}
