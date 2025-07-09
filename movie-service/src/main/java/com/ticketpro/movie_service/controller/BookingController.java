package com.ticketpro.movie_service.controller;
import com.ticketpro.movie_service.dto.BookingRequestDTO;
import com.ticketpro.movie_service.dto.BookingResponseDTO;
import com.ticketpro.movie_service.dto.PaymentVerificationDTO;
import com.ticketpro.movie_service.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/initiate")
    public ResponseEntity<BookingResponseDTO> initiateBooking(
            @RequestBody BookingRequestDTO request) {
        return ResponseEntity.ok(bookingService.initiateBooking(request));
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyPayment(
            @RequestBody PaymentVerificationDTO request) {
        bookingService.verifyPayment(request);
        return ResponseEntity.ok("Booking confirmed successfully.");
    }
}
