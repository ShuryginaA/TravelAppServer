package com.travelapp.server.service.impl;

import com.travelapp.server.dto.LoginDto;
import com.travelapp.server.service.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SecurityServiceImpl implements SecurityService {

	private final static Logger logger = LoggerFactory.getLogger(SecurityService.class);

	@Autowired
	AuthenticationManager authenticationManager;


	public Authentication authenticate(LoginDto loginDto) {

		UsernamePasswordAuthenticationToken authRequest =
				new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
		Authentication result = authenticationManager.authenticate(authRequest);

		if(result == null) {
			return null;
		}
		SecurityContextHolder.getContext().setAuthentication(result);
		return result;

	}

}
