package com.travelapp.server.mapper;

import com.travelapp.server.dto.UserDataResponseDto;
import com.travelapp.server.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper{

    UserDataResponseDto userResponseDto(User user);
}
