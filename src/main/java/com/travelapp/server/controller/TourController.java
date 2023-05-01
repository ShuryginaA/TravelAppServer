package com.travelapp.server.controller;

import com.travelapp.server.dto.BookTourDto;
import com.travelapp.server.dto.SearchQueryParamsDto;
import com.travelapp.server.dto.TourIdResponseDto;
import com.travelapp.server.dto.TourRequestDto;
import com.travelapp.server.dto.TourCreateResponseDto;
import com.travelapp.server.dto.TourResponseData;
import com.travelapp.server.dto.TourResponseDto;
import com.travelapp.server.service.TourService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Туры", description = "Операции для работы с данными туров")
@RequestMapping("/api/tour")
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    private final Path root = Paths.get("photos");

    @PostMapping
    @Operation(summary = "Добавление нового тура")
    @Produces(value = MediaType.APPLICATION_JSON)
    public TourCreateResponseDto create(@RequestBody TourRequestDto dto) {
        return tourService.saveTour(dto);
    }

    @GetMapping
    @Operation(summary = "Получение списка туров")
    @Produces(value = MediaType.APPLICATION_JSON)
    public List<TourResponseData> findAll() {
        return tourService.findAll();
    }

    @GetMapping("/popular")
    @Operation(summary = "Получение списка популярных туров")
    @Produces(value = MediaType.APPLICATION_JSON)
    public TourResponseDto findAllPopular() {
        return new TourResponseDto(tourService.getPopularTours());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение тура по идентификатору")
    @Produces(value = MediaType.APPLICATION_JSON)
    public TourResponseData getById(@PathVariable Long id) {
        return tourService.getById(id);
    }

    @GetMapping(value = "/image/{key}",
                produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
    @Operation(summary = "Получение изобржения по ключу")
    public @ResponseBody byte[] getImageWithMediaType(@PathVariable String key) throws IOException {
        return tourService.getImageWithMediaType(key);
    }

    @PostMapping(value = "/search",
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON)
    @Operation(summary = "Поиск")
    public TourResponseDto getToursBySearchParams(@RequestBody SearchQueryParamsDto dto) {
        return new TourResponseDto(tourService.getByParams(dto));
    }

    @PostMapping(value = "/{id}/book",
        consumes = MediaType.APPLICATION_JSON,
        produces = MediaType.APPLICATION_JSON)
    @Operation(summary = "Бронирование")
    public TourIdResponseDto bookTour(@PathVariable Long id, @RequestBody BookTourDto dto) {
         return tourService.bookTour(id, dto);
    }
}
