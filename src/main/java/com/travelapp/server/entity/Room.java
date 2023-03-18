package com.travelapp.server.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="rooms")
@Getter
@Setter
@NoArgsConstructor
public class Room {

    @AllArgsConstructor
    public enum RoomType {
        STANDARD("STANDARD"),
        SINGLE("SINGLE"),
        FAMILY("FAMILY"),
        LUX("LUX");
        private String type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="room_id")
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    private int maxNumberOfAdults;

    private int maxNumberOfKids;

    private String additionalInfo;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "rooms")
    private List<Hotel> hotels;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Tour> tours;
}
