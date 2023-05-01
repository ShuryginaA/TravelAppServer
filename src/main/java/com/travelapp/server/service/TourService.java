package com.travelapp.server.service;

import com.travelapp.server.dto.BookTourDto;
import com.travelapp.server.dto.SearchQueryParamsDto;
import com.travelapp.server.dto.TourIdResponseDto;
import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.dto.TourCreateResponseDto;
import com.travelapp.server.dto.TourResponseData;
import java.io.IOException;
import java.util.List;


public interface TourService {

    TourCreateResponseDto saveTour(TourRequestDto dto);

    List<TourResponseData> findAll();

    List<TourResponseData> getPopularTours();

    TourResponseData getById(Long id);

    List<TourResponseData> getByParams(SearchQueryParamsDto paramsDto);

    byte[] getImageWithMediaType(String key) throws IOException;

    TourIdResponseDto bookTour(Long userId, BookTourDto tourDto);

}
