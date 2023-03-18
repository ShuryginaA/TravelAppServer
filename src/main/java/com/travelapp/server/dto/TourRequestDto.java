package com.travelapp.server.dto;

import com.travelapp.server.entity.Tour;
import java.time.LocalDate;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TourRequestDto {

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
    private Tour.FoodService foodService;

    private Long hotelId;

    private Long roomId;

}
