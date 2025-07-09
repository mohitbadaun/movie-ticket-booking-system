package com.ticketpro.movie_service.repository;

import com.ticketpro.movie_service.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
