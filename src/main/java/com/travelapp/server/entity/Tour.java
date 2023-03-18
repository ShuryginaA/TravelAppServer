package com.travelapp.server.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tours")
@Getter
@Setter
@NoArgsConstructor
public class Tour {

    @AllArgsConstructor
    public enum FoodService {
        NO_SERVICE("NO_SERVICE"),
        ONLY_BREAKFAST("ONLY_BREAKFAST"),
        BREAKFAST_LUNCH("BREAKFAST_LUNCH"),
        ALL_INCLUSIVE("ALL_INCLUSIVE"),
        ULTRA_ALL_INCLUSIVE("ULTRA_ALL_INCLUSIVE");
        private String type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="tour_id")
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String country;

    @NotNull
    private String departureCity;

    @NotNull
    private String arrivalCity;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private Integer price;

    private String mainPhotoKey;

    private String summary;

    @Enumerated(EnumType.STRING)
    private FoodService foodService;

    @ManyToMany
    @JoinTable(name = "users_tours",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "tour_id"),
        inverseJoinColumns = @JoinColumn(name = "tour_id", referencedColumnName = "user_id")
    )
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="room_id")
    private Room room;

}
