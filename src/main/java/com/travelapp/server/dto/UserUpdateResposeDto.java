package com.travelapp.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserUpdateResposeDto {

    private String username;

    private String email;

    private String phone;
}
