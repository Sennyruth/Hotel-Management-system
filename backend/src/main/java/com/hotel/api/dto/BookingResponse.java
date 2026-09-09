package com.hotel.api.dto;

import com.hotel.api.model.Booking;
import java.math.BigDecimal;
import java.time.LocalDate;

public record BookingResponse(Long id, String reference, Long roomId, String roomName, String guestName,
        String guestEmail, String guestPhone, LocalDate checkIn, LocalDate checkOut, int guests, BigDecimal totalPrice,
        String status) {

    public static BookingResponse of(Booking booking) {
        return new BookingResponse(booking.getId(), booking.getReference(), booking.getRoom().getId(),
                booking.getRoom().getName(), booking.getGuestName(), booking.getGuestEmail(), booking.getGuestPhone(),
                booking.getCheckIn(), booking.getCheckOut(), booking.getGuests(), booking.getTotalPrice(),
                booking.getStatus().name());
    }
}
