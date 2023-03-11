package com.travelapp.server.controller;

import com.travelapp.server.dto.UserRequestDto;
import com.travelapp.server.dto.UserDataResponseDto;
import com.travelapp.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public UserDataResponseDto getUserInfo(@RequestBody UserRequestDto dto) {
        return userService.findUserById(dto);
    }

    @GetMapping("/photo")
    public MultipartFile getUserPhoto(@RequestBody UserRequestDto dto) {
        return userService.findUserPhotoByKey(dto);
    }
}
