package com.travelapp.server.repository;

import com.travelapp.server.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Nullable
    User findByUsername(String username);

    @Nullable
    List<User> findByEmail(String email);

    @Nullable
    Long deleteByUsername(String username);
}
