package com.travelapp.server.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryParamsDto {

    private LocalDate searchStartDate;

    private String departureCity;
}
