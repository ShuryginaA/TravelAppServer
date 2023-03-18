package com.travelapp.server.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="hotels")
@Getter
@Setter
@NoArgsConstructor
public class Hotel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="hotel_id")
    private Long id;

    private String name;

    private String country;

    private String city;

    private String address;

    private int foundationYear;

    private String summary;

    private int stars;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Tour> tours;

    @ManyToMany
    @JoinTable(name="hotel_room",
        joinColumns = @JoinColumn(name="room_id", referencedColumnName="hotel_id"),
        inverseJoinColumns = @JoinColumn(name="hotel_id", referencedColumnName="room_id")
    )
    private List<Room> rooms;

}
