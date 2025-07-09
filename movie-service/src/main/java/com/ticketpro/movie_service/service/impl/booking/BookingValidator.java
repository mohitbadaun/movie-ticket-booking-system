package com.ticketpro.movie_service.service.impl.booking;

import com.ticketpro.movie_service.dto.BookingRequestDTO;
import com.ticketpro.movie_service.repository.ShowRepository;
import com.ticketpro.movie_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookingValidator {

    private final UserRepository userRepo;
    private final ShowRepository showRepo;

    public void validate(BookingRequestDTO request) {
        userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        showRepo.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));
    }
}
