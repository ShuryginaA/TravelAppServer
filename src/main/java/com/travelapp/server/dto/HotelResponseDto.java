package com.travelapp.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotelResponseDto {

    private String name;

    private String country;

    private String city;

    private String address;

    private int foundationYear;

    private String summary;

    private int stars;
}
