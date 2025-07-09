package com.ticketpro.movie_service.repository;

import com.ticketpro.movie_service.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {}
