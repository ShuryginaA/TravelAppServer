package com.travelapp.server.controller;

import com.travelapp.server.dto.AuthDto;
import com.travelapp.server.dto.HelloDto;
import com.travelapp.server.dto.LoginDto;
import com.travelapp.server.dto.RegistrationDto;
import com.travelapp.server.dto.RegistrationResponseDto;
import com.travelapp.server.entity.Role;
import com.travelapp.server.entity.User;
import com.travelapp.server.exception.AuthenticationException;
import com.travelapp.server.service.AuthenticationService;
import com.travelapp.server.service.SecurityService;
import com.travelapp.server.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Tag(name = "Безопасность", description = "Операции регистрации, авторизации и выхода из системы")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final SecurityService securityService;

    @GetMapping("/hello")
    @Operation(summary =  "Проверочный эндпойнт")
    public HelloDto hello() {
       return new HelloDto("Hi, friend. I am server");
    }

    @PostMapping("/login")
    @Operation(summary = "Вход в систему")
    public AuthDto authenticate(@RequestBody LoginDto loginDto) {
        if (authenticationService.isAuthenticated()){
            return new AuthDto(userService.findUserByUsername(loginDto.getUsername()).getId());
        }
        securityService.authenticate(loginDto);
        if(userService.findUserByUsername(loginDto.getUsername())==null){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Пользователь не найден в системе");
        }
        return new AuthDto(userService.findUserByUsername(loginDto.getUsername()).getId());
    }

    @PostMapping("/registration")
    @Operation(summary = "Регистрация")
    public RegistrationResponseDto processRegistration(@RequestBody RegistrationDto registrationDto)
        throws AuthenticationException {
        User user = registrationDto.toUser();
        user.setRole(userService.findRoleByName(Role.RoleName.USER));
         return new RegistrationResponseDto(userService.saveUser(user));

    }
    @GetMapping("/logout")
    @Operation(summary = "Выход из системы")
    public String logout() {
        if (authenticationService.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return null;
    }
}
