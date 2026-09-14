package com.hotel.api.config;

import com.hotel.api.model.Room;
import com.hotel.api.repository.RoomRepository;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final RoomRepository roomRepository;

    public DataSeeder(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void run(String... args) {
        if (roomRepository.count() > 0) {
            return;
        }
        roomRepository.saveAll(List.of(
                new Room("Garden Single", "Single", "A calm single room opening onto the herb garden, ideal for solo travellers.",
                        new BigDecimal("89.00"), 1, 6,
                        "https://images.unsplash.com/photo-1505693416388-ac5ce068fe85?auto=format&fit=crop&w=1200&q=60",
                        "Free Wi-Fi, Garden view, Work desk, Rain shower"),
                new Room("Classic Double", "Double", "Warm double room with a king bed, blackout curtains and a marble bathroom.",
                        new BigDecimal("139.00"), 2, 10,
                        "https://images.unsplash.com/photo-1611892440504-42a792e24d32?auto=format&fit=crop&w=1200&q=60",
                        "Free Wi-Fi, King bed, Nespresso machine, Smart TV"),
                new Room("Deluxe Twin", "Twin", "Two plush queen beds and a lounge nook overlooking the courtyard pool.",
                        new BigDecimal("169.00"), 3, 8,
                        "https://images.unsplash.com/photo-1590490360182-c33d57733427?auto=format&fit=crop&w=1200&q=60",
                        "Free Wi-Fi, Pool view, Lounge area, Mini bar"),
                new Room("Family Suite", "Suite", "A two-bedroom suite with a kitchenette and a private terrace for the whole family.",
                        new BigDecimal("249.00"), 5, 4,
                        "https://images.unsplash.com/photo-1582719478250-c89cae4dc85b?auto=format&fit=crop&w=1200&q=60",
                        "Free Wi-Fi, Kitchenette, Terrace, Two bathrooms"),
                new Room("Panorama Penthouse", "Penthouse", "Top-floor penthouse with floor-to-ceiling windows, a soaking tub and skyline views.",
                        new BigDecimal("420.00"), 4, 2,
                        "https://images.unsplash.com/photo-1618773928121-c32242e63f39?auto=format&fit=crop&w=1200&q=60",
                        "Free Wi-Fi, Skyline view, Soaking tub, Butler service")));
    }
}
