package com.travelapp.server.service;

import com.travelapp.server.dto.LoginDto;
import org.springframework.security.core.Authentication;

public interface SecurityService {

    Authentication authenticate(LoginDto loginDto);


}
