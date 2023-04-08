package com.travelapp.server.controller;

import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.dto.TourCreateResponseDto;
import com.travelapp.server.dto.TourResponseData;
import com.travelapp.server.dto.TourResponseDto;
import com.travelapp.server.service.TourService;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tour")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    private final Path root = Paths.get("photos");

    @PostMapping
    @Produces(value = MediaType.APPLICATION_JSON)
    public TourCreateResponseDto create(@RequestBody TourRequestDto dto) {
        return tourService.saveTour(dto);
    }

    @GetMapping
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<TourResponseData> findAll() {
        return tourService.findAll();
    }

    @GetMapping("/popular")
    @Produces(value = MediaType.APPLICATION_JSON)
    public TourResponseDto findAllPopular() {
        return new TourResponseDto(tourService.getPopularTours());
    }

    @GetMapping("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public TourResponseData getById(@PathVariable Long id) {
        return tourService.getById(id);
    }

    @GetMapping(
        value = "/image/{key}",
        produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE
    )
    public @ResponseBody
    byte[] getImageWithMediaType(@PathVariable String key) throws IOException {
        InputStream in = getClass()
            .getResourceAsStream("/photos/" + key);
        if(in==null){
            InputStream inDef = getClass()
                .getResourceAsStream("/photos/nn.jpg");
            return org.apache.commons.io.IOUtils.toByteArray(inDef);
        }
        return org.apache.commons.io.IOUtils.toByteArray(in);
    }
}
