package com.hotel.api.repository;

import com.hotel.api.model.Booking;
import com.hotel.api.model.BookingStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByReference(String reference);

    List<Booking> findByGuestEmailIgnoreCaseOrderByCheckInDesc(String guestEmail);

    List<Booking> findAllByOrderByCreatedAtDesc();

    @Query("select count(b) from Booking b where b.room.id = :roomId and b.status = :status "
            + "and b.checkIn < :checkOut and b.checkOut > :checkIn")
    long countOverlapping(@Param("roomId") Long roomId, @Param("checkIn") LocalDate checkIn,
            @Param("checkOut") LocalDate checkOut, @Param("status") BookingStatus status);
}
