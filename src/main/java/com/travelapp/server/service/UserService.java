package com.travelapp.server.service;

import com.travelapp.server.entity.Role;
import com.travelapp.server.entity.User;
import com.travelapp.server.exception.AuthenticationException;
import java.util.List;

public interface UserService {

    void saveUser(User user) throws AuthenticationException;

    void updateUser(User user);

    User findUserByUsername(String username);

    boolean ifUserExist(String username);

    boolean removeUserByUsername(String username);

    boolean removeUser(User user);

    Role findRoleByName(Role.RoleName name);

    void saveRole(Role role);

    Role findUserRoleByUsername(String username);
}
