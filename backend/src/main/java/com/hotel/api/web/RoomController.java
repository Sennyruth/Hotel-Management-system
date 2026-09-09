package com.hotel.api.web;

import com.hotel.api.dto.RoomAvailabilityResponse;
import com.hotel.api.service.HotelService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final HotelService hotelService;

    public RoomController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping
    public List<RoomAvailabilityResponse> search(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkIn,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOut,
            @RequestParam(required = false) Integer guests) {
        return hotelService.searchRooms(checkIn, checkOut, guests);
    }

    @GetMapping("/{id}")
    public RoomAvailabilityResponse get(@PathVariable Long id) {
        return hotelService.getRoom(id);
    }
}
