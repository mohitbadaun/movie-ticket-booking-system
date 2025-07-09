package com.ticketpro.movie_service.service.impl.booking;

import com.ticketpro.movie_service.dto.PaymentVerificationDTO;
import com.ticketpro.movie_service.entity.Booking;
import com.ticketpro.movie_service.entity.BookingSeat;
import com.ticketpro.movie_service.entity.Payment;
import com.ticketpro.movie_service.entity.ShowSeat;
import com.ticketpro.movie_service.repository.BookingRepository;
import com.ticketpro.movie_service.repository.BookingSeatRepository;
import com.ticketpro.movie_service.repository.PaymentRepository;
import com.ticketpro.movie_service.repository.ShowSeatRepository;
import com.ticketpro.movie_service.service.support.SeatLockManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentVerifier {

    private final PaymentRepository paymentRepo;
    private final BookingRepository bookingRepo;
    private final BookingSeatRepository bookingSeatRepo;
    private final ShowSeatRepository showSeatRepo;
    private final SeatLockManager seatLockManager;

    @Transactional
    public void verify(PaymentVerificationDTO dto) {
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
        for (BookingSeat bs : bookingSeats) {
            ShowSeat seat = bs.getShowSeat();
            seat.setIsBooked(true);
            showSeatRepo.save(seat);
            seatLockManager.releaseLock(seat.getId());
        }
    }
}
