package com.hotel.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record BookingRequest(
        @NotNull Long roomId,
        @NotBlank String guestName,
        @NotBlank @Email String guestEmail,
        String guestPhone,
        @NotNull LocalDate checkIn,
        @NotNull @Future LocalDate checkOut,
        @Min(1) int guests) {
}
