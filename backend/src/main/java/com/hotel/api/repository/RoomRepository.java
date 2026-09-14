package com.hotel.api.repository;

import com.hotel.api.model.Room;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {

    List<Room> findByCapacityGreaterThanEqualOrderByPricePerNightAsc(int capacity);
}
