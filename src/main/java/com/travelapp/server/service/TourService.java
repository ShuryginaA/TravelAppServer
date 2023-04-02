package com.travelapp.server.service;

import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.dto.TourCreateResponseDto;
import com.travelapp.server.dto.TourResponseData;
import java.util.List;


public interface TourService {

    TourCreateResponseDto saveTour(TourRequestDto dto);

    List<TourResponseData> findAll();

    List<TourResponseData> getPopularTours();
}
