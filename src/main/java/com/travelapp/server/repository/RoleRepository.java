package com.travelapp.server.repository;

import com.travelapp.server.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Nullable
    Role findByName(Role.RoleName name);
}
