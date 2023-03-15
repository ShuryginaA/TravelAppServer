package com.travelapp.server.service;

import com.travelapp.server.dto.UserUpdateRequestDto;
import com.travelapp.server.dto.UserRequestDto;
import com.travelapp.server.dto.UserDataResponseDto;
import com.travelapp.server.dto.UserUpdateResposeDto;
import com.travelapp.server.entity.Role;
import com.travelapp.server.entity.User;
import com.travelapp.server.exception.AuthenticationException;
import javax.ws.rs.core.Response;

public interface UserService {

    Long saveUser(User user) throws AuthenticationException;

    UserUpdateResposeDto updateUser(Long id, UserUpdateRequestDto userRequestDto);

    User findUserByUsername(String username);

    boolean ifUserExist(String username);

    boolean removeUserByUsername(String username);

    boolean removeUser(User user);

    Role findRoleByName(Role.RoleName name);

    void saveRole(Role role);

    Role findUserRoleByUsername(String username);

    UserDataResponseDto findUserById(Long id);

    Response findUserPhotoByKey(UserRequestDto dto);
}
