package com.hotel.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String reference;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(nullable = false)
    private String guestName;

    @Column(nullable = false)
    private String guestEmail;

    private String guestPhone;

    @Column(nullable = false)
    private LocalDate checkIn;

    @Column(nullable = false)
    private LocalDate checkOut;

    @Column(nullable = false)
    private int guests;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookingStatus status = BookingStatus.CONFIRMED;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    protected Booking() {
    }

    public Booking(String reference, Room room, String guestName, String guestEmail, String guestPhone,
            LocalDate checkIn, LocalDate checkOut, int guests, BigDecimal totalPrice) {
        this.reference = reference;
        this.room = room;
        this.guestName = guestName;
        this.guestEmail = guestEmail;
        this.guestPhone = guestPhone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.totalPrice = totalPrice;
    }

    public Long getId() {
        return id;
    }

    public String getReference() {
        return reference;
    }

    public Room getRoom() {
        return room;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getGuestEmail() {
        return guestEmail;
    }

    public String getGuestPhone() {
        return guestPhone;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public int getGuests() {
        return guests;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
    }
}
