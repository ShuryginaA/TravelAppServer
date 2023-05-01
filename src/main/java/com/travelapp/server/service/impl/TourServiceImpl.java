package com.travelapp.server.service.impl;

import com.travelapp.server.dto.BookTourDto;
import com.travelapp.server.dto.SearchQueryParamsDto;
import com.travelapp.server.dto.TourCreateResponseDto;
import com.travelapp.server.dto.TourIdResponseDto;
import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.dto.TourResponseData;
import com.travelapp.server.entity.Hotel;
import com.travelapp.server.entity.Room;
import com.travelapp.server.entity.Tour;
import com.travelapp.server.entity.User;
import com.travelapp.server.mapper.TourMapper;
import com.travelapp.server.repository.HotelRepository;
import com.travelapp.server.repository.RoomRepository;
import com.travelapp.server.repository.TourRepository;
import com.travelapp.server.repository.UserRepository;
import com.travelapp.server.service.TourService;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourMapper tourMapper;

    private final TourRepository tourRepository;

    private final HotelRepository hotelRepository;

    private final RoomRepository roomRepository;

    private final UserRepository userRepository;

    @Override
    public TourCreateResponseDto saveTour(TourRequestDto dto) {
        if (dto.getHotelId() != null) {
            Optional<Hotel> hotel = hotelRepository.findById(dto.getHotelId());
            if (hotel.isEmpty()) {
                throw new NotFoundException("Hotel not found");
            }
        }

        if (dto.getRoomId() != null) {
            Optional<Room> room = roomRepository.findById(dto.getRoomId());
            if (room.isEmpty()) {
                throw new NotFoundException("Room not found");
            }
        }
        return new TourCreateResponseDto(tourRepository.save(tourMapper.toEntity(dto)).getId());
    }

    @Override
    public List<TourResponseData> findAll() {
        return tourRepository.findAll().stream().map(tourMapper::toResponseDto).toList();
    }

    @Override
    public List<TourResponseData> getPopularTours() {
        return tourRepository.findAll().stream()
            .filter(tour -> tour.getPopularNow() && !tour.getIsBooked())
            .map(tourMapper::toResponseDto)
            .toList();
    }

    @Override
    public TourResponseData getById(Long id) {
        Optional<Tour> tour =tourRepository.findById(id);
        if(tour.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return tourMapper.toResponseDto(tour.get());

    }

    @Override
    public List<TourResponseData> getByParams(SearchQueryParamsDto paramsDto) {
        return tourRepository.findAllByDepartureCity(paramsDto.getDepartureCity())
            .stream().filter(t -> isTourInDateRange(paramsDto, t))
            .map(tourMapper::toResponseDto).toList();
    }

    private boolean isTourInDateRange(SearchQueryParamsDto paramsDto, Tour tour) {
        return paramsDto.getSearchEndDate() != null
            ? (tour.getStartDate().equals(paramsDto.getSearchStartDate())
                || tour.getStartDate().isAfter(paramsDto.getSearchStartDate()))
                && tour.getStartDate().isBefore(paramsDto.getSearchEndDate())
            : tour.getStartDate().equals(paramsDto.getSearchStartDate())
            || tour.getStartDate().isAfter(paramsDto.getSearchStartDate());
    }

    public @ResponseBody byte[] getImageWithMediaType(String key) throws IOException {
        InputStream in = getClass().getResourceAsStream("/photos/" + key);
        if(in==null){
            InputStream inDef = getClass()
                .getResourceAsStream("/photos/nn.jpg");
            return org.apache.commons.io.IOUtils.toByteArray(inDef);
        }
        return org.apache.commons.io.IOUtils.toByteArray(in);
    }

    @Override
    @Transactional
    public TourIdResponseDto bookTour(Long userId, BookTourDto tourDto) {
        Tour tour = tourRepository.findById(tourDto.getTourId()).orElseThrow(NotFoundException::new);
        if(Boolean.TRUE.equals(tour.getIsBooked())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Тур же был забронирован");
        }
        tour.setIsBooked(true);
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        user.getTours().add(tour);
        tour.getUsers().add(user);
        userRepository.save(user);
        tourRepository.save(tour);

        return new TourIdResponseDto(tour.getId());
    }
}
