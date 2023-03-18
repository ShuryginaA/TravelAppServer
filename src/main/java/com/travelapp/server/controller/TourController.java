package com.travelapp.server.controller;

import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.dto.TourResponseDto;
import com.travelapp.server.dto.UserDataResponseDto;
import com.travelapp.server.dto.UserRequestDto;
import com.travelapp.server.mapper.TourMapper;
import com.travelapp.server.service.TourService;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tour")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;


    @PostMapping
    @Produces(value = MediaType.APPLICATION_JSON)
    public TourResponseDto create(@RequestBody TourRequestDto dto) {
        return tourService.saveTour(dto);
    }
}
