package com.hotel.api.service;

import com.hotel.api.dto.BookingRequest;
import com.hotel.api.dto.RoomAvailabilityResponse;
import com.hotel.api.model.Booking;
import com.hotel.api.model.BookingStatus;
import com.hotel.api.model.Room;
import com.hotel.api.repository.BookingRepository;
import com.hotel.api.repository.RoomRepository;
import java.math.BigDecimal;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HotelService {

    private static final String REFERENCE_ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";

    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;
    private final SecureRandom random = new SecureRandom();

    public HotelService(RoomRepository roomRepository, BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Transactional(readOnly = true)
    public List<RoomAvailabilityResponse> searchRooms(LocalDate checkIn, LocalDate checkOut, Integer guests) {
        int requiredCapacity = guests == null ? 1 : guests;
        List<Room> rooms = roomRepository.findByCapacityGreaterThanEqualOrderByPricePerNightAsc(requiredCapacity);
        if (checkIn == null || checkOut == null) {
            return rooms.stream().map(room -> RoomAvailabilityResponse.of(room, room.getTotalUnits(), null)).toList();
        }
        validateDates(checkIn, checkOut);
        int nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        return rooms.stream()
                .map(room -> RoomAvailabilityResponse.of(room, availableUnits(room, checkIn, checkOut), nights))
                .filter(response -> response.unitsAvailable() > 0)
                .toList();
    }

    @Transactional(readOnly = true)
    public RoomAvailabilityResponse getRoom(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Room " + id + " not found"));
        return RoomAvailabilityResponse.of(room, room.getTotalUnits(), null);
    }

    @Transactional
    public Booking book(BookingRequest request) {
        validateDates(request.checkIn(), request.checkOut());
        Room room = roomRepository.findById(request.roomId())
                .orElseThrow(() -> new NotFoundException("Room " + request.roomId() + " not found"));
        if (request.guests() > room.getCapacity()) {
            throw new BookingException(room.getName() + " sleeps a maximum of " + room.getCapacity() + " guests");
        }
        if (availableUnits(room, request.checkIn(), request.checkOut()) <= 0) {
            throw new BookingException(room.getName() + " is fully booked for the selected dates");
        }
        int nights = (int) ChronoUnit.DAYS.between(request.checkIn(), request.checkOut());
        BigDecimal totalPrice = room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
        Booking booking = new Booking(newReference(), room, request.guestName().trim(),
                request.guestEmail().trim().toLowerCase(), request.guestPhone(), request.checkIn(), request.checkOut(),
                request.guests(), totalPrice);
        return bookingRepository.save(booking);
    }

    @Transactional(readOnly = true)
    public Booking findByReference(String reference) {
        return bookingRepository.findByReference(reference.toUpperCase())
                .orElseThrow(() -> new NotFoundException("Booking " + reference + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Booking> findAll(String email) {
        if (email == null || email.isBlank()) {
            return bookingRepository.findAllByOrderByCreatedAtDesc();
        }
        return bookingRepository.findByGuestEmailIgnoreCaseOrderByCheckInDesc(email.trim());
    }

    @Transactional
    public Booking cancel(String reference) {
        Booking booking = findByReference(reference);
        booking.cancel();
        return bookingRepository.save(booking);
    }

    private int availableUnits(Room room, LocalDate checkIn, LocalDate checkOut) {
        long booked = bookingRepository.countOverlapping(room.getId(), checkIn, checkOut, BookingStatus.CONFIRMED);
        return (int) Math.max(0, room.getTotalUnits() - booked);
    }

    private void validateDates(LocalDate checkIn, LocalDate checkOut) {
        if (!checkOut.isAfter(checkIn)) {
            throw new BookingException("Check-out date must be after the check-in date");
        }
        if (checkIn.isBefore(LocalDate.now())) {
            throw new BookingException("Check-in date cannot be in the past");
        }
    }

    private String newReference() {
        StringBuilder builder = new StringBuilder("HTL-");
        for (int i = 0; i < 6; i++) {
            builder.append(REFERENCE_ALPHABET.charAt(random.nextInt(REFERENCE_ALPHABET.length())));
        }
        return builder.toString();
    }
}
