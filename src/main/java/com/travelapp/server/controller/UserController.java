package com.travelapp.server.controller;

import com.travelapp.server.dto.UserUpdateRequestDto;
import com.travelapp.server.dto.UserRequestDto;
import com.travelapp.server.dto.UserDataResponseDto;
import com.travelapp.server.dto.UserUpdateResposeDto;
import com.travelapp.server.service.UserService;
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
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public UserDataResponseDto getUserInfo(@PathVariable Long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    public UserUpdateResposeDto updateUser(@PathVariable Long id, UserUpdateRequestDto userRequestDto) {
        return userService.updateUser(id,userRequestDto);
    }

    @GetMapping(value = "/photo")
    @Produces(value = MediaType.APPLICATION_OCTET_STREAM)
    public Response getUserPhoto(@RequestBody UserRequestDto dto) {
        return userService.findUserPhotoByKey(dto);
    }
}
