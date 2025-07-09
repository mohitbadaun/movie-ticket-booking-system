package com.ticketpro.movie_service.repository;

import com.ticketpro.movie_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
