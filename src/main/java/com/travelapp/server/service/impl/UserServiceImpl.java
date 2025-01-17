package com.travelapp.server.service.impl;

import com.travelapp.server.dto.TourResponseData;
import com.travelapp.server.dto.UserUpdateRequestDto;
import com.travelapp.server.dto.UserDataResponseDto;
import com.travelapp.server.dto.UserRequestDto;
import com.travelapp.server.dto.UserUpdateResposeDto;
import com.travelapp.server.entity.Role;
import com.travelapp.server.entity.User;
import com.travelapp.server.exception.AuthenticationException;
import com.travelapp.server.mapper.TourMapper;
import com.travelapp.server.mapper.UserMapper;
import com.travelapp.server.repository.PhotoRepository;
import com.travelapp.server.repository.RoleRepository;
import com.travelapp.server.repository.UserRepository;
import com.travelapp.server.service.UserService;
import java.io.File;
import java.util.List;
import java.util.Optional;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PhotoRepository photoRepository;

    private final UserMapper userMapper;

    private final TourMapper tourMapper;

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
            return userRepository.save(user).getId();
        }
        throw new AuthenticationException();
    }

    @Override
    @Transactional
    public UserUpdateResposeDto updateUser(Long id, UserUpdateRequestDto userRequestDto) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new NotFoundException("Пользователь не найден"); }
        userMapper.updateUser(userRequestDto, user.get());
        return userMapper.toUserUpdateResponseDto(userRepository.save(user.get()));
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
    public UserDataResponseDto findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return userMapper.toUserResponseDto(user.get());
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

    @Override
    public List<TourResponseData> getUserTours(Long userId) {
        return userRepository.findById(userId).orElseThrow(NotFoundException::new).getTours()
            .stream().map(tourMapper::toResponseDto).toList();
    }
}
