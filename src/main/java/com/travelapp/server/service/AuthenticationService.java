package com.travelapp.server.service;

import com.travelapp.server.exception.AuthenticationException;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public interface AuthenticationService {

    boolean isAuthenticated();

    Collection<? extends GrantedAuthority> getLoggedUserRoles();

    void autoLogin(String username, String password) throws AuthenticationException;

    void updateAuthoritiesInSecurityContext(Authentication authentication);

    public String getRoleFromAuth(Authentication authentication);
}
