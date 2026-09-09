package com.hotel.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private BigDecimal pricePerNight;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int totalUnits;

    private String imageUrl;

    @Column(length = 1000)
    private String amenities;

    protected Room() {
    }

    public Room(String name, String type, String description, BigDecimal pricePerNight, int capacity, int totalUnits,
            String imageUrl, String amenities) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.capacity = capacity;
        this.totalUnits = totalUnits;
        this.imageUrl = imageUrl;
        this.amenities = amenities;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPricePerNight() {
        return pricePerNight;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getTotalUnits() {
        return totalUnits;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getAmenities() {
        return amenities;
    }
}
