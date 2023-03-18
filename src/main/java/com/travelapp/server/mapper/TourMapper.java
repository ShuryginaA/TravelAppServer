package com.travelapp.server.mapper;

import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.entity.Tour;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TourMapper {

    Tour toEntity(TourRequestDto dto);

    Tour toResponseDto(Tour tour);
}
