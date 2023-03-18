package com.travelapp.server.controller;

import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.dto.TourCreateResponseDto;
import com.travelapp.server.dto.TourResponseDto;
import com.travelapp.server.service.TourService;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
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
    public TourCreateResponseDto create(@RequestBody TourRequestDto dto) {
        return tourService.saveTour(dto);
    }

    @PostMapping
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<TourResponseDto> findAll() {
        return tourService.findAll();
    }
}
