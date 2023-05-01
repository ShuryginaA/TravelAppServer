package com.travelapp.server.controller;

import com.travelapp.server.dto.TourResponseData;
import com.travelapp.server.dto.TourResponseDto;
import com.travelapp.server.dto.UserUpdateRequestDto;
import com.travelapp.server.dto.UserRequestDto;
import com.travelapp.server.dto.UserDataResponseDto;
import com.travelapp.server.dto.UserUpdateResposeDto;
import com.travelapp.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Пользователи", description = "Операции для работы с данными пользователей")
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Operation(summary = "Получение информации о пользователе")
    @Produces(value = MediaType.APPLICATION_JSON)
    public UserDataResponseDto getUserInfo(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновление данных пользователя")
    public UserUpdateResposeDto updateUser(@PathVariable Long id, @RequestBody UserUpdateRequestDto userRequestDto) {
        return userService.updateUser(id,userRequestDto);
    }

    @GetMapping(value = "/photo")
    @Operation(summary = "Получение фото пользователя")
    @Produces(value = MediaType.APPLICATION_OCTET_STREAM)
    public Response getUserPhoto(@RequestBody UserRequestDto dto) {
        return userService.findUserPhotoByKey(dto);
    }

    @GetMapping("/{id}/tours")
    @Operation(summary = "Получение туров пользователя")
    public TourResponseDto getUserTours(@PathVariable Long id) {
        return new TourResponseDto(userService.getUserTours(id));
    }
}
