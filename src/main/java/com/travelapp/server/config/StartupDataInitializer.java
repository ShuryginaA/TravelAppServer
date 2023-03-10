package com.travelapp.server.config;

import com.travelapp.server.entity.Role;
import com.travelapp.server.entity.User;
import com.travelapp.server.exception.AuthenticationException;
import com.travelapp.server.repository.RoleRepository;
import com.travelapp.server.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class StartupDataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final String ADMIN_USERNAME = "admin";

    private boolean isAlreadySetup;

    private final UserService userService;

    @SneakyThrows
    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (isAlreadySetup) return;
        createDefaultUsersAndRoles();
    }

    private void createDefaultUsersAndRoles() throws AuthenticationException {
        Role adminRole = createRoleIfNotFound(Role.RoleName.ADMIN);
        createRoleIfNotFound(Role.RoleName.USER);

        User admin = new User(ADMIN_USERNAME,
                "admin",
                "admin@mail.ru"
        );
        admin.setRole(adminRole);
        admin.setEnabled(true);
        if (!userService.ifUserExist(ADMIN_USERNAME)) {
            userService.saveUser(admin);
        }

        isAlreadySetup = true;
    }

    @Transactional
    public Role createRoleIfNotFound(Role.RoleName name) {
        Role role = userService.findRoleByName(name);
        if (role == null) {
            role = new Role(name);
            userService.saveRole(role);
        }
        return role;
    }
}
