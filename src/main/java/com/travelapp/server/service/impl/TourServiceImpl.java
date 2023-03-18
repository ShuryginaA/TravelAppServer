package com.travelapp.server.service.impl;

import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.dto.TourCreateResponseDto;
import com.travelapp.server.dto.TourResponseDto;
import com.travelapp.server.entity.Hotel;
import com.travelapp.server.entity.Room;
import com.travelapp.server.mapper.TourMapper;
import com.travelapp.server.repository.HotelRepository;
import com.travelapp.server.repository.RoomRepository;
import com.travelapp.server.repository.TourRepository;
import com.travelapp.server.service.TourService;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class TourServiceImpl implements TourService {

    private final TourMapper tourMapper;
    private final TourRepository tourRepository;
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Override
    public TourCreateResponseDto saveTour(TourRequestDto dto) {
        Optional<Hotel> hotel = hotelRepository.findById(dto.getHotelId());
        if(hotel.isEmpty()) {
            throw new NotFoundException("Hotel not found");
        }
        Optional<Room> room = roomRepository.findById(dto.getRoomId());
        if( room.isEmpty()) {
            throw new NotFoundException("Room not found");
        }
        return new TourCreateResponseDto(tourRepository.save(tourMapper.toEntity(dto)).getId());
    }

    @Override
    public List<TourResponseDto> findAll() {
        return tourRepository.findAll().stream().map(tour -> tourMapper.);
    }
}
