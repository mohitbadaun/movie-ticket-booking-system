package com.ticketpro.movie_service.service.impl;

import com.ticketpro.movie_service.dto.BookingRequestDTO;
import com.ticketpro.movie_service.dto.BookingResponseDTO;
import com.ticketpro.movie_service.dto.PaymentVerificationDTO;
import com.ticketpro.movie_service.entity.*;
import com.ticketpro.movie_service.repository.*;
import com.ticketpro.movie_service.service.BookingService;
import com.ticketpro.movie_service.service.support.BookingCalculator;
import com.ticketpro.movie_service.service.support.PaymentLinkGenerator;
import com.ticketpro.movie_service.service.support.SeatLockManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@Service
@RequiredArgsConstructor
@Transactional
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepo;
    private final ShowSeatRepository showSeatRepo;
    private final PaymentRepository paymentRepo;
    private final BookingSeatRepository bookingSeatRepo;
    private final UserRepository userRepo;
    private final ShowRepository showRepo;

    private final SeatLockManager seatLockManager;
    private final BookingCalculator bookingCalculator;
    private final PaymentLinkGenerator paymentLinkGenerator;

//    @Override
//    public BookingResponseDTO initiateBooking(BookingRequestDTO request) {
//        User user = userRepo.findById(request.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//
//        Show show = showRepo.findById(request.getShowId())
//                .orElseThrow(() -> new RuntimeException("Show not found"));
//
//        List<ShowSeat> seats = showSeatRepo.findAllById(request.getSeatIds());
//
//        // Lock seats
//        seatLockManager.lockSeats(request.getSeatIds(), request.getUserId());
//
//        // Calculate total
//        BigDecimal total = bookingCalculator.calculateTotal(seats);
//
//        // Create booking
//        Booking booking = bookingRepo.save(Booking.builder()
//                .user(user)
//                .show(show)
//                .status("PENDING")
//                .build());
//
//        // Save booking seats
//        seats.forEach(seat -> bookingSeatRepo.save(BookingSeat.builder()
//                .booking(booking)
//                .showSeat(seat)
//                .build()));
//
//        // Generate payment link
//        String paymentRef = UUID.randomUUID().toString();
//        String link = paymentLinkGenerator.generate(paymentRef);
//
//        // Save payment
//        paymentRepo.save(Payment.builder()
//                .booking(booking)
//                .paymentLink(paymentRef)
//                .paymentStatus("INITIATED")
//                .build());
//
//        return BookingResponseDTO.builder()
//                .bookingId(booking.getId())
//                .totalAmount(total)
//                .paymentLink(link)
//                .build();
//    }


    @Override
    @Transactional
    public BookingResponseDTO initiateBooking(BookingRequestDTO request) {

        // 1. Validate user and show
        User user = userRepo.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Show show = showRepo.findById(request.getShowId())
                .orElseThrow(() -> new RuntimeException("Show not found"));

        // 2. Fetch show_seats
        List<ShowSeat> seats = showSeatRepo.findAllById(request.getSeatIds());

        // 3. Validate if any seat is already booked
        List<Long> alreadyBookedSeatIds = seats.stream()
                .filter(ShowSeat::getIsBooked)
                .map(ShowSeat::getId)
                .toList();

        if (!alreadyBookedSeatIds.isEmpty()) {
            throw new RuntimeException("Seats already booked: " + alreadyBookedSeatIds);
        }

        // 4. Lock seats in Redis to avoid parallel bookings
        seatLockManager.lockSeats(request.getSeatIds(), request.getUserId());

        // 5. Calculate total price
        BigDecimal total = bookingCalculator.calculateTotal(seats);

        // 6. Save booking
        Booking booking = bookingRepo.save(Booking.builder()
                .user(user)
                .show(show)
                .status("PENDING")
                .build());

        // 7. Save booking_seats
        seats.forEach(seat -> bookingSeatRepo.save(BookingSeat.builder()
                .booking(booking)
                .showSeat(seat)
                .build()));

        // 8. Create and save payment info
        String paymentRef = UUID.randomUUID().toString();
        String paymentLink = paymentLinkGenerator.generate(paymentRef);

        paymentRepo.save(Payment.builder()
                .booking(booking)
                .paymentLink(paymentRef)
                .paymentStatus("INITIATED")
                .build());

        // 9. Return response
        return BookingResponseDTO.builder()
                .bookingId(booking.getId())
                .totalAmount(total)
                .paymentLink(paymentLink)
                .build();
    }



    @Override
    public void verifyPayment(PaymentVerificationDTO dto) {
        Payment payment = paymentRepo.findByBookingId(dto.getBookingId())
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentTime(LocalDateTime.now());
        payment.setPaymentLink(dto.getPaymentRef());
        paymentRepo.save(payment);

        Booking booking = payment.getBooking();
        booking.setStatus("CONFIRMED");
        bookingRepo.save(booking);

        List<BookingSeat> bookingSeats = bookingSeatRepo.findByBookingId(booking.getId());
        bookingSeats.forEach(bs -> {
            ShowSeat seat = bs.getShowSeat();
            seat.setIsBooked(true);
            showSeatRepo.save(seat);
            seatLockManager.releaseLock(seat.getId());
        });
    }
}
