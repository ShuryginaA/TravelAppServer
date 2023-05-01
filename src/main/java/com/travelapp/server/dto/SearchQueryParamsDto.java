package com.travelapp.server.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryParamsDto {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate searchStartDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate searchEndDate;

    private String departureCity;
}
