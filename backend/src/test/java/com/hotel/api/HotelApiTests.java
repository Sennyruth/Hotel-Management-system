package com.hotel.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.hotel.api.dto.BookingRequest;
import com.hotel.api.dto.RoomAvailabilityResponse;
import com.hotel.api.model.Booking;
import com.hotel.api.service.BookingException;
import com.hotel.api.service.HotelService;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HotelApiTests {

    @Autowired
    private HotelService hotelService;

    @Test
    void seedsRoomsAndPricesStayForTheWholeStay() {
        LocalDate checkIn = LocalDate.now().plusDays(10);
        LocalDate checkOut = checkIn.plusDays(3);

        List<RoomAvailabilityResponse> rooms = hotelService.searchRooms(checkIn, checkOut, 2);

        assertThat(rooms).isNotEmpty();
        RoomAvailabilityResponse first = rooms.getFirst();
        assertThat(first.nights()).isEqualTo(3);
        assertThat(first.totalPrice()).isEqualByComparingTo(first.pricePerNight().multiply(java.math.BigDecimal.valueOf(3)));
    }

    @Test
    void bookingReducesAvailabilityForOverlappingDates() {
        LocalDate checkIn = LocalDate.now().plusDays(20);
        LocalDate checkOut = checkIn.plusDays(2);
        RoomAvailabilityResponse room = hotelService.searchRooms(checkIn, checkOut, 4).getFirst();
        int before = room.unitsAvailable();

        Booking booking = hotelService.book(new BookingRequest(room.id(), "Ada Lovelace", "ada@example.com", null,
                checkIn, checkOut, 2));

        assertThat(booking.getReference()).startsWith("HTL-");
        RoomAvailabilityResponse after = hotelService.searchRooms(checkIn, checkOut, 4).stream()
                .filter(candidate -> candidate.id().equals(room.id()))
                .findFirst()
                .orElseThrow();
        assertThat(after.unitsAvailable()).isEqualTo(before - 1);
    }

    @Test
    void rejectsCheckOutBeforeCheckIn() {
        LocalDate checkIn = LocalDate.now().plusDays(5);

        assertThatThrownBy(() -> hotelService.searchRooms(checkIn, checkIn, 1))
                .isInstanceOf(BookingException.class);
    }
}
