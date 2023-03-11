package com.travelapp.server.dto;

import lombok.Data;

@Data
public class UserDataResponseDto {

    private String username;

    private String password;

    private String email;
}
