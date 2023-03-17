package com.travelapp.server.entity;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="tours")
@Getter
@Setter
@NoArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    private Long hotelId;

    @NotNull
    private Long roomId;

    @NotNull
    private Integer price;

    private String mainPhotoKey;

    private String summary;

    private String foodTypeId;

    @ManyToMany
    @JoinTable(name="users_tours",
        joinColumns = @JoinColumn(name="user_id", referencedColumnName="id"),
        inverseJoinColumns = @JoinColumn(name="id", referencedColumnName="user_id")
    )
    private List<User> users;

}
