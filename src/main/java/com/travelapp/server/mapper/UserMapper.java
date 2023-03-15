package com.travelapp.server.mapper;

import com.travelapp.server.dto.UserUpdateRequestDto;
import com.travelapp.server.dto.UserUpdateResposeDto;
import com.travelapp.server.dto.UserDataResponseDto;
import com.travelapp.server.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper{

    UserDataResponseDto toUserResponseDto(User user);

    UserUpdateResposeDto toUserUpdateResponseDto(User entity);

    void updateUser(UserUpdateRequestDto userRequestDto, @MappingTarget User user);
}
