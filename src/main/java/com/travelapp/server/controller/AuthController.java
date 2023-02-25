package com.travelapp.server.controller;

import com.travelapp.server.dto.AuthDto;
import com.travelapp.server.dto.LoginDto;
import com.travelapp.server.dto.RegistrationDto;
import com.travelapp.server.entity.Role;
import com.travelapp.server.entity.User;
import com.travelapp.server.exception.AuthenticationException;
import com.travelapp.server.service.AuthenticationService;
import com.travelapp.server.service.SecurityService;
import com.travelapp.server.service.UserService;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    private final AuthenticationService authenticationService;

    private final SecurityService securityService;

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        return "login";
    }

    @PostMapping("/auth")
    public AuthDto authenticate (@RequestBody LoginDto loginDto) {
        if (authenticationService.isAuthenticated()){
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            return new AuthDto(authenticationService.getRoleFromAuth(
                authentication),
                userService.findUserByUsername(loginDto.getUsername()).getId());
        }
        if(userService.findUserByUsername(loginDto.getUsername())==null){
            return new AuthDto("Unauthorized",null);
        }
        return new AuthDto(authenticationService.getRoleFromAuth(
            securityService.authenticate(loginDto)),
            userService.findUserByUsername(loginDto.getUsername()).getId());
    }

    @PostMapping("/registration")
    public String processRegistration(@RequestBody RegistrationDto registrationDto)
        throws AuthenticationException {
        User user = registrationDto.toUser();
        user.setRole(userService.findRoleByName(Role.RoleName.USER));
        String unhashedPassword = user.getPassword();
        userService.saveUser(user);
        authenticationService.autoLogin(user.getUsername(), unhashedPassword);
        return "Success";

    }
    @GetMapping("/logout")
    public String logout() {
        if (authenticationService.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return null;
    }
}
