package com.travelapp.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

@Data
@AllArgsConstructor
public class RegistrationResponseDto {

    private Long userId;
}
