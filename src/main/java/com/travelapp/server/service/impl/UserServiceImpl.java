package com.travelapp.server.service.impl;

import com.travelapp.server.dto.UserDataResponseDto;
import com.travelapp.server.dto.UserRequestDto;
import com.travelapp.server.entity.Photo;
import com.travelapp.server.entity.Role;
import com.travelapp.server.entity.User;
import com.travelapp.server.exception.AuthenticationException;
import com.travelapp.server.mapper.UserMapper;
import com.travelapp.server.repository.PhotoRepository;
import com.travelapp.server.repository.RoleRepository;
import com.travelapp.server.repository.UserRepository;
import com.travelapp.server.service.UserService;
import java.io.File;
import java.util.Optional;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PhotoRepository photoRepository;

    private final UserMapper userMapper;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public Long saveUser(User user) throws AuthenticationException {

        if (user.getUsername().isEmpty() ||
            user.getPassword().isEmpty()) {
            throw new AuthenticationException();
        }

        if (user.getRole() == null) {
            throw new AuthenticationException();
        }

        User anotherUser = userRepository.findByUsername(user.getUsername());
        log.info("processing user: " + user.getUsername());
        if (anotherUser == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            Long i = userRepository.save(user).getId();
            return i;
        }
        throw new AuthenticationException();
    }

    @Override
    public void updateUser(User user) {
        User oldUser = userRepository.findByUsername(user.getUsername());
        if (oldUser == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        } else {
            user.setId(oldUser.getId());
        }
        userRepository.save(user);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean ifUserExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public boolean removeUserByUsername(String username) {
        return userRepository.deleteByUsername(username) != null;
    }

    @Override
    public boolean removeUser(User user) {
        boolean exists = userRepository.findByUsername(user.getUsername()) != null;
        if (exists) {
            userRepository.delete(user);
        }
        return exists;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User doesn't exist");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
            user.getPassword(), user.getAuthorities());
    }

    @Override
    public Role findRoleByName(Role.RoleName name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role findUserRoleByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user != null) {
            return user.getRole();
        }
        throw new UsernameNotFoundException("No such user");
    }

    @Override
    public UserDataResponseDto findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.userResponseDto(user.get());
        }
        throw new UsernameNotFoundException("No such user");
    }

    @Override
    public Response findUserPhotoByKey(UserRequestDto dto) {
//        String photoKey = userRepository.findById(dto.getUserId())
//            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Пользователь не найден"))
//            .getProfilePhotoKey();
        File file = new File("src/main/resources/photos/" + "profile" + ".jpg");
        return Response.ok(file, MediaType.APPLICATION_OCTET_STREAM)
            .header("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"") //optional
            .build();
    }
}
