package com.travelapp.server.service;

import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.dto.TourResponseDto;
import com.travelapp.server.entity.Tour;
import org.springframework.stereotype.Repository;


public interface TourService {

    TourResponseDto saveTour(TourRequestDto dto);
}
