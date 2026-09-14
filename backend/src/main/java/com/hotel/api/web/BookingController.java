package com.hotel.api.web;

import com.hotel.api.dto.BookingRequest;
import com.hotel.api.dto.BookingResponse;
import com.hotel.api.service.HotelService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final HotelService hotelService;

    public BookingController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@Valid @RequestBody BookingRequest request) {
        return BookingResponse.of(hotelService.book(request));
    }

    @GetMapping
    public List<BookingResponse> list(@RequestParam(required = false) String email) {
        return hotelService.findAll(email).stream().map(BookingResponse::of).toList();
    }

    @GetMapping("/{reference}")
    public BookingResponse get(@PathVariable String reference) {
        return BookingResponse.of(hotelService.findByReference(reference));
    }

    @PostMapping("/{reference}/cancel")
    public BookingResponse cancel(@PathVariable String reference) {
        return BookingResponse.of(hotelService.cancel(reference));
    }
}
