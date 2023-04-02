package com.travelapp.server.dto;

import com.travelapp.server.entity.Tour;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourResponseData {

    private Long id;

    private String name;

    private String country;

    private String departureCity;

    private String arrivalCity;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer price;

    private String mainPhotoKey;

    private String summary;

    private Tour.FoodService foodService;

    private HotelResponseDto hotel;

    private RoomResponseDto room;
}