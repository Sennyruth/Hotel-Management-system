package com.hotel.api.dto;

import com.hotel.api.model.Room;
import java.math.BigDecimal;
import java.util.List;

public record RoomAvailabilityResponse(Long id, String name, String type, String description, BigDecimal pricePerNight,
        int capacity, String imageUrl, List<String> amenities, int unitsAvailable, BigDecimal totalPrice, Integer nights) {

    public static RoomAvailabilityResponse of(Room room, int unitsAvailable, Integer nights) {
        List<String> amenities = room.getAmenities() == null || room.getAmenities().isBlank()
                ? List.of()
                : List.of(room.getAmenities().split("\\s*,\\s*"));
        BigDecimal totalPrice = nights == null ? null : room.getPricePerNight().multiply(BigDecimal.valueOf(nights));
        return new RoomAvailabilityResponse(room.getId(), room.getName(), room.getType(), room.getDescription(),
                room.getPricePerNight(), room.getCapacity(), room.getImageUrl(), amenities, unitsAvailable, totalPrice,
                nights);
    }
}
